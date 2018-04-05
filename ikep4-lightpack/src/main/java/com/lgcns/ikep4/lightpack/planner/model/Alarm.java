/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 일정 알림
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: Alarm.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Alarm extends BaseObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 알람 아이디
	 */
	private String alarmId;
	
	/**
	 * 알람 아이디
	 */
	private String scheduleId;
	
	/**
	 * 알람 종류 ( 0 : 메일,  1 : SMS,  2 : 쪽지) 
	 */
	private String alarmType;
	
	/**
	 * 알람 시간
	 * 단위 : 분 (5, 30, 60, 90)
	 */
	private String alarmTime;

	public Alarm() {}
	
	public Alarm(String alarmId, String scheduleId, String alarmType, String alarmTime) {
		this.alarmId = alarmId;
		this.scheduleId = scheduleId;
		this.alarmType = alarmType;
		this.alarmTime = alarmTime;
	}
	//////////////////////////////////////////////////////////////
	
	/**
	 * @return the alarmId
	 */
	public String getAlarmId() {
		return alarmId;
	}

	/**
	 * @param alarmId the alarmId to set
	 */
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	/**
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return the alarmTime
	 */
	public String getAlarmTime() {
		return alarmTime;
	}

	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
}
