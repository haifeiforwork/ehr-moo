/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale;

/**
 * 범주 Dao
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryDaoImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Repository(value = "plannerCategoryDao")
public class PlannerCategoryDaoImpl extends GenericDaoSqlmap<PlannerCategory, String> implements PlannerCategoryDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public PlannerCategory get(String id) {
		return (PlannerCategory) sqlSelectForObject(NAMESPACE + ".select", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(PlannerCategory category) {
		return (String) sqlInsert(NAMESPACE + ".insert", category);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(PlannerCategory category) {
		sqlUpdate(NAMESPACE + ".update", category);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + ".delete", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.planner.dao.PlannerCategoryDao#getList(java.lang.String)
	 */
	public List<PlannerCategory> getList() {
		return sqlSelectForList(NAMESPACE + ".getList");
	}

	public void delete(String[] cid) {
		sqlDelete(NAMESPACE + ".deleteList", cid);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#getLocaleList()
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getLocaleList() {
		return  getSqlMapClientTemplate().queryForList(NAMESPACE + ".getLocaleList");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#createCategoryLocale(com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale)
	 */
	public void createCategoryLocale(PlannerCategoryLocale plannerCategoryLocale) {
		sqlInsert(NAMESPACE + ".createCategoryLocale", plannerCategoryLocale);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#deletePlannerCategoryLocaleList(java.lang.String)
	 */
	public void deletePlannerCategoryLocaleList(String categoryId) {
		sqlDelete(NAMESPACE + ".deletePlannerCategoryLocaleList", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#deletePlannerCategoryLocaleList(java.lang.String[])
	 */
	public void deletePlannerCategoryLocaleList(String[] cid) {
		sqlDelete(NAMESPACE + ".deletePlannerCategoryLocaleListByMultiCid", cid);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#readWithLocale(java.lang.String)
	 */
	public PlannerCategory readWithLocale(String categoryId) {
		return (PlannerCategory) sqlSelectForObject(NAMESPACE + ".readWithLocale", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#getList(java.lang.String)
	 */
	public List<PlannerCategory> getList(String locale) {
		return sqlSelectForList(NAMESPACE + ".getListByLocale", locale);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao#isDuplicate(com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale)
	 */
	public int isDuplicate(PlannerCategoryLocale plannerCategoryLocale) {
		return (Integer) sqlSelectForObject(NAMESPACE + ".isDuplicate", plannerCategoryLocale);
	}

}
