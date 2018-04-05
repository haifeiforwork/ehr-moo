/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemSearchCondition;


/**
 * BoardItemDao 구현체 클래스
 */
@Repository("cfBoardItemDaoImpl")
public class BoardItemDaoImpl extends GenericDaoSqlmap<BoardItem, String> implements BoardItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.cafe.board.dao.boardItem.";

	/**
	 * 게시물 조회
	 */
	public BoardItem get(String id) {
		return (BoardItem) this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 게시물 존재유무 확인
	 */
	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "exists", id);

		return count > 0;
	}

	/**
	 * 게시물 등록
	 */
	public String create(BoardItem object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getItemId();
	}

	/**
	 * 게시물 정보 수정
	 */
	public void update(BoardItem object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/**
	 * 게시물 임시 삭제
	 */
	public void logicalDelete(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardItem);
	}

	/**
	 * 게시물 영구 삭제
	 */
	public void physicalDelete(String id) {
		this.sqlDelete(NAMESPACE + "physicalDelete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 게시물 목록
	 */
	public List<BoardItem> listBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 갯수
	 */
	public Integer countBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 하위 삭제처리
	 */
	public void physicalDeleteThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThread", id);

	}

	/**
	 * 게시물 하위 갯수
	 */
	public Integer countChildren(String id) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}

	/**
	 * 게시물 추천 수 수정
	 */
	public void updateRecommendCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateRecommendCount", id);
	}

	/**
	 * 게시물 조회수 수정
	 */
	public void updateHitCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateHitCount", id);
	}

	/**
	 * 게시물 답변 정보 Step 수정
	 */
	public void updateStep(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "updateStep", boardItem);
	}

	/**
	 * 게시물 댓글 수 수정
	 */
	public void updateLinereplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateLinereplyCount", id);
	}

	/**
	 * 게시물 답변수 수정
	 */
	public void updateReplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateReplyCount", id);
	}

	/**
	 * 게시물 답변 목록
	 */
	public List<BoardItem> listReplayItemThreadByItemId(String id) {
		return this.sqlSelectForList(NAMESPACE + "listReplayItemThreadByItemId", id);
	}

	/**
	 * 게시물 목록
	 */
	public List<BoardItem> listNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 갯수
	 */
	public Integer countNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer) this
				.sqlSelectForObject(NAMESPACE + "countNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 하위 목록
	 */
	public List<BoardItem> listLowerItemThread(String id) {
		return this.sqlSelectForList(NAMESPACE + "listLowerItemThread", id);
	}

	/**
	 * 게시물 ID 목록
	 */
	public List<BoardItem> listByItemIdArray(Map<String, Object> params) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", params);
	}

	/**
	 * 
	 */
	public List<BoardItem> listByBoardIdForBoardDelete(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardIdForBoardDelete", boardId);
	}

	/**
	 * 게시물 영구 삭제
	 */
	public List<BoardItem> listBatchDeleteBoardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeleteBoardItem", getCount);
	}

	/**
	 * 게시물 최근 목록 - 게시판 ID
	 */
	public List<BoardItem> listRecentBoardItem(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItem", parameter);
	}

	/**
	 * 게시물 최근 목록 - 카페 ID
	 */
	public List<BoardItem> listRecentBoardItemForCafe(BoardItemSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItemForCafe", searchCondition);
	}

	/**
	 * 게시물 최근 목록 전체
	 */
	public List<BoardItem> listRecentAllBoardItem(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAllBoardItem", parameter);

	}

	/* 5/17 WS에서 추가 부분 * */

	/**
	 * Collaboration 메인 화면에서 나의 Cafe 중의 최근 게시물 조회
	 */
	public List<BoardItem> listMyCollBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "listMyCollBySearchCondition",
				boardItemSearchCondition);
	}

	/**
	 * 개별 WOrkspace 게시판 최근 목록
	 */
	public List<BoardItem> listBoardItemByPortlet(Map<String, Object> map) {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "listBoardItemByPortlet", map);
	}

	/**
	 * 해당 게시판의 게시물중 첨부파일이 있는 게시물 조회 (삭제 배치중 )
	 */
	public List<BoardItem> listDeleteBoardItem(String boardId) {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "listDeleteBoardItem", boardId);

	}

	/**
	 * 게시물 이동
	 */
	public void updateBoardItemMove(Map<String, String> map) {
		this.sqlInsert(NAMESPACE + "updateBoardItemMove", map);
	}

	/*
	 * public boolean existsBoardItemRecommend(BoardItemRecommend object) {
	 * Integer count = (Integer)this.sqlSelectForObject(NAMESPACE +
	 * "existsRecommend", object); return count > 0; } public boolean
	 * existsBoardItemReference(BoardItemReference object) { Integer count =
	 * (Integer)this.sqlSelectForObject(NAMESPACE + "existsReference", object);
	 * return count > 0; } public String
	 * createBoardItemRecommend(BoardItemRecommend object) {
	 * this.sqlInsert(NAMESPACE + "createRecommend", object); return
	 * object.getItemId(); } public String
	 * createBoardItemReference(BoardItemReference object) {
	 * this.sqlInsert(NAMESPACE + "createReference", object); return
	 * object.getItemId(); }
	 */
	/**
	 * 게시물 수정
	 */
	public void updateItem(BoardItem object) {
		this.sqlInsert(NAMESPACE + "updateItem", object);
	}

	/**
	 * 게시물 조회 정보 삭제
	 */
	public void physicalDeleteReference(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteReference", id);
	}

	/**
	 * 게시물 추천정보 삭제
	 */
	public void physicalDeleteRecommend(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteRecommend", id);
	}

	/**
	 * Cafe 내의 게시물 갯수 조회
	 */
	public Integer countMyCollBySearchCondition(String userId) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countMyCollBySearchCondition", userId);
	}

	public void updateMailCount(String itemId) {
		sqlUpdate(NAMESPACE + "updateMailCount", itemId);
	}

	public void updateMblogCount(String itemId) {
		sqlUpdate(NAMESPACE + "updateMblogCount", itemId);
	}

}
