package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class ShareFolderList {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public ShareFolderListBody body;
	
	public ShareFolderList() {}
	
	public ShareFolderList(Head head, ShareFolderListBody body) {
		this.head = head;
		this.body = body;
	}
	
}