/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.board.model.Board;

/**
 * 게시판 DAO
 * 
 * 게시판 IKEP4_BD_BOARD 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardDao.java 19387 2012-06-20 07:10:28Z icerainbow $$
 */
public interface BoardDao extends GenericDao<Board, String> {

	/**
	 * 게시판의 부모들을 조회한다.
	 *
	 * @param boardId 게시판 아이디
	 * @return 부모 목록
	 */
	List<Board> getParents(String boardId);

	/**
	 * 게시판의 자식들을 조회한다.
	 *
	 * @param boardId 게시판 아이디
	 * @return 자식 게시판 목록
	 */
	List<Board> getChildren(String boardId);
	
	/**
	 * 루트 게시판  ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 *
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	public List<Board> listByBoardRootIdMap(Map<String, String> map); 
	
	List<Board> listByBoardRootIdPermission(Map<String, Object> parameter);
	
	/**
	 * 루트 게시판  ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 *
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	List<Board> listByBoardRootId(Map<String, Object> parameter);
	
	List<Board> listByBoardRootIdMobile(Map<String, Object> parameter);

	List<Board> listByBoardRootIdPer(Map<String, Object> parameter);
	
	List<Board> listByBoardRootIdPerMobile(Map<String, Object> parameter);

	/**
	 * 게시판의 위치을 변경한다.
	 *
	 * @param board 이동 대상 게시판 모델 객체
	 */
	void updateMove(Board board);

	/**
	 * 파라미터로 들어온 게시판보다 정렬값이 큰 게시판을 대상으로 현재 정렬값을 1씩 증가시킨다.
	 *
	 * @param board 정렬 대상 게시판 모델 객체
	 */
	void updateSortOderIncrease(Board board);

	/**
	 * 파라미터로 들어온 게시판보다 정렬값이 큰 게시판을 대상으로 현재 정렬값을 1씩 감소시킨다.
	 *
	 * @param board 정렬 대상 게시판 모델 객체
	 */
	void updateSortOderDecrease(Board board);

	/**
	 * 게시판을 물리적으로 삭제한다.
	 * 
	 * @param id 삭제대상 게시판ID
	 */
	void physicalDelete(String id);

	/**
	 * 게시판의권한 정보를 조회한다.
	 *
	 * @param userId 사용자 ID
	 * @param boardRootId 루트 게시판 ID
	 * @return 게시판 모델 객체
	 */
	Board readHasPermissionBoard(String userId, String boardRootId);

	/**
	 * 자식 게시판을 조회한다.
	 * 
	 * @param boardId
	 * @return
	 */
	List<Board> listChildrenBoard(String boardId);
	
	/**
	 * 자식 게시판을 조회한다.
	 * 
	 * @param boardId
	 * @return
	 */
	List<Board> listChildrenBoard(Map<String, Object> parameter);

	/**
	 * 공용 게시판 으로 선택된 게시판 목록을 가져오는 메서드
	 * 
	 * @return 게시판 목록
	 */
	List<Board> listBySelectedBoardForPublicBoard();

	/**
	 * 포틀릿여부를 모든 게시판에 대해서 해제한다.
	 * 
	 * @return 게시판 목록
	 */
	void updateRemovePortlet();

	/**
	 * 포틀릿여부를 세팅한다.
	 * 
	 * @return 게시판 목록
	 */
	void updateSetupPortlet(String boardId);

	/**
	 * 게시판형 게시판 목록을 가져온다.
	 * @return
	 */
	List<Board> listBoardTypeBoard();

	/**
	 * 게시판의 삭제 상태는 변경한다.
	 * 상태가 삭제 대기(1)인 경우 배치에 의해서 삭제된다.
	 * 
	 * @param parameter boardId 게시판 ID, status 0 : 서비스중, 1: 삭제 대기
	 */
	void updateBoardDeleteField(Map<String, Object> parameter);

	/**
	 * 삭제되어야 하는 게시판을 가져온다.
	 * 
	 */
	Board nextDeletedBoard();
	
	/**
	 * 게시판 메뉴 형태 설정
	 */
	void updateBoardMenuType(String type);
	
	/**
	 * 게시판 메뉴 설정값을 가져온다.
	 */
	String selectBoardMenuType();
	
	List<Board> listBySelectedBoardForMarkBoard(String portletConfigId);
	
	List<Board> listBoardTypeBoardmark(Map<String, Object> parameter);
	
	void deleteSetupPortletMark(String portletConfigId);
	
	void createSetupPortletMark(Map<String, Object> parameter);
	
	void updateDisplayBoardParentItem();
	
	void updateDisplayBoardItem();
	
	public List<Board> getBoardMenuList();
}
