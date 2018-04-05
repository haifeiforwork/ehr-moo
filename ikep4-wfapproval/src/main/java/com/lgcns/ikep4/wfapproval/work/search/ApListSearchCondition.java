/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 장규진
 * @version $Id: ApListSearchCondition.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApListSearchCondition extends SearchCondition {

	private static final long serialVersionUID = -5116116279546818465L;

	private String apprTypeCd;
	
	private Integer apprDocState;
	
	private String searchStartDate;
	
	private String searchEndDate;
	
	private String apprTitle;
	
	private String registUserId;
	
	private String refUserId;
	
	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
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
	public Integer getApprDocState() {
		return apprDocState;
	}

	/**
	 * @param apprDocState the apprDocState to set
	 */
	public void setApprDocState(Integer apprDocState) {
		this.apprDocState = apprDocState;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return the formTypeCd
	 */
	public String getSearchStartDate() {
		return searchStartDate;
	}

	/**
	 * @param searchStartDate the searchStartDate to set
	 */
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	/**
	 * @return the searchEndDate
	 */
	public String getSearchEndDate() {
		return searchEndDate;
	}

	/**
	 * @param searchEndDate the searchEndDate to set
	 */
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
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
	 * @return the refUserId
	 */
	public String getRefUserId() {
		return refUserId;
	}

	/**
	 * @param registUserId the registUserId to set
	 */
	public void setRefUserId(String refUserId) {
		this.refUserId = refUserId;
	}	
}
