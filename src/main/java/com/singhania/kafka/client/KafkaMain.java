package com.citi.ace.client;

import java.io.IOException;

import com.citi.ace.AceKafkaConsumerThread;
import com.citi.ace.AceKafkaAsyncProducer;

public class KafkaMain {

	public static void main(String[] args) throws IOException, InterruptedException {

		AceKafkaConsumerThread<String, String> consumer = new AceKafkaConsumerThread<String, String>("TOPIC_2", new KafkaDemoProcessor());

		for (int i = 0; i < 30; i++) {
			AceKafkaAsyncProducer<String, String> producer = new AceKafkaAsyncProducer<String, String>("TOPIC_2");
			producer.sendMessage("msg-only-1");
			producer.sendMessage("msg-only-2");
			producer.sendMessage("msg-only-3");
			producer.sendMessage("msg-only-4");
		}

		consumer.start();
		System.out.println("Exiting");
	}
}
