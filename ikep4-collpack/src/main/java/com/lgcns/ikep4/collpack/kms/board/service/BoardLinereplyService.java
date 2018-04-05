/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.kms.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.kms.board.search.BoardLinereplySearchCondition;


/**
 * 댓글 서비스 클래스
 */
@Transactional
public interface BoardLinereplyService {

	/**
	 * 댓글 검색조건을 이용해서 게시글 목록을 조회한다.
	 * 
	 * @param boardLinereplySearchCondition 댓글 검색조건
	 * @return 댓글 목록
	 */
	SearchResult<BoardLinereply> listBoardLinereplyBySearchCondition(BoardLinereplySearchCondition searchCondition);

	/**
	 * 댓글을 조회한다.
	 * 
	 * @param linereplyId 댓글 ID
	 * @return 조회된 댓글 모델 객체
	 */
	BoardLinereply readBoardLinereply(String linereplyId);

	/**
	 * 댓글을 생성한다.
	 * 
	 * @param boardLinereply 생성해야 되는 댓글 모델 객체
	 * @return 생성된 댓글 ID
	 */
	String createBoardLinereply(BoardLinereply boardLinereply);
	
	void updateScore(BoardLinereply boardLinereply);

	/**
	 * 댓글의 답변을 생성한다.
	 * 
	 * @param boardLinereply 생성해야 되는 댓글 모델 객체
	 * @return 생성된 댓글 ID
	 */
	String createReplyBoardLinereply(BoardLinereply boardLinereply);

	/**
	 * 댓글의 답변을 수정한다.
	 * 
	 * @param boardLinereply 수정해야 되는 댓글 모델 객체
	 */
	void updateBoardLinereply(BoardLinereply boardLinereply);

	/**
	 * 관리자모드로 댓글을 삭제한다. 댓글의 하위 쓰레드를 모두 삭제한다.
	 * 
	 * @param id 댓글 ID
	 */
	void adminDeleteBoardLinereply(String itemId, String linereplyId);

	/**
	 * 사용자모드로 댓글을 삭제한다. 삭제대상의 하위가 존재하는 경우 삭제플러그를 "1"로 바꾸고 하위가 없으면 물리적으로 DELETE
	 * 한다.
	 * 
	 * @param boardLinereply 삭제 대상 댓글
	 */
	void userDeleteBoardLinereply(BoardLinereply boardLinereply);

	/* WS 추가 */

}
