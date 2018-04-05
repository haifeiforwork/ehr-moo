package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class BoardListHead {

	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public BoardListBody body;
	
	public BoardListHead() {}
	
	public BoardListHead(Head head, BoardListBody body) {
		this.head = head;
		this.body = body;
	}
	
}
