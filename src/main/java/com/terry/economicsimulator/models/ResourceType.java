package com.terry.economicsimulator.models;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ResourceType {
	FREIGHT((short)0),
	EXPRESS((short)1),
	BOTH((short)2);

	ResourceType(Short resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	private static final Map<Short, ResourceType> RESOURCE_TYPE_BY_ID = Stream.of(
					ResourceType.values())
			.collect(Collectors.toMap(ResourceType::getResourceTypeId, Function.identity()));

	@Getter
	private final Short resourceTypeId;

	public static ResourceType getByResourceType(Short resourceTypeId) {
		return RESOURCE_TYPE_BY_ID.get(resourceTypeId);
	}
}
