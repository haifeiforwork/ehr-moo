package com.lgcns.ikep4.portal.usagetracker.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.usagetracker.model.PortalPortletUsageRank;

/**
 * 
 * 포틀릿 통계 로그 Service
 *
 * @author 임종상
 * @version $Id: PortalPortletUsageRankService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalPortletUsageRankService extends GenericService<PortalPortletUsageRank, String> {
	
	/**
	 * 포틀릿 통계 로그 저장 
	 * @param portalPortletUsageRank 포틀릿 통계 로그 모델 객체
	 * @return usage value
	 */
	public int createPortletLog(PortalPortletUsageRank portalPortletUsageRank);
}