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

import com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;
import com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;

/**
 * 퀵 메뉴  Service 구현 클래스
 *
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalQuickMenuServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("PortalQuickMenuService")
public class PortalQuickMenuServiceImpl extends GenericServiceImpl<String, String> implements PortalQuickMenuService {
	
	/**
	 * 퀵 메뉴 dao
	 */
	@Autowired
	private PortalQuickMenuDao portalQuickMenuDao;
	
	/**
	 * 다국어 관리 dao
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
	ACLService aclService;
	
	/**
	 * 포탈 별 전체 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> list(Map<String, String> param) {
		
		List<PortalQuickMenu> list = portalQuickMenuDao.list(param);

		return list;
		
	}
	
	/**
	 * 포탈 별 상단 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> quickMenuList(Map<String, String> param) {
		
		List<PortalQuickMenu> list = portalQuickMenuDao.quickMenuList(param);

		return list;
		
	}
	
	/**
	 * 포탈 별 사용자 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 사용자 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> quickMenuListByUserId(Map<String, String> param) {
		
		List<PortalQuickMenu> list = null;
		
		String portalId = (String) param.get("portalId");
		String userId = (String) param.get("userId");
		
		boolean exist = portalQuickMenuDao.existsByUserId(portalId, userId);
		
		if(exist) {
			list = portalQuickMenuDao.listByUserId(param);
		} else {
			list = portalQuickMenuDao.quickMenuList(param);
		}

		return list;

	}
	
	/**
	 * 개별 퀵 메뉴 정보
	 * @param param Map<String, String> Map(fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드, quickMenuId:퀵 메뉴 아이디)
	 * @return PortalMenu 개별 퀵 메뉴 정보
	 */
	public PortalQuickMenu read(Map<String, String> param) {
		
		PortalQuickMenu portalQuickMenu = portalQuickMenuDao.get((String) param.get("quickMenuId"));
		
		if(portalQuickMenu != null) {
			
			// 리소스에 대한 권한 정보를 읽어 온다.
			ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission("Portal-QuickMenu", portalQuickMenu.getQuickMenuId(), "READ");

			// 권한에 대한 상세정보를 조회 한다.
			aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);

			portalQuickMenu.setAclResourcePermissionRead(aclResourcePermissionRead);
		}

		return portalQuickMenu;
		
	}
	
	/**
	 * 퀵 메뉴 생성
	 * @param portalquickMenu 퀵 메뉴 model
	 * @return String 생성된 퀵 메뉴 아이디
	 */
	public String create(PortalQuickMenu portalQuickMenu) {
		
		String id = portalQuickMenu.getQuickMenuId();
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalQuickMenu.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalQuickMenu.getQuickMenuId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		// 시스템 등록
		portalQuickMenuDao.create(portalQuickMenu);
		
		// Security Description  생성
		portalQuickMenu.getSecurity().setResourceName(portalQuickMenu.getQuickMenuId());
		portalQuickMenu.getSecurity().setResourceDescription("퀵메뉴 권한");
		
		portalSecurityService.createSystemPermission(portalQuickMenu.getSecurity());
		
		return id;
		
	}
	
	/**
	 * 퀵 메뉴 수정
	 * @param portalquickMenu 퀵 메뉴 model
	 */
	public void update(PortalQuickMenu portalQuickMenu) {
		
		portalQuickMenuDao.update(portalQuickMenu);
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
	    //화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
	    List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalQuickMenu.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalQuickMenu.getQuickMenuId());

	    //다국어정보를 저장합니다.
	    i18nMessageService.update(i18nMessageList);
		
	    //리소스명에 게시물 key를 넣어줍니다
	    portalQuickMenu.getSecurity().setResourceName(portalQuickMenu.getQuickMenuId());
		// 게시물명으로 Security Description  생성
	    portalQuickMenu.getSecurity().setResourceDescription("퀵메뉴 권한");

		portalSecurityService.updateSystemPermissionAndResource(portalQuickMenu.getSecurity());
		
	}
	
	/**
	 * 퀵 메뉴 삭제
	 * @param quickMenuId 퀵 메뉴 아이디
	 */
	public void delete(String quickMenuId) {
		
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, quickMenuId);
		
		portalQuickMenuDao.delete(quickMenuId);
		
		// Security Description  삭제
		portalSecurityService.deleteSystemPermission("Portal-QuickMenu", quickMenuId);
		
	}
	
	/**
	 * 퀵 메뉴의 하단 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름) 
	 */
	public void moveNextPortalQuickMenu(int startIndex, int moveIndex, Map<String, String> param) {
		
		String portalId = (String) param.get("portalId");
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		List<PortalQuickMenu> listByParentMenuId = portalQuickMenuDao.listBySortOrder(portalId);
		
		String tempSortOrder = "";
		
		if(listByParentMenuId.size() >= moveIndex) {
			for(int i=startIndex; i<moveIndex+1; i++) {
				PortalQuickMenu portalQuickMenu = (PortalQuickMenu) listByParentMenuId.get(i);
				String sortOrder = portalQuickMenu.getSortOrder();
				
				if(i == startIndex) {			
					PortalQuickMenu tempQuickMenu = (PortalQuickMenu) listByParentMenuId.get(moveIndex);
					
					portalQuickMenu.setSortOrder(tempQuickMenu.getSortOrder());
				} else {
					portalQuickMenu.setSortOrder(tempSortOrder);
				}
				
				tempSortOrder = sortOrder;
				
				portalQuickMenu.setUpdaterId(updaterId);
				portalQuickMenu.setUpdaterName(updaterName);
				
				portalQuickMenuDao.update(portalQuickMenu);
			}
		}
		
	}
	
	/**
	 * 퀵 메뉴의 상단 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param param Map<String, String> Map(portalId:포탈 아이디, updaterId:수정인 아이디, updaterName:수정인 이름) 
	 */
	public void movePrevPortalQuickMenu(int startIndex, int moveIndex, Map<String, String> param) {
		
		String portalId = (String) param.get("portalId");
		String updaterId = (String) param.get("updaterId");
		String updaterName = (String) param.get("updaterName");
		
		List<PortalQuickMenu> listByParentMenuId = portalQuickMenuDao.listBySortOrder(portalId);
		
		String tempSortOrder = "";
		
		if(listByParentMenuId.size() >= startIndex) {
			for(int i=startIndex; i>moveIndex-1; i--) {
				PortalQuickMenu portalQuickMenu = (PortalQuickMenu) listByParentMenuId.get(i);
				String sortOrder = portalQuickMenu.getSortOrder();
				
				if(i == startIndex) {			
					PortalQuickMenu tempQuickMenu = (PortalQuickMenu) listByParentMenuId.get(moveIndex);
					
					portalQuickMenu.setSortOrder(tempQuickMenu.getSortOrder());
				} else {
					portalQuickMenu.setSortOrder(tempSortOrder);
				}
				
				tempSortOrder = sortOrder;
				
				portalQuickMenu.setUpdaterId(updaterId);
				portalQuickMenu.setUpdaterName(updaterName);
				
				portalQuickMenuDao.update(portalQuickMenu);
			}
		}
		
	}
	
	/**
	 * 사용자 별 퀵 메뉴 설정 저장
	 * @param ids 사용자 별 퀵 메뉴로 설정할 퀵 메뉴 아이디
	 * @param userId 사용자 아이디
	 * @param userName 사용자 이름
	 */
	public void setUserQuickMenu(String[] ids, String userId, String userName) {
		
		portalQuickMenuDao.deleteUserQuickMenu(userId);
		
		for(int i=0;i<ids.length;i++) {
			PortalQuickMenu portalQuickMenu = new PortalQuickMenu();
			
			portalQuickMenu.setQuickMenuId(ids[i]);
			portalQuickMenu.setUserId(userId);
			portalQuickMenu.setSortOrder(String.valueOf(i+1));
			portalQuickMenu.setRegisterId(userId);
			portalQuickMenu.setRegisterName(userName);
			portalQuickMenu.setUpdaterId(userId);
			portalQuickMenu.setUpdaterName(userName);
			
			portalQuickMenuDao.createUserQuickMenu(portalQuickMenu);
		}
		
	}
	
}