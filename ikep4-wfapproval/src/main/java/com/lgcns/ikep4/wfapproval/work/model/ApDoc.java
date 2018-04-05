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

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 결재문서
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApDoc.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApDoc extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4384194596792625462L;

	
	/**
	 * 결재문서ID
	 */
	//@NotEmpty
	private String apprId;

	/**
	 * 양식ID
	 */
	private String formId;

	/**
	 * 결재선ID
	 */
	private String processId;
	
	/**
	 * 인스턴스ID
	 */
	private String instanceId;
	
	/**
	 * 결재문서 식별번호
	 */
	private String apprDocNo;

	/**
	 * 결재 제목
	 */
	private String apprTitle;
	
	/**
	 * 기안일자
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
	private Date apprReqDate;

	/**
	 * 보안등급
	 */
	private String apprSecurityCd;

	/**
	 * 보존연한
	 */
	private String apprPeriodCd;

	/**
	 * 문서종류
	 */
	private String apprTypeCd;

	/**
	 * 문서상태
	 */
	private String apprDocState;
	
	/**
	 * 결재완료일
	 */
	private Date apprEndDate;

	/**
	 * 수신처 유무
	 */
	private String isApprReceive;

	/**
	 * 등록자
	 */
	private String registUserId;

	/**
	 * 등록일자
	 */
	private Date registDate;
	
	/**
	 * 수정자
	 */
	private String modifyUserId;

	/**
	 * 수정일자
	 */
	private Date modifyDate;
	
	/**
	 * 결재 테그 데이터
	 */
	private String apprDocData;

	/**
	 * 게시판 가져올 끝 수
	 */
	private String endNo;
	
	/**
	 * 게시판 가져올 처음 수
	 */
	private String baseNo;

	/**
	 * 수신처
	 */
	private String etcName;
	
	/**
	 * 수신처 List
	 */
	private List<String> workerList;
	
	/**
	 * 열람권한지정
	 */
	private String etcName1;
	
	/**
	 * 열람권한지정 List
	 */
	private List<String> workerList1;
	
	/**
	 * 결재선지정
	 */
	private String etcName2;
	
	/**
	 * 기결제참조첨부
	 */
	private String etcName3;
	
	/**
	 * 결재선지정 List
	 */
	private List<String> workerList2;
	
	/**
	 * 기결제참조첨부 List
	 */
	private List<String> workerList3;
	
	
	/**
	 * 첨부파일링크 List
	 */
	private List<FileLink> fileLinkList;  

	/**
	 * 결재자ID
	 */
	private String apprUserId;
	
	/**
	 * 결재타입
	 */
	private String apprType;
	
	/**
	 * 결재순서
	 */
	private String apprOrder;
	
	/**
	 * 결재처리일자
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
	private Date apprDate;
	
	
	
	/**
	 * 사용자 정의 결재선명
	 */
	private String processName;
	
	/**
	 * 사용자명
	 */
	private String userName;
	
	/**
	 * 메일주소
	 */
	private String mail;
	
	/**
	 * 팀명
	 */
	private String teamName;

	/**
	 * 직급
	 */
	private String jobPositionName;

	/**
	 * 진행상태
	 */
	private String apprState;
	
	/**
	 * 의견
	 */
	private String apprMessage;

	/**
	 * 인스탄스로그ID
	 */
	private String insLogId;

	/**
	 * 수신자 ID
	 */
	private String receiveId;
	
	/**
	 * 열람승인자 ID
	 */
	private String authUserId;
	
	/**
	 * 결재선 라인 타입 직렬,병렬
	 */
	private String apprLineType;
	
	/**
	 * 결재승인여부
	 */
	private String approvalYn;
	
	/**
	 * 전결여부
	 */
	private String decisionYn;
	
	/**
	 * 결재첨부문서ID
	 */
	private String apprRefId;

	/**
	 * 결재요청시 통보대상
	 */
	private String mailReqCd;

	/**
	 * 결재완료시 통보대상
	 */
	private String mailEndCd;

	/**
	 * 결재요청통보방법
	 */
	private String mailReqWayCd;

	/**
	 * 결재완료통보방법
	 */
	private String mailEndWayCd;
	
	/**
	 * 합의권한설정CD
	 */
	private String discussCd;
	
	/**
	 * activityId
	 */
	private String activityId;
	
	/**
	 * linkType
	 */
	private String linkType;

	
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
	 * @return the instanceId
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
	 * @return the apprDocState
	 */
	public String getApprDocState() {
		return apprDocState;
	}

	/**
	 * @param apprDocState the apprDocState to set
	 */
	public void setApprDocState(String apprDocState) {
		this.apprDocState = apprDocState;
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
	 * @return the isAprReceive
	 */
	public String getIsApprReceive() {
		return isApprReceive;
	}

	/**
	 * @param isAprReceive the isAprReceive to set
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
	 * @return the modifyUserId
	 */
	public String getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * @param modifyUserId the modifyUserId to set
	 */
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the apprDocData
	 */
	public String getApprDocData() {
		return apprDocData;
	}

	/**
	 * @param apprDocData the apprDocData to set
	 */
	public void setApprDocData(String apprDocData) {
		this.apprDocData = apprDocData;
	}

	/**
	 * @return the endNo
	 */
	public String getEndNo() {
		return endNo;
	}

	/**
	 * @param endNo the endNo to set
	 */
	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return the baseNo
	 */
	public String getBaseNo() {
		return baseNo;
	}

	/**
	 * @param baseNo the baseNo to set
	 */
	public void setBaseNo(String baseNo) {
		this.baseNo = baseNo;
	}
	
	/**
	 * @return the etcName
	 */
	public String getEtcName() {
		return etcName;
	}

	/**
	 * @param etcName the etcName to set
	 */
	public void setEtcName(String etcName) {
		this.etcName = etcName;
	}

	/**
	 * @return the workerList
	 */
	public List<String> getWorkerList() {
		return workerList;
	}

	/**
	 * @param workerList the workerList to set
	 */
	public void setWorkerList(List<String> workerList) {
		this.workerList = workerList;
	}

	/**
	 * @return the etcName1
	 */
	public String getEtcName1() {
		return etcName1;
	}

	/**
	 * @param etcName1 the etcName1 to set
	 */
	public void setEtcName1(String etcName1) {
		this.etcName1 = etcName1;
	}

	/**
	 * @return the workerList1
	 */
	public List<String> getWorkerList1() {
		return workerList1;
	}

	/**
	 * @param workerList1 the workerList1 to set
	 */
	public void setWorkerList1(List<String> workerList1) {
		this.workerList1 = workerList1;
	}

	/**
	 * @return the fileLinkList
	 */
	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	/**
	 * @param fileLinkList the fileLinkList to set
	 */
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
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
	 * @return the apprType
	 */
	public String getApprType() {
		return apprType;
	}

	/**
	 * @param apprType the apprType to set
	 */
	public void setApprType(String apprType) {
		this.apprType = apprType;
	}

	/**
	 * @return the apprOrder
	 */
	public String getApprOrder() {
		return apprOrder;
	}

	/**
	 * @param apprOrder the apprOrder to set
	 */
	public void setApprOrder(String apprOrder) {
		this.apprOrder = apprOrder;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
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
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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

	/**
	 * @return the jobPositionName
	 */
	public String getJobPositionName() {
		return jobPositionName;
	}

	/**
	 * @param jobPositionName the jobPositionName to set
	 */
	public void setJobPositionName(String jobPositionName) {
		this.jobPositionName = jobPositionName;
	}

	/**
	 * @return the etcName2
	 */
	public String getEtcName2() {
		return etcName2;
	}

	/**
	 * @param etcName2 the etcName2 to set
	 */
	public void setEtcName2(String etcName2) {
		this.etcName2 = etcName2;
	}

	/**
	 * @return the workerList2
	 */
	public List<String> getWorkerList2() {
		return workerList2;
	}

	/**
	 * @param workerList2 the workerList2 to set
	 */
	public void setWorkerList2(List<String> workerList2) {
		this.workerList2 = workerList2;
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
	 * @return the insLogId
	 */
	public String getInsLogId() {
		return insLogId;
	}

	/**
	 * @param insLogId the insLogId to set
	 */
	public void setInsLogId(String insLogId) {
		this.insLogId = insLogId;
	}

	/**
	 * @return the apprDate
	 */
	//public String getApprDate() {
	//	return apprDate;
	//}

	/**
	 * @param apprDate the apprDate to set
	 */
	//public void setApprDate(String apprDate) {
	//	this.apprDate = apprDate;
	//}

	/**
	 * @return the receiveId
	 */
	public String getReceiveId() {
		return receiveId;
	}

	/**
	 * @param receiveId the receiveId to set
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	/**
	 * @return the authUserId
	 */
	public String getAuthUserId() {
		return authUserId;
	}

	/**
	 * @param authUserId the authUserId to set
	 */
	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	/**
	 * @return the apprLineType
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
	 * @return the approvalYn
	 */
	public String getApprovalYn() {
		return approvalYn;
	}

	/**
	 * @param approvalYn the approvalYn to set
	 */
	public void setApprovalYn(String approvalYn) {
		this.approvalYn = approvalYn;
	}

	/**
	 * @return the decisionYn
	 */
	public String getDecisionYn() {
		return decisionYn;
	}

	/**
	 * @param decisionYn the decisionYn to set
	 */
	public void setDecisionYn(String decisionYn) {
		this.decisionYn = decisionYn;
	}

	/**
	 * @return the etcName3
	 */
	public String getEtcName3() {
		return etcName3;
	}

	/**
	 * @param etcName3 the etcName3 to set
	 */
	public void setEtcName3(String etcName3) {
		this.etcName3 = etcName3;
	}

	/**
	 * @return the workerList3
	 */
	public List<String> getWorkerList3() {
		return workerList3;
	}

	/**
	 * @param workerList3 the workerList3 to set
	 */
	public void setWorkerList3(List<String> workerList3) {
		this.workerList3 = workerList3;
	}

	/**
	 * @return the apprRefId
	 */
	public String getApprRefId() {
		return apprRefId;
	}

	/**
	 * @param apprRefId the apprRefId to set
	 */
	public void setApprRefId(String apprRefId) {
		this.apprRefId = apprRefId;
	}

	/**
	 * @return the mailReqCd
	 */
	public String getMailReqCd() {
		return mailReqCd;
	}

	/**
	 * @param mailReqCd the mailReqCd to set
	 */
	public void setMailReqCd(String mailReqCd) {
		this.mailReqCd = mailReqCd;
	}

	/**
	 * @return the mailEndCd
	 */
	public String getMailEndCd() {
		return mailEndCd;
	}

	/**
	 * @param mailEndCd the mailEndCd to set
	 */
	public void setMailEndCd(String mailEndCd) {
		this.mailEndCd = mailEndCd;
	}

	/**
	 * @return the mailReqWayCd
	 */
	public String getMailReqWayCd() {
		return mailReqWayCd;
	}

	/**
	 * @param mailReqWayCd the mailReqWayCd to set
	 */
	public void setMailReqWayCd(String mailReqWayCd) {
		this.mailReqWayCd = mailReqWayCd;
	}

	/**
	 * @return the mailEndWayCd
	 */
	public String getMailEndWayCd() {
		return mailEndWayCd;
	}

	/**
	 * @param mailEndWayCd the mailEndWayCd to set
	 */
	public void setMailEndWayCd(String mailEndWayCd) {
		this.mailEndWayCd = mailEndWayCd;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the linkType
	 */
	public String getLinkType() {
		return linkType;
	}

	/**
	 * @param linkType the linkType to set
	 */
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	/**
	 * @return the discussCd
	 */
	public String getDiscussCd() {
		return discussCd;
	}

	/**
	 * @param discussCd the discussCd to set
	 */
	public void setDiscussCd(String discussCd) {
		this.discussCd = discussCd;
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
	
	

}
