package com.bigdata.boot.chapter53.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import javax.servlet.http.HttpSession;


@RestController
public class HelloRestController {


	@RequestMapping("/hello/{username}")
	public String hello(HttpSession session, @PathVariable(value = "username") String username) {
		session.setAttribute("username", username);
		return "保存session到Redis成功";
	}

	@RequestMapping("/getName")
	public String getUsername(HttpSession session) {
		String username = (String) session.getAttribute("username");
		return username;
	}

}