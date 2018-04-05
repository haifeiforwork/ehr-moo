package com.lgcns.ikep4.servicepack.survey.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class SurveyJobDetailBean extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String jobName;
	private String description;
	private String jobClassName;
    
    private String triggerName;
    private String cronExpression;
    
    private String useYn;
    private String jobSeq;
    private String isCheck;
    
    @SuppressWarnings("rawtypes")
	private Map jobDataAsMap = new HashMap();
    private int priority;
    private Date startTime;
    private long repeatInterval;
    private int repeatCount;
    
    
    
    
 
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getJobSeq() {
		return jobSeq;
	}
	public void setJobSeq(String jobSeq) {
		this.jobSeq = jobSeq;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	@SuppressWarnings("rawtypes")
	public Map getJobDataAsMap() {
		return jobDataAsMap;
	}
	@SuppressWarnings("rawtypes")
	public void setJobDataAsMap(Map jobDataAsMap) {
		this.jobDataAsMap = jobDataAsMap;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public long getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public int getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
	
}
