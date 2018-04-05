package com.lgcns.ikep4.portal.login.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="body")
public class DeptListBody {
	
	@XmlElement(name="DeptList")
	public List<DeptListReturnData> deptListReturnData;
	
	public DeptListBody() {}
	
	public DeptListBody(List<DeptListReturnData> deptListReturnData) {
		this.deptListReturnData = deptListReturnData;
	}
}