package com.lgcns.ikep4.servicepack.usagetracker.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeConfig;

public interface UtResTimeConfigDao extends GenericDao<UtResTimeConfig,String> {

	/**
	 * 설정 정보 있는지 체크
	 * @param portalId
	 * @return
	 */
	public boolean existsConfig(String portalId);
}