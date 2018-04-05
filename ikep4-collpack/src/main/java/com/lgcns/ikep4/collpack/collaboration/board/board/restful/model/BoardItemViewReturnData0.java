package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class BoardItemViewReturnData0 {
	
	/**
	 * 게시글 Id
	 */
	private String itemId = "";
	
	/**
	 * 게시글 그룹 ID
	 */
	private String itemGroupId = "";
	
	/**
	 * 게시글STEP
	 */
	private int itemStep = 0;
	
	/**
	 * 게시물LEVEL
	 */
	private int itemLevel = 0;
	
	/**
	 * 게시글 제목
	 */
	private String itemTitle = "";
	
	/**
	 * 게시글 등록자 사번
	 */
	private String regUserId = "";
	
	/**
	 * 게시글 등록자 성명
	 */
	private String regUserName = "";
	
	/**
	 * 게시글 등록자 조직명
	 */
	private String regUserDept = "";
	
	/**
	 * 게시글 등록자 직급
	 */
	private String regUserTitle = "";
	
	/**
	 * 게시글 등록 시간
	 */
	private String regDate = "";
	
	/**
	 * 게시글 본문
	 */
	private String itemContent = "";
	
	/**
	 * 게시글 조회수
	 */
	private int itemHitCount = 0;
	
	/**
	 * 게시글 답글수
	 */
	private int itemReplyCount = 0;
	
	/**
	 * 게시글 댓글수
	 */
	private int itemCommentCount = 0;
	
		
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getItemGroupId() {
		return itemGroupId;
	}
	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}
	
	public int getItemStep() {
		return itemStep;
	}
	public void setItemStep(int itemStep) {
		this.itemStep = itemStep;
	}
	
	public int getItemLevel() {
		return itemLevel;
	}
	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}
	
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
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
	
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	
	public int getItemReplyCount() {
		return itemReplyCount;
	}
	public void setItemReplyCount(int itemReplyCount) {
		this.itemReplyCount = itemReplyCount;
	}

	public int getItemHitCount() {
		return itemHitCount;
	}
	public void setItemHitCount(int itemHitCount) {
		this.itemHitCount = itemHitCount;
	}
	
	public int getItemCommentCount() {
		return itemCommentCount;
	}
	public void setItemCommentCount(int itemCommentCount) {
		this.itemCommentCount = itemCommentCount;
	}
	
}