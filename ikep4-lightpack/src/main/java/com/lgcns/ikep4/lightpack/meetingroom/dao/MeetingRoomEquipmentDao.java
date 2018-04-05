/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomEquipment;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface MeetingRoomEquipmentDao extends GenericDao<MeetingRoomEquipment, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<MeetingRoomEquipment> selectEquipment(MeetingRoomEquipment meetingRoomEquipment);

}
