package com.bigdata.boot.chapter43.service;

import com.bigdata.boot.chapter43.config.ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

	@Autowired
	private ServiceProperties configuration;

	public String getHelloMessage(String name) {
		return this.configuration.getGreeting() + " " + name;
	}

}