package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;

/**
 * 
 * 디폴트 페이지 레이아웃 DAO
 *
 * @author 임종상
 * @version $Id: PortalDefPageLayoutDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalDefPageLayoutDao extends GenericDao<PortalDefPageLayout, String> {
	
	/**
	 * 디폴트 페이지 레이아웃 리스트 
	 * @param pageId 페이지 ID
	 * @return List<PortalDefPageLayout> 디폴트 페이지 레이아웃 목록
	 */
	public List<PortalDefPageLayout> listDefPageLayout(String pageId);
	
	/**
	 * 전체 디폴트 페이지 레이아웃 삭제
	 * @param pageId 페이지 ID
	 */
	public void removeDefPageLayout(String pageId);
	
	/**
	 * 디폴트 페이지 레이아웃 등록 
	 * @param portalDefPageLayout 디폴트 페이지 레이아웃 모델
	 */
	public void createDefPageLayout(PortalDefPageLayout portalDefPageLayout);
}
