/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 결재선 검색정보
 *
 * @author 
 * @version $Id$
 */
public class ApprLineSearchCondition extends SearchCondition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 결재문서ID
	 */
	private String	apprId;
	
	/**
	 * 사용자정의 결재선 등록자 ID
	 */
	private	String	userId;
	
	/**
	 * 사용자정의 결재선명
	 */
	private String	userLineName;
	
	/**
	 * 사용자 정의 결재선 타입(0:결재선,1:수신처)
	 */
	private String	userLineType;

	private String	searchColumn;
	
	private String	searchWord;
	
	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
	}

	/**
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}

	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * @return the apprId
	 */
	public String getApprId() {
		return apprId;
	}

	/**
	 * @param apprId the apprId to set
	 */
	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	/**
	 * @return the userLineName
	 */
	public String getUserLineName() {
		return userLineName;
	}

	/**
	 * @param userLineName the userLineName to set
	 */
	public void setUserLineName(String userLineName) {
		this.userLineName = userLineName;
	}

	/**
	 * @return the userLineType
	 */
	public String getUserLineType() {
		return userLineType;
	}

	/**
	 * @param userLineType the userLineType to set
	 */
	public void setUserLineType(String userLineType) {
		this.userLineType = userLineType;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}



}
