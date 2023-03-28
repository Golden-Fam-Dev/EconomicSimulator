package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Grade {
	GRADE_R(0, 0),
	GRADE_A(1, 2),
	GRADE_D(2, 4),
	GRADE_E(3, 6);

	Grade(Integer gradeId, Integer gradePercentage) {
		this.gradeId = gradeId;
		this.gradePercentage = gradePercentage;
	}

	private static final Map<Integer, Grade> GRADE_BY_ID = Stream.of(
					Grade.values())
			.collect(Collectors.toMap(Grade::getGradeId, Function.identity()));

	@Getter
	private final Integer gradeId;
	@Getter
	private final Integer gradePercentage;

	public static Grade getByGrade(Integer gradeId) {
		return GRADE_BY_ID.get(gradeId);
	}
}
