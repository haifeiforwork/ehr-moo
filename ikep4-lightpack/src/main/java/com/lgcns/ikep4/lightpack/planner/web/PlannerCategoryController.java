/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 범주 Controller
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryController.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Controller
@RequestMapping(value = "lightpack/planner/category")
public class PlannerCategoryController extends BaseController {

	@Autowired
	private PlannerCategoryService service;
	
	@RequestMapping("/list")
	public ModelAndView listlist() {
		ModelAndView mav = new ModelAndView("lightpack/planner/category/list");
		try {
			List<PlannerCategory> list = service.getList();
			mav.addObject("itemList", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@Deprecated
	@RequestMapping("/listjson")
	public @ResponseBody List<PlannerCategory> listjson(HttpServletResponse response) {
		List<PlannerCategory> list = null;
		try {
			list = service.getList(getUser().getLocaleCode());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	@RequestMapping("/editForm")
	public ModelAndView editForm(@RequestParam(value = "categoryId", required = false) String categoryId) {
		PlannerCategory category = service.readWithLocale(categoryId);	
		return new ModelAndView("lightpack/planner/category/editForm", "category", category);
	}
	
	@RequestMapping("/create")
	public @ResponseBody String create(@ModelAttribute("category") PlannerCategory category,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		String ret = "success";
		User user = getUser();
		String portalId = user.getPortalId();
		try {
			if(service.isDuplicate(category)) {
				ret = "duplicate";
			} else {				
				if(StringUtils.isNotBlank(category.getCategoryId())) {
					service.update(category);
				} else {				
					service.create(user, portalId, category);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret = "error";
		}
		return ret;
	}
	@RequestMapping("/delete")
	public ModelAndView delte(@RequestParam("cid") String[] cid,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		try {
			service.deleteCategory(cid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/lightpack/planner/category/list.do");
	}
	
	private User getUser() {
		return (User)getRequestAttribute("ikep.user"); 
	}
}
