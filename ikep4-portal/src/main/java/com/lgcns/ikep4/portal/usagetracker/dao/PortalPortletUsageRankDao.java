package com.lgcns.ikep4.portal.usagetracker.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalPortletUsageRank;

/**
 * 
 * 포틀릿 통계 로그 DAO
 *
 * @author 임종상
 * @version $Id: PortalPortletUsageRankDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalPortletUsageRankDao extends GenericDao<PortalPortletUsageRank, String> {
	
	/**
	 * 포틀릿 통계 로그 저장
	 * @param portalPortletUsageRank 포틀릿 통계 로그 모델
	 */
	public void createPortletLog(PortalPortletUsageRank portalPortletUsageRank);
	
	/**
	 * 포틀릿 통계 로그 조회
	 * @param portletHistoryId 포틀릿 사용이력 ID
	 * @return PortalPortletUsageRank 포틀릿 통계 로그 정보
	 */
	public PortalPortletUsageRank getPortletLog(String portletHistoryId);
}