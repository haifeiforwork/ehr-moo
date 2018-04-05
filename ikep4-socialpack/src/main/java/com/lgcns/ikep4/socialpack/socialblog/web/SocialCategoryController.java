/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 블로그 카테고리 관리 및 조회 용 Controller
 *
 * @author 이형운
 * @version $id$
 */
@Controller
@RequestMapping(value = "/socialpack/socialblog/setting")
public class SocialCategoryController extends BaseController {

	/**
	 * 블로그 카테고리 관리 컨트롤용 Service
	 */
	@Autowired
	public SocialCategoryService socialCategoryService;
	
	/**
	 * 블로그 기본 정보 관리 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogService socialBlogService;
	
	/**
	 * 블로그 관리자 권한 체크 컨트롤용 Service
	 */
	@Autowired
	private ACLService aclService;
	
	/**
	 * 소셜 블로그 포스팅에 대한 시스템 관리자 접근 권한 확인
	 * 
	 * @param user 사용자 정보 객체
	 * @return 시스템 관리자 접근 권한 확인
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user.getUserId());
		//isSystemAdmin = true;
		return isSystemAdmin;
	}
	
    /**
     * 소셜 블로그 포스팅에 대한 관리자 접근 권한 확인
     * @param blogOwnerId 블로그 소유자 ID
     * @return 블로그 관리 권한 Flag
     */
    public boolean isBlogAdmin(String blogOwnerId) { 
    	
    	boolean isBlogAdmin = false;
    	
    	User sessionUser = readSessionUser();
    	if( blogOwnerId.equals(sessionUser.getUserId())){
    		isBlogAdmin = true;
    	}
    	
        return isBlogAdmin;
    }
    
    /**
     * 세션의 로그인 User 정보
     * @return 세션에서 받아온 User 정보 객체
     */
    private User readSessionUser() {  
    	return (User)getRequestAttribute("ikep.user"); 
    }
    
	/**
	 * 블로그 카테고리 관리 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 카테고리 리스트 View
	 */
	@RequestMapping(value = "/listCategory.do", method = RequestMethod.GET)
	public ModelAndView listCategory(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId) {
		
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/setting/socialCategoryList");
		
		List<SocialCategory> socialCategoryList = null;
		User user = readSessionUser();
		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		//해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		
		socialCategoryList = socialCategoryService.listByBlogId(socialBlog.getBlogId());	
		mav.addObject("socialCategoryList", socialCategoryList);
		mav.addObject("size", socialCategoryList.size());
		mav.addObject("socialBlog", socialBlog);
		
		return mav;
	}
	
	/**
	 * 카테고리 생성
	 * @param socialCategory 블로그 카테고리 객체 정보
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 생성된 블로그 카테고리 ID
	 */
	@RequestMapping(value = "/createCategory.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmit(SocialCategory socialCategory
										, @RequestParam(value = "blogOwnerId", required = false) String blogOwnerId) {
		
		String categoryId = socialCategory.getCategoryId();
		User user = readSessionUser();
		
		try {
			
			//권한 체크
			if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			if (!StringUtil.isEmpty(categoryId)) {
				socialCategoryService.update(socialCategory);
			} else {
				categoryId = socialCategoryService.create(socialCategory);
			}
			
			categoryId = socialCategory.getCategoryId();

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return categoryId;
	}
	
	/**
	 * 소셜 블로그 카테고리 삭제
	 * @param blogId 블로그 ID
	 * @param categoryId 블로그 카테고리 ID
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 카테고리 삭제후 카테고리 리스트 재 조회 View
	 */
	@RequestMapping(value = "/deleteCategory.do", method = RequestMethod.GET)
	public String remove(@RequestParam(value = "blogId", required = false) String blogId
						, @RequestParam("categoryId") String categoryId
						, @RequestParam(value = "blogOwnerId", required = false) String blogOwnerId ) {
		
		User user = readSessionUser();
		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		socialCategoryService.physicalDelete(categoryId);

		//return "redirect:/socialpack/socialblog/setting/listCategory.do?blogId="+ blogId+"&blogOwnerId="+ blogOwnerId;
		return "redirect:/socialpack/socialblog/setting/socialblogSettingHome.do?blogOwnerId="+ blogOwnerId+"&callback=setResult";
	}
	
	/**
	 * 카테고리 Sort Order 변경  저장
	 * @param categoryIdes 카테고리 ID 들
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 저장 결과 값
	 */
	@RequestMapping(value = "/applyCategoryOrder.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String applyCategoryOrder(@RequestParam("categoryIdes") String categoryIdes
												, @RequestParam(value = "blogOwnerId", required = false) String blogOwnerId ) {
		
		User user = readSessionUser();
		
		try {
			//권한 체크
			if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			socialCategoryService.updateCategoryOrder(categoryIdes);

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}

	/**
	 * 해당 사용자의 Blog 정보를 조회 하기 위한 메서드
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 기본 정보 객체
	 */
	public SocialBlog getBlogInfo(String blogOwnerId) {

		User sessionUser = readSessionUser();
		
		// 해당 블로그에 정보를 받아 온다.
		SocialBlog searchSocialBlog = new SocialBlog();
		searchSocialBlog.setOwnerId(blogOwnerId);
		
		return socialBlogService.select(searchSocialBlog,sessionUser.getLocaleCode());
	}
}
