package com.bigdata.boot.chapter70;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SampleMethodSecurityApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SampleMethodSecurityApplication.class).run(args);
	}

}