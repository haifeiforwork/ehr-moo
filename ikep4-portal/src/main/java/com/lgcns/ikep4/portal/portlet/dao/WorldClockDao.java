package com.lgcns.ikep4.portal.portlet.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.WorldClock;

/**
 * 
 * 세계 시간 DAO
 *
 * @author 
 * @version $Id: WorldClockDao.java 3455 2011-07-21 08:11:44Z dev07 $
 */
public interface WorldClockDao extends GenericDao<WorldClock, String> {

	/**
	 * 세계 시간 설정 정보 조회
	 * @param userId
	 * @return
	 */
	public List<WorldClock> getWorldClock(String userId);
	
	/**
	 * 세계 시간 설정 저장
	 * @param worldClock
	 */
	public void createWorldClock(WorldClock worldClock);
	
	/**
	 * 세계 시간 설정 수정
	 * @param worldClock
	 */
	public void updateWorldClock(WorldClock worldClock);
}