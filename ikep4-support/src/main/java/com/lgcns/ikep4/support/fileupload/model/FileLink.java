package com.lgcns.ikep4.support.fileupload.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * FileLink
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileLink.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class FileLink extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -322602466870559513L;

	/**
	 * 파일 링크 id
	 */
	private String fileLinkId;

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
	 * fileSize
	 */
	private String fileSize;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자
	 */
	private String updaterId;

	/**
	 * 수정자명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 입력/삭제/변동없음 구분('add', 'del', 'use')
	 */
	private String flag;

	public String getFileLinkId() {
		return fileLinkId;
	}

	public void setFileLinkId(String fileLinkId) {
		this.fileLinkId = fileLinkId;
	}

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

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
