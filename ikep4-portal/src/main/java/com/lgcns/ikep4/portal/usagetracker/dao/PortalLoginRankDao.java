package com.lgcns.ikep4.portal.usagetracker.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;

/**
 * 
 * 로그인 통계 로그 DAO
 *
 * @author 임종상
 * @version $Id: PortalLoginRankDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalLoginRankDao extends GenericDao<PortalLoginRank, String> {
	
	/**
	 * 로그인 통계 로그 등록
	 * @param portalLoginRank 로그인 통계  모델
	 */
	public void createLoginLog(PortalLoginRank portalLoginRank);
	
	/**
	 * 로그인 통계 로그 조회
	 * @param loginHistoryId 로그인 히스토리 ID
	 * @return PortalLoginRank 로그인 통계 로그 정보
	 */
	public PortalLoginRank getLoginLog(String loginHistoryId);
}
