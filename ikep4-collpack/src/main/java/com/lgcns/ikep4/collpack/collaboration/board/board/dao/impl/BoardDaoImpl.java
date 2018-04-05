/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * BoardDao 구현체 클래스
 */
@Repository("wsBoardDaoImpl")
public class BoardDaoImpl extends GenericDaoSqlmap<Board, String> implements BoardDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "collpack.collaboration.board.board.dao.board.";


	/**
	 * 게시판 조회
	 */
	public Board get(String id) {
		return (Board)this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 게시판 존재유무
	 */
	public boolean exists(String id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);

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

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
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

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao#listByBoardRootId(java.lang.String)
	 */
	//public List<Board> listByBoardRootId(String boardRootId) {
	//	return this.sqlSelectForList(NAMESPACE + "listByBoardRootId", boardRootId);
	//}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao#getParents(java.lang.String)
	 */
	//public List<Board> getParents(String boardId) {
	//	return this.sqlSelectForList(NAMESPACE + "getParents", boardId);
	//}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao#getChildren(java.lang.String)
	 */
	//public List<Board> getChildren(String boardId) {
	//	return this.sqlSelectForList(NAMESPACE + "getChildren", boardId);
	//}

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
		return (Board)this.sqlSelectForObject(NAMESPACE + "readHasPermissionBoard", boardRootId);
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
	public List<Board> updateChildBoardAllWorkspace(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "updateChildBoardAllWorkspace", map);
	}	
	
	
	
	/* WS 추가 5/17 */
	
	/**
	 * 배치로 삭제할 게시판 목록(ws에 속한 게시판)
	 * @param workspaceId
	 * @return
	 */
	public List<Board> listBoardByWorkspaceId(String workspaceId) {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "listBoardByWorkspaceId", workspaceId);
	}

	/**
	 * 삭제 배치 (게시판 영구삭제처리)
	 * @param boardId
	 */
	public void physicalDeleteBatch(String boardId) {
		this.sqlUpdate(NAMESPACE + "physicalDeleteBatch", boardId); 
	}

	/**
	 * 게시판 이동 - 배치Job 권한 변경
	 */
	public void moveWorkspaceBoard() {
		sqlSelectForObject(NAMESPACE + "moveWorkspaceBoard");
		
	}
	/**
	 * Workspace 내 게시판 목록
	 */
	public List<Board> listDeleteBoardByWorkspace(String workspaceId) {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "listDeleteBoardByWorkspace",workspaceId);

	}		
	/**
	 * 배치작업으로 해당 게시판 정보 모두 삭제, 게시물, 권한 등
	 */
	public void deleteWorkspaceBoard(String boardId) {
		sqlSelectForObject(NAMESPACE + "deleteWorkspaceBoard", boardId);
		
	}	
	/**
	 * 게시판 영구 삭제 목록 ( 게시판 영구 삭제 배치 )
	 * @return
	 */
	public List<Board> listDeleteBoardBatch() {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "listDeleteBoardBatch");

	}
	/**
	 * 동맹에게 공유한 게시판 링크 정보 삭제
	 */
	public void removeBoardAlliance(String boardId) {
		this.sqlUpdate(NAMESPACE + "removeBoardAlliance", boardId); 
	}
	/**
	 * 동맹에게 공유한 게시판 링크 정보 삭제
	 */	
	public void removeBoardByAllianceId(String allianceId) {
		this.sqlUpdate(NAMESPACE + "removeBoardByAllianceId", allianceId); 
	}
	/**
	 * 게시판 이동
	 */	
	public void updateMoveBoardByWorkspace(Map<String, String> map) {
		this.sqlUpdate(NAMESPACE + "updateMoveBoardByWorkspace", map);  
	}
	/**
	 * 동맹 게시판 등록
	 */
	public void createBoardAlliance(Map<String,String> map) { 
		this.sqlInsert(NAMESPACE + "createBoardAlliance", map);
	}

	/**
	 * 게시판 정보 조회
	 */
	public Board get(Map<String, String> map) {
		return (Board)this.sqlSelectForObject(NAMESPACE + "get", map);

	}
	/**
	 * 게시판 존재유무 확인
	 * @param map
	 * @return
	 */
	public boolean exists(Map<String, String> map) {  
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", map);
		
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
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "listByBoardRootId", map);
	}
	/**
	 * 게시판 Root 하위 목록 (모바일용)
	 */
	public List<Board> listByBoardRootIdForMobile(Map<String, String> map) {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "listByBoardRootIdForMobile", map);
	}
	/**
	 * 게시판 부모 정보 조회
	 */
	public List<Board> getParents(Map<String, String> map) {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "getParents", map);
	}
	/**
	 * 게시판 하위 목록 조회
	 */
	public List<Board> getChildren(Map<String, String> map) {
		return (List<Board>)this.sqlSelectForList(NAMESPACE + "getChildren", map);
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
		return (Board)this.sqlSelectForObject(NAMESPACE + "readHasPermissionBoard", map);
	}


	
}
