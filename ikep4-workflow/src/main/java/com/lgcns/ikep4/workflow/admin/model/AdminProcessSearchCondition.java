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
 * 워크플로우 - 현황관리 - 프로세스 조회조건
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminProcessSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminProcessSearchCondition extends SearchCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String processAdministrationSearchPartition = "all";      // Partition ComboBox 값
	
	private String processAdministrationSearchProcess = "all";        // Process ComboBox 값
	
	private String processAdministrationSearchDateFlag = "both";      // 배치일자 검색 구분
	
	private String processAdministrationSearchStartDate;              // 배치일자 시작일
	
	private String processAdministrationSearchEndDate;                // 배치일자 종료일
	
	private String processAdministrationSearchState = "all";          // 프로세스 상태
	
	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}

	public String getProcessAdministrationSearchDateFlag() {
		return processAdministrationSearchDateFlag;
	}

	public void setProcessAdministrationSearchDateFlag(String processAdministrationSearchDateFlag) {
		this.processAdministrationSearchDateFlag = processAdministrationSearchDateFlag;
	}

	public String getProcessAdministrationSearchStartDate() {
		return processAdministrationSearchStartDate;
	}

	public void setProcessAdministrationSearchStartDate(String processAdministrationSearchStartDate) {
		this.processAdministrationSearchStartDate = processAdministrationSearchStartDate;
	}

	public String getProcessAdministrationSearchEndDate() {
		return processAdministrationSearchEndDate;
	}

	public void setProcessAdministrationSearchEndDate(String processAdministrationSearchEndDate) {
		this.processAdministrationSearchEndDate = processAdministrationSearchEndDate;
	}

	public String getProcessAdministrationSearchState() {
		return processAdministrationSearchState;
	}

	public void setProcessAdministrationSearchState(String processAdministrationSearchState) {
		this.processAdministrationSearchState = processAdministrationSearchState;
	}

	public String getProcessAdministrationSearchPartition() {
		return processAdministrationSearchPartition;
	}

	public void setProcessAdministrationSearchPartition(String processAdministrationSearchPartition) {
		this.processAdministrationSearchPartition = processAdministrationSearchPartition;
	}

	public String getProcessAdministrationSearchProcess() {
		return processAdministrationSearchProcess;
	}

	public void setProcessAdministrationSearchProcess(String processAdministrationSearchProcess) {
		this.processAdministrationSearchProcess = processAdministrationSearchProcess;
	}
}
