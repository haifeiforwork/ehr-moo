/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.jobReq.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * TODO Javadoc주석작성
 * 
 * @author 신정수
 * @version $Id$
 */

public class JobReq extends BaseObject {

	private static final long serialVersionUID = 8900711193377442735L;

	/* 사용자권한관리(TB_USER_AUTH_MGNT) */
	private String workGbnCd;           //업무구분코드
	private String deptId;              //부서코드
	private String userId;              //사원번호
	private String rsltFileReadYn;      //결과파일읽기권한여부
	private String apprYn;              //승인자여부
	private String useYn;               //사용여부
	
	/* 업무 의뢰 요청서(TB_JOB_REQ_REVIEW) */
	private String jobReqMgntNo;	//업무 의뢰 요청서의 문서관리 번호
	private String jobReqTitle;			//제목
	private String reqEmpNo;	 		//의뢰자사번
	private String reqDeptId;			//의뢰자소속부서코드
	private String reqContactNo;		//연락처
	private String partyName;			//당사자
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   reviewEndDate;       //계약서검토완료시한
	private String reviewEndTxt;        //계약서검토완료시한(String type)
	private String factRelated;			//사실관련
	private String jobRelatedCd;		//업무관련성코드
	private String keyReviewFocus;		//중점검토사항
	private String relatedDataExplain;	//관련자료설명	
	private String legalOpinion;		//법무의견
	private String processStatus;		//진행상태코드(C00008)
	private String apprStsCd;			//품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date apprReqDate;			//품의요청일자
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date apprDate;			//품의확정일자
	private String fileItemId;			//파일항목ID
	private String rejectReason;		//반려사유
	
	/* 업무 의뢰 요청 결재선(TB_JOB_REQ_APPR_LINE) */
	private String reqDraftEmpNo;    //의뢰부서-기안자사번
	private String reqDraftStsCd;    //의뢰부서-기안자품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   reqDraftDate;      //의뢰부서-기안일자
	private String reqReviewEmpNo;   //의뢰부서-검토자사번
	private String reqReviewStsCd;   //의뢰부서-검토자품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   reqReviewDate;     //의뢰부서-검토일자
	private String reqApprEmpNo;     //의뢰부서-승인자사번
	private String reqApprStsCd;     //의뢰부서-승인자품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   reqApprDate;       //의뢰부서-승인일자
	private String rcvDraftEmpNo;    //법무접수-기안자사번
	private String rcvDraftStsCd;       //법무접수-기안자품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   rcvDraftDate;        //법무접수-기안일자
	private String rcvReviewEmpNo;      //법무접수-검토자사번
	private String rcvReviewStsCd;      //법무접수-검토자품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   rcvReviewDate;       //법무접수-검토일자
	private String rcvApprEmpNo;        //법무접수-승인자사번
	private String rcvApprStsCd;        //법무접수-승인자품의상태코드(C00005)
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   rcvApprDate;         //법무접수-승인일자
	private String apprComment;         //승인 주석
	
	/* 업무 의뢰 요청 이력(TB_JOB_REQ_HISTORY) */
	private int    historySeqno;		//이력일련번호		
	private String writeEmpNo;          //작성자사번
	private Date   writeDate;           //작성일시
	private String historyTxt;          //이력내용
	
	/* 업무 의뢰 요청서 결과(dbo.TB_JOB_REQ_RESULT) */
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date   reportDate;          //보고일시
	private String reportDeptId;        //보고부서  
	private String reportEmpNo;         //보고사번 
	private String reviewOpinion;       //검토의견   
	
	/* 정보(IKEP4_EV_USER) */
	private String userName;            //이름
	
	/* 기타 result */
	private String reqUserName;         //의뢰자 이름
	private String managerName;         //담당자 이름
	private String staus;				//상태	
	private String cnt;					//갯수      
	private String rnum;				//번호
	private String processStatusName;	//진행상태 NAME
	private String reqReviewEmpName;	//기안부서 검토자 NAME
	private String reqApprEmpName;    	//기안부서 승인자 NAME
	private String rcvReviewEmpName;	//법무부서 검토자 NAME
	private String rcvApprEmpName;    	//법무부서 승인자 NAME
	private String dateNo;    			//업무 의뢰 요청 문서 관리 번호 앞자리(YYYY-MM)
	private String apprLv;    			//결제 Level
	private String writeEmpName;    	//이력작성자 이름
	private String resultCnt;    		//검토결과 갯수
	private String reqEmpName;    		//접수자 이름
	private String reqDeptName;    		//부서 이름
	private String reportEmpName;    	//보고자 이름
	private String reportDeptName;    	//보고부서 이름
	
	private String sessionEmpNo;    	//접속자 아이디
	private String sessionGoupId;    	//접속자 부서 아이디
	private String historyYn;    		//검토이력 파일여부
	private boolean ecmRoll;    		//ecm
	private String ecmYn;    			//ecm 여부
	private String emailEmpNo;    		//e-mail 사번
	private String processStatusOld;    //세팅전 진행상태코드
	
	
	public static final String CLS_NAME = "com.lgcns.ikep4.approval.collaboration.jobReq.model.JobReq";
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
	
	
	/* getter, setter ============================================ */
	
	public String getWorkGbnCd() {
		return workGbnCd;
	}
	public void setWorkGbnCd(String workGbnCd) {
		this.workGbnCd = workGbnCd;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRsltFileReadYn() {
		return rsltFileReadYn;
	}
	public void setRsltFileReadYn(String rsltFileReadYn) {
		this.rsltFileReadYn = rsltFileReadYn;
	}
	public String getApprYn() {
		return apprYn;
	}
	public void setApprYn(String apprYn) {
		this.apprYn = apprYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getJobReqMgntNo() {
		return jobReqMgntNo;
	}
	public void setJobReqMgntNo(String jobReqMgntNo) {
		this.jobReqMgntNo = jobReqMgntNo;
	}
	public String getJobReqTitle() {
		return jobReqTitle;
	}
	public void setJobReqTitle(String jobReqTitle) {
		this.jobReqTitle = jobReqTitle;
	}
	public String getReqEmpNo() {
		return reqEmpNo;
	}
	public void setReqEmpNo(String reqEmpNo) {
		this.reqEmpNo = reqEmpNo;
	}
	public String getReqDeptId() {
		return reqDeptId;
	}
	public void setReqDeptId(String reqDeptId) {
		this.reqDeptId = reqDeptId;
	}
	public String getReqContactNo() {
		return reqContactNo;
	}
	public void setReqContactNo(String reqContactNo) {
		this.reqContactNo = reqContactNo;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public Date getReviewEndDate() {
		return reviewEndDate;
	}
	public void setReviewEndDate(Date reviewEndDate) {
		this.reviewEndDate = reviewEndDate;
	}
	
	public String getReviewEndTxt() {
		return reviewEndTxt;
	}
	public void setReviewEndTxt(String reviewEndTxt) {
		this.reviewEndTxt = reviewEndTxt;
	}
	public String getFactRelated() {
		return factRelated;
	}
	public void setFactRelated(String factRelated) {
		this.factRelated = factRelated;
	}
	public String getJobRelatedCd() {
		return jobRelatedCd;
	}
	public void setJobRelatedCd(String jobRelatedCd) {
		this.jobRelatedCd = jobRelatedCd;
	}
	public String getKeyReviewFocus() {
		return keyReviewFocus;
	}
	public void setKeyReviewFocus(String keyReviewFocus) {
		this.keyReviewFocus = keyReviewFocus;
	}
	public String getRelatedDataExplain() {
		return relatedDataExplain;
	}
	public void setRelatedDataExplain(String relatedDataExplain) {
		this.relatedDataExplain = relatedDataExplain;
	}
	public String getLegalOpinion() {
		return legalOpinion;
	}
	public void setLegalOpinion(String legalOpinion) {
		this.legalOpinion = legalOpinion;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getApprStsCd() {
		return apprStsCd;
	}
	public void setApprStsCd(String apprStsCd) {
		this.apprStsCd = apprStsCd;
	}
	public Date getApprReqDate() {
		return apprReqDate;
	}
	public void setApprReqDate(Date apprReqDate) {
		this.apprReqDate = apprReqDate;
	}
	public Date getApprDate() {
		return apprDate;
	}
	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}
	public String getFileItemId() {
		return fileItemId;
	}
	public void setFileItemId(String fileItemId) {
		this.fileItemId = fileItemId;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public String getReqDraftEmpNo() {
		return reqDraftEmpNo;
	}
	public void setReqDraftEmpNo(String reqDraftEmpNo) {
		this.reqDraftEmpNo = reqDraftEmpNo;
	}
	public String getReqDraftStsCd() {
		return reqDraftStsCd;
	}
	public void setReqDraftStsCd(String reqDraftStsCd) {
		this.reqDraftStsCd = reqDraftStsCd;
	}
	public Date getReqDraftDate() {
		return reqDraftDate;
	}
	public void setReqDraftDate(Date reqDraftDate) {
		this.reqDraftDate = reqDraftDate;
	}
	public String getReqReviewEmpNo() {
		return reqReviewEmpNo;
	}
	public void setReqReviewEmpNo(String reqReviewEmpNo) {
		this.reqReviewEmpNo = reqReviewEmpNo;
	}
	public String getReqReviewStsCd() {
		return reqReviewStsCd;
	}
	public void setReqReviewStsCd(String reqReviewStsCd) {
		this.reqReviewStsCd = reqReviewStsCd;
	}
	public Date getReqReviewDate() {
		return reqReviewDate;
	}
	public void setReqReviewDate(Date reqReviewDate) {
		this.reqReviewDate = reqReviewDate;
	}
	public String getReqApprEmpNo() {
		return reqApprEmpNo;
	}
	public void setReqApprEmpNo(String reqApprEmpNo) {
		this.reqApprEmpNo = reqApprEmpNo;
	}
	public String getReqApprStsCd() {
		return reqApprStsCd;
	}
	public void setReqApprStsCd(String reqApprStsCd) {
		this.reqApprStsCd = reqApprStsCd;
	}
	public Date getReqApprDate() {
		return reqApprDate;
	}
	public void setReqApprDate(Date reqApprDate) {
		this.reqApprDate = reqApprDate;
	}
	public String getRcvDraftEmpNo() {
		return rcvDraftEmpNo;
	}
	public void setRcvDraftEmpNo(String rcvDraftEmpNo) {
		this.rcvDraftEmpNo = rcvDraftEmpNo;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getRcvDraftStsCd() {
		return rcvDraftStsCd;
	}
	public void setRcvDraftStsCd(String rcvDraftStsCd) {
		this.rcvDraftStsCd = rcvDraftStsCd;
	}
	public Date getRcvDraftDate() {
		return rcvDraftDate;
	}
	public void setRcvDraftDate(Date rcvDraftDate) {
		this.rcvDraftDate = rcvDraftDate;
	}
	public String getRcvReviewEmpNo() {
		return rcvReviewEmpNo;
	}
	public void setRcvReviewEmpNo(String rcvReviewEmpNo) {
		this.rcvReviewEmpNo = rcvReviewEmpNo;
	}
	public String getRcvReviewStsCd() {
		return rcvReviewStsCd;
	}
	public void setRcvReviewStsCd(String rcvReviewStsCd) {
		this.rcvReviewStsCd = rcvReviewStsCd;
	}
	public Date getRcvReviewDate() {
		return rcvReviewDate;
	}
	public void setRcvReviewDate(Date rcvReviewDate) {
		this.rcvReviewDate = rcvReviewDate;
	}
	public String getRcvApprEmpNo() {
		return rcvApprEmpNo;
	}
	public void setRcvApprEmpNo(String rcvApprEmpNo) {
		this.rcvApprEmpNo = rcvApprEmpNo;
	}
	public String getRcvApprStsCd() {
		return rcvApprStsCd;
	}
	public void setRcvApprStsCd(String rcvApprStsCd) {
		this.rcvApprStsCd = rcvApprStsCd;
	}
	public Date getRcvApprDate() {
		return rcvApprDate;
	}
	public void setRcvApprDate(Date rcvApprDate) {
		this.rcvApprDate = rcvApprDate;
	}
	public String getApprComment() {
		return apprComment;
	}
	public void setApprComment(String apprComment) {
		this.apprComment = apprComment;
	}
	public int getHistorySeqno() {
		return historySeqno;
	}
	public void setHistorySeqno(int historySeqno) {
		this.historySeqno = historySeqno;
	}
	public String getWriteEmpNo() {
		return writeEmpNo;
	}
	public void setWriteEmpNo(String writeEmpNo) {
		this.writeEmpNo = writeEmpNo;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getHistoryTxt() {
		return historyTxt;
	}
	public void setHistoryTxt(String historyTxt) {
		this.historyTxt = historyTxt;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getReportDeptId() {
		return reportDeptId;
	}
	public void setReportDeptId(String reportDeptId) {
		this.reportDeptId = reportDeptId;
	}
	public String getReportEmpNo() {
		return reportEmpNo;
	}
	public void setReportEmpNo(String reportEmpNo) {
		this.reportEmpNo = reportEmpNo;
	}
	public String getReviewOpinion() {
		return reviewOpinion;
	}
	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReqUserName() {
		return reqUserName;
	}
	public void setReqUserName(String reqUserName) {
		this.reqUserName = reqUserName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getStaus() {
		return staus;
	}
	public void setStaus(String staus) {
		this.staus = staus;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getProcessStatusName() {
		return processStatusName;
	}
	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}
	public String getReqReviewEmpName() {
		return reqReviewEmpName;
	}
	public void setReqReviewEmpName(String reqReviewEmpName) {
		this.reqReviewEmpName = reqReviewEmpName;
	}
	public String getReqApprEmpName() {
		return reqApprEmpName;
	}
	public void setReqApprEmpName(String reqApprEmpName) {
		this.reqApprEmpName = reqApprEmpName;
	}
	public String getRcvReviewEmpName() {
		return rcvReviewEmpName;
	}
	public void setRcvReviewEmpName(String rcvReviewEmpName) {
		this.rcvReviewEmpName = rcvReviewEmpName;
	}
	public String getRcvApprEmpName() {
		return rcvApprEmpName;
	}
	public void setRcvApprEmpName(String rcvApprEmpName) {
		this.rcvApprEmpName = rcvApprEmpName;
	}
	public String getDateNo() {
		return dateNo;
	}
	public void setDateNo(String dateNo) {
		this.dateNo = dateNo;
	}
	public String getApprLv() {
		return apprLv;
	}
	public void setApprLv(String apprLv) {
		this.apprLv = apprLv;
	}
	public String getWriteEmpName() {
		return writeEmpName;
	}
	public void setWriteEmpName(String writeEmpName) {
		this.writeEmpName = writeEmpName;
	}
	public String getResultCnt() {
		return resultCnt;
	}
	public void setResultCnt(String resultCnt) {
		this.resultCnt = resultCnt;
	}
	public String getReqEmpName() {
		return reqEmpName;
	}
	public void setReqEmpName(String reqEmpName) {
		this.reqEmpName = reqEmpName;
	}
	public String getReqDeptName() {
		return reqDeptName;
	}
	public void setReqDeptName(String reqDeptName) {
		this.reqDeptName = reqDeptName;
	}
	public String getReportEmpName() {
		return reportEmpName;
	}
	public void setReportEmpName(String reportEmpName) {
		this.reportEmpName = reportEmpName;
	}
	public String getReportDeptName() {
		return reportDeptName;
	}
	public void setReportDeptName(String reportDeptName) {
		this.reportDeptName = reportDeptName;
	}
	public String getSessionEmpNo() {
		return sessionEmpNo;
	}
	public void setSessionEmpNo(String sessionEmpNo) {
		this.sessionEmpNo = sessionEmpNo;
	}
	public String getSessionGoupId() {
		return sessionGoupId;
	}
	public void setSessionGoupId(String sessionGoupId) {
		this.sessionGoupId = sessionGoupId;
	}
	public String getHistoryYn() {
		return historyYn;
	}
	public void setHistoryYn(String historyYn) {
		this.historyYn = historyYn;
	}
	public Integer getAttachFileCount() {
		return attachFileCount;
	}
	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}
	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}
	public List<FileData> getFileDataList() {
		return fileDataList;
	}
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}
	public List<FileData> getRsltFileDataList() {
		return rsltFileDataList;
	}
	public void setRsltFileDataList(List<FileData> rsltFileDataList) {
		this.rsltFileDataList = rsltFileDataList;
	}
	public List<FileData> getEcmFileDataList() {
		return ecmFileDataList;
	}
	public void setEcmFileDataList(List<FileData> ecmFileDataList) {
		this.ecmFileDataList = ecmFileDataList;
	}
	public String getEcmFileId() {
		return ecmFileId;
	}
	public void setEcmFileId(String ecmFileId) {
		this.ecmFileId = ecmFileId;
	}
	public String getEcmFileName() {
		return ecmFileName;
	}
	public void setEcmFileName(String ecmFileName) {
		this.ecmFileName = ecmFileName;
	}
	public String getEcmFilePath() {
		return ecmFilePath;
	}
	public void setEcmFilePath(String ecmFilePath) {
		this.ecmFilePath = ecmFilePath;
	}
	public String getEcmFileUrl1() {
		return ecmFileUrl1;
	}
	public void setEcmFileUrl1(String ecmFileUrl1) {
		this.ecmFileUrl1 = ecmFileUrl1;
	}
	public String getEcmFileUrl2() {
		return ecmFileUrl2;
	}
	public void setEcmFileUrl2(String ecmFileUrl2) {
		this.ecmFileUrl2 = ecmFileUrl2;
	}
	public Integer getFileAttachOption() {
		return fileAttachOption;
	}
	public void setFileAttachOption(Integer fileAttachOption) {
		this.fileAttachOption = fileAttachOption;
	}
	public String getAllowType() {
		return allowType;
	}
	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getClsName() {
		return CLS_NAME;
	}
	public static String getItemFileType() {
		return ITEM_FILE_TYPE;
	}
	public static String getAttechedFile() {
		return ATTECHED_FILE;
	}
	public static String getAllFile() {
		return ALL_FILE;
	}
	public boolean isEcmRoll() {
		return ecmRoll;
	}
	public void setEcmRoll(boolean ecmRoll) {
		this.ecmRoll = ecmRoll;
	}
	public String getEcmYn() {
		return ecmYn;
	}
	public void setEcmYn(String ecmYn) {
		this.ecmYn = ecmYn;
	}
	public String getEmailEmpNo() {
		return emailEmpNo;
	}
	public void setEmailEmpNo(String emailEmpNo) {
		this.emailEmpNo = emailEmpNo;
	}
	public String getProcessStatusOld() {
		return processStatusOld;
	}
	public void setProcessStatusOld(String processStatusOld) {
		this.processStatusOld = processStatusOld;
	}
}
