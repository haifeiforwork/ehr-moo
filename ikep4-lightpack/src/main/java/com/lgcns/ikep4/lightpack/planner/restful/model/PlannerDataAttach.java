package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="attachFile")
public class PlannerDataAttach {

	private String attachFileName;
	private String attachFileUrl;
	private String attachFileId;
	private String attachFileSize;
	private String attachFileStoredName;
	private String attachTargetUrl;

	
	
	public PlannerDataAttach() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlannerDataAttach(String attachFileName, String attachFileUrl,
			String attachFileId, String attachFileSize, String attachFileStoredName, String attachTargetUrl) {
		super();
		
		this.attachFileName = attachFileName;
		this.attachFileUrl = attachFileUrl;
		this.attachFileId = attachFileId;
		this.attachFileSize = attachFileSize;
		this.attachFileStoredName = attachFileStoredName;
		this.attachTargetUrl = attachTargetUrl;
	}

	public String getAttachFileName() {
		return attachFileName;
	}
	
	public String getAttachFileUrl() {
		return attachFileUrl;
	}

	public String getAttachFileId() {
		return attachFileId;
	}
	
	public String getAttachFileSize() {
		return attachFileSize;
	}
	
	public String getAttachFileStoredName() {
		return attachFileStoredName;
	}
	
	public String getAttachTargetUrl() {
		return attachTargetUrl;
	}
	
	

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public void setAttachFileUrl(String attachFileUrl) {
		this.attachFileUrl = attachFileUrl;
	}

	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}
	
	public void setAttachFileSize(String attachFileSize) {
		this.attachFileSize = attachFileSize;
	}
	
	public void setAttachFileStoredName(String attachFileStoredName) {
		this.attachFileStoredName = attachFileStoredName;
	}
	
	public void setAttachTargetUrl(String attachTargetUrl) {
		this.attachTargetUrl = attachTargetUrl;
	}

}