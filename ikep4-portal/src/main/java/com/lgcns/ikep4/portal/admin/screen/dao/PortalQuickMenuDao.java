package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;

/**
 * 
 * 퀵 메뉴 DAO
 *
 * @author 박철종
 * @version $Id: PortalQuickMenuDao.java 19021 2012-05-31 06:36:11Z malboru80 $
 */
public interface PortalQuickMenuDao extends GenericDao<PortalQuickMenu, String> {
	
	/**
	 * 포탈 별 전체 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 전체 퀵 메뉴 목록
	 */
	List<PortalQuickMenu> list(Map<String, String> param);
	
	/**
	 * 포탈 별 상단 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 상단 퀵 메뉴 목록
	 */
	List<PortalQuickMenu> quickMenuList(Map<String, String> param);
	
	/**
	 * 포탈 별 사용자 퀵 메뉴 목록
	 * @param param Map<String, String> Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 아이디, fieldName:퀵 메뉴의 다국어 관리 field 명, localeCode:사용자의 locale 코드)
	 * @return List<PortalMenu> 포탈 별 사용자 퀵 메뉴 목록
	 */
	List<PortalQuickMenu> listByUserId(Map<String, String> param);
	
	/**
	 * 정렬 순서 별 퀵 메뉴 목록
	 * @param portalId 포탈 아이디
	 * @return List<PortalMenu> 정렬 순서 별 퀵 메뉴 목록
	 */
	List<PortalQuickMenu> listBySortOrder(String portalId);

	/**
	 * 사용자 별 퀵 메뉴 설정 여부
	 * @param portalId 포탈 아이디
	 * @param userId 사용자 아이디
	 * @return boolean
	 */
	boolean existsByUserId(String portalId, String userId);
	
	/**
	 * 퀵 메뉴 삭제
	 * @param quickMenuId 퀵 메뉴 아이디
	 */
	public void delete(String quickMenuId);
	
	/**
	 * 사용자 별 퀵 메뉴 설정 등록
	 * @param object 퀵 메뉴 정보
	 */
	public void createUserQuickMenu(PortalQuickMenu object);
	
	/**
	 * 사용자 별 퀵 메뉴 설정 삭제
	 * @param userId 사용자 아이디
	 */
	public void deleteUserQuickMenu(String userId);
	
	/**
	 * 포탈 별 전체 퀵 메뉴 목록
	 * @param portalId 포탈 ID
	 * @return List<PortalMenu> 포탈 별 전체 퀵 메뉴 목록
	 */
	List<PortalQuickMenu> listQuickMenu(String portalId);
	
}