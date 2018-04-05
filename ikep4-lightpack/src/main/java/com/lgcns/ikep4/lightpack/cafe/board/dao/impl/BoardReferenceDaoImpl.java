/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardReferencePK;


/**
 * BoardReferenceDao 구현체 클래스
 */
@Repository("cfBoardReferenceDaoImpl")
public class BoardReferenceDaoImpl extends GenericDaoSqlmap<BoardReference, BoardReferencePK> implements
		BoardReferenceDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.cafe.board.dao.boardReference.";

	/**
	 * 게시물 조회이력 정보 조회
	 */
	public BoardReference get(BoardReferencePK id) {
		return (BoardReference) this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 게시물 조회 정보 존재유무
	 */
	public boolean exists(BoardReferencePK id) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "exists", id);
		return count > 0;
	}

	/**
	 * 게시물 조회이력 정보 등록
	 */
	public BoardReferencePK create(BoardReference object) {
		this.sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/**
	 * 게시물 조회이력 정보 수정
	 */
	public void update(BoardReference object) {
		this.sqlInsert(NAMESPACE + "update", object);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(BoardReferencePK id) {

	}

	/**
	 * 게시물 아이디에 해당하는 게시물조회 정보이력삭제
	 */
	public void removeByItemId(String itemId) {
		this.sqlDelete(NAMESPACE + "removeByItemId", itemId);

	}

}
