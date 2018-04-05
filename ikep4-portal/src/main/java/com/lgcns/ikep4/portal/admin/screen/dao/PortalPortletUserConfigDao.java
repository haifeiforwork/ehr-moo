/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletUserConfig;

/**
 * TODO 포틀릿 사용자 설정 DAO
 *
 * @author 한승환
 * @version $Id: PortalPortletUserConfigDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalPortletUserConfigDao  extends GenericDao<PortalPortletUserConfig, String> {

	/**
	 * 포틀릿 사용자 설정 조회
	 * 
	 * @param param (portletConfigId, propertyName)
	 * @return 포틀릿 목록
	 */
	public PortalPortletUserConfig read(Map<String,String> param);
	
	/**
	 * 포틀릿 사용자 설정 삭제
	 * 
	 * @param param (portletConfigId, propertyName)
	 * @return 포틀릿 목록
	 */
	public int remove(Map<String,String> param);
	
	/**
	 * 포틀릿 사용자 설정 등록
	 * 
	 * @param object 등록정보
	 */
	public String create(PortalPortletUserConfig object);
	
	/**
	 * 포틀릿 사용자 설정 수정
	 * 
	 * @param object 등록정보
	 */
	public void update(PortalPortletUserConfig object);
	
	/**
	 * 포틀릿 사용자 설정 조회
	 * 
	 * @param param (portletConfigId, propertyName)
	 * @return 포틀릿 목록
	 */
	public boolean exists(Map<String, String> param);
}
