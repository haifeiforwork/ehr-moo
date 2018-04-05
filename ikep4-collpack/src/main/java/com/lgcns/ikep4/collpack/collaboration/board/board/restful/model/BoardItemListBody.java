package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BoardItemListBody {
	
	@XmlElement(name="CollItemList")
	public List<BoardItemListReturnData0> boardItemListReturnData0;

	@XmlElement(name="TotalListInfo")
	public BoardItemListReturnData1 boardItemListReturnData1;
	
	public BoardItemListBody() {}
	
	public BoardItemListBody(List<BoardItemListReturnData0> boardItemListReturnData0, BoardItemListReturnData1 boardItemListReturnData1) {
		this.boardItemListReturnData0 = boardItemListReturnData0;
		this.boardItemListReturnData1 = boardItemListReturnData1;
	}
}