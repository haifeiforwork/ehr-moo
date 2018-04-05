package com.lgcns.ikep4.support.user.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.PeopleListReturnData0;
import com.lgcns.ikep4.support.user.restful.model.PeopleListReturnData1;

@XmlRootElement(name="body")
public class PeopleListBody {
	
	@XmlElement(name="AddrList")
	public List<PeopleListReturnData0> peopleListReturnData0;

	@XmlElement(name="TotalListInfo")
	public PeopleListReturnData1 peopleListReturnData1;
	
	public PeopleListBody() {}
	
	public PeopleListBody(List<PeopleListReturnData0> peopleListReturnData0, PeopleListReturnData1 peopleListReturnData1) {
		this.peopleListReturnData0 = peopleListReturnData0;
		this.peopleListReturnData1 = peopleListReturnData1;
	}
}