package com.bigdata.boot.chapter17.model;

public interface HotelSummary {

	City getCity();

	String getName();

	Double getAverageRating();

	default Integer getAverageRatingRounded() {
		return (getAverageRating() != null) ? (int) Math.round(getAverageRating()) : null;
	}

}