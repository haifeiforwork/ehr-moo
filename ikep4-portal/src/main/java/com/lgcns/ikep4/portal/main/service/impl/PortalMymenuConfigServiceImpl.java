package com.lgcns.ikep4.portal.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.main.dao.PortalMymenuConfigDao;
import com.lgcns.ikep4.portal.main.model.PortalMymenuConfig;
import com.lgcns.ikep4.portal.main.service.PortalMymenuConfigService;

/**
 * 
 * 포탈 mymenuConfig Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalMymenuConfigServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalMymenuConfigService")
public class PortalMymenuConfigServiceImpl extends GenericServiceImpl<PortalMymenuConfig, String> implements PortalMymenuConfigService {

	@Autowired
	private PortalMymenuConfigDao portalMymenuConfigDao;
	
	/**
	 * PortalMymenuConfig openOption 업데이트 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void updateOpenOption(PortalMymenuConfig portalMymenuConfig) {
		portalMymenuConfigDao.updateOpenOption(portalMymenuConfig);
	}

	/**
	 * PortalMymenuConfig 조회 
	 * @param userId 유저 ID
	 * @return PortalMymenuConfig 모델객체
	 */
	public PortalMymenuConfig readPortalMymenuConfig(String userId) {
		return portalMymenuConfigDao.getPortalMymenuConfig(userId);
	}

	/**
	 * PortalMymenuConfig 저장
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void createPortalMymenuConfig(PortalMymenuConfig portalMymenuConfig) {
		portalMymenuConfigDao.createPortalMymenuConfig(portalMymenuConfig);
	}
	
}
