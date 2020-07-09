package com.bigdata.boot.chapter49;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for a secure reactive application.
 *
 * @author Madhura Bhave
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "management.endpoint.health.show-details=never")
public class SampleSecureWebFluxApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void userDefinedMappingsSecureByDefault() {
		this.webClient.get().uri("/").accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void healthInsecureByDefault() {
		this.webClient.get().uri("/actuator/health").accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk();
	}

	@Test
	public void infoInsecureByDefault() {
		this.webClient.get().uri("/actuator/info").accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk();
	}

	@Test
	public void otherActuatorsSecureByDefault() {
		this.webClient.get().uri("/actuator/env").accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isUnauthorized();
	}

	@Test
	public void userDefinedMappingsAccessibleOnLogin() {
		this.webClient.get().uri("/").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "basic " + getBasicAuth()).exchange()
				.expectBody(String.class).isEqualTo("Hello user");
	}

	@Test
	public void actuatorsAccessibleOnLogin() {
		this.webClient.get().uri("/actuator/health").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "basic " + getBasicAuth()).exchange()
				.expectBody(String.class).isEqualTo("{\"status\":\"UP\"}");
	}

	private String getBasicAuth() {
		return new String(Base64.getEncoder().encode(("user:password").getBytes()));
	}

}