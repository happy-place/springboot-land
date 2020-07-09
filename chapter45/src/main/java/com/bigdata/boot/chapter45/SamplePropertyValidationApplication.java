package com.bigdata.boot.chapter45;

import com.bigdata.boot.chapter45.component.SampleProperties;
import com.bigdata.boot.chapter45.validation.SamplePropertiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

@SpringBootApplication
public class SamplePropertyValidationApplication implements CommandLineRunner {

	@Autowired
	private SampleProperties properties;

	@Bean
	public static Validator configurationPropertiesValidator() {
		return new SamplePropertiesValidator();
	}

	@Override
	public void run(String... args) {
		System.out.println("=========================================");
		System.out.println("Sample host: " + this.properties.getHost());
		System.out.println("Sample port: " + this.properties.getPort());
		System.out.println("=========================================");
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SamplePropertyValidationApplication.class).run(args);
	}

}