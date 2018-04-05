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
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;

/**
 * 퀵 메뉴 Service
 *
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalQuickMenuService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalQuickMenuService extends GenericService<String, String> {

	/**
	 * 포탈 별 전체 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> list(Map<String, String> param);
	
	/**
	 * 포탈 별 상단 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> quickMenuList(Map<String, String> param);
	
	/**
	 * 포탈 별 사용자 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 사용자 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> quickMenuListByUserId(Map<String, String> param);
	
	/**
	 * 개별 퀵 메뉴 정보
	 * @param param Map<String, String> Map(fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드, quickMenuId:퀵 메뉴 아이디)
	 * @return PortalMenu 개별 퀵 메뉴 정보
	 */
	public PortalQuickMenu read(Map<String, String> param);
	
	/**
	 * 퀵 메뉴 생성
	 * @param portalquickMenu 퀵 메뉴 model
	 * @return String 생성된 퀵 메뉴 아이디
	 */
	public String create(PortalQuickMenu portalquickMenu);
	
	/**
	 * 퀵 메뉴 수정
	 * @param portalquickMenu 퀵 메뉴 model
	 */
	public void update(PortalQuickMenu portalquickMenu);
	
	/**
	 * 퀵 메뉴 삭제
	 * @param quickMenuId 퀵 메뉴 아이디
	 */
	public void delete(String quickMenuId);
	
	/**
	 * 퀵 메뉴의 하단 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름) 
	 */
	public void moveNextPortalQuickMenu(int startIndex, int moveIndex, Map<String, String> param);
	
	/**
	 * 퀵 메뉴의 상단 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름) 
	 */
	public void movePrevPortalQuickMenu(int startIndex, int moveIndex, Map<String, String> param);
	
	/**
	 * 사용자 별 퀵 메뉴 설정 저장
	 * @param ids 사용자 별 퀵 메뉴로 설정할 퀵 메뉴 아이디
	 * @param userId 사용자 아이디
	 * @param userName 사용자 이름
	 */
	public void setUserQuickMenu(String[] ids, String userId, String userName);
	
}