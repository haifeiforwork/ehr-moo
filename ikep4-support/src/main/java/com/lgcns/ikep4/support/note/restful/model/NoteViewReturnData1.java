package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData1")
public class NoteViewReturnData1 {
	
	/**
	 * 첨부파일명
	 */
	private String attachFileName = "";

	/**
	 * 첨부파일PATH
	 */
	private String attachFilePath = "";
	/**
	 * 첨부파일URL
	 */
	private String attachFileUrl = "";
	
	/**
	 * 첨부파일 id
	 */
	private String attachFileId = "";
	
	/**
	 * 첨부파일 크기
	 */
	private String attachFileSize = "";
	
	/**
	 * 첨부서버저장파일명
	 */
	private String attachFileStoredName = "";
	
	/**
	 * 첨부파일FULL URL
	 */
	private String attachTargetUrl = "";
		
	
	public NoteViewReturnData1() {}
	
	public String getAttachFileName() {
		return attachFileName;
	}
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}
	
	public String getAttachFileUrl() {
		return attachFileUrl;
	}
	public void setAttachFileUrl(String attachFileUrl) {
		this.attachFileUrl = attachFileUrl;
	}
	
	public String getAttachFileId() {
		return attachFileId;
	}
	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}
	
	public String getAttachFileSize() {
		return attachFileSize;
	}
	public void setAttachFileSize(String attachFileSize) {
		this.attachFileSize = attachFileSize;
	}
	
	public String getAttachFileStoredName() {
		return attachFileStoredName;
	}
	public void setAttachFileStoredName(String attachFileStoredName) {
		this.attachFileStoredName = attachFileStoredName;
	}
	
	public String getAttachTargetUrl() {
		return attachTargetUrl;
	}
	public void setAttachTargetUrl(String attachTargetUrl) {
		this.attachTargetUrl = attachTargetUrl;
	}

	public String getAttachFilePath() {
		return attachFilePath;
	}

	public void setAttachFilePath(String attachFilePath) {
		this.attachFilePath = attachFilePath;
	}
}