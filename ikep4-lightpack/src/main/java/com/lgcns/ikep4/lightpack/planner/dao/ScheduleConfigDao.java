/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.ScheduleConfig;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 일정 처리 Dao interface
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleDao.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
public interface ScheduleConfigDao extends GenericDao<Schedule, String> {
	
	String NAMESPACE = "lightpack.planner.dao.ScheduleConfig";
	
	/**
	 * 일정 기본 설정 정보 등록
	 * @param ScheduleConfig
	 */
	void insertScheduleConfig(ScheduleConfig config);
	
	/**
	 * 일정 기본 설정 정보 업데이트
	 * @param ScheduleConfig
	 */
	void updateScheduleConfig(ScheduleConfig config);
	
	/**
	 * 일정 기본 설정 정보 읽기
	 * @param userId
	 * @param portalId
	 * @return
	 */
	ScheduleConfig getScheduleConfig(String userId, String portalId);
	
	/**
	 * 일정 개인설정이 존재하는지 확인
	 * @param userId
	 * @param portalId
	 * @return 
	 */
	boolean isExistPersonalConfig(String userId, String portalId);
	
	/**
	 * 개인 설정 삭제
	 * @param userInfo
	 */
	void remove(User userInfo);
}
