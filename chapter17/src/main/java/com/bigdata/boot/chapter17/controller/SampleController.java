package com.bigdata.boot.chapter17.controller;

import com.bigdata.boot.chapter17.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@Autowired
	private CityService cityService;

	@GetMapping("/")
	@ResponseBody
	public String helloWorld() {
		return this.cityService.getCity("Bath", "UK").getName();
	}

}