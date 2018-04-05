package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ShareFolderList")
public class ShareFolderListReturnData {
	
	private String folderId;
	private String folderName;
	private int siblingOrder;
	private String shareUserId;
	private String shareUserName;
	private String shareUserDept;
	private String shareUserTitle;
		
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getSiblingOrder() {
		return siblingOrder;
	}
	public void setSiblingOrder(int siblingOrder) {
		this.siblingOrder = siblingOrder;
	}
	public String getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}
	public String getShareUserName() {
		return shareUserName;
	}
	public void setShareUserName(String shareUserName) {
		this.shareUserName = shareUserName;
	}
	public String getShareUserDept() {
		return shareUserDept;
	}
	public void setShareUserDept(String shareUserDept) {
		this.shareUserDept = shareUserDept;
	}
	public String getShareUserTitle() {
		return shareUserTitle;
	}
	public void setShareUserTitle(String shareUserTitle) {
		this.shareUserTitle = shareUserTitle;
	}
}