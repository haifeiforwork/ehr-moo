package com.lgcns.ikep4.collpack.kms.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData1")
public class KmsItemListReturnData1 {
	
	/**
	 * 리스트 총 페이지 수
	 */
	public int totalPageNum = 0;
	
	/**
	 * 리스트 총 리스트 수
	 */
	public int totalListCount = 0;
		
	public KmsItemListReturnData1() {}
	
	public KmsItemListReturnData1(int totalPageNum, int totalListCount) {
		this.totalPageNum = totalPageNum;
		this.totalListCount = totalListCount;
	}
	
}