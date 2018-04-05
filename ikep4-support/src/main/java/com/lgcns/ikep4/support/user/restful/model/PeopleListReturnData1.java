package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TotalListInfo")
public class PeopleListReturnData1 {
	
	public int totalPageNum;
	public int totalListCount;
		
	public PeopleListReturnData1() {}
	
	public PeopleListReturnData1(int totalPageNum, int totalListCount) {
		this.totalPageNum = totalPageNum;
		this.totalListCount = totalListCount;
	}
	
}