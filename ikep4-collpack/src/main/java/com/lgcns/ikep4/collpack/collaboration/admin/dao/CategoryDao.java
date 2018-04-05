/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.admin.model.Category;
import com.lgcns.ikep4.collpack.collaboration.admin.search.CategorySearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 카테고리 DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceCategoryDao.java 1231 2011-02-24 09:19:30Z happyi1018
 *          $
 */
public interface CategoryDao extends GenericDao<Category, String> {

	/**
	 * 카테고리 목록
	 * @param workspaceSearchCondition
	 * @return
	 */
	public List<Category> listBySearchCondition(CategorySearchCondition searchCondition);
	/**
	 * 카테고리 갯수
	 * @param workspaceSearchCondition
	 * @return
	 */
	public Integer countBySearchCondition(CategorySearchCondition searchCondition);
	/**
	 * Type 별 카테고리 목록
	 * @param object
	 * @return
	 */
	public List<Category> listByWorkspaceType(Map<String, String> map);	
	
	/**
	 * TODO 카테고리 순서 변경
	 * @param object
	 */
	public void updateCategoryOrder(Category object);
	
	/**
	 * 카테고리 임시 삭제
	 * 
	 * @param category
	 */
	public void logicalDelete(Category object);

	/**
	 * 카테고리 영구 삭제
	 * 
	 * @param workspaceId
	 */
	public void physicalDelete(String categoryId);
	/**
	 * 유형에 속한 카테고리 존재유무 확인
	 * @param typeId
	 * @return
	 */
	public boolean existsByTypeId(String typeId);
	/**
	 * 카테고리에 속한 Workspace 존재유무 확인
	 * @param categoryId
	 * @return
	 */
	public boolean existsWorkspaceByCategoryId(String categoryId);
}
