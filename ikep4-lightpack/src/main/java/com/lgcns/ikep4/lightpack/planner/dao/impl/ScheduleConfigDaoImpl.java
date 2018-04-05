/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleConfigDao;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.ScheduleConfig;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 일정관리 Dao class
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleDaoImpl.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Repository(value = "scheduleConfigDao")
public class ScheduleConfigDaoImpl extends GenericDaoSqlmap<Schedule, String> implements ScheduleConfigDao {

	public void insertScheduleConfig(ScheduleConfig config) {
		sqlInsert(NAMESPACE + ".insertConfig", config);
	}
	
	public void updateScheduleConfig(ScheduleConfig config) {
		sqlUpdate(NAMESPACE + ".updateConfig", config);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ScheduleConfig getScheduleConfig(String userId, String portalId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("portalId", portalId);
		return (ScheduleConfig) sqlSelectForObject(NAMESPACE + ".getConfig", param);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#isExistName(java.lang.String, java.lang.String)
	 */
	public boolean isExistPersonalConfig(String userId, String portalId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("portalId", portalId);
		int mcnt = (Integer) sqlSelectForObject(NAMESPACE + ".isExistPersonalConfig", param);
		return mcnt == 1 ? true : false;
	}
	
	public void remove(User userInfo) {
		this.sqlDelete(NAMESPACE + ".removeConfig", userInfo);
	}

	public String create(Schedule arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Schedule get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Schedule arg0) {
		// TODO Auto-generated method stub
		
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}
}
