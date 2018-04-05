/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;

/**
 * 메뉴 DAO 구현 클래스
 * 
 * @author 박철종 (yruyo@cnspartner.com)
 * @version $Id: PortalMenuDaoImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Repository("PortalMenuDao")
public class PortalMenuDaoImpl extends GenericDaoSqlmap<PortalMenu, String> implements PortalMenuDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.admin.screen.portalMenu.";
	
	/**
	 * 포탈 별 전체 메뉴 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 메뉴 목록
	 */
	public List<PortalMenu> list(Map<String, String> param) {
		
		List<PortalMenu> list = (List<PortalMenu>) this.sqlSelectForList(NAMESPACE + "list", param);
		
		return list;
		
	}
	
	/**
	 * 포탈 별 상단 메뉴 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 메뉴 목록
	 */
	public List<PortalMenu> menuList(Map<String, String> param) {
		
		List<PortalMenu> list = (List<PortalMenu>) this.sqlSelectForList(NAMESPACE + "menuList", param);
		
		return list;
		
	}
	
	/**
	 * 상위 메뉴 별 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디)
	 * @return List<PortalMenu> 상위 메뉴 별 목록
	 */
	public List<PortalMenu> listByParentMenuId(Map<String, String> param) {
		
		List<PortalMenu> list = (List<PortalMenu>) this.sqlSelectForList(NAMESPACE + "listByParentMenuId", param);
		
		return list;
		
	}
	
	/**
	 * 메뉴 정보
	 * @param id 메뉴 아이디
	 * @return PortalMenu 메뉴 정보
	 */
	public PortalMenu get(String id) {
		
		PortalMenu portalMenu = (PortalMenu) sqlSelectForObject(NAMESPACE + "get", id);
		
		return portalMenu;
		
	}
	
	/**
	 * 메뉴 생성
	 * @param object 메뉴 정보
	 * @return String 생성된 메뉴 아이디
	 */
	public String create(PortalMenu object) {
		
		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getSystemCode();
		
	}
	
	/**
	 * 메뉴 삭제
	 * @param menuId 메뉴 아이디
	 */
	public void delete(String id) {
		
		this.sqlDelete(NAMESPACE + "delete", id);
		
	}

	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * 메뉴 수정
	 * @param object 메뉴 정보
	 */
	public void update(PortalMenu object) {
			
		this.sqlInsert(NAMESPACE + "update", object); 
		
	}

	@Deprecated
	public void remove(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
	public List<PortalMenu> listMenuAll(Map<String, String> param) {
		List<PortalMenu> list = (List<PortalMenu>) this.sqlSelectForList(NAMESPACE + "listMenuAll", param);
		
		return list;
	}
}