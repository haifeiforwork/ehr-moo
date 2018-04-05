package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class BoardItemListReturnData0 {
	
	/**
	 * 게시글 ID
	 */
	private String itemId = "";
	
	/**
	 * 그룹ID
	 */
	private String itemGroupId = "";
	
	/**
	 * 부모 게시글 Id
	 */
	private String itemParentId = "";
	
	/**
	 * 게시글STEP
	 */
	private String itemStep = "";
	
	/**
	 * 게시글LEVEL
	 */
	private String itemLevel = "";
	
	/**
	 * 게시글 제목
	 */
	private String itemTitle = "";
	
	/**
	 * 전사 게시판 목록의 상단 노출 여부
	 * ( 0 : 목록상단에 노출하지 않음 1 : 목록상단에 항상 노출함)
	 */
	private String itemDisplay = "";
	
	/**
	 * 게시글 조회수
	 */
	private String itemHitCount = "";
	
	/**
	 * 댓글수
	 */
	private String itemCommentCount = "";
	
	/**
	 * 첨부파일갯수
	 */
	private String itemAttachCnt = "";
	
	/**
	 * 중요 게시물 여부
	 */
	private String isImportant = "";
	
	/**
	 * 게시글 작성자 이름
	 */
	private String regUserName = "";
	
	/**
	 * 게시글 등록일
	 */
	private String regDate;
	
	/**
	 * 카테고리 id
	 */
	private String wordId = "";
	
	/**
	 * 카테고리 이름
	 */
	private String wordName = "";
	
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
	public String getItemParentId() {
		return itemParentId;
	}
	public void setItemParentId(String itemParentId) {
		this.itemParentId = itemParentId;
	}
	public String getItemStep() {
		return itemStep;
	}
	public void setItemStep(String itemStep) {
		this.itemStep = itemStep;
	}
	public String getItemLevel() {
		return itemLevel;
	}
	public void setItemLevel(String itemLevel) {
		this.itemLevel = itemLevel;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getItemDisplay() {
		return itemDisplay;
	}
	public void setItemDisplay(String itemDisplay) {
		this.itemDisplay = itemDisplay;
	}
	public String getItemHitCount() {
		return itemHitCount;
	}
	public void setItemHitCount(String itemHitCount) {
		this.itemHitCount = itemHitCount;
	}
	public String getItemCommentCount() {
		return itemCommentCount;
	}
	public void setItemCommentCount(String itemCommentCount) {
		this.itemCommentCount = itemCommentCount;
	}
	public String getItemAttachCnt() {
		return itemAttachCnt;
	}
	public void setItemAttachCnt(String itemAttachCnt) {
		this.itemAttachCnt = itemAttachCnt;
	}
	public String getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
}