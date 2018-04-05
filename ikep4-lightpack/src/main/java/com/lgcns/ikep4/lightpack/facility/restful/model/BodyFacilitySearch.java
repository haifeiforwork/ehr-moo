package com.lgcns.ikep4.lightpack.facility.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BodyFacilitySearch {
	@XmlElement(name="FacilityList")
	private List<DataFacilityDetail> facilityList;
	
	@XmlElement(name="TotalListInfo")
	private DataTotalListInfo totalListInfo;
	
	public BodyFacilitySearch() {
		super();
		
	}
	
	public BodyFacilitySearch(List<DataFacilityDetail> facilityList, DataTotalListInfo totalListInfo) {
		super();
		
		this.facilityList = facilityList;
		this.totalListInfo = totalListInfo;
	}
	
}