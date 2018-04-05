/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.proposal.model;

import java.util.List;

import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;

/**
 * 개발제안서 VO
 * 
 * @author pjh
 * @version $Id$
 */
public class Proposal extends BaseObject {
	
	public static final String CLS_NAME = "com.lgcns.ikep4.approval.collaboration.proposal.model.TestRequest";
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
	
	/*------------------------------- 개발제안서 정보 -----------------------------*/
	
	/**
	 * 제안서 관리번호
	 */
	private String proposalNo;
	
	/**
	 * 회사코드(C00010)
	 */
	private String companyCode;
	
	/**
	 * TCS 접수자 사번
	 */
	private String tcsRcvEmpNo;
	
	/**
	 * TCS 접수자 명
	 */
	private String tcsRcvEmpNm;
	
	/**
	 * 접수일자
	 */
	private String tcsRcvDate;
	
	/**
	 * 의뢰자사번 
	 */
	private String reqEmpNo;
	/**
	 * 의뢰자 명
	 */
	private String reqEmpNm;
	
	/**
	 * 의뢰부서ID 
	 */
	private String reqDeptId;
	
	/**
	 * 의뢰부서명
	 */
	private String reqDeptNm;
	
	/**
	 * 의뢰자직급
	 */
	private String reqUserRank;
	
	/**
	 * 작성일자
	 */
	private String reqDate;
	
	/**
	 * 고객명
	 */
	private String custName;
	
	/**
	 * 지군명
	 */
	private String paperGrpName;
	
	/**
	 * 제품명
	 */
	private String productName;
	
	/**
	 * 제안사유
	 */
	private String proposalReason;
	
	/**
	 * 주요특징및기능
	 */
	private String keyFeatureFunction;
	
	/**
	 * 고객요청사항
	 */
	private String custRequests;
	
	/**
	 * 시장현황
	 */
	private String marketStatus;
	
	/**
	 * 목표판매량
	 */
	private String goalSaleQty;
	
	/**
	 * 목표판매단가
	 */
	private String goalSalePrice;
	
	/**
	 * 지원요청(C00006)
	 */
	private String supportReqCd;
	
	/**
	 * 개발기간
	 */
	private String devPeriod;
	
	/**
	 * 개발구분코드(C00007)
	 */
	private String devGbnCd;
	
	/**
	 * 품질보증부(의견)
	 */
	private String qadOpinion;
	
	/**
	 * 신제품연구팀(의견)
	 */
	private String labOpinion;
	
	/**
	 * 영업팀 의견
	 */
	private String salesOpinion;
	
	/**
	 * SCM팀 담당사번
	 */
	private String scmEmpNo;
	/**
	 * SCM팀 담당사명
	 */
	private String scmEmpNm;
	
	/**
	 * SCM팀 의견
	 */
	private String scmOpinion;
	
	/**
	 * 마케팅팀 담당사번
	 */
	private String marketEmpNo;
	/**
	 * 마케팅팀 담당명
	 */
	private String marketEmpNm;
	
	/**
	 * 마케팅팀 의견
	 */
	private String marketOpinion;
	
	/**
	 * 마케팅팀 관리등급(C00018)
	 */
	private String marketGradeCd;
	
	/**
	 * TCS팀 종합 의견
	 */
	private String tcsTotalOpinion;
	
	private String proposalStatus;
	
	/**
	 * 승인Comment
	 */
	private String apprComment;
	
	/**
	 * 파일항목ID
	 */
	private String fileItemId;
	
	/**
	 * 저장여부
	 */
	private String saveYn;
	
	/* ----------- 그외 */
	/**
	 * 부서의견 종류
	 */
	private String opinionType;
	
	/**
	 * 부서의견 공통 value
	 */
	private String opinionValue;
	
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
	 * 메일대상자 부서명
	 */
	private String mailUserDeptNm;
	
	/**
	 * 수정타입
	 */
	private String saveType;
	
	/**
	 * 현재 세션 EMPNO(수정시만 사용)
	 */
	private String sessionEmpNo;

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
	 * @return the proposalNo
	 */
	public String getProposalNo() {
		return proposalNo;
	}

	/**
	 * @param proposalNo the proposalNo to set
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
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
	 * @return the tcsRcvEmpNo
	 */
	public String getTcsRcvEmpNo() {
		return tcsRcvEmpNo;
	}

	/**
	 * @param tcsRcvEmpNo the tcsRcvEmpNo to set
	 */
	public void setTcsRcvEmpNo(String tcsRcvEmpNo) {
		this.tcsRcvEmpNo = tcsRcvEmpNo;
	}

	/**
	 * @return the tcsRcvEmpNm
	 */
	public String getTcsRcvEmpNm() {
		return tcsRcvEmpNm;
	}

	/**
	 * @param tcsRcvEmpNm the tcsRcvEmpNm to set
	 */
	public void setTcsRcvEmpNm(String tcsRcvEmpNm) {
		this.tcsRcvEmpNm = tcsRcvEmpNm;
	}

	/**
	 * @return the tcsRcvDate
	 */
	public String getTcsRcvDate() {
		return tcsRcvDate;
	}

	/**
	 * @param tcsRcvDate the tcsRcvDate to set
	 */
	public void setTcsRcvDate(String tcsRcvDate) {
		this.tcsRcvDate = tcsRcvDate;
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
	 * @return the reqUserRank
	 */
	public String getReqUserRank() {
		return reqUserRank;
	}

	/**
	 * @param reqUserRank the reqUserRank to set
	 */
	public void setReqUserRank(String reqUserRank) {
		this.reqUserRank = reqUserRank;
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
	 * @return the paperGrpName
	 */
	public String getPaperGrpName() {
		return paperGrpName;
	}

	/**
	 * @param paperGrpName the paperGrpName to set
	 */
	public void setPaperGrpName(String paperGrpName) {
		this.paperGrpName = paperGrpName;
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
	 * @return the proposalReason
	 */
	public String getProposalReason() {
		return proposalReason;
	}

	/**
	 * @param proposalReason the proposalReason to set
	 */
	public void setProposalReason(String proposalReason) {
		this.proposalReason = proposalReason;
	}

	/**
	 * @return the keyFeatureFunction
	 */
	public String getKeyFeatureFunction() {
		return keyFeatureFunction;
	}

	/**
	 * @param keyFeatureFunction the keyFeatureFunction to set
	 */
	public void setKeyFeatureFunction(String keyFeatureFunction) {
		this.keyFeatureFunction = keyFeatureFunction;
	}

	/**
	 * @return the custRequests
	 */
	public String getCustRequests() {
		return custRequests;
	}

	/**
	 * @param custRequests the custRequests to set
	 */
	public void setCustRequests(String custRequests) {
		this.custRequests = custRequests;
	}

	/**
	 * @return the marketStatus
	 */
	public String getMarketStatus() {
		return marketStatus;
	}

	/**
	 * @param marketStatus the marketStatus to set
	 */
	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	/**
	 * @return the goalSaleQty
	 */
	public String getGoalSaleQty() {
		return goalSaleQty;
	}

	/**
	 * @param goalSaleQty the goalSaleQty to set
	 */
	public void setGoalSaleQty(String goalSaleQty) {
		this.goalSaleQty = goalSaleQty;
	}

	/**
	 * @return the goalSalePrice
	 */
	public String getGoalSalePrice() {
		return goalSalePrice;
	}

	/**
	 * @param goalSalePrice the goalSalePrice to set
	 */
	public void setGoalSalePrice(String goalSalePrice) {
		this.goalSalePrice = goalSalePrice;
	}

	/**
	 * @return the supportReqCd
	 */
	public String getSupportReqCd() {
		return supportReqCd;
	}

	/**
	 * @param supportReqCd the supportReqCd to set
	 */
	public void setSupportReqCd(String supportReqCd) {
		this.supportReqCd = supportReqCd;
	}

	/**
	 * @return the devPeriod
	 */
	public String getDevPeriod() {
		return devPeriod;
	}

	/**
	 * @param devPeriod the devPeriod to set
	 */
	public void setDevPeriod(String devPeriod) {
		this.devPeriod = devPeriod;
	}

	/**
	 * @return the devGbnCd
	 */
	public String getDevGbnCd() {
		return devGbnCd;
	}

	/**
	 * @param devGbnCd the devGbnCd to set
	 */
	public void setDevGbnCd(String devGbnCd) {
		this.devGbnCd = devGbnCd;
	}

	/**
	 * @return the qadOpinion
	 */
	public String getQadOpinion() {
		return qadOpinion;
	}

	/**
	 * @param qadOpinion the qadOpinion to set
	 */
	public void setQadOpinion(String qadOpinion) {
		this.qadOpinion = qadOpinion;
	}

	/**
	 * @return the labOpinion
	 */
	public String getLabOpinion() {
		return labOpinion;
	}

	/**
	 * @param labOpinion the labOpinion to set
	 */
	public void setLabOpinion(String labOpinion) {
		this.labOpinion = labOpinion;
	}

	/**
	 * @return the salesOpinion
	 */
	public String getSalesOpinion() {
		return salesOpinion;
	}

	/**
	 * @param salesOpinion the salesOpinion to set
	 */
	public void setSalesOpinion(String salesOpinion) {
		this.salesOpinion = salesOpinion;
	}

	/**
	 * @return the scmEmpNo
	 */
	public String getScmEmpNo() {
		return scmEmpNo;
	}

	/**
	 * @param scmEmpNo the scmEmpNo to set
	 */
	public void setScmEmpNo(String scmEmpNo) {
		this.scmEmpNo = scmEmpNo;
	}

	/**
	 * @return the scmOpinion
	 */
	public String getScmOpinion() {
		return scmOpinion;
	}

	/**
	 * @param scmOpinion the scmOpinion to set
	 */
	public void setScmOpinion(String scmOpinion) {
		this.scmOpinion = scmOpinion;
	}

	/**
	 * @return the marketEmpNo
	 */
	public String getMarketEmpNo() {
		return marketEmpNo;
	}

	/**
	 * @param marketEmpNo the marketEmpNo to set
	 */
	public void setMarketEmpNo(String marketEmpNo) {
		this.marketEmpNo = marketEmpNo;
	}

	/**
	 * @return the marketOpinion
	 */
	public String getMarketOpinion() {
		return marketOpinion;
	}

	/**
	 * @param marketOpinion the marketOpinion to set
	 */
	public void setMarketOpinion(String marketOpinion) {
		this.marketOpinion = marketOpinion;
	}

	/**
	 * @return the tcsTotalOpinion
	 */
	public String getTcsTotalOpinion() {
		return tcsTotalOpinion;
	}

	/**
	 * @param tcsTotalOpinion the tcsTotalOpinion to set
	 */
	public void setTcsTotalOpinion(String tcsTotalOpinion) {
		this.tcsTotalOpinion = tcsTotalOpinion;
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
	 * @return the saveYn
	 */
	public String getSaveYn() {
		return saveYn;
	}

	/**
	 * @param saveYn the saveYn to set
	 */
	public void setSaveYn(String saveYn) {
		this.saveYn = saveYn;
	}

	/**
	 * @return the opinionType
	 */
	public String getOpinionType() {
		return opinionType;
	}

	/**
	 * @param opinionType the opinionType to set
	 */
	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
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
	 * @return the scmEmpNm
	 */
	public String getScmEmpNm() {
		return scmEmpNm;
	}

	/**
	 * @param scmEmpNm the scmEmpNm to set
	 */
	public void setScmEmpNm(String scmEmpNm) {
		this.scmEmpNm = scmEmpNm;
	}

	/**
	 * @return the marketEmpNm
	 */
	public String getMarketEmpNm() {
		return marketEmpNm;
	}

	/**
	 * @param marketEmpNm the marketEmpNm to set
	 */
	public void setMarketEmpNm(String marketEmpNm) {
		this.marketEmpNm = marketEmpNm;
	}

	/**
	 * @return the marketGradeCd
	 */
	public String getMarketGradeCd() {
		return marketGradeCd;
	}

	/**
	 * @param marketGradeCd the marketGradeCd to set
	 */
	public void setMarketGradeCd(String marketGradeCd) {
		this.marketGradeCd = marketGradeCd;
	}

	/**
	 * @return the proposalStatus
	 */
	public String getProposalStatus() {
		return proposalStatus;
	}

	/**
	 * @param proposalStatus the proposalStatus to set
	 */
	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	/**
	 * @return the mailUserDeptNm
	 */
	public String getMailUserDeptNm() {
		return mailUserDeptNm;
	}

	/**
	 * @param mailUserDeptNm the mailUserDeptNm to set
	 */
	public void setMailUserDeptNm(String mailUserDeptNm) {
		this.mailUserDeptNm = mailUserDeptNm;
	}

	/**
	 * @return the opinionValue
	 */
	public String getOpinionValue() {
		return opinionValue;
	}

	/**
	 * @param opinionValue the opinionValue to set
	 */
	public void setOpinionValue(String opinionValue) {
		this.opinionValue = opinionValue;
	}

	/**
	 * @return the saveType
	 */
	public String getSaveType() {
		return saveType;
	}

	/**
	 * @param saveType the saveType to set
	 */
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	/**
	 * @return the sessionEmpNo
	 */
	public String getSessionEmpNo() {
		return sessionEmpNo;
	}

	/**
	 * @param sessionEmpNo the sessionEmpNo to set
	 */
	public void setSessionEmpNo(String sessionEmpNo) {
		this.sessionEmpNo = sessionEmpNo;
	}
}
