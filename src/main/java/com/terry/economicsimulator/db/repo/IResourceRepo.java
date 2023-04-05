package com.terry.economicsimulator.db.repo;

import com.terry.economicsimulator.db.models.IResource;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IResourceRepo {
	Map<Short, List<IResource>> findByResourceIds(Set<Short> resourceIds);
	List<IResource> findByFilter(ResourceFilter filter);
	List<IResource> findAll();
}
