package com.bigdata.boot.chapter43;

import com.bigdata.boot.chapter43.config.IntegrationConfig;
import com.bigdata.boot.chapter43.config.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServiceProperties.class)
public class SampleParentContextApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleParentContextApplication.class);
	}

}