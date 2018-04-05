package com.lgcns.ikep4.support.activitystream.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * ActivityDelLog
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityDelLog.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ActivityDelLog extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -2452069888889922917L;

	/**
	 * id
	 */
	private String logId;

	/**
	 * user id
	 */
	private String userId;

	/**
	 * 성공여부
	 */
	private int success;

	/**
	 * 삭제갯수
	 */
	private int deleteCount;

	/**
	 * 년
	 */
	private String year;

	/**
	 * 월
	 */
	private String month;

	/**
	 * 시작일
	 */
	private Date startTime;

	/**
	 * 종료일
	 */
	private Date endTime;

	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * config value
	 */
	private int configValue;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public int getDeleteCount() {
		return deleteCount;
	}

	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}

	public int getConfigValue() {
		return configValue;
	}

	public void setConfigValue(int configValue) {
		this.configValue = configValue;
	}
	
	

}
