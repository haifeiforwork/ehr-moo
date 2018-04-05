/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.gallery.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.gallery.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * BoardLinereplyDao 구현체 클래스
 */
@Repository("pfBoardLinereplyDaoImpl")
public class BoardLinereplyDaoImpl extends GenericDaoSqlmap<BoardLinereply, String> implements BoardLinereplyDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.gallery.dao.boardLinereply.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public BoardLinereply get(String id) {
		return (BoardLinereply)this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);

		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(BoardLinereply object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getLinereplyId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(BoardLinereply object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#updateStep(com.lgcns.ikep4.lightpack.gallery.model.BoardLinereply)
	 */
	public void updateStep(BoardLinereply boardLinereply) {
		this.sqlInsert(NAMESPACE + "updateStep", boardLinereply);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#logicalDelete(com.lgcns.ikep4.lightpack.gallery.model.BoardLinereply)
	 */
	public void logicalDelete(BoardLinereply boardLinereply) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardLinereply);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String id) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", id);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#physicalDeleteThreadByLinereplyId(java.lang.String)
	 */
	public void physicalDeleteThreadByLinereplyId(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByLinereplyId", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#physicalDeleteThreadByItemThread(java.lang.String)
	 */
	public void physicalDeleteThreadByItemThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByItemThread", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#physicalDeleteThreadByItemId(java.lang.String)
	 */
	public void physicalDeleteThreadByItemId(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByItemId", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#listBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardLinereplySearchCondition)
	 */
	public List<BoardLinereply> listBySearchCondition(BoardLinereplySearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondtion);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#countBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardLinereplySearchCondition)
	 */
	public Integer countBySearchCondition(BoardLinereplySearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondtion);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao#countChildren(java.lang.String)
	 */
	public Integer countChildren(String id) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}
}
