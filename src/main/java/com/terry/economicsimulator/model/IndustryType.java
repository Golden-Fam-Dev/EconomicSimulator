package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IndustryType {
	FACTORY(0),
	FARM(1),
	MINE(2),
	SERVICE(3);

	IndustryType(Integer industryType) {
		this.industryTypeId = industryType;
	}

	private static final Map<Integer, IndustryType> INDUSTRY_TYPE_BY_ID = Stream.of(
			IndustryType.values())
			.collect(Collectors.toMap(IndustryType::getIndustryTypeId, Function.identity()));

	@Getter
	private final Integer industryTypeId;

	public static IndustryType getByIndustryType(Integer industryType) {
		return INDUSTRY_TYPE_BY_ID.get(industryType);
	}
}
