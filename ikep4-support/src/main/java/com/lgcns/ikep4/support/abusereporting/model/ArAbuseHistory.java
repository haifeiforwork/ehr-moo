/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * Keyword VO
 *
 * @author 최성우
 * @version $Id: ArAbuseHistory.java 16271 2011-08-18 07:06:14Z giljae $
 */

public class ArAbuseHistory extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -6909620688599888080L;

	/**
	 * ID
	 */
	private String historyId;

	/**
	 * 모듈 코드(MB)
	 */
	private String moduleCode;

	/**
	 * 모듈 명(Micro Blogging)
	 */
	private String moduleName;

	/**
	 * 해당 모듈의 아이템 ID(마이크로 블로깅 : MBLOG_ID)
	 */
	private String itemId;

	/**
	 * 외부_타겟(페이스북, 트위터 등)
	 */
	private String externalTarget;

	/**
	 * 외부 계정(페이스북/트위터 계정)
	 */
	private String externalAccount;

	/**
	 * Detecting된 금지어
	 */
	private String keyword;

	/**
	 * 모니터링 대상 컨텐츠
	 */
	private String contents;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록자 이름(영문)
	 */
	private String registerEnglishName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/*
	 * 금지어 존재 여부 (0: 금지어 없음, 1: 금지어 있음			
	 */
	private int isDetected;

	/*
	 * 직급		
	 */
	private String jobTitleName;

	/**
	 * 해당 호칭코드의 영문이름
	 */
	private String jobTitleEnglishName;
	
	/*
	 * 금지어 존재 여부 (0: 금지어 없음, 1: 금지어 있음			
	 */
	private int isExternal;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	private String referenceId;
	
	private String title;


	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getExternalTarget() {
		return externalTarget;
	}

	public void setExternalTarget(String externalTarget) {
		this.externalTarget = externalTarget;
	}

	public String getExternalAccount() {
		return externalAccount;
	}

	public void setExternalAccount(String externalAccount) {
		this.externalAccount = externalAccount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public int getIsDetected() {
		return isDetected;
	}

	public void setIsDetected(int isDetected) {
		this.isDetected = isDetected;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public int getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(int isExternal) {
		this.isExternal = isExternal;
	}

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
