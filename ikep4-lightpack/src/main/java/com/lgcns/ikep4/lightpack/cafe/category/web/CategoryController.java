/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.category.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;
import com.lgcns.ikep4.lightpack.cafe.category.service.CategoryService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeMaker;

/**
 * 카테고리 관리 Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeCategoryController.java 3449 2011-03-19 08:04:08Z
 *          happyi1018 $
 */

@Controller("cfController")
@RequestMapping(value = "/lightpack/cafe/category")
@SessionAttributes("cafe")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CafeService cafeService;

	/**
	 * cafe 카테고리 목록 메인
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/listCategoryView.do")
	public ModelAndView listCafeCategoryView() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		ModelAndView modelAndView = new ModelAndView(
				"lightpack/cafe/category/listCategoryView");

		List<Category> cafeCategoryList = this.categoryService
				.listCafeCategoryByPortalId(portal.getPortalId());
		String categoryTreeJson;
		categoryTreeJson = TreeMaker
				.init(cafeCategoryList, "categoryId", "parentId",
						"categoryName").create().toJsonString();
		modelAndView.addObject("categoryTreeJson", categoryTreeJson);

		return modelAndView;
	}

	// 카테고리 상세 조회 - AJAX R:읽기 전용 C:신규 U:수정
	@RequestMapping(value = "/categoryPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView categoryPage(@RequestParam("mode") String mode,
			@RequestParam("categoryId") String categoryId,
			@RequestParam("upYn") String upYn) {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = null;
		Category category = new Category();

		if (mode.equals("R")) {
			mav = new ModelAndView("/lightpack/cafe/category/readCategoryPage");
			category = categoryService.readCategoryInfo(categoryId,
					user.getPortalId());
		} else if (mode.equals("C")) {
			mav = new ModelAndView(
					"/lightpack/cafe/category/createCategoryPage");
			category.setParentId(categoryId);
		} else if (mode.equals("U")) {
			mav = new ModelAndView(
					"/lightpack/cafe/category/createCategoryPage");
			category = categoryService.readCategoryInfo(categoryId,
					user.getPortalId());
		}

		mav.addObject("category", category);

		// etc
		mav.addObject("mode", mode);
		mav.addObject("upYn", upYn);

		return mav;
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
	String onSubmit(Category category, BindingResult result) {

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

	// 카테고리 상세 저장 - AJAX
	@RequestMapping(value = "/saveCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String saveCategory(Category category, @RequestParam("mode") String mode) {
		User user = (User) getRequestAttribute("ikep.user");
		String categoryId = "";
		category.setPortalId(user.getPortalId());
		try {
			if (mode.equals("C")) {// 신규
				ModelBeanUtil.bindRegisterInfo(category, user.getUserId(),
						user.getUserName());
				categoryId = categoryService.create(category);
			} else { // 수정
				categoryId = category.getCategoryId();
				ModelBeanUtil.bindUpdaterInfo(category, user.getUserId(),
						user.getUserName());
				categoryService.update(category);
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
	public String deleteCafeCategory(
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
		return "redirect:/lightpack/cafe/category/listCategoryView.do";
	}

	/**
	 * 카테고리 체크
	 * 
	 * @param categoryId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/checkCategory.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkCafeCategory(@RequestParam("categoryId") String categoryId) {
		// 세션 객체 가지고 오기
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		String check = "";

		CafeSearchCondition searchCondition = new CafeSearchCondition();
		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setCategoryId(categoryId);
		SearchResult<Cafe> searchResult = cafeService
				.listBySearchCondition(searchCondition);

		if (searchResult.getRecordCount() < 1) {
			check = "ok";
		}

		return check;
	}

	@RequestMapping(value = "/viewCategoryTree")
	public ModelAndView viewBoardTree() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		ModelAndView modelAndView = new ModelAndView(
				"lightpack/cafe/category/viewCategoryTree");

		List<Category> cafeCategoryList = this.categoryService
				.listCafeCategoryByPortalId(portal.getPortalId());
		String categoryTreeJson;
		categoryTreeJson = TreeMaker
				.init(cafeCategoryList, "categoryId", "parentId",
						"categoryName").create().toJsonString();
		modelAndView.addObject("categoryTreeJson", categoryTreeJson);
		return modelAndView;

	}

}
