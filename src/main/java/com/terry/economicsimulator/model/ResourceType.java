package com.terry.economicsimulator.model;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ResourceType {
	FREIGHT(0),
	EXPRESS(1),
	BOTH(2);

	ResourceType(Integer resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	private static final Map<Integer, ResourceType> RESOURCE_TYPE_BY_ID = Stream.of(
					ResourceType.values())
			.collect(Collectors.toMap(ResourceType::getResourceTypeId, Function.identity()));

	@Getter
	private final Integer resourceTypeId;

	public static ResourceType getByResourceType(Integer resourceTypeId) {
		return RESOURCE_TYPE_BY_ID.get(resourceTypeId);
	}
}
