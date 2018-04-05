/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 권한 설정 뷰 Controller
 *
 * @author 한승환
 * @version $Id: PortalSecurityController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/security")
public class PortalSecurityController  extends BaseController {
	
	
	@Autowired
	ACLService aclService;
	
	/**
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/loadSecurity.do")
	public ModelAndView loadSecurity(String className, String resourceName , String operationName, String optionList, String thWidth,
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult	
			){
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/security/loadSecurity");
		
		PortalSecurity security = new PortalSecurity();
		
		User user = (User) getRequestAttribute("ikep.user");
		
		security.setClassName(className);
		security.setResourceName(resourceName);
		security.setOperationName(operationName);
		security.setOwnerId(user.getUserId());
		security.setOwnerName(user.getUserName());
		
		ACLResourcePermission aclResourcePermissionRead = new ACLResourcePermission();
		
		if(!StringUtil.isEmpty(className) && !StringUtil.isEmpty(resourceName) && !StringUtil.isEmpty(operationName))
		{
			// 리소스에 대한 권한 정보를 읽어 온다.
			aclResourcePermissionRead = aclService.getSystemPermission(className,resourceName, operationName);
	
			// 권한에 대한 상세정보를 조회 한다.
			if(aclResourcePermissionRead != null)
			{
				aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);
			}
			else
			{
				aclResourcePermissionRead = new ACLResourcePermission();
				aclResourcePermissionRead.setOpen(1);
			}
		
		}else{
			
			aclResourcePermissionRead.setOpen(1);
		}
			
		security.setAclResourcePermissionRead(aclResourcePermissionRead);
		
		mav.addObject("security",security);
		mav.addObject("optionList",optionList);
		mav.addObject("thWidth",thWidth);
		
		
		return mav;
	}
	
	/**
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readSecurity.do")
	public ModelAndView readSecurity(String className, String resourceName , String operationName, String optionList, String thWidth,
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult	
			){
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/security/readSecurity");
		
		PortalSecurity security = new PortalSecurity();
		
		User user = (User) getRequestAttribute("ikep.user");
		
		security.setClassName(className);
		security.setResourceName(resourceName);
		security.setOperationName(operationName);
		security.setOwnerId(user.getUserId());
		security.setOwnerName(user.getUserName());
		
		ACLResourcePermission aclResourcePermissionRead = new ACLResourcePermission();
		
		if(!StringUtil.isEmpty(className) && !StringUtil.isEmpty(resourceName) && !StringUtil.isEmpty(operationName))
		{
			// 리소스에 대한 권한 정보를 읽어 온다.
			aclResourcePermissionRead = aclService.getSystemPermission(className,resourceName, operationName);
	
			// 권한에 대한 상세정보를 조회 한다.
			if(aclResourcePermissionRead != null)
			{
				aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);
			}
			else
			{
				aclResourcePermissionRead = new ACLResourcePermission();
				aclResourcePermissionRead.setOpen(1);
			}
		
		}else{
			
			aclResourcePermissionRead.setOpen(1);
		}
			
		security.setAclResourcePermissionRead(aclResourcePermissionRead);
		
		mav.addObject("security",security);
		mav.addObject("optionList",optionList);
		mav.addObject("thWidth",thWidth);
		
		
		return mav;
	}
}
