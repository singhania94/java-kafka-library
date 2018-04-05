package com.citi.ace;

public interface AceKafkaConstants {

	String PRODUCER_PROPERTY_FILE_DEFAULT = "producer.properties";
	String CONSUMER_PROPERTY_FILE_DEFAULT = "consumer.properties";

	String PRODUCER_RETRIES = "producer.retries";
	String PRODUCER_BATCH_SIZE = "producer.batch-size";
	String PRODUCER_BOOTSTRAP_SERVERS = "producer.bootstrap-servers";
	String PRODUCER_TOPIC_PREFIX = "producer.topic.prefix";
	String PRODUCER_TOPIC = "producer.topic";
	String KEY_SERIALIZER = "producer.key-serializer";
	String VALUE_SERIALIZER = "producer.value-serializer";

	int PRODUCER_RETRIES_DEFAULT = 1;
	int PRODUCER_BATCH_SIZE_DEFAULT = 1;
	String PRODUCER_BOOTSTRAP_SERVERS_DEFAULT = "localhost:9092";
	String PRODUCER_TOPIC_PREFIX_DEFAULT = "";
	String PRODUCER_TOPICS_DEFAULT = "";
	String KEY_SERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringSerializer";
	String VALUE_SERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringSerializer";

	String CONSUMER_FETCH_MIN_SIZE = "consumer.fetch-min-size";
	String CONSUMER_FETCH_MAX_WAIT = "consumer.fetch-max-wait";
	String CONSUMER_ENABLE_AUTO_COMMIT = "consumer.enable-auto-commit";
	String CONSUMER_AUTO_OFFSET_RESET = "consumer.auto-offset-reset";
	String CONSUMER_BOOTSTRAP_SERVERS = "consumer.bootstrap-servers";
	String CONSUMER_TOPICS = "consumer.topics";
	String CONSUMER_GROUP_ID = "consumer.group-id";
	String KEY_DESERAILIZER = "consumer.key-deserializer";
	String VALUE_DESERIALIZER = "consumer.value-deserializer";

	int CONSUMER_FETCH_MIN_SIZE_DEFAULT = 102400;
	int CONSUMER_FETCH_MAX_WAIT_DEFAULT = 5000;
	boolean CONSUMER_ENABLE_AUTO_COMMIT_DEFAULT = true;
	String CONSUMER_AUTO_OFFSET_RESET_DEFAULT = "earliest";
	String CONSUMER_BOOTSTRAP_SERVERS_DEFAULT = "localhost:9092";
	String CONSUMER_TOPICS_DEFAULT = "";
	String CONSUMER_GROUP_ID_DEFAULT = "default-consumer-group";
	String KEY_DESERAILIZER_DEFAULT = "org.apache.kafka.common.serialization.StringDeserializer";
	String VALUE_DESERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringDeserializer";

}
