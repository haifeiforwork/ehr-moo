package com.lgcns.ikep4.lightpack.planner.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyWorkGroupList {
	@XmlElement(name="WorkGroupList")
	private List<PlannerDataWorkGroup> workGroupList;
	
	public PlannerBodyWorkGroupList() {
		this.workGroupList = new ArrayList<PlannerDataWorkGroup>();
	}

	public PlannerBodyWorkGroupList(List<PlannerDataWorkGroup> workGroupList) {
		super();
		this.workGroupList = workGroupList;
	}
	
	public void addWorkGroup(PlannerDataWorkGroup workGroup) {
		this.workGroupList.add(workGroup);
	}
}