/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;

/**
 * 기자재 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: MeetingRoomBuildingDaoImpl.java 16276 2011-08-18 07:09:07Z yruyo $
 */
public interface ReserveDao extends GenericDao<MeetingRoom, String> {

	/**
	 *  조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<Map<String, Object>> selectAll(MeetingRoomSearchCondition searchCondition);
	
	public Integer selectCount(MeetingRoomSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectAllCar(MeetingRoomSearchCondition searchCondition);
	
	public Integer selectCarCount(MeetingRoomSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectExists(Schedule schedule);

}