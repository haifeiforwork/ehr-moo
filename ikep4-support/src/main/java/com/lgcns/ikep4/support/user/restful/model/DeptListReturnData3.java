package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="currentDeptInfo")
public class DeptListReturnData3 {

	private String selectedDeptId;
	private String selectedDeptName;
	private String selectedDeptLevel;

	public String getSelectedDeptId() {
		return selectedDeptId;
	}
	public void setSelectedDeptId(String selectedDeptId) {
		this.selectedDeptId = selectedDeptId;
	}
	public String getSelectedDeptName() {
		return selectedDeptName;
	}
	public void setSelectedDeptName(String selectedDeptName) {
		this.selectedDeptName = selectedDeptName;
	}
	public String getSelectedDeptLevel() {
		return selectedDeptLevel;
	}
	public void setSelectedDeptLevel(String selectedDeptLevel) {
		this.selectedDeptLevel = selectedDeptLevel;
	}
}