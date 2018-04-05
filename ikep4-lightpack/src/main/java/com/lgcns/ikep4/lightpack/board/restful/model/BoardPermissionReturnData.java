package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData")
public class BoardPermissionReturnData {
	
	/**
	 * 게시판 쓰기권한
	 */
	private String isWritePermit = "";
	
	public String getIsWritePermit() {
		return isWritePermit;
	}
	public void setIsWritePermit(String isWritePermit) {
		this.isWritePermit = isWritePermit;
	}
	
}