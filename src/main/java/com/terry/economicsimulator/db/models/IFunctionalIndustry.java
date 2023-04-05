package com.terry.economicsimulator.db.models;

public interface IFunctionalIndustry {
	Short getIndustryId();
	String getIndustryName();
	Short getIntroductionYear();
	Integer getIndustryCost();
	Integer getOverhead();
	Short getMinimumCitySize();
	Integer getRelativeProfitability();
}
