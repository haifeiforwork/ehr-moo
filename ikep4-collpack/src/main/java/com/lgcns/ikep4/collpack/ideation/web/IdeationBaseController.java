/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.model.IdCategory;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdCategoryService;
import com.lgcns.ikep4.collpack.ideation.service.IdUserActivitiesService;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 모두 상속받는 controller
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: IdeationBaseController.java 11183 2011-05-16 06:48:14Z loverfairy $$
 */
@RequestMapping(value = "/collpack/ideation")
public class IdeationBaseController extends BaseController {

	@Autowired
	private IdUserActivitiesService idUserActivitiesService;
	
	
	 @Autowired
	 ACLService aclService;

	@Autowired
	private IdCategoryService idCategoryService;
	
	/**
	 * 공통 - 나의 토론 활동
	 * @return
	 */
	@ModelAttribute("myActivities")
	public IdUserActivities getQnaStatistics(HttpServletRequest request) {
		
		IdUserActivities  idUserActivities =  new IdUserActivities();
		
		//Pattern p = Pattern.compile("(list.*?Qna.do)");	//리스트 페이지 에서만
		
        //Matcher m = p.matcher(requestUri);

		if(request.getMethod().equalsIgnoreCase("GET") ){
			User user = (User) getRequestAttribute("ikep.user");
		
			idUserActivities = idUserActivitiesService.get(user.getUserId());
		}
		
		return idUserActivities;
	}
	
	
	/**
	 * 어드민 유저 인지
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
        
		User user = (User) getRequestAttribute("ikep.user");
		
		String sysName = IdeationConstants.ITEM_TYPE_CODE_IDEATION;
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
	
	
	/**
	 * 공통으로 가져감 카테고리 리스트
	 * @return
	 */
	@ModelAttribute("categoryList")
	public List<IdCategory> getCategoryList(HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		List<IdCategory> list = new ArrayList<IdCategory>();
		if(!requestUri.contains("Reply.do") && !requestUri.contains("Ajax.do")& !requestUri.contains("More.do")& !requestUri.contains("Tag.do")){
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			if(portal != null){
				list = idCategoryService.list(portal.getPortalId());
			}
			
		} 
		
		return list;
	}

}
