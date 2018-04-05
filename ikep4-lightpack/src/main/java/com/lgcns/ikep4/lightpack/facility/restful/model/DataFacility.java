package com.lgcns.ikep4.lightpack.facility.restful.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="facility")
public class DataFacility {

	protected String facilityId;
	protected String facilityName;
	
	public DataFacility(String facilityId, String facilityName) {
		
		super();
		
		this.facilityId = facilityId;
		this.facilityName = facilityName;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}
	


	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
}