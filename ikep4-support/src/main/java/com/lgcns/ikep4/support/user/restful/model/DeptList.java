package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class DeptList {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public DeptListBody body;
	
	public DeptList() {}
	
	public DeptList(Head head, DeptListBody body) {
		this.head = head;
		this.body = body;
	}
	
}