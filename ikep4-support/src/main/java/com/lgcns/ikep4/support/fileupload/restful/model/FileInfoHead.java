package com.lgcns.ikep4.support.fileupload.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class FileInfoHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public FileInfoBody body;
	
	public FileInfoHead() {}
	
	public FileInfoHead(Head head, FileInfoBody body) {
		this.head = head;
		this.body = body;
	}
	
}