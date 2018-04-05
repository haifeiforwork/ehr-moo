/* 
 * Copyright (C) 2013 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.util.restful.model.param;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * File Param
 * 
 * @author jmjeon
 * @since 2013. 2. 28. 오전 11:03:39
 * @version 1.0.0 
 */
@XmlRootElement(name="FileParam")
public class FileParam {

	private static final long serialVersionUID = 1L;

	/**
	 * 첨부파일명
	 */
	private String attachFileName;
	
	/**
	 * 첨부파일URL
	 */
	private String attachFileUrl;
	
	/**
	 * 첨부파일 id
	 */
	private String attachFileId;
	
	/**
	 * 첨부파일 크기
	 */
	private int attachFileSize;
	
	/**
	 * 첨부서버저장파일명
	 */
	private String attachFileStoredName;
	
	/**
	 * 첨부파일FULL URL
	 */
	private String attachTargetUrl;
	
	
	/**
	 * @return the attachFileName
	 */
	public String getAttachFileName() {
		return attachFileName;
	}
	
	/**
	 * @param attachFileName the attachFileName to set
	 */
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}
	
	/**
	 * @return the attachFileUrl
	 */
	public String getAttachFileUrl() {
		return attachFileUrl;
	}
	
	/**
	 * @param attachFileUrl the attachFileUrl to set
	 */
	public void setAttachFileUrl(String attachFileUrl) {
		this.attachFileUrl = attachFileUrl;
	}
	
	/**
	 * @return the attachFileId
	 */
	public String getAttachFileId() {
		return attachFileId;
	}
	
	/**
	 * @param attachFileId the attachFileId to set
	 */
	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}
	
	/**
	 * @return the attachFileSize
	 */
	public int getAttachFileSize() {
		return attachFileSize;
	}
	
	/**
	 * @param attachFileSize the attachFileSize to set
	 */
	public void setAttachFileSize(int attachFileSize) {
		this.attachFileSize = attachFileSize;
	}
	
	/**
	 * @return the attachFileStoredName
	 */
	public String getAttachFileStoredName() {
		return attachFileStoredName;
	}
	
	/**
	 * @param attachFileStoredName the attachFileStoredName to set
	 */
	public void setAttachFileStoredName(String attachFileStoredName) {
		this.attachFileStoredName = attachFileStoredName;
	}
	
	/**
	 * @return the attachTargetUrl
	 */
	public String getAttachTargetUrl() {
		return attachTargetUrl;
	}
	
	/**
	 * @param attachTargetUrl the attachTargetUrl to set
	 */
	public void setAttachTargetUrl(String attachTargetUrl) {
		this.attachTargetUrl = attachTargetUrl;
	}
}
