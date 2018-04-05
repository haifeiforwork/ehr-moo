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
 * 워크플로우 - 현황관리 - 인스턴스 조회조건
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminInstanceSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminInstanceSearchCondition extends SearchCondition{
	
	private static final long serialVersionUID = 231767328317453351L;
	
	private String instanceAdministrationSearchPartition = "all"; // Partition ComboBox 값
	
	private String instanceAdministrationSearchProcess = "all";   // Process ComboBox 값
	
	private String instanceAdministrationSearchTitle;             // Title검색
	
	private String instanceAdministrationSearchDateFlag = "both";  // 생성일자 검색 구분
	
	private String instanceAdministrationSearchStartDate;         // 생성일자 시작일
	
	private String instanceAdministrationSearchEndDate;           // 생성일자 종료일
	
	private String instanceAdministrationSearchState = "RUNNING";     // 인스턴스 상태
	
	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}
	
	public String getInstanceAdministrationSearchDateFlag() {
		return instanceAdministrationSearchDateFlag;
	}

	public void setInstanceAdministrationSearchDateFlag(String instanceAdministrationSearchDateFlag) {
		this.instanceAdministrationSearchDateFlag = instanceAdministrationSearchDateFlag;
	}

	public String getInstanceAdministrationSearchStartDate() {
		return instanceAdministrationSearchStartDate;
	}



	public void setInstanceAdministrationSearchStartDate(String instanceAdministrationSearchStartDate) {
		this.instanceAdministrationSearchStartDate = instanceAdministrationSearchStartDate;
	}



	public String getInstanceAdministrationSearchEndDate() {
		return instanceAdministrationSearchEndDate;
	}



	public void setInstanceAdministrationSearchEndDate(String instanceAdministrationSearchEndDate) {
		this.instanceAdministrationSearchEndDate = instanceAdministrationSearchEndDate;
	}



	public String getInstanceAdministrationSearchState() {
		return instanceAdministrationSearchState;
	}



	public void setInstanceAdministrationSearchState(String instanceAdministrationSearchState) {
		this.instanceAdministrationSearchState = instanceAdministrationSearchState;
	}



	public String getInstanceAdministrationSearchPartition() {
		return instanceAdministrationSearchPartition;
	}

	public void setInstanceAdministrationSearchPartition(String instanceAdministrationSearchPartition) {
		this.instanceAdministrationSearchPartition = instanceAdministrationSearchPartition;
	}

	public String getInstanceAdministrationSearchProcess() {
		return instanceAdministrationSearchProcess;
	}

	public void setInstanceAdministrationSearchProcess(String instanceAdministrationSearchProcess) {
		this.instanceAdministrationSearchProcess = instanceAdministrationSearchProcess;
	}

	public String getInstanceAdministrationSearchTitle() {
		return instanceAdministrationSearchTitle;
	}

	public void setInstanceAdministrationSearchTitle(String instanceAdministrationSearchTitle) {
		this.instanceAdministrationSearchTitle = instanceAdministrationSearchTitle;
	}
}
