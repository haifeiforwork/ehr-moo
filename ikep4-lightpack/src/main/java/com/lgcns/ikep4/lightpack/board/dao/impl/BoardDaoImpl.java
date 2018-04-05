/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.model.Board;

/**
 * BoardDao 구현체 클래스
 */
@Repository
public class BoardDaoImpl extends GenericDaoSqlmap<Board, String> implements BoardDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.board.dao.board.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Board get(String id) {
		return (Board)this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);

		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Board object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getBoardId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Board object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String id) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", id);
	}

	/**
	 * 게시판 Root 하위 목록 
	 */
	public List<Board> listByBoardRootIdMap(Map<String, String> map) {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "listByBoardRootIdMap", map);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#listByBoardRootId(java.lang.String)
	 */
	public List<Board> listByBoardRootIdPermission(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardRootIdPermission", parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#listByBoardRootId(java.lang.String)
	 */
	public List<Board> listByBoardRootId(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardRootId", parameter);
	}
	
	public List<Board> listByBoardRootIdMobile(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardRootIdMobile", parameter);
	}
	
	public List<Board> listByBoardRootIdPer(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardRootIdPer", parameter);
	}
	
	public List<Board> listByBoardRootIdPerMobile(Map<String, Object> parameter) {
		List<Board> board =  this.sqlSelectForList(NAMESPACE + "listByBoardRootIdPerMobile", parameter);
		return board;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#getParents(java.lang.String)
	 */
	public List<Board> getParents(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "getParents", boardId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#getChildren(java.lang.String)
	 */
	public List<Board> getChildren(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "getChildren", boardId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#updateMove(com.lgcns.ikep4.lightpack.board.model.Board)
	 */
	public void updateMove(Board board) {
		this.sqlUpdate(NAMESPACE + "updateMove", board);
	}



	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#readHasPermissionBoard(java.lang.String, java.lang.String)
	 */
	public Board readHasPermissionBoard(String userId, String boardRootId) {
		return (Board)this.sqlSelectForObject(NAMESPACE + "readHasPermissionBoard", boardRootId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#updateSortOderIncrease(com.lgcns.ikep4.lightpack.board.model.Board)
	 */
	public void updateSortOderIncrease(Board board) {
		this.sqlUpdate(NAMESPACE + "updateSortOderIncrease", board);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#updateSortOderDecrease(com.lgcns.ikep4.lightpack.board.model.Board)
	 */
	public void updateSortOderDecrease(Board board) {
		this.sqlUpdate(NAMESPACE + "updateSortOderDecrease", board);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#listChildrenBoard(java.lang.String)
	 */
	public List<Board> listChildrenBoard(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenBoardPortal", parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#listChildrenBoard(java.lang.String)
	 */
	public List<Board> listChildrenBoard(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenBoard", boardId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#listBySelectedBoardForPublicBoard()
	 */
	public List<Board> listBySelectedBoardForPublicBoard() {
		return this.sqlSelectForList(NAMESPACE + "listBySelectedBoardForPublicBoard");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#updateRemovePortlet()
	 */
	public void updateRemovePortlet() {
		this.sqlUpdate(NAMESPACE + "updateRemovePortlet");

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#updateSetupPortlet(java.lang.String)
	 */
	public void updateSetupPortlet(String boardId) {
		this.sqlUpdate(NAMESPACE + "updateSetupPortlet", boardId);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#listBoardTypeBoard()
	 */
	public List<Board> listBoardTypeBoard() {
		return this.sqlSelectForList(NAMESPACE + "listBoardTypeBoard");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#updateBoardDeleteField(java.lang.Integer)
	 */
	public void updateBoardDeleteField(Map<String, Object> parameter) {
		this.sqlUpdate(NAMESPACE + "updateBoardDeleteField", parameter);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardDao#nextDeletedBoard()
	 */
	public Board nextDeletedBoard() {
		return (Board)this.sqlSelectForObject(NAMESPACE + "nextDeletedBoard");
	}
	
	public void updateBoardMenuType(String type) {
		this.sqlUpdate(NAMESPACE + "updateBoardMenuType", type);
	}
	
	public String selectBoardMenuType() {
		return this.sqlSelectForObject(NAMESPACE + "selectBoardMenuType").toString();
	}
	
	public List<Board> listBySelectedBoardForMarkBoard(String portletConfigId) {
		return this.sqlSelectForList(NAMESPACE + "listBySelectedBoardForMarkBoard", portletConfigId);
	}
	
	public List<Board> listBoardTypeBoardmark(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listBoardTypeBoardmark", parameter);
	}
	
	public void deleteSetupPortletMark(String portletConfigId) {
		this.sqlInsert(NAMESPACE + "deleteSetupPortletMark", portletConfigId);
	}
	
	public void createSetupPortletMark(Map<String, Object> parameter) {
		this.sqlInsert(NAMESPACE + "createSetupPortletMark",parameter);
	}
	
	public void updateDisplayBoardParentItem(){
		String dsp = "1";
		this.sqlUpdate(NAMESPACE + "updateDisplayBoardParentItem",dsp);
	}
	
	public void updateDisplayBoardItem(){
		String dsp = "1";
		this.sqlUpdate(NAMESPACE + "updateDisplayBoardItem",dsp);
	}

	public List<Board> getBoardMenuList() {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getBoardMenuList");
	}
}
