package com.terry.economicsimulator.services;

import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IResourceService {
	Map<ResourceFilter, List<Resource>> getResourceByFilters(Set<ResourceFilter> filters);
	Map<Short, Optional<Resource>> getResourceByResourceIds(Set<Short> resourceIds);
}
