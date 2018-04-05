package com.lgcns.ikep4.servicepack.seamless.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class SeamlessmessageUserSeting extends BaseObject {

	private static final long serialVersionUID = 5365777630109111598L;
	
	/**
     * 등록자 ID
     */
	private String registerId;

    /**
     * 설정여부
     */
    private Integer isSourceDelete;
    
    /**
     * 메일 자동접속여부
     */
    private Integer autoConnect;

    /**
     * 등록일자
     */
    private Date registDate;

    /**
     * 수정자 ID
     */
    private String updaterId;

    /**
     * 수정일자
     */
    private Date updateDate;
    
    /**
     * 받은 매일함 동기화 중 여부 (1 작업중 0 작업완료)
     */
    private Integer inboxSyncComplete;
    
    /**
     * 받은 매일함 동기화 일자
     */
    private Date inboxSyncDate;
    
    /**
     * 보낸 매일함 동기화 중 여부 (1 작업중 0 작업완료)
     */
    private Integer sentSyncComplete;
    
    /**
     * 보낸 매일함 동기화 일자
     */
    private Date sentSyncDate;

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getIsSourceDelete() {
		return isSourceDelete;
	}

	public void setIsSourceDelete(Integer isSourceDelete) {
		this.isSourceDelete = isSourceDelete;
	}

	public Integer getAutoConnect() {
		return autoConnect;
	}

	public void setAutoConnect(Integer autoConnect) {
		this.autoConnect = autoConnect;
	}

	public Integer getInboxSyncComplete() {
		return inboxSyncComplete;
	}

	public void setInboxSyncComplete(Integer inboxSyncComplete) {
		this.inboxSyncComplete = inboxSyncComplete;
	}

	public Date getInboxSyncDate() {
		return inboxSyncDate;
	}

	public void setInboxSyncDate(Date inboxSyncDate) {
		this.inboxSyncDate = inboxSyncDate;
	}

	public Integer getSentSyncComplete() {
		return sentSyncComplete;
	}

	public void setSentSyncComplete(Integer sentSyncComplete) {
		this.sentSyncComplete = sentSyncComplete;
	}

	public Date getSentSyncDate() {
		return sentSyncDate;
	}

	public void setSentSyncDate(Date sentSyncDate) {
		this.sentSyncDate = sentSyncDate;
	}

}
