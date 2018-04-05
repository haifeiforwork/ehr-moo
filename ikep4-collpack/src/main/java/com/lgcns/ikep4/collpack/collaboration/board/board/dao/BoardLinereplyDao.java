/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.dao;

import java.util.List;


import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
 
/**
 * 댓글 DAO
 * 
 * 댓글 IKEP4_BD_LINEREPLY 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardLinereplyDao.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public interface BoardLinereplyDao extends GenericDao<BoardLinereply, String> {
	
	/**
	 * 댓글 검색조건을 이용해서 게시글 목록을 조회한다.
	 *
	 * @param boardLinereplySearchCondition 댓글  검색조건
	 * @return 댓글 목록
	 */
	List<BoardLinereply> listBySearchCondition(BoardLinereplySearchCondition searchCondtion); 
	 
	/**
	 * 댓글 검색조건을 충족하는 댓글의 총 갯수를 구한다.
	 *
	 * @param boardLinereplySearchCondition 댓글 검색조건
	 * @return 댓글 갯수
	 */
	Integer countBySearchCondition(BoardLinereplySearchCondition searchCondtion);
	
	/**
	 * 댓글의 하위 답변 댓글의 갯수를 조회한다.
	 *
	 * @param id 조회 대상 댓글 ID
	 * @return 자식댓글(답변) 갯수
	 */
	Integer countChildren(String id);
	
	/**
	 * 스탭을 업데이트 한다.
	 *
	 * @param boardLinereply the board linereply
	 */
	void updateStep(BoardLinereply boardLinereply);
	
	/**
	 * 삭제 플러그을 "1"로 업데이트 한다.
	 *
	 * @param boardLinereply 삭제 대상 댓글
	 */
	void logicalDelete(BoardLinereply boardLinereply);

	/**
	 * 댓글을 삭제 한다.
	 *
	 * @param id  삭제 대상 댓글 ID
	 */
	void physicalDelete(String id);
	
	/**
	 * 댓글 쓰레드를  댓글 ID를 기준으로 삭제 한다.
	 *
	 * @param id  삭제 대상 댓글 ID
	 */
	void physicalDeleteThreadByLinereplyId(String id);

	/**
	 * 게시글에 존재하는 모든 댓글을 삭제한다.
	 *
	 * @param id  게시글 ID
	 */
	void physicalDeleteThreadByItemId(String id);
	
	/**
	 * 게시글 쓰레드에 존재하는 모든 댓글 쓰레드를 삭제한다.
	 *
	 * @param id 게시글 ID
	 */
	void physicalDeleteThreadByItemThread(String id);
	
	
	
	
	/* WS 추가 5/17 */
	
	
	
}
