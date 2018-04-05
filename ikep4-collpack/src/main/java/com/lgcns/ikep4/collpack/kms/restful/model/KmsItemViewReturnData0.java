package com.lgcns.ikep4.collpack.kms.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class KmsItemViewReturnData0 {
	
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
	
	private String isKnowhow;
	
	/**
	 * 게시글 제목
	 */
	private String itemTitle = "";
	
	private String infoGrade = "";
	
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
	
	private String assessDate = "";
	
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
	
	/**
	 * 익명 사용자 지원 여부
	 */
	private int isAnonymous = 0;
	
	/**
	 * 이전 게시글 제목
	 */
	private String preItemTitle = "";
	
	/**
	 * 이전 게시글 ID
	 */
	private String preItemId = "";
	
	/**
	 * 이전 게시글 댓글수
	 */
	private int preItemRepCount = 0;
	
	/**
	 * 다음 게시글 제목
	 */
	private String nexItemTitle = "";
	
	/**
	 * 다음 게시글 ID
	 */
	private String nexItemId = "";
	
	/**
	 * 다음 게시글 댓글수
	 */
	private int nexItemReplyCount = 0;
	
	/**
	 * 카테고리 아이디
	 */
	private String wordId = "";
	
	/**
	 * 카테고리 이름
	 */
	private String wordName = "";
	
	private Integer recommendCount;
	
	private Integer mark;
	
	private String score;
	
	private String boardId;

		
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
	
	public int getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(int isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	
	public String getPreItemTitle() {
		return preItemTitle;
	}
	public void setPreItemTitle(String preItemTitle) {
		this.preItemTitle = preItemTitle;
	}
	
	public String getPreItemId() {
		return preItemId;
	}
	public void setPreItemId(String preItemId) {
		this.preItemId = preItemId;
	}

	public int getPreItemRepCount() {
		return preItemRepCount;
	}
	public void setPreItemRepCount(int preItemRepCount) {
		this.preItemRepCount = preItemRepCount;
	}
	
	public String getNexItemTitle() {
		return nexItemTitle;
	}
	public void setNexItemTitle(String nexItemTitle) {
		this.nexItemTitle = nexItemTitle;
	}
	
	public String getNexItemId() {
		return nexItemId;
	}
	public void setNexItemId(String nexItemId) {
		this.nexItemId = nexItemId;
	}

	public int getNexItemReplyCount() {
		return nexItemReplyCount;
	}
	public void setNexItemReplyCount(int nexItemReplyCount) {
		this.nexItemReplyCount = nexItemReplyCount;
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
	public String getAssessDate() {
		return assessDate;
	}
	public void setAssessDate(String assessDate) {
		this.assessDate = assessDate;
	}
	public Integer getRecommendCount() {
		return recommendCount;
	}
	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getInfoGrade() {
		return infoGrade;
	}
	public void setInfoGrade(String infoGrade) {
		this.infoGrade = infoGrade;
	}
	public String getIsKnowhow() {
		return isKnowhow;
	}
	public void setIsKnowhow(String isKnowhow) {
		this.isKnowhow = isKnowhow;
	}
	
}