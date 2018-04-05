package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtResTimeConfigDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeConfig;

@Repository
public class UtResTimeConfigDaoImpl extends GenericDaoSqlmap<UtResTimeConfig,String>  implements UtResTimeConfigDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utResTimeConfig.";

	/**
	 * 설정 정보 있는지 체크
	 * @param portalId
	 * @return
	 */
	public boolean existsConfig(String portalId) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"existsConfig", portalId);
		return count > 0;
	}
	
	/**
	 * 설정 정보 등록
	 * @param resTimeUrl
	 * @return
	 */
	public String create(UtResTimeConfig resTimeUrl) {
		sqlInsert(NAMESPACE+"create", resTimeUrl);
		
		return resTimeUrl.getPortalId();
	}
	
	/**
	 * 설정 정보 수정
	 * @param resTimeUrl
	 * @return
	 */
	public void update(UtResTimeConfig resTimeUrl) {
		sqlUpdate(NAMESPACE+"update", resTimeUrl);
	}
	
	/**
	 * 설정 정보 조회
	 * @param resTimeUrl
	 * @return
	 */
	public UtResTimeConfig get(String portalId) {
		return (UtResTimeConfig) sqlSelectForObject(NAMESPACE+"get", portalId);
	}

	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}

	@Deprecated
	public void remove(String arg0) {
		
	}

	
	
}
