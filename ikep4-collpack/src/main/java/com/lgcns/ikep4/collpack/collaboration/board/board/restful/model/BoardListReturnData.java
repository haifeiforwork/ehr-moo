package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class BoardListReturnData {
	
	/**
	 * 게시판 ID
	 */
	private String boardId = "";
	
	/**
	 * 게시판 명
	 */
	private String boardName = "";
	
	/**
	 * 부모 게시판ID
	 */
	private String boardParentId = "";
	
	/**
	 * 하위게시판존재여부
	 */
	private String hasChildren = "";
	
	/**
	 * 게시판 정렬순서
	 */
	private String siblingOrder = "";
	
	/**
	 * 읽기 권한
	 */
	private String readPermit = "";
	
	/**
	 * 쓰기 권한
	 */
	private String writePermit = "";
	
	/**
	 * 답글사용 여부
	 */
	private String isReply = "";
	
	/**
	 * 댓글사용 여부
	 */
	private String isComment = "";
	
	
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardParentId() {
		return boardParentId;
	}
	public void setBoardParentId(String boardParentId) {
		this.boardParentId = boardParentId;
	}
	public String getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getSiblingOrder() {
		return siblingOrder;
	}
	public void setSiblingOrder(String siblingOrder) {
		this.siblingOrder = siblingOrder;
	}
	public String getIsReply() {
		return isReply;
	}
	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}	
	public String getIsComment() {
		return isComment;
	}
	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}
	public String getReadPermit() {
		return readPermit;
	}
	public void setReadPermit(String readPermit) {
		this.readPermit = readPermit;
	}
	public String getWritePermit() {
		return writePermit;
	}
	public void setWritePermit(String writePermit) {
		this.writePermit = writePermit;
	}
}