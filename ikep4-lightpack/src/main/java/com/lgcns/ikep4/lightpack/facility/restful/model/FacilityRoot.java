package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class FacilityRoot {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public Object body;
	
	public FacilityRoot() {}
	
	public FacilityRoot(Head head, Object body) {
		this.head = head;
		this.body = body;
	}
	
}