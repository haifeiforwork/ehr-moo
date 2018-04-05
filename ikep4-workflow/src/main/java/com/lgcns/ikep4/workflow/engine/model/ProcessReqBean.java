package com.lgcns.ikep4.workflow.engine.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박철순(sniper28@naver.com)
 * @version $Id: ActivityBean.java 오후 2:31:55
 */
public class ProcessReqBean extends BaseObject {

	private String processId = "";

	private String processVer = "";

	private String activityId = ""; // Activity Id

	private String instanceId = "";
	
	private String insLogId = "";

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
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @return the insLogId
	 */
	public String getInsLogId() {
		return insLogId;
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
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @param insLogId the insLogId to set
	 */
	public void setInsLogId(String insLogId) {
		this.insLogId = insLogId;
	}
	
	

}
