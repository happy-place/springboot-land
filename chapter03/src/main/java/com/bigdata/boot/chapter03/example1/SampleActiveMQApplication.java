package com.bigdata.boot.chapter03.example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SampleActiveMQApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActiveMQApplication.class, args);
	}

}