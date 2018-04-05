/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.addressbook.dao.CategoryDao;
import com.lgcns.ikep4.support.addressbook.model.Category;

/**
 * Dao 구현체 클래스
 */
@Repository
public class CategoryDaoImpl extends GenericDaoSqlmap<Category, String> implements CategoryDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "support.addressbook.dao.Category.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.dao.CategoryDao#listCategory(java.util.Map)
	 */
	public List<Category> listCategory(Category categoryBoardId) {
		return this.sqlSelectForList(NAMESPACE + "listCategory",categoryBoardId);
	}
	public void createCategoryNm(Category category) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",category);
	}
	
	public void deleteCategoryNm(Category category) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",category);
	}
	
	public void updateCategoryNm(Category category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",category);
	}
	
	public void updateCategoryAlign(Category category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",category);
	}
	
	@Deprecated
	public String create(Category arg0) {
		return null;
	}



	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}



	@Deprecated
	public Category get(String arg0) {
		return null;
	}



	@Deprecated
	public void remove(String arg0) {
		
	}



	@Deprecated
	public void update(Category arg0) {
		
	}
}
