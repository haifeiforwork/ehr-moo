package com.lgcns.ikep4.portal.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLogConfig;

/**
 * 
 * 로그 config DAO
 *
 * @author 임종상
 * @version $Id: PortalLogConfigDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalLogConfigDao extends GenericDao<PortalLogConfig, String> {
	
	/**
	 * 로그 설정 정보 목록
	 * @return logConfigList 로그 설정 목록
	 */
	public List<PortalLogConfig> getLogConfig();
}