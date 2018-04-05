package com.lgcns.ikep4.portal.main.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.main.dao.PortalMymenuConfigDao;
import com.lgcns.ikep4.portal.main.model.PortalMymenuConfig;

/**
 * 
 * 포탈 MymenuConfig 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalMymenuConfigDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalMymenuConfigDao")
public class PortalMymenuConfigDaoImpl extends GenericDaoSqlmap<PortalMymenuConfig, String> implements PortalMymenuConfigDao {

	/**
	 * PortalMymenuConfig 조회 
	 * @param userId 유저 ID
	 * @return PortalMymenuConfig 모델객체
	 */
	public PortalMymenuConfig getPortalMymenuConfig(String userId) {
		return (PortalMymenuConfig) sqlSelectForObject("portal.main.portalMymenuConfig.getPortalMymenuConfig", userId);
	}
	
	/**
	 * PortalMymenuConfig openOption 업데이트 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void updateOpenOption(PortalMymenuConfig portalMymenuConfig) {
		sqlUpdate("portal.main.portalMymenuConfig.updateOpenOption", portalMymenuConfig);
	}
	
	/**
	 * PortalMymenuConfig 인서트 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void createPortalMymenuConfig(PortalMymenuConfig portalMymenuConfig) {
		sqlInsert("portal.main.portalMymenuConfig.createPortalMymenuConfig", portalMymenuConfig);
	}
	
	@Deprecated
	public PortalMymenuConfig get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalMymenuConfig object) {
		return null;
	}

	@Deprecated
	public void update(PortalMymenuConfig object) {}
	
	@Deprecated
	public void remove(String id) {}
}