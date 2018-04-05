package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class UserAuthHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public UserAuthBody body;
	
	public UserAuthHead() {}
	
	public UserAuthHead(Head head, UserAuthBody body) {
		this.head = head;
		this.body = body;
	}
	
}