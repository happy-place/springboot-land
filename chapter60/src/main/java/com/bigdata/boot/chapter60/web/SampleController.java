package com.bigdata.boot.chapter60.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@GetMapping("/hello")
	public String helloWorld() {
		return "hello";
	}

}