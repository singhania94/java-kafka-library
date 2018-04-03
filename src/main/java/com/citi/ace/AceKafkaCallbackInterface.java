package com.citi.ace;

public interface AceKafkaCallbackInterface {

	public void onCompletion(AceKafkaProducerRecordMetadata metadata, Exception exception);
}
