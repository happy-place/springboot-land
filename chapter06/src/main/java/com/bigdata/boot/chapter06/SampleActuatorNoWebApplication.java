package com.bigdata.boot.chapter06;

import com.bigdata.boot.chapter06.properties.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServiceProperties.class)
public class SampleActuatorNoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActuatorNoWebApplication.class, args);
	}

}