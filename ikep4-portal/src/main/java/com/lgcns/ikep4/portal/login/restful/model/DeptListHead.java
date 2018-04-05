package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class DeptListHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public DeptListBody body;
	
	public DeptListHead() {}
	
	public DeptListHead(Head head, DeptListBody body) {
		this.head = head;
		this.body = body;
	}
	
}