package com.lgcns.ikep4.support.note.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.note.model.NoteShare;
import com.lgcns.ikep4.support.note.model.BoardItemVersion;

public interface NoteShareDao extends GenericDao<NoteShare, String> {

	List<NoteShare> boardListByBBS(Map<String, Object> parameter);

	List<NoteShare> boardListByBBSPer(Map<String, Object> parameter);

	List<NoteShare> myCollaborationListByWorkspace(Map<String, String> parameter);

	NoteShare boardByWorkspace(Map<String, Object> parameter);

	List<NoteShare> boardListByWorkspace(Map<String, Object> parameter);

	List<NoteShare> boardListByWorkspacePer(Map<String, Object> parameter);

	List<NoteShare> boardListByKnowledge(Map<String, Object> parameter);

	List<NoteShare> boardListByKnowledgePer(Map<String, Object> parameter);
	
	String createBBS(NoteShare noteShare);

	void updateBBS(NoteShare noteShare);

	String createWS(NoteShare noteShare);

	void updateWS(NoteShare noteShare);

	String createCK(NoteShare noteShare);

	void updateCK(NoteShare noteShare);

	String createItemVersion(BoardItemVersion boardItemVersion);
}