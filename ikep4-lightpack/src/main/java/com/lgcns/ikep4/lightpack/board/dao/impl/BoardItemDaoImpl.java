/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;

/**
 * BoardItemDao 구현체 클래스
 */
@Repository
public class BoardItemDaoImpl extends GenericDaoSqlmap<BoardItem, String> implements BoardItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.board.dao.boardItem.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public BoardItem get(String id) {
		return (BoardItem)this.sqlSelectForObject(NAMESPACE + "get", id);
	}
	
	public List<String> workplaceList(String itemId){
		return (List)sqlSelectForListOfObject(NAMESPACE + "workplaceList", itemId);
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
	public String create(BoardItem object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getItemId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(BoardItem object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#logicalDelete(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void logicalDelete(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardItem);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String id) {
		this.sqlDelete(NAMESPACE + "physicalDelete", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", boardItemSearchCondition);
	}
	
	public List<BoardItem> listBySearchCondition3(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition3", boardItemSearchCondition);
	}
	
	public List<BoardItem> deleteListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "deleteListBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#countBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public Integer countBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", boardItemSearchCondition);
	}
	
	public Integer countBySearchCondition3(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition3", boardItemSearchCondition);
	}
	
	public Integer deleteCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "deleteCountBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listBySearchCondition2(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition2", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#countBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public Integer countBySearchCondition2(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition2", boardItemSearchCondition);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#physicalDeleteThread(java.lang.String)
	 */
	public void physicalDeleteThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThread", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#countChildren(java.lang.String)
	 */
	public Integer countChildren(String id) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#updateRecommendCount(java.lang.String)
	 */
	public void updateRecommendCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateRecommendCount", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateHitCount", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#updateStep(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void updateStep(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "updateStep", boardItem);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#updateLinereplyCount(java.lang.String)
	 */
	public void updateLinereplyCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateLinereplyCount", itemId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#updateReplyCount(java.lang.String)
	 */
	public void updateReplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateReplyCount", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listReplayItemThreadByItemId(java.lang.String)
	 */
	public List<BoardItem> listReplayItemThreadByItemId(String id) {
		return this.sqlSelectForList(NAMESPACE + "listReplayItemThreadByItemId", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listNoThreadBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#countNoThreadBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public Integer countNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listLowerItemThread(java.lang.String)
	 */
	public List<BoardItem> listLowerItemThread(String id) {
		return this.sqlSelectForList(NAMESPACE + "listLowerItemThread", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#getByItemIdList(java.util.List)
	 */
	public List<BoardItem> listByItemIdArray(List<String> idList) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", idList);
	}

	public void saveBoardItemWorkplace(BoardItem boardItem){
		this.sqlInsert(NAMESPACE + "saveBoardItemWorkplace", boardItem);
	}
	
	public void deleteBoardItemWorkplace(String itemId) {
		this.sqlDelete(NAMESPACE + "deleteBoardItemWorkplace", itemId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listDeleteBoardItem(java.lang.Integer)
	 */
	public List<BoardItem> listBatchDeletePassedBoardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeletePassedBoardItem", getCount);
	}

	public List<BoardItem> listBatchDeleteStatusBoardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeleteStatusBoardItem", getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listRecentBoardItem(java.util.Map)
	 */
	public List<BoardItem> listRecentBoardItem(BoardItemSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItem", searchCondition);
	}
	public List<BoardItem> listRecentBoardItem2(BoardItemSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItem2", searchCondition);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listRecentAllBoardItem(java.util.Map)
	 */
	public List<BoardItem> listRecentAllBoardItem(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAllBoardItem", parameter);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#updateItemDelelteField(java.util.Map)
	 */
	public void updateItemDeleteField(Map<String, Object> parameter) {
		this.sqlUpdate(NAMESPACE + "updateItemDeleteField", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.BoardItemDao#listByBoardItemOfDeletedBoard(java.util.Map)
	 */
	public List<BoardItem> listByBoardItemOfDeletedBoard(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardItemOfDeletedBoard", parameter);
	}
	
	public void updateMailCount(String itemId) {
		sqlUpdate(NAMESPACE + "updateMailCount", itemId);
	}

	public void updateMblogCount(String itemId) {
		sqlUpdate(NAMESPACE + "updateMblogCount", itemId);
	}
	
	/**
	 * 게시물 이동
	 */
	public void updateBoardItemMove(Map<String, String> map) {
		this.sqlInsert(NAMESPACE + "updateBoardItemMove", map);
	}

	public List<BoardItem> listRecentBoardItemPortlet(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItemPortlet", parameter);
	}
	
	/**
	 * 임시 저장 게시물 리스트 갯수
	 * @param boardItemSearchCondition
	 * @return
	 */
	public Integer countTempSaveBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTempSaveBySearchCondition", boardItemSearchCondition);
	}
	/**
	 * 임시 저장 게시물 리스트
	 * @param boardItemSearchCondition
	 * @return
	 */
	public List<BoardItem> listTempSaveBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listTempSaveBySearchCondition", boardItemSearchCondition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateMailSent(Set alarmIds) {
		String[] params = (String[]) alarmIds.toArray(new String[0]);
		sqlUpdate(NAMESPACE + "updateMailSent", params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateMailSending(Set itemIds) {
		String[] params = (String[]) itemIds.toArray(new String[0]);
		sqlUpdate(NAMESPACE + "updateMailSending", params);
	}
	
	public int getMailWaitYn(String itemId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getMailWaitYn", itemId);
	}
	
	public int getBoardItemCount(BoardItem boardItem) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getBoardItemCount", boardItem);
	}
	
	public int getHappyBoardItemCount(BoardItem boardItem) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getHappyBoardItemCount", boardItem);
	}
	
	public void updateMailWaitYn(String itemId) {
		sqlUpdate(NAMESPACE + "updateMailWaitYn", itemId);
	}
	public int getMailWaitTime() {
		return (Integer) sqlSelectForObject(NAMESPACE + "getMailWaitTime");
	}
	
	public int getMailWaitCount() {
		return (Integer) sqlSelectForObject(NAMESPACE + "getMailWaitCount");
	}
	
	/**
	 * 이전 게시글의 정보를 조회한다.
	 *
	 * @param bordId 조회 대상 게시판 ID
	 * @param itemId 조회 대상 게시글 ID
	 * @param readFlag "prev"
	 * @return 이전 게시글의 정보
	 */
	public BoardItem getPrevItem(String boardId, String itemId, String readFlag) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("boardId", boardId);
		parameterMap.put("itemId", itemId);
		parameterMap.put("readFlag", readFlag);
		return (BoardItem)this.sqlSelectForObject(NAMESPACE + "getOtherItem", parameterMap);
	}
	
	/**
	 * 다음 게시글의 정보를 조회한다.
	 *
	 * @param bordId 조회 대상 게시판 ID
	 * @param itemId 조회 대상 게시글 ID
	 * @param readFlag "next"
	 * @return 다음 게시글의 정보
	 */
	public BoardItem getNextItem(String boardId, String itemId, String readFlag) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("boardId", boardId);
		parameterMap.put("itemId", itemId);
		parameterMap.put("readFlag", readFlag);
		return (BoardItem)this.sqlSelectForObject(NAMESPACE + "getOtherItem", parameterMap);
	}
	
	public Integer countBoardMenuList(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBoardMenuList");
	}

	public List<BoardItem> getBoardMenuList(BoardItemSearchCondition searchCondition) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getBoardMenuList");
	}
	
	public List<BoardItem> getPresentBoardMenuList(String userId) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getPresentBoardMenuList", userId);
	}

	public void deleteBoardMenuList(String userId) {
		// TODO Auto-generated method stub
		this.sqlDelete(NAMESPACE + "deleteBoardMenuList", userId);
	}

	public void insertBoardMenuList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		this.sqlInsert(NAMESPACE + "insertBoardMenuList", param);
	}
}
