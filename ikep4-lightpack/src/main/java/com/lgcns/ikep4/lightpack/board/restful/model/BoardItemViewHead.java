package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class BoardItemViewHead {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public BoardItemViewBody body;
	
	public BoardItemViewHead() {}
	
	public BoardItemViewHead(Head head, BoardItemViewBody body) {
		this.head = head;
		this.body = body;
	}
	
}