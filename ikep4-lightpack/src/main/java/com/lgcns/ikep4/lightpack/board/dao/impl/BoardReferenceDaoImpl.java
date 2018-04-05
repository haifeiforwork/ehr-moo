/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.model.BoardReferencePK;

/**
 * BoardReferenceDao 구현체 클래스
 */
@Repository
public class BoardReferenceDaoImpl extends GenericDaoSqlmap<BoardReference, BoardReferencePK> implements BoardReferenceDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.board.dao.boardReference.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public BoardReference get(BoardReferencePK id) {
		return (BoardReference)this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(BoardReferencePK id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);
		return count > 0;
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public BoardReferencePK create(BoardReference object) {
		this.sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(BoardReference object) {
		this.sqlInsert(NAMESPACE + "update", object);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(BoardReferencePK id) {

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardReferenceDao#removeByItemId(java.lang.String)
	 */
	public void removeByItemId(String itemId) {
		this.sqlDelete(NAMESPACE + "removeByItemId", itemId);

	}

}
