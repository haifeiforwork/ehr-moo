package com.lgcns.ikep4.support.user.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData0;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData1;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData2;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData3;

@XmlRootElement(name="body")
public class DeptListBody {
	
	@XmlElement(name="AddrList")
	public List<DeptListReturnData0> deptListReturnData0;

	@XmlElement(name="DeptList")
	public List<DeptListReturnData1> deptListReturnData1;

	@XmlElement(name="parentDeptList")
	public List<DeptListReturnData2> deptListReturnData2;

	@XmlElement(name="currentDeptInfo")
	public DeptListReturnData3 deptListReturnData3;
	
	public DeptListBody() {}
	
	public DeptListBody(List<DeptListReturnData0> deptListReturnData0, List<DeptListReturnData1> deptListReturnData1, List<DeptListReturnData2> deptListReturnData2, DeptListReturnData3 deptListReturnData3) {
		this.deptListReturnData0 = deptListReturnData0;
		this.deptListReturnData1 = deptListReturnData1;
		this.deptListReturnData2 = deptListReturnData2;
		this.deptListReturnData3 = deptListReturnData3;
	}
}