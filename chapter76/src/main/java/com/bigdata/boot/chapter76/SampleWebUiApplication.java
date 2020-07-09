package com.bigdata.boot.chapter76;

import com.bigdata.boot.chapter76.dao.MessageRepository;
import com.bigdata.boot.chapter76.dao.impl.InMemoryMessageRepository;
import com.bigdata.boot.chapter76.domain.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class SampleWebUiApplication {

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRepository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleWebUiApplication.class, args);
	}

}