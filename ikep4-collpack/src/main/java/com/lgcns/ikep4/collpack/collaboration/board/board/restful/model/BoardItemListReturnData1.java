package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData1")
public class BoardItemListReturnData1 {
	
	/**
	 * 리스트 총 페이지 수
	 */
	public int totalPageNum = 0;
	
	/**
	 * 리스트 총 리스트 수
	 */
	public int totalListCount = 0;
		
	public BoardItemListReturnData1() {}
	
	public BoardItemListReturnData1(int totalPageNum, int totalListCount) {
		this.totalPageNum = totalPageNum;
		this.totalListCount = totalListCount;
	}
	
}