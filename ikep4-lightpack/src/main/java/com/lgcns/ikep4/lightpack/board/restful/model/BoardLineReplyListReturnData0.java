package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class BoardLineReplyListReturnData0 {
	
	/**
	 * 댓글수
	 */
	public int itemCommentCount = 0;
		
	public BoardLineReplyListReturnData0() {}
	
	public BoardLineReplyListReturnData0(int itemCommentCount) {
		this.itemCommentCount = itemCommentCount;
	}
	
}