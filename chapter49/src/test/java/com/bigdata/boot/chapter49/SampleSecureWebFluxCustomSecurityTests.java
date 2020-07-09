package com.bigdata.boot.chapter49;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for a secure reactive application with custom security.
 *
 * @author Madhura Bhave
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		SampleSecureWebFluxCustomSecurityTests.SecurityConfiguration.class,
		SampleSecureWebFluxApplication.class })
public class SampleSecureWebFluxCustomSecurityTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void userDefinedMappingsSecure() {
		this.webClient.get().uri("/").accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void healthAndInfoDoNotRequireAuthentication() {
		this.webClient.get().uri("/actuator/health").accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk();
		this.webClient.get().uri("/actuator/info").accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk();
	}

	@Test
	public void actuatorsSecuredByRole() {
		this.webClient.get().uri("/actuator/env").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "basic " + getBasicAuth()).exchange()
				.expectStatus().isForbidden();
	}

	@Test
	public void actuatorsAccessibleOnCorrectLogin() {
		this.webClient.get().uri("/actuator/env").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "basic " + getBasicAuthForAdmin()).exchange()
				.expectStatus().isOk();
	}

	@Test
	public void actuatorExcludedFromEndpointRequestMatcher() {
		this.webClient.get().uri("/actuator/mappings").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "basic " + getBasicAuth()).exchange()
				.expectStatus().isOk();
	}

	@Test
	public void staticResourceShouldBeAccessible() {
		this.webClient.get().uri("/css/bootstrap.min.css")
				.accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
	}

	@Test
	public void actuatorLinksIsSecure() {
		this.webClient.get().uri("/actuator").accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isUnauthorized();
		this.webClient.get().uri("/actuator").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "basic " + getBasicAuthForAdmin()).exchange()
				.expectStatus().isOk();
	}

	private String getBasicAuth() {
		return new String(Base64.getEncoder().encode(("user:password").getBytes()));
	}

	private String getBasicAuthForAdmin() {
		return new String(Base64.getEncoder().encode(("admin:admin").getBytes()));
	}

	@Configuration
	static class SecurityConfiguration {

		@SuppressWarnings("deprecation")
		@Bean
		public MapReactiveUserDetailsService userDetailsService() {
			return new MapReactiveUserDetailsService(
					User.withDefaultPasswordEncoder().username("user")
							.password("password").authorities("ROLE_USER").build(),
					User.withDefaultPasswordEncoder().username("admin").password("admin")
							.authorities("ROLE_ACTUATOR", "ROLE_USER").build());
		}

		@Bean
		public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
			return http.authorizeExchange().matchers(EndpointRequest.to("health", "info"))
					.permitAll()
					.matchers(EndpointRequest.toAnyEndpoint()
							.excluding(MappingsEndpoint.class))
					.hasRole("ACTUATOR")
					.matchers(PathRequest.toStaticResources().atCommonLocations())
					.permitAll().pathMatchers("/login").permitAll().anyExchange()
					.authenticated().and().httpBasic().and().build();
		}

	}

}