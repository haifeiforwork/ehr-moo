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
 * TODO 문서결재 결재선정보
 *
 * @author 
 * @version $Id$
 */
public class ApprLineVersion extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 결재선 수정버젼
	 */
	private	Double version;
	
	/**
	 * 결재문서 ID
	 */
	private	String	apprId;
	
	/**
	 * 결재타입 (0:결재, 1:합의(필수), 2:합의(선택), 3:수신)
	 */
	private	int		apprType;
	
	/**
	 * 결재자 ID (결재선 타입이 사용자이면 USER_ID, 부서면 GROUP_ID)
	 */
	private	String	approverId;
	
	/**
	 * 결재자 이름
	 */
	private	String	approverName;

	/**
	 * 결재자 부서명
	 */
	private	String	approverGroupName;
	
	/**
	 * 결재자 직위명
	 */
	private String	approverJobTitle;
	
	/**
	 * 결재자 유형 (사용자:0, 그룹:1)
	 */
	private	int		approverType;
	
	/**
	 * 결재순서
	 */
	private	int		apprOrder;
	
	
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
	

	/**
	 * 등록자 ID
	 */
	private	String registerId;
	
	/**
	 * 등록자 이름
	 */
	private	String registerName;
	
	/**
	 * 등록일자
	 */
	private	Date	registDate;
	
	/**
	 * 등록자 부서 ID
	 */
	private	String	registerGroupId;
	
	/**
	 * 등록자 부서명
	 */
	private	String	registerGroupName;
	
	/**
	 * 등록자 직위
	 */
	private	String	registerJobTitle;
	
	/**
	 * 결재선 수정사유
	 */
	private	String	modifyReason;
	
	private	int cnt;
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
	 * @return the approverId
	 */
	public String getApproverId() {
		return approverId;
	}

	/**
	 * @param approverId the approverId to set
	 */
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	/**
	 * @return the approverName
	 */
	public String getApproverName() {
		return approverName;
	}

	/**
	 * @param approverName the approverName to set
	 */
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	/**
	 * @return the approverGroupName
	 */
	public String getApproverGroupName() {
		return approverGroupName;
	}

	/**
	 * @param approverGroupName the approverGroupName to set
	 */
	public void setApproverGroupName(String approverGroupName) {
		this.approverGroupName = approverGroupName;
	}

	/**
	 * @return the approverJobTitle
	 */
	public String getApproverJobTitle() {
		return approverJobTitle;
	}

	/**
	 * @param approverJobTitle the approverJobTitle to set
	 */
	public void setApproverJobTitle(String approverJobTitle) {
		this.approverJobTitle = approverJobTitle;
	}

	/**
	 * @return the approverType
	 */
	public int getApproverType() {
		return approverType;
	}

	/**
	 * @param approverType the approverType to set
	 */
	public void setApproverType(int approverType) {
		this.approverType = approverType;
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
	 * @return the version
	 */
	public Double getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Double version) {
		this.version = version;
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
	 * @return the registerGroupId
	 */
	public String getRegisterGroupId() {
		return registerGroupId;
	}

	/**
	 * @param registerGroupId the registerGroupId to set
	 */
	public void setRegisterGroupId(String registerGroupId) {
		this.registerGroupId = registerGroupId;
	}

	/**
	 * @return the registerGroupName
	 */
	public String getRegisterGroupName() {
		return registerGroupName;
	}

	/**
	 * @param registerGroupName the registerGroupName to set
	 */
	public void setRegisterGroupName(String registerGroupName) {
		this.registerGroupName = registerGroupName;
	}

	/**
	 * @return the registerJobTitle
	 */
	public String getRegisterJobTitle() {
		return registerJobTitle;
	}

	/**
	 * @param registerJobTitle the registerJobTitle to set
	 */
	public void setRegisterJobTitle(String registerJobTitle) {
		this.registerJobTitle = registerJobTitle;
	}

	/**
	 * @return the modifyReason
	 */
	public String getModifyReason() {
		return modifyReason;
	}

	/**
	 * @param modifyReason the modifyReason to set
	 */
	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	/**
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}




}
