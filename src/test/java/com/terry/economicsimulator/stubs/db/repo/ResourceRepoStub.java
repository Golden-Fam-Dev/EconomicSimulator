package com.terry.economicsimulator.stubs.db.repo;

import com.terry.economicsimulator.db.models.IResource;
import com.terry.economicsimulator.db.repo.IResourceRepo;
import com.terry.economicsimulator.db.repo.ResourceRepo;
import com.terry.economicsimulator.models.ResourceType;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import com.terry.economicsimulator.stubs.db.models.ResourceRecordStub;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.terry.economicsimulator.stubs.ExpressionEvaluator.*;

public class ResourceRepoStub extends ResourceRepo implements IResourceRepo {
	private static final Short FREIGHT = ResourceType.FREIGHT.getResourceTypeId();
	private static final Short EXPRESS = ResourceType.EXPRESS.getResourceTypeId();
	private static final List<IResource> RESOURCES = List.of(
			// resource 1
			ResourceRecordStub.builder().resourceId((short) 1).resourceName("Dirt").resourceType(FREIGHT).medianPrice(1).deliveryTimeSensitivity(10).metraCargo(true).build(),
			ResourceRecordStub.builder().resourceId((short) 2).resourceName("Oxygen").resourceType(EXPRESS).medianPrice(2).deliveryTimeSensitivity(1).metraCargo(false).build(),
			ResourceRecordStub.builder().resourceId((short) 3).resourceName("Helium").resourceType(FREIGHT).medianPrice(1).deliveryTimeSensitivity(100).metraCargo(true).build(),
			ResourceRecordStub.builder().resourceId((short) 4).resourceName("Cobblestone").resourceType(FREIGHT).medianPrice(3).deliveryTimeSensitivity(10).metraCargo(true).build(),
			ResourceRecordStub.builder().resourceId((short) 10).resourceName("Tires").resourceType(FREIGHT).medianPrice(1).deliveryTimeSensitivity(10).metraCargo(true).build(),
			ResourceRecordStub.builder().resourceId((short) 11).resourceName("Lamp").resourceType(FREIGHT).medianPrice(4).deliveryTimeSensitivity(15).metraCargo(true).build(),
			ResourceRecordStub.builder().resourceId((short) 12).resourceName("Love").resourceType(EXPRESS).medianPrice(5).deliveryTimeSensitivity(1).metraCargo(false).build(),
			ResourceRecordStub.builder().resourceId((short) 14).resourceName("Poo").resourceType(EXPRESS).medianPrice(1).deliveryTimeSensitivity(1).metraCargo(false).build()
	);

	public ResourceRepoStub() {
		super(null, null);
	}

	public List<IResource> findAll() {
		return RESOURCES;
	}

	@Override
	public Map<Short, List<IResource>> findByResourceIds(Set<Short> resourceIds) {
		return RESOURCES.stream()
				.filter(ae -> resourceIds.contains(ae.getResourceId()))
				.collect(Collectors.groupingBy(IResource::getResourceId));
	}

	@Override
	public List<IResource> findByFilter(ResourceFilter filter) {
		return evaluateFilter(filter);
	}

	private List<IResource> evaluateFilter(ResourceFilter filter) {
		return RESOURCES.stream()
				.filter(resource -> evaluateShortIDExpression(filter, ResourceFilter::getResourceId, resource.getResourceId()))
				.filter(resource -> evaluateResourceTypeExpression(filter, ResourceFilter::getResourceType, resource.getResourceType()))
				.filter(resource -> evaluateStringExpression(filter, ResourceFilter::getResourceName, resource.getResourceName()))
				.filter(resource -> evaluateShortExpression(filter, ResourceFilter::getYearAvailable, resource.getYearAvailable()))
				.toList();
	}
}
