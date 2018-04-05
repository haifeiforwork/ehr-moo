/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_ACTIVITY 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminProcessDash.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminProcessDash extends BaseObject{
	
	private String 	processId	= "";
	
	private String 	processName	= "";
	
	private	String	processCount = "";

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @return the processCount
	 */
	public String getProcessCount() {
		return processCount;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @param processCount the processCount to set
	 */
	public void setProcessCount(String processCount) {
		this.processCount = processCount;
	}
	
	
	
}
