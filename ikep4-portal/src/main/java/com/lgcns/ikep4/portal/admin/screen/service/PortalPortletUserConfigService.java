/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletUserConfig;

/**
 * 포틀릿 사용자 설정 서비스
 *
 * @author 한승환
 * @version $Id: PortalPortletUserConfigService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalPortletUserConfigService extends GenericService<PortalPortletUserConfig, String> {

	
	/**
	 * 사용자 포틀릿 항목갯수 설정값 설정 (등록/수정)
	 * 
	 * @param portletConfigId  포틀릿 설정 아이디
	 * @param propertyValue    설정값
	 */
	public void setListSize(String portletConfigId,int propertyValue);
	
	/**
	 * 사용자 포틀릿 항목갯수 설정값 조회
	 * 
	 * @param portletConfigId  포틀릿 설정 아이디
	 * @return 포틀릿 항목갯수
	 */
	public int readListSize(String portletConfigId);
	
	/**
	 * 포틀릿 설정 등록/수정
	 * 
	 * @param portletConfigId  포틀릿 설정 아이디
	 * @param propertyValue    설정명
	 * @param propertyValue    설정값
	 */
	public void setPortletUserConfig(String portletConfigId,String propertyName,String propertyValue);
	
	/**
	 * 사용자 포틀릿 설정 조회
	 * 
	 * @param portletConfigId  포틀릿 설정 아이디
	 * @param propertyName     설정명
	 * @return 포틀릿 설정 값
	 */
	public String readPortalPortletUserConfig(String portletConfigId,String propertyName);

}
