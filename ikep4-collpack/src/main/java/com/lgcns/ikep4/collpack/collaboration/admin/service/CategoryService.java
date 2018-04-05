/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.admin.model.Category;
import com.lgcns.ikep4.collpack.collaboration.admin.search.CategorySearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * Workspace 카테고리 Service
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CategoryService.java 16543 2011-09-16 07:19:03Z giljae $
 */
@Transactional
public interface CategoryService extends GenericService<Category, String> {

	/**
	 * 검색 조건에 의한 카테고리 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Category> listBySearchCondition(
			CategorySearchCondition searchCondition);

	public SearchResult<Category> listBySearchCondition(
			CategorySearchCondition searchCondition, String isCodeView);

	/**
	 * workspace Type 별 카테고리 목록
	 * 
	 * @param object
	 * @return
	 */
	public List<Category> listByWorkspaceType(Map<String, String> map);

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
	 * 유형에 속한 카테고리 존재유무 확인
	 * 
	 * @param typeId
	 */
	public boolean existsByTypeId(String typeId);

	/**
	 * 카테고리에 속한 Workspace 존재유무 확인
	 * 
	 * @param categoryId
	 * @return
	 */
	public boolean existsWorkspaceByCategoryId(String categoryId);

}
