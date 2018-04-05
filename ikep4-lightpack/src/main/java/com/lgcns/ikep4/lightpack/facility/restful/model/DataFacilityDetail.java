package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="facilityDetail")
public class DataFacilityDetail extends DataFacility {

	protected String categoryId;
	protected String categoryName;
	
	public DataFacilityDetail(String facilityId, String facilityName, String categoryId, String categoryName) {
		
		super(facilityId, facilityName);
		
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}