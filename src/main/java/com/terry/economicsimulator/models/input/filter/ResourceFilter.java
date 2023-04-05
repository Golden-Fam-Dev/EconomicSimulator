package com.terry.economicsimulator.models.input.filter;

import com.terry.economicsimulator.models.input.expression.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ResourceFilter implements Filter {
	IDExpression resourceId;
	StringExpression resourceName;
	ResourceTypeExpression resourceType;
	ShortExpression yearAvailable;
	IntExpression medianPrice;
	IntExpression deliveryTimeSensitivity;
	BooleanExpression metraCargo;
}
