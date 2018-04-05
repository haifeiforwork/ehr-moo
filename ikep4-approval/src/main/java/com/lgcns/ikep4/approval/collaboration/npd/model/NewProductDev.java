/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.npd.model;

import java.util.List;

import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;

/**
 * 신제품관리 VO
 * 
 * @author pjh
 * @version $Id$
 */
public class NewProductDev extends BaseObject {
	
	public static final String CLS_NAME = "com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev";
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
	
	/*------------------------------- 개발검토 의뢰서 정보 -----------------------------*/
	/**
	 * 관리번호
	 */
	private String mgntNo;
	
	/**
	 * 회사코드
	 */
	private String companyCode;
	
	/**
	 * 분류번호
	 */
	private String divisionNo;
	
	/**
	 * 의뢰부서 담당자사번
	 */
	private String reqEmpNo;
	
	/**
	 * 의뢰부서 담당자 명
	 */
	private String reqEmpNm;
	
	/**
	 * 의뢰일자
	 */
	private String reqDate;
	
	/**
	 * 의뢰부서
	 */
	private String reqDeptId;
	
	/**
	 * 의뢰부서명
	 */
	private String reqDeptNm;
	
	/**
	 * TCS팀 ('51000651')
	 */
	private String reqRcvDeptId;
	
	/**
	 * TCS팀 ('51000651') 명
	 */
	private String reqRcvDeptNm;
	
	/**
	 * TCS팀 담당자사번
	 */
	private String fstReviewEmpNo;
	
	/**
	 * TCS팀 담당자명
	 */
	private String fstReviewEmpNm;
	
	/**
	 * 1차검토완료일
	 */
	private String fstReviewDate;
	
	/**
	 * 1차검토부서
	 */
	private String fstReviewDeptId;
	
	/**
	 * 1차검토부서명
	 */
	private String fstReviewDeptNm;
	
	/**
	 * 2차검토부서
	 */
	private String sndReviewDeptId;
	
	/**
	 * 2차검토부서
	 */
	private String sndReviewDeptNm;
	
	/**
	 * 제품명
	 */
	private String productName;
	
	/**
	 * 예상수량
	 */
	private String estQty;
	
	/**
	 * 예상판가
	 */
	private String estPrice;
	
	/**
	 * 규격
	 */
	private String standard;
	
	/**
	 * 제품용도
	 */
	private String productUsage;
	
	/**
	 * 고객사명
	 */
	private String custName;
	
	/**
	 * 비교요청샘플
	 */
	private String compAskSample;
	
	/**
	 * 파일항목ID
	 */
	private String reqFileItemId;
	
	/**
	 * 의뢰목적
	 */
	private String reqPurpose;
	
	/**
	 * 요청사항( 공통코드 C00003 )
	 */
	private String reqItemCd;
	
	/**
	 * 요청사항기타내역
	 */
	private String reqItemEtcTxt;
	
	/**
	 * 요구일정 코드( 공통코드 C00004 )	
	 */
	private String reqScheduleCd;
	
	/**
	 * 요구일정기타일자
	 */
	private String reqScheduleEtcDate;
	
	/**
	 * 요구일정한계일자
	 */
	private String reqScheduleLimitDate;
	
	/**
	 * 1.분석 목적 및 배경. 가.분석 목적
	 */
	private String analysisPurpose;
	
	/**
	 * 1.분석 목적 및 배경. 나.영업 정보 (고객사/시장 정보, 요청사항 입력)
	 */
	private String businessInfo;
	
	/**
	 * 2.분석 요청 사항. 가.품질 분석 항목
	 */
	private String qualityAnalysis;
	
	/**
	 * 2.분석 요청 사항. 나.기타 요청사항	
	 */
	private String etcRequest;
	
	/**
	 * 2차 검토 부서 진행 의견
	 */
	private String sndReviewOpinion;
	
	/**
	 * 2차 검토 부서 결과
	 */
	private String sndRsltReviewResult;
	
	/**
	 * 2차 검토 부서 결과
	 */
	private String sndReviewResult;
	
	/**
	 * 결과파일항목ID
	 */
	private String rsltFileItemId;
	
	/**
	 * 진행상태코드( 공통코드 C00002)
	 */
	private String processStatus;
	
	/**
	 * 품의상태코드( 공통코드 C00005)
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
	
	/*------------------------------- 개발 검토의뢰서 결재선 정보 -----------------------------*/
	
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
	 * TCS-기안자사번
	 */
	private String tcsDraftEmpNo;
	
	/**
	 * TCS-기안자명
	 */
	private String tcsDraftEmpNm;
	
	/**
	 * TCS-기안자품의상태코드(  공통코드 C00005)
	 */
	private String tcsDraftStsCd;
	
	/**
	 * TCS-기안일자
	 */
	private String tcsDraftDate;
	
	/**
	 * TCS-검토자사번
	 */
	private String tcsReviewEmpNo;
	
	/**
	 * TCS-검토자명
	 */
	private String tcsReviewEmpNm;
	
	/**
	 * TCS-검토자품의상태코드(  공통코드 C00005)
	 */
	private String tcsReviewStsCd;
	
	/**
	 * TCS-검토일자
	 */
	private String tcsReviewDate;
	
	/**
	 * TCS-승인자사번
	 */
	private String tcsApprEmpNo;
	
	/**
	 * TCS-승인자명
	 */
	private String tcsApprEmpNm;
	
	/**
	 * TCS-승인자품의상태코드(  공통코드 C00005)
	 */
	private String tcsApprStsCd;
	
	/**
	 * TCS-승인일자
	 */
	private String tcsApprDate;
	
	/**
	 * 2차검토부서접수-기안자사번
	 */
	private String sndRcvDraftEmpNo;
	/**
	 * 2차검토부서접수-기안자명
	 */
	private String sndRcvDraftEmpNm;
	
	/**
	 * 2차검토부서접수-기안자품의상태코드( 공통코드 C00005)
	 */
	private String sndRcvDraftStsCd;
	
	/**
	 * 2차검토부서접수-기안일자
	 */
	private String sndRcvDraftDate;
	
	/**
	 * 2차검토부서접수-검토자사번
	 */
	private String sndRcvReviewEmpNo;
	
	/**
	 * 2차검토부서접수-검토자명
	 */
	private String sndRcvReviewEmpNm;
	
	/**
	 * 2차검토부서접수-검토자품의상태코드( 공통코드 C00005)
	 */
	private String sndRcvReviewStsCd;
	
	/**
	 * 2차검토부서접수-검토일자
	 */
	private String sndRcvReviewDate;
	
	/**
	 * 2차검토부서접수-승인자사번
	 */
	private String sndRcvApprEmpNo;
	
	/**
	 * 2차검토부서접수-승인자명
	 */
	private String sndRcvApprEmpNm;
	
	/**
	 * 2차검토부서접수-승인자품의상태코드( 공통코드 C00005)
	 */
	private String sndRcvApprStsCd;
	
	/**
	 * 2차검토부서접수-승인일자
	 */
	private String sndRcvApprDate;
	
	/**
	 * 2차검토부서결과-기안자사번
	 */
	private String sndRsltDraftEmpNo;
	
	/**
	 * 2차검토부서결과-기안자명
	 */
	private String sndRsltDraftEmpNm;
	
	/**
	 * 2차검토부서결과-기안자품의상태코드(  공통코드 C00005)
	 */
	private String sndRsltDraftStsCd;
	
	/**
	 * 2차검토부서결과-기안일자
	 */
	private String sndRsltDraftDate;
	
	/**
	 * 2차검토부서결과-검토자사번
	 */
	private String sndRsltReviewEmpNo;
	
	
	/**
	 * 2차검토부서결과-검토자명
	 */
	private String sndRsltReviewEmpNm;
	
	/**
	 * 2차검토부서결과-검토자품의상태코드(  공통코드 C00005)
	 */
	private String sndRsltReviewStsCd;
	
	/**
	 * 2차검토부서결과-검토일자
	 */
	private String sndRsltReviewDate;
	
	/**
	 * 2차검토부서결과-승인자사번
	 */
	private String sndRsltApprEmpNo;
	/**
	 * 2차검토부서결과-승인자명
	 */
	private String sndRsltApprEmpNm;
	
	/**
	 * 2차검토부서결과-승인자품의상태코드(  공통코드 C00005)
	 */
	private String sndRsltApprStsCd;
	
	/**
	 * 2차검토부서결과-승인일자
	 */
	private String sndRsltApprDate;
	
	/**
	 * 품의주석
	 */
	private String apprComment;
	
	/* ----------- 그외 */
	
	/**
	 * 회람부서
	 */
	private List<DevReqShareDept> devReqShareDeptList;
	
	/**
	 * 회람부서 ID목록 
	 */
	private List<String> devReqShareDeptIdList;
	
	/**
	 * 파라미터( 승인, 부결)
	 */
	private String approveStsCd;
	
	/**
	 * 프로세스 그룹번호
	 */
	private String processGroupNo;
	
	/**
	 * 결과파일 읽기 권한
	 */
	private String rsltFileReadYn;
	
	/**
	 *  신제품 개발 검토 의뢰서 조회 권한 체크 
	 */
	private String authYn;
	
	/**
	 * 대상자 명
	 */
	private String userName;
	
	/**
	 * 이메일 주소
	 */
	private String email;
	
	/**
	 * 파일 변경 타입
	 */
	private String fileEditType;
	
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
	 * @return the mgntNo
	 */
	public String getMgntNo() {
		return mgntNo;
	}

	/**
	 * @param mgntNo the mgntNo to set
	 */
	public void setMgntNo(String mgntNo) {
		this.mgntNo = mgntNo;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the divisionNo
	 */
	public String getDivisionNo() {
		return divisionNo;
	}

	/**
	 * @param divisionNo the divisionNo to set
	 */
	public void setDivisionNo(String divisionNo) {
		this.divisionNo = divisionNo;
	}

	/**
	 * @return the reqEmpNo
	 */
	public String getReqEmpNo() {
		return reqEmpNo;
	}

	/**
	 * @param reqEmpNo the reqEmpNo to set
	 */
	public void setReqEmpNo(String reqEmpNo) {
		this.reqEmpNo = reqEmpNo;
	}

	/**
	 * @return the reqEmpNm
	 */
	public String getReqEmpNm() {
		return reqEmpNm;
	}

	/**
	 * @param reqEmpNm the reqEmpNm to set
	 */
	public void setReqEmpNm(String reqEmpNm) {
		this.reqEmpNm = reqEmpNm;
	}

	/**
	 * @return the reqDate
	 */
	public String getReqDate() {
		return reqDate;
	}

	/**
	 * @param reqDate the reqDate to set
	 */
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	/**
	 * @return the reqDeptId
	 */
	public String getReqDeptId() {
		return reqDeptId;
	}

	/**
	 * @param reqDeptId the reqDeptId to set
	 */
	public void setReqDeptId(String reqDeptId) {
		this.reqDeptId = reqDeptId;
	}

	/**
	 * @return the reqDeptNm
	 */
	public String getReqDeptNm() {
		return reqDeptNm;
	}

	/**
	 * @param reqDeptNm the reqDeptNm to set
	 */
	public void setReqDeptNm(String reqDeptNm) {
		this.reqDeptNm = reqDeptNm;
	}

	/**
	 * @return the reqRcvDeptId
	 */
	public String getReqRcvDeptId() {
		return reqRcvDeptId;
	}

	/**
	 * @param reqRcvDeptId the reqRcvDeptId to set
	 */
	public void setReqRcvDeptId(String reqRcvDeptId) {
		this.reqRcvDeptId = reqRcvDeptId;
	}

	/**
	 * @return the reqRcvDeptNm
	 */
	public String getReqRcvDeptNm() {
		return reqRcvDeptNm;
	}

	/**
	 * @param reqRcvDeptNm the reqRcvDeptNm to set
	 */
	public void setReqRcvDeptNm(String reqRcvDeptNm) {
		this.reqRcvDeptNm = reqRcvDeptNm;
	}

	/**
	 * @return the fstReviewEmpNo
	 */
	public String getFstReviewEmpNo() {
		return fstReviewEmpNo;
	}

	/**
	 * @param fstReviewEmpNo the fstReviewEmpNo to set
	 */
	public void setFstReviewEmpNo(String fstReviewEmpNo) {
		this.fstReviewEmpNo = fstReviewEmpNo;
	}

	/**
	 * @return the fstReviewEmpNm
	 */
	public String getFstReviewEmpNm() {
		return fstReviewEmpNm;
	}

	/**
	 * @param fstReviewEmpNm the fstReviewEmpNm to set
	 */
	public void setFstReviewEmpNm(String fstReviewEmpNm) {
		this.fstReviewEmpNm = fstReviewEmpNm;
	}

	/**
	 * @return the fstReviewDate
	 */
	public String getFstReviewDate() {
		return fstReviewDate;
	}

	/**
	 * @param fstReviewDate the fstReviewDate to set
	 */
	public void setFstReviewDate(String fstReviewDate) {
		this.fstReviewDate = fstReviewDate;
	}

	/**
	 * @return the fstReviewDeptId
	 */
	public String getFstReviewDeptId() {
		return fstReviewDeptId;
	}

	/**
	 * @param fstReviewDeptId the fstReviewDeptId to set
	 */
	public void setFstReviewDeptId(String fstReviewDeptId) {
		this.fstReviewDeptId = fstReviewDeptId;
	}

	/**
	 * @return the fstReviewDeptNm
	 */
	public String getFstReviewDeptNm() {
		return fstReviewDeptNm;
	}

	/**
	 * @param fstReviewDeptNm the fstReviewDeptNm to set
	 */
	public void setFstReviewDeptNm(String fstReviewDeptNm) {
		this.fstReviewDeptNm = fstReviewDeptNm;
	}

	/**
	 * @return the sndReviewDeptId
	 */
	public String getSndReviewDeptId() {
		return sndReviewDeptId;
	}

	/**
	 * @param sndReviewDeptId the sndReviewDeptId to set
	 */
	public void setSndReviewDeptId(String sndReviewDeptId) {
		this.sndReviewDeptId = sndReviewDeptId;
	}

	/**
	 * @return the sndReviewDeptNm
	 */
	public String getSndReviewDeptNm() {
		return sndReviewDeptNm;
	}

	/**
	 * @param sndReviewDeptNm the sndReviewDeptNm to set
	 */
	public void setSndReviewDeptNm(String sndReviewDeptNm) {
		this.sndReviewDeptNm = sndReviewDeptNm;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the estQty
	 */
	public String getEstQty() {
		return estQty;
	}

	/**
	 * @param estQty the estQty to set
	 */
	public void setEstQty(String estQty) {
		this.estQty = estQty;
	}

	/**
	 * @return the estPrice
	 */
	public String getEstPrice() {
		return estPrice;
	}

	/**
	 * @param estPrice the estPrice to set
	 */
	public void setEstPrice(String estPrice) {
		this.estPrice = estPrice;
	}

	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}

	/**
	 * @return the productUsage
	 */
	public String getProductUsage() {
		return productUsage;
	}

	/**
	 * @param productUsage the productUsage to set
	 */
	public void setProductUsage(String productUsage) {
		this.productUsage = productUsage;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the compAskSample
	 */
	public String getCompAskSample() {
		return compAskSample;
	}

	/**
	 * @param compAskSample the compAskSample to set
	 */
	public void setCompAskSample(String compAskSample) {
		this.compAskSample = compAskSample;
	}

	/**
	 * @return the reqFileItemId
	 */
	public String getReqFileItemId() {
		return reqFileItemId;
	}

	/**
	 * @param reqFileItemId the reqFileItemId to set
	 */
	public void setReqFileItemId(String reqFileItemId) {
		this.reqFileItemId = reqFileItemId;
	}

	/**
	 * @return the reqPurpose
	 */
	public String getReqPurpose() {
		return reqPurpose;
	}

	/**
	 * @param reqPurpose the reqPurpose to set
	 */
	public void setReqPurpose(String reqPurpose) {
		this.reqPurpose = reqPurpose;
	}

	/**
	 * @return the reqItemCd
	 */
	public String getReqItemCd() {
		return reqItemCd;
	}

	/**
	 * @param reqItemCd the reqItemCd to set
	 */
	public void setReqItemCd(String reqItemCd) {
		this.reqItemCd = reqItemCd;
	}

	/**
	 * @return the reqItemEtcTxt
	 */
	public String getReqItemEtcTxt() {
		return reqItemEtcTxt;
	}

	/**
	 * @param reqItemEtcTxt the reqItemEtcTxt to set
	 */
	public void setReqItemEtcTxt(String reqItemEtcTxt) {
		this.reqItemEtcTxt = reqItemEtcTxt;
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
	 * @return the reqScheduleEtcDate
	 */
	public String getReqScheduleEtcDate() {
		return reqScheduleEtcDate;
	}

	/**
	 * @param reqScheduleEtcDate the reqScheduleEtcDate to set
	 */
	public void setReqScheduleEtcDate(String reqScheduleEtcDate) {
		this.reqScheduleEtcDate = reqScheduleEtcDate;
	}

	/**
	 * @return the reqScheduleLimitDate
	 */
	public String getReqScheduleLimitDate() {
		return reqScheduleLimitDate;
	}

	/**
	 * @param reqScheduleLimitDate the reqScheduleLimitDate to set
	 */
	public void setReqScheduleLimitDate(String reqScheduleLimitDate) {
		this.reqScheduleLimitDate = reqScheduleLimitDate;
	}

	/**
	 * @return the analysisPurpose
	 */
	public String getAnalysisPurpose() {
		return analysisPurpose;
	}

	/**
	 * @param analysisPurpose the analysisPurpose to set
	 */
	public void setAnalysisPurpose(String analysisPurpose) {
		this.analysisPurpose = analysisPurpose;
	}

	/**
	 * @return the businessInfo
	 */
	public String getBusinessInfo() {
		return businessInfo;
	}

	/**
	 * @param businessInfo the businessInfo to set
	 */
	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

	/**
	 * @return the qualityAnalysis
	 */
	public String getQualityAnalysis() {
		return qualityAnalysis;
	}

	/**
	 * @param qualityAnalysis the qualityAnalysis to set
	 */
	public void setQualityAnalysis(String qualityAnalysis) {
		this.qualityAnalysis = qualityAnalysis;
	}

	/**
	 * @return the etcRequest
	 */
	public String getEtcRequest() {
		return etcRequest;
	}

	/**
	 * @param etcRequest the etcRequest to set
	 */
	public void setEtcRequest(String etcRequest) {
		this.etcRequest = etcRequest;
	}

	/**
	 * @return the sndReviewOpinion
	 */
	public String getSndReviewOpinion() {
		return sndReviewOpinion;
	}

	/**
	 * @param sndReviewOpinion the sndReviewOpinion to set
	 */
	public void setSndReviewOpinion(String sndReviewOpinion) {
		this.sndReviewOpinion = sndReviewOpinion;
	}

	/**
	 * @return the sndReviewResult
	 */
	public String getSndReviewResult() {
		return sndReviewResult;
	}

	/**
	 * @param sndReviewResult the sndReviewResult to set
	 */
	public void setSndReviewResult(String sndReviewResult) {
		this.sndReviewResult = sndReviewResult;
	}

	/**
	 * @return the rsltFileItemId
	 */
	public String getRsltFileItemId() {
		return rsltFileItemId;
	}

	/**
	 * @param rsltFileItemId the rsltFileItemId to set
	 */
	public void setRsltFileItemId(String rsltFileItemId) {
		this.rsltFileItemId = rsltFileItemId;
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
	 * @return the tcsDraftEmpNo
	 */
	public String getTcsDraftEmpNo() {
		return tcsDraftEmpNo;
	}

	/**
	 * @param tcsDraftEmpNo the tcsDraftEmpNo to set
	 */
	public void setTcsDraftEmpNo(String tcsDraftEmpNo) {
		this.tcsDraftEmpNo = tcsDraftEmpNo;
	}

	/**
	 * @return the tcsDraftEmpNm
	 */
	public String getTcsDraftEmpNm() {
		return tcsDraftEmpNm;
	}

	/**
	 * @param tcsDraftEmpNm the tcsDraftEmpNm to set
	 */
	public void setTcsDraftEmpNm(String tcsDraftEmpNm) {
		this.tcsDraftEmpNm = tcsDraftEmpNm;
	}

	/**
	 * @return the tcsDraftStsCd
	 */
	public String getTcsDraftStsCd() {
		return tcsDraftStsCd;
	}

	/**
	 * @param tcsDraftStsCd the tcsDraftStsCd to set
	 */
	public void setTcsDraftStsCd(String tcsDraftStsCd) {
		this.tcsDraftStsCd = tcsDraftStsCd;
	}

	/**
	 * @return the tcsDraftDate
	 */
	public String getTcsDraftDate() {
		return tcsDraftDate;
	}

	/**
	 * @param tcsDraftDate the tcsDraftDate to set
	 */
	public void setTcsDraftDate(String tcsDraftDate) {
		this.tcsDraftDate = tcsDraftDate;
	}

	/**
	 * @return the tcsReviewEmpNo
	 */
	public String getTcsReviewEmpNo() {
		return tcsReviewEmpNo;
	}

	/**
	 * @param tcsReviewEmpNo the tcsReviewEmpNo to set
	 */
	public void setTcsReviewEmpNo(String tcsReviewEmpNo) {
		this.tcsReviewEmpNo = tcsReviewEmpNo;
	}

	/**
	 * @return the tcsReviewStsCd
	 */
	public String getTcsReviewStsCd() {
		return tcsReviewStsCd;
	}

	/**
	 * @param tcsReviewStsCd the tcsReviewStsCd to set
	 */
	public void setTcsReviewStsCd(String tcsReviewStsCd) {
		this.tcsReviewStsCd = tcsReviewStsCd;
	}

	/**
	 * @return the tcsReviewDate
	 */
	public String getTcsReviewDate() {
		return tcsReviewDate;
	}

	/**
	 * @param tcsReviewDate the tcsReviewDate to set
	 */
	public void setTcsReviewDate(String tcsReviewDate) {
		this.tcsReviewDate = tcsReviewDate;
	}

	/**
	 * @return the tcsApprEmpNo
	 */
	public String getTcsApprEmpNo() {
		return tcsApprEmpNo;
	}

	/**
	 * @param tcsApprEmpNo the tcsApprEmpNo to set
	 */
	public void setTcsApprEmpNo(String tcsApprEmpNo) {
		this.tcsApprEmpNo = tcsApprEmpNo;
	}

	/**
	 * @return the tcsApprStsCd
	 */
	public String getTcsApprStsCd() {
		return tcsApprStsCd;
	}

	/**
	 * @param tcsApprStsCd the tcsApprStsCd to set
	 */
	public void setTcsApprStsCd(String tcsApprStsCd) {
		this.tcsApprStsCd = tcsApprStsCd;
	}

	/**
	 * @return the tcsApprDate
	 */
	public String getTcsApprDate() {
		return tcsApprDate;
	}

	/**
	 * @param tcsApprDate the tcsApprDate to set
	 */
	public void setTcsApprDate(String tcsApprDate) {
		this.tcsApprDate = tcsApprDate;
	}

	/**
	 * @return the sndRcvDraftEmpNo
	 */
	public String getSndRcvDraftEmpNo() {
		return sndRcvDraftEmpNo;
	}

	/**
	 * @param sndRcvDraftEmpNo the sndRcvDraftEmpNo to set
	 */
	public void setSndRcvDraftEmpNo(String sndRcvDraftEmpNo) {
		this.sndRcvDraftEmpNo = sndRcvDraftEmpNo;
	}

	/**
	 * @return the sndRcvDraftStsCd
	 */
	public String getSndRcvDraftStsCd() {
		return sndRcvDraftStsCd;
	}

	/**
	 * @param sndRcvDraftStsCd the sndRcvDraftStsCd to set
	 */
	public void setSndRcvDraftStsCd(String sndRcvDraftStsCd) {
		this.sndRcvDraftStsCd = sndRcvDraftStsCd;
	}

	/**
	 * @return the sndRcvDraftDate
	 */
	public String getSndRcvDraftDate() {
		return sndRcvDraftDate;
	}

	/**
	 * @param sndRcvDraftDate the sndRcvDraftDate to set
	 */
	public void setSndRcvDraftDate(String sndRcvDraftDate) {
		this.sndRcvDraftDate = sndRcvDraftDate;
	}

	/**
	 * @return the sndRcvReviewEmpNo
	 */
	public String getSndRcvReviewEmpNo() {
		return sndRcvReviewEmpNo;
	}

	/**
	 * @param sndRcvReviewEmpNo the sndRcvReviewEmpNo to set
	 */
	public void setSndRcvReviewEmpNo(String sndRcvReviewEmpNo) {
		this.sndRcvReviewEmpNo = sndRcvReviewEmpNo;
	}

	/**
	 * @return the sndRcvReviewStsCd
	 */
	public String getSndRcvReviewStsCd() {
		return sndRcvReviewStsCd;
	}

	/**
	 * @param sndRcvReviewStsCd the sndRcvReviewStsCd to set
	 */
	public void setSndRcvReviewStsCd(String sndRcvReviewStsCd) {
		this.sndRcvReviewStsCd = sndRcvReviewStsCd;
	}

	/**
	 * @return the sndRcvReviewDate
	 */
	public String getSndRcvReviewDate() {
		return sndRcvReviewDate;
	}

	/**
	 * @param sndRcvReviewDate the sndRcvReviewDate to set
	 */
	public void setSndRcvReviewDate(String sndRcvReviewDate) {
		this.sndRcvReviewDate = sndRcvReviewDate;
	}

	/**
	 * @return the sndRcvApprEmpNo
	 */
	public String getSndRcvApprEmpNo() {
		return sndRcvApprEmpNo;
	}

	/**
	 * @param sndRcvApprEmpNo the sndRcvApprEmpNo to set
	 */
	public void setSndRcvApprEmpNo(String sndRcvApprEmpNo) {
		this.sndRcvApprEmpNo = sndRcvApprEmpNo;
	}

	/**
	 * @return the sndRcvApprStsCd
	 */
	public String getSndRcvApprStsCd() {
		return sndRcvApprStsCd;
	}

	/**
	 * @param sndRcvApprStsCd the sndRcvApprStsCd to set
	 */
	public void setSndRcvApprStsCd(String sndRcvApprStsCd) {
		this.sndRcvApprStsCd = sndRcvApprStsCd;
	}

	/**
	 * @return the sndRcvApprDate
	 */
	public String getSndRcvApprDate() {
		return sndRcvApprDate;
	}

	/**
	 * @param sndRcvApprDate the sndRcvApprDate to set
	 */
	public void setSndRcvApprDate(String sndRcvApprDate) {
		this.sndRcvApprDate = sndRcvApprDate;
	}

	/**
	 * @return the sndRsltDraftEmpNo
	 */
	public String getSndRsltDraftEmpNo() {
		return sndRsltDraftEmpNo;
	}

	/**
	 * @param sndRsltDraftEmpNo the sndRsltDraftEmpNo to set
	 */
	public void setSndRsltDraftEmpNo(String sndRsltDraftEmpNo) {
		this.sndRsltDraftEmpNo = sndRsltDraftEmpNo;
	}

	/**
	 * @return the sndRsltDraftStsCd
	 */
	public String getSndRsltDraftStsCd() {
		return sndRsltDraftStsCd;
	}

	/**
	 * @param sndRsltDraftStsCd the sndRsltDraftStsCd to set
	 */
	public void setSndRsltDraftStsCd(String sndRsltDraftStsCd) {
		this.sndRsltDraftStsCd = sndRsltDraftStsCd;
	}

	/**
	 * @return the sndRsltDraftDate
	 */
	public String getSndRsltDraftDate() {
		return sndRsltDraftDate;
	}

	/**
	 * @param sndRsltDraftDate the sndRsltDraftDate to set
	 */
	public void setSndRsltDraftDate(String sndRsltDraftDate) {
		this.sndRsltDraftDate = sndRsltDraftDate;
	}

	/**
	 * @return the sndRsltReviewEmpNo
	 */
	public String getSndRsltReviewEmpNo() {
		return sndRsltReviewEmpNo;
	}

	/**
	 * @param sndRsltReviewEmpNo the sndRsltReviewEmpNo to set
	 */
	public void setSndRsltReviewEmpNo(String sndRsltReviewEmpNo) {
		this.sndRsltReviewEmpNo = sndRsltReviewEmpNo;
	}

	/**
	 * @return the sndRsltReviewStsCd
	 */
	public String getSndRsltReviewStsCd() {
		return sndRsltReviewStsCd;
	}

	/**
	 * @param sndRsltReviewStsCd the sndRsltReviewStsCd to set
	 */
	public void setSndRsltReviewStsCd(String sndRsltReviewStsCd) {
		this.sndRsltReviewStsCd = sndRsltReviewStsCd;
	}

	/**
	 * @return the sndRsltReviewDate
	 */
	public String getSndRsltReviewDate() {
		return sndRsltReviewDate;
	}

	/**
	 * @param sndRsltReviewDate the sndRsltReviewDate to set
	 */
	public void setSndRsltReviewDate(String sndRsltReviewDate) {
		this.sndRsltReviewDate = sndRsltReviewDate;
	}

	/**
	 * @return the sndRsltApprEmpNo
	 */
	public String getSndRsltApprEmpNo() {
		return sndRsltApprEmpNo;
	}

	/**
	 * @param sndRsltApprEmpNo the sndRsltApprEmpNo to set
	 */
	public void setSndRsltApprEmpNo(String sndRsltApprEmpNo) {
		this.sndRsltApprEmpNo = sndRsltApprEmpNo;
	}

	/**
	 * @return the sndRsltApprStsCd
	 */
	public String getSndRsltApprStsCd() {
		return sndRsltApprStsCd;
	}

	/**
	 * @param sndRsltApprStsCd the sndRsltApprStsCd to set
	 */
	public void setSndRsltApprStsCd(String sndRsltApprStsCd) {
		this.sndRsltApprStsCd = sndRsltApprStsCd;
	}

	/**
	 * @return the sndRsltApprDate
	 */
	public String getSndRsltApprDate() {
		return sndRsltApprDate;
	}

	/**
	 * @param sndRsltApprDate the sndRsltApprDate to set
	 */
	public void setSndRsltApprDate(String sndRsltApprDate) {
		this.sndRsltApprDate = sndRsltApprDate;
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
	 * @return the attechedFile
	 */
	public static String getAttechedFile() {
		return ATTECHED_FILE;
	}

	/**
	 * @return the allFile
	 */
	public static String getAllFile() {
		return ALL_FILE;
	}

	/**
	 * @return the tcsApprEmpNm
	 */
	public String getTcsApprEmpNm() {
		return tcsApprEmpNm;
	}

	/**
	 * @param tcsApprEmpNm the tcsApprEmpNm to set
	 */
	public void setTcsApprEmpNm(String tcsApprEmpNm) {
		this.tcsApprEmpNm = tcsApprEmpNm;
	}

	/**
	 * @return the sndRcvDraftEmpNm
	 */
	public String getSndRcvDraftEmpNm() {
		return sndRcvDraftEmpNm;
	}

	/**
	 * @param sndRcvDraftEmpNm the sndRcvDraftEmpNm to set
	 */
	public void setSndRcvDraftEmpNm(String sndRcvDraftEmpNm) {
		this.sndRcvDraftEmpNm = sndRcvDraftEmpNm;
	}

	/**
	 * @return the sndRcvReviewEmpNm
	 */
	public String getSndRcvReviewEmpNm() {
		return sndRcvReviewEmpNm;
	}

	/**
	 * @param sndRcvReviewEmpNm the sndRcvReviewEmpNm to set
	 */
	public void setSndRcvReviewEmpNm(String sndRcvReviewEmpNm) {
		this.sndRcvReviewEmpNm = sndRcvReviewEmpNm;
	}

	/**
	 * @return the sndRcvApprEmpNm
	 */
	public String getSndRcvApprEmpNm() {
		return sndRcvApprEmpNm;
	}

	/**
	 * @param sndRcvApprEmpNm the sndRcvApprEmpNm to set
	 */
	public void setSndRcvApprEmpNm(String sndRcvApprEmpNm) {
		this.sndRcvApprEmpNm = sndRcvApprEmpNm;
	}

	/**
	 * @return the sndRsltDraftEmpNm
	 */
	public String getSndRsltDraftEmpNm() {
		return sndRsltDraftEmpNm;
	}

	/**
	 * @param sndRsltDraftEmpNm the sndRsltDraftEmpNm to set
	 */
	public void setSndRsltDraftEmpNm(String sndRsltDraftEmpNm) {
		this.sndRsltDraftEmpNm = sndRsltDraftEmpNm;
	}

	/**
	 * @return the sndRsltReviewEmpNm
	 */
	public String getSndRsltReviewEmpNm() {
		return sndRsltReviewEmpNm;
	}

	/**
	 * @param sndRsltReviewEmpNm the sndRsltReviewEmpNm to set
	 */
	public void setSndRsltReviewEmpNm(String sndRsltReviewEmpNm) {
		this.sndRsltReviewEmpNm = sndRsltReviewEmpNm;
	}

	/**
	 * @return the sndRsltApprEmpNm
	 */
	public String getSndRsltApprEmpNm() {
		return sndRsltApprEmpNm;
	}

	/**
	 * @param sndRsltApprEmpNm the sndRsltApprEmpNm to set
	 */
	public void setSndRsltApprEmpNm(String sndRsltApprEmpNm) {
		this.sndRsltApprEmpNm = sndRsltApprEmpNm;
	}

	/**
	 * @return the tcsReviewEmpNm
	 */
	public String getTcsReviewEmpNm() {
		return tcsReviewEmpNm;
	}

	/**
	 * @param tcsReviewEmpNm the tcsReviewEmpNm to set
	 */
	public void setTcsReviewEmpNm(String tcsReviewEmpNm) {
		this.tcsReviewEmpNm = tcsReviewEmpNm;
	}

	/**
	 * @return the devReqShareDeptList
	 */
	public List<DevReqShareDept> getDevReqShareDeptList() {
		return devReqShareDeptList;
	}

	/**
	 * @param devReqShareDeptList the devReqShareDeptList to set
	 */
	public void setDevReqShareDeptList(List<DevReqShareDept> devReqShareDeptList) {
		this.devReqShareDeptList = devReqShareDeptList;
	}
	
	/**
	 * @return the devReqShareDeptIdList
	 */
	public List<String> getDevReqShareDeptIdList() {
		return devReqShareDeptIdList;
	}

	/**
	 * @param devReqShareDeptIdList the devReqShareDeptIdList to set
	 */
	public void setDevReqShareDeptIdList(List<String> devReqShareDeptIdList) {
		this.devReqShareDeptIdList = devReqShareDeptIdList;
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
	 * @return the rsltFileReadYn
	 */
	public String getRsltFileReadYn() {
		return rsltFileReadYn;
	}

	/**
	 * @param rsltFileReadYn the rsltFileReadYn to set
	 */
	public void setRsltFileReadYn(String rsltFileReadYn) {
		this.rsltFileReadYn = rsltFileReadYn;
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
	 * @return the sndRsltReviewResult
	 */
	public String getSndRsltReviewResult() {
		return sndRsltReviewResult;
	}

	/**
	 * @param sndRsltReviewResult the sndRsltReviewResult to set
	 */
	public void setSndRsltReviewResult(String sndRsltReviewResult) {
		this.sndRsltReviewResult = sndRsltReviewResult;
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
	 * @return the fileEditType
	 */
	public String getFileEditType() {
		return fileEditType;
	}

	/**
	 * @param fileEditType the fileEditType to set
	 */
	public void setFileEditType(String fileEditType) {
		this.fileEditType = fileEditType;
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
