/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.batch;

/**
 * Knowledge Monitor KnowledgeMonitorBatchCondition model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorBatchCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorBatchCondition {

	/**
	 * 작업일자 (yyyymmdd 형식)
	 */
	private String workDate;

	/**
	 * 모듈
	 */
	private String moduleCode;

	/**
	 * Constructor
	 */
	public KnowledgeMonitorBatchCondition() {
	}

	/**
	 * Constructor
	 * @param workDate
	 */
	public KnowledgeMonitorBatchCondition(String workDate) {
		this.workDate = workDate;
	}

	/**
	 * Constructor
	 * @param workDate
	 * @param moduleCode
	 */
	public KnowledgeMonitorBatchCondition(String workDate, String moduleCode) {
		this.workDate = workDate;
		this.moduleCode = moduleCode;
	}

	/**
	 * @return the workDate
	 */
	public String getWorkDate() {
		return workDate;
	}

	/**
	 * @param workDate the workDate to set
	 */
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}
