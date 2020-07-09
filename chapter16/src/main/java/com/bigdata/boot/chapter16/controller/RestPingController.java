package com.bigdata.boot.chapter16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest-ping")
public class RestPingController {

	@GetMapping
	public Map<String,Object> ping() {
		Map<String,Object> result = new HashMap<>();
		result.put("pong",System.currentTimeMillis());
		return result;
	}

}