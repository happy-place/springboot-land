package com.bigdata.boot.chapter16.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;

@Data
public class Customer {

	@Id
	private Long id;

	private String firstName;

	private LocalDate dateOfBirth;

}