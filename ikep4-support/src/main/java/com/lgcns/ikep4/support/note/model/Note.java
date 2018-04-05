/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: Note.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Note extends BaseObject {

	private static final long serialVersionUID = -822341272640411863L;

	/** 에디터 파일 여부. */
	public static final String EDITOR_FILE = "Y";

	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";

	/** 게시글 삭제 대기 상태 */
	public static final Integer STATUS_DELETE_WAITING = 2;

	/** NOTE 아이템 타입. */
	public static final String ITEM_TYPE = "NO";

	/** 노트 ID. */
	private String noteId;

	/** 폴더 ID. */
	private String folderId;

	/** 폴더명. */
	private String folderName;

	/** 제목. */
	private String title;

	/** 게시글 내용. */
	private String contents;

	/** 게시글 내용. */
	private String textContents;

	/** 삭제여부 (0:미삭제, 1:삭제 휴지통으로 이동됨).  */
	private Integer noteDelete;
 
	/** 포탈 ID. */
	private String portalId;

	/** 사용자 ID. */
	private String userId;

	/** 중요여부 (0:일반, 1:중요). */
	private Integer priority;

	/** 등록자 ID. */
	private String registerId;

	/** 등록자 이름. */
	private String registerName;

	/** 등록자 영문 이름. */
	private String registerEnglishName;

	/** 등록일. */
	private Date registDate;

	/** 수정자 ID. */
	private String updaterId;

	/** 수정자 이름. */
	private String updaterName;

	/** 수정자 영문 이름. */
	private String updaterEnglishName;

	/** 수정일. */
	private Date updateDate;

	/** 첨부파일 링크 리스트. */
	private List<FileLink> fileLinkList;

	/** 에디터내의 파일링크 리스트. */
	private List<FileLink> editorFileLinkList;

	/** 첨부파일 리스트. */
	private List<FileData> fileDataList;

	/** 에디터내의 파일 리스트. */
	private List<FileData> editorFileDataList;

	/** 작성자. */
	private User user;
	
	/** 태그 문자열 (ex: xxx,yyy,zzz). */
	private String tag;

	/** 태그 리스트. */
	private List<Tag> tagList;

	/** 첨부파일수. */
	private Integer attachFileCount;

	/** 썸네일 이미지 파일 아이디. */
	private String imageFileId;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/**
	 * 현재 페이지 번호
	 */
	private int pageIndex = 1;

	/**
	 * 게시판 가져올 끝 수
	 */
	private Integer endRowIndex;

	/**
	 * 게시판 가져올 처음 수
	 */
	private Integer startRowIndex;
	
	private Integer pagePerRecord;

	
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTextContents() {
		return this.textContents;
	}

	public void setTextContents(String textContents) {
		this.textContents = textContents;
	}

	public Integer getNoteDelete() {
		return this.noteDelete;
	}

	public void setNoteDelete(Integer noteDelete) {
		this.noteDelete = noteDelete;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getRegisterId() {
		return this.registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return this.registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterEnglishName() {
		return this.registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	public Date getRegistDate() {
		return this.registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return this.updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return this.updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdaterEnglishName() {
		return this.updaterEnglishName;
	}

	public void setUpdaterEnglishName(String updaterEnglishName) {
		this.updaterEnglishName = updaterEnglishName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<FileLink> getFileLinkList() {
		return this.fileLinkList;
	}

	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	public List<FileLink> getEditorFileLinkList() {
		return this.editorFileLinkList;
	}

	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	public List<FileData> getFileDataList() {
		return this.fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public List<FileData> getEditorFileDataList() {
		return this.editorFileDataList;
	}

	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Tag> getTagList() {
		return this.tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public Integer getAttachFileCount() {
		return this.attachFileCount;
	}

	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	public String getImageFileId() {
		return this.imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public Integer getMsie() {
		return this.msie;
	}
	
	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getEndRowIndex() {
		return endRowIndex;
	}

	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public Integer getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(Integer startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public Integer getPagePerRecord() {
		return pagePerRecord;
	}

	public void setPagePerRecord(Integer pagePerRecord) {
		this.pagePerRecord = pagePerRecord;
	}

}
