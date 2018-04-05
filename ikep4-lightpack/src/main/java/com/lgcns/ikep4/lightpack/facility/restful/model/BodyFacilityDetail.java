package com.lgcns.ikep4.lightpack.facility.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BodyFacilityDetail {

	@XmlElement(name="FacilityInfo")
	private DataFacilityDetailWithReserve facilityInfo;
	
	@XmlElement(name="ReserveTimeList")
	private List<DataReserveTime> reserveTimeList;
	
	public BodyFacilityDetail() {
		super();
		
	}
	
	public BodyFacilityDetail(DataFacilityDetailWithReserve facilityInfo,  List<DataReserveTime> reserveTimeList) {
		super();
		
		this.facilityInfo = facilityInfo;
		this.reserveTimeList = reserveTimeList;
	}
	
}