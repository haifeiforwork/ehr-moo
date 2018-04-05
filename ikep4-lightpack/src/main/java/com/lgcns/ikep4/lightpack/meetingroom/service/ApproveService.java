/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service;

import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.Approve;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface ApproveService extends GenericService<Approve, String> {

	public String create(Approve approve);
	
	public void update(Approve approve);
	
	public void delete(Map<String, String> param);
	
	public Approve getApprove(String scheduleId);
	
	public Approve getApproveMap(Map<String, String> param);
	
	public SearchResult<Map<String, Object>> list(MeetingRoomSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> listCar(MeetingRoomSearchCondition searchCondition);
	
	public boolean isMeetingRoomManager(Map<String, String> param);
	
	public boolean isCarToolManager(Map<String, String> param);
}