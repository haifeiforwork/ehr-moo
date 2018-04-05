package com.lgcns.ikep4.support.fileupload.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * FileLink
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileCheck.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class FileCheck extends BaseObject {

	
	/**
	 *
	 */
	private static final long serialVersionUID = -8807144625903746159L;

	/**
	 * fileId
	 */
	private String fileId;

	/**
	 * itemId
	 */
	private String itemId;

	/**
	 * itemTypeCode
	 */
	private String itemTypeCode;

	/**
	 * 사용자
	 */
	private String userId;

	/**
	 * 권한여부 (Y/N)
	 */
	private String isAuth;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
}
