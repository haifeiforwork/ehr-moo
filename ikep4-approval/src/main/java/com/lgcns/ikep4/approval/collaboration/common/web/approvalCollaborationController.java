package com.lgcns.ikep4.approval.collaboration.common.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.service.UserAuthMgntService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 부서간 협업시스템 공통 Controller
 * 
 * @author pjh
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/collaboration")
public class approvalCollaborationController extends BaseController {
	
	/** The UserAuthMgnt service. */
	@Autowired
	private UserAuthMgntService userAuthMgntService;
	
	@RequestMapping(value = "/ajaxCheckPermissionMenu", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> ajaxCreateUserAuthMgnt( UserAuthMgntSearchCondition searchCondition){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isUserAuthMgntAdmin = false;
		try {
			
			isUserAuthMgntAdmin = userAuthMgntService.checkPermission( getSessionUser(), searchCondition);
		}catch(IKEP4AjaxValidationException e) {
			throw e;
		}catch(Exception e) {
			throw new IKEP4AjaxException( "userAuthMgnt", e);
		}
		
		resultMap.put("isUserAuthMgntAdmin", isUserAuthMgntAdmin);
		
		return resultMap;
	}
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User getSessionUser() {
		
		return (User)this.getRequestAttribute("ikep.user");
	}
	
}
