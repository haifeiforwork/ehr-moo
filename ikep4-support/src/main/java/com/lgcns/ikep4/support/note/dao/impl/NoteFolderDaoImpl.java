package com.lgcns.ikep4.support.note.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.note.dao.NoteFolderDao;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.user.member.model.User;

@Repository
public class NoteFolderDaoImpl extends GenericDaoSqlmap<NoteFolder,String> implements NoteFolderDao {

	private String nameSpace = "support.note.noteFolder.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteFolderDao#listByFolderRootId(java.lang.String)
	 */
	public List<NoteFolder> listByFolderRootId(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByFolderRootId", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteFolderDao#listByUserFolder(java.lang.String)
	 */
	public List<NoteFolder> listByUserFolder(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByUserFolder", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteFolderDao#listBySharedFolder(java.lang.String)
	 */
	public List<NoteFolder> listBySharedFolder(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listBySharedFolder", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteFolderDao#listByMoveUserFolder(java.lang.String)
	 */
	public List<NoteFolder> listByMoveUserFolder(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listByMoveUserFolder", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.dao.NoteFolderDao#listChildrenNoteFolder(java.lang.String)
	 */
	public List<NoteFolder> listChildrenFolder(Map<String, Object> parameter) {
		return this.sqlSelectForList(nameSpace + "listChildrenNoteFolder", parameter);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#selectAll(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public List<NoteFolder> selectAll(NoteSearchCondition noteSearchCondition) {
		return sqlSelectForList(nameSpace + "selectAddrgroupPerPage", noteSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#selectAllTotalCount(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public Integer selectAllTotalCount(NoteSearchCondition noteSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace + "selectTotalCntAddrgroupPerPage", noteSearchCondition);
	}

	/**
	 * 폴더명 존재하는 체크
	 */
	public boolean existFolderName(Map<String, String> param) {
		Integer count = (Integer) sqlSelectForObject(nameSpace + "existFolderName", param);
		return count > 0;
	}
	
	/**
	 * 폴더생성
	 * @param NoteFolder
	 */
	public String createFolder(NoteFolder noteFolder){
		this.sqlInsert(nameSpace + "createFolder", noteFolder);
		
		return noteFolder.getFolderId();
	}

	/**
	 * 기존 기본 폴더 Clear
	 * @param NoteFolder
	 */
	public void clearDefaultFolder(NoteFolder noteFolder){
		this.sqlInsert(nameSpace + "clearDefaultFolder", noteFolder);
	}

	/**
	 * 폴더수정
	 * @param NoteFolder
	 */
	public void updateFolder(NoteFolder noteFolder){
		this.sqlInsert(nameSpace + "updateFolder", noteFolder);
	}

	/**
	 * 폴더의 위치을 변경한다.
	 * @param NoteFolder
	 */
	public void updateMove(NoteFolder noteFolder) {
		this.sqlUpdate(nameSpace + "updateMove", noteFolder);
	}

	/**
	 * 파라미터로 들어온 폴더보다 정렬값이 큰 폴더를 대상으로 현재 정렬값을 1씩 증가시킨다.
	 * @param NoteFolder 정렬 대상 게시판 모델 객체
	 */
	public void updateSortOderIncrease(NoteFolder noteFolder) {
		this.sqlUpdate(nameSpace + "updateSortOderIncrease", noteFolder);
	}

	/**
	 * 파라미터로 들어온 폴더보다 정렬값이 큰 폴더를 대상으로 현재 정렬값을 1씩 감소시킨다.
	 * @param NoteFolder 정렬 대상 게시판 모델 객체
	 */
	public void updateSortOderDecrease(NoteFolder noteFolder) {
		this.sqlUpdate(nameSpace + "updateSortOderDecrease", noteFolder);
	}

	/**
	 * 폴더에 삭제
	 * @param 	folderId
	 * @return 	void
	 */
	public void deleteFolder(String folderId){
		sqlDelete(nameSpace + "physicalDeleteFolder", folderId);
	}

	public NoteFolder get(String folderId) {
		return (NoteFolder)this.sqlSelectForObject(nameSpace + "get", folderId);
	}

	public NoteFolder getDefaultFolder(String userId) {
		return (NoteFolder)this.sqlSelectForObject(nameSpace + "getDefaultFolder", userId);
	}

	@Deprecated
	public String create(NoteFolder arg0) {
		return null;
	}

	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}

	@Deprecated
	public void remove(String arg0) {
		
	}

	@Deprecated
	public void update(NoteFolder arg0) {
		
	}
}