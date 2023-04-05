package com.terry.economicsimulator.db.models;

public interface IResource {
	Short getResourceId();
	String getResourceName();
	Short getResourceType();
	Short getYearAvailable();
	Integer getMedianPrice();
	Integer getDeliveryTimeSensitivity();
	Boolean getMetraCargo();
}
