/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.service;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageService.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface NoteService extends GenericService<Note, String> {

	SearchResult<Note> listNoteBySearchCondition(NoteSearchCondition noteSearchCondition);
	
	public void folderDeleteStore(String folderId);

	String createNote(Note note, User user);

	Note readNote(String noteId);

	void updateNote(Note note, User user);
	
	Note readNoteMasterInfo(String noteId);

	void logicalDeleteNote(Note note);

	void restorationNote(Note note, User user);
	
	void physicalDeleteNote(Note note);

	void moveFolder(Note note);
	
	void checkPriority(Note note);
	
	SearchResult<Note> webDiaryListNoteBySearchCondition(NoteSearchCondition noteSearchCondition);
	
}
