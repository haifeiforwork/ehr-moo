/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.base.tree.TreeManager;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Map CategoryManagement controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategoryManagementController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgehub/admin")
public class KnowledgeMapCategoryManagementController extends CustomTreeController {

	private static final int MAX_TAG_COUNT = 10;

	/**
	 * [Ajax]
	 * 카테고리 관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/categoryManagementViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView categoryManagementViewAjax(SessionStatus status) {
		// Session 정보
		User user = getSessionUser();

		// Root Category
		KnowledgeMapCategory rootCategory = getRootCategory(user);

		// Category
		List<KnowledgeMapCategory> categoryList = categoryService.listAndChildCountAndTagsByCategoryParentId(rootCategory.getCategoryId());
		List<TreeNode> treeNodeList = listCategory2TreeNodes(categoryList);
		String categoryJSON = TreeManager.getJSON(treeNodeList);

		// view 연결
		ModelAndView mav = new ModelAndView("collpack/knowledgehub/admin/categoryManagement");

		mav.addObject("rootCategory", rootCategory);
		mav.addObject("categoryJSON", categoryJSON);

		return mav;
	}

	/**
	 * [Ajax]
	 * 카테고리 List 반환
	 * @param categoryParentId - 부모 카테고리
	 * @return
	 */
	@RequestMapping(value = "/listChildCategorys")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildCategorys(@RequestParam("categoryParentId") String categoryParentId) {
		List<KnowledgeMapCategory> categoryList = null;
		List<TreeNode> treeNodeList = null;

    	try {
    		categoryList = categoryService.listAndChildCountAndTagsByCategoryParentId(categoryParentId);
    		treeNodeList = listCategory2TreeNodes(categoryList);
        	return treeNodeList;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("categoryListAjax", ex);
    	}
	}

	/**
	 * [Ajax]
	 * 카테고리 생성
	 * @param KMCategory
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategory(@ValidEx KnowledgeMapCategory KMCategory, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Session 정보
		User user = getSessionUser();
		KMCategory.setPortalId(user.getPortalId());
		KMCategory.setRegisterId(user.getUserId());
		KMCategory.setRegisterName(user.getUserName());

		// 카테고리명 중복체크
		if (0 < categoryService.countByCategoryName(KMCategory)) {
			throw new IKEP4AjaxValidationException("categoryName", getMessage("Duplication.KMCategory.categoryName"));
		}

		// Tag 체크
		String tags = KMCategory.getTags();
		String[] tagArray = tags.split(CommonConstant.COMMA_SEPARATER);

		// Tag는 10개 까지만
		if (MAX_TAG_COUNT < tagArray.length) {
			throw new IKEP4AjaxValidationException("tags", getMessage("MaxCount.KMCategory.tags"));
		}
		// Tag 중복검사
		for (int i = 0; i < tagArray.length - 1; i++) {
			for (int j = i + 1; j < tagArray.length; j++) {
				if (tagArray[i].equals(tagArray[j])) {
					throw new IKEP4AjaxValidationException("tags", getMessage("Duplication.KMCategory.tags", new String[]{tagArray[i]}));
				}
			}
		}

		// 관리자 권한
		checkAdmin(user.getUserId());

		try {
			categoryService.createWithTags(KMCategory);
			String categoryJSON = TreeManager.getJSON(category2TreeNode(KMCategory));
			status.setComplete();
			return categoryJSON;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("categoryListAjax", ex);
    	}
	}

	/**
	 * [Ajax]
	 * 카테고리 수정
	 * @param KMCategory
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/updateCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateCategory(@ValidEx KnowledgeMapCategory KMCategory, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// 카테고리명 중복체크
		if (0 < categoryService.countByCategoryName(KMCategory)) {
			throw new IKEP4AjaxValidationException("categoryName", getMessage("Duplication.KMCategory.categoryName"));
		}

		// Tag 체크
		String tags = KMCategory.getTags();
		String[] tagArray = tags.split(CommonConstant.COMMA_SEPARATER);

		// Tag는 10개 까지만
		if (MAX_TAG_COUNT < tagArray.length) {
			throw new IKEP4AjaxValidationException("tags", getMessage("MaxCount.KMCategory.tags"));
		}
		// Tag 중복검사
		for (int i = 0; i < tagArray.length - 1; i++) {
			for (int j = i + 1; j < tagArray.length; j++) {
				if (tagArray[i].equals(tagArray[j])) {
					throw new IKEP4AjaxValidationException("tags", getMessage("Duplication.KMCategory.tags", new String[]{tagArray[i]}));
				}
			}
		}

		// Session 정보
		User user = getSessionUser();
		KMCategory.setRegisterId(user.getUserId());
		KMCategory.setRegisterName(user.getUserName());

		// 관리자 권한
		checkAdmin(user.getUserId());

		try {
			categoryService.updateWithTags(KMCategory);
			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("categoryListAjax", ex);
    	}
	}

	/**
	 * [Ajax]
	 * 카테고리 삭제
	 * @param categoryId - 카테고리ID
	 * @param status
	 */
	@RequestMapping(value = "/deleteCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteCategory(@RequestParam("categoryId") String categoryId, SessionStatus status) {

		// 관리자 권한
		checkAdmin();

		try {
			categoryService.deleteWithTagsHierarchy(categoryId);
			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("categoryListAjax", ex);
    	}
	}
	
	/**
	 * [Ajax]
	 * 카테고리 이동
	 * @param sourceId - 카테고리ID
	 * @param sourceParentId - 이동전 부모카테고리 ID
	 * @param sourceSortOrder - 이동전 순번
	 * @param targetParentId - 이동후 부모카테고리 ID
	 * @param targetSortOrder - 이동후 순번
	 * @param status
	 */
	@RequestMapping(value = "/moveCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void moveCategory(
			@RequestParam("sourceId") String sourceId,
			@RequestParam("sourceParentId") String sourceParentId,
			@RequestParam("sourceSortOrder") int sourceSortOrder,
			@RequestParam("targetParentId") String targetParentId,
			@RequestParam("targetSortOrder") int targetSortOrder,
			SessionStatus status) {

		// 관리자 권한
		checkAdmin();

		try {
			if (sourceParentId.equals(targetParentId)) {
				categoryService.moveInnerCategory(sourceId, sourceParentId, sourceSortOrder, targetSortOrder);
			} else {
				categoryService.moveOuterCategory(sourceId, sourceParentId, sourceSortOrder, targetParentId, targetSortOrder);
			}
			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("categoryListAjax", ex);
    	}
	}

}
