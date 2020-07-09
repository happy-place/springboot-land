package com.bigdata.boot.chapter38.component;

import com.bigdata.boot.chapter38.domain.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {

	@Autowired
	private KafkaTemplate<Object, SampleMessage> kafkaTemplate;

	public void send(SampleMessage message) {
		kafkaTemplate.send("testTopic", message);
		log.info("Sent message [{}].", message);
	}

}