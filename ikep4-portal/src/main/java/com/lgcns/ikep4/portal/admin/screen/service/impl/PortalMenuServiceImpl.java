/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;
import com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 메뉴 관리  Service 구현 클래스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalMenuServiceImpl.java 19272 2012-06-14 07:56:50Z malboru80 $
 */
@Service("PortalMenuService")
public class PortalMenuServiceImpl extends GenericServiceImpl<PortalMenu, String> implements PortalMenuService {
	
	/**
	 * 포탈 메뉴 dao
	 */
	@Autowired
	private PortalMenuDao portalMenuDao;
	
	/**
	 * 다국어 관리 service
	 */
	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * 포탈 관리자 권한 관리 service
	 */
	@Autowired
	private PortalSecurityService portalSecurityService;
	
	/**
	 * 포탈 접근 관리 service
	 */
	@Autowired
	private ACLService aclService;
	
	/**
	 * 포탈 별 전체 메뉴 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 메뉴 목록
	 */
	public List<PortalMenu> list(Map<String, String> param) {
		
		List<PortalMenu> list = portalMenuDao.list(param);
		
		return list;
		
	}
	
	/**
	 * 포탈 별 상단 메뉴 목록
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 메뉴 목록
	 */
	public List<PortalMenu> menuList(Map<String, String> param) {
		
		List<PortalMenu> list = portalMenuDao.menuList(param);
		
		return list;
		
	}
	
	/**
	 * 개별 메뉴 정보
	 * @param param Map<String, String> Map(fieldName:메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드, menuId:메뉴 아이디)
	 * @return PortalMenu 포탈 메뉴 정보
	 */
	public PortalMenu read(Map<String,String> param) {
		
		PortalMenu portalMenu = portalMenuDao.get((String) param.get("menuId"));
		
		if(portalMenu != null) {
			
			// 리소스에 대한 권한 정보를 읽어 온다.
			ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission("Portal-Menu", portalMenu.getMenuId(), "READ");
			
			//권한에 대한 상세정보를 조회 한다.
			aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);
			
			portalMenu.setAclResourcePermissionRead(aclResourcePermissionRead);
		}
		
		return portalMenu;
		
	}
	
	/**
	 * 메뉴 생성
	 * @param portalMenu 메뉴 정보
	 * @return String 메뉴 아이디
	 */
	public String create(PortalMenu portalMenu) {
		
		String id = portalMenu.getMenuId();
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalMenu.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalMenu.getMenuId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		// 시스템 등록
		portalMenuDao.create(portalMenu);
		
		// Security Description  생성
		portalMenu.getSecurity().setResourceName(portalMenu.getMenuId());
		portalMenu.getSecurity().setResourceDescription("메뉴 권한");
		
		portalSecurityService.createSystemPermission(portalMenu.getSecurity());

		return id;
	}
	
	/**
	 * 메뉴 수정
	 * @param portalMenu 메뉴 정보
	 */
	public void update(PortalMenu portalMenu) {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
	    //화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
	    List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalMenu.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalMenu.getMenuId());

	    //다국어정보를 저장합니다.
	    i18nMessageService.update(i18nMessageList);
	    
		portalMenuDao.update(portalMenu);
		
		//리소스명에 게시물 key를 넣어줍니다
		portalMenu.getSecurity().setResourceName(portalMenu.getMenuId());
		// 게시물명으로 Security Description  생성
		portalMenu.getSecurity().setResourceDescription("메뉴 권한");

		portalSecurityService.updateSystemPermissionAndResource(portalMenu.getSecurity());
	}
	
	/**
	 * 메뉴 삭제
	 * @param menuId 메뉴 아이디 
	 */
	public void delete(String menuId) {
		
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, menuId);
		
		// Security Description  삭제
		portalSecurityService.deleteSystemPermission("Portal-Menu", menuId);
		
		portalMenuDao.delete(menuId);
	}
	
	/**
	 * 상위 메뉴의 우측 이동 및 하위 메뉴의 하위 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디)
	 */
	public void moveNextPortalMenu(int startIndex, int moveIndex, Map<String,String> param) {
		
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		List<PortalMenu> listByParentMenuId = portalMenuDao.listByParentMenuId(param);
		
		String tempSortOrder = "";
		
		if(listByParentMenuId.size() >= moveIndex) {
			for(int i=startIndex; i<moveIndex+1; i++) {
				PortalMenu portalMenu = (PortalMenu) listByParentMenuId.get(i);
				String sortOrder = portalMenu.getSortOrder();
				
				if(i == startIndex) {			
					PortalMenu tempMenu = (PortalMenu) listByParentMenuId.get(moveIndex);
					
					portalMenu.setSortOrder(tempMenu.getSortOrder());
				} else {
					portalMenu.setSortOrder(tempSortOrder);
				}
				
				tempSortOrder = sortOrder;
				
				portalMenu.setUpdaterId(updaterId);
				portalMenu.setUpdaterName(updaterName);
				
				portalMenuDao.update(portalMenu);
			}
		}
	}
	
	/**
	 * 상위 메뉴의 좌측 이동 및 하위 메뉴의 상위 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디)
	 */
	public void movePrevPortalMenu(int startIndex, int moveIndex, Map<String,String> param) {
		
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		List<PortalMenu> listByParentMenuId = portalMenuDao.listByParentMenuId(param);
		
		String tempSortOrder = "";
		
		if(listByParentMenuId.size() >= startIndex) {
			for(int i=startIndex; i>moveIndex-1; i--) {
				PortalMenu portalMenu = (PortalMenu) listByParentMenuId.get(i);
				String sortOrder = portalMenu.getSortOrder();
				
				if(i == startIndex) {			
					PortalMenu tempMenu = (PortalMenu) listByParentMenuId.get(moveIndex);
					
					portalMenu.setSortOrder(tempMenu.getSortOrder());
				} else {
					portalMenu.setSortOrder(tempSortOrder);
				}
				
				tempSortOrder = sortOrder;
				
				portalMenu.setUpdaterId(updaterId);
				portalMenu.setUpdaterName(updaterName);
				
				portalMenuDao.update(portalMenu);
			}
		}
		
	}
	
	/**
	 * 하위 메뉴의 다른 상위메뉴로의 이동
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(systemName:시스템 이름, portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:상위메뉴인 경우는 공백값/하위메뉴인 경우는 상위 메뉴 아이디, menuId:이동하고자 하는 메뉴 아이디)
	 */
	public void moveOtherPortalMenu(int moveIndex, Map<String,String> param) {
		
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		String parentMenuId = (String) param.get("parentMenuId");
		String menuId = (String) param.get("menuId");
		
		List<PortalMenu> listByParentMenuId = portalMenuDao.listByParentMenuId(param);
		PortalMenu portalMenu = portalMenuDao.get(menuId);
		
		//String tempSortOrder = "";
		String menuSortOrder = portalMenu.getSortOrder();
		int tempIndex = -1;
		
		for(int i=0; i<listByParentMenuId.size(); i++) {
			PortalMenu tempMenu = (PortalMenu) listByParentMenuId.get(i);
			String sortOrder = tempMenu.getSortOrder();
			
			if(menuSortOrder.compareTo(sortOrder) < 0) {
				tempIndex = i;
				
				break;
			}
		}
				
		if(moveIndex > tempIndex && tempIndex != -1) {
			portalMenu = moveDndCase1(moveIndex, tempIndex, param, listByParentMenuId, portalMenu);
		} else if(moveIndex > tempIndex && tempIndex == -1) {
			portalMenu = moveDndCase2(moveIndex, param, listByParentMenuId, portalMenu);
		} else if(moveIndex < tempIndex && tempIndex != -1) {
			portalMenu = moveDndCase3(moveIndex, tempIndex, param, listByParentMenuId, portalMenu);
		}
		
		portalMenu.setParentMenuId(parentMenuId);
		portalMenu.setUpdaterId(updaterId);
		portalMenu.setUpdaterName(updaterName);
		
		portalMenuDao.update(portalMenu);
	}
	
	/**
	 * 이동하고자 하는 위치가 위치 정보 저장 전 정렬 순서로 따질때 위치 보다 뒤 일때사용되는 재정렬 method
	 * @param moveIndex 이동 후 위치
	 * @param tempIndex 이동 후 정렬 정보 저장하기 전 순서 정렬 시 위치
	 * @param param Map(updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:이동 후 속할 상위 메뉴 아이디)
	 * @param listByParentMenuId 이동 할 노드의 하위 메뉴 리스트
	 * @param portalMenu 이동 할 메뉴 정보
	 * @return PortalMenu 이동 할 메뉴 정보
	 */
	private PortalMenu moveDndCase1(int moveIndex, int tempIndex, Map<String, String> param, List<PortalMenu> listByParentMenuId, PortalMenu portalMenu) {
		
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		String tempSortOrder = "";
		String menuSortOrder = portalMenu.getSortOrder();
		
		for(int i=tempIndex; i<moveIndex; i++) {
			PortalMenu menu = (PortalMenu) listByParentMenuId.get(i);
			String sortOrder = menu.getSortOrder();
			
			if(i == tempIndex) {			
				menu.setSortOrder(menuSortOrder);
			} else {
				menu.setSortOrder(tempSortOrder);
			}
			
			tempSortOrder = sortOrder;
			
			menu.setUpdaterId(updaterId);
			menu.setUpdaterName(updaterName);
			
			portalMenuDao.update(menu);
		}
		
		portalMenu.setSortOrder(tempSortOrder);
		
		return portalMenu;
		
	}
	
	/**
	 * 이동후 위치 정보 저장 전 이동하고자 하는 메뉴 보다 정렬 순서 값이 큰 하위 메뉴가 없는 경우 재정렬 method
	 * @param moveIndex 이동 후 위치
	 * @param tempIndex 이동 후 정렬 정보 저장하기 전 순서 정렬 시 위치
	 * @param param Map(updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:이동 후 속할 상위 메뉴 아이디)
	 * @param listByParentMenuId 이동 할 노드의 하위 메뉴 리스트
	 * @param portalMenu 이동 할 메뉴 정보
	 * @return PortalMenu 이동 할 메뉴 정보
	 */
	private PortalMenu moveDndCase2(int moveIndex, Map<String, String> param, List<PortalMenu> listByParentMenuId, PortalMenu portalMenu) {
		
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		String tempSortOrder = "";
		String menuSortOrder = portalMenu.getSortOrder();
		
		for(int i=listByParentMenuId.size()-1; i>moveIndex-1; i--) {
			PortalMenu menu = (PortalMenu) listByParentMenuId.get(i);
			String sortOrder = menu.getSortOrder();
			
			if(i == listByParentMenuId.size()-1) {			
				menu.setSortOrder(menuSortOrder);
			} else {
				menu.setSortOrder(tempSortOrder);
			}
			
			tempSortOrder = sortOrder;
			
			menu.setUpdaterId(updaterId);
			menu.setUpdaterName(updaterName);
			
			portalMenuDao.update(menu);
		}
		
		if(!StringUtil.isEmpty(tempSortOrder)) {
			portalMenu.setSortOrder(tempSortOrder);
		}
		
		return portalMenu;
		
	}
	
	/**
	 * 이동하고자 하는 위치가 위치 정보 저장 전 정렬 순서로 따질때 위치 보다 먼저 일때사용되는 재정렬 method
	 * @param moveIndex 이동 후 위치
	 * @param tempIndex 이동 후 정렬 정보 저장하기 전 순서 정렬 시 위치
	 * @param param Map(updaterId:수정인 아이디, updaterName:수정인 이름, parentMenuId:이동 후 속할 상위 메뉴 아이디)
	 * @param listByParentMenuId 이동 할 노드의 하위 메뉴 리스트
	 * @param portalMenu 이동 할 메뉴 정보
	 * @return PortalMenu 이동 할 메뉴 정보
	 */
	private PortalMenu moveDndCase3(int moveIndex, int tempIndex, Map<String, String> param, List<PortalMenu> listByParentMenuId, PortalMenu portalMenu) {
		
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		String tempSortOrder = "";
		String menuSortOrder = portalMenu.getSortOrder();
		
		for(int i=tempIndex-1; i>moveIndex-1; i--) {
			PortalMenu menu = (PortalMenu) listByParentMenuId.get(i);
			String sortOrder = menu.getSortOrder();
			
			if(i == tempIndex-1) {			
				menu.setSortOrder(menuSortOrder);
			} else {
				menu.setSortOrder(tempSortOrder);
			}
			
			tempSortOrder = sortOrder;
			
			menu.setUpdaterId(updaterId);
			menu.setUpdaterName(updaterName);
			
			portalMenuDao.update(menu);
		}
		
		portalMenu.setSortOrder(tempSortOrder);
		
		return portalMenu;
		
	}
	
}