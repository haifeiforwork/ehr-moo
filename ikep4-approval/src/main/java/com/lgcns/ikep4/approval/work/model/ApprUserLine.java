/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * TODO 문서결재 사용자 정의 결재선정보
 *
 * @author 
 * @version $Id$
 */
public class ApprUserLine extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 정의 결재선 ID
	 */
	private	String	userLineId;
	
	/**
	 * 사용자 정의 결재선명
	 */
	private	String	userLineName;
	
	/**
	 * 사용자 정의 결재선 타입 (0:결재선, 1:수신처)
	 */
	private	int	userLineType;
	
	/**
	 * 등록자 ID
	 */
	private	String	userId;

	/**
	 * 등록일자
	 */
	private	Date	registDate;
	
	/**
	 * 수정일자
	 */
	private Date	updateDate;

	private	List<ApprUserLineSub>	apprUserLineSub;
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
	public int getUserLineType() {
		return userLineType;
	}

	/**
	 * @param userLineType the userLineType to set
	 */
	public void setUserLineType(int userLineType) {
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the apprUserLineSub
	 */
	public List<ApprUserLineSub> getApprUserLineSub() {
		return apprUserLineSub;
	}

	/**
	 * @param apprUserLineSub the apprUserLineSub to set
	 */
	public void setApprUserLineSub(List<ApprUserLineSub> apprUserLineSub) {
		this.apprUserLineSub = apprUserLineSub;
	}


}
