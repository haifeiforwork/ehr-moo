package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;



/**
 * 
 * 디폴트 포틀릿 config DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalDefPortletConfigDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalDefPortletConfigDao")
public class PortalDefPortletConfigDaoImpl extends GenericDaoSqlmap<PortalDefPortletConfig, String> implements PortalDefPortletConfigDao {

	/**
	 * 디폴트 포틀릿 페이지 레이아웃별 포틀릿 리스트
	 * @param defaultPageLayoutId 디폴트 페이지 레이아웃 ID
	 * @param userId 유저 ID
	 * @return List<PortalDefPortletConfig> 디폴트 포틀릿 페이지 레이아웃별 포틀릿 목록
	 */
	public List<PortalDefPortletConfig> listDefPageLayoutPortlet(String defaultPageLayoutId, String userId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("defaultPageLayoutId", defaultPageLayoutId);
		map.put("userId", userId);
		
		return sqlSelectForList("portal.admin.screen.portalDefPortletConfig.listDefPageLayoutPortlet", map);
	}
	
	/**
	 * 디폴트 포틀릿 리스트 
	 * @param defPageLayoutList 디폴트 페이지 레이아웃 리스트
	 * @return List<PortalDefPortletConfig> 디폴트 포틀릿 config 목록 
	 */
	public List<PortalDefPortletConfig> listDefPortlet(List<PortalDefPageLayout> defPageLayoutList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("defPageLayoutList", defPageLayoutList);
		
		return sqlSelectForList("portal.admin.screen.portalDefPortletConfig.listDefPortlet", map);
	}
	
	/**
	 * 디폴트 포틀릿 리스트 삭제 
	 * @param defPageLayoutList 디폴트 페이지 레이아웃 리스트
	 */
	public void removeDefPortletAll(List<PortalDefPageLayout> defPageLayoutList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("defPageLayoutList", defPageLayoutList);
		
		sqlDelete("portal.admin.screen.portalDefPortletConfig.removeDefPortletAll", map);
	}
	
	/**
	 * 디폴트 포틀릿 등록 
	 * @param portalDefPortletConfig 디폴트 포틀릿 config 모델
	 */
	public void createDefPortlet(PortalDefPortletConfig portalDefPortletConfig) {
		sqlInsert("portal.admin.screen.portalDefPortletConfig.createDefPortlet", portalDefPortletConfig);
	}
	
	/**
	 * 디폴트 포틀릿 삭제
	 * @param portletId 포틀릿 ID
	 */
	public void removeDefPortletConfigAll(String portletId) {
		sqlDelete("portal.admin.screen.portalDefPortletConfig.removeDefPortletConfigAll", portletId);
	}
	
	@Deprecated
	public PortalDefPortletConfig get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalDefPortletConfig object) {
		return null;
	}

	@Deprecated
	public void update(PortalDefPortletConfig object) {}

	@Deprecated
	public void remove(String id) {}
}