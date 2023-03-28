package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Fuel {
	DIESEL(0, 6),
	STEAM(1, 2),
	GASOLINE(2, 4),
	HYDROGEN(3, 3),
	NUCLEAR(4, 10),
	ELECTRIC(5, 2);

	Fuel(Integer fuelId, Integer cost) {
		this.fuelId = fuelId;
		this.cost = cost;
	}

	private static final Map<Integer, Fuel> FUEL_BY_ID = Stream.of(
					Fuel.values())
			.collect(Collectors.toMap(Fuel::getFuelId, Function.identity()));

	@Getter
	private final Integer fuelId;
	@Getter
	private final Integer cost;

	public static Fuel getByFuel(Integer fuelId) {
		return FUEL_BY_ID.get(fuelId);
	}
}