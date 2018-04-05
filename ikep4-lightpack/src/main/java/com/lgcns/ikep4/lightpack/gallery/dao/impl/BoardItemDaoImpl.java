/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.gallery.model.BoardItem;
import com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 프로파일 갤러리게시판 BoardItemDao 구현체 클래스
 */
@Repository("pfBoardItemDaoImpl")
public class BoardItemDaoImpl extends GenericDaoSqlmap<BoardItem, String> implements BoardItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.gallery.dao.boardItem.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public BoardItem get(String id) {
		return (BoardItem)this.sqlSelectForObject(NAMESPACE + "get", id);
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
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#logicalDelete(com.lgcns.ikep4.lightpack.gallery.model.BoardItem)
	 */
	public void logicalDelete(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardItem);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#physicalDelete(java.lang.String)
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
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#countBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition)
	 */
	public Integer countBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", boardItemSearchCondition);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listBySearchConditionView(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionView", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#countBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition)
	 */
	public Integer countBySearchConditionView(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchConditionView", boardItemSearchCondition);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#physicalDeleteThread(java.lang.String)
	 */
	public void physicalDeleteThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThread", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#countChildren(java.lang.String)
	 */
	public Integer countChildren(String id) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateHitCount", id);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#updateLinereplyCount(java.lang.String)
	 */
	public void updateLinereplyCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateLinereplyCount", itemId);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listNoThreadBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#countNoThreadBySearchCondition(com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition)
	 */
	public Integer countNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listLowerItemThread(java.lang.String)
	 */
	public List<BoardItem> listLowerItemThread(String id) {
		return this.sqlSelectForList(NAMESPACE + "listLowerItemThread", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#getByItemIdList(java.util.List)
	 */
	public List<BoardItem> listByItemIdArray(List<String> idList) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", idList);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listDeleteBoardItem(java.lang.Integer)
	 */
	public List<BoardItem> listBatchDeletePassedBoardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeletePassedBoardItem", getCount);
	}

	public List<BoardItem> listBatchDeleteStatusBoardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeleteStatusBoardItem", getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listRecentBoardItem(java.util.Map)
	 */
	public List<BoardItem> listRecentBoardItem(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItem", parameter);
	}

	public List<BoardItem> listRecentBoardItemPortlet(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItemPortlet", parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listRecentAllBoardItem(java.util.Map)
	 */
	public List<BoardItem> listRecentAllBoardItem(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAllBoardItem", parameter);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#updateItemDelelteField(java.util.Map)
	 */
	public void updateItemDeleteField(Map<String, Object> parameter) {
		this.sqlUpdate(NAMESPACE + "updateItemDeleteField", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao#listByBoardItemOfDeletedBoard(java.util.Map)
	 */
	public List<BoardItem> listByBoardItemOfDeletedBoard(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardItemOfDeletedBoard", parameter);
	}
}
