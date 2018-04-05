package com.lgcns.ikep4.servicepack.seamless.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class SeamlessmessageAdmin extends BaseObject {

	private static final long serialVersionUID = -7231563853430543933L;

    /**
	  * 포탈ID 
	  */
	private String portalId ;
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
	  * imap mail 동기화 가져오기 최대 건수
	  */
	private Integer maxImapCount ;
	/**
	  * 수정시컬럼값
	  */
	private String columnName ;
	
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getMaxImapCount() {
		return maxImapCount;
	}
	public void setMaxImapCount(Integer maxImapCount) {
		this.maxImapCount = maxImapCount;
	}
}
