package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class VcardFolderList {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public VcardFolderListBody body;
	
	public VcardFolderList() {}
	
	public VcardFolderList(Head head, VcardFolderListBody body) {
		this.head = head;
		this.body = body;
	}
	
}