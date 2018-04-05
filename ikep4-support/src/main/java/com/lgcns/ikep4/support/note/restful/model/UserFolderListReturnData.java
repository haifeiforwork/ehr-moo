package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UserFolderList")
public class UserFolderListReturnData {
	
	private String folderId;
	private String folderName;
	private String folderColor;
	private int siblingOrder;
		
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
	public String getFolderColor() {
		return folderColor;
	}
	public void setFolderColor(String folderColor) {
		this.folderColor = folderColor;
	}
	public int getSiblingOrder() {
		return siblingOrder;
	}
	public void setSiblingOrder(int siblingOrder) {
		this.siblingOrder = siblingOrder;
	}
}