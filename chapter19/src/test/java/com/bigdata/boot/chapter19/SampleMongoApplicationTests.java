package com.bigdata.boot.chapter19;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleMongoApplicationTests {

	@ClassRule
	public static OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testDefaultSettings() {
		String output = SampleMongoApplicationTests.outputCapture.getOut();
		assertThat(output).contains("firstName='Alice', lastName='Smith'");
	}

}