package com.lgcns.ikep4.portal.usagetracker.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalPortletUsageRankDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalPortletUsageRank;

/**
 * 
 * 포틀릿 통계 로그 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalPortletUsageRankDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalPortletUsageRankDao")
public class PortalPortletUsageRankDaoImpl extends GenericDaoSqlmap<PortalPortletUsageRank, String> implements PortalPortletUsageRankDao {

	/**
	 * 포틀릿 통계 로그 저장
	 * @param portalPortletUsageRank 포틀릿 통계 로그 모델
	 */
	public void createPortletLog(PortalPortletUsageRank portalPortletUsageRank) {
		sqlInsert("portal.usagetracker.PortalPortletUsageRank.createPortletLog", portalPortletUsageRank);
	}
	
	/**
	 * 포틀릿 통계 로그 조회
	 * @param portletHistoryId 포틀릿 사용이력 ID
	 * @return PortalPortletUsageRank 포틀릿 통계 로그 정보
	 */
	public PortalPortletUsageRank getPortletLog(String portletHistoryId) {
		return (PortalPortletUsageRank) sqlSelectForObject("portal.usagetracker.PortalPortletUsageRank.getPortletLog", portletHistoryId);
	}
	
	@Deprecated
	public PortalPortletUsageRank get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalPortletUsageRank object) {
		return null;
	}

	@Deprecated
	public void update(PortalPortletUsageRank object) {}

	@Deprecated
	public void remove(String id) {}
}
