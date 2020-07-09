package com.bigdata.boot.chapter82;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.system.OutputCaptureRule;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleSpringXmlApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testDefaultSettings() throws Exception {
		SampleSpringXmlApplication.main(new String[0]);
		String output = this.outputCapture.getOut();
		assertThat(output).contains("Hello World");
	}

}