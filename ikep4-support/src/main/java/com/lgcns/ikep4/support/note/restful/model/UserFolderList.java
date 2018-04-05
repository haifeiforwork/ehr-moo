package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class UserFolderList {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public UserFolderListBody body;
	
	public UserFolderList() {}
	
	public UserFolderList(Head head, UserFolderListBody body) {
		this.head = head;
		this.body = body;
	}
	
}