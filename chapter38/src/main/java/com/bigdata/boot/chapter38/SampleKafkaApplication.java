package com.bigdata.boot.chapter38;

import com.bigdata.boot.chapter38.component.Producer;
import com.bigdata.boot.chapter38.domain.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleKafkaApplication.class, args);
	}

	@Autowired
	private Producer producer;

	@Override
	public void run(String... args) throws Exception {
		String msg = "A simple test message";
		producer.send(new SampleMessage(1, msg));
	}

}