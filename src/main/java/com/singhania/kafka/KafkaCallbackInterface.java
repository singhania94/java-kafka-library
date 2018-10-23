package com.singhania.kafka;

public interface AceKafkaCallbackInterface {

	public void onCompletion(AceKafkaProducerRecordMetadata metadata, Exception exception);
}
