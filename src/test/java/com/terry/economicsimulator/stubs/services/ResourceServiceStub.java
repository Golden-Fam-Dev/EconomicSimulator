package com.terry.economicsimulator.stubs.services;

import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.ResourceType;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import com.terry.economicsimulator.services.IResourceService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.terry.economicsimulator.stubs.ExpressionEvaluator.*;

public class ResourceServiceStub implements IResourceService {
	public static final List<Resource> RESOURCES = List.of(
			Resource.builder()
					.resourceId((short) 0)
					.resourceName("Apples")
					.resourceType(ResourceType.FREIGHT)
					.yearAvailable((short) 1999)
					.medianPrice(100)
					.deliveryTimeSensitivity(10)
					.metraCargo(true)
					.build(),
			Resource.builder()
					.resourceId((short) 1)
					.resourceName("Oranges")
					.resourceType(ResourceType.EXPRESS)
					.yearAvailable((short) 1909)
					.medianPrice(105)
					.deliveryTimeSensitivity(8)
					.metraCargo(false)
					.build()
	);

	public ResourceServiceStub() {
	}

	private static boolean evaluateFilter(Resource resource, ResourceFilter filter) {
		return evaluateShortIDExpression(filter, ResourceFilter::getResourceId, resource.getResourceId())
				& evaluateResourceTypeExpression(filter, ResourceFilter::getResourceType, resource.getResourceType())
				& evaluateStringExpression(filter, ResourceFilter::getResourceName, resource.getResourceName())
				& evaluateShortExpression(filter, ResourceFilter::getYearAvailable, resource.getYearAvailable())
				& evaluateIntExpression(filter, ResourceFilter::getMedianPrice, resource.getMedianPrice())
				& evaluateIntExpression(filter, ResourceFilter::getDeliveryTimeSensitivity, resource.getDeliveryTimeSensitivity())
				& evaluateBooleanExpression(filter, ResourceFilter::getMetraCargo, resource.getMetraCargo());
	}

	@Override
	public Map<ResourceFilter, List<Resource>> getResourceByFilters(Set<ResourceFilter> filters) {
		return filters.stream()
				.collect(Collectors.toMap(
						filter -> filter,
						filter -> RESOURCES.stream()
								.filter(resource -> evaluateFilter(resource, filter))
								.toList()
				));
	}

	@Override
	public Map<Short, Optional<Resource>> getResourceByResourceIds(Set<Short> resourceIds) {
		return resourceIds.stream()
				.collect(Collectors.toMap(
						Function.identity(),
						id -> RESOURCES.stream()
								.filter(resource -> Objects.equals(resource.getResourceId(), id))
								.findFirst()
				));
	}
}
