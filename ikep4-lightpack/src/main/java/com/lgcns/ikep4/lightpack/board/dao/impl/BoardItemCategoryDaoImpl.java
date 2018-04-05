/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemCategoryDao;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.support.message.model.Message;

/**
 * BoardItemDao 구현체 클래스
 */
@Repository
public class BoardItemCategoryDaoImpl extends GenericDaoSqlmap<BoardItemCategory, String> implements BoardItemCategoryDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.board.dao.boardItemCategory.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemCategoryDao#listCategoryBoardItem(java.util.Map)
	 */
	public List<BoardItemCategory> listCategoryBoardItem(BoardItemCategory categoryBoardId) {
		return this.sqlSelectForList(NAMESPACE + "listCategoryBoardItem",categoryBoardId);
	}
	public void createCategoryNm(BoardItemCategory boardItemCategory) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",boardItemCategory);
	}
	
	public void deleteCategoryNm(BoardItemCategory boardItemCategory) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",boardItemCategory);
	}
	
	public void updateCategoryNm(BoardItemCategory boardItemCategory) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",boardItemCategory);
	}
	
	public void updateCategoryAlign(BoardItemCategory boardItemCategory) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",boardItemCategory);
	}
	
	@Deprecated
	public String create(BoardItemCategory arg0) {
		return null;
	}



	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}



	@Deprecated
	public BoardItemCategory get(String arg0) {
		return null;
	}



	@Deprecated
	public void remove(String arg0) {
		
	}



	@Deprecated
	public void update(BoardItemCategory arg0) {
		
	}
}
