/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.admin.dao.CategoryDao;
import com.lgcns.ikep4.collpack.collaboration.admin.model.Category;
import com.lgcns.ikep4.collpack.collaboration.admin.search.CategorySearchCondition;
import com.lgcns.ikep4.collpack.collaboration.admin.service.CategoryService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Workspace 카테고리 Service 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CategoryServiceImpl.java 13911 2011-05-31 01:43:24Z happyi1018
 *          $
 */
@Service("workspaceCategoryService")
public class CategoryServiceImpl extends GenericServiceImpl<Category, String>
		implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private IdgenService idgenService;

	/**
	 * 검색 조건에 의한 카테고리 목록
	 */
	public SearchResult<Category> listBySearchCondition(
			CategorySearchCondition searchCondition, String isCodeView) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		Integer count = this.categoryDao
				.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Category> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Category>(searchCondition);

		} else {

			List<Category> list = this.categoryDao
					.listBySearchCondition(searchCondition);

			if (list != null) {
				if (!sessionuser.getLocaleCode().equals("ko")
						&& isCodeView.equals("N")) {
					for (Category category : list) {
						category.setCategoryName(category
								.getCategoryEnglishName());
					}
				}
			}

			searchResult = new SearchResult<Category>(list, searchCondition);
		}

		return searchResult;
	}

	public SearchResult<Category> listBySearchCondition(
			CategorySearchCondition searchCondition) {
		return listBySearchCondition(searchCondition, "N");
	}

	/**
	 * workspace Type 별 카테고리 목록
	 */
	public List<Category> listByWorkspaceType(Map<String, String> map) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Category> list = categoryDao.listByWorkspaceType(map);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Category category : list) {
					category.setCategoryName(category.getCategoryEnglishName());
					category.setTypeName(category.getTypeEnglishName());
				}
			}
		}

		return list;

	}

	/**
	 * 카테고리 생성
	 */
	public String create(Category object) {
		object.setCategoryId(idgenService.getNextId());
		object.setSortOrder(1);
		object.setIsDelete(0);

		return categoryDao.create(object);

	}

	/**
	 * 카테고리 조회
	 */
	public Category read(String categoryId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		Category category = categoryDao.get(categoryId);
		if (category != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				category.setCategoryName(category.getCategoryEnglishName());
			}
		}
		return category;
	}

	/**
	 * 카테고리 수정
	 */
	public void update(Category category) {
		categoryDao.update(category);
	}

	/**
	 * 유형에 속한 카테고리 존재유무 확인
	 */
	public boolean existsByTypeId(String typeId) {
		return categoryDao.existsByTypeId(typeId);
	}

	/**
	 * 카테고리에 속한 Workspace 존재유무 확인
	 */
	public boolean existsWorkspaceByCategoryId(String categoryId) {
		return categoryDao.existsWorkspaceByCategoryId(categoryId);
	}

	/**
	 * 카테고리 순서변경
	 */
	public void updateCategoryOrder(String categoryIds, String portalId) {

		String[] categoryIdList = categoryIds.split(",");

		for (int i = 0, count = categoryIdList.length; i < count; i++) {
			String categoryId = categoryIdList[i];
			Category category = new Category();
			category.setPortalId(portalId);
			category.setCategoryId(categoryId);
			category.setSortOrder(i);

			categoryDao.updateCategoryOrder(category);
		}

	}

	/**
	 * 카테고리 임시 삭제
	 */
	public void logicalDelete(Category object) {
		categoryDao.logicalDelete(object);
	}

	/**
	 * 카테고리 영구 삭제
	 */
	public void physicalDelete(String categoryId) {
		categoryDao.physicalDelete(categoryId);

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(
				name, RequestAttributes.SCOPE_SESSION);
	}

}
