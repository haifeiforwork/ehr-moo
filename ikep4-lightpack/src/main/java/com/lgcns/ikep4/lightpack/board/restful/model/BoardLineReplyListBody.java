package com.lgcns.ikep4.lightpack.board.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BoardLineReplyListBody {
	
	@XmlElement(name="CommentCount")
	public BoardLineReplyListReturnData0 boardLineReplyListReturnData0;
	
	@XmlElement(name="CommentList")
	public List<BoardLineReplyListReturnData1> boardLineReplyListReturnData1;

	@XmlElement(name="TotalListInfo")
	public BoardLineReplyListReturnData2 boardLineReplyListReturnData2;
	
	public BoardLineReplyListBody() {}
	
	public BoardLineReplyListBody(BoardLineReplyListReturnData0 boardLineReplyListReturnData0
								 ,List<BoardLineReplyListReturnData1> boardLineReplyListReturnData1
								 ,BoardLineReplyListReturnData2 boardLineReplyListReturnData2) {
		this.boardLineReplyListReturnData0 = boardLineReplyListReturnData0;
		this.boardLineReplyListReturnData1 = boardLineReplyListReturnData1;
		this.boardLineReplyListReturnData2 = boardLineReplyListReturnData2;
	}
}