/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.rss.web;

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
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.service.ChannelCategoryService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 *
 * @author 최재영 (Francis@lgcns.com)
 * @version $$Id: ChannelCategoryController.java 13086 2011-05-25 06:36:17Z loverfairy $$
 */
@Controller
@RequestMapping(value = "/support/rss/channelCategory")
public class ChannelCategoryController extends BaseController {

	@Autowired
	private ChannelCategoryService channelCategoryService;
	
	/**
	 * 카테고리 등록
	 * @param channelCategory 카테고리 객체
	 * @param result
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmit(@ValidEx ChannelCategory channelCategory, BindingResult result) {
		
		String categoryId = channelCategory.getCategoryId();
		
		try {
			
			if(result.hasErrors()) {
		        throw new IKEP4AjaxValidationException(result, messageSource);
		    }
			
			User user = (User) getRequestAttribute("ikep.user");
						
			channelCategory.setUpdaterId(user.getUserId());
			channelCategory.setUpdaterName(user.getUserName());
			channelCategory.setOwnerId(user.getUserId());
			
			if (!StringUtil.isEmpty(categoryId)) {
				channelCategoryService.update(channelCategory);
			} else {
				channelCategory.setRegisterId(user.getUserId());
				channelCategory.setRegisterName(user.getUserName());
				
				categoryId = channelCategoryService.create(channelCategory);
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
	@RequestMapping(value = "/listCategory.do", method = RequestMethod.POST)
	public ModelAndView list(@RequestParam(value = "categoryId", required = false) String categoryId) {
				
		ModelAndView mav = new ModelAndView("support/rss/channel/categoryList");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		List<ChannelCategory> list = channelCategoryService.list(user.getUserId());
		
		mav.addObject("categoryList", list);
		mav.addObject("size", list.size());
		
		
		ChannelCategory channelCategory = new ChannelCategory();
		
		if(!StringUtil.isEmpty(categoryId)){	//등록이면
			channelCategory = channelCategoryService.read(categoryId);
		}
		
		mav.addObject("channelCategory", channelCategory);
		
		return mav;
	}
	
	/**
	 * 카테고리 삭제
	 * @param categoryId 카테고리 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String remove(@RequestParam("categoryId") String categoryId) {
		
		try {
			channelCategoryService.delete(categoryId);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 		
		return "success";
	}
	
	/**
	 * 카테고리 order 수정
	 * @param categoryIdes 카테고리 id 리스트
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/applyCategoryOrder.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String applyCategoryOrder(@RequestParam("categoryIdes") String categoryIdes) {
		try {
			channelCategoryService.updateCategoryOrder(categoryIdes);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}
	
	/**
	 * 카테고리 이름 가져오기
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getgetCategoryTitle.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ChannelCategory getCategoryTitle(String channelCategoryId) {

		ChannelCategory channelCategory;
		try {
			channelCategory = channelCategoryService.read(channelCategoryId);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return channelCategory;
	}
	
	

}
