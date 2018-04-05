package com.lgcns.ikep4.servicepack.seamless.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Recipient extends BaseObject {

	private static final long serialVersionUID = 3835529807904347651L;

	/**
	  * 메시지ID 
	  */
	private String messageId ;
	/**
	  * 수신자ID 
	  */
	private String receiverId ;
	/**
	  * 수신자_메일 
	  */
	private String receiverMail ;
	/**
	  * 수신자_이름 
	  */
	private String receiverName ;
	/**
	  * 수신자_타입 
	  */
	private String receiverType ;
	/**
	  * 수신_타입 
	  */
	private String receiveType ;
	/**
	  * 수신_일자 
	  */
	private Date receiveDate ;
	/**
	  * 읽은_일자 
	  */
	private Date readDate ;
	 
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverMail() {
		return receiverMail;
	}
	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}


}
