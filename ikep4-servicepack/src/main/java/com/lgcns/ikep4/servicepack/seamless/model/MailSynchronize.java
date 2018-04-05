package com.lgcns.ikep4.servicepack.seamless.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class MailSynchronize extends BaseObject {

	private static final long serialVersionUID = -1155193132128955446L;
	
	/**
	 * 소유자ID
	 */
	private String ownerId;
	/**
	 * 폴더 타입
	 */
	private String folderType;
	/**
	 * 등록일
	 */
	private Date registDate;
	/**
	 * 메세지타입
	 */
	private String messageType;
	/**
	 * 결과여부
	 */
	private Integer result;
	/**
	 * 결과 설명
	 */
	private String resultDescription;
	
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getFolderType() {
		return folderType;
	}
	public void setFolderType(String folderType) {
		this.folderType = folderType;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getResultDescription() {
		return resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

}
