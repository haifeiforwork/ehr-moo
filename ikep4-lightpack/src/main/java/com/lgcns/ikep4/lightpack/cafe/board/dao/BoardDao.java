/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;


/**
 * 게시판 DAO 게시판 IKEP4_BD_BOARD 테이블 CRUD와 목록조회등 DB와의 연동을 담당하는 클래스이다.
 * 
 * @author ${user}
 * @version $$Id: BoardDao.java 17081 2011-12-13 10:18:57Z giljae $$
 */
public interface BoardDao extends GenericDao<Board, String> {

	/**
	 * 게시판의 부모들을 조회한다.
	 * 
	 * @param boardId 게시판 아이디
	 * @return 부모 목록
	 */
	public List<Board> getParents(String boardId);

	/**
	 * 게시판의 자식들을 조회한다.
	 * 
	 * @param boardId 게시판 아이디
	 * @return 자식 게시판 목록
	 */
	// public List<Board> getChildren(String boardId);

	/**
	 * 루트 게시판 ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 * 
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	// public List<Board> listByBoardRootId(String boardRootId);

	/**
	 * 게시판의 위치을 변경한다.
	 * 
	 * @param board 이동 대상 게시판 모델 객체
	 */
	public void updateMove(Board board);

	/**
	 * 파라미터로 들어온 게시판보다 정렬값이 큰 게시판을 대상으로 현재 정렬값을 1씩 증가시킨다.
	 * 
	 * @param board 정렬 대상 게시판 모델 객체
	 */
	public void updateSortOderIncrease(Board board);

	/**
	 * 파라미터로 들어온 게시판보다 정렬값이 큰 게시판을 대상으로 현재 정렬값을 1씩 감소시킨다.
	 * 
	 * @param board 정렬 대상 게시판 모델 객체
	 */
	public void updateSortOderDecrease(Board board);

	/**
	 * 게시판을 물리적으로 삭제한다.
	 * 
	 * @param id 삭제대상 게시판ID
	 */
	public void physicalDelete(String id);

	/**
	 * 게시판의권한 정보를 조회한다.
	 * 
	 * @param userId 사용자 ID
	 * @param boardRootId 루트 게시판 ID
	 * @return 게시판 모델 객체
	 */
	public Board readHasPermissionBoard(String userId, String boardRootId);

	/**
	 * 자식 게시판을 조회한다.
	 * 
	 * @param boardId
	 * @return
	 */
	public List<Board> listChildrenBoard(String boardId);

	/**
	 * 게시판 하위 Tree 생성
	 * 
	 * @param map
	 * @return
	 */
	public List<Board> listChildrenBoard(Map<String, String> map);

	/**
	 * 게시판 하위 전체 목록- 자신포함 , 계층구조
	 * 
	 * @param boardId
	 * @return
	 */
	public List<Board> listChildBoardAll(String boardId);

	/**
	 * 게시판 하위 Cafe 일괄변경
	 * 
	 * @param map
	 * @return
	 */
	public List<Board> updateChildBoardAllCafe(Map<String, String> map);

	/* WS 추가 5/17 */
	/**
	 * 게시판 이동
	 */
	public void moveCafeBoard();

	/**
	 * 삭제된 워크스페이스 게시판 목록
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<Board> listDeleteBoardByCafe(String cafeId);

	/**
	 * 게시판 삭제
	 * 
	 * @param boardId
	 */
	public void deleteCafeBoard(String boardId);

	/**
	 * 게시판 삭제 배치용 삭제된 게시판 목록
	 * 
	 * @return
	 */
	public List<Board> listDeleteBoardBatch();

	/**
	 * 워크스페이스 게시판 목록
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<Board> listBoardByCafeId(String cafeId);

	/**
	 * 게시판 이동 정보 수정 - 해당 WS의 Target 게시판 하위로 이동처리
	 * 
	 * @param map
	 */
	public void updateMoveBoardByCafe(Map<String, String> map);

	/**
	 * 게시판 영구 삭제 처리 - 배치작업
	 * 
	 * @param boardId
	 */
	public void physicalDeleteBatch(String boardId);

	/**
	 * 게시판의 상위 게시판 정보
	 * 
	 * @param map
	 * @return
	 */
	public List<Board> getParents(Map<String, String> map);

	/**
	 * 게시판의 하위 게시판 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Board> getChildren(Map<String, String> map);

	/**
	 * 루트 게시판 ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 * 
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	public List<Board> listByBoardRootId(Map<String, String> map);

	/**
	 * @param map
	 * @return
	 */
	public List<Board> listByMenuBoardRootId(Map<String, String> map);

	/**
	 * 게시판 정보
	 * 
	 * @param map
	 * @return
	 */
	public Board get(Map<String, String> map);

	/**
	 * 게시판 삭제 처리
	 * 
	 * @param map
	 */
	public void physicalDelete(Map<String, String> map);

	/**
	 * 게시판 임시 삭제
	 * 
	 * @param boardId
	 */
	public void logicalDelete(String boardId);

	/**
	 * 게시판 Read 권한 정보
	 * 
	 * @param map
	 * @return
	 */
	public Board readHasPermissionBoard(Map<String, String> map);

	public List<Board> getCafeWriteBoard(String cafeId);
	
	/**
	 * 카페에 소속된 전체 게시판 목록
	 * @param cafeId
	 * @return
	 */
	public List<String> listBoardIdForCafe(String cafeId);

}
