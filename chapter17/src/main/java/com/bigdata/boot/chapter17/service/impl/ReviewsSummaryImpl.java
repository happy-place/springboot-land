package com.bigdata.boot.chapter17.service.impl;

import com.bigdata.boot.chapter17.model.RatingCount;
import com.bigdata.boot.chapter17.model.enums.Rating;
import com.bigdata.boot.chapter17.service.ReviewsSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewsSummaryImpl implements ReviewsSummary {

		private final Map<Rating, Long> ratingCount;

		ReviewsSummaryImpl(List<RatingCount> ratingCounts) {
			this.ratingCount = new HashMap<>();
			for (RatingCount ratingCount : ratingCounts) {
				this.ratingCount.put(ratingCount.getRating(), ratingCount.getCount());
			}
		}

		@Override
		public long getNumberOfReviewsWithRating(Rating rating) {
			Long count = this.ratingCount.get(rating);
			return (count != null) ? count : 0;
		}

	}