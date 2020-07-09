package com.bigdata.boot.chapter28.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.bigdata.boot.chapter28.service.HelloService;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class HelloEndpoint {

	private final HelloService helloService;

	public HelloEndpoint(HelloService helloService) {
		this.helloService = helloService;
	}

	@GET
	public String message() {
		return "Hello " + this.helloService.message();
	}

}