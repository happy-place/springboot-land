package com.bigdata.boot.chapter60;

import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.AbstractConfigurableWebServerFactory;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for {@link SampleTomcatTwoConnectorsApplication}.
 *
 * @author Brock Mills
 * @author Andy Wilkinson
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(SampleTomcatTwoConnectorsApplicationTests.Ports.class)
public class SampleTomcatTwoConnectorsApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private Ports ports;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AbstractConfigurableWebServerFactory webServerFactory;

	@Test
	public void testSsl() {
		assertThat(this.webServerFactory.getSsl().isEnabled()).isTrue();
	}

	@Test
	public void testHello() {
		assertThat(this.ports.getHttpsPort()).isEqualTo(this.port);
		assertThat(this.ports.getHttpPort()).isNotEqualTo(this.port);
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				"http://localhost:" + this.ports.getHttpPort() + "/hello", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo("hello");
		ResponseEntity<String> httpsEntity = this.restTemplate
				.getForEntity("https://localhost:" + this.port + "/hello", String.class);
		assertThat(httpsEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(httpsEntity.getBody()).isEqualTo("hello");
	}

	@TestConfiguration
	static class Ports implements ApplicationListener<WebServerInitializedEvent> {

		private int httpPort;

		private int httpsPort;

		@Override
		public void onApplicationEvent(WebServerInitializedEvent event) {
			Service service = ((TomcatWebServer) event.getWebServer()).getTomcat()
					.getService();
			for (Connector connector : service.findConnectors()) {
				if (connector.getSecure()) {
					this.httpsPort = connector.getLocalPort();
				}
				else {
					this.httpPort = connector.getLocalPort();
				}
			}
		}

		int getHttpPort() {
			return this.httpPort;
		}

		int getHttpsPort() {
			return this.httpsPort;
		}

	}

}