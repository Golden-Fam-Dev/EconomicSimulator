package com.terry.economicsimulator.db.repo;

import com.terry.economicsimulator.db.generated.economicsimulator.tables.pojos.Resource;
import com.terry.economicsimulator.db.models.IResource;
import com.terry.economicsimulator.db.repo.handler.FilterHandlerFactory;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.terry.economicsimulator.db.generated.economicsimulator.tables.Resource.RESOURCE;

@Repository
public class ResourceRepo implements IResourceRepo {
	private static final List<Field<?>> ACTIVE_FIELDS = List.of(
			RESOURCE.RESOURCE_ID,
			RESOURCE.RESOURCE_NAME,
			RESOURCE.RESOURCE_TYPE,
			RESOURCE.YEAR_AVAILABLE,
			RESOURCE.MEDIAN_PRICE,
			RESOURCE.DELIVERY_TIME_SENSITIVITY,
			RESOURCE.METRA_CARGO
	);

	private final DSLContext context;
	private final FilterHandlerFactory filterHandlerFactory;

	public ResourceRepo(DSLContext context, FilterHandlerFactory filterHandlerFactory) {
		this.context = context;
		this.filterHandlerFactory = filterHandlerFactory;
	}

	public Map<Short, List<IResource>> findByResourceIds(Set<Short> resourceIds) {
		return context.select(ACTIVE_FIELDS)
				.from(RESOURCE)
				.where(RESOURCE.RESOURCE_ID.in(resourceIds))
				.fetchGroups(RESOURCE.RESOURCE_ID, Resource.class);
	}

	public List<IResource> findByFilter(ResourceFilter filter) {
		return context
				.select(ACTIVE_FIELDS)
				.from(RESOURCE)
				.where(getFilterCondition(filter))
				.fetchInto(Resource.class);
	}

	public List<IResource> findAll() {
		return context
				.select(ACTIVE_FIELDS)
				.from(RESOURCE)
				.fetchInto(Resource.class);
	}

	private Condition getFilterCondition(ResourceFilter filter) {
		return filterHandlerFactory
				.getFilterHandler(filter)
				.handleFilter(filter);
	}
}
