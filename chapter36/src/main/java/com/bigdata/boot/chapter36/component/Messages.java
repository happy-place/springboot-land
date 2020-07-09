package com.bigdata.boot.chapter36.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Messages {

	@JmsListener(destination = "queue")
	public void onMessage(String content) {
		log.info("receive message from accounts: " + content);
	}

}