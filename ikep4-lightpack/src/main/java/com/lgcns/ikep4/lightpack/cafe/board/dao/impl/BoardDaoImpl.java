/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;


/**
 * BoardDao 구현체 클래스
 */
@Repository("cfBoardDaoImpl")
public class BoardDaoImpl extends GenericDaoSqlmap<Board, String> implements BoardDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.cafe.board.dao.board.";

	/**
	 * 게시판 조회
	 */
	public Board get(String id) {
		return (Board) this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 게시판 존재유무
	 */
	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "exists", id);

		return count > 0;
	}

	/**
	 * 게시판 생성
	 */
	public String create(Board object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getBoardId();
	}

	/**
	 * 게시판 수정
	 */
	public void update(Board object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 물리적 게시판 삭제
	 */
	public void physicalDelete(String id) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao#listByBoardRootId(java
	 * .lang.String)
	 */
	// public List<Board> listByBoardRootId(String boardRootId) {
	// return this.sqlSelectForList(NAMESPACE + "listByBoardRootId",
	// boardRootId);
	// }

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao#getParents(java.lang
	 * .String)
	 */
	public List<Board> getParents(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "getParents", boardId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao#getChildren(java.lang
	 * .String)
	 */
	// public List<Board> getChildren(String boardId) {
	// return this.sqlSelectForList(NAMESPACE + "getChildren", boardId);
	// }

	/**
	 * 게시판 이동
	 */
	public void updateMove(Board board) {
		this.sqlUpdate(NAMESPACE + "updateMove", board);
	}

	/**
	 * 게시판 조회 권한
	 */
	public Board readHasPermissionBoard(String userId, String boardRootId) {
		return (Board) this.sqlSelectForObject(NAMESPACE + "readHasPermissionBoard", boardRootId);
	}

	/**
	 * 게시판 순서 수정
	 */
	public void updateSortOderIncrease(Board board) {
		this.sqlUpdate(NAMESPACE + "updateSortOderIncrease", board);

	}

	/**
	 * 게시판 순서 수정
	 */
	public void updateSortOderDecrease(Board board) {
		this.sqlUpdate(NAMESPACE + "updateSortOderDecrease", board);

	}

	/**
	 * 하위 게시판 정보 조회
	 */
	public List<Board> listChildrenBoard(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenBoard", boardId);
	}

	/**
	 * 하위 게시판 정보 조회 모두
	 */
	public List<Board> listChildBoardAll(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "listChildBoardAll", boardId);
	}

	/**
	 * 하위 게시판 정보 조회 모두
	 */
	public List<Board> updateChildBoardAllCafe(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "updateChildBoardAllCafe", map);
	}

	/* WS 추가 5/17 */

	/**
	 * 배치로 삭제할 게시판 목록(ws에 속한 게시판)
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<Board> listBoardByCafeId(String workspaceId) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "listBoardByCafeId", workspaceId);
	}

	/**
	 * 삭제 배치 (게시판 영구삭제처리)
	 * 
	 * @param boardId
	 */
	public void physicalDeleteBatch(String boardId) {
		this.sqlUpdate(NAMESPACE + "physicalDeleteBatch", boardId);
	}

	/**
	 * 게시판 이동
	 */
	public void moveCafeBoard() {
		sqlSelectForObject(NAMESPACE + "moveCafeBoard");

	}

	/**
	 * Cafe 내 게시판 목록
	 */
	public List<Board> listDeleteBoardByCafe(String workspaceId) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "listDeleteBoardByCafe", workspaceId);

	}

	public void deleteCafeBoard(String boardId) {
		sqlSelectForObject(NAMESPACE + "moveCafeBoard", boardId);

	}

	/**
	 * 게시판 영구 삭제 목록 ( 게시판 영구 삭제 배치 )
	 * 
	 * @return
	 */
	public List<Board> listDeleteBoardBatch() {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "listDeleteBoardBatch");

	}

	/**
	 * 게시판 이동
	 */
	public void updateMoveBoardByCafe(Map<String, String> map) {
		this.sqlUpdate(NAMESPACE + "updateMoveBoardByCafe", map);
	}

	/**
	 * 게시판 정보 조회
	 */
	public Board get(Map<String, String> map) {
		return (Board) this.sqlSelectForObject(NAMESPACE + "get", map);

	}

	/**
	 * 게시판 존재유무 확인
	 * 
	 * @param map
	 * @return
	 */
	public boolean exists(Map<String, String> map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "exists", map);

		return count > 0;
	}

	/**
	 * 게시판 영구 삭제
	 */
	public void physicalDelete(Map<String, String> map) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", map);
	}

	/**
	 * 게시판 임시 삭제 처리
	 */
	public void logicalDelete(String boardId) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardId);
	}

	/**
	 * 게시판 Root 하위 목록
	 */
	public List<Board> listByBoardRootId(Map<String, String> map) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "listByBoardRootId", map);
	}

	/**
	 * 게시판 Menu Root 하위 목록
	 */
	public List<Board> listByMenuBoardRootId(Map<String, String> map) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "listByMenuBoardRootId", map);
	}

	/**
	 * 게시판 부모 정보 조회
	 */
	public List<Board> getParents(Map<String, String> map) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "getParents", map);
	}

	/**
	 * 게시판 하위 목록 조회
	 */
	public List<Board> getChildren(Map<String, String> map) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "getChildren", map);
	}

	/**
	 * 하위 게시판 목록 조회
	 */
	public List<Board> listChildrenBoard(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenBoard", map);
	}

	/**
	 * 게시판 조회 권한
	 */
	public Board readHasPermissionBoard(Map<String, String> map) {
		return (Board) this.sqlSelectForObject(NAMESPACE + "readHasPermissionBoard", map);
	}

	public List<Board> getCafeWriteBoard(String cafeId) {
		return (List<Board>) this.sqlSelectForList(NAMESPACE + "getCafeWriteBoard", cafeId);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> listBoardIdForCafe(String cafeId) {
		return (List<String>) this.getSqlMapClientTemplate().queryForList(NAMESPACE + "listBoardIdForCafe", cafeId);
	}

}
