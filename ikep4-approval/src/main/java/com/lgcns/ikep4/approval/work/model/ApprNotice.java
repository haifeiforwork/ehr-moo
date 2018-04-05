/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.model;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.approval.admin.model.Code;

/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 서지혜
 * @version $Id$
 */
 
public class ApprNotice extends BaseObject {

	
	private static final long serialVersionUID = 8900711193377442735L;


	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 사용자
	 */
	private String userId;
	
	/**
	 * 사용유무(0:사용안함,1:사용)
	 */
	private String usage;
	
	/**
	 * 결재문서 알림 방식 (0:메일, 1:SMS, 2:메신저, 3:메일/SMS, 4:메일/메신저, 5:SMS/메신저, 6;메일/SMS/메신저)
	 */
	private String 	noticeMethod;
	
	/**
	 * 결재문서 도착 알림 사용 여부
	 */
	private String 	noticeArrival;
	
	/**
	 * 결재문서 완료 알림 사용 여부
	 */
	private String 	noticeFinish;
	
	/**
	 * 수신문서 반려 알림 사용 여부
	 */
	private String 	noticeReject;
	
	/**
	 * 수신문서 회송 알림 사용 여부
	 */
	private String 	noticeReturn;
	
	
	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;
	
	/**
	 * 수정일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date updateDate;
	
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> METHOD_LIST = Arrays.asList(
			new Code<String>("0",  "ui.approval.work.config.notice.alram.mail"),
			new Code<String>("1",  "ui.approval.work.config.notice.alram.sms"),
			new Code<String>("2",  "ui.approval.work.config.notice.alram.msg")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> USAGE_LIST = Arrays.asList(
			new Code<String>("1",  "ui.approval.work.config.notice.alram.useY"),
			new Code<String>("0",  "ui.approval.work.config.notice.alram.useN")
	);

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUsage() {
		return usage;
	}


	public void setUsage(String usage) {
		this.usage = usage;
	}


	public String getNoticeMethod() {
		return noticeMethod;
	}


	public void setNoticeMethod(String noticeMethod) {
		this.noticeMethod = noticeMethod;
	}


	public String getNoticeArrival() {
		return noticeArrival;
	}


	public void setNoticeArrival(String noticeArrival) {
		this.noticeArrival = noticeArrival;
	}


	public String getNoticeFinish() {
		return noticeFinish;
	}


	public void setNoticeFinish(String noticeFinish) {
		this.noticeFinish = noticeFinish;
	}


	public String getNoticeReject() {
		return noticeReject;
	}


	public void setNoticeReject(String noticeReject) {
		this.noticeReject = noticeReject;
	}


	public String getNoticeReturn() {
		return noticeReturn;
	}


	public void setNoticeReturn(String noticeReturn) {
		this.noticeReturn = noticeReturn;
	}


	public Date getRegistDate() {
		return registDate;
	}


	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public static List<Code<String>> getMethodList() {
		return METHOD_LIST;
	}


	public static List<Code<String>> getUsageList() {
		return USAGE_LIST;
	}


	public String getPortalId() {
		return portalId;
	}


	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

}
