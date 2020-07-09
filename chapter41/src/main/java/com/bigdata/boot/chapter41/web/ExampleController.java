package com.bigdata.boot.chapter41.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@RequestMapping("/")
	public String email(Principal principal) {
		return "Hello " + principal.getName();
	}

}