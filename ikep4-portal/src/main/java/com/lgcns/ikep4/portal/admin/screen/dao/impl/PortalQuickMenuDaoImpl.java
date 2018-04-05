package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;

/**
 * 
 * 퀵 메뉴 DAOImpl
 *
 * @author 박철종
 * @version $Id: PortalQuickMenuDaoImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Repository("portalQuickMenuDao")
public class PortalQuickMenuDaoImpl extends GenericDaoSqlmap<PortalQuickMenu, String> implements PortalQuickMenuDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.admin.screen.portalQuickMenu.";
	
	/**
	 * 포탈 별 전체 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> list(Map<String, String> param) {
		
		List<PortalQuickMenu> list = (List<PortalQuickMenu>) this.sqlSelectForList(NAMESPACE + "list", param);
		
		return list;
		
	}
	
	/**
	 * 포탈 별 상단 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> quickMenuList(Map<String, String> param) {
		
		List<PortalQuickMenu> list = (List<PortalQuickMenu>) this.sqlSelectForList(NAMESPACE + "quickMenuList", param);
		
		return list;
		
	}
	
	/**
	 * 포탈 별 사용자 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 사용자 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> listByUserId(Map<String, String> param) {
		
		List<PortalQuickMenu> list = (List<PortalQuickMenu>) this.sqlSelectForList(NAMESPACE + "listByUserId", param);
		
		return list;
		
	}
	
	/**
	 * 정렬 순서 별 퀵 메뉴 목록
	 * @param portalId 포탈 아이디
	 * @return List<PortalMenu> 정렬 순서 별 퀵 메뉴 목록
	 */
	public List<PortalQuickMenu> listBySortOrder(String portalId) {
		
		List<PortalQuickMenu> list = (List<PortalQuickMenu>) this.sqlSelectForList(NAMESPACE + "listBySortOrder", portalId);
		
		return list;
		
	}
	
	/**
	 * 퀵 메뉴 정보
	 * @param id 퀵 메뉴 아이디
	 */
	public PortalQuickMenu get(String id) {
		
		PortalQuickMenu portalQuickMenu = (PortalQuickMenu) sqlSelectForObject(NAMESPACE + "get", id);
		
		return portalQuickMenu;
		
	}

	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * 사용자 별 퀵 메뉴 설정 여부
	 * @param portalId 포탈 아이디
	 * @param userId 사용자 아이디
	 * @return boolean
	 */
	public boolean existsByUserId(String portalId, String userId) {
		
		boolean exist = false;
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", portalId);
		param.put("userId", userId);

		int count = (Integer) sqlSelectForObject(NAMESPACE + "existsByUserId", param);
		
		if(count > 0) {
			exist = true;
		} else {
			exist = false;
		}
		
		return exist;
		
	}
	
	/**
	 * 퀵 메뉴 생성
	 * @param object 퀵 메뉴 정보
	 * @return String 생성된 퀵 메뉴 아이디
	 */
	public String create(PortalQuickMenu object) {

		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getQuickMenuId();
		
	}

	/**
	 * 퀵 메뉴 수정
	 * @param object 퀵 메뉴 정보
	 */
	public void update(PortalQuickMenu object) {

		this.sqlInsert(NAMESPACE + "update", object); 
		
	}
	
	/**
	 * 퀵 메뉴 삭제
	 * @param id 퀵 메뉴 아이디
	 */
	public void delete(String id) {
		
		this.sqlDelete(NAMESPACE + "delete", id);
		
	}

	@Deprecated
	public void remove(String id) {

		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * 사용자 별 퀵 메뉴 설정 등록
	 * @param object 퀵 메뉴 정보
	 */
	public void createUserQuickMenu(PortalQuickMenu object) {
		
		this.sqlInsert(NAMESPACE + "createUserQuickMenu", object);
		
	}
	
	/**
	 * 사용자 별 퀵 메뉴 설정 삭제
	 * @param userId 사용자 아이디
	 */
	public void deleteUserQuickMenu(String userId) {
		
		this.sqlDelete(NAMESPACE + "deleteUserQuickMenu", userId);
		
	}

	public List<PortalQuickMenu> listQuickMenu(String portalId) {
		List<PortalQuickMenu> list = (List<PortalQuickMenu>) this.sqlSelectForList(NAMESPACE + "listQuickMenu", portalId);
		
		return list;
	}

}