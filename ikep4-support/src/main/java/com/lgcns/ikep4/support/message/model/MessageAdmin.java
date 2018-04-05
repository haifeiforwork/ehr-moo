/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageAdmin.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageAdmin extends BaseObject {

	
	private static final long serialVersionUID = 5873445155002066405L;
	
	/**
	  * 포탈ID 
	  */
	private String portalId ;
	/**
	  * 월별 최대발송용량 
	  */
	private Integer maxMonthFilesize ;
	/**
	  * 보관함 최대 용량 
	  */
	private Integer maxStoredFilesize ;
	/**
	  * 건별 첨부 최대 용량 
	  */
	private Integer maxAttachFilesize ;
	/**
	  * 건별 최대 수신자수 
	  */
	private Integer maxReceiverCount ;
	/**
	  * 보관 만료 일수 
	  */
	private Integer keepDays ;
	/**
	  * 최대 디스크 용량 
	  */
	private Integer diskSize ;
	/**
	  * 관리자 알람 설정 
	  */
	private Integer managerAlarmRatio ;
	
	/**
	  * 월별 사용자 발송 용량 
	  */
	private Integer useMonthFilesize ;
	/**
	  * 보관함 사용자 용량 
	  */
	private Integer useStoredFilesize ;
	/**
	  * 사용월
	  */
	private Integer nowMonth ;
	
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public Integer getMaxMonthFilesize() {
		return maxMonthFilesize;
	}
	public void setMaxMonthFilesize(Integer maxMonthFilesize) {
		this.maxMonthFilesize = maxMonthFilesize;
	}
	public Integer getMaxStoredFilesize() {
		return maxStoredFilesize;
	}
	public void setMaxStoredFilesize(Integer maxStoredFilesize) {
		this.maxStoredFilesize = maxStoredFilesize;
	}
	public Integer getMaxAttachFilesize() {
		return maxAttachFilesize;
	}
	public void setMaxAttachFilesize(Integer maxAttachFilesize) {
		this.maxAttachFilesize = maxAttachFilesize;
	}
	public Integer getMaxReceiverCount() {
		return maxReceiverCount;
	}
	public void setMaxReceiverCount(Integer maxReceiverCount) {
		this.maxReceiverCount = maxReceiverCount;
	}
	public Integer getKeepDays() {
		return keepDays;
	}
	public void setKeepDays(Integer keepDays) {
		this.keepDays = keepDays;
	}
	public Integer getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}
	public Integer getManagerAlarmRatio() {
		return managerAlarmRatio;
	}
	public void setManagerAlarmRatio(Integer managerAlarmRatio) {
		this.managerAlarmRatio = managerAlarmRatio;
	}
	public Integer getUseMonthFilesize() {
		return useMonthFilesize;
	}
	public void setUseMonthFilesize(Integer useMonthFilesize) {
		this.useMonthFilesize = useMonthFilesize;
	}
	public Integer getUseStoredFilesize() {
		return useStoredFilesize;
	}
	public void setUseStoredFilesize(Integer useStoredFilesize) {
		this.useStoredFilesize = useStoredFilesize;
	}
	public Integer getNowMonth() {
		return nowMonth;
	}
	public void setNowMonth(Integer nowMonth) {
		this.nowMonth = nowMonth;
	}

}
