/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.planner.model.FavoriteTarget;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.search.ScheduleSearchCondition;
//import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ScheduleDetail;

/**
 * 일정관리 서비스
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: ScheduleService.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
public interface ScheduleService  extends GenericService<Schedule, String> {
	/**
	 * 사용자 기간별 일정 읽기(참여,참조 일정 포함) 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	List<Map<String, Object>> selectByPeriod(String userId, Date startDate, Date endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriod(String userId, long startDate, long endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriod(String userId, String startDate, String endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriod(Map<String, Object> params) throws ParseException;
	/**
	 * 팀 기간별 일정 읽기
	 * @param workspaceId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	List<Map<String, Object>> selectByPeriodForWorkspace(String workspaceId, Date startDate, Date endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriodForWorkspace(String workspaceId, long startDate, long endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriodForWorkspace(String userId, String workspaceId, long start, long end) throws ParseException;
	List<Map<String, Object>> selectByPeriodForWorkspaceHb(String userId, String hbName,String workspaceId, long start, long end) throws ParseException;
	//List<Map<String, Object>> selectByPeriodForWorkspaceBySort(String workspaceId, long start, long end) throws ParseException;

	
	/**
	 * 전사일정 기간별 일정 읽기
	 * @param workspaceId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	List<Map<String, Object>> selectByPeriodForCompany(String workspaceId, Date startDate, Date endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriodForCompany(String workspaceId, long startDate, long endDate) throws ParseException;
	List<Map<String, Object>> selectByPeriodForCompany(String userId, String workspaceId, long start, long end) throws ParseException;
	
	List<Map<String, Object>> selectByPeriodForCompanyPortlet(String userId, String workspaceId, long start, long end) throws ParseException;
	
	
	/**
	 * 사용자들 기간별 일정 읽기(참여,참조 일정 포함, 참여자 확인 스크립트에서 사용)
	 * @param users
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	List<Map<String, Object>> readUsersSchedule(String[] users, Date startDate, Date endDate) throws ParseException;
	List<Map<String, Object>> readUsersSchedule(String[] users, long startDate, long endDate) throws ParseException;
	List<Map<String, Object>> readUsersSchedule(Map<String, Object> params) throws ParseException;
	

	/**
	 * 특정일 사용자의 일정 갯수(참조/참여 포함)
	 * @param userId
	 * @param startDate
	 * @return
	 * @throws ParseException 
	 */
	int getUserScheduleCount(String userId, Date startDate) throws ParseException;
	
	/**
	 * jobTime 알람시간에 해당하는 발송 데이터 목록 읽기
	 * @param jobTime
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("rawtypes")
	List getAlarmTargetList(Date jobTime, int interval) throws ParseException;
	
	/**
	 * 공유 workspace 오늘의 일정 읽기 (Workspace에서 사용)
	 * @param workspaceId
	 * @param startDate
	 * @param viewUrl
	 * @return
	 * @throws ParseException 
	 */
	List<Map<String, Object>> readWorkspaceSchedule(String workspaceId, String startDate, String viewUrl) throws ParseException;
	
	/**
	 * 오늘의 일정
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	
	List<Map<String, Object>> readMyTodaySchedule(String userId) throws ParseException;
	/**
	 * 위임자 일정 정보 읽기
	 * @param userId
	 * @param mandatorId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	List<Map<String, Object>> selectByPeriodByTrustee(String userId, String mandatorId, long startDate, long endDate) throws ParseException;
	
	/**
	 * 타 사용자 일정 읽기
	 * @param userId
	 * @param targetUserId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> selectByPeriodByTargetUser(String userId, String targetUserId, long startDate,
			long endDate) throws ParseException;
	
	List<Map<String, Object>> selectByPeriodByTargetUserHb(String userId,String hbName, String targetUserId, long startDate,
			long endDate) throws ParseException;
	
	/**
	 * 나의 일정 읽기
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> selectByPeriodByMySchedule(String userId, long startDate, long endDate) throws ParseException;
	
	List<Map<String, Object>> selectByPeriodByMyScheduleHb(String userId, String hbName, long startDate, long endDate) throws ParseException;
	
	public boolean isEditable(String userId, String regUserId);
	public boolean isEditable(String[] userId, String regUserId);
	
	public List<Map<String, Object>> readMeetingRoomScheduleById(String meetingRoomId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> readCartooletcScheduleById(String cartooletcId, Date fromDate, Date toDate) throws ParseException;

	public List<Map<String, Object>> readMeetingRoomScheduleByBuildingFloor(String buildingId, String floorId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> readCartooletcScheduleByCategoryRegion(String categoryId, String regionId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> readMeetingRoomScheduleByFavorite(String portalId, String userId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> readCartooletcScheduleByFavorite(String portalId, String userId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> readMeetingRoomScheduleByRecent(String portalId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> readCartooletcScheduleByRecent(String portalId, Date fromDate, Date toDate) throws ParseException;
	
	public List<Map<String, Object>> selectMeetingRoomByReserveList(String meetingRoomId, Date startDate, Date endDate, String scheduleId) throws ParseException;
	
	public List<Map<String, Object>> selectCartooletcByReserveList(String cartooletcId, Date startDate, Date endDate, String scheduleId) throws ParseException;
	
	public List<Map<String, Object>> getMeetingRoomRepeatList(Schedule schedule);
	
	public List<Map<String, Object>> getCartooletcRepeatList(Schedule schedule);
	
	public List<Map<String, Object>> selectFacilityReserveListWithoutSchedule(Schedule schedule);
	
	public List<Map<String, Object>> portalQuickMenuCountAll(String userId, Date startDate) throws ParseException;
	
	public List<Schedule> getCompanyScheduleWaitList();
	
	public List<FavoriteTarget> selectFavoriteTeam(String userId);
	
	public List<FavoriteTarget> selectFavoriteUser(String userId);
	
	public void favoriteSetting(Schedule schedule, String flg);
	
	/**
	 * 개인 일정 에서 사용
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> readUserDateSchedule(String userId, String startDate, String endDate) throws ParseException;

	public Date convertToServerTimezoneOnlyDate(Date d, Date t);
	
	public Map<String, Object> selectByPeriodScheduleSearch(String userId, ScheduleSearchCondition searchCondition) throws ParseException;
	
	public void updateScheduleSyncData(List<ScheduleDetail> scheduleDetai1);
	
	public void insertScheduleSyncData();
	
	
	/**
	 * 오늘의 일정(Web Diary에서 사용)
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	
	List<Map<String, Object>> readMyWebDiarySchedule(String userId) throws ParseException;
	List<Map<String, Object>> readTeamWebDiarySchedule(String userId, String workspaceId, long start, long end) throws ParseException;
	List<Map<String, Object>> webDiaryReadUserDateSchedule(String userId, String startDate, String endDate) throws ParseException;
	List<Map<String, Object>> webDiaryReadWorkspaceSchedule(String workspaceId, String startDate, String viewUrl) throws ParseException;
	
}