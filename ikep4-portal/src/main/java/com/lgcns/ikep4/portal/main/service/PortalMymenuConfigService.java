package com.lgcns.ikep4.portal.main.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.main.model.PortalMymenuConfig;

/**
 * 
 * 포탈 mymenuConfig Service
 *
 * @author 임종상
 * @version $Id: PortalMymenuConfigService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalMymenuConfigService extends GenericService<PortalMymenuConfig, String> {
	
	/**
	 * PortalMymenuConfig 조회 
	 * @param userId 유저 ID
	 * @return PortalMymenuConfig 모델객체
	 */
	public PortalMymenuConfig readPortalMymenuConfig(String userId);
	
	/**
	 * PortalMymenuConfig openOption 업데이트 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void updateOpenOption(PortalMymenuConfig portalMymenuConfig);
	
	/**
	 * PortalMymenuConfig 저장
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 */
	public void createPortalMymenuConfig(PortalMymenuConfig portalMymenuConfig);
}
