/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.portlet.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;

/**
 * BBS 포틀릿용 서비스 인터페이스 클래스
 *
 * @author 최현식
 * @version $Id: BoardPortletService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface BoardPortletService {

	/**
	 * 최신 게시물을 가져오는 메서드
	 * 
	 * @param userId 사용자 Id
	 * 
	 * @return 최근 게시물
	 */
	List<BoardItem> listRecentBoardItem(String userId, Integer count);

	/**
	 * 공용 게시판 으로 선택된 게시판 목록을 가져오는 메서드
	 * 
	 * @return 게시판 목록
	 */
	List<Board> listBySelectedBoardForPublicBoard();
	
	List<Board> listBySelectedBoardForMarkBoard(String registerId);

	/**
	 * 게시판에 대해서 포틀릿여부를 설정한다.
	 * 
	 * @param boardIdList 포틀릿으로 설정해야 하는 게시판
	 */
	void updatePortlet(List<String> boardIdList);

	/**
	 * 게시판형 게시판을 가져온다.
	 * @return
	 */
	List<Board> listBoardTypeBoard();
	
	List<Board> listBoardTypeBoardmark(String portletConfigId, String userId, String portalId) ;
 
	void deletePortletMark(String registerId);
	void createPortletMark(List<String> boardIdList, String registerId,String portletConfigId,String viewCount);

}
