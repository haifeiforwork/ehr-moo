package com.lgcns.ikep4.support.message.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class MessageAlarm extends BaseObject {

	private static final long serialVersionUID = 7408138196175087947L;
	
	/**
	  * 메시지ID 
	  */
	private String messageId ;
	
	/**
	  * USER ID 
	  */
	private String userId ;
	
	/**
	  * 신규메세지 수
	  */
	private Integer newCount ;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getNewCount() {
		return newCount;
	}

	public void setNewCount(Integer newCount) {
		this.newCount = newCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
