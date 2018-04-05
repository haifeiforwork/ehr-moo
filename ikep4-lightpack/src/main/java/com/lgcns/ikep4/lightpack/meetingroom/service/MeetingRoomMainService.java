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
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface MeetingRoomMainService extends GenericService<MeetingRoom, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<MeetingRoom> select(MeetingRoom meetingRoom);

	/**
	 * 순서 변경
	 * 
	 * @param map
	 * @return
	 */
	public void changeSort(List<MeetingRoom> meetingRoomList, String roomId, int sortOrder);

	/**
	 * 삭제
	 * 
	 * @param roomIds
	 */
	public void delete(String[] roomIds);

	/**
	 * 회의실별 일별 예약 리스트 만들기
	 * 
	 * @param scheduleList
	 * @return
	 */
	public List<Map<String, String>> makeMeetingTimeList(String roomId, List<Map<String, Object>> scheduleList);

}
