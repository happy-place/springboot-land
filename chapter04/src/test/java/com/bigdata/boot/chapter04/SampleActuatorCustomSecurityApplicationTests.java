package com.bigdata.boot.chapter04;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Madhura Bhave
 * @author Stephane Nicoll
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleActuatorCustomSecurityApplicationTests {

	@Autowired
	private Environment environment;

	@Test
	public void homeIsSecure() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = restTemplate().getForEntity("/", Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED); // 不带授权访问根，反馈UNAUTHORIZED
		@SuppressWarnings("unchecked")
		Map<String, Object> body = entity.getBody();
		assertThat(body.get("error")).isEqualTo("Unauthorized"); // 异常为 Unauthorized
		assertThat(entity.getHeaders()).doesNotContainKey("Set-Cookie"); // 响应中不包含 Set-Cookie 请求头
	}

	@Test
	public void testInsecureApplicationPath() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = restTemplate().getForEntity("/example/foo", Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);// 内部异常
		@SuppressWarnings("unchecked")
		Map<String, Object> body = entity.getBody();
		assertThat((String) body.get("message")).contains("Expected exception in controller"); // 响应message字段
	}

	@Test
	public void testInsecureStaticResources() {
		// 静态资源 直接放行
		ResponseEntity<String> entity = restTemplate()
				.getForEntity("/css/bootstrap.min.css", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("body");
	}

	@Test
	public void actuatorInsecureEndpoint() {
		// /actuator/health、/actuator/info 接口 直接放行
		ResponseEntity<String> entity = restTemplate().getForEntity("/actuator/health", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"status\":\"UP\""); // 服务状态 UP
	}

	@Test
	public void actuatorLinksIsSecure() {
		// /actuator 需要 admin 才能访问
		ResponseEntity<Object> entity = restTemplate().getForEntity("/actuator", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		entity = adminRestTemplate().getForEntity("/actuator", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void actuatorSecureEndpointWithAnonymous() {
		// /actuator/env 无法直接访问
		ResponseEntity<Object> entity = restTemplate().getForEntity("/actuator/env", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void actuatorSecureEndpointWithUnauthorizedUser() {
		// user 用户无法访问 /actuator/env
		ResponseEntity<Object> entity = userRestTemplate().getForEntity("/actuator/env", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void actuatorSecureEndpointWithAuthorizedUser() {
		// admin 允许访问 /actuator/env
		ResponseEntity<Object> entity = adminRestTemplate().getForEntity("/actuator/env", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void actuatorCustomMvcSecureEndpointWithAnonymous() {
		// 匿名用户无法访问  /actuator/example-rest/echo
		ResponseEntity<String> entity = restTemplate().getForEntity("/actuator/example-rest/echo?text={t}", String.class, "test");
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void actuatorCustomMvcSecureEndpointWithUnauthorizedUser() {
		// user 用户允许访问 /actuator/example-rest/echo
		ResponseEntity<String> entity = userRestTemplate().getForEntity("/actuator/example-rest/echo?text={t}", String.class, "test");
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void actuatorCustomMvcSecureEndpointWithAuthorizedUser() {
		// admin 用户允许访问 /actuator/example-rest/echo
		ResponseEntity<String> entity = adminRestTemplate().getForEntity("/actuator/example-rest/echo?text={t}", String.class, "test");
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo("test"); // 响应内容 test
		assertThat(entity.getHeaders().getFirst("echo")).isEqualTo("test");
	}

	@Test
	public void actuatorExcludedFromEndpointRequestMatcher() {
		// /actuator/mappings 访问无需 admin ，user身份即可
		ResponseEntity<Object> entity = userRestTemplate().getForEntity("/actuator/mappings", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void mvcMatchersCanBeUsedToSecureActuators() {
		ResponseEntity<Object> entity = beansRestTemplate().getForEntity("/actuator/beans", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		entity = beansRestTemplate().getForEntity("/actuator/beans/", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
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