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


/**
 * TODO Javadoc주석작성
 * 
 * @author 박철순
 * @version $Id: InstanceVarBean.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class InstanceVarBean extends BaseObject {

	private String seqId = "";
	
	private String instanceId = "";

	private String processId = "";
	
	private String processVer = "";

	private String varId = "";

	private String varName = "";
	
	private String varType = "";
	
	private String varKey = "";
	
	private String varValue = "";
	
	private Date createDate;
	
	private Date updateDate;

	/**
	 * @return the seqId
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @return the processVer
	 */
	public String getProcessVer() {
		return processVer;
	}

	/**
	 * @return the varId
	 */
	public String getVarId() {
		return varId;
	}

	/**
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * @return the varType
	 */
	public String getVarType() {
		return varType;
	}

	/**
	 * @return the varKey
	 */
	public String getVarKey() {
		return varKey;
	}

	/**
	 * @return the varValue
	 */
	public String getVarValue() {
		return varValue;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @param processVer the processVer to set
	 */
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}

	/**
	 * @param varId the varId to set
	 */
	public void setVarId(String varId) {
		this.varId = varId;
	}

	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/**
	 * @param varType the varType to set
	 */
	public void setVarType(String varType) {
		this.varType = varType;
	}

	/**
	 * @param varKey the varKey to set
	 */
	public void setVarKey(String varKey) {
		this.varKey = varKey;
	}

	/**
	 * @param varValue the varValue to set
	 */
	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
	
	

}