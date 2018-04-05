package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BoardPermissionBody {
	
	@XmlElement(name="PermitInfo")
	public BoardPermissionReturnData boardPermissionReturnData;

	public BoardPermissionBody() {}
	
	public BoardPermissionBody(BoardPermissionReturnData boardPermissionReturnData) {
		this.boardPermissionReturnData = boardPermissionReturnData;
	} 
	
}