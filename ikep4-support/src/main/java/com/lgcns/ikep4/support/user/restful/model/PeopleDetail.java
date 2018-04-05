package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class PeopleDetail {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public PeopleDetailBody body;
	
	public PeopleDetail() {}
	
	public PeopleDetail(Head head, PeopleDetailBody body) {
		this.head = head;
		this.body = body;
	}
	
}