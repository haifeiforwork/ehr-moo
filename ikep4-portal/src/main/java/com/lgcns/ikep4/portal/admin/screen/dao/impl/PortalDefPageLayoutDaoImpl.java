package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;

/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 임종상
 * @version $Id: PortalDefPageLayoutDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalDefPageLayoutDao")
public class PortalDefPageLayoutDaoImpl extends GenericDaoSqlmap<PortalDefPageLayout, String> implements PortalDefPageLayoutDao {

	/**
	 * 디폴트 페이지 레이아웃 리스트 
	 * @param pageId 페이지 ID
	 * @return List<PortalDefPageLayout> 디폴트 페이지 레이아웃 목록
	 */
	public List<PortalDefPageLayout> listDefPageLayout(String pageId) {
		return sqlSelectForList("portal.admin.screen.portalDefPageLayout.listDefPageLayout", pageId);
	}
	
	/**
	 * 전체 디폴트 페이지 레이아웃 삭제
	 * @param pageId 페이지 ID
	 */
	public void removeDefPageLayout(String pageId) {
		sqlDelete("portal.admin.screen.portalDefPageLayout.removeDefPageLayout", pageId);
	}
	
	/**
	 * 디폴트 페이지 레이아웃 등록 
	 * @param portalDefPageLayout 디폴트 페이지 레이아웃 모델
	 */
	public void createDefPageLayout(PortalDefPageLayout portalDefPageLayout) {
		sqlInsert("portal.admin.screen.portalDefPageLayout.createDefPageLayout", portalDefPageLayout);
	}
	
	@Deprecated
	public PortalDefPageLayout get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalDefPageLayout object) {
		return null;
	}

	@Deprecated
	public void update(PortalDefPageLayout object) {}

	@Deprecated
	public void remove(String id) {}
}