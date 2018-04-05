/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.note.dao.NoteDao;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageDaoImpl.java 17308 2012-02-06 04:43:19Z yruyo $
 */
@Repository
public class NoteDaoImpl extends GenericDaoSqlmap<Note,String> implements NoteDao {
	
	private String nameSpace = "support.note.";
	
	public Note get(String noteId) {
		return (Note) sqlSelectForObject(nameSpace+"get", noteId);
	}

	public boolean readCheck(NoteSearchCondition noteSearchCondition) {
		Integer check = (Integer) sqlSelectForObject(nameSpace+"readCheck", noteSearchCondition);
		if (check > 0) { return true; } 
		return false; 
	}

	public String getPortalHost(String portalId) {		
		return (String) sqlSelectForObject(nameSpace+"getPortalHost",portalId);
	}

	@Deprecated
	public boolean exists(String noteId) {
		return false;
	}

	public String create(Note note) {
		this.sqlInsert(nameSpace+"create",note);
		return note.getNoteId();
	}

	@Deprecated
	public void update(Note note) {
		this.sqlInsert(nameSpace + "update", note);
	}

	@Deprecated
	public void remove(String id) {
	}

	public void folderDeleteStore(String folderId) {
		this.sqlUpdate(nameSpace+"folderDeleteStore", folderId);		
	}

	public List<Note> listBySearchCondition(NoteSearchCondition noteSearchCondition) {
		return this.sqlSelectForList(nameSpace + "listBySearchCondition", noteSearchCondition);
	}
	
	public Integer countBySearchCondition(NoteSearchCondition noteSearchCondition) {
		return (Integer)this.sqlSelectForObject(nameSpace + "countBySearchCondition", noteSearchCondition);
	}

	public void logicalDelete(Note note) {
		this.sqlUpdate(nameSpace + "logicalDelete", note);
	}

	public void restorationNote(Note note) {
		this.sqlUpdate(nameSpace + "restorationNote", note);
	}

	public void physicalDelete(Note note) {
		this.sqlUpdate(nameSpace + "physicalDelete", note);
	}
}
