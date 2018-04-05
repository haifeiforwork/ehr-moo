package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="workGroup")
public class PlannerDataWorkGroup {

	private String groupId;
	private String groupName;
	private String groupType;
	
	public PlannerDataWorkGroup(String groupId, String groupName, String groupType) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupType = groupType;
	}

	public String getGroupId() {
		return groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public String getGroupType() {
		return groupType;
	}

	

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}