package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;

/**
 * 
 * 디폴트 포틀릿 config Dao
 *
 * @author 임종상
 * @version $Id: PortalDefPortletConfigDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalDefPortletConfigDao extends GenericDao<PortalDefPortletConfig, String> {
	
	/**
	 * 디폴트 포틀릿 페이지 레이아웃별 포틀릿 리스트
	 * @param defaultPageLayoutId 디폴트 페이지 레이아웃 ID
	 * @param userId 유저 ID
	 * @return List<PortalDefPortletConfig> 디폴트 포틀릿 페이지 레이아웃별 포틀릿 목록
	 */
	public List<PortalDefPortletConfig> listDefPageLayoutPortlet(String defaultPageLayoutId, String userId);
	
	/**
	 * 디폴트 포틀릿 리스트 
	 * @param defPageLayoutList 디폴트 페이지 레이아웃 리스트
	 * @return List<PortalDefPortletConfig> 디폴트 포틀릿 config 목록 
	 */
	public List<PortalDefPortletConfig> listDefPortlet(List<PortalDefPageLayout> defPageLayoutList);
	
	/**
	 * 디폴트 포틀릿 리스트 삭제 
	 * @param defPageLayoutList 디폴트 페이지 레이아웃 리스트
	 */
	public void removeDefPortletAll(List<PortalDefPageLayout> defPageLayoutList);
	
	/**
	 * 디폴트 포틀릿 등록 
	 * @param portalDefPortletConfig 디폴트 포틀릿 config 모델
	 */
	public void createDefPortlet(PortalDefPortletConfig portalDefPortletConfig);
	
	/**
	 * 디폴트 포틀릿 삭제
	 * @param portletId 포틀릿 ID
	 */
	public void removeDefPortletConfigAll(String portletId);
}
