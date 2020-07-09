package com.bigdata.boot.chapter06.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

// 直接从 application.properties 引入变量
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = true)
public class ServiceProperties {

	/**
	 * Name of the service.
	 */
	private String name = "World";

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}