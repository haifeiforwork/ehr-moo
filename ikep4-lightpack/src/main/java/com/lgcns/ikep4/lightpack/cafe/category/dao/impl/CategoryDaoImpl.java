/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.category.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.category.dao.CategoryDao;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;
import com.lgcns.ikep4.lightpack.cafe.category.search.CategorySearchCondition;


/**
 * 카테고리 Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CategoryDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository("cafeCategoryDao")
public class CategoryDaoImpl extends GenericDaoSqlmap<Category, String> implements CategoryDao {

	private static final String NAMESPACE = "lightpack.cafe.category.dao.Category.";

	public Integer countBySearchCondition(CategorySearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	public List<Category> listByCafeType(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listByCafeType", map);

	}

	public String create(Category object) {

		sqlInsert(NAMESPACE + "create", object);
		return object.getCategoryId();
	}

	public Category get(String categoryId) {
		return (Category) sqlSelectForObject(NAMESPACE + "get", categoryId);
	}

	public boolean exists(String categoryId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", categoryId);
		return count > 0;
	}

	public void update(Category object) {
		sqlUpdate(NAMESPACE + "update", object);

	}

	public void updateCategoryOrder(Category object) {
		this.sqlUpdate(NAMESPACE + "updateCategoryOrder", object);

	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public void logicalDelete(Category object) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", object);
	}

	public void physicalDelete(String categoryId) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", categoryId);
	}

	public List<Category> listCafeCategoryByPortalId(String portalId) {
		return this.sqlSelectForList(NAMESPACE + "listCafeCategoryByPortalId", portalId);
	}

	/*
	 * 개별 카테고리 상세조회
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.category.dao.CategoryDao#getCategoryInfo
	 * (java.lang.String, java.lang.String)
	 */
	public Category getCategoryInfo(String categoryId, String portalId) {

		Map<String, String> param = new HashMap<String, String>();
		param.put("categoryId", categoryId);
		param.put("portalId", portalId);

		return (Category) this.sqlSelectForObject(NAMESPACE + "getCategoryInfo", param);
	}

	/*
	 * 기본 카테고리
	 * @see com.lgcns.ikep4.lightpack.cafe.category.dao.CategoryDao#
	 * getDefaultCategoryList(java.lang.String)
	 */
	public List<Category> getDefaultCategoryList(String portalId) {
		return this.sqlSelectForList(NAMESPACE + "getDefaultCategoryList", portalId);
	}

	/*
	 * 하위카테고리
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.category.dao.CategoryDao#lowRankCategoryList
	 * (java.util.Map)
	 */
	public List<Category> lowRankCategoryList(Map<String, String> param) {
		return this.sqlSelectForList(NAMESPACE + "getLowRankCategoryList", param);
	}

}
