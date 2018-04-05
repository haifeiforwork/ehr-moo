package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="facilities")
public class PlannerDataFacilities {

	private String facilitiesId;
	private String facilitiesName;
	
	public PlannerDataFacilities(String facilitiesId, String facilitiesName) {
		super();
		this.facilitiesId = facilitiesId;
		this.facilitiesName = facilitiesName;
	}

	public String getFacilitiesId() {
		return facilitiesId;
	}
	
	public String getFacilitiesName() {
		return facilitiesName;
	}


	
	public void setFacilitiesId(String facilitiesId) {
		this.facilitiesId = facilitiesId;
	}

	public void setFacilitiesName(String facilitiesName) {
		this.facilitiesName = facilitiesName;
	}

}