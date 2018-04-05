package com.lgcns.ikep4.portal.usagetracker.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;

/**
 * 
 * 로그인 통계 로그 Service
 *
 * @author 임종상
 * @version $Id: PortalLoginRankService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalLoginRankService extends GenericService<PortalLoginRank, String> {
	
	/**
	 * 로그인 통계  로그 저장
	 * @param portalLoginRank 로그인 통계 로그 모델 객체
	 * @return usage value
	 */
	public int createLoginLog(PortalLoginRank portalLoginRank);
}
