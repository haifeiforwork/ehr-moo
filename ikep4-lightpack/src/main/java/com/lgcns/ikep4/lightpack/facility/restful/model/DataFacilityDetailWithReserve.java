package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="facilityDetail")
public class DataFacilityDetailWithReserve extends DataFacilityDetail {

	private String reserveDate;
	
	public DataFacilityDetailWithReserve(String facilityId, String facilityName, String categoryId, String categoryName,
			String reserveDate) {
		
		super(facilityId, facilityName, categoryId, categoryName);
		
		this.reserveDate = reserveDate;
	}
	
	public String getReserveDate() {
		return reserveDate;
	}
	


	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
}