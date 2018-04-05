package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class CollListHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public CollListBody body;
	
	public CollListHead() {}
	
	public CollListHead(Head head, CollListBody body) {
		this.head = head;
		this.body = body;
	}
	
}