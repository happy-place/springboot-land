package com.bigdata.boot.chapter16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/ping")
public class PingController {

	@ResponseBody
	@GetMapping
	public Map<String,Object> ping() {
		Map<String,Object> result = new HashMap<>();
		result.put("pong",System.currentTimeMillis());
		return result;
	}


}