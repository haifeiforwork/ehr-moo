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
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface CartooletcService extends GenericService<Cartooletc, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<Cartooletc> select(Cartooletc cartooletc);

	/**
	 * 순서 변경
	 * 
	 * @param map
	 * @return
	 */
	public void changeSort(List<Cartooletc> meetingRoomList, String meetingRoomId, int sortOrder);

	/**
	 * 삭제
	 * 
	 * @param roomIds
	 */
	public void delete(String[] cartooletcIds);
	
	public String checkFavorite(String cartooletcId);
	
	public void addFavorite(String cartooletcId);
	
	public void delFavorite(String cartooletcId);
	
	public List<Map<String, String>> makeMeetingTimeList(String cartooletcId, List<Map<String, Object>> scheduleList);
	
	public List<Map<String, Object>> makeMeetingWeekTimeList(String cartooletcId, List<Map<String, Object>> scheduleList, String startDayOfWeek);
	
	public List<Cartooletc> categoryList(Map<String, String> param);
	
	public List<Cartooletc> regionList(Map<String, String> param);
	
	public List<Cartooletc> toolList(Map<String, String> param);

}