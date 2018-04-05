/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.meetingroom.dao.MeetingRoomEquipmentDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomEquipment;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class MeetingRoomEquipmentDaoImpl extends GenericDaoSqlmap<MeetingRoomEquipment, String> implements
		MeetingRoomEquipmentDao {

	String NAMESPACE = "lightpack.meeting.dao.MeetingRoomCommon.";

	public String create(MeetingRoomEquipment arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public MeetingRoomEquipment get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(MeetingRoomEquipment arg0) {
		// TODO Auto-generated method stub

	}

	public List<MeetingRoomEquipment> selectEquipment(MeetingRoomEquipment meetingRoomEquipment) {
		return (List<MeetingRoomEquipment>) sqlSelectForList(NAMESPACE + "selectEquipment", meetingRoomEquipment);
	}

}
