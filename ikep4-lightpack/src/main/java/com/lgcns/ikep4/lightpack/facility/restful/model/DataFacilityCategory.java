package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="facilityCategory")
public class DataFacilityCategory {

	protected String categoryId;
	protected String categoryName;
	
	public DataFacilityCategory(String categoryId, String categoryName) {
		
		super();
		
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