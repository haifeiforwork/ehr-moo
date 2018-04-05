/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;

/**
 * AwardItemDao 구현체 클래스
 */
@Repository
public class AwardItemDaoImpl extends GenericDaoSqlmap<AwardItem, String> implements AwardItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.award.dao.awardItem.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public AwardItem get(String id) {
		return (AwardItem)this.sqlSelectForObject(NAMESPACE + "get", id);
	}
	
	public List<String> materialList(String itemId){
		return (List)sqlSelectForListOfObject(NAMESPACE + "materialList", itemId);
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
	public String create(AwardItem object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getItemId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(AwardItem object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#logicalDelete(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	public void logicalDelete(AwardItem awardItem) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", awardItem);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#physicalDelete(java.lang.String)
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
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public List<AwardItem> listBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", awardItemSearchCondition);
	}
	
	public List<AwardItem> listBySearchCondition3(AwardItemSearchCondition awardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition3", awardItemSearchCondition);
	}
	
	public List<AwardItem> deleteListBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "deleteListBySearchCondition", awardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#countBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public Integer countBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", awardItemSearchCondition);
	}
	
	public Integer countBySearchCondition3(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition3", awardItemSearchCondition);
	}
	
	public Integer deleteCountBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "deleteCountBySearchCondition", awardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public List<AwardItem> listBySearchCondition2(AwardItemSearchCondition awardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition2", awardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#countBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public Integer countBySearchCondition2(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition2", awardItemSearchCondition);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#physicalDeleteThread(java.lang.String)
	 */
	public void physicalDeleteThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThread", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#countChildren(java.lang.String)
	 */
	public Integer countChildren(String id) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#updateRecommendCount(java.lang.String)
	 */
	public void updateRecommendCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateRecommendCount", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateHitCount", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#updateStep(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	public void updateStep(AwardItem awardItem) {
		this.sqlUpdate(NAMESPACE + "updateStep", awardItem);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#updateLinereplyCount(java.lang.String)
	 */
	public void updateLinereplyCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateLinereplyCount", itemId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#updateReplyCount(java.lang.String)
	 */
	public void updateReplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateReplyCount", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listReplayItemThreadByItemId(java.lang.String)
	 */
	public List<AwardItem> listReplayItemThreadByItemId(String id) {
		return this.sqlSelectForList(NAMESPACE + "listReplayItemThreadByItemId", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listNoThreadBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public List<AwardItem> listNoThreadBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listNoThreadBySearchCondition", awardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#countNoThreadBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public Integer countNoThreadBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countNoThreadBySearchCondition", awardItemSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listLowerItemThread(java.lang.String)
	 */
	public List<AwardItem> listLowerItemThread(String id) {
		return this.sqlSelectForList(NAMESPACE + "listLowerItemThread", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#getByItemIdList(java.util.List)
	 */
	public List<AwardItem> listByItemIdArray(List<String> idList) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", idList);
	}

	public void saveAwardItemMaterial(AwardItem awardItem){
		this.sqlInsert(NAMESPACE + "saveAwardItemMaterial", awardItem);
	}
	
	public void deleteAwardItemMaterial(String itemId) {
		this.sqlDelete(NAMESPACE + "deleteAwardItemMaterial", itemId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listDeleteAwardItem(java.lang.Integer)
	 */
	public List<AwardItem> listBatchDeletePassedAwardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeletePassedAwardItem", getCount);
	}

	public List<AwardItem> listBatchDeleteStatusAwardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeleteStatusAwardItem", getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listRecentAwardItem(java.util.Map)
	 */
	public List<AwardItem> listRecentAwardItem(AwardItemSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAwardItem", searchCondition);
	}
	public List<AwardItem> listRecentAwardItem2(AwardItemSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAwardItem2", searchCondition);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listRecentAllAwardItem(java.util.Map)
	 */
	public List<AwardItem> listRecentAllAwardItem(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAllAwardItem", parameter);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#updateItemDelelteField(java.util.Map)
	 */
	public void updateItemDeleteField(Map<String, Object> parameter) {
		this.sqlUpdate(NAMESPACE + "updateItemDeleteField", parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemDao#listByAwardItemOfDeletedAward(java.util.Map)
	 */
	public List<AwardItem> listByAwardItemOfDeletedAward(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByAwardItemOfDeletedAward", parameter);
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
	public void updateAwardItemMove(Map<String, String> map) {
		this.sqlInsert(NAMESPACE + "updateAwardItemMove", map);
	}

	public List<AwardItem> listRecentAwardItemPortlet(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listRecentAwardItemPortlet", parameter);
	}
	
	/**
	 * 임시 저장 게시물 리스트 갯수
	 * @param awardItemSearchCondition
	 * @return
	 */
	public Integer countTempSaveBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTempSaveBySearchCondition", awardItemSearchCondition);
	}
	/**
	 * 임시 저장 게시물 리스트
	 * @param awardItemSearchCondition
	 * @return
	 */
	public List<AwardItem> listTempSaveBySearchCondition(AwardItemSearchCondition awardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listTempSaveBySearchCondition", awardItemSearchCondition);
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
	
	public int getAwardItemCount(AwardItem awardItem) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getAwardItemCount", awardItem);
	}
	
	public int getHappyAwardItemCount(AwardItem awardItem) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getHappyAwardItemCount", awardItem);
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
	public AwardItem getPrevItem(String awardId, String itemId, String readFlag) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("awardId", awardId);
		parameterMap.put("itemId", itemId);
		parameterMap.put("readFlag", readFlag);
		return (AwardItem)this.sqlSelectForObject(NAMESPACE + "getOtherItem", parameterMap);
	}
	
	/**
	 * 다음 게시글의 정보를 조회한다.
	 *
	 * @param bordId 조회 대상 게시판 ID
	 * @param itemId 조회 대상 게시글 ID
	 * @param readFlag "next"
	 * @return 다음 게시글의 정보
	 */
	public AwardItem getNextItem(String awardId, String itemId, String readFlag) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("awardId", awardId);
		parameterMap.put("itemId", itemId);
		parameterMap.put("readFlag", readFlag);
		return (AwardItem)this.sqlSelectForObject(NAMESPACE + "getOtherItem", parameterMap);
	}
	
	public Integer countAwardMenuList(AwardItemSearchCondition awardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countAwardMenuList");
	}

	public List<AwardItem> getAwardMenuList(AwardItemSearchCondition searchCondition) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getAwardMenuList");
	}
	
	public List<AwardItem> getPresentAwardMenuList(String userId) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getPresentAwardMenuList", userId);
	}

	public void deleteAwardMenuList(String userId) {
		// TODO Auto-generated method stub
		this.sqlDelete(NAMESPACE + "deleteAwardMenuList", userId);
	}

	public void insertAwardMenuList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		this.sqlInsert(NAMESPACE + "insertAwardMenuList", param);
	}
	
	public List<AwardItem> listCode(String grpCd){
		return sqlSelectForList(NAMESPACE + "listCode", grpCd);
	}
}
