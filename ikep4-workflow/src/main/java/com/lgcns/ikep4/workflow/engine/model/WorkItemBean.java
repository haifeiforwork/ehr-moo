package com.lgcns.ikep4.workflow.engine.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순
 * @version $Id: WorkItemBean.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class WorkItemBean extends BaseObject {
	
	static final long serialVersionUID = 8987323362198717041L;

	private String partitionId = "";	// 파티션 ID

	private String partitionName = "";

	private String processId = "";
	
	private String processVer = "";
	
	private String processName = "";

	private String activityId = "";

	private String activityName = "";

	private String instanceId = "";
	
	private String miSeq = "";
	
	private String miBlock = "";

	private String insLogId = "";

	private String type = "";

	private String state = "";
	
	private String stateName = "";

	private String title = "";

	private String userId = "";

	private String requestorId = "";

	private String userName = "";
	
	private String userEnglishName = "";
	
	private String jobTitleName = "";
	
	private String instanceAuthor = "";
	
	private String instanceAuthorName = "";
	
	private String delegatorId = "";

	private String orgId = "";

	private String orgName = "";
	
	private String varDefine = "";
	
	private String varSchema = "";

	private Date createDate;

	private Date assignDate;

	private Date selectDate;

	private Date dueDate;

	private Date finishedDate;
	
	private Date procStartDate;
	
	private Date procEndDate;
	
	private String appKey01	= "";
	
	private String appKey02	= "";
	
	private String appKey03	= "";
	
	private String appKey04	= "";
	
	private String appKey05	= "";
	
	private String appKey06	= "";
	
	private String appKey07	= "";
	
	private String appKey08	= "";
	
	private String appKey09	= "";
	
	private String appKey10	= "";
	
	private String screenUrl = "";
	
	private String resultMsg = "";
	
	private String formTypeName = "";

	/**
	 * @return the partitionId
	 */
	public String getPartitionId() {
		return partitionId;
	}

	/**
	 * @return the partitionName
	 */
	public String getPartitionName() {
		return partitionName;
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
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
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
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @return the miSeq
	 */
	public String getMiSeq() {
		return miSeq;
	}

	/**
	 * @return the miBlock
	 */
	public String getMiBlock() {
		return miBlock;
	}

	/**
	 * @return the insLogId
	 */
	public String getInsLogId() {
		return insLogId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the requestorId
	 */
	public String getRequestorId() {
		return requestorId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}

	/**
	 * @return the instanceAuthor
	 */
	public String getInstanceAuthor() {
		return instanceAuthor;
	}

	/**
	 * @return the instanceAuthorName
	 */
	public String getInstanceAuthorName() {
		return instanceAuthorName;
	}

	/**
	 * @return the delegatorId
	 */
	public String getDelegatorId() {
		return delegatorId;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @return the varDefine
	 */
	public String getVarDefine() {
		return varDefine;
	}

	/**
	 * @return the varSchema
	 */
	public String getVarSchema() {
		return varSchema;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}

	/**
	 * @return the selectDate
	 */
	public Date getSelectDate() {
		return selectDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return the finishedDate
	 */
	public Date getFinishedDate() {
		return finishedDate;
	}

	/**
	 * @return the procStartDate
	 */
	public Date getProcStartDate() {
		return procStartDate;
	}

	/**
	 * @return the procEndDate
	 */
	public Date getProcEndDate() {
		return procEndDate;
	}

	/**
	 * @return the appKey01
	 */
	public String getAppKey01() {
		return appKey01;
	}

	/**
	 * @return the appKey02
	 */
	public String getAppKey02() {
		return appKey02;
	}

	/**
	 * @return the appKey03
	 */
	public String getAppKey03() {
		return appKey03;
	}

	/**
	 * @return the appKey04
	 */
	public String getAppKey04() {
		return appKey04;
	}

	/**
	 * @return the appKey05
	 */
	public String getAppKey05() {
		return appKey05;
	}

	/**
	 * @return the appKey06
	 */
	public String getAppKey06() {
		return appKey06;
	}

	/**
	 * @return the appKey07
	 */
	public String getAppKey07() {
		return appKey07;
	}

	/**
	 * @return the appKey08
	 */
	public String getAppKey08() {
		return appKey08;
	}

	/**
	 * @return the appKey09
	 */
	public String getAppKey09() {
		return appKey09;
	}

	/**
	 * @return the appKey10
	 */
	public String getAppKey10() {
		return appKey10;
	}

	/**
	 * @return the screenUrl
	 */
	public String getScreenUrl() {
		return screenUrl;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @return the formTypeName
	 */
	public String getFormTypeName() {
		return formTypeName;
	}

	/**
	 * @param partitionId the partitionId to set
	 */
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	/**
	 * @param partitionName the partitionName to set
	 */
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
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
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
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
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @param miSeq the miSeq to set
	 */
	public void setMiSeq(String miSeq) {
		this.miSeq = miSeq;
	}

	/**
	 * @param miBlock the miBlock to set
	 */
	public void setMiBlock(String miBlock) {
		this.miBlock = miBlock;
	}

	/**
	 * @param insLogId the insLogId to set
	 */
	public void setInsLogId(String insLogId) {
		this.insLogId = insLogId;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param requestorId the requestorId to set
	 */
	public void setRequestorId(String requestorId) {
		this.requestorId = requestorId;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param userEnglishName the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	/**
	 * @param instanceAuthor the instanceAuthor to set
	 */
	public void setInstanceAuthor(String instanceAuthor) {
		this.instanceAuthor = instanceAuthor;
	}

	/**
	 * @param instanceAuthorName the instanceAuthorName to set
	 */
	public void setInstanceAuthorName(String instanceAuthorName) {
		this.instanceAuthorName = instanceAuthorName;
	}

	/**
	 * @param delegatorId the delegatorId to set
	 */
	public void setDelegatorId(String delegatorId) {
		this.delegatorId = delegatorId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @param varDefine the varDefine to set
	 */
	public void setVarDefine(String varDefine) {
		this.varDefine = varDefine;
	}

	/**
	 * @param varSchema the varSchema to set
	 */
	public void setVarSchema(String varSchema) {
		this.varSchema = varSchema;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @param selectDate the selectDate to set
	 */
	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param finishedDate the finishedDate to set
	 */
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	/**
	 * @param procStartDate the procStartDate to set
	 */
	public void setProcStartDate(Date procStartDate) {
		this.procStartDate = procStartDate;
	}

	/**
	 * @param procEndDate the procEndDate to set
	 */
	public void setProcEndDate(Date procEndDate) {
		this.procEndDate = procEndDate;
	}

	/**
	 * @param appKey01 the appKey01 to set
	 */
	public void setAppKey01(String appKey01) {
		this.appKey01 = appKey01;
	}

	/**
	 * @param appKey02 the appKey02 to set
	 */
	public void setAppKey02(String appKey02) {
		this.appKey02 = appKey02;
	}

	/**
	 * @param appKey03 the appKey03 to set
	 */
	public void setAppKey03(String appKey03) {
		this.appKey03 = appKey03;
	}

	/**
	 * @param appKey04 the appKey04 to set
	 */
	public void setAppKey04(String appKey04) {
		this.appKey04 = appKey04;
	}

	/**
	 * @param appKey05 the appKey05 to set
	 */
	public void setAppKey05(String appKey05) {
		this.appKey05 = appKey05;
	}

	/**
	 * @param appKey06 the appKey06 to set
	 */
	public void setAppKey06(String appKey06) {
		this.appKey06 = appKey06;
	}

	/**
	 * @param appKey07 the appKey07 to set
	 */
	public void setAppKey07(String appKey07) {
		this.appKey07 = appKey07;
	}

	/**
	 * @param appKey08 the appKey08 to set
	 */
	public void setAppKey08(String appKey08) {
		this.appKey08 = appKey08;
	}

	/**
	 * @param appKey09 the appKey09 to set
	 */
	public void setAppKey09(String appKey09) {
		this.appKey09 = appKey09;
	}

	/**
	 * @param appKey10 the appKey10 to set
	 */
	public void setAppKey10(String appKey10) {
		this.appKey10 = appKey10;
	}

	/**
	 * @param screenUrl the screenUrl to set
	 */
	public void setScreenUrl(String screenUrl) {
		this.screenUrl = screenUrl;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @param formTypeName the formTypeName to set
	 */
	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}

	
	
	
	
}