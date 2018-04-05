package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class DeptInfoHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public DeptInfoBody body;
	
	public DeptInfoHead() {}
	
	public DeptInfoHead(Head head, DeptInfoBody body) {
		this.head = head;
		this.body = body;
	}
	
}