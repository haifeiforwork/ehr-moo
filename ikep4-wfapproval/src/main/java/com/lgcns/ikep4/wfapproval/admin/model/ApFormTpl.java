/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 양식 템플릿 정보
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormTpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApFormTpl extends BaseObject {

	private static final long serialVersionUID = -975115802385150940L;
	
	public static final String EDITOR_FILE = "Y";
	
	public static final String ITEM_TYPE = "APFORMTPL";

	/**
	 *	양식ID
	 */
	private String 	formId;
	
	/**
	 *	프로세스 ID
	 */
	private String 	processId;
	
	/**
	 *	프로세스명
	 */
	private String 	processName;
	
	/**
	 *	프로세스 유형 Modeler:WORKFLOW, User:APPROVAL
	 */
	private String 	processType;
	
	/**
	 *	프로세스명 버젼
	 */
	private String 	processVersion;
	
	/**
	 *	위임설정
	 */
	private String 	authCd;
	
	/**
	 *	결재요청통보
	 */
	private String 	mailReqCd;
	
	/**
	 *	결재완료통보
	 */
	private String 	mailEndCd;
	
	/**
	 *	결재요청통보 방법
	 */
	private String 	mailReqWayCd;
	
	/**
	 *	결재완료통보 방법
	 */
	private String 	mailEndWayCd;
	
	/**
	 *	비정형 양식 구분
	 */
	private String 	isNoneForm;

	/**
	 *	결재사용
	 */
	private String 	isAppr;
	
	/**
	 *	합의사용
	 */
	private String	isDiscuss;
	
	/**
	 *	합의유형
	 */
	private String 	discussCd;
	
	/**
	 *	결재라인수
	 */
	private int 	apprLineCnt;
	
	/**
	 *	합의라인수
	 */
	private int 	discussLineCnt;
	
	/**
	 *	보존연한 유무
	 */
	private String 	isApprPeriod;
	
	/**
	 *	보존연한 타입
	 */
	private String 	apprPeriodCd;
	
	/**
	 *	문서구분 유무
	 */
	private String 	isApprType;
	
	/**
	 *	문서구분 타입
	 */
	private String 	apprTypeCd;
	
	/**
	 *	문서종류 유무
	 */
	private String 	isApprDoc;
	
	/**
	 *	문서종류 타입
	 */
	private String 	apprDocCd;
	
	/**
	 *	문서제목
	 */
	private String 	apprTitle;
	
	/**
	 *	문서제목 고정유무
	 */
	private String 	isApprTitle;
	
	/**
	 *	파일첨부 유무
	 */
	private String 	isApprAttach;
	
	/**
	 *	등록자
	 */
	private String 	registUserId;
	
	/**
	 *	등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date 	registDate;
	
	/**
	 *	수정자
	 */
	private String 	modifyUserId;
	
	/**
	 *	수정일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date 	modifyDate;
	
	/**
	 *	양식 테그데이터
	 */
	private String 	apprFormData;
	
	/**
	 * Edit Link Info
	 */
	private List<FileLink> editorFileLinkList;
    
	/**
	 * Edit File Info
	 */
    private List<FileData> editorFileDataList;
    

	/**
	 * 양식ID
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * 양식ID
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	/**
	 * 프로세스 ID
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * 프로세스 ID
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * 프로세스명
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * 프로세스명
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * 프로세스 유형 Modeler:WORKFLOW, User:APPROVAL
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * 프로세스 유형 Modeler:WORKFLOW, User:APPROVAL
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	/**
	 * 프로세스 버젼
	 * @return the processVersion
	 */
	public String getProcessVersion() {
		return processVersion;
	}

	/**
	 * 프로세스 버젼
	 * @param processVersion the processVersion to set
	 */
	public void setProcessVersion(String processVersion) {
		this.processVersion = processVersion;
	}

	/**
	 * 위임설정
	 * @return the authCd
	 */
	public String getAuthCd() {
		return authCd;
	}

	/**
	 * 위임설정
	 * @param authCd the authCd to set
	 */
	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}

	/**
	 * 결재요청통보
	 * @return the mailReqCd
	 */
	public String getMailReqCd() {
		return mailReqCd;
	}

	/**
	 * 결재요청통보
	 * @param mailReq the mailReqCd to set
	 */
	public void setMailReqCd(String mailReqCd) {
		this.mailReqCd = mailReqCd;
	}

	/**
	 * 결재완료통보
	 * @return the mailEndCd
	 */
	public String getMailEndCd() {
		return mailEndCd;
	}

	/**
	 * 결재완료통보
	 * @param mailEnd the mailEndCd to set
	 */
	public void setMailEndCd(String mailEndCd) {
		this.mailEndCd = mailEndCd;
	}


	/**
	 * 결재요청통보 방법
	 * @return the mailReqWayCd
	 */
	public String getMailReqWayCd() {
		return mailReqWayCd;
	}

	/**
	 * 결재요청통보 방법
	 * @param mailReqWayCd the mailReqWayCd to set
	 */
	public void setMailReqWayCd(String mailReqWayCd) {
		this.mailReqWayCd = mailReqWayCd;
	}

	/**
	 * 결재완료통보 방법
	 * @return the mailEndWayCd
	 */
	public String getMailEndWayCd() {
		return mailEndWayCd;
	}

	/**
	 * 결재완료통보 방법
	 * @param mailEndWayCd the mailEndWayCd to set
	 */
	public void setMailEndWayCd(String mailEndWayCd) {
		this.mailEndWayCd = mailEndWayCd;
	}

	/**
	 * 비정형 양식 구분
	 * @return the isNoneForm
	 */
	public String getIsNoneForm() {
		return isNoneForm;
	}

	/**
	 * 비정형 양식 구분
	 * @param isNoneForm the isNoneForm to set
	 */
	public void setIsNoneForm(String isNoneForm) {
		this.isNoneForm = isNoneForm;
	}

	/**
	 * 결재사용 유무
	 * @return the isAppr
	 */
	public String getIsAppr() {
		return (isAppr==null)?"N":isAppr;
	}

	/**
	 * 결재사용 유무
	 * @param isAppr the isAppr to set
	 */
	public void setIsAppr(String isAppr) {
		this.isAppr = isAppr;
	}

	/**
	 * 합의사용 유무
	 * @return the isDiscuss
	 */
	public String getIsDiscuss() {
		return (isDiscuss==null)?"N":isDiscuss;
	}

	/**
	 * 합의사용 유무
	 * @param isDiscuss the isDiscuss to set
	 */
	public void setIsDiscuss(String isDiscuss) {
		this.isDiscuss = isDiscuss;
	}

	/**
	 * 합의유형
	 * @return the discussCd
	 */
	public String getDiscussCd() {
		return discussCd;
	}

	/**
	 * 합의유형
	 * @param discussCd the discussCd to set
	 */
	public void setDiscussCd(String discussCd) {
		this.discussCd = discussCd;
	}

	/**
	 * 결재라인수
	 * @return the apprLineCnt
	 */
	public int getApprLineCnt() {
		return apprLineCnt;
	}

	/**
	 * 결재라인수
	 * @param apprLineCnt the apprLineCnt to set
	 */
	public void setApprLineCnt(int apprLineCnt) {
		this.apprLineCnt = apprLineCnt;
	}

	/**
	 * 합의라인수
	 * @return the discussLineCnt
	 */
	public int getDiscussLineCnt() {
		return discussLineCnt;
	}

	/**
	 * 합의라인수
	 * @param discussLineCnt the discussLineCnt to set
	 */
	public void setDiscussLineCnt(int discussLineCnt) {
		this.discussLineCnt = discussLineCnt;
	}

	/**
	 * 보존연한 유무
	 * @return the isApprPeriod
	 */
	public String getIsApprPeriod() {
		return isApprPeriod;
	}

	/**
	 * 보존연한 유무
	 * @param isApprPeriod the isApprPeriod to set
	 */
	public void setIsApprPeriod(String isApprPeriod) {
		this.isApprPeriod = isApprPeriod;
	}

	/**
	 * 보존연한 타입
	 * @return the apprPeriodCd
	 */
	public String getApprPeriodCd() {
		return apprPeriodCd;
	}

	/**
	 * 보존연한 타입
	 * @param apprPeriodCd the apprPeriodCd to set
	 */
	public void setApprPeriodCd(String apprPeriodCd) {
		this.apprPeriodCd = apprPeriodCd;
	}

	/**
	 * 문서구분 유무
	 * @return the isApprType
	 */
	public String getIsApprType() {
		return isApprType;
	}

	/**
	 * 문서구분 유무
	 * @param isApprType the isApprType to set
	 */
	public void setIsApprType(String isApprType) {
		this.isApprType = isApprType;
	}

	/**
	 * 문서구분 타입
	 * @return the apprTypeCd
	 */
	public String getApprTypeCd() {
		return apprTypeCd;
	}

	/**
	 * 문서구분 타입
	 * @param apprTypeCd the apprTypeCd to set
	 */
	public void setApprTypeCd(String apprTypeCd) {
		this.apprTypeCd = apprTypeCd;
	}

	/**
	 * 문서종류
	 * @return the isApprDoc
	 */
	public String getIsApprDoc() {
		return isApprDoc;
	}

	/**
	 * 문서종류
	 * @param isApprDoc the isApprDoc to set
	 */
	public void setIsApprDoc(String isApprDoc) {
		this.isApprDoc = isApprDoc;
	}

	/**
	 * 문서종류 타입
	 * @return the apprDocCd
	 */
	public String getApprDocCd() {
		return apprDocCd;
	}

	/**
	 * 문서종류 타입
	 * @param apprDocCd the apprDocCd to set
	 */
	public void setApprDocCd(String apprDocCd) {
		this.apprDocCd = apprDocCd;
	}

	/**
	 * 문서제목
	 * @return the apprTitle
	 */
	public String getApprTitle() {
		return apprTitle;
	}

	/**
	 * 문서제목
	 * @param apprTitle the apprTitle to set
	 */
	public void setApprTitle(String apprTitle) {
		this.apprTitle = apprTitle;
	}

	/**
	 * 문서제목 고정유무
	 * @return the isApprTitle
	 */
	public String getIsApprTitle() {
		return (isApprTitle==null)?"N":isApprTitle;
	}

	/**
	 * 문서제목 고정유무
	 * @param isApprTitle the isApprTitle to set
	 */
	public void setIsApprTitle(String isApprTitle) {
		this.isApprTitle = isApprTitle;
	}

	/**
	 * 파일첨부 유무
	 * @return the isApprAttach
	 */
	public String getIsApprAttach() {
		return isApprAttach;
	}

	/**
	 * 파일첨부 유무
	 * @param isApprAttach the isApprAttach to set
	 */
	public void setIsApprAttach(String isApprAttach) {
		this.isApprAttach = isApprAttach;
	}

	/**
	 * 등록자
	 * @return the registUserId
	 */
	public String getRegistUserId() {
		return registUserId;
	}

	/**
	 * 등록자
	 * @param registUserId the registUserId to set
	 */
	public void setRegistUserId(String registUserId) {
		this.registUserId = registUserId;
	}

	/**
	 * 등록일자
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * 등록일자
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * 수정자
	 * @return the modifyUserId
	 */
	public String getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * 수정자
	 * @param modifyUserId the modifyUserId to set
	 */
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	/**
	 * 수정일자
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 수정일자
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 양식 테그데이터
	 * @return the apprFormData
	 */
	public String getApprFormData() {
		return apprFormData;
	}

	/**
	 * 양식 테그데이터
	 * @param apprFormData the apprFormData to set
	 */
	public void setApprFormData(String apprFormData) {
		this.apprFormData = apprFormData;
	}
	
	/**
	 * @return the editorFileLinkList
	 */
	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	/**
	 * @param editorFileLinkList the editorFileLinkList to set
	 */
	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	/**
	 * @return the editorFileDataList
	 */
	public List<FileData> getEditorFileDataList() {
		return editorFileDataList;
	}

	/**
	 * @param editorFileDataList the editorFileDataList to set
	 */
	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}
}
