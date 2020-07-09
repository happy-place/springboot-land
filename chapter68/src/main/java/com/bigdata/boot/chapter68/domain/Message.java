package com.bigdata.boot.chapter68.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

public class Message {

	private Long id;

	// @NotEmpty用在集合类上面 @NotBlank 用在String上面 @NotNull 用在基本类型上
	@NotEmpty(message = "Text is required.")
	private String text;

	@NotEmpty(message = "Summary is required.")
	private String summary;

	private Date created = new Date();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}