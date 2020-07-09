package com.bigdata.boot.chapter47;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"APP-CLIENT-ID=my-client-id", "APP-CLIENT-SECRET=my-client-secret",
		"YAHOO-CLIENT-ID=my-google-client-id",
		"YAHOO-CLIENT-SECRET=my-google-client-secret" })
public class SampleReactiveOAuth2ClientApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void everythingShouldRedirectToLogin() {
		this.webTestClient.get().uri("/").exchange().expectStatus().isFound()
				.expectHeader().valueEquals("Location", "/login");
	}

	@Test
	public void loginShouldHaveBothOAuthClientsToChooseFrom() {
		byte[] body = this.webTestClient.get().uri("/login").exchange().expectStatus()
				.isOk().returnResult(String.class).getResponseBodyContent();
		String bodyString = new String(body);
//		assertThat(bodyString).contains("/oauth2/authorization/yahoo");
		assertThat(bodyString).contains("/oauth2/authorization/github-client-1");
		assertThat(bodyString).contains("/oauth2/authorization/github-client-2");
	}

}