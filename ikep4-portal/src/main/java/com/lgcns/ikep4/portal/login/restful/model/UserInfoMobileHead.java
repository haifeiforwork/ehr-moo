package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class UserInfoMobileHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public UserInfoMobileBody body;
	
	public UserInfoMobileHead() {}
	
	public UserInfoMobileHead(Head head, UserInfoMobileBody body) {
		this.head = head;
		this.body = body;
	}
	
}