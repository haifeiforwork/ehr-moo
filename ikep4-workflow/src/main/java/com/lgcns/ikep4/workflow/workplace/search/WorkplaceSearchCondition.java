/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * Workplace SearchCondition 확장
 *
 * @author 이재경
 * @version $Id: WorkplaceSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class WorkplaceSearchCondition extends SearchCondition {

	static final long serialVersionUID = -2780647941090818147L; 
	
	private String queryId;
	
	@SuppressWarnings("unused")
	private String countQueryId;
	
	private String partition;
	private String searchcondition;
	private String searchkeyword;
	private String mandatesetting;
	private String mandateresponsible;
	private String mandatemsg;
	private String userId;
	private String requestorId;
	private String startPeriod;
	private String endPeriod;
	
	//업무통계
	private String processId;
	private String processVer;
	private String searchperiod;
	
	//전자결제 조회조건 추가
	private String apprDocState;
	private String apprTypeCd;
	
	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
	}

	/**
	 * @return the queryId
	 */
	public String getQueryId() {
		return queryId;
	}

	/**
	 * @param queryId the queryId to set
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * @return the countQueryId
	 */
	public String getCountQueryId() {
		return "count"+queryId;
	}

	/**
	 * @param countQueryId the countQueryId to set
	 */
	public void setCountQueryId(String countQueryId) {
		this.countQueryId = countQueryId;
	}

	/**
	 * @return the partitionId
	 */
	public String getPartition() {
		return partition;
	}

	/**
	 * @param partitionId the partitionId to set
	 */
	public void setPartition(String partition) {
		this.partition = partition;
	}

	/**
	 * @return the searchcondition
	 */
	public String getSearchcondition() {
		return searchcondition;
	}

	/**
	 * @param searchcondition the searchcondition to set
	 */
	public void setSearchcondition(String searchcondition) {
		this.searchcondition = searchcondition;
	}

	/**
	 * @return the searchkeyword
	 */
	public String getSearchkeyword() {
		return searchkeyword;
	}

	/**
	 * @param searchkeyword the searchkeyword to set
	 */
	public void setSearchkeyword(String searchkeyword) {
		this.searchkeyword = searchkeyword;
	}

	/**
	 * @return the mandatesetting
	 */
	public String getMandatesetting() {
		return mandatesetting;
	}

	/**
	 * @param mandatesetting the mandatesetting to set
	 */
	public void setMandatesetting(String mandatesetting) {
		this.mandatesetting = mandatesetting;
	}

	/**
	 * @return the mandateresponsible
	 */
	public String getMandateresponsible() {
		return mandateresponsible;
	}

	/**
	 * @param mandateresponsible the mandateresponsible to set
	 */
	public void setMandateresponsible(String mandateresponsible) {
		this.mandateresponsible = mandateresponsible;
	}

	/**
	 * @return the mandatemsg
	 */
	public String getMandatemsg() {
		return mandatemsg;
	}

	/**
	 * @param mandatemsg the mandatemsg to set
	 */
	public void setMandatemsg(String mandatemsg) {
		this.mandatemsg = mandatemsg;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the requestorId
	 */
	public String getRequestorId() {
		return requestorId;
	}

	/**
	 * @param requestorId the requestorId to set
	 */
	public void setRequestorId(String requestorId) {
		this.requestorId = requestorId;
	}

	/**
	 * @return the startPeriod
	 */
	public String getStartPeriod() {
		return startPeriod;
	}

	/**
	 * @param startPeriod the startPeriod to set
	 */
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	/**
	 * @return the endPeriod
	 */
	public String getEndPeriod() {
		return endPeriod;
	}

	/**
	 * @param endPeriod the endPeriod to set
	 */
	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
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
	 * @return the searchperiod
	 */
	public String getSearchperiod() {
		return searchperiod;
	}

	/**
	 * @param searchperiod the searchperiod to set
	 */
	public void setSearchperiod(String searchperiod) {
		this.searchperiod = searchperiod;
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
	 * @return the processVer
	 */
	public String getProcessVer() {
		return processVer;
	}

	/**
	 * @param processVer the processVer to set
	 */
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}
	
	
}