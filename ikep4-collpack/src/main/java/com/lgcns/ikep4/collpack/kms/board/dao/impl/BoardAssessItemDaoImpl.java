/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.board.dao.BoardAssessItemDao;
import com.lgcns.ikep4.collpack.kms.board.model.BoardAssessItem;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * BoardItemDao 구현체 클래스
 */
@Repository("kmsBoardAssessItemDaoImpl")
public class BoardAssessItemDaoImpl extends GenericDaoSqlmap<BoardAssessItem, String> implements BoardAssessItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "collpack.kms.board.dao.boardAssessItem.";


	public List<BoardAssessItem> listAssessItem(String boardId) {
		// TODO Auto-generated method stub
		return (List<BoardAssessItem>) this.sqlSelectForList(NAMESPACE + "listAssessItem", boardId);
	}


	public String create(BoardAssessItem arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public BoardAssessItem get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void update(BoardAssessItem arg0) {
		// TODO Auto-generated method stub
		
	}


	public void createAssessItem(BoardAssessItem boardAssessItem) {
		// TODO Auto-generated method stub
		this.sqlInsert(NAMESPACE + "createAssessItem", boardAssessItem);
	}


	public void updateAssessItem(BoardAssessItem boardAssessItem) {
		// TODO Auto-generated method stub
		this.sqlUpdate(NAMESPACE + "updateAssessItem", boardAssessItem);
	}


	public void deleteAssessItem(String id) {
		// TODO Auto-generated method stub
		this.sqlDelete(NAMESPACE + "deleteAssessItem", id);
	}
	
	public int countIsAssessor(String userId) {

		return (Integer)this.sqlSelectForObject(NAMESPACE + "countIsAssessor", userId);

	}
	
	public int countIsKeyInfoAssessor(String userId) {

		return (Integer)this.sqlSelectForObject(NAMESPACE + "countIsKeyInfoAssessor", userId);

	}
}
