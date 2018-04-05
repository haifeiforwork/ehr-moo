/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.admin.model.Category;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.search.CategorySearchCondition;
import com.lgcns.ikep4.collpack.collaboration.admin.service.CategoryService;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 카테고리 관리 Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceCategoryController.java 3449 2011-03-19 08:04:08Z
 *          happyi1018 $
 */

@Controller
@RequestMapping(value = "/collpack/collaboration/admin/category")
@SessionAttributes("collaboration")
public class CategoryController extends BaseController {

	@Autowired
	private WorkspaceTypeService workspaceTypeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * workspace Type 별 카테고리 목록 메인
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/listCategoryView.do")
	public ModelAndView listWorkspaceCategoryView(
			CategorySearchCondition searchCondition) {

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(user.getPortalId());

		ModelAndView modelAndView = new ModelAndView(
				"collpack/collaboration/admin/category/listCategoryView");

		List<WorkspaceType> workspaceTypeList = workspaceTypeService
				.listWorkspaceType(user.getPortalId());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);

		String typeId = null;
		String typeName = null;

		WorkspaceType workspaceType = new WorkspaceType();
		workspaceType.setPortalId(portal.getPortalId());

		if (searchCondition.getTypeId() == null && workspaceTypeList != null
				&& workspaceTypeList.size() > 0) {

			typeId = (String) workspaceTypeList.get(0).getTypeId();
			typeName = (String) workspaceTypeList.get(0).getTypeName();
			searchCondition.setTypeId(typeId);

		} else {
			workspaceType.setTypeId(searchCondition.getTypeId());
			typeName = workspaceTypeService.getTypeName(workspaceType);
		}
		SearchResult<Category> searchResult = categoryService
				.listBySearchCondition(searchCondition, "Y");
		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("portalId", portal.getPortalId());
		mapCategory.put("typeId", searchCondition.getTypeId());
		// 카테고리별 WS 갯수 조회
		List<Workspace> countWorkspaceTypeList = workspaceService
				.countWorkspaceType(mapCategory);

		Category category = new Category();
		modelAndView.addObject("category", category);

		modelAndView.addObject("categoryList", searchResult.getEntity());
		if (searchResult.getEntity() != null) {
			modelAndView.addObject("size", searchResult.getEntity().size());
		}
		modelAndView.addObject("typeName", typeName);
		modelAndView
				.addObject("countWorkspaceTypeList", countWorkspaceTypeList);
		modelAndView.addObject("searchCondition",
				searchResult.getSearchCondition());

		return modelAndView;
	}

	/**
	 * workspace Type 별 카테고리 목록
	 * 
	 * @param searchCondition
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listWorkspaceCategory.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Category> listWorkspaceCategory(CategorySearchCondition searchCondition) {

		SearchResult<Category> searchResult = null;
		try {
			User user = (User) getRequestAttribute("ikep.user");
			searchCondition.setPortalId(user.getPortalId());
			searchCondition.setSortColumn("A.SORT_ORDER");
			searchResult = categoryService
					.listBySearchCondition(searchCondition);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return searchResult.getEntity();
	}

	/**
	 * 카테고리 등록/수정처리
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/createCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String onSubmit(@Valid Category category, BindingResult result) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		String categoryId = category.getCategoryId();
		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			/**
			 * 카테고리 등록/수정처리
			 */
			if (!StringUtil.isEmpty(categoryId)) {
				category.setPortalId(portal.getPortalId());
				category.setRegisterId(user.getUserId());
				category.setRegisterName(user.getUserName());
				category.setUpdaterId(user.getUserId());
				category.setUpdaterName(user.getUserName());

				categoryService.update(category);
			} else {
				category.setPortalId(portal.getPortalId());
				category.setRegisterId(user.getUserId());
				category.setRegisterName(user.getUserName());
				category.setUpdaterId(user.getUserId());
				category.setUpdaterName(user.getUserName());

				categoryId = categoryService.create(category);

			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}
		return categoryId;
	}

	/**
	 * 카테고리 순서 변경
	 * 
	 * @param categoryIds
	 * @return
	 */
	@RequestMapping(value = "/updateCategoryOrder.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateCategoryOrder(@RequestParam("categoryIds") String categoryIds) {

		User user = (User) getRequestAttribute("ikep.user");
		try {
			categoryService
					.updateCategoryOrder(categoryIds, user.getPortalId());
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}
		return "success";
	}

	/**
	 * 카테고리 임시 삭제
	 * 
	 * @param categoryId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteCategory.do", method = RequestMethod.GET)
	public String deleteWorkspaceCategory(
			@RequestParam("categoryId") String categoryId, SessionStatus status) {
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Category category = new Category();
		category.setPortalId(portal.getPortalId());
		category.setCategoryId(categoryId);
		category.setUpdaterId(user.getUserId());
		category.setUpdaterName(user.getUserName());

		categoryService.logicalDelete(category);
		status.setComplete();
		return "redirect:/collpack/collaboration/admin/category/listCategoryView.do";
	}

	/**
	 * 유형에 속한 카테고리 존재유무 확인
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/existsCategory.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean existsCategory(@RequestParam("typeId") String typeId, Model model) {

		boolean checkCategory = false;
		try {
			// 조직인 경우 WS 정보수정시 팀변경을 위한 팀 개설여부 확인 (개설대기,개설,폐쇄대기는 팀변경이 불가)
			checkCategory = categoryService.existsByTypeId(typeId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return checkCategory;

	}

	/**
	 * 유형에 속한 카테고리 존재유무 확인
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/existsWorkspace.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean existsWorkspaceByCategoryId(
			@RequestParam("categoryId") String categoryId, Model model) {

		boolean checkWorkspace = false;
		try {
			// 조직인 경우 WS 정보수정시 팀변경을 위한 팀 개설여부 확인 (개설대기,개설,폐쇄대기는 팀변경이 불가)
			checkWorkspace = categoryService
					.existsWorkspaceByCategoryId(categoryId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return checkWorkspace;

	}
}
