package com.bigdata.boot.chapter22.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

@Entity
public class Hotel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "hotel_generator", sequenceName = "hotel_sequence", initialValue = 28)
	@GeneratedValue(generator = "hotel_generator")
	private Long id;

	@ManyToOne(optional = false)
	@NaturalId
	private City city;

	@Column(nullable = false)
	@NaturalId
	private String name;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String zip;

	protected Hotel() {
	}

	public Hotel(City city, String name) {
		this.city = city;
		this.name = name;
	}

	public City getCity() {
		return this.city;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getZip() {
		return this.zip;
	}

}