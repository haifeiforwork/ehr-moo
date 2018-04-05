/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;



public class SplitTrackBean extends BaseObject {

	private String splitSeq			= "";
	
	private String instanceId		= "";
	
	private String splitId			= "";
	
	private String parentSplitId	= "";
	
	private String logId			= "";
	
	private String isComplete		= "";

	/**
	 * @return the splitSeq
	 */
	public String getSplitSeq() {
		return splitSeq;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @return the splitId
	 */
	public String getSplitId() {
		return splitId;
	}

	/**
	 * @return the parentSplitId
	 */
	public String getParentSplitId() {
		return parentSplitId;
	}

	/**
	 * @return the logId
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * @return the isComplete
	 */
	public String getIsComplete() {
		return isComplete;
	}

	/**
	 * @param splitSeq the splitSeq to set
	 */
	public void setSplitSeq(String splitSeq) {
		this.splitSeq = splitSeq;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @param splitId the splitId to set
	 */
	public void setSplitId(String splitId) {
		this.splitId = splitId;
	}

	/**
	 * @param parentSplitId the parentSplitId to set
	 */
	public void setParentSplitId(String parentSplitId) {
		this.parentSplitId = parentSplitId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * @param isComplete the isComplete to set
	 */
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	
	
	
	
	
	
}