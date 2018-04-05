package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.restful.model.Head;

@XmlRootElement(name="Root")
public class NoteList {
	
	@XmlElement(name="header")
	public Head head;
	
	@XmlElement(name="body")
	public NoteListBody body;
	
	public NoteList() {}
	
	public NoteList(Head head, NoteListBody body) {
		this.head = head;
		this.body = body;
	}
	
}