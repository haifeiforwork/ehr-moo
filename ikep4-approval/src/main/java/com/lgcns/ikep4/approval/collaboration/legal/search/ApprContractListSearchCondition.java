/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.legal.search;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.approval.collaboration.common.model.Code;
import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 게시글 검색조건 모델 클래스
 */
public class ApprContractListSearchCondition extends SearchCondition {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date   startDate;     //기안시작일자
	private Date   endDate;       //기안종료일자
	private String userName;      //의뢰자
	private String managerName;   //담당자
	private String processStatus; //진행상태
	private String loginEmpNo; 	  //로그인사번
	
	
	 /** 검색 컬럼. */
    private String searchColumn;
	
	
	//날짜시작
	private String searchStartRegDate;
	private String searchStartRegDate1;
	
	//날짜종료
	private String searchEndRegDate;
	private String searchEndRegDate1;
	
	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> pageNumList = Arrays.asList(new Code<Integer>(10, "10"), new Code<Integer>(15,
			"15"), new Code<Integer>(20, "20"), new Code<Integer>(30, "30"), new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> fileSizeList = Arrays.asList(new Code<Integer>(1 * 1024 * 1024, "1"),
			new Code<Integer>(2 * 1024 * 1024, "2"), new Code<Integer>(5 * 1024 * 1024, "5"), new Code<Integer>(
					10 * 1024 * 1024, "10"), new Code<Integer>(20 * 1024 * 1024, "20"));
	/**
	 * @return the pageNumList
	 */
	public List<Code<Integer>> getPageNumList() {
		return pageNumList;
	}

	/**
	 * @return the fileSizeList
	 */
	public List<Code<Integer>> getFileSizeList() {
		return fileSizeList;
	}

	

	
    @Override
    public Integer getDefaultPagePerRecord(){
        return 10;
    }
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getLoginEmpNo() {
		return loginEmpNo;
	}

	public void setLoginEmpNo(String loginEmpNo) {
		this.loginEmpNo = loginEmpNo;
	}

	public String getSearchStartRegDate() {
		return searchStartRegDate;
	}


	public void setSearchStartRegDate(String searchStartRegDate) {
		this.searchStartRegDate = searchStartRegDate;
	}


	public String getSearchStartRegDate1() {
		return searchStartRegDate1;
	}


	public void setSearchStartRegDate1(String searchStartRegDate1) {
		this.searchStartRegDate1 = searchStartRegDate1;
	}


	public String getSearchEndRegDate() {
		return searchEndRegDate;
	}


	public void setSearchEndRegDate(String searchEndRegDate) {
		this.searchEndRegDate = searchEndRegDate;
	}


	public String getSearchEndRegDate1() {
		return searchEndRegDate1;
	}


	public void setSearchEndRegDate1(String searchEndRegDate1) {
		this.searchEndRegDate1 = searchEndRegDate1;
	}


	public String getSearchColumn() {
		return searchColumn;
	}


	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}
	

}