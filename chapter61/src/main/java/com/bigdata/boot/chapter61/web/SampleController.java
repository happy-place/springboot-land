package com.bigdata.boot.chapter61.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@GetMapping("/")
	public String helloWorld() {
		return "Hello, world";
	}

}