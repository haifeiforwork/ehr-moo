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
public class ApprLine extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 결재자 직위코드
	 */
	private String	approverJobTitleCode;	
	/**
	 * 결재자 유형 (사용자:0, 그룹:1)
	 */
	private	int		approverType;
	
	/**
	 * 결재순서
	 */
	private	int		apprOrder;
	
	/**
	 * 진행상태 (0:대기,1:진행,2:승인,3:반려,4:합의..)
	 */
	private	int		apprStatus;
	
	/**
	 * 결재 의견
	 */
	private	String	apprMessage;
	
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
	 * 위임자 ID
	 */
	private	String	entrustUserId;
	
	/**
	 * 위임자명
	 */
	private	String	entrustUserName;
	
	/**
	 * 위임자부서명
	 */
	private	String	entrustGroupName;
	
	/**
	 * 위임자직위명
	 */
	private	String	entrustJobTitle;
	
	/**
	 * 최초 결재 조회(확인)일자
	 */
	private	Date	viewDate;
	
	/**
	 * 결재 처리일자
	 */
	private	Date	apprDate;

	/**
	 * 결재선명
	 */
	private	String	lineName;
	
	/**
	 * 결재선 합의유형 (0:순차합의, 1:병렬합의)
	 */
	private	int		apprLineType;
	
	/**
	 * 결재 비밀번호
	 */
	private	String	apprPassword;
	
	/**
	 * 사용자 결재선 정보
	 */
	private	List<ApprUserLine>	apprUserLine;
	
	/**
	 * 결재선지정 목록
	 */
	private	List<ApprLine>	apprLineList;
	/**
	 * 수신처 지정 목록
	 */
	private	List<ApprLine>	apprReceiveLineList;
	
	/**
	 * 결재선 버전 목록
	 */
	private	List<ApprLineVersion>	apprLineVersion;

	/**
	 * 결재선 변경사유
	 */
	private String modifyReason;
	
	/**
	 * 서명 파일 Id
	 */
	private	String	signFileId;
	
	/**
	 * 위임유일키Id
	 */
	private	String	entrustId;
	
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
	 * @return the apprStatus
	 */
	public int getApprStatus() {
		return apprStatus;
	}

	/**
	 * @param apprStatus the apprStatus to set
	 */
	public void setApprStatus(int apprStatus) {
		this.apprStatus = apprStatus;
	}

	/**
	 * @return the apprMessage
	 */
	public String getApprMessage() {
		return apprMessage;
	}

	/**
	 * @param apprMessage the apprMessage to set
	 */
	public void setApprMessage(String apprMessage) {
		this.apprMessage = apprMessage;
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
	 * @return the entrustUserId
	 */
	public String getEntrustUserId() {
		return entrustUserId;
	}

	/**
	 * @param entrustUserId the entrustUserId to set
	 */
	public void setEntrustUserId(String entrustUserId) {
		this.entrustUserId = entrustUserId;
	}

	/**
	 * @return the entrustUserName
	 */
	public String getEntrustUserName() {
		return entrustUserName;
	}

	/**
	 * @param entrustUserName the entrustUserName to set
	 */
	public void setEntrustUserName(String entrustUserName) {
		this.entrustUserName = entrustUserName;
	}

	/**
	 * @return the entrustGroupName
	 */
	public String getEntrustGroupName() {
		return entrustGroupName;
	}

	/**
	 * @param entrustGroupName the entrustGroupName to set
	 */
	public void setEntrustGroupName(String entrustGroupName) {
		this.entrustGroupName = entrustGroupName;
	}

	/**
	 * @return the entrustJobTitle
	 */
	public String getEntrustJobTitle() {
		return entrustJobTitle;
	}

	/**
	 * @param entrustJobTitle the entrustJobTitle to set
	 */
	public void setEntrustJobTitle(String entrustJobTitle) {
		this.entrustJobTitle = entrustJobTitle;
	}

	/**
	 * @return the viewDate
	 */
	public Date getViewDate() {
		return viewDate;
	}

	/**
	 * @param viewDate the viewDate to set
	 */
	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
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
	 * @return the apprUserLine
	 */
	public List<ApprUserLine> getApprUserLine() {
		return apprUserLine;
	}

	/**
	 * @param apprUserLine the apprUserLine to set
	 */
	public void setApprUserLine(List<ApprUserLine> apprUserLine) {
		this.apprUserLine = apprUserLine;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * @return the apprLineType
	 */
	public int getApprLineType() {
		return apprLineType;
	}

	/**
	 * @param apprLineType the apprLineType to set
	 */
	public void setApprLineType(int apprLineType) {
		this.apprLineType = apprLineType;
	}

	/**
	 * @return the apprLine
	 */
	public List<ApprLine> getApprLineList() {
		return apprLineList;
	}

	/**
	 * @param apprLine the apprLine to set
	 */
	public void setApprLineList(List<ApprLine> apprLineList) {
		this.apprLineList = apprLineList;
	}

	/**
	 * @return the apprReceiveLineList
	 */
	public List<ApprLine> getApprReceiveLineList() {
		return apprReceiveLineList;
	}

	/**
	 * @param apprReceiveLineList the apprReceiveLineList to set
	 */
	public void setApprReceiveLineList(List<ApprLine> apprReceiveLineList) {
		this.apprReceiveLineList = apprReceiveLineList;
	}

	public String getApprPassword() {
		return apprPassword;
	}

	public void setApprPassword(String apprPassword) {
		this.apprPassword = apprPassword;
	}

	/**
	 * @return the approverJobTitleCode
	 */
	public String getApproverJobTitleCode() {
		return approverJobTitleCode;
	}

	/**
	 * @param approverJobTitleCode the approverJobTitleCode to set
	 */
	public void setApproverJobTitleCode(String approverJobTitleCode) {
		this.approverJobTitleCode = approverJobTitleCode;
	}

	/**
	 * @return the apprLineVersion
	 */
	public List<ApprLineVersion> getApprLineVersion() {
		return apprLineVersion;
	}

	/**
	 * @param apprLineVersion the apprLineVersion to set
	 */
	public void setApprLineVersion(List<ApprLineVersion> apprLineVersion) {
		this.apprLineVersion = apprLineVersion;
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

	public String getSignFileId() {
		return signFileId;
	}

	public void setSignFileId(String signFileId) {
		this.signFileId = signFileId;
	}

	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

}
