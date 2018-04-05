/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.EventInfoVO;
import com.lgcns.ikep4.lightpack.planner.model.FavoriteTarget;
import com.lgcns.ikep4.lightpack.planner.model.Holiday;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.search.ScheduleSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ScheduleDetail;

/**
 * 일정 처리 Dao interface
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleDao.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
public interface ScheduleDao extends GenericDao<Schedule, String> {
	
	String NAMESPACE = "lightpack.planner.dao.Schedule";

	/**
	 * 사용자 기간별 일정 읽기(참여,참조 일정 포함)
	 * @param params : String userId, String startDate, String endDate
	 * @return
	 */
	List<Map<String, Object>> selectByPeriod(Map<String, Object> params);
	
	List<Map<String, Object>> selectByPeriodHb(Map<String, Object> params);
	
	List<Map<String, Object>> selectByPeriodNew(Map<String, Object> params);
	
	List<Map<String, Object>> selectByPeriodNewHb(Map<String, Object> params);

	/**
	 * 팀 기간별 일정 읽기
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectByPeriodForWorkspace(Map<String, Object> params);
	
	List<Map<String, Object>> selectByPeriodForWorkspaceHb(Map<String, Object> params);
	

	/**
	 * 전사일정 기간별 일정 읽기
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectByPeriodForCompany(Map<String, Object> params);
	
	List<Map<String, Object>> selectByPeriodForCompanyPortlet(Map<String, Object> params);
	

	/**
	 * 사용자들 기간별 일정 읽기(참여,참조 일정 포함)
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> readUsersSchedule(Map<String, Object> params);

	/**
	 * 사용자 기간별 일반 일정 갯수
	 * @param params
	 * @return
	 */
	int getUserNormalEventCount(Map<String, Object> params);

	/**
	 * 사용자 기간별 반복 일정 읽기
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> readUserRecurrence(Map<String, Object> params);

	/**
	 * jobTime 알람시간에 해당하는 발송 데이터(일반일정 대상) 목록 읽기
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List getAlarmTargetNormalList(Map<String, Object> params);

	/**
	 * jobTime 알람시간에 해당하는 반복일정 목록 읽기
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List getAlarmTargetRecurrenceEvents(Map<String, Object> params);

	/**
	 * 반복일정에 해당하는 알람 정보 읽기
	 * @param event
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map getAlarmInfo(Map event);
	
	/** Schedule 등록
	 * @param schedule
	 */
	void createSchedule(Schedule schedule);

	/** 되풀이 등록
	 * @param recurrences
	 */
	void createRecurrences(Recurrences recurrences);

	void scheduleMove(Map<String, Object> params);
	
	void scheduleMoveAllday(Map<String, Object> params);
	
	void scheduleMoveTime(Map<String, Object> params);
	
	void scheduleMoveCall(Map<String, Object> params);

	void scheduleResize(Map<String, Object> params);

	Mandator getTrustee(String userId);

	void createMandator(Mandator mandator);

	void deleteMandator(String mandatorId);

	@SuppressWarnings("rawtypes")
	List<Map> getMyMandators(String trusteeId);

	void createHoliday(Holiday holyday);

	Holiday getHoliday(String holydayId);

	@SuppressWarnings("rawtypes")
	List getHolidayList();

	@SuppressWarnings("rawtypes")
	List<Map> getHolidayList(Map<String, Object> params);
	
	void updateHoliday(Holiday holyday);

	void deleteHoliday(String[] holydayIdList);

	String createByTrustee(Schedule schedule);

	void removeScheduleSubData(String scheduleId);
	
	void scheduleMoveAfterEvents(Map<String, Object> params) throws SQLException;

	void updatePrevRecurrenceEndDate(String scheduleId, Date prevStart, Date prevEnd);

	/**
	 * 반복일정 복사(반복일정 일자 변경)
	 * @param scheduleId
	 * @param nextStart
	 * @param nextEnd
	 * @param start
	 */
	void insertNextRecurrence(String scheduleId, Date nextStart, Date nextEnd, Date start);
	
	@SuppressWarnings("rawtypes")
	@Deprecated
	void insertNextRecurrence(Map sco);
	@Deprecated
	void insertNextRecurrence(EventInfoVO eventInfo);

	/**
	 * TODO Javadoc주석작성
	 * @param scheduleId
	 * @param start
	 * @param end
	 */
	void updateScheduleDate(String scheduleId, Date start, Date end);

	
	
	void updateScheduleUpdateDisplay(String scheduleId, String value);
	/**
	 * TODO Javadoc주석작성
	 * @param scheduleId
	 * @param prevStart
	 * @param prevEnd
	 */
	void shiftNextRepeatDate(String scheduleId, Date nextStart, Date nextEnd);
	
	void shiftPrevRepeatDate(String scheduleId, Date prevStart, Date prevEnd);
	
	void updateRepeatScheduleDate(String scheduleId, Date start, Date end);
	

	/**반복일정을 일반일정으로 복사
	 * @param scheduleId
	 * @param newScheduleId 
	 * @param start
	 * @param end
	 */
	void copySchedule(String scheduleId, String newScheduleId, Date start, Date end);
	
	void copyMySchedule(String scheduleId, String newScheduleId, String userId, String userName);
	
	void copyTeamSchedule(String scheduleId, String newScheduleId, String userId, String userName, String groupId);
	
	void copyTeamSapSchedule(String awart, String atextCode, String scheduleId, String newScheduleId);

	/**특정일자 이후의 반복일정 삭제
	 * @param scheduleId
	 * @param prevStart
	 */
	void deleteAfterRecurrences(String scheduleId, Date start);

	/**해당 일정의 반복일정 갯수
	 * @param scheduleId
	 * @return
	 */
	int getCountRepeatEvents(String scheduleId);

	/**반복일정중 개별 반복테이블의  row 삭제
	 * @param scheduleId
	 * @param date
	 */
	void deleteCurrenctRepeat(String scheduleId, Date date);

	/**특정일자 이후의 반복 row 삭제
	 * @param scheduleId
	 * @param date
	 */
	void deleteAfterRepeat(String scheduleId, Date date);

	/**특정일자 이후의 반복 row의 scheduleId를 변경
	 * @param scheduleId
	 * @param newScheduleId
	 * @param start
	 */
	void updateAfterRepeatScheduleId(String scheduleId, String newScheduleId, Date start);

	/**해당 범주와 같은 속성값을 갖고 있는 일정들의 범주를 기본범주로 변경
	 * @param cid
	 */
	void updateToDefaultCategory(String[] cid);
	
	/**
	 * 위임자 정보 읽기
	 * @param mandatorId
	 * @return
	 */
	User getMandator(String mandatorId);

	/**
	 * 현재 반복일정 row가 첫번재 row인지 확인
	 * @param scheduleId
	 * @param repeatStartDate
	 * @return 첫번째이면 0, 아니면 1
	 */
	int isFirstRepeatRow(String scheduleId, Date repeatStartDate);

	/**
	 * 사용자 정보(이름,직책,팀명, 영문 포함)
	 * @param userId
	 * @return
	 */
	Map<String, String> getUserInfo(String userId);

	/**
	 * 일정정보 읽기(locale에 해당하는 범주 이름 포함)
	 * @param scheduleId
	 * @param locale
	 * @return
	 */
	Schedule get(String scheduleId, String locale);

	/**
	 * 사용자 이름으로 사용자 아이디 목록 읽기
	 * @param userName
	 * @param maxCount  최대 사용자 수
	 * @return
	 */
	List<String> findUserByName(String userName, int maxCount);

	/**
	 * 휴무명이 존재하는지 확인
	 * @param nation
	 * @param holidayName
	 * @return 
	 */
	boolean isExistName(String nation, String holidayName);

	/**
	 * oldScheduleId 반복 row의 scheduleId를 newScheduleId로 변경
	 * @param newScheduleId
	 * @param oldScheduleId
	 */
	void updateRepeatScheduleId(String newScheduleId, String oldScheduleId);
	
	/**
	 * schedule 목록
	 * @param params
	 * @return
	 */
	public List<Schedule> exportSchedule(Map<String, Object> params);
	
	/**
	 * 회의실별 일정 읽기
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectByPeriodForMeetingRoom(Map<String, Object> params);
	
	public List<Map<String, Object>> selectByPeriodForCartooletc(Map<String, Object> params);
	
	/**
	 * 회의실별 일정 읽기
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectMeetingRoomByReserveList(Map<String, Object> params);
	
	
	public List<Map<String, Object>> selectCartooletcByReserveList(Map<String, Object> params);
	
	/**
	 * 즐겨찾기 회의실별 일정 읽기
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByFavorite(Map<String, Object> params);
	
	public List<Map<String, Object>> selectByPeriodForCartooletcByFavorite(Map<String, Object> params);
	
	/**
	 * 최근 회의실별 일정 읽기
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByRecent(Map<String, Object> params);
	
	public List<Map<String, Object>> selectByPeriodForCartooletcByRecent(Map<String, Object> params);
	
	public List<Schedule> getCompanyScheduleWaitList();
	
	public List<FavoriteTarget> selectFavoriteTeam(String userId);
	
	public List<FavoriteTarget> selectFavoriteUser(String userId);
	
	void updateCompanyScheduleApprove(String[] scheduleIdList);
	
	public List<Map<String, Object>> portalQuickMenuCountAll(Map<String, Object> params);
	
	/**
	 * 사용자 기간별 일정 검색 개수
	 * @param params : ScheduleSearchCondition searchCondition
	 * @return
	 */
	int searchScheduleCount(ScheduleSearchCondition searchCondition);
	
	/**
	 * 사용자 기간별 일정 검색
	 * @param params : ScheduleSearchCondition searchCondition
	 * @return
	 */
	List<Map<String, Object>> searchSchedule(ScheduleSearchCondition searchCondition);
	
	public void updateScheduleSyncData(ScheduleDetail scheduleDetail);
	
	List<Schedule> sapScheduleList();
	
	public void insertSapSchedule(Schedule schedule);
	
	public void insertFavoriteTeam(Schedule schedule);
	
	public void insertFavoriteUser(Schedule schedule);
	
	public void deleteFavoriteSetting(Schedule schedule);
	
	public void updateScheduleSyncDataEnd();
	
	public void removeTeamSchedule(String scheduleId);
	
	public void removeSapSchedule(String scheduleId);

}
