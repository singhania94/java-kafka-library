package com.citi.ace;

public interface AceKafkaOnMessageInterface<K, V> {

	void onMessage(AceKafkaConsumerRecord<K, V> aceRecord);
}
