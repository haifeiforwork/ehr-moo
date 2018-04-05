/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.meetingroom.dao.ReserveDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class ReserveDaoImpl extends GenericDaoSqlmap<MeetingRoom, String> implements ReserveDao {

	String NAMESPACE = "lightpack.meeting.dao.reserve.";
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectAll(MeetingRoomSearchCondition searchCondition) {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectAll", searchCondition);
	}
	
	public Integer selectCount(MeetingRoomSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject(NAMESPACE + "selectCount", searchCondition);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectAllCar(MeetingRoomSearchCondition searchCondition) {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectAllCar", searchCondition);
	}
	
	public Integer selectCarCount(MeetingRoomSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject(NAMESPACE + "selectCarCount", searchCondition);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectExists(Schedule schedule) {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectExists", schedule);
	}

	public String create(MeetingRoom arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public MeetingRoom get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(MeetingRoom arg0) {
		// TODO Auto-generated method stub
		
	}

}