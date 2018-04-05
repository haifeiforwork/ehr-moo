/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.intelligentsearch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.servicepack.intelligentsearch.web.model.IntelligentConst;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * intelligentsearch BASE Controller
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ISBaseController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/intelligentsearch")
public class ISBaseController extends BaseController {
	
	/**
	 * 권한 관리
	 */
	@Autowired
    ACLService aclService;
	
	/**
	 * 어드민 유저 인지
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
		User user = (User) getRequestAttribute("ikep.user");
		
        String userId = user.getUserId();
        return  aclService.isSystemAdmin(IntelligentConst.ITEM_TYPE_CODE, userId);
	}
	/**
	 * 내정보
	 * @return
	 */
	@ModelAttribute("user")
	public User getUser(){
		return (User) getRequestAttribute("ikep.user");
	}
	
}
