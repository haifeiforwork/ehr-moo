/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemReaderDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition;

/**
 * BoardDao 구현체 클래스
 */
@Repository
public class BoardItemReaderDaoImpl extends GenericDaoSqlmap<BoardItemReader, String> implements BoardItemReaderDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.board.dao.boardItemReader.";

	public String create(BoardItemReader boardItemReader) {
		this.sqlInsert(NAMESPACE + "create", boardItemReader);
		return boardItemReader.getItemId();
	}

	
	public Integer readerFlag(BoardItemReader boardItemReader) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "readerFlag", boardItemReader);
	}
	
	public Integer selectReaderCount(BoardItemReader boardItemReader) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "selectReaderCount", boardItemReader);
	}
	
	public Integer countReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countReaderBySearchCondition", searchCondtion);
	}
	
	public Integer countBoardBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBoardBySearchCondition", searchCondtion);
	}
	
	public Integer countNoReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countNoReaderBySearchCondition", searchCondtion);
	}
	
	public Integer checkReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "checkReaderBySearchCondition", searchCondtion);
	}
	
	public List<BoardItemReader> listReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listReaderBySearchCondition", searchCondtion);
	}
	
	public List<BoardItemReader> listBoardBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listBoardBySearchCondition", searchCondtion);
	}
	
	public List<BoardItemReader> listNoReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listNoReaderBySearchCondition", searchCondtion);
	}
	
	public void deleteReader(String id) {
		this.sqlDelete(NAMESPACE + "deleteReader", id);
	}
	
	
	public List<BoardItemReader> listBoardItemReader(String  boardItemId) {
		return this.sqlSelectForList(NAMESPACE + "listBoardItemReader", boardItemId);
	}
	
	public List<BoardItemReader> listBoardItemAllNoti() {
		return this.sqlSelectForList(NAMESPACE + "listBoardItemAllNoti");
	}
	
	public List<BoardItemReader> listReaderAllMail(String  boardItemId) {
		return this.sqlSelectForList(NAMESPACE + "listReaderAllMail", boardItemId);
	}
	
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public BoardItemReader get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(BoardItemReader arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateMailWaitYn(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateMailWaitYn", itemId);
	}
	
	public List<BoardItemReader> listBoardItemAllNotiInstant(String itemId) {
		return this.sqlSelectForList(NAMESPACE + "listBoardItemAllNotiInstant", itemId);
	}


}
