package com.bigdata.boot.chapter50.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

	@Secured("ROLE_USER") // 需要 USER角色
	public String secure() {
		return "Hello Security";
	}

	@PreAuthorize("true") // 允许调用
	public String authorized() {
		return "Hello World";
	}

	@PreAuthorize("false") // 不允许调用
	public String denied() {
		return "Goodbye World";
	}

}