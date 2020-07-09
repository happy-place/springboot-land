package com.bigdata.boot.chapter65.web;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@GetMapping("/")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping("/async")
	public Callable<String> helloWorldAsync() {
	    // 返回的是一个函数，在实际返回时被执行
		return () -> "async: Hello World";
	}

}