/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.category.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;


/**
 * Cafe 카테고리 Service
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CategoryService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface CategoryService extends GenericService<Category, String> {

	/**
	 * workspace Type 별 카테고리 목록
	 * 
	 * @param object
	 * @return
	 */
	public List<Category> listByCafeType(Map<String, String> map);

	/**
	 * 카테고리 순선 변경
	 * 
	 * @param categoryIds
	 * @param portalId
	 */
	public void updateCategoryOrder(String categoryIds, String portalId);

	/**
	 * 카테고리 임시 삭제
	 * 
	 * @param object
	 */
	public void logicalDelete(Category object);

	/**
	 * 카테고리 영구 삭제
	 * 
	 * @param categoryId
	 */
	public void physicalDelete(String categoryId);

	/**
	 * LIST Cafe CategoryList
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Category> listCafeCategoryByPortalId(String portalId);

	/**
	 * Read Cafe Category
	 * 
	 * @param categoryId
	 * @param portalId
	 * @return
	 */
	public Category readCategoryInfo(String categoryId, String portalId);

	/**
	 * 1Depth DefautCategory
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Category> defaultCategoryList(String portalId);

	/**
	 * lowRank Category(Root DefaultCategory)
	 * 
	 * @param portalId
	 * @param defaultCateogryList
	 * @return
	 */
	public List<Category> lowRankCategoryList(Map<String, String> param);

}
