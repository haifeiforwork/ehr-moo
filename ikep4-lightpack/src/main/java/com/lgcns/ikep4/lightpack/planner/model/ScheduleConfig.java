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
public class ScheduleConfig extends BaseObject {

	private static final long serialVersionUID = 4681017614415148814L;

	/**
	 * Portal 아이디
	 */
	private String portalId;
	
	/**
	 * USER 아이디
	 */
	private String userId;
	
	/**
	 * 일정 설정 단위(5분, 10분, 15분, 30분)
	 */
	private int unit = 30;
	
	/**
	 * 일정 기본 보기(1:일간, 2:주간, 3:월간, 4:목록)
	 */
	private int defaultView = 1;
	
	/**
	 * 공휴일 표시할 나라 코드 (KOR:한국,JPN:일본,CHN:중국,USA:미국,TWN:대만) 사용:KOR,JPN,CHN
	 */
	private String holiday;
	
	/**
	 * To-Do 목록 표시 (0:표시안함, 1:표시)
	 */
	private int viewTodo = 0;
	
	/**
	 * 일정 등록 가능 시작 시간
	 */
	private int startHour = 8;
	
	/**
	 * 일정 등록 가능 종료 시간
	 */
	private int endHour = 20;
	

	public ScheduleConfig() {}
	
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getDefaultView() {
		return defaultView;
	}

	public void setDefaultView(int defaultView) {
		this.defaultView = defaultView;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public int getViewTodo() {
		return viewTodo;
	}

	public void setViewTodo(int viewTodo) {
		this.viewTodo = viewTodo;
	}


	public int getStartHour() {
		return startHour;
	}


	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}


	public int getEndHour() {
		return endHour;
	}


	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	
}
