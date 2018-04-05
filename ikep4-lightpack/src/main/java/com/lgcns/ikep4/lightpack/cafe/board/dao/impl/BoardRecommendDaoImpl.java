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
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardRecommendDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardRecommendPK;


/**
 * BoardRecommendDao 구현체 클래스
 */
@Repository("cfBoardRecommendDaoImpl")
public class BoardRecommendDaoImpl extends GenericDaoSqlmap<BoardRecommend, BoardRecommendPK> implements
		BoardRecommendDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.cafe.board.dao.boardRecommend.";

	/**
	 * 추천정보 조회
	 */
	public BoardRecommend get(BoardRecommendPK id) {
		return (BoardRecommend) this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 추천정보 존재유무
	 */
	public boolean exists(BoardRecommendPK id) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "exists", id);
		return count > 0;
	}

	/**
	 * 추천정보 등록
	 */
	public BoardRecommendPK create(BoardRecommend object) {
		this.sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/**
	 * 추천정보 수정
	 */
	public void update(BoardRecommend object) {
		this.sqlInsert(NAMESPACE + "update", object);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(BoardRecommendPK id) {

	}

	/**
	 * 추천정보 삭제 - 해당 게시물의 전체 추천정보
	 */
	public void removeByItemId(String itemId) {
		this.sqlDelete(NAMESPACE + "removeByItemId", itemId);

	}
}
