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
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;

/**
 * 포탈 관리자 설정
 *
 * @author 한승환
 * @version $Id: PortalSecurityService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalSecurityService extends GenericService<PortalSecurity, String> {
	
	/**
	 *  관리자 설정 등록
	 * 
	 * @param security  권한 설정정보
	 */
	public void createSystemPermission(PortalSecurity security);
	
	/**
	 *  관리자 설정 수정
	 * 
	 * @param security  권한 설정정보
	 */
	public void updateSystemPermissionAndResource(PortalSecurity security);
	
	/**
	 *  관리자 설정 수정
	 * 
	 * @param security  권한 설정정보
	 * @param portalId 포탈 아이디
	 */
	public void updateSystemPermission(PortalSecurity security, String portalId);
	
	/**
	 *  관리자 설정 삭제
	 * 
	 * @param className  클래스 명
	 * @param resourceName  리소스 명 
	 */
	public void deleteSystemPermission(String className, String resourceName);
}
