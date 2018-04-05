/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.web;

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

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.base.tree.TreeManager;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Expert Network CategoryManagement controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkCategoryManagementController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/expertnetwork/admin")
public class ExpertNetworkCategoryManagementController extends CustomTreeController {

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
		ExpertNetworkCategory rootCategory = getRootCategory(user);

		// Category
		List<ExpertNetworkCategory> categoryList = categoryService.listAndChildCountAndTagsByCategoryParentId(rootCategory.getCategoryId());
		List<TreeNode> treeNodeList = listCategory2TreeNodes(categoryList);
		String categoryJSON = TreeManager.getJSON(treeNodeList);

		// view 연결
		ModelAndView mav = new ModelAndView("collpack/expertnetwork/admin/categoryManagement");

		mav.addObject("rootCategory", rootCategory);
		mav.addObject("categoryJSON", categoryJSON);

		return mav;
	}

	/**
	 * [Ajax]
	 * 카테고리 List 반환
	 * @param categoryParentId - 부모 카테고리ID
	 * @return
	 */
	@RequestMapping(value = "/listChildCategorys")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildCategorys(@RequestParam("categoryParentId") String categoryParentId) {
		List<ExpertNetworkCategory> categoryList = null;
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
	 * @param ENCategory
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategory(@ValidEx ExpertNetworkCategory ENCategory, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Session 정보
		User user = getSessionUser();
		ENCategory.setPortalId(user.getPortalId());
		ENCategory.setRegisterId(user.getUserId());
		ENCategory.setRegisterName(user.getUserName());

		// 카테고리명 중복체크
		if (0 < categoryService.countByCategoryName(ENCategory)) {
			throw new IKEP4AjaxValidationException("categoryName", getMessage("Duplication.ENCategory.categoryName"));
		}

		// Tag 체크
		String tags = ENCategory.getTags();
		String[] tagArray = tags.split(CommonConstant.COMMA_SEPARATER);

		// Tag는 10개 까지만
		if (MAX_TAG_COUNT < tagArray.length) {
			throw new IKEP4AjaxValidationException("tags", getMessage("MaxCount.ENCategory.tags"));
		}
		// Tag 중복검사
		for (int i = 0; i < tagArray.length - 1; i++) {
			for (int j = i + 1; j < tagArray.length; j++) {
				if (tagArray[i].equals(tagArray[j])) {
					throw new IKEP4AjaxValidationException("tags", getMessage("Duplication.ENCategory.tags", new String[]{tagArray[i]}));
				}
			}
		}
/*
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}
				return o1.compareTo(o2);
			}
		};
		Arrays.sort(tagArray, comparator);
		for (int i = 0; i < tagArray.length - 1; i++) {
			if (tagArray[i].equals(tagArray[i + 1])) {
				throw new IKEP4AjaxValidationException("tags", getMessage("Duplication.category.tags", new String[]{tagArray[i]}));
			}
		}
*/
		// 관리자 권한
		checkAdmin(user.getUserId());

		try {
			categoryService.createWithTags(ENCategory);
			String categoryJSON = TreeManager.getJSON(category2TreeNode(ENCategory));
			status.setComplete();
			return categoryJSON;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("createCategoryAjax", ex);
    	}
	}

	/**
	 * [Ajax]
	 * 카테고리 수정
	 * @param ENCategory
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/updateCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateCategory(@ValidEx ExpertNetworkCategory ENCategory, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// 카테고리명 중복체크
		if (0 < categoryService.countByCategoryName(ENCategory)) {
			throw new IKEP4AjaxValidationException("categoryName", getMessage("Duplication.ENCategory.categoryName"));
		}

		// Tag 체크
		String tags = ENCategory.getTags();
		String[] tagArray = tags.split(CommonConstant.COMMA_SEPARATER);

		// Tag는 10개 까지만
		if (MAX_TAG_COUNT < tagArray.length) {
			throw new IKEP4AjaxValidationException("tags", getMessage("MaxCount.ENCategory.tags"));
		}
		// Tag 중복검사
		for (int i = 0; i < tagArray.length - 1; i++) {
			for (int j = i + 1; j < tagArray.length; j++) {
				if (tagArray[i].equals(tagArray[j])) {
					throw new IKEP4AjaxValidationException("tags", getMessage("Duplication.ENCategory.tags", new String[]{tagArray[i]}));
				}
			}
		}

		// Session 정보
		User user = getSessionUser();
		ENCategory.setRegisterId(user.getUserId());
		ENCategory.setRegisterName(user.getUserName());

		// 관리자 권한
		checkAdmin(user.getUserId());

		try {
			categoryService.updateWithTags(ENCategory);
			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("updateCategoryAjax", ex);
    	}
	}

	/**
	 * [Ajax]
	 * 카테고리 삭제
	 * @param categoryId - 카테고리 ID
	 * @param result
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
    		throw new IKEP4AjaxException("deleteCategoryAjax", ex);
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
    		throw new IKEP4AjaxException("moveCategoryAjax", ex);
    	}
	}

}
