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

import com.lgcns.ikep4.collpack.kms.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.collpack.kms.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommend;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommendPK;
import com.lgcns.ikep4.collpack.kms.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * BoardLinereplyDao 구현체 클래스
 */
@Repository("kmsBoardLinereplyDaoImpl")
public class BoardLinereplyDaoImpl extends GenericDaoSqlmap<BoardLinereply, String> implements BoardLinereplyDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "collpack.kms.board.dao.boardLinereply.";


	/**
	 * 댓글 정보 조회
	 */
	public BoardLinereply get(String id) {
		return (BoardLinereply)this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 댓글 존재유무
	 */
	public boolean exists(String id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);

		return count > 0;
	}

	/**
	 * 댓글 등록
	 */
	public String create(BoardLinereply object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getLinereplyId();
	}

	/**
	 * 댓글 수정
	 */
	public void update(BoardLinereply object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/**
	 * 댓글 Step 수정
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

	/**
	 * 댓글 임시 삭제
	 */
	public void logicalDelete(BoardLinereply boardLinereply) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardLinereply);
	}
	
	public void deleteLinereplyScore(BoardLinereply boardLinereply) {
		this.sqlUpdate(NAMESPACE + "deleteLinereplyScore", boardLinereply);
	}

	/**
	 * 댓글 삭제
	 */
	public void physicalDelete(String id) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", id);
	}


	/**
	 * 댓글 하위 삭제
	 */
	public void physicalDeleteThreadByLinereplyId(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByLinereplyId", id);
	}

	/**
	 * 댓글 하위 삭제
	 */
	public void physicalDeleteThreadByItemThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByItemThread", id);

	}

	/**
	 * 게시물 아이디로 댓글 삭제
	 */
	public void physicalDeleteThreadByItemId(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThreadByItemId", id);

	}

	/** 
	 * 댓글 목록
	 */
	public List<BoardLinereply> listBySearchCondition(BoardLinereplySearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondtion);
	}

	/**
	 * 댓글 갯수
	 */
	public Integer countBySearchCondition(BoardLinereplySearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondtion);
	}

	/**
	 * 댓글 하위 갯수
	 */
	public Integer countChildren(String id) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}
	
	
	public boolean scoreExists(BoardLinereply id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "scoreExists", id);
		return count > 9;
	}
	
	public boolean linereplyExists(BoardLinereply id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "linereplyExists", id);
		return count > 1;
	}
	
	public BoardLinereply insertScore(BoardLinereply object) {
		this.sqlInsert(NAMESPACE + "insertScore", object);
		return object;
	}
	
	
	/* WS 추가 */
	

}
