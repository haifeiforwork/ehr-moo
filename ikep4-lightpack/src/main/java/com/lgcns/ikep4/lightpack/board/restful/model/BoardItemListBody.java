package com.lgcns.ikep4.lightpack.board.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BoardItemListBody {
	
	@XmlElement(name="BoardItemList")
	public List<BoardItemListReturnData0> boardItemListReturnData0;

	@XmlElement(name="TotalListInfo")
	public BoardItemListReturnData1 boardItemListReturnData1;
	
	@XmlElement(name="Category")
	public List< BoardItemListReturnData2> boardItemListReturnData2;
	
	public BoardItemListBody() {}
	
	public BoardItemListBody(List<BoardItemListReturnData0> boardItemListReturnData0, BoardItemListReturnData1 boardItemListReturnData1,  List< BoardItemListReturnData2> boardItemListReturnData2) {
		this.boardItemListReturnData0 = boardItemListReturnData0;
		this.boardItemListReturnData1 = boardItemListReturnData1;
		this.boardItemListReturnData2 = boardItemListReturnData2;
	}
}