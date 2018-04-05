package com.lgcns.ikep4.support.fileupload.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Thumbnail
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: Thumbnail.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Thumbnail extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = -5188752238137332126L;

	/**
	 * id
	 */
	private String thumbnailId;

	/**
	 * 저장경로
	 */
	private String thumbnailPath;

	/**
	 * 저장파일명
	 */
	private String thumbnailName;

	/**
	 * 실제파일명
	 */
	private String thumbnailRealName;

	/**
	 * 파일타입
	 */
	private String thumbnailContentsType;

	/**
	 * 확장자
	 */
	private String thumbnailExtension;

	/**
	 * 크기
	 */
	private int thumbnailFileSize;

	/**
	 * 원본파일 id
	 */
	private String parentFileId;

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
	 * 에이터 첨부 파일 여부
	 */
	private int editorAttach;

	/**
	 * itemTypeCode
	 */
	private String itemTypeCode;

	public String getThumbnailId() {
		return thumbnailId;
	}

	public void setThumbnailId(String thumbnailId) {
		this.thumbnailId = thumbnailId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public String getThumbnailRealName() {
		return thumbnailRealName;
	}

	public void setThumbnailRealName(String thumbnailRealName) {
		this.thumbnailRealName = thumbnailRealName;
	}

	public String getThumbnailContentsType() {
		return thumbnailContentsType;
	}

	public void setThumbnailContentsType(String thumbnailContentsType) {
		this.thumbnailContentsType = thumbnailContentsType;
	}

	public String getThumbnailExtension() {
		return thumbnailExtension;
	}

	public void setThumbnailExtension(String thumbnailExtension) {
		this.thumbnailExtension = thumbnailExtension;
	}

	public int getThumbnailFileSize() {
		return thumbnailFileSize;
	}

	public void setThumbnailFileSize(int thumbnailFileSize) {
		this.thumbnailFileSize = thumbnailFileSize;
	}

	public String getParentFileId() {
		return parentFileId;
	}

	public void setParentFileId(String parentFileId) {
		this.parentFileId = parentFileId;
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

	public int getEditorAttach() {
		return editorAttach;
	}

	public void setEditorAttach(int editorAttach) {
		this.editorAttach = editorAttach;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

}
