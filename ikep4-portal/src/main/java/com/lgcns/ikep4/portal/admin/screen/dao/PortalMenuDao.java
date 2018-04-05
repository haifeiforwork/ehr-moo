/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;

/**
 * 메뉴 DAO 정의
 *
 * @author 박철종 (yruyo@cnspartner.com)
 * @version $Id: PortalMenuDao.java 19021 2012-05-31 06:36:11Z malboru80 $
 */
public interface PortalMenuDao extends GenericDao<PortalMenu, String> {
	
	/**
	 * 포탈 별 전체 메뉴 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 메뉴 목록
	 */
	public List<PortalMenu> list(Map<String, String> param);
	
	/**
	 * 포탈 별 상단 메뉴 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 메뉴 목록
	 */
	public List<PortalMenu> menuList(Map<String, String> param);
	
	/**
	 * 상위 메뉴 별 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디)
	 * @return List<PortalMenu> 상위 메뉴 별 목록
	 */
	public List<PortalMenu> listByParentMenuId(Map<String, String> param);
	
	/**
	 * 메뉴 삭제
	 * @param menuId 메뉴 아이디
	 */
	public void delete(String menuId);
	
	public List<PortalMenu> listMenuAll(Map<String, String> param);
}