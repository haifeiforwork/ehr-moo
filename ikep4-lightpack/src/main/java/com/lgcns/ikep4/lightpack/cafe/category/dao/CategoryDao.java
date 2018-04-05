/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.category.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;
import com.lgcns.ikep4.lightpack.cafe.category.search.CategorySearchCondition;


/**
 * 카테고리 DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CategoryDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface CategoryDao extends GenericDao<Category, String> {

	/**
	 * 카테고리 갯수
	 * 
	 * @param cafeSearchCondition
	 * @return
	 */
	public Integer countBySearchCondition(CategorySearchCondition searchCondition);

	/**
	 * Type 별 카테고리 목록
	 * 
	 * @param object
	 * @return
	 */
	public List<Category> listByCafeType(Map<String, String> map);

	/**
	 * TODO 카테고리 순서 변경
	 * 
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
	 * @param cafeId
	 */
	public void physicalDelete(String categoryId);

	/**
	 * 카테고리 목록
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Category> listCafeCategoryByPortalId(String portalId);

	/**
	 * 개별카테고리 상세조회
	 * 
	 * @param categoryId
	 * @param portalId
	 * @return
	 */
	public Category getCategoryInfo(String categoryId, String portalId);

	/**
	 * 기본카테고리 조회
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Category> getDefaultCategoryList(String portalId);

	/**
	 * 하위카테고리 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<Category> lowRankCategoryList(Map<String, String> param);

}
