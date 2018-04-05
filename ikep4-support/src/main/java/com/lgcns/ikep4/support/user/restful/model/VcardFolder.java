package com.lgcns.ikep4.support.user.restful.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 명함폴더 model
 *
 * @version $Id$
 */
public class VcardFolder extends BaseObject {

	private static final long serialVersionUID = -7422822093417627718L;

	/**
	 * 폴더 ID
	 */
	private String folderId;
	
	/**
	 * 사용자 ID
	 */
	private String userId;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 부모 폴더 ID
	 */
	private String folderParentId;
	
	/**
	 * 폴더명
	 */
	private String folderName;
	
	/**
	 * 문서함 속성 타입 ( 0 폴더용, 1:카테고리 구분용 Dummy폴더)
	 */
	private int folderType;
	
	/**
	 * SIBLING ORDER(순서)
	 */
	private int sortOrder;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;
	
	private int level;

	private String mode;
	
	private String isHasChildren;

	private String folderRootId;
	
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

	public String getFolderRootId() {
		return folderRootId;
	}

	public void setFolderRootId(String folderRootId) {
		this.folderRootId = folderRootId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

	public int getFolderType() {
		return folderType;
	}

	public void setFolderType(int folderType) {
		this.folderType = folderType;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
}