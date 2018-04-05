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
import com.lgcns.ikep4.lightpack.meetingroom.dao.ApproveDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.Approve;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class ApproveDaoImpl extends GenericDaoSqlmap<Approve, String> implements ApproveDao {

	String NAMESPACE = "lightpack.meeting.dao.approve.";

	public String create(Approve approve) {
		
		return (String) sqlInsert(NAMESPACE + "insert", approve);
	}
	
	public void delete(Map<String, String> params) {
		
		sqlDelete(NAMESPACE + "delete", params);
	}
	
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
	
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Approve get(String scheduleId) {

		return (Approve) sqlSelectForObject(NAMESPACE + "get", scheduleId);
	}
	
	public Approve getApproveMap(Map<String, String> param) {

		return (Approve) sqlSelectForObject(NAMESPACE + "getApproveMap", param);
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(Approve approve) {
		
		sqlUpdate(NAMESPACE + "update", approve);
	}
	
	public Integer selectCountByManagerId(Map<String, String> param) {
		
		return (Integer) sqlSelectForObject(NAMESPACE + "selectCountByManagerId", param);
	}
	public Integer selectCountByManagerId2(Map<String, String> param) {
		
		return (Integer) sqlSelectForObject(NAMESPACE + "selectCountByManagerId2", param);
	}
}