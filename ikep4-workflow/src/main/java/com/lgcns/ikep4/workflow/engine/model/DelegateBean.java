package com.lgcns.ikep4.workflow.engine.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순
 * @version $Id: DelegateBean.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class DelegateBean extends BaseObject {

	static final long serialVersionUID = 3909204398342671514L;

	private String	seqId		= "";			
	
	private String 	userId		= "";
	
	private String 	userName	= "";
	
	private String  userEnglishName = "";
	
	private	String	isSetup		= "";
	
	private	Date 	startDate;
	
	private	Date 	endDate;
	
	private String 	startDateStr = "";
	
	private String 	endDateStr = "";
	
	private	String 	reasonComment	= "";
	
	private	String 	mandatorId		= "";
	
	private	String 	mandatorName	= "";
	
	private	String 	mandatorEnglishName = "";
	
	private	String 	instanceLogId = "";

	/**
	 * @return the seqId
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
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
	 * @return the isSetup
	 */
	public String getIsSetup() {
		return isSetup;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the startDateStr
	 */
	public String getStartDateStr() {
		return startDateStr;
	}

	/**
	 * @return the endDateStr
	 */
	public String getEndDateStr() {
		return endDateStr;
	}

	/**
	 * @return the reasonComment
	 */
	public String getReasonComment() {
		return reasonComment;
	}

	/**
	 * @return the mandatorId
	 */
	public String getMandatorId() {
		return mandatorId;
	}

	/**
	 * @return the mandatorName
	 */
	public String getMandatorName() {
		return mandatorName;
	}

	/**
	 * @return the mandatorEnglishName
	 */
	public String getMandatorEnglishName() {
		return mandatorEnglishName;
	}

	/**
	 * @return the instanceLogId
	 */
	public String getInstanceLogId() {
		return instanceLogId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @param isSetup the isSetup to set
	 */
	public void setIsSetup(String isSetup) {
		this.isSetup = isSetup;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param startDateStr the startDateStr to set
	 */
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	/**
	 * @param endDateStr the endDateStr to set
	 */
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	/**
	 * @param reasonComment the reasonComment to set
	 */
	public void setReasonComment(String reasonComment) {
		this.reasonComment = reasonComment;
	}

	/**
	 * @param mandatorId the mandatorId to set
	 */
	public void setMandatorId(String mandatorId) {
		this.mandatorId = mandatorId;
	}

	/**
	 * @param mandatorName the mandatorName to set
	 */
	public void setMandatorName(String mandatorName) {
		this.mandatorName = mandatorName;
	}

	/**
	 * @param mandatorEnglishName the mandatorEnglishName to set
	 */
	public void setMandatorEnglishName(String mandatorEnglishName) {
		this.mandatorEnglishName = mandatorEnglishName;
	}

	/**
	 * @param instanceLogId the instanceLogId to set
	 */
	public void setInstanceLogId(String instanceLogId) {
		this.instanceLogId = instanceLogId;
	}




}
