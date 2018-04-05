package com.lgcns.ikep4.support.note.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.note.restful.model.NoteListReturnData0;
import com.lgcns.ikep4.support.note.restful.model.NoteListReturnData1;

@XmlRootElement(name="body")
public class NoteListBody {
	
	@XmlElement(name="NoteList")
	public List<NoteListReturnData0> noteListReturnData0;

	@XmlElement(name="TotalListInfo")
	public NoteListReturnData1 noteListReturnData1;
	
	public NoteListBody() {}
	
	public NoteListBody(List<NoteListReturnData0> noteListReturnData0, NoteListReturnData1 noteListReturnData1) {
		this.noteListReturnData0 = noteListReturnData0;
		this.noteListReturnData1 = noteListReturnData1;
	}
}