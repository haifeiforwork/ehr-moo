/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.dao.ApproveDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.Approve;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;

/**
 * 시스템 관리 Service 구현 클래스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: ApproveServiceImpl.java 17792 2012-03-30 01:32:04Z arthes $
 */
@Service("ApproveService")
public class ApproveServiceImpl extends GenericServiceImpl<Approve, String> implements ApproveService {

	@Autowired
	ApproveDao approveDao;
	
	public String create(Approve approve) {

		String id = approve.getScheduleId();
		
		approveDao.create(approve);

		return id;
	}
	
	public void update(Approve approve) {

		approveDao.update(approve);
	}
	
	public void delete(Map<String, String> param) {
		
		approveDao.delete(param);
	}
	
	public Approve getApprove(String scheduleId) {
		
		Approve approve = approveDao.get(scheduleId);
		
		return approve;
	}
	
	public Approve getApproveMap(Map<String, String> param) {
		
		Approve approve = approveDao.getApproveMap(param);
		
		return approve;
	}
	
	public SearchResult<Map<String, Object>> list(MeetingRoomSearchCondition searchCondition) {
		
		Integer count = approveDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = approveDao.selectAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<Map<String, Object>> listCar(MeetingRoomSearchCondition searchCondition) {
		
		Integer count = approveDao.selectCarCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = approveDao.selectAllCar(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
	}
	
	public boolean isMeetingRoomManager(Map<String, String> param) {
		
		Integer meetingRoomCount = approveDao.selectCountByManagerId(param);
		
		if(meetingRoomCount > 0) {
			
			return Boolean.TRUE;
		} else {
			
			return Boolean.FALSE;
		}
	}
	public boolean isCarToolManager(Map<String, String> param) {
		
		Integer meetingRoomCount = approveDao.selectCountByManagerId2(param);
		
		if(meetingRoomCount > 0) {
			
			return Boolean.TRUE;
		} else {
			
			return Boolean.FALSE;
		}
	}
}