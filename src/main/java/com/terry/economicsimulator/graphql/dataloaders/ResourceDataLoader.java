package com.terry.economicsimulator.graphql.dataloaders;

import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import com.terry.economicsimulator.services.IResourceService;
import org.dataloader.MappedBatchLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@Component
public class ResourceDataLoader implements MappedBatchLoader<ResourceFilter, List<Resource>> {
	public static final String NAME = "resourceDataLoader";

	private final IResourceService resourceService;
	private final Executor commonTaskExecutor;

	public ResourceDataLoader(IResourceService resourceService, Executor commonTaskExecutor) {
		this.resourceService = resourceService;
		this.commonTaskExecutor = commonTaskExecutor;
	}

	@Override
	public CompletionStage<Map<ResourceFilter, List<Resource>>> load(Set<ResourceFilter> filters) {
		return CompletableFuture.supplyAsync(() -> resourceService.getResourceByFilters(filters), commonTaskExecutor);
	}
}
