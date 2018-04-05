package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="parentDeptList")
public class DeptListReturnData2 {

	private String parentDeptId;
	private String parentDeptName;
	private String parentDeptLevel;

	public String getParentDeptId() {
		return parentDeptId;
	}
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}
	public String getParentDeptName() {
		return parentDeptName;
	}
	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	public String getParentDeptLevel() {
		return parentDeptLevel;
	}
	public void setParentDeptLevel(String parentDeptLevel) {
		this.parentDeptLevel = parentDeptLevel;
	}
}