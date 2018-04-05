/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: NoteDao.java 17308 2012-02-06 04:43:19Z yruyo $
 */
public interface NoteDao extends GenericDao<Note,String> {

	Integer countBySearchCondition(NoteSearchCondition noteSearchCondition);

	List<Note> listBySearchCondition(NoteSearchCondition noteSearchCondition);
	
	public void folderDeleteStore(String folderId);
	
	public String getPortalHost(String portalId);

	void logicalDelete(Note note);

	void restorationNote(Note note);
	
	void physicalDelete(Note note);
}
