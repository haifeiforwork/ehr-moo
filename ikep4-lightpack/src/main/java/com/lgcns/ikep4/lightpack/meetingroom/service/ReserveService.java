/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface ReserveService extends GenericService<MeetingRoom, String> {

	public SearchResult<Map<String, Object>> list(MeetingRoomSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> listCar(MeetingRoomSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectExists(Schedule schedule);
	
	public List<Map<String, Object>> selectCarExists(Schedule schedule);
	
	public void sendReservationMail(String roomOrCar, String message, Schedule schedule, User sender, User manager);
}