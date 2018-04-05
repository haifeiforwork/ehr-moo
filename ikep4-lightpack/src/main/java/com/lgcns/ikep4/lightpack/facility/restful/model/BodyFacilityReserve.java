package com.lgcns.ikep4.lightpack.facility.restful.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

public class BodyFacilityReserve {
	@XmlElement(name="ReserveInfo")
	private Map<String, String> reserveInfo;
	
	public BodyFacilityReserve() {
		super();
		
	}
	
	public BodyFacilityReserve(boolean isReserve, String notReserveFacilityId) {
		super();
		
		reserveInfo = new HashMap<String, String>();
		reserveInfo.put("isReserve", isReserve ? "1" : "0");
		reserveInfo.put("notReserveFacilityId", notReserveFacilityId);
	}
	
}