package com.lgcns.ikep4.portal.main.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.main.model.PortalMymenuConfig;

/**
 * 
 * 포탈 MymenuConfig DAO
 *
 * @author 임종상
 * @version $Id: PortalMymenuConfigDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalMymenuConfigDao extends GenericDao<PortalMymenuConfig, String> {
	
	/**
	 * PortalMymenuConfig 조회 
	 * @param userId 유저 ID
	 * @return PortalMymenuConfig 모델객체
	 */
	public PortalMymenuConfig getPortalMymenuConfig(String userId);
	
	/**
	 * PortalMymenuConfig openOption 업데이트 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void updateOpenOption(PortalMymenuConfig portalMymenuConfig);
	
	/**
	 * PortalMymenuConfig 인서트 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void createPortalMymenuConfig(PortalMymenuConfig portalMymenuConfig);
}