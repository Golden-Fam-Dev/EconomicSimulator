package com.terry.economicsimulator.db.models;

public interface IIndustrialIndustry {
	Short getIndustryId();
	Short getIndustryType();
	Integer getRetailPrice();
	Integer getMinPrice();
	Integer getProduction();
	String getRating();
	Short getMinEmployee();
	Short getMaxEmployee();
	Boolean getWaterBased();
}
