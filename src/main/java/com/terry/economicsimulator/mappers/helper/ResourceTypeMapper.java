package com.terry.economicsimulator.mappers.helper;

import com.terry.economicsimulator.models.ResourceType;

public interface ResourceTypeMapper {
	default ResourceType mapResourceType(Short resourceTypeId) {
		return ResourceType.getByResourceType(resourceTypeId);
	}

	default Short map(ResourceType resourceType) { return resourceType.getResourceTypeId(); }
}
