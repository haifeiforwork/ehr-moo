/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.collpack.ideation.model.IdCategory;
import com.lgcns.ikep4.collpack.ideation.service.IdCategoryService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: IdCategoryController.java 13086 2011-05-25 06:36:17Z loverfairy $$
 */
@Controller
public class IdCategoryController extends IdeationBaseController {

	@Autowired
	private IdCategoryService idCategoryService;
	
	/**
	 * 카테고리 등록
	 * @param idCategory 카테고리 객체
	 * @param result
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmit(@ValidEx IdCategory idCategory, BindingResult result, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		String categoryId = idCategory.getCategoryId();
		
		try {
			
			if(result.hasErrors()) {
		        throw new IKEP4AjaxValidationException(result, messageSource);
		    }
			
			//권한 체크
			if(!isAdmin){
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			idCategory.setUpdaterId(user.getUserId());
			idCategory.setUpdaterName(user.getUserName());
			idCategory.setPortalId(portal.getPortalId());
			
			if (!StringUtil.isEmpty(categoryId)) {
				idCategoryService.update(idCategory);
			} else {
				idCategory.setRegisterId(user.getUserId());
				idCategory.setRegisterName(user.getUserName());
				
				categoryId = idCategoryService.create(idCategory);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return categoryId;
	}
	
	/**
	 * 카테고리 관리
	 * @param categoryId 카테고리 Id
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/listCategory.do", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "categoryId", required = false) String categoryId
							, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		//권한 체크
		if(!isAdmin){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		ModelAndView mav = new ModelAndView("collpack/ideation/categoryList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<IdCategory> list = idCategoryService.list(portal.getPortalId());
		
		mav.addObject("categoryList", list);
		mav.addObject("size", list.size());
		
		
		IdCategory idCategory = new IdCategory();
		
		if(!StringUtil.isEmpty(categoryId)){	//등록이면
			idCategory = idCategoryService.read(categoryId);
		}
		
		mav.addObject("idCategory", idCategory);
		
		return mav;
	}
	
	/**
	 * 카테고리 삭제
	 * @param categoryId 카테고리 ID
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
		
		idCategoryService.delete(categoryId, portal.getPortalId());

		return "redirect:listCategory.do";
	}
	
	/**
	 * 카테고리 order 수정
	 * @param categoryIdes 카테고리 id 리스트
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
			
			idCategoryService.updateCategoryOrder(categoryIdes);

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}
	
	

}
