/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.lightpack.meetingroom.dao.MeetingRoomEquipmentDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomEquipment;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomEquipmentService;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class MeetingRoomEquipmentServiceImpl implements MeetingRoomEquipmentService {

	@Autowired
	private MeetingRoomEquipmentDao meetingRoomEquipmentDao;

	public String create(MeetingRoomEquipment arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public MeetingRoomEquipment read(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(MeetingRoomEquipment arg0) {
		// TODO Auto-generated method stub

	}

	public List<MeetingRoomEquipment> selectEquipment(MeetingRoomEquipment meetingRoomEquipment) {
		return meetingRoomEquipmentDao.selectEquipment(meetingRoomEquipment);
	}

}
