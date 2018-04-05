package com.lgcns.ikep4.portal.usagetracker.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalLoginRankDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;

/**
 * 
 * 로그인 통계 로그 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalLoginRankDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portalLoginRankDao")
public class PortalLoginRankDaoImpl extends GenericDaoSqlmap<PortalLoginRank, String> implements PortalLoginRankDao {

	/**
	 * 로그인 통계 로그 등록
	 * @param portalLoginRank 로그인 통계  모델
	 */
	public void createLoginLog(PortalLoginRank portalLoginRank) {
		sqlInsert("portal.usagetracker.PortalLoginRank.createLoginLog", portalLoginRank); 
	}
	
	/**
	 * 로그인 통계 로그 조회
	 * @param loginHistoryId 로그인 히스토리 ID
	 * @return PortalLoginRank 로그인 통계 로그 정보
	 */
	public PortalLoginRank getLoginLog(String loginHistoryId) {
		return (PortalLoginRank) sqlSelectForObject("portal.usagetracker.PortalLoginRank.getLoginLog", loginHistoryId);
	}
	
	@Deprecated
	public PortalLoginRank get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalLoginRank object) {
		return null;
	}

	@Deprecated
	public void update(PortalLoginRank object) {}

	@Deprecated
	public void remove(String id) {}
}