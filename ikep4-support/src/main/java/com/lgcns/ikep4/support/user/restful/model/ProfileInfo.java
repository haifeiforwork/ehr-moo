package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class ProfileInfo {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public ProfileInfoBody body;
	
	public ProfileInfo() {}
	
	public ProfileInfo(Head head, ProfileInfoBody body) {
		this.head = head;
		this.body = body;
	}
	
}