/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Assessment Management 에서 사용하는 Custom Controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: CustomController.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class CustomController extends BaseController {

	/**
	 * 세션에 저장된 유저 정보 Attribute ID
	 */
	private static final String SESSION_ATTRIBUTE_USER = "ikep.user";

	/**
	 * 세션에 저장된 포털 정보 Attribute ID
	 */
	private static final String SESSION_ATTRIBUTE_PORTAL = "ikep.portal";

	/**
	 * 관리자 권한 리소스명칭
	 */
	protected static final String SYSTEM_NAME = "AssessmentMng";

	/**
	 * 사용자 정보
	 * @return User
	 */
	protected User getSessionUser() {
		return (User)this.getRequestAttribute(SESSION_ATTRIBUTE_USER);
	}

	/**
	 * 포털 정보
	 * @return Portal
	 */
	protected Portal getSessionPortal() {
		return (Portal)this.getRequestAttribute(SESSION_ATTRIBUTE_PORTAL);
	}

	@Autowired
	private ACLService aclService;

	/**
	 * 관리자 권한 (모듈 Assessment Management의 관리자 권한)
	 * @param userId
	 * @return boolean
	 */
	protected boolean isAdmin(String userId) {
		return aclService.isSystemAdmin(SYSTEM_NAME, userId);
	}

	/**
	 * 권한체크<br/>
	 * 관리자 권한이 없으면 예외발생 (IKEP4AjaxException)
	 */
	protected void checkAdmin() {
		if (!isAdmin(getSessionUser().getUserId())) {
			throw new IKEP4AjaxException("Access Denied", new Exception());
		}
	}

	/**
	 * 권한체크<br/>
	 * 관리자 권한이 없으면 예외발생 (IKEP4AjaxException)
	 * @param userId
	 */
	protected void checkAdmin(String userId) {
		if (!isAdmin(userId)) {
			throw new IKEP4AjaxException("Access Denied", new Exception());
		}
	}

	/**
	 * 리소스번들에서 메시지 값 조회 <br/>
	 * 현재 세션의 Locale 을 기준으로 조회
	 * @param code - 리소스번들의 코드값
	 * @return 코드값에 해당하는 리소스 값 
	 */
	protected String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}

	/**
	 * 리소스번들에서 메시지 값 조회 <br/>
	 * 현재 세션의 Locale 을 기준으로 조회
	 * @param code - 리소스번들의 코드값
	 * @param args - 파라미터 배열
	 * @return 코드값에 해당하는 리소스 값
	 */
	protected String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}

	/**
	 * 로케일 상태
	 * @return boolean
	 */
	protected boolean isSameLocale() {
		return getSessionPortal().getDefaultLocaleCode().equals(getSessionUser().getLocaleCode());
	}

}
