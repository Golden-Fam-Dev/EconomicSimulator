package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Rating {
	ATROCIOUS(0), //Ugly, Slow
	EXTREMELY_POOR(1),
	VERY_POOR(2),
	POOR(3),
	BELOW_AVERAGE(4),
	AVERAGE(5),
	ABOVE_AVERAGE(6),
	GOOD(7),
	VERY_GOOD(8),
	EXCELLENT(9), //Near Perfect
	OUTSTANDING(10); //Perfect, Super Fast, Sharp



	Rating(Integer ratingId) {
		this.ratingId = ratingId;
	}

	private static final Map<Integer, Rating> RATING_BY_ID = Stream.of(
					Rating.values())
			.collect(Collectors.toMap(Rating::getRatingId, Function.identity()));

	@Getter
	private final Integer ratingId;

	public static Rating getByFuel(Integer ratingId) {
		return RATING_BY_ID.get(ratingId);
	}
}