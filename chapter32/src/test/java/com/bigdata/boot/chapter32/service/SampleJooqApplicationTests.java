package com.bigdata.boot.chapter32.service;

import com.bigdata.boot.chapter32.SampleJooqApplication;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.system.OutputCaptureRule;

import static org.assertj.core.api.Assertions.assertThat;


public class SampleJooqApplicationTests {

	private static final String[] NO_ARGS = {};

	@Rule
	public OutputCaptureRule out = new OutputCaptureRule();

	@Test
	public void outputResults() {
		SampleJooqApplication.main(NO_ARGS);
		assertThat(this.out.toString()).contains("jOOQ Fetch 1 Greg Turnquest");
		assertThat(this.out.toString()).contains("jOOQ Fetch 2 Craig Walls");
		assertThat(this.out.toString())
				.contains("jOOQ SQL " + "[Learning Spring Boot : Greg Turnquest, "
						+ "Spring Boot in Action : Craig Walls]");
	}

}