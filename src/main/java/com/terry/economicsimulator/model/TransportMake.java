package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TransportMake {
	TRAIN(0),
	SHIP(1),
	TRUCK(2),
	PLANE(3);

	TransportMake(Integer transportMake) {
		this.transportMakeId = transportMake;
	}

	private static final Map<Integer, TransportMake> TRANSPORT_MAKE_BY_ID = Stream.of(
					TransportMake.values())
			.collect(Collectors.toMap(TransportMake::getTransportMakeId, Function.identity()));

	@Getter
	private final Integer transportMakeId;

	public static TransportMake getByTransportMake(Integer transportMake) {
		return TRANSPORT_MAKE_BY_ID.get(transportMake);
	}
}
