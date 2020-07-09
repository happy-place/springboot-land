package com.bigdata.boot.chapter57.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNameNotFoundException extends RuntimeException {

	private final String username;

	public UserNameNotFoundException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

}