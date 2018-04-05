/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 일정 알람 관리
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: AlarmDao.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public interface AlarmDao extends GenericDao<Alarm, String> {

	String NAMESPACE = "lightpack.planner.dao.Alarm";

	/**일정알림 목록 등록
	 * @param alarms
	 */
	void create(List<Alarm> alarms);

	/** 일정 알람 목록 읽어오기
	 * @param scheduleId
	 * @return
	 */
	List<Alarm> getList(String scheduleId);

	@SuppressWarnings("rawtypes")
	List getAlarmTargetList(Map<String, Object> params);

	/**알람 설정 disable
	 * @param alarmIds
	 */
	@SuppressWarnings("rawtypes")
	void updateAlarmSent(Set alarmIds);

	/**
	 * 사용자 id를 이용하여 사용자 정보 목록 읽기
	 * @param string
	 * @return
	 */
	List<User> getUserInfoList(String[] userIds);


}
