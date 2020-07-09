package com.bigdata.boot.chapter46;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleQuartzApplication}.
 *
 * @author Eddú Meléndez
 */
public class SampleQuartzApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void quartzJobIsTriggered() throws InterruptedException {
		try (ConfigurableApplicationContext context = SpringApplication.run(SampleQuartzApplication.class)) {
			long end = System.currentTimeMillis() + 5000;
			while ((!this.outputCapture.getOut().contains("Hello World!"))
					&& System.currentTimeMillis() < end) {
				Thread.sleep(100);
			}
			assertThat(this.outputCapture.getOut()).contains("Hello World!");
		}
	}

}