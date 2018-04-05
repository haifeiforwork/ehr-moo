package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="unfixedTime")
public class PlannerDataUnfixedTime {
	
	private String itemStartDateTime;
	private String itemEndDateTime;

	
	public PlannerDataUnfixedTime(String itemStartDateTime, String itemEndDateTime) {
		super();
		this.itemStartDateTime = itemStartDateTime;
		this.itemEndDateTime = itemEndDateTime;
	}

	public String getItemStartDateTime() {
		return itemStartDateTime;
	}
	
	public String getItemEndDateTime() {
		return itemEndDateTime;
	}


	
	public void setItemStartDateTime(String itemStartDateTime) {
		this.itemStartDateTime = itemStartDateTime;
	}

	public void setItemEndDateTime(String itemEndDateTime) {
		this.itemEndDateTime = itemEndDateTime;
	}

}