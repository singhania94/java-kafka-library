package com.singhania.kafka;

public interface AceKafkaOnMessageInterface<K, V> {

	void onMessage(AceKafkaConsumerRecord<K, V> aceRecord);
}
