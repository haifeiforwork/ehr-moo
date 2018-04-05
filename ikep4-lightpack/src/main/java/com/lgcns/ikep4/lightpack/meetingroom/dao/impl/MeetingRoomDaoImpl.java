/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.facility.model.FacilitySearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.dao.MeetingRoomDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class MeetingRoomDaoImpl extends GenericDaoSqlmap<MeetingRoom, String> implements MeetingRoomDao {

	String NAMESPACE = "lightpack.meeting.dao.meetingroom.";

	public String create(MeetingRoom meetingRoom) {
		
		return (String) sqlInsert(NAMESPACE + "insert", meetingRoom);
	}
	
	public void createToolRoomMap(MeetingRoom meetingRoom) {
		
		sqlUpdate(NAMESPACE + "insertToolRoom", meetingRoom);
	}
	
	public void deleteToolRoomMap(MeetingRoom meetingRoom) {
		
		sqlDelete(NAMESPACE + "deleteToolRoom", meetingRoom);
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public MeetingRoom get(String meetingRoomId) {
		
		return (MeetingRoom) sqlSelectForObject(NAMESPACE + "get", meetingRoomId);
	}

	public void remove(String meetingRoomId) {
		
		sqlDelete(NAMESPACE + "delete", meetingRoomId);
	}

	public void update(MeetingRoom meetingRoom) {
		
		sqlUpdate(NAMESPACE + "update", meetingRoom);
	}

	public List<MeetingRoom> select(MeetingRoom meetingRoom) {
		return (List<MeetingRoom>) sqlSelectForList(NAMESPACE + "select", meetingRoom);
	}

	public void updateSortOrder(MeetingRoom meetingRoom) {
		
		sqlUpdate(NAMESPACE + "updateSortOrder", meetingRoom);
	}

	public String getSortOrder(MeetingRoom meetingRoom) {
		
		return (String) sqlSelectForObject(NAMESPACE + "selectSortOrder", meetingRoom);
	}
	
	public String addFavorite(MeetingRoom meetingRoom) {
		return (String) sqlInsert(NAMESPACE + "addFavorite", meetingRoom);
	}

	public void delFavorite(MeetingRoom meetingRoom) {
		sqlDelete(NAMESPACE + "delFavorite", meetingRoom);
	}

	public String checkFavorite(MeetingRoom meetingRoom) {
		return (String) sqlSelectForObject(NAMESPACE + "checkFavorite", meetingRoom);
	}
	
	/**
	 * 설비 트리
	 * @param param Map
	 * @return List
	 */
	public List<MeetingRoom> getMeetingRoomList(FacilitySearchCondition facilitySearchCondition){
		List<MeetingRoom> mr = this.sqlSelectForList(NAMESPACE + "getMeetingRoomList", facilitySearchCondition);
		return mr;
	}
	
	public MeetingRoom readRootCategory(String portalId) {
		
		return (MeetingRoom)sqlSelectForObject(NAMESPACE + "readRootCategory", portalId);
	}
	
	public MeetingRoom readCategory(String categoryId) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("categoryId", categoryId);
		return (MeetingRoom)sqlSelectForObject(NAMESPACE + "readCategory", param);
	}
	
	public List<MeetingRoom> readParentsCategory(String categoryId) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("categoryId", categoryId);
		return sqlSelectForList(NAMESPACE + "readParentsCategory", param);
	}
	
	public List<MeetingRoom> readChildCategory(String categoryId) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("categoryId", categoryId);
		return sqlSelectForList(NAMESPACE + "readChildCategory", param);
	}
}