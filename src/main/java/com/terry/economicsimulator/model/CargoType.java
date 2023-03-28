package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CargoType {
	FREIGHT(0),
	EXPRESS(1),
	MIXED(2);

	CargoType(Integer cargoType) {
		this.cargoTypeId = cargoType;
	}

	private static final Map<Integer, CargoType> CARGO_TYPE_BY_ID = Stream.of(
					CargoType.values())
			.collect(Collectors.toMap(CargoType::getCargoTypeId, Function.identity()));

	@Getter
	private final Integer cargoTypeId;

	public static CargoType getByCargoType(Integer cargoType) {
		return CARGO_TYPE_BY_ID.get(cargoType);
	}
}
