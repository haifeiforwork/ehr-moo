/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletUserConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletUserConfig;

/**
 * 포틀릿 사용자 설정 DAO 구현체
 *
 * @author 한승환
 * @version $Id: PortalPortletUserConfigDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalPortletUserConfigDao")
public class PortalPortletUserConfigDaoImpl extends GenericDaoSqlmap<PortalPortletUserConfig, String>  implements PortalPortletUserConfigDao {

	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(PortalPortletUserConfig object) {
		return (String) this.sqlInsert("portal.admin.screen.portalPortletUserConfig.create", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletUserConfigDAO#read(java.util.Map)
	 */
	public boolean exists(Map<String, String> param) {
		String count = (String) sqlSelectForObject("portal.admin.screen.portalPortletUserConfig.count", param);
		return Integer.valueOf(count) > 0;
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletUserConfigDAO#read(java.util.Map)
	 */
	public PortalPortletUserConfig read(Map<String, String> param) {
		return (PortalPortletUserConfig) sqlSelectForObject("portal.admin.screen.portalPortletUserConfig.read", param);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletUserConfigDAO#remove(java.util.Map)
	 */
	public int remove(Map<String, String> param) {
		return  sqlDelete("portal.admin.screen.portalPortletUserConfig.remove", param);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletUserConfigDAO#update(com.lgcns.ikep4.portal.admin.screen.model.PortalPortletUserConfig)
	 */
	public void update(PortalPortletUserConfig object) {
		sqlUpdate("portal.admin.screen.portalPortletUserConfig.update", object);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public PortalPortletUserConfig get(String id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}


}
