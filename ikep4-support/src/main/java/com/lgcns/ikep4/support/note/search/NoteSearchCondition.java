/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageSearchCondition.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class NoteSearchCondition extends SearchCondition {

	private static final long serialVersionUID = -1298252955877795186L;

	private String noteId; 

	private String folderId;

	private String userId;

	private String portalId;

	private String groupType;

	private String searchColumn;
	
	private String searchWord;

	private String searchType;

	private String sortWord;

	private String layoutType;
	
	private String paramFolderId;
	
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

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSortWord() {
		return this.sortWord;
	}

	public void setSortWord(String sortWord) {
		this.sortWord = sortWord;
	}

	public String getLayoutType() {
		return this.layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	public String getParamFolderId() {
		return paramFolderId;
	}

	public void setParamFolderId(String paramFolderId) {
		this.paramFolderId = paramFolderId;
	}
	
}
