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
import com.lgcns.ikep4.lightpack.facility.model.FacilitySearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface MeetingRoomService extends GenericService<MeetingRoom, String> {

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
	public void changeSort(List<MeetingRoom> meetingRoomList, String meetingRoomId, int sortOrder);

	/**
	 * 삭제
	 * 
	 * @param roomIds
	 */
	public void delete(String[] meetingRoomIds);
	
	public String checkFavorite(String meetingRoomId);
	
	public void addFavorite(String meetingRoomId);
	
	public void delFavorite(String meetingRoomId);
	
	public List<Map<String, String>> makeMeetingTimeList(String meetingRoomId, List<Map<String, Object>> scheduleList);
	
	public List<Map<String, Object>> makeMeetingWeekTimeList(String meetingRoomId, List<Map<String, Object>> scheduleList, String startDayOfWeek);
	
	/**
	 * 설비 목록
	 * @param param Map
	 * @return List
	 */
	public List<MeetingRoom> getMeetingRoomList(FacilitySearchCondition facilitySearchCondition);
	
	/**
	 * 최상위 카테고리
	 * @param  포탈코드
	 * @return 카테고리
	 */
	public MeetingRoom readRootCategory(String portalId);
	
	/**
	 * 카테고리
	 * @param  카테고리 코드
	 * @return 카테고리
	 */
	public MeetingRoom readCategory(String categoryId);
	
	/**
	 * 카테고리
	 * @param  카테고리 코드
	 * @return 카테고리
	 */
	public List<MeetingRoom> readParentsCategory(String categoryId);
	
	/**
	 * 카테고리
	 * @param  카테고리 코드
	 * @return 카테고리
	 */
	public List<MeetingRoom> readChildCategory(String categoryId);
	
	public void createToolRoomMap(MeetingRoom meetingRoom);

}