package com.terry.economicsimulator.graphql.datafetchers;

import com.terry.economicsimulator.graphql.datafetchers.internal.ResourceDataFetcherInternal;
import com.terry.economicsimulator.graphql.dataloaders.ResourceDataLoader;
import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.expression.IDExpression;
import com.terry.economicsimulator.models.input.expression.StringExpression;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import com.terry.economicsimulator.services.IResourceService;
import com.terry.economicsimulator.stubs.services.ResourceServiceStub;
import graphql.GraphQLError;
import graphql.execution.*;
import graphql.language.Field;
import graphql.schema.*;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceDataFetcherTest {
	private DataFetchingEnvironment dfe;
	private ResourceDataFetcher resourceDataFetcher;
	private DataLoader<ResourceFilter, List<Resource>> resourceDataLoader;

	@BeforeEach
	void setup() {
		DataFetcherHelper dataFetcherHelper = new DataFetcherHelper(new SimpleDataFetcherExceptionHandler());
		ResourceDataFetcherInternal resourceDataFetcherInternal = new ResourceDataFetcherInternal();
		IResourceService resourceService = new ResourceServiceStub();

		resourceDataLoader = DataLoaderFactory.newMappedDataLoader(new ResourceDataLoader(resourceService, ForkJoinPool.commonPool()));

		dfe = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
				.dataLoaderRegistry(new DataLoaderRegistry()
						.register(ResourceDataLoader.NAME, resourceDataLoader))
				.executionStepInfo(ExecutionStepInfo.newExecutionStepInfo()
						.path(ResultPath.parse("/resources"))
						.type(GraphQLNonNull.nonNull(GraphQLList.list(GraphQLNonNull.nonNull(GraphQLObjectType.newObject().name("Resource").build()))))
						.build())
				.mergedField(MergedField.newMergedField()
						.addField(Field.newField()
								.name("resources")
								.build())
						.build())
				.build();

		resourceDataFetcher = new ResourceDataFetcher(resourceDataFetcherInternal, dataFetcherHelper);
	}

	@Nested
	@DisplayName("Tests for resourceFromCalendars")
	class ResourcesFromCalendarTests {
		@Test
		@DisplayName("No resources for given calendar -> CompletableFuture of Empty list returned")
		void noResourcesTest() {
			DataFetchingEnvironment dfeWithSource = DataFetchingEnvironmentImpl.newDataFetchingEnvironment(dfe)
					.source(Resource.builder().build()).build();

			CompletableFuture<DataFetcherResult<List<Resource>>> resourceResultFuture =
					resourceDataFetcher.resourceQuery(Optional.empty(), dfeWithSource);
			resourceDataLoader.dispatchAndJoin();

			assertNotNull(resourceResultFuture);
			DataFetcherResult<List<Resource>> resourceResult = resourceResultFuture.join();

			assertTrue(resourceResult.getErrors().isEmpty());
			List<Resource> resources = resourceResult.getData();

			List<Resource> expectedResources = ResourceServiceStub.RESOURCES;

			assertEquals(expectedResources.size(), resources.size());
			assertEquals(expectedResources, resources);
		}

		@Test
		@DisplayName("Non-matching Resource ID -> Empty resource list returned with error")
		void resourceIdNotExists() {
			ResourceFilter filter = ResourceFilter.builder().resourceId(IDExpression.builder().eq("-1").build()).build();

			CompletableFuture<DataFetcherResult<List<Resource>>> resourcesResultFuture = resourceDataFetcher.resourceQuery(Optional.of(filter), dfe);
			resourceDataLoader.dispatchAndJoin();

			assertNotNull(resourcesResultFuture);
			DataFetcherResult<List<Resource>> resourcesResult = resourcesResultFuture.join();

			List<GraphQLError> errors = resourcesResult.getErrors();
			assertEquals(1, errors.size());
			GraphQLError graphQLError = errors.get(0);
			assertEquals("resources", graphQLError.getPath().get(0));
			assertTrue(graphQLError.getMessage().contains("Resource not present for given filter"));

			List<Resource> resources = resourcesResult.getData();
			assertTrue(resources.isEmpty());
		}

		@Test
		@DisplayName("Resource with filter -> All resources returned which match filter")
		void resourceFilterTest() {
			ResourceFilter resourceFilter = ResourceFilter.builder()
					.resourceName(StringExpression.builder().eq("Apples").build())
					.build();

			CompletableFuture<DataFetcherResult<List<Resource>>> resourcesResultFuture = resourceDataFetcher.resourceQuery(Optional.of(resourceFilter), dfe);
			resourceDataLoader.dispatchAndJoin();

			assertNotNull(resourcesResultFuture);
			DataFetcherResult<List<Resource>> resourcesResult = resourcesResultFuture.join();

			assertTrue(resourcesResult.getErrors().isEmpty());

			List<Resource> resources = resourcesResult.getData();
			assertThat(resources, everyItem(hasProperty("resourceName", is("Apples"))));
			assertEquals(1, resources.size());
		}

		@Test
		@DisplayName("Multiple filter matches -> All resources returned which match filter")
		void multipleFilterMatches() {
			ResourceFilter resourceFilter = ResourceFilter.builder()
					.resourceId(IDExpression.builder().in(List.of("0", "1")).build())
					.build();

			CompletableFuture<DataFetcherResult<List<Resource>>> resourcesResultFuture = resourceDataFetcher.resourceQuery(Optional.of(resourceFilter), dfe);
			resourceDataLoader.dispatchAndJoin();

			assertNotNull(resourcesResultFuture);
			DataFetcherResult<List<Resource>> resourcesResult = resourcesResultFuture.join();

			assertTrue(resourcesResult.getErrors().isEmpty());

			List<Resource> resources = resourcesResult.getData();
			assertThat(resources, everyItem(hasProperty("resourceId", not((short) 2))));
			assertThat(resources, hasItem(hasProperty("resourceId", is((short) 0))));
			assertThat(resources, hasItem(hasProperty("resourceId", is((short) 1))));
			assertThat(resources, everyItem(hasProperty("resourceId", not((short) 3))));
			assertEquals(2, resources.size());
		}
	}
}


