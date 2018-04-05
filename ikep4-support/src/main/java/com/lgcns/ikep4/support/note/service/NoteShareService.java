/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.model.NoteShare;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: NoteShareService.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface NoteShareService extends GenericService<NoteShare, String> {

	public List<NoteShare> boardListByBBS(String boardRootId, String portalId);

	public List<NoteShare> boardListByBBSPer(Map<String, Object> param);

	public List<NoteShare> myCollaborationListByWorkspace(Map<String, String> param);

	public List<NoteShare> boardListByWorkspace(String boardRootId, String portalId, String workspaceId);

	public List<NoteShare> boardListByWorkspacePer(Map<String, Object> param);

	public List<NoteShare> boardListByKnowledge(String boardRootId, String portalId);

	public List<NoteShare> boardListByKnowledgePer(Map<String, Object> param);

	String createBoardItemForBBS(Note note, NoteShare noteShare, User user);

	String createBoardItemForWS(Note note, NoteShare noteShare, User user);

	String createBoardItemForCK(Note note, NoteShare noteShare, User user);
}
