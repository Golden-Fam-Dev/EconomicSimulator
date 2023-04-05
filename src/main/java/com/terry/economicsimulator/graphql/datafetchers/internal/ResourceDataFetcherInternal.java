package com.terry.economicsimulator.graphql.datafetchers.internal;

import com.terry.economicsimulator.exception.EntityNotFoundException;
import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import com.terry.economicsimulator.graphql.dataloaders.ResourceDataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ResourceDataFetcherInternal {
	public CompletableFuture<List<Resource>> resourceQuery(ResourceFilter filter, DataFetchingEnvironment dfe) {
		DataLoader<ResourceFilter, List<Resource>> dataLoader = dfe.getDataLoader(ResourceDataLoader.NAME);

		return dataLoader.load(filter)
				.thenApply(res -> {
					if (res.isEmpty()) {
						throw EntityNotFoundException.entityNotFound("Resource not present for given filter");
					}
					return res;
				});
	}
}
