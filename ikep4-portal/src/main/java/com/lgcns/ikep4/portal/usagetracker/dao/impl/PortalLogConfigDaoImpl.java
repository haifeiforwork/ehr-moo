package com.lgcns.ikep4.portal.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalLogConfigDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLogConfig;

/**
 * 
 * 로그 config DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalLogConfigDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalLogConfigDao")
public class PortalLogConfigDaoImpl extends GenericDaoSqlmap<PortalLogConfig, String> implements PortalLogConfigDao {

	/**
	 * 로그 설정 정보 목록
	 * @return logConfigList 로그 설정 목록
	 */
	public List<PortalLogConfig> getLogConfig() {
		List<PortalLogConfig> logConfigList = (List<PortalLogConfig>) sqlSelectForList("portal.usagetracker.PortalLogConfig.getLogConfig");
		
		return logConfigList;
	}

	@Deprecated
	public PortalLogConfig get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalLogConfig object) {
		return null;
	}

	@Deprecated
	public void update(PortalLogConfig object) {}

	@Deprecated
	public void remove(String id) {}
}