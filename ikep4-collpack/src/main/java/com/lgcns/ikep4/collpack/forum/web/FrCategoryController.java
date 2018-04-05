/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: FrCategoryController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@Controller
public class FrCategoryController extends ForumBaseController {

	@Autowired
	private FrCategoryService frCategoryService;
	
	/**
	 * 카테고리 등록
	 * @param frCategory	카테고리 객체
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmit(FrCategory frCategory, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		String categoryId = frCategory.getCategoryId();
		
		try {
			
			//권한 체크
			if(!isAdmin){
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			frCategory.setPortalId(portal.getPortalId());
			
			if (!StringUtil.isEmpty(categoryId)) {
				frCategoryService.update(frCategory);
			} else {
				frCategory.setRegisterId(user.getUserId());
				frCategory.setRegisterName(user.getUserName());
				
				categoryId = frCategoryService.create(frCategory);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return categoryId;
	}
	
	/**
	 * 카테고리 리스트
	 * @param categoryId	카테고리 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/categoryList.do", method = RequestMethod.GET)
	public ModelAndView categoryList(@RequestParam(value = "categoryId", required = false) String categoryId
							, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		//권한 체크
		if(!isAdmin){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		ModelAndView mav = new ModelAndView("collpack/forum/categoryList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<FrCategory> list = frCategoryService.list(portal.getPortalId());
		
		mav.addObject("categoryList", list);
		mav.addObject("size", list.size());
		
		
		FrCategory frCategory = new FrCategory();
		
		if(!StringUtil.isEmpty(categoryId)){	//등록이면
			frCategory = frCategoryService.read(categoryId);
		}
		
		mav.addObject("frCategory", frCategory);
		
		return mav;
	}
	
	/**
	 * 카테고리 삭제
	 * @param categoryId	카테고리 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteCategory.do", method = RequestMethod.GET)
	public String remove(@RequestParam("categoryId") String categoryId, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		//권한 체크
		if(!isAdmin){
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		frCategoryService.delete(categoryId, portal.getPortalId());

		return "redirect:listCategory.do";
	}
	
	/**
	 * 카테고리 순서 변경
	 * @param categoryIdes	카테고리 ID 리스트
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/applyCategoryOrder.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String applyCategoryOrder(@RequestParam("categoryIdes") String categoryIdes
									, @ModelAttribute("isAdmin") boolean isAdmin) {
		try {
			//권한 체크
			if(!isAdmin){
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			frCategoryService.updateCategoryOrder(categoryIdes);

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}
	
	

}
