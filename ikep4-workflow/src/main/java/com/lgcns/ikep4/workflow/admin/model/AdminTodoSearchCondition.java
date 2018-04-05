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
 * 워크플로우 - 현황관리 - 업무관리 조회조건
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminTodoSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminTodoSearchCondition extends SearchCondition {
	
	private static final long serialVersionUID = 231767328317453351L;
	
	private String todoAdministrationSearchTitle; //제목조회
	
	private String todoAdministrationSearchUser;  //사용자 조회
	
	private String todoAdministrationSearchState = "all"; //상태 조회
	
	private String todoAdministrationSearchPartiton = "all"; //데시보드 업무현황 파티션 조회 (ComboBox)
	
	private String todoAdministrationSearchProcess = "all"; //데시보드 업무현황 프로세스 조회 (ComboBox)
	
	private String todoAdministrationSearchDateFlag = "both";  // 생성일자 검색 구분
	
	private String todoAdministrationSearchStartDate;              // 생성일자 시작일
	
	private String todoAdministrationSearchEndDate;                // 생성일자 종료일
	
	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}
	
	public String getTodoAdministrationSearchDateFlag() {
		return todoAdministrationSearchDateFlag;
	}

	public void setTodoAdministrationSearchDateFlag(String todoAdministrationSearchDateFlag) {
		this.todoAdministrationSearchDateFlag = todoAdministrationSearchDateFlag;
	}

	public String getTodoAdministrationSearchStartDate() {
		return todoAdministrationSearchStartDate;
	}

	public void setTodoAdministrationSearchStartDate(String todoAdministrationSearchStartDate) {
		this.todoAdministrationSearchStartDate = todoAdministrationSearchStartDate;
	}

	public String getTodoAdministrationSearchEndDate() {
		return todoAdministrationSearchEndDate;
	}

	public void setTodoAdministrationSearchEndDate(String todoAdministrationSearchEndDate) {
		this.todoAdministrationSearchEndDate = todoAdministrationSearchEndDate;
	}

	public String getTodoAdministrationSearchTitle() {
		return todoAdministrationSearchTitle;
	}

	public void setTodoAdministrationSearchTitle(String todoAdministrationSearchTitle) {
		this.todoAdministrationSearchTitle = todoAdministrationSearchTitle;
	}

	public String getTodoAdministrationSearchUser() {
		return todoAdministrationSearchUser;
	}

	public void setTodoAdministrationSearchUser(String todoAdministrationSearchUser) {
		this.todoAdministrationSearchUser = todoAdministrationSearchUser;
	}

	public String getTodoAdministrationSearchState() {
		return todoAdministrationSearchState;
	}

	public void setTodoAdministrationSearchState(String todoAdministrationSearchState) {
		this.todoAdministrationSearchState = todoAdministrationSearchState;
	}

	public String getTodoAdministrationSearchPartiton() {
		return todoAdministrationSearchPartiton;
	}

	public void setTodoAdministrationSearchPartiton(String todoAdministrationSearchPartiton) {
		this.todoAdministrationSearchPartiton = todoAdministrationSearchPartiton;
	}

	public String getTodoAdministrationSearchProcess() {
		return todoAdministrationSearchProcess;
	}

	public void setTodoAdministrationSearchProcess(String todoAdministrationSearchProcess) {
		this.todoAdministrationSearchProcess = todoAdministrationSearchProcess;
	}
}
