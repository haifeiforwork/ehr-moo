/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.collpack.forum.service.FrUserActivitiesService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 모두 상속받는 controller
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: ForumBaseController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@RequestMapping(value = "/collpack/forum")
public class ForumBaseController extends BaseController {

	@Autowired
	private FrUserActivitiesService frUserActivitiesService;
	
	@Autowired
	private FrCategoryService frCategoryService;
	
	
	 @Autowired
	 ACLService aclService;
	 

	/**
	 * 공통으로 가져감 카테고리 리스트
	 * @return
	 */
	@ModelAttribute("categoryList")
	public List<FrCategory> getCategoryList(HttpServletRequest request) {
		
		List<FrCategory> list = new ArrayList<FrCategory>();
		
		String requestUri = request.getRequestURI();
			
		if(requestUri.contains("discussionForm.do") || request.getMethod().equalsIgnoreCase("GET") ){
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			if(portal != null){
				list = frCategoryService.list(portal.getPortalId());
			}
		} 
		
		return list;
	}
	
	
	
	/**
	 * 공통 - 나의 토론 활동
	 * @return
	 */
	@ModelAttribute("myActivities")
	public FrUserActivities getQnaStatistics(HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		FrUserActivities  frUserActivities =  new FrUserActivities();

		if(requestUri.contains("discussionForm.do") || request.getMethod().equalsIgnoreCase("GET") ){
			User user = (User) getRequestAttribute("ikep.user");
		
			frUserActivities = frUserActivitiesService.get(user.getUserId());
			
		}
		
		return frUserActivities;
	}
	
	
	/**
	 * 어드민 유저 인지
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
        
		User user = (User) getRequestAttribute("ikep.user");
		
		String sysName = ForumConstants.ITEM_TYPE_CODE_FORUM;
        String userId = user.getUserId();
        boolean isSystemAdmin = aclService.isSystemAdmin(sysName, userId);
        
        return isSystemAdmin;
        
	}
	
	/**
	 * 권한체크 - 본인자신이 쓴것인지 관리자인지
	 * @param isAdmin
	 * @param qnaRegisterId
	 * @param qnaId
	 */
	public void accessCheck(boolean isAdmin, String registerId){

		User user = (User) getRequestAttribute("ikep.user");
		
		//권한체크
		if(!(registerId.equals(user.getUserId()) || isAdmin)){
			throw new IKEP4AjaxException("Access Error", null);
		}
	}
	
	
}
