package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.PeopleDetailReturnData0;

@XmlRootElement(name="body")
public class PeopleDetailBody {
	
	@XmlElement(name="returnData0")
	public PeopleDetailReturnData0 peopleInfoListReturnData0;

	public PeopleDetailBody() {}
	
	public PeopleDetailBody(PeopleDetailReturnData0 peopleInfoListReturnData0) {
		this.peopleInfoListReturnData0 = peopleInfoListReturnData0;
	}
}