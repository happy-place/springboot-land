package com.bigdata.boot.chapter34;

import org.assertj.core.api.Condition;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.system.OutputCaptureRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *
 * @author Phillip Webb
 */
public class SampleAtomikosApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testTransactionRollback() throws Exception {
		SampleAtomikosApplication.main(new String[] {});
		String output = this.outputCapture.getOut();
		assertThat(output).has(substring(1, "receive message from accounts:"));
		assertThat(output).has(substring(1, "receive message from accounts: josh"));
		assertThat(output).has(substring(2, "Count is 1"));
		assertThat(output).has(substring(1, "Simulated error"));
	}

	private Condition<String> substring(int times, String substring) {
		return new Condition<String>("containing '" + substring + "' " + times + " times") {
			@Override
			public boolean matches(String value) {
				int i = 0;
				while (value.contains(substring)) {
					int beginIndex = value.indexOf(substring) + substring.length();
					value = value.substring(beginIndex);
					i++;
				}
				return i == times;
			}
		};
	}

}