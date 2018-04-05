/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 결재수신처
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApReceive.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApReceive extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4384194596792625462L;

	
	/**
	 * 결재문서ID
	 */
	//@NotEmpty
	private String apprId;

	/**
	 * 수신자ID
	 */
	private String receiveId;

	/**
	 * 발신유무
	 */
	private String isSend;
	
	/**
	 * 발신일자
	 */
	private Date sendDate;
	
	/**
	 * 결재첨부 문서ID
	 */
	private String apprRefId;
	

	/**
	 * @return the apprId
	 */
	public String getApprId() {
		return apprId;
	}

	/**
	 * @param apprId the apprId to set
	 */
	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	/**
	 * @return the receiveId
	 */
	public String getReceiveId() {
		return receiveId;
	}

	/**
	 * @param receiveId the receiveId to set
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	/**
	 * @return the isSend
	 */
	public String getIsSend() {
		return isSend;
	}

	/**
	 * @param isSend the isSend to set
	 */
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * @return the apprRefId
	 */
	public String getApprRefId() {
		return apprRefId;
	}

	/**
	 * @param apprRefId the apprRefId to set
	 */
	public void setApprRefId(String apprRefId) {
		this.apprRefId = apprRefId;
	}



}
