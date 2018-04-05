/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleConfigDao;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.ScheduleConfig;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleConfigService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 일정관리 서비스 구현
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleServiceImpl.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Service("scheduleConfigService")
public class ScheduleConfigServiceImpl extends GenericServiceImpl<Schedule, String> implements ScheduleConfigService {
	
	@Autowired
	private ScheduleConfigDao scheduleConfigDao;
	
	public void updateScheduleConfig(ScheduleConfig config) {
		scheduleConfigDao.updateScheduleConfig(config);
	}
	
	public void insertScheduleConfig(ScheduleConfig config) {
		scheduleConfigDao.insertScheduleConfig(config);
	}
	
	public ScheduleConfig getScheduleConfig(String userId, String portalId) {
		ScheduleConfig config;
		
		ScheduleConfig defaultConfig = getDefaultScheduleConfig(portalId);
		if(isExistPersonalConfig(userId, portalId)){
			config = scheduleConfigDao.getScheduleConfig(userId, portalId);
			
			config.setUnit(defaultConfig.getUnit());
			config.setViewTodo(defaultConfig.getViewTodo());
			config.setStartHour(defaultConfig.getStartHour());
			config.setEndHour(defaultConfig.getEndHour());
			
		} else {
			config = defaultConfig;
		}
		
		return config;
	}
	
	public ScheduleConfig getDefaultScheduleConfig(String portalId) {
		return scheduleConfigDao.getScheduleConfig(portalId, portalId);
	}
	
	public boolean isExistPersonalConfig(String userId, String portalId) {
		return scheduleConfigDao.isExistPersonalConfig(userId, portalId);
	}
	
	public boolean isExistScheduleConfig(String portalId) {
		return scheduleConfigDao.isExistPersonalConfig(portalId, portalId);
	}

	public void removeConfig(User userInfo) {
		scheduleConfigDao.remove(userInfo);
	}

}
