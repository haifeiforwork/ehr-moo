package com.lgcns.ikep4.lightpack.facility.restful.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BodyFacilityList {
	@XmlElement(name="FacilityList")
	private List<DataFacility> facilityList;
	
	@XmlElement(name="FacilityCategoryList")
	private List<DataFacilityCategory> facilityCategoryList;
	
	@XmlElement(name="ParentFacilityCategoryList")
	private List<DataFacilityCategoryWithLevel> parentFacilityCategoryList;
	
	@XmlElement(name="CurrentFacilityCategoryInfo")
	private DataFacilityCategory currentFacilityCategoryInfo;
	
	public BodyFacilityList() {
		super();
		
		this.facilityList = new ArrayList<DataFacility>();
		this.facilityCategoryList = new ArrayList<DataFacilityCategory>();
		this.parentFacilityCategoryList = new ArrayList<DataFacilityCategoryWithLevel>();
	}
	
	public BodyFacilityList(List<DataFacility> facilityList, List<DataFacilityCategory> facilityCategoryList,
			List<DataFacilityCategoryWithLevel> parentFacilityCategoryList, DataFacilityCategory currentFacilityCategoryInfo) {
		super();
		
		this.facilityList = facilityList;
		this.facilityCategoryList = facilityCategoryList;
		this.parentFacilityCategoryList = parentFacilityCategoryList;
		this.currentFacilityCategoryInfo = currentFacilityCategoryInfo;
	}
	
	public void addFacilityList(DataFacility facility) {
		this.facilityList.add(facility);
	}
	
	public void addFacilityCategoryList(DataFacilityCategory category) {
		this.facilityCategoryList.add(category);
	}
	
	public void addParentFacilityCategoryList(DataFacilityCategoryWithLevel category) {
		this.parentFacilityCategoryList.add(category);
	}
}