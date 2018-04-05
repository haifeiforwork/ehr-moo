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
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: NotifyBean.java 16245 2011-08-18 04:28:59Z giljae $ NotifyBean.java 오후 1:08:58
 */
public class NotifyBean extends BaseObject {

	private String notifySeq = "";

	private String activityId = "";

	private String processId = "";

	private String processVer = "";

	private String notifyPoint = "";
	
	private String notifyType = "";

	private String receiverId = "";

	private String mobileNumber = "";

	private String emailType = "";

	private String emailAddress = "";

	private String notifyTitle = "";

	private String notifyContent = "";

	private Date createDate;

	/**
	 * @return the notifySeq
	 */
	public String getNotifySeq() {
		return notifySeq;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
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
	 * @return the notifyPoint
	 */
	public String getNotifyPoint() {
		return notifyPoint;
	}

	/**
	 * @return the notifyType
	 */
	public String getNotifyType() {
		return notifyType;
	}

	/**
	 * @return the receiverId
	 */
	public String getReceiverId() {
		return receiverId;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @return the emailType
	 */
	public String getEmailType() {
		return emailType;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the notifyTitle
	 */
	public String getNotifyTitle() {
		return notifyTitle;
	}

	/**
	 * @return the notifyContent
	 */
	public String getNotifyContent() {
		return notifyContent;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param notifySeq the notifySeq to set
	 */
	public void setNotifySeq(String notifySeq) {
		this.notifySeq = notifySeq;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
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
	 * @param notifyPoint the notifyPoint to set
	 */
	public void setNotifyPoint(String notifyPoint) {
		this.notifyPoint = notifyPoint;
	}

	/**
	 * @param notifyType the notifyType to set
	 */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @param emailType the emailType to set
	 */
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @param notifyTitle the notifyTitle to set
	 */
	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}

	/**
	 * @param notifyContent the notifyContent to set
	 */
	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
	
}
