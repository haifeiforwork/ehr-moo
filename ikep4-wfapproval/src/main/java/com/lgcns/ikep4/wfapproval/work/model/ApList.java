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
 * @version $Id: ApList.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApList extends BaseObject {

	private static final long serialVersionUID = -1130109513493565971L;

	/**
	 *	결재ID
	 */
	@NotEmpty	
	private String 	apprId;
	
	/**
	 *	양식ID
	 */
	private String 	formId;
	
	/**
	 *	Process ID
	 */
	private String 	processId;
	
	/**
	 *	Instance ID
	 */
	private String 	instanceId;	
	
	/**
	 *	문서번호
	 */
	private String 	apprDocNo;
	
	/**
	 *	제목
	 */
	private String 	apprTitle;

	/**
	 *	상신일자
	 */
	private Date 	apprReqDate;
	
	/**
	 *	보안등급
	 */
	private String 	apprSecurityCd;

	/**
	 *	보존연한
	 */
	private String 	apprPeriodCd;

	/**
	 *	문서종류
	 */
	private String 	apprTypeCd;

	/**
	 *	문서종류 명
	 */
	private String 	apprTypeName;

	/**
	 * 문서상태 (0:임시저장, 1:처리중, 2:완료)
	 */
	private Integer apprDocState;	

	/**
	 * 문서상태명 (0:임시저장, 1:처리중, 2:완료)
	 */
	private String apprDocStateNm;	

	/**
	 *	결재완료일
	 */
	private Date 	apprEndDate;
	
	/**
	 *	수신처 유무 : Y=사용, N=미사용
	 */
	private String 	isApprReceive;	
	
	/**
	 *	 등록자 ID
	 */
	private String 	registUserId;

	/**
	 *	 등록자명
	 */
	private String 	registUserName;
	
	/**
	 * 페이지 번호
	 */
	private Integer pageNum;

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
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @return the formClassCd
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the apprDocNo
	 */
	public String getApprDocNo() {
		return apprDocNo;
	}

	/**
	 * @param apprDocNo the apprDocNo to set
	 */
	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
	}

	/**
	 * @return the apprTitle
	 */
	public String getApprTitle() {
		return apprTitle;
	}

	/**
	 * @param apprTitle the apprTitle to set
	 */
	public void setApprTitle(String apprTitle) {
		this.apprTitle = apprTitle;
	}

	/**
	 * @return the apprReqDate
	 */
	public Date getApprReqDate() {
		return apprReqDate;
	}

	/**
	 * @param apprReqDate the apprReqDate to set
	 */
	public void setApprReqDate(Date apprReqDate) {
		this.apprReqDate = apprReqDate;
	}
	
	/**
	 * @return the apprSecurityCd
	 */
	public String getApprSecurityCd() {
		return apprSecurityCd;
	}

	/**
	 * @param apprSecurityCd the apprSecurityCd to set
	 */
	public void setApprSecurityCd(String apprSecurityCd) {
		this.apprSecurityCd = apprSecurityCd;
	}

	/**
	 * @return the apprPeriodCd
	 */
	public String getApprPeriodCd() {
		return apprPeriodCd;
	}

	/**
	 * @param apprPeriodCd the apprPeriodCd to set
	 */
	public void setApprPeriodCd(String apprPeriodCd) {
		this.apprPeriodCd = apprPeriodCd;
	}	
	
	/**
	 * @return the apprTypeCd
	 */
	public String getApprTypeCd() {
		return apprTypeCd;
	}

	/**
	 * @param apprTypeCd the apprTypeCd to set
	 */
	public void setApprTypeCd(String apprTypeCd) {
		this.apprTypeCd = apprTypeCd;
	}	

	/**
	 * @return the apprTypeCd
	 */
	public String getApprTypeName() {
		return apprTypeName;
	}

	/**
	 * @param apprTypeCd the apprTypeCd to set
	 */
	public void setApprTypeName(String apprTypeName) {
		this.apprTypeName = apprTypeName;
	}	

	/**
	 * @return the apprDocState
	 */
	public Integer getApprDocState() {
		return apprDocState;
	}

	/**
	 * @param apprDocState the apprDocState to set
	 */
	public void setApprDocState(Integer apprDocState) {
		this.apprDocState = apprDocState;
	}

	/**
	 * @return the apprDocState
	 */
	public String getApprDocStateNm() {
		return apprDocStateNm;
	}

	/**
	 * @param apprDocState the apprDocState to set
	 */
	public void setApprDocStateNm(String apprDocStateNm) {
		this.apprDocStateNm = apprDocStateNm;
	}

	/**
	 * @return the apprEndDate
	 */
	public Date getApprEndDate() {
		return apprEndDate;
	}

	/**
	 * @param apprEndDate the apprEndDate to set
	 */
	public void setApprEndDate(Date apprEndDate) {
		this.apprEndDate = apprEndDate;
	}
	
	/**
	 * @return the isApprReceive
	 */
	public String getIsApprReceive() {
		return isApprReceive;
	}

	/**
	 * @param isApprReceive the isApprReceive to set
	 */
	public void setIsApprReceive(String isApprReceive) {
		this.isApprReceive = isApprReceive;
	}
	
	/**
	 * @return the registUserId
	 */
	public String getRegistUserId() {
		return registUserId;
	}

	/**
	 * @param registUserId the registUserId to set
	 */
	public void setRegistUserId(String registUserId) {
		this.registUserId = registUserId;
	}

	/**
	 * @return the registUserName
	 */
	public String getRegistUserName() {
		return registUserName;
	}

	/**
	 * @param registUserId the registUserId to set
	 */
	public void setRegistUserName(String registUserName) {
		this.registUserName = registUserName;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * 페이지 번호
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * 페이지 번호
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

}
