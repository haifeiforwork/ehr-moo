/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 게시판 관리 서비스 클래스
 */
@Transactional
public interface BoardAdminService {

	/**
	 * 루트 게시판 ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 * 
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	// List<Board> listByBoardRootId(String boardRootId);

	/**
	 * 게시판을 조회한다.
	 * 
	 * @param boardId 게시판 ID
	 * @return 게시판 모델 객체
	 */
	Board readBoard(String boardId);

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
	 * 게시판을 물리적으로 삭제한다. 게시판, 게시글, 첨부파일, 위지윅에디터 파일, 댓글, 답글의 레코드를 물리적으로 삭제한다.
	 * (DELETE SQL)
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
	// List<Board> listParentBoard(String boardId);

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
	// List<Board> listChildrenBoard(String boardId);

	/* 5/17 WS 추가 */

	/**
	 * 게시판 이동 정보 저장 (Migration Tbl)
	 */
	public void moveBoardByCafe(String orgCafeId, String orgBoardId, String targetCafeId, String targetBoardId,
			User user);

	/**
	 * 게시판 이동
	 */
	public void moveCafeBoard();

	/**
	 * 삭제된 Cafe 내의 게시판 목록 조회
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<Board> listDeleteBoardByCafe(String workspaceId);

	/**
	 * 게시판 삭제
	 * 
	 * @param boardId
	 */
	public void deleteCafeBoard(String boardId);

	/**
	 * 삭제된 게시판 목록조회
	 * 
	 * @return
	 */
	public List<Board> listDeleteBoardBatch();

	/**
	 * 게시판 생성
	 * 
	 * @param board
	 * @param user
	 * @return
	 */
	public String createBoard(Board board, User user);

	/**
	 * TODO Javadoc주석작성
	 * 
	 * @param map
	 * @return
	 */
	public List<Board> listByMenuBoardRootId(Map<String, String> map);

	/**
	 * 루트 게시판 ID에 종속되어 있는 모든 게시판 목록을 조회한다.
	 * 
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트게시판 ID에 종속되어 있는 모든 게시판 목록
	 */
	public List<Board> listByBoardRootId(Map<String, String> map);

	/**
	 * 게시판 조회
	 * 
	 * @param map
	 * @return
	 */
	public Board readBoard(Map<String, String> map);

	/**
	 * 게시판 Read 권한 조회
	 * 
	 * @param map
	 * @return
	 */
	public Board readHasPermissionBoard(Map<String, String> map);

	/**
	 * 게시판 정보 수정
	 * 
	 * @param board
	 * @param user
	 */
	public void updateBoard(Board board, User user);

	/**
	 * 게시판 이름 변경
	 * 
	 * @param board
	 * @param user
	 */
	public void updateBoardName(Board board, User user);

	/**
	 * 게시판 영구 삭제
	 * 
	 * @param map
	 */
	public void physicalDeleteBoard(Map<String, String> map);

	/**
	 * 게시판 임시 삭제
	 * 
	 * @param boardId
	 */
	public void logicalDeleteBoard(String boardId);

	/**
	 * 게시판 부모 정보 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Board> listParentBoard(Map<String, String> map);

	/**
	 * 게시판 하위 정보 목록
	 * 
	 * @param boardMap
	 * @return
	 */
	List<Board> listChildrenBoard(Map<String, String> boardMap);

	public List<Board> getCafeWriteBoard(String cafeId);
	
	/**
	 * 카페에 소속된 전체 게시판 아이디 목록
	 * @param cafeId
	 * @return
	 */
	public List<String> listBoardIdForCafe(String cafeId);

}
