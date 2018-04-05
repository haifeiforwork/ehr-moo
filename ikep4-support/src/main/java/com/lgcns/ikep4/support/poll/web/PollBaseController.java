/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;

import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 모두 상속받는 controller
 *
 * @author 서혜숙 
 * @version $$Id: PollBaseController.java 16316 2011-08-22 00:26:53Z giljae $$
 */
@RequestMapping(value = "/support/poll")
public class PollBaseController extends BaseController {
	
	 @Autowired
	 ACLService aclService;
	
	@ModelAttribute("isAdmin")
	
	/**
	 * 권한체크 - 관리자인지 여부
	 * @param 
	 */	
	public boolean isAdmin(){
        
		User user = (User) getRequestAttribute("ikep.user");
		
		String sysName = "Poll";
        String userId = user.getUserId();
        boolean isSystemAdmin = aclService.isSystemAdmin(sysName, userId);
        
        return isSystemAdmin;
        
	}
	
	/**
	 * 권한체크 - 본인자신이 쓴것인지 관리자인지 여부
	 * @param isAdmin
	 * @param registerId
	 */
	public void accessCheck(boolean isAdmin, String registerId){

		User user = (User) getRequestAttribute("ikep.user");
		
		//권한체크
		if(!(registerId.equals(user.getUserId()) || isAdmin)){
			throw new IKEP4AuthorizedException();
		}
	}
	

}
