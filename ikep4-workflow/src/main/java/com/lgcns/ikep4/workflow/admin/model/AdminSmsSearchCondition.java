/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 워크플로우 - 알림현황관리 - SMS관리 조회조건
 *
 * @author 이재경
 * @version $Id: AdminSmsSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminSmsSearchCondition extends SearchCondition {
	
	static final long serialVersionUID = 231767328317453351L;

	private String title; //제목조회
	
	private String startPeriod; //시작일
	private String endPeriod;	//종료일
	
	private String searchkeyword;	//검색어
	private String searchcondition;	//검색조건
	
	
	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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

}
