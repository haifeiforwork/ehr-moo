package com.lgcns.ikep4.support.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class Root {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public Body body;
	
	public Root() {}
	
	public Root(Head head, Body body) {
		this.head = head;
		this.body = body;
	}
	
}