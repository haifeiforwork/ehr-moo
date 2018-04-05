/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Knowledge Monitor KnowledgeMonitorCviPointBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorCviPointBody extends KnowledgeMonitorCviPointPK {

	/**
	 *
	 */
	private static final long serialVersionUID = 499082311302718838L;

	/**
	 * 모듈 코드, IKEP4_EV_ITEM_TYPE 테이블의 ITEM_TYPE_CODE값 ( ex.  SB : Social Blog, WS : Workspace, QA : QnA, CV : Cooperate Vocabulary, WM : Work Manual, CF : Cafe, FR : Forum, ID : Idea, BD : BBS )
	 */
	@NotEmpty
	private String moduleCode;

	/**
	 * 포탈 ID
	 */
	@NotEmpty
	private String portalId;

	/**
	 * 컨텐츠 제목
	 */
	private String title;

	/**
	 * 컨텐츠 작성자ID
	 */
	private String registerId;

	/**
	 *컨텐츠 등록일
	 */
	private Date registDate;

	/**
	 * View 화면 URL
	 */
	private String itemUrl;

	/**
	 * CVI 점수
	 */
	private int cviScore;

	/**
	 * 조회 수
	 */
	private int hitCount;

	/**
	 * 조회점수
	 */
	private int hitScore;

	/**
	 * 추천 수
	 */
	private int recommendCount;

	/**
	 * 추천점수
	 */
	private int recommendScore;

	/**
	 * 댓글 수
	 */
	private int linereplyCount;

	/**
	 * 댓글점수
	 */
	private int linereplyScore;

	/**
	 * Favorite 수
	 */
	private int favoriteCount;

	/**
	 * Favorite 점수
	 */
	private int favoriteScore;

	/**
	 * 메일 수
	 */
	private int mailCount;

	/**
	 * MicroBlog 수
	 */
	private int mblogCount;

	/**
	 * 배포 점수
	 */
	private int distributeScore;

	/**
	 *집계일
	 */
	private Date collectDate;

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the itemUrl
	 */
	public String getItemUrl() {
		return itemUrl;
	}

	/**
	 * @param itemUrl the itemUrl to set
	 */
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	/**
	 * @return the cviScore
	 */
	public int getCviScore() {
		return cviScore;
	}

	/**
	 * @param cviScore the cviScore to set
	 */
	public void setCviScore(int cviScore) {
		this.cviScore = cviScore;
	}

	/**
	 * @return the hitCount
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * @param hitCount the hitCount to set
	 */
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	/**
	 * @return the hitScore
	 */
	public int getHitScore() {
		return hitScore;
	}

	/**
	 * @param hitScore the hitScore to set
	 */
	public void setHitScore(int hitScore) {
		this.hitScore = hitScore;
	}

	/**
	 * @return the recommendCount
	 */
	public int getRecommendCount() {
		return recommendCount;
	}

	/**
	 * @param recommendCount the recommendCount to set
	 */
	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	/**
	 * @return the recommendScore
	 */
	public int getRecommendScore() {
		return recommendScore;
	}

	/**
	 * @param recommendScore the recommendScore to set
	 */
	public void setRecommendScore(int recommendScore) {
		this.recommendScore = recommendScore;
	}

	/**
	 * @return the linereplyCount
	 */
	public int getLinereplyCount() {
		return linereplyCount;
	}

	/**
	 * @param linereplyCount the linereplyCount to set
	 */
	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	/**
	 * @return the linereplyScore
	 */
	public int getLinereplyScore() {
		return linereplyScore;
	}

	/**
	 * @param linereplyScore the linereplyScore to set
	 */
	public void setLinereplyScore(int linereplyScore) {
		this.linereplyScore = linereplyScore;
	}

	/**
	 * @return the favoriteCount
	 */
	public int getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * @param favoriteCount the favoriteCount to set
	 */
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	/**
	 * @return the favoriteScore
	 */
	public int getFavoriteScore() {
		return favoriteScore;
	}

	/**
	 * @param favoriteScore the favoriteScore to set
	 */
	public void setFavoriteScore(int favoriteScore) {
		this.favoriteScore = favoriteScore;
	}

	/**
	 * @return the mailCount
	 */
	public int getMailCount() {
		return mailCount;
	}

	/**
	 * @param mailCount the mailCount to set
	 */
	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}

	/**
	 * @return the mblogCount
	 */
	public int getMblogCount() {
		return mblogCount;
	}

	/**
	 * @param mblogCount the mblogCount to set
	 */
	public void setMblogCount(int mblogCount) {
		this.mblogCount = mblogCount;
	}

	/**
	 * @return the distributeScore
	 */
	public int getDistributeScore() {
		return distributeScore;
	}

	/**
	 * @param distributeScore the distributeScore to set
	 */
	public void setDistributeScore(int distributeScore) {
		this.distributeScore = distributeScore;
	}

	/**
	 * @return the collectDate
	 */
	public Date getCollectDate() {
		return collectDate;
	}

	/**
	 * @param collectDate the collectDate to set
	 */
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

}
