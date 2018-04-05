/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.admin.dao.CategoryDao;
import com.lgcns.ikep4.collpack.collaboration.admin.model.Category;
import com.lgcns.ikep4.collpack.collaboration.admin.search.CategorySearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;



/**
 * 카테고리 Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceCategoryDaoImpl.java 2473 2011-03-11 02:04:25Z
 *          happyi1018 $
 */
@Repository("workspaceCategoryDao")
public class CategoryDaoImpl extends GenericDaoSqlmap<Category, String> implements CategoryDao {

	private static final String NAMESPACE = "collpack.collaboration.admin.dao.Category.";

	/**
	 * 카테고리 목록
	 */
	public List<Category> listBySearchCondition(CategorySearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);

	}
	/**
	 * 카테고리 갯수
	 */
	public Integer countBySearchCondition(CategorySearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	/**
	 * 타입별 목록
	 */
	public List<Category> listByWorkspaceType(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listByWorkspaceType", map);

	}
	/**
	 * 카테고리 생성
	 */
	public String create(Category object) {

		sqlInsert(NAMESPACE + "create", object);
		return object.getCategoryId();
	}
	/**
	 * 카테고리 조회
	 */
	public Category get(String categoryId) {
		return (Category) sqlSelectForObject(NAMESPACE + "get", categoryId);
	}
	/**
	 * 카테고리 존재유무 확인
	 */
	public boolean exists(String categoryId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", categoryId);
		return count > 0;
	}
	/**
	 * 유형에 속한 카테고리 존재유무 확인
	 */
	public boolean existsByTypeId(String typeId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "existsByTypeId", typeId);
		return count > 0;
	}
	/**
	 * 카테고리에 속한 Workspace 존재유무 확인
	 */
	public boolean existsWorkspaceByCategoryId(String categoryId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "existsWorkspaceByCategoryId", categoryId);
		return count > 0;
	}	
	/**
	 * 카테고리 수정
	 */
	public void update(Category object) {
		sqlUpdate(NAMESPACE + "update", object);

	}
	/**
	 * 카테고리 순서 변경
	 */
	public void updateCategoryOrder(Category object) {
		this.sqlUpdate(NAMESPACE + "updateCategoryOrder", object);

	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}
	/**
	 * 카테고리 임시삭제
	 */
	public void logicalDelete(Category object) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", object);
	}
	/**
	 * 카테고리 영구 삭제
	 */
	public void physicalDelete(String categoryId) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", categoryId);
	}

}
