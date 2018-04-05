package com.lgcns.ikep4.servicepack.seamless.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class AttachFile extends BaseObject {

	private static final long serialVersionUID = 5714935919164844938L;
	
	/**
	  * 메시지ID 
	  */
	private String messageId;
	
	/**
	  * 첨부파일 ID 
	  */
	private String attachId;
	
	/**
	  * 첨부파일 ID 
	  */
	private String attachName;
	
	/**
	  * 파일사이즈 
	  */
	private Integer attachSize;
	
	/**
	 * multipart에서 파일의 위치 경로
	 */
	private String multipartPath;


	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public Integer getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Integer attachSize) {
		this.attachSize = attachSize;
	}

	public String getMultipartPath() {
		return multipartPath;
	}

	public void setMultipartPath(String multipartPath) {
		this.multipartPath = multipartPath;
	}


}
