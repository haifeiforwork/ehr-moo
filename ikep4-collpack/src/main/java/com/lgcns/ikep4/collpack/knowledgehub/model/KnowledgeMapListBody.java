/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;

import java.util.Date;


/**
 * Knowledge Map KnowledgeListPK model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapListBody extends KnowledgeMapListPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -614925530820906270L;

	/**
	 * 태그 아이템 이름(예 : 게시글 제목)
	 */
	private String tagItemName;

	/**
	 * 태그 아이템 내용(예 : 게시글 내용)
	 */
	private String tagItemContents;
	
	/**
	 * 태그 아이템 view 화면 URL
	 */
	private String tagItemUrl;
	
	/**
	 * 점수
	 */
	private int score;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * Record Number
	 */
	private int recordNumber;

	/**
	 * @return the tagItemName
	 */
	public String getTagItemName() {
		return tagItemName;
	}

	/**
	 * @param tagItemName the tagItemName to set
	 */
	public void setTagItemName(String tagItemName) {
		this.tagItemName = tagItemName;
	}

	/**
	 * @return the tagItemContents
	 */
	public String getTagItemContents() {
		return tagItemContents;
	}

	/**
	 * @param tagItemContents the tagItemContents to set
	 */
	public void setTagItemContents(String tagItemContents) {
		this.tagItemContents = tagItemContents;
	}

	/**
	 * @return the tagItemUrl
	 */
	public String getTagItemUrl() {
		return tagItemUrl;
	}

	/**
	 * @param tagItemUrl the tagItemUrl to set
	 */
	public void setTagItemUrl(String tagItemUrl) {
		this.tagItemUrl = tagItemUrl;
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
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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
	 * @return the recordNumber
	 */
	public int getRecordNumber() {
		return recordNumber;
	}

	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
