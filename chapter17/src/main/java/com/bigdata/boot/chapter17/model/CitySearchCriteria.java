package com.bigdata.boot.chapter17.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CitySearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public CitySearchCriteria() {
	}

	public CitySearchCriteria(String name) {
		Assert.notNull(name, "Name must not be null");
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}