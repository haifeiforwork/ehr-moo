package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="VcardGroupList")
public class VcardFolderListReturnData {

	private int level;
	private String isHasChildren;
	private String folderId;
	private String folderParentId;
	private String folderName;
	private int siblingOrder;
	private String rootId;
	
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getIsHasChildren() {
		return isHasChildren;
	}
	public void setIsHasChildren(String isHasChildren) {
		this.isHasChildren = isHasChildren;
	}
	public String getFolderParentId() {
		return folderParentId;
	}
	public void setFolderParentId(String folderParentId) {
		this.folderParentId = folderParentId;
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
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
}