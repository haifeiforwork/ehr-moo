package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TotalListInfo")
public class NoteListReturnData1 {
	
	public int totalPageNum=0;
	public int totalListCount=0;
		
	public NoteListReturnData1() {}
	
	public NoteListReturnData1(int totalPageNum, int totalListCount) {
		this.totalPageNum = totalPageNum;
		this.totalListCount = totalListCount;
	}
	
}