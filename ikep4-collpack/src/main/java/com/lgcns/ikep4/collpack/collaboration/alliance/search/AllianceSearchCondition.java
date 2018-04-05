/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 동맹 관련 검색조건
 * 
 * @author 김종철
 * @version $Id: AllianceSearchCondition.java 2048 2011-03-08 12:15:53Z
 *          happyi1018 $
 */
public class AllianceSearchCondition extends SearchCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -1308703820976270117L;

	/**
	 * 포탈Id
	 */
	private String portalId;

	/**
	 * 리스트 타입 ( 전체/동맹요청목록/동맹요청받은목록)
	 */
	private String listType;

	/**
	 * 워크스페이스ID
	 */
	private String workspaceId;

	/**
	 * 동맹 요청받은 워크스페이스 ID
	 */
	private String toWorkspaceId;

	/**
	 * 동맹 ID
	 */
	private String allianceId;

	/**
	 * 동맹 상태 ( 0: 동맹요청, 1: 동맹승인, 2: 동맹거부, 3: 동맹종료 )
	 */
	private String status;

	/**
	 * Search 컬럼
	 */
	private String searchColumn;

	/**
	 * Search Word
	 */
	private String searchWord;

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
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	/**
	 * @return the toWorkspaceId
	 */
	public String getToWorkspaceId() {
		return toWorkspaceId;
	}

	/**
	 * @param toWorkspaceId the toWorkspaceId to set
	 */
	public void setToWorkspaceId(String toWorkspaceId) {
		this.toWorkspaceId = toWorkspaceId;
	}

	/**
	 * @return the allianceId
	 */
	public String getAllianceId() {
		return allianceId;
	}

	/**
	 * @param allianceId the allianceId to set
	 */
	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

}
