package com.bigdata.boot.chapter52.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;


@RestController
public class HelloRestController {

	@GetMapping("/")
	String sessionId(WebSession session) {
		String id = session.getId();
		session.getAttributes().put("id",id);
		return id;
	}

}