package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class UserInfoHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public UserInfoBody body;
	
	public UserInfoHead() {}
	
	public UserInfoHead(Head head, UserInfoBody body) {
		this.head = head;
		this.body = body;
	}
	
}