package com.lgcns.ikep4.lightpack.planner.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyEntrustUserList {
	@XmlElement(name="EntrustUserList")
	private List<PlannerDataEntrustUser> entrustUserList;
	
	public PlannerBodyEntrustUserList() {
		this.entrustUserList = new ArrayList<PlannerDataEntrustUser>();
	}

	public PlannerBodyEntrustUserList(List<PlannerDataEntrustUser> entrustUserList) {
		super();
		this.entrustUserList = entrustUserList;
	}
	
	public void addEntrustUser(PlannerDataEntrustUser entrustUser) {
		this.entrustUserList.add(entrustUser);
	}
}