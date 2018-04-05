/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;


/**
 * 포탈 포틀릿 DAO 구현 클래스
 * 
 * @author 한승환
 * @version $Id: PortalPortletDaoImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Repository("portalPortletDao")
public class PortalPortletDaoImpl extends GenericDaoSqlmap<PortalPortlet, String> implements PortalPortletDao {


	public PortalPortlet readPortalPortlet(String portletId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("portletId", portletId);
		return (PortalPortlet) sqlSelectForObject("portal.admin.screen.portalPortlet.readPortalPortlet", map);
	}
	
	public PortalPortlet readPortalPortlet(String portletId, String userLocaleCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("portletId", portletId);
		map.put("userLocaleCode", userLocaleCode);
		map.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
		return (PortalPortlet) sqlSelectForObject("portal.admin.screen.portalPortlet.readPortalPortlet", map);
	}
	

	public List<PortalPortlet> listPortalPortletByCondition(SearchCondition searchCondition) {
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPortalPortletByCondition", searchCondition);
	}
	
	

	public Integer countPortalPortletByCondition(SearchCondition searchCondition) {
		return (Integer) sqlSelectForObject("portal.admin.screen.portalPortlet.countPortalPortletByCondition", searchCondition);
	}

	public String createPortalPortlet(PortalPortlet object) {
		this.sqlInsert("portal.admin.screen.portalPortlet.createPortalPortlet", object);
		return object.getPortletId();
	}
	

	public int updatePortalPortlet(PortalPortlet object) {
		return  sqlUpdate("portal.admin.screen.portalPortlet.updatePortalPortlet", object);
	}

	public int removePortalPortlet(String portletId) {
		return  sqlDelete("portal.admin.screen.portalPortlet.deletePortalPortlet", portletId);
	}


	public List<PortalPortlet> listPortlet(String systemCode) {
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPortlet", systemCode);
	}
	
	public List<PortalPortlet> listPortletPrivate(String systemCode) {
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPortletPrivate", systemCode);
	}
	

	public List<PortalPortlet> listPortletCommonCheck(String systemCode, String userId, String localeCode, String fieldName) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("systemCode", systemCode);
		map.put("fieldName", fieldName);
		map.put("localeCode", localeCode);
		
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPortletCommonCheck", map);
	}
	

	public List<PortalPortlet> listPageLayoutPortlet(String pageLayoutId, String systemCode, String localeCode, String fieldName) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pageLayoutId", pageLayoutId);
		map.put("systemCode", systemCode);
		map.put("fieldName", fieldName);
		map.put("localeCode", localeCode);
		
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPageLayoutPortlet", map);
	}
	

	public List<PortalPortlet> listActivePortlet(String systemCode, List<PortalPageLayout> pageLayoutList, String userId, String localeCode, String fieldName) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("systemCode", systemCode);
		map.put("pageLayoutList", pageLayoutList);
		map.put("userId", userId);
		map.put("fieldName", fieldName);
		map.put("localeCode", localeCode);
		
		return sqlSelectForList("portal.admin.screen.portalPortlet.listActivePortlet", map);
	}
	
	public List<PortalPortlet> listActiveNomovePortlet(String systemCode, List<PortalPageLayout> pageLayoutList, String userId, String localeCode, String fieldName) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("systemCode", systemCode);
		map.put("pageLayoutList", pageLayoutList);
		map.put("userId", userId);
		map.put("fieldName", fieldName);
		map.put("localeCode", localeCode);
		
		return sqlSelectForList("portal.admin.screen.portalPortlet.listActiveNomovePortlet", map);
	}
	
	public List<PortalPortlet> listActivePortletSetting(String systemCode, List<PortalPageLayout> pageLayoutList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("systemCode", systemCode);
		map.put("pageLayoutList", pageLayoutList);
		
		return sqlSelectForList("portal.admin.screen.portalPortlet.listActivePortletSetting", map);
	}
	

	public List<PortalPortlet> listPageLayoutMovePortlet(String pageLayoutId, String systemCode, String portletConfigId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pageLayoutId", pageLayoutId);
		map.put("systemCode", systemCode);
		map.put("portletConfigId", portletConfigId); 
		
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPageLayoutMovePortlet", map);
	}

	public boolean exists(String id) {
		return false;
	}

	public void update(PortalPortlet object) {
		
	}

	public void remove(String id) {
		
	}

	public PortalPortlet get(String id) {
		return null;
	}

	public String create(PortalPortlet object) {
		return null;
	}

	public List<PortalPortlet> listPortletShareAll(String portalId) {
		return sqlSelectForList("portal.admin.screen.portalPortlet.listPortletShareAll", portalId);
	}
}
