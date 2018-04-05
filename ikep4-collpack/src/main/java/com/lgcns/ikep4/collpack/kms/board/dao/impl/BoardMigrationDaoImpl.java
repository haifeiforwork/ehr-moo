/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.board.dao.BoardMigrationDao;
import com.lgcns.ikep4.collpack.kms.board.model.BoardMigration;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author happyi1018
 * @version $Id: BoardMigrationDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("kmsBoardMigrationDaoImpl")
public class BoardMigrationDaoImpl  extends GenericDaoSqlmap< BoardMigration, String> implements BoardMigrationDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "collpack.kms.board.dao.BoardMigration.";

	public String create(BoardMigration arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public BoardMigration get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(BoardMigration arg0) {
		// TODO Auto-generated method stub
		
	}

	public String getBoardId(Map<String, String> boardIdMap) {

		return (String) this.sqlSelectForObject(NAMESPACE + "getBoardId", boardIdMap);
	}

	public void createItemMapping(Map<String, String> boardIdMap) {

		this.sqlInsert(NAMESPACE + "createItemMapping", boardIdMap);
	}

	public String getItemId(Map<String, String> boardIdMap) {

		return (String) this.sqlSelectForObject(NAMESPACE + "getItemId", boardIdMap);
	}

	public Object listItem() {
		return this.sqlSelectForListOfObject(NAMESPACE + "listBoardItem");
	}

	public void deleteItem() {
		this.sqlDelete(NAMESPACE + "deleteBoardItem");
	}
	
	public Integer migrateBoardItemChkCnt(Map<String, String> hashMap) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "migrateBoardItemChkCnt", hashMap);
	}

//	private static final String NAMESPACE = "collpack.kms.board.dao.boardMigration."; 
//	/* (non-Javadoc)
//	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
//	 */
//	public BoardMigration get(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
//	 */
//	public boolean exists(String id) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	/**
//	 * 게시판 이관 이력 등록
//	 */
//	public String create(BoardMigration object) {
//		this.sqlInsert(NAMESPACE + "create", object);
//		
//		return object.getMigrationId();
//	}
//
//	/**
//	 * 게시판 이관 이력 정보 삭제
//	 */
//	public void remove(String boardId) {
//		this.sqlDelete(NAMESPACE + "remove", boardId);
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
//	 */
//	public void update(BoardMigration object) {
//		// TODO Auto-generated method stub
//		
//	}

}
