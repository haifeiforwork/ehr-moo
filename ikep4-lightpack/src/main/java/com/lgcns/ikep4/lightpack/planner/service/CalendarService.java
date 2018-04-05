/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.planner.model.Holiday;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.ical.model.ICalendar;

/**
 * TODO Javadoc주석작성
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: CalendarService.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
public interface CalendarService extends GenericService<Schedule, String> {

	void scheduleMove(Map<String, Object> params) throws ParseException, SQLException;
	
	void scheduleMoveAllday(Map<String, Object> params) throws ParseException, SQLException;
	
	void scheduleMoveTime(Map<String, Object> params) throws ParseException, SQLException;

	void scheduleResize(Map<String, Object> params);

	/**
	 * 수임자  정보 가져옴.
	 * @param userId
	 * @return
	 */
	Mandator getTrustee(String userId);
	
	/**
	 * 수임자 등록
	 * @param user
	 * @param portalid
	 * @param trusteeId
	 * @return
	 */
	Mandator createMandator(User user, String portalid, String trusteeId);

	/**
	 * 수임자 삭제
	 * @param mandatorId
	 */
	void deleteMandator(String mandatorId);

	/**
	 * 위임자 목록 가져옴
	 * @param trusteeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getMyMandators(String trusteeId);
	
	/**
	 * 위임자 정보 읽기
	 * @param mandatorId
	 * @return
	 */
	User getMandator(String mandatorId);

	String createHoliday(User user, String portalId, Holiday Holiday);

	Holiday getHoliday(String HolidayId);

	List<Holiday> getHolidayList();

	void updateHoliday(Holiday Holiday);

	void deleteHoliday(String[] HolidayIdList);

	String createByTrustee(Schedule schedule, User user);

	/**
	 * schedule관련 모든 데이터 읽기
	 * @param scheduleId
	 * @param locale
	 * @return
	 */
	Schedule getScheduleAllData(String scheduleId);
	
	/**
	 * schedule관련 모든 데이터 읽기 (locale에 해당하는 범주)
	 * @param scheduleId
	 * @param locale
	 * @return
	 */
	Schedule getScheduleAllData(String scheduleId, String locale);
	
	/**
	 * 일정 상세정보 수정시 호출(Schedule, Recurrence 정보는 이미 있음)
	 * @param scheduleId
	 * @return
	 */
	Map<String, Object> getScheduleSubInfo(String scheduleId);
	
	void deleteSchedule(String scheduleId);

	void updateSchedule(Schedule schedule);
	
	void clearCall();
	
	void updateSchedule(UpdateScheduleVO data) throws ParseException;

	/**TODO Javadoc주석작성
	 * @param params
	 */
	void updateAcceptInfo(Map<String, String> params);

	/**TODO Javadoc주석작성
	 * @param schedule
	 * @param user
	 * @return
	 */
	String create(Schedule schedule, User user);

	/**TODO Javadoc주석작성
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//List<Map<String, Object>> readMySchedule(String userId, String startDate, String endDate);
	
	/**
	 * 특정일 나의 일정 갯수(참조/참여 포함)
	 * @param userId
	 * @param startDate
	 * @return
	 */
	//int getMyScheduleCount(String userId, Date startDate);

	/**
	 * 특정 기간 해당 국가코드의 휴무일 목록 읽기
	 * @param start
	 * @param end
	 * @param nationCode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getHolidayList(long start, long end, String nationCode);
	
	/**
	 * 특정 기간 해당 국가코드의 휴무일 목록 읽기
	 * @param start
	 * @param end
	 * @param userInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getHolidayList(long start, long end, User userInfo);

	/**
	 * 사용자 정보(이름,직책,팀명, 영문 포함)
	 * @param userId
	 * @return
	 */
	Map<String, String> getUserInfo(String userId);

	/**
	 * 특정 사용자 일정 목록 읽기
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	//List<Map<String, Object>> getUserSchedule(String userId, long start, long end);

	/**
	 * 일정 만들기 (메일전송)
	 * @param schedule
	 * @param user
	 * @param sendmail
	 * @return
	 */
	String create(Schedule schedule, User user, boolean sendmail);
	
	/**
	 * 일정 수정 (메일발송 여부)
	 * @param param
	 * @param sendmail
	 * @throws Exception 
	 */
	void updateSchedule(UpdateScheduleVO param, boolean sendmail);
	
	/**
	 * 일정 수정 (메일발송 여부)
	 * @param param
	 * @param sendmail
	 * @throws Exception 
	 */
	String updateScheduleNew(UpdateScheduleVO param, boolean sendmail);
	
	/**
	 * 일정 삭제 (메일발송 확인)
	 * @param params
	 * @throws ParseException 
	 */
	void deleteSchedule(UpdateScheduleVO params) throws ParseException;
	
	void copyMySchedule(UpdateScheduleVO params, User user) throws ParseException;
	
	void copyTeamSchedule(UpdateScheduleVO params, User user) throws ParseException;

	/**일정 삭제 (메일발송 확인)
	 * @param scheduleId
	 * @param sendmail
	 */
	@Deprecated
	void deleteSchedule(String scheduleId, boolean sendmail);
	
	/**
	 * 일정 삭제표시
	 * @param params
	 * @throws ParseException 
	 */
	void removeScheduleUpdateDisplay(UpdateScheduleVO params) throws ParseException;
	
	/**TODO Javadoc주석작성
	 * @param params
	 * @throws ParseException 
	 */
	@Deprecated
	void deleteRepeatSchedule(Map<String, Object> params) throws ParseException;

	/**
	 * 사용자 이름으로 사용자 찾기 (이름과 일치하는 user가 1명 있으면 사용자  User 
	 * @param userName
	 * @return 이름과 일치하는 user가 1명 있으면 사용자  아이디, count = 한명 이상이면 2, 없으면 0
	 * 
	 */
	Map<String, Object> findUserByName(String userName);

	/**
	 * 사용자 일정 읽기(본인, 공유 포함)
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	//List<Map<String, Object>> getUserSchedule(String userId, String start, String end);

	/**
	 * 휴무명이 존재하는지 확인
	 * @param nation
	 * @param holidayName
	 * @return
	 */
	boolean isExistName(String nation, String holidayName);
	
	/**
	 * import 받은 스케줄 변환 저장
	 * @param importSchedule
	 * @return
	 */
	boolean importSchedule(InputStream importSchedule, User user);

	public ICalendar exportSchedule(Map<String, Object> params, User user);
	
	void updateCompanyScheduleApprove(String[] scheduleIdList);
	
	/**
	 * 모바일에서 첨부파일의 실제 저장 경로 및 파일명의 요청으로 별도로 사용함
	 * @param scheduleId
	 * @param locale
	 * @return
	 */
	Schedule getScheduleAllDataWithMobile(String scheduleId, String locale);

}
