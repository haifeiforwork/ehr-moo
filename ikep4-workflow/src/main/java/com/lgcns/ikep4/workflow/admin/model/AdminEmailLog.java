/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: AdminEmailLog.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminEmailLog extends BaseObject {
	
	
	static final long serialVersionUID = 5023689993374905950L;

	/**
	 * 로그ID
	 */
	private Long logId;
	
	/**
	 * 발신인 ID
	 */
	private String senderId;
	
	/**
	 * 발신인 메일주소
	 */
	@NotEmpty
	private String senderEmail;
	
	/**
	 * 수신인 아이디
	 */
	private String receiver;
	
	/**
	 * 수신인 메일주소
	 */	
	private String receiverEmail;
	
	/**
	 * 참조인 메일주소
	 */	
	private String ccEmail;	

	/**
	 * 비밀 참조인 메일주소
	 */	
	private String bccEmail;	

	/**
	 * 메일 제목
	 */
	private String emailTitle;

	/**
	 * 메일 내용
	 */
	private String emailContent;
	
	/**
	 * 발신일자
	 */
	private String sendDate;
	
	/**
	 * 생성일자
	 */
	private String createDate;
	
	/**
	 * 발신 성공 여부
	 */
	private String isSuccess;
	
	/**
	 * 수신 유형 (TO,CC,BCC)
	 */
	private String receiveType;
	
	
	/**
	 * @return the logId;
	 */
	public Long getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(long logId) {
		this.logId = logId;
	}

	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the senderEmail
	 */
	public String getSenderEmail() {
		return senderEmail;
	}

	/**
	 * @param senderEmail the senderEmail to set
	 */
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	/**
	 * @return the receiver;
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the receiverEmail
	 */
	public String getReceiverEmail() {
		return receiverEmail;
	}

	/**
	 * @param receiverEmail the receiverEmail to set
	 */
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	/**
	 * @return the ccEmail
	 */
	public String getCcEmail() {
		return ccEmail;
	}

	/**
	 * @param ccEmail the ccEmail to set
	 */
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	/**
	 * @return the bccEmail
	 */
	public String getBccEmail() {
		return bccEmail;
	}

	/**
	 * @param bccEmail the bccEmail to set
	 */
	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}

	/**
	 * @return the emailTitle
	 */
	public String getEmailTitle() {
		return emailTitle;
	}

	/**
	 * @param emailTitle the emailTitle to set
	 */
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	/**
	 * @return the emailContent
	 */
	public String getEmailContent() {
		return emailContent;
	}

	/**
	 * @param emailContent the emailContent to set
	 */
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	/**
	 * @return the sendDate
	 */
	public String getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the isSuccess
	 */
	public String getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the receiveType
	 */
	public String getReceiveType() {
		return receiveType;
	}

	/**
	 * @param receiveType the receiveType to set
	 */
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

}
