package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DeptList")
public class DeptListReturnData1 {

	private String deptId;
	private String deptName;

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}