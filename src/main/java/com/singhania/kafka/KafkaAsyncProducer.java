package com.singhania.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AceKafkaAsyncProducer<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(AceKafkaAsyncProducer.class);

	private String prefix;
	private String topic;
	private final Properties configs;
	private final KafkaProducer<K, V> producer;
	private final Callback callback;

	public AceKafkaAsyncProducer() throws IOException {
		this("", AceKafkaConstants.PRODUCER_PROPERTY_FILE_DEFAULT);
	}

	public AceKafkaAsyncProducer(String topic) throws IOException {
		this(topic, AceKafkaConstants.PRODUCER_PROPERTY_FILE_DEFAULT);
	}

	public AceKafkaAsyncProducer(String topic, String filename) throws IOException {
		this(topic, filename, new AceKafkaCallbackInterface() {
			@Override
			public void onCompletion(AceKafkaProducerRecordMetadata metadata, Exception exception) {
				if (exception != null)
					logger.error("messageMetadata={};exception={};", metadata, exception);
				else
					logger.debug("SentRecordMetadata={};", metadata);
			}
		});
	}

	public AceKafkaAsyncProducer(String topic, String filename, final AceKafkaCallbackInterface callback)
			throws IOException {
		this.topic = topic;
		this.configs = loadProducerProperties(filename);
		this.producer = loadKafkaProducer(configs);
		this.callback = new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if(metadata != null)
					callback.onCompletion(new AceKafkaProducerRecordMetadata(metadata), exception);
			}
		};
	}

	private Properties loadProducerProperties(String filename) throws IOException {

		InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
		Properties ipConfigs = new Properties();
		Properties configs = new Properties();

		ipConfigs.load(stream);
		this.prefix = ipConfigs.getProperty(AceKafkaConstants.PRODUCER_TOPIC_PREFIX, AceKafkaConstants.PRODUCER_TOPIC_PREFIX_DEFAULT);
		configs.put(ProducerConfig.RETRIES_CONFIG, ipConfigs.get(AceKafkaConstants.PRODUCER_RETRIES));
		configs.put(ProducerConfig.BATCH_SIZE_CONFIG, ipConfigs.get(AceKafkaConstants.PRODUCER_BATCH_SIZE));
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ipConfigs.get(AceKafkaConstants.PRODUCER_BOOTSTRAP_SERVERS));
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ipConfigs.get(AceKafkaConstants.KEY_SERIALIZER));
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ipConfigs.get(AceKafkaConstants.VALUE_SERIALIZER));
		return configs;
	}

	private KafkaProducer<K, V> loadKafkaProducer(Properties configs) {
		return new KafkaProducer<K, V>(configs);
	}

	public void sendMessage(V value) {
		sendMessage(topic, null, value);
	}

	public void sendMessage(K key, V value) {
		sendMessage(topic, key, value);
	}

	public void sendMessageOnKey(K key, V value) {
		if ((key instanceof String))
			sendMessage(prefix + (String) key, key, value);
		else {
			logger.error("Inappropriate value of key has been provided. Aborting send for key={};value={};", key,
					value);
		}
	}

	public void sendMessage(String topic, K key, V value) {
		if (!topic.isEmpty()) {
			producer.send(new ProducerRecord<K, V>(topic, key, value), callback);
		} else {
			logger.error("Topic provided is empty for the request. topic={};key={};value={};", topic, key, value);
		}
	}

	public void close() {
		producer.close();
	}

	public String getPrefix() {
		return prefix;
	}

	public String getTopic() {
		return topic;
	}

	public Properties getConfigs() {
		return new Properties(configs);
	}
}
