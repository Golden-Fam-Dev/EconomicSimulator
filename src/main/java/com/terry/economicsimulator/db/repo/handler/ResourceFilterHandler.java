package com.terry.economicsimulator.db.repo.handler;

import com.terry.economicsimulator.db.repo.expression.*;
import com.terry.economicsimulator.models.input.filter.Filter;
import com.terry.economicsimulator.models.input.filter.ResourceFilter;
import org.jooq.Condition;
import org.springframework.stereotype.Component;

import static com.terry.economicsimulator.db.generated.economicsimulator.Tables.RESOURCE;
import static org.jooq.impl.DSL.noCondition;

@Component
public class ResourceFilterHandler implements IFilterHandler<ResourceFilter> {

	@Override
	public Class<? extends Filter> supportedFilter() {
		return ResourceFilter.class;
	}

	public Condition handleFilter(ResourceFilter filter) {
		Condition condition = noCondition();
		if (filter == null) {
			return condition;
		}

		condition = ShortIDExpressionHandler.handleExpression(filter.getResourceId(), condition, RESOURCE.RESOURCE_ID);
		condition = StringExpressionHandler.handleExpression(filter.getResourceName(), condition, RESOURCE.RESOURCE_NAME);
		condition = ResourceTypeExpressionHandler.handleExpression(filter.getResourceType(), condition, RESOURCE.RESOURCE_TYPE);
		condition = ShortExpressionHandler.handleExpression(filter.getYearAvailable(), condition, RESOURCE.YEAR_AVAILABLE);
		condition = IntExpressionHandler.handleExpression(filter.getMedianPrice(), condition, RESOURCE.MEDIAN_PRICE);
		condition = IntExpressionHandler.handleExpression(filter.getDeliveryTimeSensitivity(), condition, RESOURCE.DELIVERY_TIME_SENSITIVITY);
		condition = BooleanExpressionHandler.handleExpression(filter.getMetraCargo(), condition, RESOURCE.METRA_CARGO);

		return condition;
	}

}
