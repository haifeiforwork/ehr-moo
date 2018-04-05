package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.WorldClock;

/**
 * 
 * 세계시간 Service
 *
 * @author 
 * @version $Id: WorldClockService.java 3455 2011-07-21 08:11:44Z dev07 $
 */
@Transactional
public interface WorldClockService extends GenericService<WorldClock, String> {
	
	/**
	 * 세계 시간 설정 정보 조회
	 * @param userId
	 * @return
	 */
	public List<WorldClock> readWorldClock(String userId);
	
	
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