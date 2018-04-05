package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="planCategory")
public class PlannerDataPlanCategory {

	private String itemCategoryId;
	private String itemCategoryName;
	private String itemCategoryIcon;
	
	public PlannerDataPlanCategory(String itemCategoryId, String itemCategoryName, String itemCategoryIcon) {
		super();
		
		this.itemCategoryId = itemCategoryId;
		this.itemCategoryName = itemCategoryName;
		this.itemCategoryIcon = itemCategoryIcon;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}
	
	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public String getItemCategoryIcon() {
		return itemCategoryIcon;
	}

	

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public void setItemCategoryIcon(String itemCategoryIcon) {
		this.itemCategoryIcon = itemCategoryIcon;
	}
}