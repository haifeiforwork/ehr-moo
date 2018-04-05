/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시글 모델 클래스.
 *
 * @author ${user}
 * @version $$Id: BoardItem.java 14675 2011-06-10 01:21:04Z icerainbow $$
 */
public class BoardItem extends BaseObject {

	/**
	 * 게시글 생성시 사용하는 ValidationGroup.
	 */
	public interface CreateItemGroup {}

	/**
	 * 답글 생성시 사용하는 ValidationGroup.
	 */
	public interface CreateReplyItemGroup {}

	/**
	 * 게시글 수정시 사용하는 ValidationGroup.
	 */
	public interface ModifyItemGroup {}


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1035752613602705789L;

	/** 에디터 파일 여부. */
	public static final String EDITOR_FILE = "Y";

	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";

	/** 게시글 서비스중 */
	public static final Integer STATUS_SERVICING = 0;

	/** 게시글 삭제 처리 */
	public static final Integer STATUS_DELETED = 1;


	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE = IKepConstant.ITEM_TYPE_CODE_PROFILE;

	/** 모두 파일. */
	public static final String ALL_FILE = " ";

	/** 포털 ID. */
	private String portalId;

	/** 게시글 ID. */
	private String itemId;
	
	/** 게시글의 일련번호. */
	private String itemSeqId;

	/** 게시글 제목. */
	@NotNull(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	@NotEmpty(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	@NotBlank(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	private String title;

	/** 게시글 조회수. */
	private Integer hitCount;

	/** 댓글수. */
	private Integer linereplyCount;

	/** 첨부파일수. */
	private Integer attachFileCount;


	/**
	 * 게시글 논리적 삭제 여부 (작성자의 의해서 삭제될 때 하위 글이 있으면 itemDelete가 1로 변경되고 삭제된 글이 라고
	 * 표시된다.)
	 */
	private Integer itemDelete;

	/** 게시글 이미지 파일 아이디. */
	private String imageFileId;

	/** 등록자 ID. */
	private String registerId;

	/** 등록자 이름. */
	private String registerName;

	/** 등록일. */
	private Date registDate;

	/** 수정자 Id. */
	private String updaterId;

	/** 게시물 ID. */
	private String updaterName;

	/** 수정자 ID. */
	private Date updateDate;

	/** 게시글 내용. */
	private String contents;

	/** 첨부파일 링크 리스트. */
	private List<FileLink> fileLinkList;

	/** 에디터내의 파일링크 리스트. */
	private List<FileLink> editorFileLinkList;

	/** 첨부파일 리스트. */
	private List<FileData> fileDataList;

	/** 에디터내의 파일 리스트. */
	private List<FileData> editorFileDataList;

	private String fileId;
	
	private String oldFileId;
	
	/** 작성자. */
	private User user;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/**
	 * Gets the item id.
	 * 
	 * @return the item id
	 */
	public String getItemId() {
		return this.itemId;
	}

	/**
	 * Sets the item id.
	 * 
	 * @param itemId the new item id
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the hit count.
	 * 
	 * @return the hit count
	 */
	public Integer getHitCount() {
		return this.hitCount;
	}

	/**
	 * Sets the hit count.
	 * 
	 * @param hitCount the new hit count
	 */
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}
	
	/**
	 * Gets the item seq id.
	 * 
	 * @return the item seq id
	 */
	public String getItemSeqId() {
		return this.itemSeqId;
	}

	/**
	 * Sets the item seq id.
	 * 
	 * @param itemSeqId the new item seq id
	 */
	public void setItemSeqId(String itemSeqId) {
		this.itemSeqId = itemSeqId;
	}

	/**
	 * Gets the linereply count.
	 * 
	 * @return the linereply count
	 */
	public Integer getLinereplyCount() {
		return this.linereplyCount;
	}

	/**
	 * Sets the linereply count.
	 * 
	 * @param linereplyCount the new linereply count
	 */
	public void setLinereplyCount(Integer linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	/**
	 * Gets the attach file count.
	 * 
	 * @return the attach file count
	 */
	public Integer getAttachFileCount() {
		return this.attachFileCount;
	}

	/**
	 * Sets the attach file count.
	 * 
	 * @param attachFileCount the new attach file count
	 */
	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	/**
	 * Gets the item delete.
	 * 
	 * @return the item delete
	 */
	public Integer getItemDelete() {
		return this.itemDelete;
	}

	/**
	 * Sets the item delete.
	 * 
	 * @param itemDelete the new item delete
	 */
	public void setItemDelete(Integer itemDelete) {
		this.itemDelete = itemDelete;
	}

	/**
	 * Gets the register id.
	 * 
	 * @return the register id
	 */
	public String getRegisterId() {
		return this.registerId;
	}

	/**
	 * Sets the register id.
	 * 
	 * @param registerId the new register id
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * Gets the register name.
	 * 
	 * @return the register name
	 */
	public String getRegisterName() {
		return this.registerName;
	}

	/**
	 * Sets the register name.
	 * 
	 * @param registerName the new register name
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * Gets the regist date.
	 * 
	 * @return the regist date
	 */
	public Date getRegistDate() {
		return this.registDate;
	}

	/**
	 * Sets the regist date.
	 * 
	 * @param registDate the new regist date
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * Gets the updater id.
	 * 
	 * @return the updater id
	 */
	public String getUpdaterId() {
		return this.updaterId;
	}

	/**
	 * Sets the updater id.
	 * 
	 * @param updaterId the new updater id
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * Gets the updater name.
	 * 
	 * @return the updater name
	 */
	public String getUpdaterName() {
		return this.updaterName;
	}

	/**
	 * Sets the updater name.
	 * 
	 * @param updaterName the new updater name
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * Gets the update date.
	 * 
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Sets the update date.
	 * 
	 * @param updateDate the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Gets the contents.
	 * 
	 * @return the contents
	 */
	public String getContents() {
		return this.contents;
	}

	/**
	 * Sets the contents.
	 * 
	 * @param contents the new contents
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * Gets the file link list.
	 * 
	 * @return the file link list
	 */
	public List<FileLink> getFileLinkList() {
		return this.fileLinkList;
	}

	/**
	 * Sets the file link list.
	 * 
	 * @param fileLinkList the new file link list
	 */
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	/**
	 * Gets the editor file link list.
	 * 
	 * @return the editor file link list
	 */
	public List<FileLink> getEditorFileLinkList() {
		return this.editorFileLinkList;
	}

	/**
	 * Sets the editor file link list.
	 * 
	 * @param editorFileLinkList the new editor file link list
	 */
	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	/**
	 * Gets the file data list.
	 * 
	 * @return the file data list
	 */
	public List<FileData> getFileDataList() {
		return this.fileDataList;
	}

	/**
	 * Sets the file data list.
	 * 
	 * @param fileDataList the new file data list
	 */
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * Gets the editor file data list.
	 * 
	 * @return the editor file data list
	 */
	public List<FileData> getEditorFileDataList() {
		return this.editorFileDataList;
	}

	/**
	 * Sets the editor file data list.
	 * 
	 * @param editorFileDataList the new editor file data list
	 */
	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}


	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * Gets the portal id.
	 *
	 * @return the portal id
	 */
	public String getPortalId() {
		return this.portalId;
	}

	/**
	 * Sets the portal id.
	 *
	 * @param portalId the new portal id
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}


	/**
	 * Gets the image file id.
	 *
	 * @return the image file id
	 */
	public String getImageFileId() {
		return this.imageFileId;
	}

	/**
	 * Sets the image file id.
	 *
	 * @param imageFileId the new image file id
	 */
	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	
	/**
	 * Gets the msie.
	 *
	 * @return the msie
	 */
	public Integer getMsie() {
		return this.msie;
	}
	
	/**
	 * Sets the msie.
	 *
	 * @param msie the new msie
	 */
	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the oldFileId
	 */
	public String getOldFileId() {
		return oldFileId;
	}

	/**
	 * @param oldFileId the oldFileId to set
	 */
	public void setOldFileId(String oldFileId) {
		this.oldFileId = oldFileId;
	}
	
	

}