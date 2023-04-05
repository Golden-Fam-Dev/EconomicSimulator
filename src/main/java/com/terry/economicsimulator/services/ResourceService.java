package com.terry.economicsimulator.services;

import com.terry.economicsimulator.db.models.IResource;
import com.terry.economicsimulator.db.repo.IResourceRepo;
import com.terry.economicsimulator.mappers.ResourceMapper;
import com.terry.economicsimulator.mappers.helper.RecordMappingHelper;
import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResourceService implements IResourceService {
	private final IResourceRepo resourceRepo;

	public ResourceService(IResourceRepo resourceRepo) {this.resourceRepo = resourceRepo;}

	@Override
	public Map<ResourceFilter, List<Resource>> getResourceByFilters(Set<ResourceFilter> filters) {
		return filters.parallelStream()
				.collect(Collectors.toMap(
						filter -> filter,
						filter -> resourceRepo.findByFilter(filter).stream()
								.map(ResourceMapper.MAPPER::resourceRecordToResource)
								.toList()
				));
	}

	@Override
	public Map<Short, Optional<Resource>> getResourceByResourceIds(Set<Short> resourceIds) {
		Map<Short, List<IResource>> auditEventRecordsMap = resourceRepo.findByResourceIds(resourceIds);
		return RecordMappingHelper.mapRecordsToOptional(resourceIds, auditEventRecordsMap, ResourceMapper.MAPPER::resourceRecordToResource);
	}
}
