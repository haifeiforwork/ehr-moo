/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.innovation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 모두 상속받는 controller
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: InnovationBaseController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@RequestMapping(value = "/collpack/innovation")
public class InnovationBaseController extends BaseController {

	
	 @Autowired
	 ACLService aclService;
	
	
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
	

}
