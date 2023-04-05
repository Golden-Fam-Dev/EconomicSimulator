package com.terry.economicsimulator.graphql.dataloaders;

import com.terry.economicsimulator.graphql.dto.GraphQLModelWithFilter;
import org.dataloader.MappedBatchLoader;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractFilteredMappedDataLoader<I, O> implements MappedBatchLoader<GraphQLModelWithFilter<I>, O> {

	private final Executor executor;

	AbstractFilteredMappedDataLoader(Executor executor) {
		this.executor = executor;
	}

	public CompletionStage<Map<GraphQLModelWithFilter<I>, O>> load(Set<GraphQLModelWithFilter<I>> inputModels) {
		return CompletableFuture.supplyAsync(() -> {
			Assert.notNull(inputModels, "inputModels must not be null");
			// From the given input 'inputModels', create a map where each unique filter set
			// is the key and a list of associated models is the value.  This is needed so we can
			// call the getData function for each map entry (unique filter set)
			Map<Map<String, Object>, Set<I>> filterAndModelsOnFilter = inputModels
					.stream()
					.collect(Collectors.toMap(
							GraphQLModelWithFilter::getFilters,
							m -> Collections.singleton(m.getModel()),
							(m1, m2) -> Stream.concat(m1.stream(), m2.stream()).collect(Collectors.toSet())
					));


			// Get the data associated with each filter + input list pair and add the response data
			// to a map of GraphQLModelWithFilter as the key and the associated data as the value
			// Map each inputModel to its associated data result.  Use the defaultDataValue if no
			// associated data is found.

			return filterAndModelsOnFilter.entrySet()
					.stream()
					.map(entry -> getDataByGraphQLModelWithFilter(entry.getValue(), entry.getKey()))
					.flatMap(map -> map.entrySet().stream())
					.collect(HashMap::new,
							(newMap, dataFromStream) -> newMap.put(dataFromStream.getKey(), dataFromStream.getValue()),
							HashMap::putAll);
		}, executor);
	}

	private Map<GraphQLModelWithFilter<I>, O> getDataByGraphQLModelWithFilter(Set<I> models, Map<String, Object> filters) {
		Map<I, O> calcedData = getData(models, filters);

		return models.stream()
				.map(model ->
						GraphQLModelWithFilter.<I>builder()
								.model(model)
								.filters(filters)
								.build()
				)
				.collect(
						HashMap::new,
						(newMap, dataFromStream) -> newMap.put(dataFromStream, calcedData.get(dataFromStream.getModel()) == null ? defaultDataValue() : calcedData.get(dataFromStream.getModel())),
						HashMap::putAll
				);
	}


	/**
	 * Helper function get a filter by the specified filterKey
	 * from the filters map
	 *
	 * @param filters   Map of filters to get filter from
	 * @param filterKey filterKey to find filter by
	 * @param <T>       Generic response type
	 * @return The filter found by the specified filterKey, if exists; null otherwise
	 */
	public <T> T getFilter(Map<String, T> filters, String filterKey) {
		if (filters == null || filters.isEmpty()) return null;
		return filters.get(filterKey);
	}

	/**
	 * @param models  list of models extracted from the input GraphQLModelWithFilter list
	 * @param filters map of filters extracted from the input GraphQLModelWithFilter list
	 * @return Data returned as a map of input key to associated output data
	 */
	public abstract Map<I, O> getData(Set<I> models, Map<String, Object> filters);

	/**
	 * Returns the default data value <O> to utilize when no matching value is found
	 * in the getData function for a specific key <I>.
	 *
	 * @return Default data value <O>
	 */
	public abstract O defaultDataValue();
}
