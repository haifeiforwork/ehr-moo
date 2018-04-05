/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.main.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포탈 메인 Service
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalMainService.java 19462 2012-06-22 07:19:41Z malboru80 $
 */
@Transactional
public interface PortalMainService extends GenericService<String, String> {

	/**
	 * 메인 포틀릿 조회 
	 * @param ownerId 소유자 ID
	 * @param localeCode 로케일 코드
	 * @param pageId 페이지 ID
	 * @param user 유저 객체
	 * @return PortalMain 모델객체
	 */
	public PortalMain readPortletMain(String ownerId, String localeCode, String pageId, User user);
}
