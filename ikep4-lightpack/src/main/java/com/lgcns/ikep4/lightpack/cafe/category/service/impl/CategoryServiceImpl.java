/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.category.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.cafe.category.dao.CategoryDao;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;
import com.lgcns.ikep4.lightpack.cafe.category.service.CategoryService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Cafe 카테고리 Service 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CategoryServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service("cafeCategoryService")
public class CategoryServiceImpl extends GenericServiceImpl<Category, String> implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private IdgenService idgenService;

	/**
	 * cafe Type 별 카테고리 목록
	 */
	public List<Category> listByCafeType(Map<String, String> map) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Category> list = categoryDao.listByCafeType(map);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Category category : list) {
					category.setCategoryName(category.getCategoryEnglishName());
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
	 * 카테고리 목록보기
	 */
	public List<Category> listCafeCategoryByPortalId(String portalId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Category> list = categoryDao.listCafeCategoryByPortalId(portalId);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Category category : list) {
					category.setCategoryName(category.getCategoryEnglishName());
				}
			}
		}

		return list;
	}

	/**
	 * 개별 카테고리 정보 조회
	 */
	public Category readCategoryInfo(String categoryId, String portalId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		Category category = categoryDao.getCategoryInfo(categoryId, portalId);
		if (category != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				category.setCategoryName(category.getCategoryEnglishName());
			}
		}
		return category;
	}

	/**
	 * 1Depth 기본 카테고리
	 */
	public List<Category> defaultCategoryList(String portalId) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Category> list = categoryDao.getDefaultCategoryList(portalId);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Category category : list) {
					category.setCategoryName(category.getCategoryEnglishName());
				}
			}
		}
		return list;
	}

	/**
	 * 하위카테고리 리스트 listCafeCategoryByPortalId
	 */
	public List<Category> lowRankCategoryList(Map<String, String> param) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Category> list = categoryDao.lowRankCategoryList(param);
		if (list != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Category category : list) {
					category.setCategoryName(category.getCategoryEnglishName());
				}
			}
		}

		return list;
	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}
