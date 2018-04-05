package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData1")
public class BoardLineReplyListReturnData2 {
	
	/**
	 * 리스트 총 페이지 수
	 */
	public int totalPageNum = 0;
	
	/**
	 * 리스트 총 리스트 수
	 */
	public int totalListCount = 0;
		
	public BoardLineReplyListReturnData2() {}
	
	public BoardLineReplyListReturnData2(int totalPageNum, int totalListCount) {
		this.totalPageNum = totalPageNum;
		this.totalListCount = totalListCount;
	}
	
}