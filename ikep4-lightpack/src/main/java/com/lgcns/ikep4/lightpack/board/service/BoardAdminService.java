/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.lightpack.board.model.Board;

/**
 * 게시판 관리 서비스 클래스
 */
@Transactional
public interface BoardAdminService {
	List<Board> listByBoardRootIdPermission(String boardRootId, String portalId,String userId);
	/**
	 * 루트 게시판  ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 *
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	public List<Board> listByBoardRootIdMap(Map<String, String> map);
	/**
	 * 게시판 조회
	 * @param map
	 * @return
	 */
	/**
	 * 루트 게시판  ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 *
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	List<Board> listByBoardRootId(String boardRootId, String portalId);

	List<Board> listByBoardRootIdMobile(String boardRootId, String portalId);
	
	List<Board> listByBoardRootIdPer(Map<String, Object> param);
	
	List<Board> listByBoardRootIdPerMobile(Map<String, Object> param);
	
	/**
	 * 게시판을 조회한다.
	 *
	 * @param boardId 게시판 ID
	 * @return 게시판 모델 객체
	 */
	Board readBoard(String boardId);
	Board readBoard2(String boardId);
	/**
	 * 게시판의권한 정보를 조회한다.
	 *
	 * @param userId 사용자 ID
	 * @param boardRootId 루트 게시판 ID
	 * @return 게시판 모델 객체
	 */
	Board readHasPermissionBoard(String userId, String boardRootId);

	/**
	 * 게시판을 생성한다.
	 *
	 * @param 생성해야 하는 게시판 모델 객체
	 * @return 생성된 게시판 ID
	 */
	String createBoard(Board board);

	/**
	 * 게시판을 수정한다.
	 *
	 * @param 수정해야 하는 게시판 모델 객체
	 */
	void updateBoard(Board board);

	/**
	 * 게시판의 위치을 변경한다.
	 *
	 * @param board 이동 대상 게시판 모델 객체
	 */
	void updateBoardMove(Board board);

	/**
	 * 게시판을 물리적으로 삭제한다.
	 * 게시판, 게시글, 첨부파일, 위지윅에디터 파일, 댓글, 답글의 레코드를 물리적으로 삭제한다. (DELETE SQL)
	 * 
	 * @param id 삭제대상 게시판ID
	 */
	void physicalDeleteBoard(String boardId);

	/**
	 * 게시판의 부모들을 조회한다.
	 *
	 * @param boardId 게시판 아이디
	 * @return 부모 목록
	 */
	List<Board> listParentBoard(String boardId);

	/**
	 * 위지윅에디터에서 업로드 가능한 이미지 미디어 타입 체크한다.
	 *
	 * @param imageType 이미지 미디어타입
	 * @return 지원가능 여부
	 */
	Boolean checkSupportedImageMediaType(String imageType);

	/**
	 * 게시판의 자식 게시판을 가져온다.
	 * 
	 * @param boardId
	 * @return
	 */
	List<Board> listChildrenBoard(String boardId);
	
	/**
	 * 게시판의 자식 게시판을 가져온다.
	 * 
	 * @param boardId
	 * @return
	 */
	List<Board> listChildrenBoard(String boardId, String portalId);

	/**
	 * 게시판의 삭제 상태는 변경한다.
	 * 상태가 삭제 대기(1)인 경우 배치에 의해서 삭제된다.
	 * 
	 * @param boardId 게시판 ID
	 * @param status 0 : 서비스중, 1: 삭제 대기
	 */
	void updateBoardDeleteField(String boardId, Integer status);
	
	/**
	 * 게시판 메뉴 형태 설정
	 */
	void updateBoardMenuType(boolean isTree);
	
	/**
	 * 게시판 설정 값 반환
	 * @return true면 트리형태
	 */
	boolean isBoardMenuTree();
	
	public List<Board> getBoardMenuList();
}
