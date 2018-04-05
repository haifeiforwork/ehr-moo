/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.seamless.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SeamlessmessageSearchCondition.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SeamlessmessageSearchCondition extends SearchCondition {

	private static final long serialVersionUID = 7610678047448389510L;

	private String searchColumn;
	
	private String searchWord;
	
	private String userId;
	
	private String messageId; 
	
	private String resultCode ;
	
	private String sendDate; 
	
	private String messageType; 
	
	private String guestId;
	
	private Integer rowNum ;
	
	private String userIdR;
	
	private String userIdS;
	
	private Integer startRnum ;
	
	private Integer endRnum ;
	
	private String boxType;
	
	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getUserIdR() {
		return userIdR;
	}

	public void setUserIdR(String userIdR) {
		this.userIdR = userIdR;
	}

	public String getUserIdS() {
		return userIdS;
	}

	public void setUserIdS(String userIdS) {
		this.userIdS = userIdS;
	}

	public Integer getStartRnum() {
		return startRnum;
	}

	public void setStartRnum(Integer startRnum) {
		this.startRnum = startRnum;
	}

	public Integer getEndRnum() {
		return endRnum;
	}

	public void setEndRnum(Integer endRnum) {
		this.endRnum = endRnum;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

}
