package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class BoardLineReplyListHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public BoardLineReplyListBody body;
	
	public BoardLineReplyListHead() {}
	
	public BoardLineReplyListHead(Head head, BoardLineReplyListBody body) {
		this.head = head;
		this.body = body;
	}
	
}