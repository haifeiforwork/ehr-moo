package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="body")
public class DeptInfoBody {
	
	@XmlElement(name="DeptInfo")
	public DeptInfoReturnData deptInfoReturnData;
	
	public DeptInfoBody() {}
	
	public DeptInfoBody(DeptInfoReturnData deptInfoReturnData) {
		this.deptInfoReturnData = deptInfoReturnData;
	}
}