/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemVersionDao;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemVersion;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemVersionSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;



/**
 * TODO Javadoc주석작성
 *
 * @author happyi1018
 * @version $Id: BoardItemVersionDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("kmsBoardItemVersionDaoImpl")
public class BoardItemVersionDaoImpl extends GenericDaoSqlmap<BoardItemVersion, String>  implements BoardItemVersionDao {

	private static final String NAMESPACE = "collpack.kms.board.dao.boardItemVersion."; 
	
	/**
	 * 버전 게시물 조회 
	 */
	public BoardItemVersion get(String itemId) {
		return (BoardItemVersion) this.sqlSelectForObject(NAMESPACE + "get", itemId);
	}
	/**
	 * 버전 게시물 조회
	 */
	public BoardItemVersion getVersionItem(String versionId) {
		return (BoardItemVersion) this.sqlSelectForObject(NAMESPACE + "getVersionItem", versionId);
	}
	/**
	 * 버전 게시물 조회 - 버전 최근 1개 
	 */
	public BoardItemVersion getMaxVersionId(String itemId) {
		return (BoardItemVersion) this.sqlSelectForObject(NAMESPACE + "getMaxVersionId", itemId);
	}	
	/**
	 * 버전 게시물 존재유무
	 */
	public boolean exists(String itemId) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", itemId);
		
		return count > 0;
	}
	/**
	 * 버전 정보 등록
	 */
	public String create(BoardItemVersion object) {
		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getVersionId();
	}

	public void update(BoardItemVersion object) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 버전 정보 삭제
	 */
	public void physicalDelete(String itemId) {
		this.sqlDelete(NAMESPACE + "physicalDelete", itemId); 
		
	}
	/**
	 * 버전 게시물 하위 삭제
	 */
	public void physicalDeleteThread(String itemId) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThread", itemId); 
		
	}
	/**
	 * 버전 게시물 목록
	 */
	public List<BoardItemVersion> listBySearchCondition(BoardItemVersionSearchCondition searchCondition) { 
		return (List<BoardItemVersion>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	/**
	 * 버전 게시물 갯수
	 */
	public Integer countBySearchCondition(BoardItemVersionSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
}
