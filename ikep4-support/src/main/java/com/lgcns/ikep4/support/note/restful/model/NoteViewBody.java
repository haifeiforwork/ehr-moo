package com.lgcns.ikep4.support.note.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.note.restful.model.NoteViewReturnData0;
import com.lgcns.ikep4.support.note.restful.model.NoteViewReturnData1;

@XmlRootElement(name="body")
public class NoteViewBody {
	
	@XmlElement(name="NoteDetail")
	public NoteViewReturnData0 noteViewReturnData0;

	@XmlElement(name="AttachList")
	public List<NoteViewReturnData1> noteViewReturnData1;
	
	public NoteViewBody() {}
	
	public NoteViewBody(NoteViewReturnData0 noteViewReturnData0, List<NoteViewReturnData1> noteViewReturnData1) {
		this.noteViewReturnData0 = noteViewReturnData0;
		this.noteViewReturnData1 = noteViewReturnData1;
	}
}