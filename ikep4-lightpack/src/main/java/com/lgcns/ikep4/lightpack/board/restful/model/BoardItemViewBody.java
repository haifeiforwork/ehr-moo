package com.lgcns.ikep4.lightpack.board.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BoardItemViewBody {
	
	@XmlElement(name="BoradDetail")
	public BoardItemViewReturnData0 boardItemViewReturnData0;

	@XmlElement(name="AttachList")
	public List<BoardItemViewReturnData1> boardItemViewReturnData1;
	
	@XmlElement(name="CategoryList")
	public List<BoardItemViewReturnData2> boardItemViewReturnData2;
	
	public BoardItemViewBody() {}
	
	public BoardItemViewBody(BoardItemViewReturnData0 boardItemViewReturnData0, List<BoardItemViewReturnData1> boardItemViewReturnData1) {
		this.boardItemViewReturnData0 = boardItemViewReturnData0;
		this.boardItemViewReturnData1 = boardItemViewReturnData1;
	}

	public BoardItemViewBody(BoardItemViewReturnData0 boardItemViewReturnData0, List<BoardItemViewReturnData1> boardItemViewReturnData1 , List<BoardItemViewReturnData2> boardItemViewReturnData2) {
		this.boardItemViewReturnData0 = boardItemViewReturnData0;
		this.boardItemViewReturnData1 = boardItemViewReturnData1;
		this.boardItemViewReturnData2 = boardItemViewReturnData2;
	}
}