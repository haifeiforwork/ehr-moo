/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.lightpack.planner.model.Schedule;


/**
 * 일정 알람 서비스
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: AlarmPlannerService.java 17315 2012-02-08 04:56:13Z yruyo $
 */

@Transactional
public interface AlarmPlannerService {
	
	/**해당 알람시간에 해당하는 발송 데이터 목록 읽기
	 * @param jobTime
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List getAlarmTargetList(Date jobTime, int interval);
	
	/**알람 서비스(메일,SMS,쪽지 발송)
	 * @param jobTime
	 * @return
	 * @throws ParseException 
	 */
	void doAlarmJobSchedule(Date jobTime, int interval, String fileUrl) throws ParseException;

	/**
	 * 일정 생성, 수정, 삭제시 메일 보내기
	 * @param string
	 * @param schedule
	 */
	void sendSimpleMail(String string, Schedule schedule);
	
	void sendDeleteMail(String string, Schedule schedule);
}
