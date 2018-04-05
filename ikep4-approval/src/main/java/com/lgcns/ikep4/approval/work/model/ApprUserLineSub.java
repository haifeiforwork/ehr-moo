/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * TODO 문서결재 사용자 정의 결재선 Item 정보
 *
 * @author 
 * @version $Id$
 */
public class ApprUserLineSub extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 정의 결재선 ID
	 */
	private	String	userLineId;
	
	/**
	 * 결재자 ID
	 */
	private	String	apprUserId;
	
	/**
	 * 결재자 유형 (사용자:0, 그룹:1)
	 */
	private	int		apprUserType;
	
	/**
	 * 결재타입 (0:결재, 1:합의(필수), 2:합의(선택), 3:수신처)
	 */
	private	int		apprType;

	/**
	 * 결재순서
	 */
	private	int		apprOrder;

	/**
	 * 결재자 직위명
	 */
	private	String	apprJobTitle;
	
	/**
	 * 결재선 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private	int		lineModifyAuth;
	
	/**
	 * 결재문서 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private	int		docModifyAuth;
	
	/**
	 * 참조자,열람권자 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private	int		readModifyAuth;
	
	private	String	userName;
	
	private	String	teamName;
	/**
	 * @return the userLineId
	 */
	public String getUserLineId() {
		return userLineId;
	}

	/**
	 * @param userLineId the userLineId to set
	 */
	public void setUserLineId(String userLineId) {
		this.userLineId = userLineId;
	}

	/**
	 * @return the apprUserId
	 */
	public String getApprUserId() {
		return apprUserId;
	}

	/**
	 * @param apprUserId the apprUserId to set
	 */
	public void setApprUserId(String apprUserId) {
		this.apprUserId = apprUserId;
	}

	/**
	 * @return the apprUserType
	 */
	public int getApprUserType() {
		return apprUserType;
	}

	/**
	 * @param apprUserType the apprUserType to set
	 */
	public void setApprUserType(int apprUserType) {
		this.apprUserType = apprUserType;
	}

	/**
	 * @return the apprType
	 */
	public int getApprType() {
		return apprType;
	}

	/**
	 * @param apprType the apprType to set
	 */
	public void setApprType(int apprType) {
		this.apprType = apprType;
	}

	/**
	 * @return the apprOrder
	 */
	public int getApprOrder() {
		return apprOrder;
	}

	/**
	 * @param apprOrder the apprOrder to set
	 */
	public void setApprOrder(int apprOrder) {
		this.apprOrder = apprOrder;
	}

	/**
	 * @return the apprJobTitle
	 */
	public String getApprJobTitle() {
		return apprJobTitle;
	}

	/**
	 * @param apprJobTitle the apprJobTitle to set
	 */
	public void setApprJobTitle(String apprJobTitle) {
		this.apprJobTitle = apprJobTitle;
	}

	/**
	 * @return the lineModifyAuth
	 */
	public int getLineModifyAuth() {
		return lineModifyAuth;
	}

	/**
	 * @param lineModifyAuth the lineModifyAuth to set
	 */
	public void setLineModifyAuth(int lineModifyAuth) {
		this.lineModifyAuth = lineModifyAuth;
	}

	/**
	 * @return the docModifyAuth
	 */
	public int getDocModifyAuth() {
		return docModifyAuth;
	}

	/**
	 * @param docModifyAuth the docModifyAuth to set
	 */
	public void setDocModifyAuth(int docModifyAuth) {
		this.docModifyAuth = docModifyAuth;
	}

	/**
	 * @return the readModifyAuth
	 */
	public int getReadModifyAuth() {
		return readModifyAuth;
	}

	/**
	 * @param readModifyAuth the readModifyAuth to set
	 */
	public void setReadModifyAuth(int readModifyAuth) {
		this.readModifyAuth = readModifyAuth;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
}
