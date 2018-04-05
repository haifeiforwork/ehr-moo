/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.model.Award;

/**
 * AwardDao 구현체 클래스
 */
@Repository
public class AwardDaoImpl extends GenericDaoSqlmap<Award, String> implements AwardDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.award.dao.award.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Award get(String id) {
		return (Award)this.sqlSelectForObject(NAMESPACE + "get", id);
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
	public String create(Award object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getAwardId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Award object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String id) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", id);
	}

	/**
	 * 게시판 Root 하위 목록 
	 */
	public List<Award> listByAwardRootIdMap(Map<String, String> map) {
		return (List<Award>)this.sqlSelectForList(NAMESPACE + "listByAwardRootIdMap", map);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#listByAwardRootId(java.lang.String)
	 */
	public List<Award> listByAwardRootIdPermission(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByAwardRootIdPermission", parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#listByAwardRootId(java.lang.String)
	 */
	public List<Award> listByAwardRootId(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByAwardRootId", parameter);
	}
	
	public List<Award> listByAwardRootIdMobile(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByAwardRootIdMobile", parameter);
	}
	
	public List<Award> listByAwardRootIdPer(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listByAwardRootIdPer", parameter);
	}
	
	public List<Award> listByAwardRootIdPerMobile(Map<String, Object> parameter) {
		List<Award> award =  this.sqlSelectForList(NAMESPACE + "listByAwardRootIdPerMobile", parameter);
		return award;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#getParents(java.lang.String)
	 */
	public List<Award> getParents(String awardId) {
		return this.sqlSelectForList(NAMESPACE + "getParents", awardId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#getChildren(java.lang.String)
	 */
	public List<Award> getChildren(String awardId) {
		return this.sqlSelectForList(NAMESPACE + "getChildren", awardId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#updateMove(com.lgcns.ikep4.lightpack.award.model.Award)
	 */
	public void updateMove(Award award) {
		this.sqlUpdate(NAMESPACE + "updateMove", award);
	}



	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#readHasPermissionAward(java.lang.String, java.lang.String)
	 */
	public Award readHasPermissionAward(String userId, String awardRootId) {
		return (Award)this.sqlSelectForObject(NAMESPACE + "readHasPermissionAward", awardRootId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#updateSortOderIncrease(com.lgcns.ikep4.lightpack.award.model.Award)
	 */
	public void updateSortOderIncrease(Award award) {
		this.sqlUpdate(NAMESPACE + "updateSortOderIncrease", award);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#updateSortOderDecrease(com.lgcns.ikep4.lightpack.award.model.Award)
	 */
	public void updateSortOderDecrease(Award award) {
		this.sqlUpdate(NAMESPACE + "updateSortOderDecrease", award);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#listChildrenAward(java.lang.String)
	 */
	public List<Award> listChildrenAward(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenAwardPortal", parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#listChildrenAward(java.lang.String)
	 */
	public List<Award> listChildrenAward(String awardId) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenAward", awardId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#listBySelectedAwardForPublicAward()
	 */
	public List<Award> listBySelectedAwardForPublicAward() {
		return this.sqlSelectForList(NAMESPACE + "listBySelectedAwardForPublicAward");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#updateRemovePortlet()
	 */
	public void updateRemovePortlet() {
		this.sqlUpdate(NAMESPACE + "updateRemovePortlet");

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#updateSetupPortlet(java.lang.String)
	 */
	public void updateSetupPortlet(String awardId) {
		this.sqlUpdate(NAMESPACE + "updateSetupPortlet", awardId);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#listAwardTypeAward()
	 */
	public List<Award> listAwardTypeAward() {
		return this.sqlSelectForList(NAMESPACE + "listAwardTypeAward");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#updateAwardDeleteField(java.lang.Integer)
	 */
	public void updateAwardDeleteField(Map<String, Object> parameter) {
		this.sqlUpdate(NAMESPACE + "updateAwardDeleteField", parameter);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardDao#nextDeletedAward()
	 */
	public Award nextDeletedAward() {
		return (Award)this.sqlSelectForObject(NAMESPACE + "nextDeletedAward");
	}
	
	public void updateAwardMenuType(String type) {
		this.sqlUpdate(NAMESPACE + "updateAwardMenuType", type);
	}
	
	public String selectAwardMenuType() {
		return this.sqlSelectForObject(NAMESPACE + "selectAwardMenuType").toString();
	}
	
	public List<Award> listBySelectedAwardForMarkAward(String portletConfigId) {
		return this.sqlSelectForList(NAMESPACE + "listBySelectedAwardForMarkAward", portletConfigId);
	}
	
	public List<Award> listAwardTypeAwardmark(Map<String, Object> parameter) {
		return this.sqlSelectForList(NAMESPACE + "listAwardTypeAwardmark", parameter);
	}
	
	public void deleteSetupPortletMark(String portletConfigId) {
		this.sqlInsert(NAMESPACE + "deleteSetupPortletMark", portletConfigId);
	}
	
	public void createSetupPortletMark(Map<String, Object> parameter) {
		this.sqlInsert(NAMESPACE + "createSetupPortletMark",parameter);
	}
	
	public void updateDisplayAwardParentItem(){
		String dsp = "1";
		this.sqlUpdate(NAMESPACE + "updateDisplayAwardParentItem",dsp);
	}
	
	public void updateDisplayAwardItem(){
		String dsp = "1";
		this.sqlUpdate(NAMESPACE + "updateDisplayAwardItem",dsp);
	}

	public List<Award> getAwardMenuList() {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getAwardMenuList");
	}
	
	public Integer userAuthMgntYn(String userId) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "userAuthMgntYn", userId);
	}
}
