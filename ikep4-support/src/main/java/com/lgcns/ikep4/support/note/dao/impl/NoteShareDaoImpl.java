package com.lgcns.ikep4.support.note.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.note.dao.NoteShareDao;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.model.NoteShare;
import com.lgcns.ikep4.support.note.model.BoardItemVersion;

@Repository
public class NoteShareDaoImpl extends GenericDaoSqlmap<NoteShare,String> implements NoteShareDao {

	private String nameSpace = "support.note.noteShare.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardListByBBS(java.lang.String)
	 */
	public List<NoteShare> boardListByBBS(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByBBSBoardRootId", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardListByBBSPer(java.lang.String)
	 */
	public List<NoteShare> boardListByBBSPer(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByBBSBoardRootIdPer", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#myCollaborationListByWorkspace(java.lang.String)
	 */
	public List<NoteShare> myCollaborationListByWorkspace(Map<String, String> parameter) {
		return this.sqlSelectForList(nameSpace + "listMyCollaboration", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardByWorkspace(java.lang.String)
	 */
	public NoteShare boardByWorkspace(Map<String, Object> parameter) {
		return (NoteShare) sqlSelectForObject(nameSpace+"getBoardbyWS", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardListByWorkspace(java.lang.String)
	 */
	public List<NoteShare> boardListByWorkspace(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByWSBoardRootId", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardListByWorkspacePer(java.lang.String)
	 */
	public List<NoteShare> boardListByWorkspacePer(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByWSBoardRootIdPer", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardListByKnowledge(java.lang.String)
	 */
	public List<NoteShare> boardListByKnowledge(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByCKBoardRootId", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#boardListByKnowledgePer(java.lang.String)
	 */
	public List<NoteShare> boardListByKnowledgePer(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByCKBoardRootIdPer", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#createBBS(java.lang.String)
	 */
	public String createBBS(NoteShare object) {
		this.sqlInsert(nameSpace + "createBBS", object);

		return object.getItemId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#updateBBS(java.lang.String)
	 */
	public void updateBBS(NoteShare object) {
		this.sqlInsert(nameSpace + "updateBBS", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#createWS(java.lang.String)
	 */
	public String createWS(NoteShare object) {
		this.sqlInsert(nameSpace + "createWS", object);

		return object.getItemId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#updateWS(java.lang.String)
	 */
	public void updateWS(NoteShare object) {
		this.sqlInsert(nameSpace + "updateWS", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#createKW(java.lang.String)
	 */
	public String createCK(NoteShare object) {
		this.sqlInsert(nameSpace + "createCK", object);

		return object.getItemId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteShareDao#updateCK(java.lang.String)
	 */
	public void updateCK(NoteShare object) {
		this.sqlInsert(nameSpace + "updateCK", object);
	}

	/**
	 * 버전 정보 등록
	 */
	public String createItemVersion(BoardItemVersion object) {
		this.sqlInsert(nameSpace + "createItemVersion", object);
		
		return object.getVersionId();
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public NoteShare get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(NoteShare arg0) {
		// TODO Auto-generated method stub
		
	}

	public String create(NoteShare arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}