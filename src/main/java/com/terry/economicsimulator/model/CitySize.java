package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CitySize {
	SMALL(0),
	MEDIUM(1),
	LARGE(2);

	CitySize(Integer citySizeId) {
		this.citySizeId = citySizeId;
	}

	private static final Map<Integer, CitySize> CITY_SIZE_BY_ID = Stream.of(
					CitySize.values())
			.collect(Collectors.toMap(CitySize::getCitySizeId, Function.identity()));

	@Getter
	private final Integer citySizeId;

	public static CitySize getByCitySize(Integer citySizeId) {
		return CITY_SIZE_BY_ID.get(citySizeId);
	}
}
