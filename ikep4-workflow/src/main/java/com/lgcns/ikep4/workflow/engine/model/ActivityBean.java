package com.lgcns.ikep4.workflow.engine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박철순(sniper28@naver.com)
 * @version $Id: ActivityBean.java 오후 2:31:55
 */
public class ActivityBean extends BaseObject {

	private String processId = "";

	private String processVer = "";

	private String activityId = ""; // Activity Id

	private String activityName = "";

	private String activityType = ""; // [Human | SubWorkflow]

	private String gatewayType = "";

	private String subType = "";

	private String loopType = "";

	private String startMode = "";

	private String endMode = "";

	private String varDefine = "";

	private String attrExpand = "";

	private String description = "";

	private String subProcessId = "";
	
	private Date createDate;
	
	private String screenUrl = "";

	private Map<String, DatafieldBean> inputSetHsh = new HashMap<String, DatafieldBean>();

	private Map<String, DatafieldBean> outputSetHsh = new HashMap<String, DatafieldBean>();
	
	private List<NotifyBean> notifyList 	= new ArrayList<NotifyBean>();
	
	private List<String> participantList	= new ArrayList<String>();
	

	public ActivityBean() {

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
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @return the gatewayType
	 */
	public String getGatewayType() {
		return gatewayType;
	}

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * @return the loopType
	 */
	public String getLoopType() {
		return loopType;
	}

	/**
	 * @return the startMode
	 */
	public String getStartMode() {
		return startMode;
	}

	/**
	 * @return the endMode
	 */
	public String getEndMode() {
		return endMode;
	}

	/**
	 * @return the varDefine
	 */
	public String getVarDefine() {
		return varDefine;
	}

	/**
	 * @return the attrExpand
	 */
	public String getAttrExpand() {
		return attrExpand;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the subProcessId
	 */
	public String getSubProcessId() {
		return subProcessId;
	}
	
	
	
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * @return the screenUrl
	 */
	public String getScreenUrl() {
		return screenUrl;
	}	

	/**
	 * @return the participantList
	 */
	public List<String> getParticipantList() {
		return participantList;
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param index
	 * @return
	 */
	public String getParticipant(int index) {
		return participantList.get(index).toString();
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @return
	 */
	public List<NotifyBean> getNotifyList() {
		return notifyList;
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param index
	 * @return
	 */
	public String getNotify(int index) {
		return notifyList.get(index).toString();
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
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @param gatewayType the gatewayType to set
	 */
	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * @param loopType the loopType to set
	 */
	public void setLoopType(String loopType) {
		this.loopType = loopType;
	}

	/**
	 * @param startMode the startMode to set
	 */
	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}

	/**
	 * @param endMode the endMode to set
	 */
	public void setEndMode(String endMode) {
		this.endMode = endMode;
	}

	/**
	 * @param varDefine the varDefine to set
	 */
	public void setVarDefine(String varDefine) {
		this.varDefine = varDefine;
	}

	/**
	 * @param attrExpand the attrExpand to set
	 */
	public void setAttrExpand(String attrExpand) {
		this.attrExpand = attrExpand;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param subProcessId the subProcessId to set
	 */
	public void setSubProcessId(String subProcessId) {
		this.subProcessId = subProcessId;
	}

	
	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * @param screenUrl the screenUrl to set
	 */
	public void setScreenUrl(String screenUrl) {
		this.screenUrl = screenUrl;
	}	

	/**
	 * @return
	 */
	public Map<String, DatafieldBean> getInputSet() {
		return inputSetHsh;
	}

	/**
	 * @return
	 */
	public Map<String, DatafieldBean> getOutputSet() {
		return outputSetHsh;
	}

	/**
	 * @param datafieldName
	 * @param datafieldBean
	 */
	public void addInputSet(String datafieldName, DatafieldBean datafieldBean) {
		inputSetHsh.put(datafieldName, datafieldBean);
	}

	/**
	 * @param datafieldName
	 * @param datafieldBean
	 */
	public void addOutputSet(String datafieldName, DatafieldBean datafieldBean) {
		outputSetHsh.put(datafieldName, datafieldBean);
	}
	
	
	/**
	 * @param datafieldName
	 * @return
	 */
	public DatafieldBean getInputDatafieldBean(String datafieldName) {
		return (DatafieldBean) inputSetHsh.get(datafieldName);
	}

	/**
	 * @param datafieldName
	 * @return
	 */
	public DatafieldBean getOutputDatafieldBean(String datafieldName) {
		return (DatafieldBean) outputSetHsh.get(datafieldName);
	}


	/**
	 * @param participantList the participantList to set
	 */
	public void setNotifyList(List<NotifyBean> notifyList) {
		this.notifyList = notifyList;
	}

	/**
	 * TODO Javadoc주석작성
	 * @param participant
	 */
	public void addNotify(NotifyBean notifyBean) {
		notifyList.add(notifyList.size(), notifyBean);
	}	
	
	
	
	/**
	 * @param participantList the participantList to set
	 */
	public void setParticipantList(List<String> participantList) {
		this.participantList = participantList;
	}

	/**
	 * TODO Javadoc주석작성
	 * @param participant
	 */
	public void setParticipant(String participant) {
		participantList.add(participantList.size(), participant);
	}

	
}
