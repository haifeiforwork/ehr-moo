package com.lgcns.ikep4.support.note.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

public class NoteFolder extends BaseObject {

	private static final long serialVersionUID = -631248645447674778L;

	/**
	 * 폴더 ID
	 */
	private String folderId;
	
	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자명
	 */
	private String userName;

	/**
	 * 사용자영문명
	 */
	private String userEnglishName;

	/**
	 * 사용자 부서
	 */
	private String userTeamName;

	/**
	 * 사용자 직책
	 */
	private String userTitleName;

	/**
	 * 사용자 직책 영문명
	 */
	private String userTitleEnglishName;
	
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
	 * 기본폴더 여부 (0:기본아님, 1:기본폴더)
	 */
	private int defaultFlag;

	/**
	 * 폴더색상
	 */
	private String color;
	
	/**
	 * 공유여부 (0:공유안함, 1:공유)
	 */
	private int shared;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;
	
	/** 자식 노드 갯수 */
	private Integer hasChildren;

	private Integer indentation;

	private Integer noteCnt;
	
	/** 공유 사용자 아이디 리스트 */
	private List<User> sharedUserList;

	/** RSS_ATOM 허용 여부 */
	private Integer rss = 0;

	/** 허용 첨부파일 크기(10M) */
	private Long fileSize = 10 * 1024 * 1024L;

	/** 허용 위지윅에디터내의 이미지 크기(1000K) */
	private Long imageFileSize = 1000 * 1024L;

	/** 이미지 넓이 */
	private Integer imageWidth = 800;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getUserTeamName() {
		return userTeamName;
	}

	public void setUserTeamName(String userTeamName) {
		this.userTeamName = userTeamName;
	}

	public String getUserTitleName() {
		return userTitleName;
	}

	public void setUserTitleName(String userTitleName) {
		this.userTitleName = userTitleName;
	}

	public String getUserTitleEnglishName() {
		return userTitleEnglishName;
	}

	public void setUserTitleEnglishName(String userTitleEnglishName) {
		this.userTitleEnglishName = userTitleEnglishName;
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

	public int getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getShared() {
		return shared;
	}

	public void setShared(int shared) {
		this.shared = shared;
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

	public Integer getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Integer getNoteCnt() {
		return noteCnt;
	}

	public void setNoteCnt(Integer noteCnt) {
		this.noteCnt = noteCnt;
	}

	public Integer getIndentation() {
		return this.indentation;
	}

	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	public List<User> getSharedUserList() {
		return this.sharedUserList;
	}

	public void setSharedUserList(List<User> sharedUserList) {
		this.sharedUserList = sharedUserList;
	}

	public Integer getRss() {
		return this.rss;
	}

	public void setRss(Integer rss) {
		this.rss = rss;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getImageFileSize() {
		return this.imageFileSize;
	}

	public void setImageFileSize(Long imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	public Integer getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getFolderNameReplaceString() {
		return StringUtil.replace(StringUtil.replace(folderName, "\"", "&quot;"), "\'", "&#39;");
	}
}