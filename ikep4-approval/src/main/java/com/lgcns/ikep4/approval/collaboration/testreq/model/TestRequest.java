/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.testreq.model;

import java.util.List;

import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;

/**
 * 시험의뢰서 VO
 * 
 * @author pjh
 * @version $Id$
 */
public class TestRequest extends BaseObject {
	
	public static final String CLS_NAME = "com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest";
	/*------------------------------ 파일 -------------------------------------------*/
	
	/** 아이템 타입. */
	public static final String ITEM_FILE_TYPE = "AP";
	
	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";
	
	/** 모두 파일. */
	public static final String ALL_FILE = "";
	
	/** 첨부파일수. */
	private Integer attachFileCount;
	
	/** 첨부파일 링크 리스트. */
	private List<FileLink> fileLinkList;
	
	/** 첨부파일 리스트. */
	private List<FileData> fileDataList;
	
	/** 결과파일 리스트. */
	private List<FileData> rsltFileDataList;
	
	/** Ecm 파일 변수 */
	private List<FileData> ecmFileDataList;
	private String ecmFileId;
	private String ecmFileName;
	private String ecmFilePath;
	private String ecmFileUrl1;
	private String ecmFileUrl2;
	
	/** 파일 업로드 옵션 */
	private Integer fileAttachOption = 1;
	
	/** 파일 업로드 허용 타입 */
	@Size(min=1, max=100)
	private String allowType;
	
	/** 허용 첨부파일 크기 */
	private Long fileSize;
	
	/**
	 * 순번
	 */
	private int rNum;
	
	/*------------------------------- 시험 의뢰서 정보 -----------------------------*/
	/**
	 * 시험의뢰서 관리번호
	 */
	private String trMgntNo;
	
	/**
	 * 회사 체크 값
	 */
	private String companyChkVal;
	
	/**
	 * 품목 코드(C00016)
	 */
	private String itemKindCd;
	
	/**
	 * 품목코드명
	 */
	private String itemKindNm;
	
	/**
	 * 작성자 사원번호
	 */
	private String writeEmpNo;
	
	/**
	 * 작성자 사원명
	 */
	private String writeEmpNm;
	
	/**
	 * 작성일자
	 */
	private String writeDate;
	
	/**
	 * 작성자 부서코드
	 */
	private String writeDeptId;
	
	/**
	 * 작성자 부서명
	 */
	private String writeDeptNm;
	
	/**
	 * 수신자 사번
	 */
	private String rcvEmpNo;
	
	/**
	 * 수신자 명
	 */
	private String rcvEmpNm;
	
	/**
	 * 수신자 부서코드
	 */
	private String rcvDeptId;
	
	/**
	 * 수신자 부서명
	 */
	private String rcvDeptNm;
	
	/**
	 * 시험의뢰서 제목
	 */
	private String testReqTitle;
	
	/**
	 * 요구 일정(C00013)
	 */
	private String reqScheduleCd;
	
	/**
	 * 요구일정 기타내역
	 */
	private String reqScheduleEtcTxt;
	
	/**
	 * 시험의뢰내용
	 */
	private String reqDetail;
	
	/**
	 * 파일항목ID
	 */
	private String fileItemId;
	
	/**
	 * 완료예정일
	 */
	private String completePlanDate;
	
	/**
	 * 주관부서 의견
	 */
	private String resDeptOpinion;
	
	/**
	 * 진행 상태(C00014)
	 */
	private String processStatus;
	
	/**
	 * 품의상태
	 */
	private String apprStsCd;
	
	/**
	 * 품의요청일자
	 */
	private String apprReqDate;
	
	/**
	 * 품의확정일자
	 */
	private String apprDate;
	
	/**
	 * 반려사유
	 */
	private String rejectReason;
	
	/**
	 * 추가기재사항
	 */
	private String addWriteDetail;
	
	/**
	 * 임원여부
	 */
	private String imwonYn;
	
	
	/*------------------------------- 시험 의뢰서 결재선 정보 -----------------------------*/
	
	/**
	 * 의뢰부서-기안자사번
	 */
	private String reqDraftEmpNo;
	
	/**
	 * 의뢰부서-기안자명
	 */
	private String reqDraftEmpNm;
	
	/**
	 * 의뢰부서-기안자품의상태코드( 공통코드 C00005)
	 */
	private String reqDraftStsCd;
	
	/**
	 * 	의뢰부서-기안일자	
	 */
	private String reqDraftDate;
	
	/**
	 * 의뢰부서-검토자사번
	 */
	private String reqReviewEmpNo;
	
	/**
	 * 의뢰부서-검토자명
	 */
	private String reqReviewEmpNm;
	
	/**
	 * 의뢰부서-검토자품의상태코드( 공통코드 C00005)
	 */
	private String reqReviewStsCd;
	
	/**
	 * 의뢰부서-검토일자
	 */
	private String reqReviewDate;
	
	/**
	 * 의뢰부서-승인자사번
	 */
	private String reqApprEmpNo;
	
	/**
	 * 의뢰부서-승인자명
	 */
	private String reqApprEmpNm;
	
	/**
	 * 의뢰부서-승인자품의상태코드(  공통코드 C00005)
	 */
	private String reqApprStsCd;
	
	/**
	 * 의뢰부서-승인일자
	 */
	private String reqApprDate;
	
	/**
	 * 주관부서-기안자사번
	 */
	private String rcvDraftEmpNo;
	
	/**
	 * 주관부서-기안자명
	 */
	private String rcvDraftEmpNm;
	
	/**
	 * 주관부서-기안자품의상태코드(C00005)
	 */
	private String rcvDraftStsCd;
	
	/**
	 * 주관부서-기안일자
	 */
	private String rcvDraftDate;
	
	/**
	 * 주관부서-검토자사번
	 */
	private String rcvReviewEmpNo;
	
	/**
	 * 주관부서-검토자명
	 */
	private String rcvReviewEmpNm;
	
	/**
	 * 주관부서-검토자품의상태코드(C00005)
	 */
	private String rcvReviewStsCd;
	
	/**
	 * 주관부서-검토일자
	 */
	private String rcvReviewDate;
	
	/**
	 * 주관부서-승인자사번
	 */
	private String rcvApprEmpNo;
	
	/**
	 * 주관부서-승인자명
	 */
	private String rcvApprEmpNm;
	
	/**
	 * 주관부서-승인자품의상태코드(C00005)
	 */
	private String rcvApprStsCd;
	
	/**
	 * 주관부서-승인일자
	 */
	private String rcvApprDate;
	
	/**
	 * 품의주석
	 */
	private String apprComment;
	
	/* ----------- 그외 */
	
	/**
	 * 파라미터( 승인, 부결)
	 */
	private String approveStsCd;
	
	/**
	 * 프로세스 그룹번호
	 */
	private String processGroupNo;
	
	/**
	 * 대상자 명
	 */
	private String userName;
	
	/**
	 * 이메일 주소
	 */
	private String email;
	
	/**
	 * 조회권한
	 */
	private String authYn;
	
	/**
	 * 메일 발신자
	 */
	private String senderEmail;

	/**
	 * @return the attachFileCount
	 */
	public Integer getAttachFileCount() {
		return attachFileCount;
	}

	/**
	 * @param attachFileCount the attachFileCount to set
	 */
	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
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
	 * @return the fileDataList
	 */
	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	/**
	 * @param fileDataList the fileDataList to set
	 */
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * @return the rsltFileDataList
	 */
	public List<FileData> getRsltFileDataList() {
		return rsltFileDataList;
	}

	/**
	 * @param rsltFileDataList the rsltFileDataList to set
	 */
	public void setRsltFileDataList(List<FileData> rsltFileDataList) {
		this.rsltFileDataList = rsltFileDataList;
	}

	/**
	 * @return the ecmFileDataList
	 */
	public List<FileData> getEcmFileDataList() {
		return ecmFileDataList;
	}

	/**
	 * @param ecmFileDataList the ecmFileDataList to set
	 */
	public void setEcmFileDataList(List<FileData> ecmFileDataList) {
		this.ecmFileDataList = ecmFileDataList;
	}

	/**
	 * @return the ecmFileId
	 */
	public String getEcmFileId() {
		return ecmFileId;
	}

	/**
	 * @param ecmFileId the ecmFileId to set
	 */
	public void setEcmFileId(String ecmFileId) {
		this.ecmFileId = ecmFileId;
	}

	/**
	 * @return the ecmFileName
	 */
	public String getEcmFileName() {
		return ecmFileName;
	}

	/**
	 * @param ecmFileName the ecmFileName to set
	 */
	public void setEcmFileName(String ecmFileName) {
		this.ecmFileName = ecmFileName;
	}

	/**
	 * @return the ecmFilePath
	 */
	public String getEcmFilePath() {
		return ecmFilePath;
	}

	/**
	 * @param ecmFilePath the ecmFilePath to set
	 */
	public void setEcmFilePath(String ecmFilePath) {
		this.ecmFilePath = ecmFilePath;
	}

	/**
	 * @return the ecmFileUrl1
	 */
	public String getEcmFileUrl1() {
		return ecmFileUrl1;
	}

	/**
	 * @param ecmFileUrl1 the ecmFileUrl1 to set
	 */
	public void setEcmFileUrl1(String ecmFileUrl1) {
		this.ecmFileUrl1 = ecmFileUrl1;
	}

	/**
	 * @return the ecmFileUrl2
	 */
	public String getEcmFileUrl2() {
		return ecmFileUrl2;
	}

	/**
	 * @param ecmFileUrl2 the ecmFileUrl2 to set
	 */
	public void setEcmFileUrl2(String ecmFileUrl2) {
		this.ecmFileUrl2 = ecmFileUrl2;
	}

	/**
	 * @return the rNum
	 */
	public int getrNum() {
		return rNum;
	}

	/**
	 * @param rNum the rNum to set
	 */
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}

	/**
	 * @return the trMgntNo
	 */
	public String getTrMgntNo() {
		return trMgntNo;
	}

	/**
	 * @param trMgntNo the trMgntNo to set
	 */
	public void setTrMgntNo(String trMgntNo) {
		this.trMgntNo = trMgntNo;
	}

	/**
	 * @return the companyChkVal
	 */
	public String getCompanyChkVal() {
		return companyChkVal;
	}

	/**
	 * @param companyChkVal the companyChkVal to set
	 */
	public void setCompanyChkVal(String companyChkVal) {
		this.companyChkVal = companyChkVal;
	}

	/**
	 * @return the itemKindCd
	 */
	public String getItemKindCd() {
		return itemKindCd;
	}

	/**
	 * @param itemKindCd the itemKindCd to set
	 */
	public void setItemKindCd(String itemKindCd) {
		this.itemKindCd = itemKindCd;
	}

	/**
	 * @return the itemKindNm
	 */
	public String getItemKindNm() {
		return itemKindNm;
	}

	/**
	 * @param itemKindNm the itemKindNm to set
	 */
	public void setItemKindNm(String itemKindNm) {
		this.itemKindNm = itemKindNm;
	}

	/**
	 * @return the writeEmpNo
	 */
	public String getWriteEmpNo() {
		return writeEmpNo;
	}

	/**
	 * @param writeEmpNo the writeEmpNo to set
	 */
	public void setWriteEmpNo(String writeEmpNo) {
		this.writeEmpNo = writeEmpNo;
	}

	/**
	 * @return the writeEmpNm
	 */
	public String getWriteEmpNm() {
		return writeEmpNm;
	}

	/**
	 * @param writeEmpNm the writeEmpNm to set
	 */
	public void setWriteEmpNm(String writeEmpNm) {
		this.writeEmpNm = writeEmpNm;
	}

	/**
	 * @return the writeDate
	 */
	public String getWriteDate() {
		return writeDate;
	}

	/**
	 * @param writeDate the writeDate to set
	 */
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	/**
	 * @return the writeDeptId
	 */
	public String getWriteDeptId() {
		return writeDeptId;
	}

	/**
	 * @param writeDeptId the writeDeptId to set
	 */
	public void setWriteDeptId(String writeDeptId) {
		this.writeDeptId = writeDeptId;
	}

	/**
	 * @return the writeDeptNm
	 */
	public String getWriteDeptNm() {
		return writeDeptNm;
	}

	/**
	 * @param writeDeptNm the writeDeptNm to set
	 */
	public void setWriteDeptNm(String writeDeptNm) {
		this.writeDeptNm = writeDeptNm;
	}

	/**
	 * @return the rcvEmpNo
	 */
	public String getRcvEmpNo() {
		return rcvEmpNo;
	}

	/**
	 * @param rcvEmpNo the rcvEmpNo to set
	 */
	public void setRcvEmpNo(String rcvEmpNo) {
		this.rcvEmpNo = rcvEmpNo;
	}

	/**
	 * @return the rcvEmpNm
	 */
	public String getRcvEmpNm() {
		return rcvEmpNm;
	}

	/**
	 * @param rcvEmpNm the rcvEmpNm to set
	 */
	public void setRcvEmpNm(String rcvEmpNm) {
		this.rcvEmpNm = rcvEmpNm;
	}

	/**
	 * @return the rcvDeptId
	 */
	public String getRcvDeptId() {
		return rcvDeptId;
	}

	/**
	 * @param rcvDeptId the rcvDeptId to set
	 */
	public void setRcvDeptId(String rcvDeptId) {
		this.rcvDeptId = rcvDeptId;
	}

	/**
	 * @return the rcvDeptNm
	 */
	public String getRcvDeptNm() {
		return rcvDeptNm;
	}

	/**
	 * @param rcvDeptNm the rcvDeptNm to set
	 */
	public void setRcvDeptNm(String rcvDeptNm) {
		this.rcvDeptNm = rcvDeptNm;
	}

	/**
	 * @return the testReqTitle
	 */
	public String getTestReqTitle() {
		return testReqTitle;
	}

	/**
	 * @param testReqTitle the testReqTitle to set
	 */
	public void setTestReqTitle(String testReqTitle) {
		this.testReqTitle = testReqTitle;
	}

	/**
	 * @return the reqScheduleCd
	 */
	public String getReqScheduleCd() {
		return reqScheduleCd;
	}

	/**
	 * @param reqScheduleCd the reqScheduleCd to set
	 */
	public void setReqScheduleCd(String reqScheduleCd) {
		this.reqScheduleCd = reqScheduleCd;
	}

	/**
	 * @return the reqScheduleEtcTxt
	 */
	public String getReqScheduleEtcTxt() {
		return reqScheduleEtcTxt;
	}

	/**
	 * @param reqScheduleEtcTxt the reqScheduleEtcTxt to set
	 */
	public void setReqScheduleEtcTxt(String reqScheduleEtcTxt) {
		this.reqScheduleEtcTxt = reqScheduleEtcTxt;
	}

	/**
	 * @return the reqDetail
	 */
	public String getReqDetail() {
		return reqDetail;
	}

	/**
	 * @param reqDetail the reqDetail to set
	 */
	public void setReqDetail(String reqDetail) {
		this.reqDetail = reqDetail;
	}

	/**
	 * @return the fileItemId
	 */
	public String getFileItemId() {
		return fileItemId;
	}

	/**
	 * @param fileItemId the fileItemId to set
	 */
	public void setFileItemId(String fileItemId) {
		this.fileItemId = fileItemId;
	}

	/**
	 * @return the completePlanDate
	 */
	public String getCompletePlanDate() {
		return completePlanDate;
	}

	/**
	 * @param completePlanDate the completePlanDate to set
	 */
	public void setCompletePlanDate(String completePlanDate) {
		this.completePlanDate = completePlanDate;
	}

	/**
	 * @return the resDeptOpinion
	 */
	public String getResDeptOpinion() {
		return resDeptOpinion;
	}

	/**
	 * @param resDeptOpinion the resDeptOpinion to set
	 */
	public void setResDeptOpinion(String resDeptOpinion) {
		this.resDeptOpinion = resDeptOpinion;
	}

	/**
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the apprStsCd
	 */
	public String getApprStsCd() {
		return apprStsCd;
	}

	/**
	 * @param apprStsCd the apprStsCd to set
	 */
	public void setApprStsCd(String apprStsCd) {
		this.apprStsCd = apprStsCd;
	}

	/**
	 * @return the apprReqDate
	 */
	public String getApprReqDate() {
		return apprReqDate;
	}

	/**
	 * @param apprReqDate the apprReqDate to set
	 */
	public void setApprReqDate(String apprReqDate) {
		this.apprReqDate = apprReqDate;
	}

	/**
	 * @return the apprDate
	 */
	public String getApprDate() {
		return apprDate;
	}

	/**
	 * @param apprDate the apprDate to set
	 */
	public void setApprDate(String apprDate) {
		this.apprDate = apprDate;
	}

	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the addWriteDetail
	 */
	public String getAddWriteDetail() {
		return addWriteDetail;
	}

	/**
	 * @param addWriteDetail the addWriteDetail to set
	 */
	public void setAddWriteDetail(String addWriteDetail) {
		this.addWriteDetail = addWriteDetail;
	}

	/**
	 * @return the reqDraftEmpNo
	 */
	public String getReqDraftEmpNo() {
		return reqDraftEmpNo;
	}

	/**
	 * @param reqDraftEmpNo the reqDraftEmpNo to set
	 */
	public void setReqDraftEmpNo(String reqDraftEmpNo) {
		this.reqDraftEmpNo = reqDraftEmpNo;
	}

	/**
	 * @return the reqDraftEmpNm
	 */
	public String getReqDraftEmpNm() {
		return reqDraftEmpNm;
	}

	/**
	 * @param reqDraftEmpNm the reqDraftEmpNm to set
	 */
	public void setReqDraftEmpNm(String reqDraftEmpNm) {
		this.reqDraftEmpNm = reqDraftEmpNm;
	}

	/**
	 * @return the reqDraftStsCd
	 */
	public String getReqDraftStsCd() {
		return reqDraftStsCd;
	}

	/**
	 * @param reqDraftStsCd the reqDraftStsCd to set
	 */
	public void setReqDraftStsCd(String reqDraftStsCd) {
		this.reqDraftStsCd = reqDraftStsCd;
	}

	/**
	 * @return the reqDraftDate
	 */
	public String getReqDraftDate() {
		return reqDraftDate;
	}

	/**
	 * @param reqDraftDate the reqDraftDate to set
	 */
	public void setReqDraftDate(String reqDraftDate) {
		this.reqDraftDate = reqDraftDate;
	}

	/**
	 * @return the reqReviewEmpNo
	 */
	public String getReqReviewEmpNo() {
		return reqReviewEmpNo;
	}

	/**
	 * @param reqReviewEmpNo the reqReviewEmpNo to set
	 */
	public void setReqReviewEmpNo(String reqReviewEmpNo) {
		this.reqReviewEmpNo = reqReviewEmpNo;
	}

	/**
	 * @return the reqReviewEmpNm
	 */
	public String getReqReviewEmpNm() {
		return reqReviewEmpNm;
	}

	/**
	 * @param reqReviewEmpNm the reqReviewEmpNm to set
	 */
	public void setReqReviewEmpNm(String reqReviewEmpNm) {
		this.reqReviewEmpNm = reqReviewEmpNm;
	}

	/**
	 * @return the reqReviewStsCd
	 */
	public String getReqReviewStsCd() {
		return reqReviewStsCd;
	}

	/**
	 * @param reqReviewStsCd the reqReviewStsCd to set
	 */
	public void setReqReviewStsCd(String reqReviewStsCd) {
		this.reqReviewStsCd = reqReviewStsCd;
	}

	/**
	 * @return the reqReviewDate
	 */
	public String getReqReviewDate() {
		return reqReviewDate;
	}

	/**
	 * @param reqReviewDate the reqReviewDate to set
	 */
	public void setReqReviewDate(String reqReviewDate) {
		this.reqReviewDate = reqReviewDate;
	}

	/**
	 * @return the reqApprEmpNo
	 */
	public String getReqApprEmpNo() {
		return reqApprEmpNo;
	}

	/**
	 * @param reqApprEmpNo the reqApprEmpNo to set
	 */
	public void setReqApprEmpNo(String reqApprEmpNo) {
		this.reqApprEmpNo = reqApprEmpNo;
	}

	/**
	 * @return the reqApprEmpNm
	 */
	public String getReqApprEmpNm() {
		return reqApprEmpNm;
	}

	/**
	 * @param reqApprEmpNm the reqApprEmpNm to set
	 */
	public void setReqApprEmpNm(String reqApprEmpNm) {
		this.reqApprEmpNm = reqApprEmpNm;
	}

	/**
	 * @return the reqApprStsCd
	 */
	public String getReqApprStsCd() {
		return reqApprStsCd;
	}

	/**
	 * @param reqApprStsCd the reqApprStsCd to set
	 */
	public void setReqApprStsCd(String reqApprStsCd) {
		this.reqApprStsCd = reqApprStsCd;
	}

	/**
	 * @return the reqApprDate
	 */
	public String getReqApprDate() {
		return reqApprDate;
	}

	/**
	 * @param reqApprDate the reqApprDate to set
	 */
	public void setReqApprDate(String reqApprDate) {
		this.reqApprDate = reqApprDate;
	}

	/**
	 * @return the rcvDraftEmpNo
	 */
	public String getRcvDraftEmpNo() {
		return rcvDraftEmpNo;
	}

	/**
	 * @param rcvDraftEmpNo the rcvDraftEmpNo to set
	 */
	public void setRcvDraftEmpNo(String rcvDraftEmpNo) {
		this.rcvDraftEmpNo = rcvDraftEmpNo;
	}

	/**
	 * @return the rcvDraftEmpNm
	 */
	public String getRcvDraftEmpNm() {
		return rcvDraftEmpNm;
	}

	/**
	 * @param rcvDraftEmpNm the rcvDraftEmpNm to set
	 */
	public void setRcvDraftEmpNm(String rcvDraftEmpNm) {
		this.rcvDraftEmpNm = rcvDraftEmpNm;
	}

	/**
	 * @return the rcvDraftStsCd
	 */
	public String getRcvDraftStsCd() {
		return rcvDraftStsCd;
	}

	/**
	 * @param rcvDraftStsCd the rcvDraftStsCd to set
	 */
	public void setRcvDraftStsCd(String rcvDraftStsCd) {
		this.rcvDraftStsCd = rcvDraftStsCd;
	}

	/**
	 * @return the rcvDraftDate
	 */
	public String getRcvDraftDate() {
		return rcvDraftDate;
	}

	/**
	 * @param rcvDraftDate the rcvDraftDate to set
	 */
	public void setRcvDraftDate(String rcvDraftDate) {
		this.rcvDraftDate = rcvDraftDate;
	}

	/**
	 * @return the rcvReviewEmpNo
	 */
	public String getRcvReviewEmpNo() {
		return rcvReviewEmpNo;
	}

	/**
	 * @param rcvReviewEmpNo the rcvReviewEmpNo to set
	 */
	public void setRcvReviewEmpNo(String rcvReviewEmpNo) {
		this.rcvReviewEmpNo = rcvReviewEmpNo;
	}

	/**
	 * @return the rcvReviewEmpNm
	 */
	public String getRcvReviewEmpNm() {
		return rcvReviewEmpNm;
	}

	/**
	 * @param rcvReviewEmpNm the rcvReviewEmpNm to set
	 */
	public void setRcvReviewEmpNm(String rcvReviewEmpNm) {
		this.rcvReviewEmpNm = rcvReviewEmpNm;
	}

	/**
	 * @return the rcvReviewStsCd
	 */
	public String getRcvReviewStsCd() {
		return rcvReviewStsCd;
	}

	/**
	 * @param rcvReviewStsCd the rcvReviewStsCd to set
	 */
	public void setRcvReviewStsCd(String rcvReviewStsCd) {
		this.rcvReviewStsCd = rcvReviewStsCd;
	}

	/**
	 * @return the rcvReviewDate
	 */
	public String getRcvReviewDate() {
		return rcvReviewDate;
	}

	/**
	 * @param rcvReviewDate the rcvReviewDate to set
	 */
	public void setRcvReviewDate(String rcvReviewDate) {
		this.rcvReviewDate = rcvReviewDate;
	}

	/**
	 * @return the rcvApprEmpNo
	 */
	public String getRcvApprEmpNo() {
		return rcvApprEmpNo;
	}

	/**
	 * @param rcvApprEmpNo the rcvApprEmpNo to set
	 */
	public void setRcvApprEmpNo(String rcvApprEmpNo) {
		this.rcvApprEmpNo = rcvApprEmpNo;
	}

	/**
	 * @return the rcvApprEmpNm
	 */
	public String getRcvApprEmpNm() {
		return rcvApprEmpNm;
	}

	/**
	 * @param rcvApprEmpNm the rcvApprEmpNm to set
	 */
	public void setRcvApprEmpNm(String rcvApprEmpNm) {
		this.rcvApprEmpNm = rcvApprEmpNm;
	}

	/**
	 * @return the rcvApprStsCd
	 */
	public String getRcvApprStsCd() {
		return rcvApprStsCd;
	}

	/**
	 * @param rcvApprStsCd the rcvApprStsCd to set
	 */
	public void setRcvApprStsCd(String rcvApprStsCd) {
		this.rcvApprStsCd = rcvApprStsCd;
	}

	/**
	 * @return the rcvApprDate
	 */
	public String getRcvApprDate() {
		return rcvApprDate;
	}

	/**
	 * @param rcvApprDate the rcvApprDate to set
	 */
	public void setRcvApprDate(String rcvApprDate) {
		this.rcvApprDate = rcvApprDate;
	}

	/**
	 * @return the apprComment
	 */
	public String getApprComment() {
		return apprComment;
	}

	/**
	 * @param apprComment the apprComment to set
	 */
	public void setApprComment(String apprComment) {
		this.apprComment = apprComment;
	}

	/**
	 * @return the approveStsCd
	 */
	public String getApproveStsCd() {
		return approveStsCd;
	}

	/**
	 * @param approveStsCd the approveStsCd to set
	 */
	public void setApproveStsCd(String approveStsCd) {
		this.approveStsCd = approveStsCd;
	}

	/**
	 * @return the processGroupNo
	 */
	public String getProcessGroupNo() {
		return processGroupNo;
	}

	/**
	 * @param processGroupNo the processGroupNo to set
	 */
	public void setProcessGroupNo(String processGroupNo) {
		this.processGroupNo = processGroupNo;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fileAttachOption
	 */
	public Integer getFileAttachOption() {
		return fileAttachOption;
	}

	/**
	 * @param fileAttachOption the fileAttachOption to set
	 */
	public void setFileAttachOption(Integer fileAttachOption) {
		this.fileAttachOption = fileAttachOption;
	}

	/**
	 * @return the allowType
	 */
	public String getAllowType() {
		return allowType;
	}

	/**
	 * @param allowType the allowType to set
	 */
	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the authYn
	 */
	public String getAuthYn() {
		return authYn;
	}

	/**
	 * @param authYn the authYn to set
	 */
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}

	/**
	 * @return the imwonYn
	 */
	public String getImwonYn() {
		return imwonYn;
	}

	/**
	 * @param imwonYn the imwonYn to set
	 */
	public void setImwonYn(String imwonYn) {
		this.imwonYn = imwonYn;
	}

	/**
	 * @return the senderEmail
	 */
	public String getSenderEmail() {
		return senderEmail;
	}

	/**
	 * @param senderEmail the senderEmail to set
	 */
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
}
