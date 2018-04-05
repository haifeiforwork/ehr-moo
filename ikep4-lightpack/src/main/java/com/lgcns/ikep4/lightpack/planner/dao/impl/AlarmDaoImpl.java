/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 알람 DAO
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: AlarmDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository(value = "alarmDao")
public class AlarmDaoImpl extends GenericDaoSqlmap<Alarm, String> implements AlarmDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Alarm get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Alarm alarm) {
		return (String) sqlInsert(NAMESPACE + ".insert", alarm);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.AlarmDao#create(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	public void create(List<Alarm> alarms) {
		HashMap<String, List> param = new HashMap<String, List>();
		param.put("items", alarms);
		sqlInsert(NAMESPACE + ".insertList", param);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Alarm alarm) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String scheduleId) {
		sqlDelete(NAMESPACE + ".delete", scheduleId);
	}

	/* (non-Javadoc)s
	 * @see com.lgcns.ikep4.lightpack.planner.dao.AlarmDao#getList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Alarm> getList(String scheduleId) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectList", scheduleId);
	}

	@SuppressWarnings("rawtypes")
	public List getAlarmTargetList(Map<String, Object> params) {
		return sqlSelectForList(NAMESPACE + ".alarmTargetList", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.AlarmDao#updateAlarmSent(java.util.Set)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateAlarmSent(Set alarmIds) {
		String[] params = (String[]) alarmIds.toArray(new String[0]);
		sqlUpdate(NAMESPACE + ".updateAlarmSent", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.AlarmDao#getUserInfoList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserInfoList(String[] userIds) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".getUserInfoList", userIds);
	}

}
