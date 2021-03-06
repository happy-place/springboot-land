package com.bigdata.boot.chapter57;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sample application to demonstrate testing.
 *
 * @author Phillip Webb
 */
@SpringBootApplication
public class SampleTestApplication {

	// NOTE: this application will intentionally not start without MySQL, the test will
	// still run.

	public static void main(String[] args) {
		SpringApplication.run(SampleTestApplication.class, args);
	}

}