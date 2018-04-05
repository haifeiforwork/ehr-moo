package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData")
public class DeptListReturnData {
	
	private String portalId;
	private String groupId;
	private String groupName;
	private String groupEnglishName;
	private String fullPath;
	private String viewOption;
	
	
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupEnglishName() {
		return groupEnglishName;
	}
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getViewOption() {
		return viewOption;
	}
	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}
}