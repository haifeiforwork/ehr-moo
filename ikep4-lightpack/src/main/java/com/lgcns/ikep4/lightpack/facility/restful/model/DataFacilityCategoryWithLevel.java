package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="facilityCategoryWithLevel")
public class DataFacilityCategoryWithLevel extends DataFacilityCategory {

	private String level;
	
	public DataFacilityCategoryWithLevel(String categoryId, String categoryName, String level) {
		
		super(categoryId, categoryName);
		
		this.level = level;
	}

	public String getLevel() {
		return level;
	}
	


	public void setLevel(String level) {
		this.level = level;
	}
}