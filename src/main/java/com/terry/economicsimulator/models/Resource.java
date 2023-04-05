package com.terry.economicsimulator.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Resource {
	Short resourceId;
	String resourceName;
	ResourceType resourceType;
	Short yearAvailable;
	Integer medianPrice;
	Integer deliveryTimeSensitivity;
	Boolean metraCargo;
}
