package com.terry.economicsimulator.stubs.db.models;

import com.terry.economicsimulator.db.models.IResource;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResourceRecordStub implements IResource {
	Short resourceId;
	String resourceName;
	Short resourceType;
	Short yearAvailable;
	Integer medianPrice;
	Integer deliveryTimeSensitivity;
	Boolean metraCargo;
}
