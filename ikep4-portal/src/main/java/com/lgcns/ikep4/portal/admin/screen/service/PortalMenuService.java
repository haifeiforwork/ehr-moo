/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;

/**
 * 포탈 메뉴 Service
 * 
 * @author 박철종 (yruyo@cnspartner.com)
 * @version $Id: PortalMenuService.java 19249 2012-06-13 06:42:29Z malboru80 $
 */
@Transactional
public interface PortalMenuService extends GenericService<PortalMenu, String> {
	
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
	 * 개별 메뉴 정보
	 * @param param Map<String, String> Map(fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드, menuId:메뉴 아이디)
	 * @return PortalMenu 포탈 메뉴 정보
	 */
	public PortalMenu read(Map<String,String> param);
	
	/**
	 * 메뉴 생성
	 * @param portalMenu 메뉴 정보
	 * @return String 메뉴 아이디
	 */
	public String create(PortalMenu portalMenu);
	
	/**
	 * 메뉴 수정
	 * @param portalMenu 메뉴 정보
	 */
	public void update(PortalMenu portalMenu);
	
	/**
	 * 메뉴 삭제
	 * @param menuId 메뉴 아이디 
	 */
	public void delete(String menuId);
	
	/**
	 * 상위 메뉴의 우측 이동 및 하위 메뉴의 하위 이동
	 * @param startIndex 이동전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디)
	 */
	public void moveNextPortalMenu(int startIndex, int moveIndex, Map<String,String> param);
	
	/**
	 * 상위 메뉴의 좌측 이동 및 하위 메뉴의 상위 이동
	 * @param startIndex 이동전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디)
	 */
	public void movePrevPortalMenu(int startIndex, int moveIndex, Map<String,String> param);
	
	/**
	 * 하위 메뉴의 다른 상위메뉴로의 이동
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디, menuId:이동하고자 하는 메뉴 아이디)
	 */
	public void moveOtherPortalMenu(int moveIndex, Map<String,String> param);
	
}