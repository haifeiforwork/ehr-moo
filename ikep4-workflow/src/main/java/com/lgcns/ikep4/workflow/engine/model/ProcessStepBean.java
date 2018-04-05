/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박철순(sniper28@naver.com)
 * @version $Id: ProcessStepBean.java 오후 7:36:10
 */
public class ProcessStepBean extends BaseObject {
	private String processId = "";

	private String actId = "";

	private String actName = "";

	private String subType = "";

	private String actType = "";
	
	private String loopType = "";
	
	private String gatewayType = "";

	private String varDefine = "";

	private String nextActId = "";

	private String nextActName = "";

	private String nextSubType = "";

	private String nextActType = "";
	
	private String nextLoopType = "";
	
	private String nextGatewayType = "";

	private String conditionType = "";

	private String conditionExpression = "";

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @return the actId
	 */
	public String getActId() {
		return actId;
	}

	/**
	 * @return the actName
	 */
	public String getActName() {
		return actName;
	}

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * @return the actType
	 */
	public String getActType() {
		return actType;
	}

	/**
	 * @return the loopType
	 */
	public String getLoopType() {
		return loopType;
	}

	/**
	 * @return the gatewayType
	 */
	public String getGatewayType() {
		return gatewayType;
	}

	/**
	 * @return the varDefine
	 */
	public String getVarDefine() {
		return varDefine;
	}

	/**
	 * @return the nextActId
	 */
	public String getNextActId() {
		return nextActId;
	}

	/**
	 * @return the nextActName
	 */
	public String getNextActName() {
		return nextActName;
	}

	/**
	 * @return the nextSubType
	 */
	public String getNextSubType() {
		return nextSubType;
	}

	/**
	 * @return the nextActType
	 */
	public String getNextActType() {
		return nextActType;
	}

	/**
	 * @return the nextLoopType
	 */
	public String getNextLoopType() {
		return nextLoopType;
	}

	/**
	 * @return the nextGatewayType
	 */
	public String getNextGatewayType() {
		return nextGatewayType;
	}

	/**
	 * @return the conditionType
	 */
	public String getConditionType() {
		return conditionType;
	}

	/**
	 * @return the conditionExpression
	 */
	public String getConditionExpression() {
		return conditionExpression;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @param actId the actId to set
	 */
	public void setActId(String actId) {
		this.actId = actId;
	}

	/**
	 * @param actName the actName to set
	 */
	public void setActName(String actName) {
		this.actName = actName;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * @param actType the actType to set
	 */
	public void setActType(String actType) {
		this.actType = actType;
	}

	/**
	 * @param loopType the loopType to set
	 */
	public void setLoopType(String loopType) {
		this.loopType = loopType;
	}

	/**
	 * @param gatewayType the gatewayType to set
	 */
	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}

	/**
	 * @param varDefine the varDefine to set
	 */
	public void setVarDefine(String varDefine) {
		this.varDefine = varDefine;
	}

	/**
	 * @param nextActId the nextActId to set
	 */
	public void setNextActId(String nextActId) {
		this.nextActId = nextActId;
	}

	/**
	 * @param nextActName the nextActName to set
	 */
	public void setNextActName(String nextActName) {
		this.nextActName = nextActName;
	}

	/**
	 * @param nextSubType the nextSubType to set
	 */
	public void setNextSubType(String nextSubType) {
		this.nextSubType = nextSubType;
	}

	/**
	 * @param nextActType the nextActType to set
	 */
	public void setNextActType(String nextActType) {
		this.nextActType = nextActType;
	}

	/**
	 * @param nextLoopType the nextLoopType to set
	 */
	public void setNextLoopType(String nextLoopType) {
		this.nextLoopType = nextLoopType;
	}

	/**
	 * @param nextGatewayType the nextGatewayType to set
	 */
	public void setNextGatewayType(String nextGatewayType) {
		this.nextGatewayType = nextGatewayType;
	}

	/**
	 * @param conditionType the conditionType to set
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	/**
	 * @param conditionExpression the conditionExpression to set
	 */
	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}


	
	
	

}