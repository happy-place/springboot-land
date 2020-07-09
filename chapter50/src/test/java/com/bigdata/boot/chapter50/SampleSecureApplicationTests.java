package com.bigdata.boot.chapter50;

import com.bigdata.boot.chapter50.service.SampleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *
 * @author Dave Syer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SampleSecureApplication.class })
public class SampleSecureApplicationTests {

	@Autowired
	private SampleService service;

	private Authentication authentication;

	@Before
	public void init() {
		this.authentication = new UsernamePasswordAuthenticationToken("user", "password");
	}

	@After
	public void close() {
		SecurityContextHolder.clearContext();
	}

	@Test(expected = AuthenticationException.class)
	public void secure() {
		assertThat("Hello Security").isEqualTo(this.service.secure());
	}

	@Test
	public void authenticated() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Hello Security").isEqualTo(this.service.secure());
	}

	@Test
	public void preauth() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Hello World").isEqualTo(this.service.authorized());
	}

	@Test/*(expected = AccessDeniedException.class)*/
	public void denied() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Goodbye World").isEqualTo(this.service.denied());
	}

}