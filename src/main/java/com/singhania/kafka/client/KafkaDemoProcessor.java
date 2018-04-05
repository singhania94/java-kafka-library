package com.citi.ace.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citi.ace.AceKafkaConsumerRecord;
import com.citi.ace.AceKafkaOnMessageInterface;

public class KafkaDemoProcessor implements AceKafkaOnMessageInterface<String, String> {

	private static Logger logger = LoggerFactory.getLogger(KafkaDemoProcessor.class);

	public void onMessage(AceKafkaConsumerRecord<String, String> consumerRecord) {
		logger.info(consumerRecord.toString());
		System.out.println(consumerRecord);
	}

}
