package com.lgcns.ikep4.lightpack.board.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BoardListBody {
	
	@XmlElement(name="BoardList")
	public List<BoardListReturnData> boardListReturnData;

	public BoardListBody() {}
	
	public BoardListBody(List<BoardListReturnData> boardListReturnData) {
		this.boardListReturnData = boardListReturnData;
	} 
	
}