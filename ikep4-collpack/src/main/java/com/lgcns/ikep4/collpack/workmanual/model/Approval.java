/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: Approval.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Approval extends BaseObject {
	private static final long serialVersionUID = 180135570664608992L;
	
	/**
	 * 결재ID
	 */
	private String  approvalId;

	/**
	 * 메뉴얼ID
	 */
	private String  manualId;

	/**
	 * 버전ID
	 */
	private String  versionId;

	/**
	 * 메뉴얼 요청 타입
	 */
	private String  manualType;

	/**
	 * 결재요청 내용
	 */
	private String  requestContents;

	/**
	 * 기안자 ID
	 */
	private String  registerId;

	/**
	 * 기안자 이름
	 */
	private String  registerName;

	/**
	 * 등록일
	 */
	private Date    registDate;

	/**
	 * 결재상태
	 */
	private String  approvalStatus;

	/**
	 * 검색될 갯수
	 */
	private Integer indexRowNum = 0;

	/**
	 * 버젼
	 */
	private Float   version;  

	/**
	 * 버젼제목
	 */
	private String  versionTitle;

	/**
	 * 기안자 이름(영어)
	 */
	private String  registerEnglishName;
	
	/**
	 * @return the versionTitle
	 */
	public String getVersionTitle() {
		return versionTitle;
	}
	/**
	 * @param versionTitle the versionTitle to set
	 */
	public void setVersionTitle(String versionTitle) {
		this.versionTitle = versionTitle;
	}
	private String  approvalResult;
	
	/**
	 * @return the indexRowNum
	 */
	public Integer getIndexRowNum() {
		return indexRowNum;
	}
	/**
	 * @param indexRowNum the indexRowNum to set
	 */
	public void setIndexRowNum(Integer indexRowNum) {
		this.indexRowNum = indexRowNum;
	}
	/**
	 * @return the version
	 */
	public Float getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Float version) {
		this.version = version;
	}
	/**
	 * @return the approvalResult
	 */
	public String getApprovalResult() {
		return approvalResult;
	}
	/**
	 * @param approvalResult the approvalResult to set
	 */
	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	/**
	 * @return the approvalId
	 */
	public String getApprovalId() {
		return approvalId;
	}
	/**
	 * @param approvalId the approvalId to set
	 */
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}
	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}
	/**
	 * @return the versionId
	 */
	public String getVersionId() {
		return versionId;
	}
	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	/**
	 * @return the manualType
	 */
	public String getManualType() {
		return manualType;
	}
	/**
	 * @param manualType the manualType to set
	 */
	public void setManualType(String manualType) {
		this.manualType = manualType;
	}
	/**
	 * @return the requestContents
	 */
	public String getRequestContents() {
		return requestContents;
	}
	/**
	 * @param requestContents the requestContents to set
	 */
	public void setRequestContents(String requestContents) {
		this.requestContents = requestContents;
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
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}
	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	/**
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}
	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

}
