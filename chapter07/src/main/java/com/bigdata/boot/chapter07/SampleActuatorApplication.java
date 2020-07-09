package com.bigdata.boot.chapter07;

import com.bigdata.boot.chapter07.config.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ServiceProperties.class)
public class SampleActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActuatorApplication.class, args);
	}

	@Bean
	public HealthIndicator helloHealthIndicator() {
		return () -> Health.up().withDetail("hello", "world").build();
	}

}