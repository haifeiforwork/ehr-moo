/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.web;

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

import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: QnaCategoryController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@Controller
public class QnaCategoryController extends QnaBaseController {

	@Autowired
	private QnaCategoryService qnaCategoryService;
	
	/**
	 * 카테고리 등록
	 * @param qnaCategory 카테고리 객체
	 * @param result
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmit(@ValidEx QnaCategory qnaCategory, BindingResult result, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		String categoryId = qnaCategory.getCategoryId();
		
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
			
			qnaCategory.setUpdaterId(user.getUserId());
			qnaCategory.setUpdaterName(user.getUserName());
			qnaCategory.setPortalId(portal.getPortalId());
			
			if (!StringUtil.isEmpty(categoryId)) {
				qnaCategoryService.update(qnaCategory);
			} else {
				qnaCategory.setRegisterId(user.getUserId());
				qnaCategory.setRegisterName(user.getUserName());
				
				categoryId = qnaCategoryService.create(qnaCategory);
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
		
		ModelAndView mav = new ModelAndView("collpack/qna/categoryList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<QnaCategory> list = qnaCategoryService.list(portal.getPortalId());
		
		mav.addObject("categoryList", list);
		mav.addObject("size", list.size());
		
		
		QnaCategory qnaCategory = new QnaCategory();
		
		if(!StringUtil.isEmpty(categoryId)){	//등록이면
			qnaCategory = qnaCategoryService.read(categoryId);
		}
		
		mav.addObject("qnaCategory", qnaCategory);
		
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
		
		qnaCategoryService.delete(categoryId, portal.getPortalId());

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
			
			qnaCategoryService.updateCategoryOrder(categoryIdes);

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}
	
	

}
