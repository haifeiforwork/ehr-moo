package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.WorldClockDao;
import com.lgcns.ikep4.portal.portlet.model.WorldClock;

/**
 * 
 * 세계 시간 DAO 구현 객체
 *
 * @author 
 * @version $Id: WorldClockDaoImpl.java 3455 2011-07-21 08:11:44Z dev07 $
 */
@Repository("worldClockDao")
public class WorldClockDaoImpl extends GenericDaoSqlmap<WorldClock, String> implements WorldClockDao {

	/**
	 * 세계 시간 설정 정보 조회
	 * @param userId
	 * @return
	 */
	public List<WorldClock> getWorldClock(String userId) {		
		
		return  sqlSelectForList("portal.portlet.worldClock.getWorldClock", userId);
	}
		
	/**
	 * 세계 시간 설정 저장
	 * @param worldClock
	 */
	public void createWorldClock(WorldClock worldClock) {
		sqlInsert("portal.portlet.worldClock.createWorldClock", worldClock);
	}

	/**
	 * 세계 시간 설정 수정
	 * @param worldClock
	 */
	public void updateWorldClock(WorldClock worldClock) {
		sqlUpdate("portal.portlet.worldClock.updateWorldClock", worldClock);
	}
	
	@Deprecated
	public WorldClock get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(WorldClock object) {
		return null;
	}

	@Deprecated
	public void update(WorldClock object) {}

	@Deprecated
	public void remove(String id) {}
}