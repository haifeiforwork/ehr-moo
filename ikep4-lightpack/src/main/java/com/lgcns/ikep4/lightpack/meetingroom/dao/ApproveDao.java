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
import com.lgcns.ikep4.lightpack.meetingroom.model.Approve;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;

/**
 * 예약 합의 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: ApproveDao.java 16276 2011-08-18 07:09:07Z yruyo $
 */
public interface ApproveDao extends GenericDao<Approve, String> {

	void delete(Map<String, String> param);
	
	public List<Map<String, Object>> selectAll(MeetingRoomSearchCondition searchCondition);
	
	public Integer selectCount(MeetingRoomSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectAllCar(MeetingRoomSearchCondition searchCondition);
	
	public Integer selectCarCount(MeetingRoomSearchCondition searchCondition);

	public Integer selectCountByManagerId(Map<String, String> param);
	public Integer selectCountByManagerId2(Map<String, String> param);
	public Approve getApproveMap(Map<String, String> param);
}