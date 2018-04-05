/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시판 배치 서비스 클래스
 *
 * @author 최현식
 * @version $Id: BoardBatchService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface BoardBatchService {

	/**
	 * 파라미터를 게시글의 쓰레드를 삭제한다.
	 * 
	 * @param itemId
	 */
	void adminDeleteBoardItemForBatch(String itemId);

	/**
	 * 전시 일자가 지난 게시물 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글 ID 목록
	 */
	List<BoardItem> listBatchDeletePassedBoardItem(Integer getCount);

	/**
	 * 게시물 상태가 삭제인  목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글 ID 목록
	 */
	List<BoardItem> listBatchDeleteStatusBoardItem(Integer getCount);

	/**
	 * 삭제 상태의 게시글을 가져온다.
	 * 
	 * @param boardId 게시판 Id
	 * @param repeadCount  가져와야 하는 삭제 목록
	 * @return
	 */
	List<BoardItem> listByBoardItemOfDeletedBoard(String boardId, Integer getCount);

	/**
	 * 삭제되어야 할 게시판을 가져온다.
	 * 
	 * @return
	 */
	Board nextDeletedBoard();

	/**
	 * 게시판을 삭제한다.
	 * 
	 * @param boardId
	 */
	void physicalBoardDelete(String boardId);
	
	void updateDisplayBoardParentItem();
	
	void updateDisplayBoardItem();
	
	void sendUserMail(List<BoardItemReader>  boardItemReaderList,String contents,User user,String wordName);
}
