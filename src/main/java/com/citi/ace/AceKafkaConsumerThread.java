package com.citi.ace;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AceKafkaConsumerThread<K, V> extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(AceKafkaConsumerThread.class);

	private final String topics;
	private final Properties configs;
	private final KafkaConsumer<K, V> consumer;
	private final AceKafkaOnMessageInterface<K, V> processor;

	public AceKafkaConsumerThread(String topics, AceKafkaOnMessageInterface<K, V> processor) throws IOException {
		this(topics, AceKafkaConstants.CONSUMER_PROPERTY_FILE_DEFAULT, processor);
	}

	public AceKafkaConsumerThread(String topics, String filename, AceKafkaOnMessageInterface<K, V> processor) throws IOException {
		super(topics + "_consumer_thread");
		this.topics = topics;
		this.configs = loadConsumerProperties(filename);
		this.consumer = loadKafkaConsumer(configs);
		this.processor = processor;
	}

	private Properties loadConsumerProperties(String filename) throws IOException {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
		Properties ipConfigs = new Properties();
		Properties configs = new Properties();

		ipConfigs.load(stream);
		configs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, ipConfigs.get(AceKafkaConstants.CONSUMER_FETCH_MIN_SIZE));
		configs.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, ipConfigs.get(AceKafkaConstants.CONSUMER_FETCH_MAX_WAIT));
		configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, ipConfigs.get(AceKafkaConstants.CONSUMER_ENABLE_AUTO_COMMIT));
		configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, ipConfigs.get(AceKafkaConstants.CONSUMER_AUTO_OFFSET_RESET));
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ipConfigs.get(AceKafkaConstants.CONSUMER_BOOTSTRAP_SERVERS));
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, ipConfigs.get(AceKafkaConstants.CONSUMER_GROUP_ID));
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ipConfigs.get(AceKafkaConstants.KEY_DESERAILIZER));
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ipConfigs.get(AceKafkaConstants.VALUE_DESERIALIZER));
		return configs;
	}

	private KafkaConsumer<K, V> loadKafkaConsumer(Properties configs) {
		return new KafkaConsumer<K, V>(configs);
	}

	public void run() {
		consume();
	}

	public void consume() {
		consumer.subscribe(Arrays.asList(topics.split(",")));
		logger.info("[KAFKA] Starting consumer block for topics {}", topics);
		try {
			while (true) {
				ConsumerRecords<K, V> records = consumer.poll(100);
				for (ConsumerRecord<K, V> record : records) {
					AceKafkaConsumerRecord<K, V> aceRecord = new AceKafkaConsumerRecord<>(record);
					logger.debug("ConsumedRecord={}", aceRecord);
					processor.onMessage(aceRecord);
				}
				logger.info("[KAFKA] Exiting consumer block for topics {}", topics);
			}
		} catch (WakeupException ex) {
			logger.info("Initiating kafka shutdown as requested.");
		} finally {
			consumer.close();
		}
	}

	public void terminate() {
		consumer.wakeup();
	}

	public String getTopics() {
		return topics;
	}

	public Properties getConfigs() {
		return new Properties(configs);
	}

	public AceKafkaOnMessageInterface<K, V> getProcessor() {
		return processor;
	}
}
