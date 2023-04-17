package com.terry.economicsimulator.graphql.datafetchers;

import com.terry.economicsimulator.graphql.datafetchers.internal.ResourceDataFetcherInternal;
import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Controller
public class ResourceDataFetcher {
	private final ResourceDataFetcherInternal resourceDataFetcherInternal;
	private final DataFetcherHelper dataFetcherHelper;

	public ResourceDataFetcher(ResourceDataFetcherInternal resourceDataFetcherInternal, DataFetcherHelper dataFetcherHelper) {
		this.resourceDataFetcherInternal = resourceDataFetcherInternal;
		this.dataFetcherHelper = dataFetcherHelper;
	}

	@QueryMapping
	public CompletableFuture<DataFetcherResult<List<Resource>>> resources(@Argument("filter") Optional<ResourceFilter> filter, DataFetchingEnvironment dfe) {
		return dataFetcherHelper.handle(
				() -> resourceDataFetcherInternal.resourceQuery(filter.orElse(ResourceFilter.builder().build()), dfe),
				dfe,
				List.of()
		);
	}
}
