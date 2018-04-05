package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="totalListInfo")
public class DataTotalListInfo {

	protected int totalPageNum;
	protected int totalListCount;
	
	public DataTotalListInfo(int totalPageNum, int totalListCount) {
		
		super();
		
		this.totalPageNum = totalPageNum;
		this.totalListCount = totalListCount;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public int getTotalListCount() {
		return totalListCount;
	}
	


	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	
	public void setTotalListCount(int totalListCount) {
		this.totalListCount = totalListCount;
	}
}