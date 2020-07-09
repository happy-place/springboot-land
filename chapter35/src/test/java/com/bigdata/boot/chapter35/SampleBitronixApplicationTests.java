package com.bigdata.boot.chapter35;

import bitronix.tm.resource.jms.PoolingConnectionFactory;
import org.assertj.core.api.Condition;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *
 * @author Phillip Webb
 */
public class SampleBitronixApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testTransactionRollback() throws Exception {
		SampleBitronixApplication.main(new String[] {});
		String output = this.outputCapture.toString();
		assertThat(output).has(substring(1, "receive message from accounts:"));
		assertThat(output).has(substring(1, "receive message from accounts: josh"));
		assertThat(output).has(substring(2, "Count is 1"));
		assertThat(output).has(substring(1, "Simulated error"));
	}

	@Test
	public void testExposesXaAndNonXa() {
		ApplicationContext context = SpringApplication.run(SampleBitronixApplication.class);
		Object jmsConnectionFactory = context.getBean("jmsConnectionFactory");
		Object xaJmsConnectionFactory = context.getBean("xaJmsConnectionFactory");
		Object nonXaJmsConnectionFactory = context.getBean("nonXaJmsConnectionFactory");
		assertThat(jmsConnectionFactory).isSameAs(xaJmsConnectionFactory);
		assertThat(jmsConnectionFactory).isInstanceOf(PoolingConnectionFactory.class);
		assertThat(nonXaJmsConnectionFactory).isNotInstanceOf(PoolingConnectionFactory.class);
	}

	private Condition<String> substring(int times, String substring) {
		return new Condition<String>(
				"containing '" + substring + "' " + times + " times") {
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