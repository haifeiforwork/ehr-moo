/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.support.message.model.Message;

/**
 * 게시글 DAO
 * 
 * 게시글 IKEP4_BD_ITEM 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardItemCategoryDao.java 16240 2011-08-18 04:08:15Z giljae $$
 */
public interface BoardItemCategoryDao extends GenericDao<BoardItemCategory, String> {

	
	/**
	 * 특정 게시판에 최신에 등록된 게시글을 가져온다.
	 * 
	 * @param parameter  boardId 게시판 ID, count 글갯수
	 * @return
	 */

	List<BoardItemCategory> listCategoryBoardItem(BoardItemCategory categoryBoardId);
	
	public void createCategoryNm(BoardItemCategory boardItemCategory);
	
	public void updateCategoryNm(BoardItemCategory boardItemCategory);
	
	public void deleteCategoryNm(BoardItemCategory boardItemCategory);
	
	public void updateCategoryAlign(BoardItemCategory boardItemCategory);

}
