/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 결재 목록정보
 *
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApProcessViewData.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApProcessViewData extends BaseObject {

	/**
	 *	결재ID
	 */
	@NotEmpty	
	private String 	apprId;
	
	/**
	 *	결재자 ID
	 */
	@NotEmpty
	private String 	apprUserId;

	/**
	 *	결재자 명
	 */
	@NotEmpty
	private String 	apprUserName;

	/**
	 *	APPR TYPER
	 */
	@NotEmpty
	private Integer	apprType;
	
	/**
	 *	결재 타입 명
	 */
	@NotEmpty
	private String 	apprTypeName;
	
	/**
	 *	결재 순서
	 */
	@NotEmpty
	private Integer	apprOrder;	
	
	/**
	 *	결재상태
	 */
	@NotEmpty
	private String 	apprState;

	/**
	 *	결재상태명
	 */
	@NotEmpty
	private String 	apprStateName;

	/**
	 *	결재일자
	 */
	@NotEmpty
	private Date 	apprDate;

	/**
	 *	결재타입
	 */
	@NotEmpty
	private String 	apprLineType;

	/**
	 *	order by 순서
	 */
	private Integer	apprOrderbySeq;		
	
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
	 * @return apprUserId
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
	 * @return apprUserName
	 */
	public String getApprUserName() {
		return apprUserName;
	}

	/**
	 * @param apprUserName the apprUserName to set
	 */
	public void setApprUserName(String apprUserName) {
		this.apprUserName = apprUserName;
	}

	/**
	 * @return the apprType
	 */
	public Integer getApprType() {
		return apprType;
	}

	/**
	 * @param apprType the apprType to set
	 */
	public void setApprType(int apprType) {
		this.apprType = apprType;
	}

	/**
	 * @return the apprTypeName
	 */
	public String getApprTypeName() {
		return apprTypeName;
	}

	/**
	 * @param apprTypeName the apprTypeName to set
	 */
	public void setApprTypeName(String apprTypeName) {
		this.apprTypeName = apprTypeName;
	}

	/**
	 * @return the apprOrder
	 */
	public Integer getApprOrder() {
		return apprOrder;
	}

	/**
	 * @param apprOrder the apprOrder to set
	 */
	public void setApprOrder(int apprOrder) {
		this.apprOrder = apprOrder;
	}

	/**
	 * @return the apprState
	 */
	public String getApprState() {
		return apprState;
	}

	/**
	 * @param apprState the apprState to set
	 */
	public void setApprState(String apprState) {
		this.apprState = apprState;
	}

	/**
	 * @return the apprStateName
	 */
	public String getApprStateName() {
		return apprStateName;
	}

	/**
	 * @param apprStateName the apprStateName to set
	 */
	public void setApprStateName(String apprStateName) {
		this.apprStateName = apprStateName;
	}

	/**
	 * @return the apprDate
	 */
	public Date getApprDate() {
		return apprDate;
	}

	/**
	 * @param apprDate the apprDate to set
	 */
	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}

	/**
	 * @return apprLineType
	 */
	public String getApprLineType() {
		return apprLineType;
	}

	/**
	 * @param apprLineType the apprLineType to set
	 */
	public void setApprLineType(String apprLineType) {
		this.apprLineType = apprLineType;
	}

	/**
	 * @return the apprOrderbySeq
	 */
	public Integer getApprOrderbySeq() {
		return apprOrderbySeq;
	}

	/**
	 * @param apprOrderbySeq the apprOrderbySeq to set
	 */
	public void setApprOrderbySeq(int apprOrderbySeq) {
		this.apprOrderbySeq = apprOrderbySeq;
	}
}
