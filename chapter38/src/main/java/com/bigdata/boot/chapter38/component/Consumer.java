package com.bigdata.boot.chapter38.component;

import com.bigdata.boot.chapter38.domain.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class Consumer {

	@KafkaListener(topics = "testTopic")
	public void processMessage(SampleMessage message) {
		log.info("Receive message [{}].",message);
	}

}