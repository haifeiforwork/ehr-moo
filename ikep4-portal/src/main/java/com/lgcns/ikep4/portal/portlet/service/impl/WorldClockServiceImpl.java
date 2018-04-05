package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.WorldClockDao;
import com.lgcns.ikep4.portal.portlet.model.WorldClock;
import com.lgcns.ikep4.portal.portlet.service.WorldClockService;

/**
 * 
 * 세계시간 Service 구현 객체
 *
 * @author 
 * @version $Id: WorldClockServiceImpl.java 3455 2011-07-21 08:11:44Z dev07 $
 */
@Service("worldClockService")
public class WorldClockServiceImpl extends GenericServiceImpl<WorldClock, String> implements WorldClockService {

	@Autowired
	private WorldClockDao worldClockDao;
	
	/**
	 * 세계 시간 설정 정보 조회
	 * @param userId
	 * @return
	 */
	public List<WorldClock> readWorldClock(String userId) {
		return worldClockDao.getWorldClock(userId);
	}
	
	/**
	 * 세계 시간 설정 저장
	 * @param worldClock
	 */
	public void createWorldClock(WorldClock worldClock) {
		worldClockDao.createWorldClock(worldClock);
	}
	
	/**
	 * 세계 시간 설정 수정
	 * @param worldClock
	 */
	public void updateWorldClock(WorldClock worldClock) {
		worldClockDao.updateWorldClock(worldClock);
	}
}