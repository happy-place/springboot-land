package com.bigdata.boot.chapter55;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.system.OutputCaptureRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleSimpleApplication}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 */
public class SampleSimpleApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	private String profiles;

	@Before
	public void init() {
		this.profiles = System.getProperty("spring.profiles.active");
	}

	@After
	public void after() {
		if (this.profiles != null) {
			System.setProperty("spring.profiles.active", this.profiles);
		}
		else {
			System.clearProperty("spring.profiles.active");
		}
	}

	@Test
	public void testDefaultSettings() throws Exception {
		SampleSimpleApplication.main(new String[0]);
		String output = this.outputCapture.getOut();
		assertThat(output).contains("Hello Phil");
	}

	@Test
	public void testCommandLineOverrides() throws Exception {
		SampleSimpleApplication.main(new String[] { "--name=Gordon", "--duration=1m" });
		String output = this.outputCapture.toString();
		assertThat(output).contains("Hello Gordon for 60 seconds");
	}

}