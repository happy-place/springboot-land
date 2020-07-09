package com.bigdata.boot.chapter37;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleJunitJupiterApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testMessage() {
		String message = this.restTemplate.getForObject("/hi", String.class);
		assertThat(message).isEqualTo("Hello World");
	}

}