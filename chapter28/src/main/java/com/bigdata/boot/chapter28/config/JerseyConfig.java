package com.bigdata.boot.chapter28.config;

import com.bigdata.boot.chapter28.endpoint.HelloEndpoint;
import com.bigdata.boot.chapter28.endpoint.ReverseEndpoint;
import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(HelloEndpoint.class);
		register(ReverseEndpoint.class);
	}

}