package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData1")
public class BoardLineReplyListReturnData1 {
	/**
	 * 게시글 ID
	 */
	private String itemId = "";
	/**
	 * 댓글 ID
	 */
	private String commentId = "";
	
	/**
	 * 댓글 부모 ID
	 */
	private String commentParentId = "";
	
	/**
	 * 댓글 그룹 ID
	 */
	private String commentGroupId = "";
	
	/**
	 * 댓글 내용
	 */
	private String commentContent = "";
	
	/**
	 * 댓글 등록자 ID
	 */
	private String regUserId = "";
	
	/**
	 * 댓글 등록자 성명
	 */
	private String regUserName = "";
	
	/**
	 * 댓글 등록자 부서
	 */
	private String regUserDept = "";
	
	/**
	 * 댓글 등록자 직급
	 */
	private String regUserTitle = "";
	
	/**
	 * 댓글 작성일시
	 */
	private String regDate;

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	public String getCommentParentId() {
		return commentContent;
	}
	public void setCommentParentId(String commentParentId) {
		this.commentParentId = commentParentId;
	}
	
	public String getCommentGroupId() {
		return commentContent;
	}
	public void setCommentGroupId(String commentGroupId) {
		this.commentGroupId = commentGroupId;
	}
	
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	
	public String getRegUserDept() {
		return regUserDept;
	}
	public void setRegUserDept(String regUserDept) {
		this.regUserDept = regUserDept;
	}
	
	public String getRegUserTitle() {
		return regUserTitle;
	}
	public void setRegUserTitle(String regUserTitle) {
		this.regUserTitle = regUserTitle;
	}
	
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
}