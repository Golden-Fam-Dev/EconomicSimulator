package com.terry.economicsimulator.services;

import com.terry.economicsimulator.db.models.IResource;
import com.terry.economicsimulator.db.repo.IResourceRepo;
import com.terry.economicsimulator.mappers.ResourceMapper;
import com.terry.economicsimulator.models.Resource;
import com.terry.economicsimulator.models.input.expression.IDExpression;
import com.terry.economicsimulator.models.input.expression.StringExpression;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import com.terry.economicsimulator.stubs.db.repo.ResourceRepoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceServiceTest {
	private IResourceRepo resourceRepo;
	private ResourceService resourceService;

	@BeforeEach
	void setup() {
		resourceRepo = new ResourceRepoStub();
		resourceService = new ResourceService(resourceRepo);
	}

	// Helper functions
	private List<Resource> mapResources(IResourceRepo resourceRepo, Predicate<IResource> resourceRecordFilter) {
		return resourceRepo.findAll().stream()
				.filter(resourceRecordFilter)
				.map(ResourceMapper.MAPPER::resourceRecordToResource)
				.collect(Collectors.toList());
	}

	@Nested
	@DisplayName("Tests for getResources function on resourceId")
	class GetResourceOnResourceIdTests {
		@Test
		@DisplayName("nonMatching resource ids -> All resource ids map to empty list")
		void nonMatchingResourceIds() {
			Set<Short> resourceIds = Set.of((short) 999, (short) 9999);

			Map<Short, Optional<Resource>> resourceMap = resourceService.getResourceByResourceIds(resourceIds);

			assertNotNull(resourceMap);
			assertEquals(2, resourceMap.size());
			assertThat(resourceMap.values(), everyItem(is(Optional.empty())));
		}

		@Test
		@DisplayName("Matching and non-matching resource Ids -> Ids return lists with events if available")
		void matchingResourceIds() {
			Set<Short> resourceIds = Set.of((short) 1, (short) 2, (short) 999);

			Map<Short, Optional<Resource>> resourceMap = resourceService.getResourceByResourceIds(resourceIds);

			assertNotNull(resourceMap);
			List<IResource> knownResources = resourceRepo.findAll();
			Optional<Resource> resource0 = knownResources.stream()
					.filter(a -> Objects.equals(a.getResourceId(), (short) 1))
					.findFirst()
					.map(ResourceMapper.MAPPER::resourceRecordToResource);
			assertEquals(3, resourceMap.size());
			assertEquals(resource0, resourceMap.get((short) 1));
			Optional<Resource> resource1 = knownResources.stream()
					.filter(a -> Objects.equals(a.getResourceId(), (short) 2))
					.findFirst()
					.map(ResourceMapper.MAPPER::resourceRecordToResource);
			assertEquals(resource1, resourceMap.get((short) 2));

			Optional<Resource> resource999 = resourceMap.get((short) 999);
			assertNotNull(resource999);
			assertTrue(resource999.isEmpty());
		}
	}

	@Nested
	@DisplayName("Tests for getResources function on filters")
	class GetResourceOnFiltersTests {
		@Test
		void noFiltersTest() {
			Set<Short> resourceIds = Set.of((short) 999, (short) 9999);

			Map<ResourceFilter, List<Resource>> result = resourceService.getResourceByFilters(Set.of());

			assertNotNull(result);
			assertTrue(result.isEmpty());
		}

		@Test
		@DisplayName("Matching and non-matching resource Ids -> Ids return lists with events if available")
		void nonMatchingSingleFilterTest() {
			ResourceFilter filter = ResourceFilter.builder().build();
			Map<ResourceFilter, List<Resource>> resourceMap = resourceService.getResourceByFilters(Set.of(filter));

			assertNotNull(resourceMap);
			List<Resource> result = resourceMap.get(filter);
			assertNotNull(result);

			List<IResource> knownResources = resourceRepo.findAll();
			assertEquals(knownResources.size(), result.size());
			assertEquals(knownResources.stream().map(ResourceMapper.MAPPER::resourceRecordToResource).collect(Collectors.toList()), result);
		}

		@Test
		void matchingFiltersTest() {
			ResourceFilter idFilter = ResourceFilter.builder().resourceId(IDExpression.builder().eq("1").build()).build();
			ResourceFilter resourceNameFilter = ResourceFilter.builder().resourceName(StringExpression.builder().eq("Dirt").build()).build();
			Map<ResourceFilter, List<Resource>> resourceMap = resourceService.getResourceByFilters(Set.of(idFilter, resourceNameFilter));

			assertNotNull(resourceMap);
			List<Resource> resourcesById = resourceMap.get(idFilter);
			assertNotNull(resourcesById);
			List<Resource> expectedResourcesById = mapResources(resourceRepo, (lr -> Objects.equals(lr.getResourceId(), (short) 1)));
			assertEquals(expectedResourcesById, resourcesById);

			List<Resource> resourcesByName = resourceMap.get(resourceNameFilter);
			assertNotNull(resourcesByName);
			List<Resource> expectedResourcesByName = mapResources(resourceRepo, (lr -> lr.getResourceName().equals("Dirt")));
			assertEquals(expectedResourcesByName, resourcesByName);
		}

		@Test
		void matchingAndNonMatchingFiltersTest() {
			ResourceFilter idFilter = ResourceFilter.builder().resourceId(IDExpression.builder().eq("1").build()).build();
			ResourceFilter resourceNameFilter = ResourceFilter.builder().resourceName(StringExpression.builder().eq("NoMatchOwner").build()).build();
			Map<ResourceFilter, List<Resource>> resourceMap = resourceService.getResourceByFilters(Set.of(idFilter, resourceNameFilter));

			assertNotNull(resourceMap);
			List<Resource> resourcesById = resourceMap.get(idFilter);
			assertNotNull(resourcesById);
			List<Resource> expectedResourcesById = mapResources(resourceRepo, (lr -> Objects.equals(lr.getResourceId(), (short) 1)));
			assertEquals(expectedResourcesById, resourcesById);

			List<Resource> resourcesByOwner = resourceMap.get(resourceNameFilter);
			assertNotNull(resourcesByOwner);
			assertTrue(resourcesByOwner.isEmpty());
		}
	}
}
