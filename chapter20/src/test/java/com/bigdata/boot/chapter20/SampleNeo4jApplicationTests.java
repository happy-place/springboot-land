package com.bigdata.boot.chapter20;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.exceptions.ServiceUnavailableException;
import org.springframework.boot.test.system.OutputCaptureRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleNeo4jApplication}.
 *
 * @author Stephane Nicoll
 */
public class SampleNeo4jApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testDefaultSettings() {
		try {
			SampleNeo4jApplication.main(new String[0]);
		}
		catch (Exception ex) {
			if (!neo4jServerRunning(ex)) {
				return;
			}
		}
		String output = this.outputCapture.getOut();
		assertThat(output).contains("firstName='Alice', lastName='Smith'");
	}

	private boolean neo4jServerRunning(Throwable ex) {
		if (ex instanceof ServiceUnavailableException) {
			return false;
		}
		return (ex.getCause() == null || neo4jServerRunning(ex.getCause()));
	}

}