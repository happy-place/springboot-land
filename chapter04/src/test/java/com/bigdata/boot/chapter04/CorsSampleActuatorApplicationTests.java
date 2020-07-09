package com.bigdata.boot.chapter04;

import java.net.URI;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for cors preflight requests to management endpoints.
 *
 * @author Madhura Bhave
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("cors")
public class CorsSampleActuatorApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@LocalServerPort
	int randomServerPort;

	@Autowired
	private Environment environment;


	@Test
	public void endpointShouldReturnUnauthorized() {
		ResponseEntity<?> entity = restTemplate().getForEntity("/actuator/env",
				Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void preflightRequestToEndpointShouldReturnOk() throws Exception {
		RequestEntity<?> healthRequest = RequestEntity.options(new URI("/actuator/env"))
				.header("Origin", "http://localhost:"+randomServerPort)
				.header("Access-Control-Request-Method", "GET").build();
		ResponseEntity<byte[]> exchange = userRestTemplate().exchange(healthRequest, byte[].class);
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void preflightRequestWhenCorsConfigInvalidShouldReturnForbidden()
			throws Exception {
		int otherPort = randomServerPort + 1;
		RequestEntity<?> entity = RequestEntity.options(new URI("/actuator/env"))
				.header("Origin", "http://localhost:"+otherPort)
				.header("Access-Control-Request-Method", "GET").build();
		ResponseEntity<byte[]> exchange = userRestTemplate().exchange(entity,
				byte[].class);
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	private TestRestTemplate restTemplate() {
		return configure(new TestRestTemplate());
	}

	private TestRestTemplate adminRestTemplate() {
		return configure(new TestRestTemplate("admin", "admin"));
	}

	private TestRestTemplate userRestTemplate() {
		return configure(new TestRestTemplate("user", "password"));
	}

	private TestRestTemplate beansRestTemplate() {
		return configure(new TestRestTemplate("beans", "beans"));
	}

	private TestRestTemplate configure(TestRestTemplate restTemplate) {
		restTemplate.setUriTemplateHandler(new LocalHostUriTemplateHandler(this.environment));
		return restTemplate;
	}

}