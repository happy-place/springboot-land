package com.bigdata.boot.chapter59.common.response;

public class MyRestResponse {

	private String message;

	public MyRestResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}