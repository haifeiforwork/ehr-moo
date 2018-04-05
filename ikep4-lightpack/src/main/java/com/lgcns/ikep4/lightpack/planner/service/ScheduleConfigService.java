/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.ScheduleConfig;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 일정관리 서비스
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: ScheduleService.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Transactional
public interface ScheduleConfigService  extends GenericService<Schedule, String> {

	public void updateScheduleConfig(ScheduleConfig config);
	
	public void insertScheduleConfig(ScheduleConfig config);
	
	public ScheduleConfig getScheduleConfig(String userId, String portalId);
	
	public ScheduleConfig getDefaultScheduleConfig(String portalId);
	
	/**
	 * 일정 개인설정이 존재하는지 확인
	 * @param userId
	 * @param portalId
	 * @return 
	 */
	boolean isExistPersonalConfig(String userId, String portalId);
	
	boolean isExistScheduleConfig(String portalId);
	
	public void removeConfig(User useriInfo);

}