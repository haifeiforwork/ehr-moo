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
 * 워크플로우 - 현황관리 - 액티비티 조회조건
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminActivitySearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminActivitySearchCondition extends SearchCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String processId; // processId
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}
}
