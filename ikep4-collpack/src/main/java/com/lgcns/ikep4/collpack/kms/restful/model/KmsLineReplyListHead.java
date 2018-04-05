package com.lgcns.ikep4.collpack.kms.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class KmsLineReplyListHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public KmsLineReplyListBody body;
	
	public KmsLineReplyListHead() {}
	
	public KmsLineReplyListHead(Head head, KmsLineReplyListBody body) {
		this.head = head;
		this.body = body;
	}
	
}