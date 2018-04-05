/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.EventInfoVO;
import com.lgcns.ikep4.lightpack.planner.model.FavoriteTarget;
import com.lgcns.ikep4.lightpack.planner.model.Holiday;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.model.GroupDetail;
import com.lgcns.ikep4.util.model.ScheduleDetail;
import com.lgcns.ikep4.lightpack.planner.model.CSExcelDownSearchCondition;
import com.lgcns.ikep4.lightpack.planner.search.ScheduleSearchCondition;
/**
 * 일정관리 Dao class
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleDaoImpl.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Repository(value = "scheduleDao")
public class ScheduleDaoImpl extends GenericDaoSqlmap<Schedule, String> implements ScheduleDao {

	@Autowired
	private IdgenService idgenService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#selectByPeriod(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriod(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriod", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodHb(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodHb", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodNew(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodNew", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodNewHb(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodNewHb", params);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#selectByPeriodForWorkspace(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForWorkspace(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForWorkspace", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForWorkspaceHb(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForWorkspaceHb", params);
	}

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#selectByPeriodForWorkspace(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForCompany(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForCompany", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForCompanyPortlet(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForCompanyPortlet", params);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#readUsersSchedule(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> readUsersSchedule(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".readUsersSchedule", params);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#getUserNormalEventCount(java.util.Map)
	 */
	public int getUserNormalEventCount(Map<String, Object> params) {
		return (Integer) sqlSelectForObject(NAMESPACE + ".getUserNormalEventCount", params);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#readUserRecurrence(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> readUserRecurrence(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".readUserRecurrence", params);
	}
	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#getAlarmTargetNormalList(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public List getAlarmTargetNormalList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".getAlarmTargetNormalList", params);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#getAlarmTargetRecurrenceEvents(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public List getAlarmTargetRecurrenceEvents(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".getAlarmTargetRecurrenceEvents", params);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#getAlarmInfo(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public Map getAlarmInfo(Map event) {
		return (Map) sqlSelectForObject(NAMESPACE + ".getAlarmInfo", event);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Schedule get(String id, String locale) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("scheduleId", id);
		param.put("locale", locale);
		return (Schedule) sqlSelectForObject(NAMESPACE + ".selectByLocale", param);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Schedule schedule) {
		String id = idgenService.getNextId();
		if(log.isDebugEnabled()) {
			StringBuffer msg = new StringBuffer("ID created by idgenService: [")
			.append(id).append("]");
			log.debug(msg.toString());
		}
		
		schedule.setScheduleId(id);
		sqlInsert(NAMESPACE + ".insertSchedule", schedule);
		
		return id;
	}
	
	public String createByTrustee(Schedule schedule) {
		String id = idgenService.getNextId();
		if(log.isDebugEnabled()) {
			StringBuffer msg = new StringBuffer("ID created by idgenService: [")
			.append(id).append("]");
			log.debug(msg.toString());
		}
		
		schedule.setScheduleId(id);
		sqlInsert(NAMESPACE + ".insertScheduleByTrustee", schedule);
		
		return id;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#createSchedule(com.lgcns.ikep4.lightpack.planner.model.Schedule)
	 */
	public void createSchedule(Schedule schedule) {
		sqlInsert(NAMESPACE + ".insertSchedule", schedule);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Schedule schedule) {
		sqlUpdate(NAMESPACE + ".update", schedule);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String scheduleId) {
		// TODO: 관련 데이터가 있는지 확인후 실행
		sqlDelete(NAMESPACE + ".delete-recurrence", scheduleId);
		sqlDelete(NAMESPACE + ".delete-participant", scheduleId);
		sqlDelete(NAMESPACE + ".delete-alarm", scheduleId);
		sqlDelete(NAMESPACE + ".delete-schedule", scheduleId);
	}
	
	public void removeTeamSchedule(String scheduleId) {
		sqlDelete(NAMESPACE + ".delete-recurrence", scheduleId);
		sqlDelete(NAMESPACE + ".delete-participant", scheduleId);
		sqlDelete(NAMESPACE + ".delete-alarm", scheduleId);
		sqlDelete(NAMESPACE + ".delete-team-schedule", scheduleId);
	}
	
	public void removeSapSchedule(String scheduleId) {
		
		sqlDelete(NAMESPACE + ".delete-sap-schedule", scheduleId);
	}

	public void removeScheduleSubData(String scheduleId) {
		sqlDelete(NAMESPACE + ".delete-recurrence", scheduleId);
		sqlDelete(NAMESPACE + ".delete-participant", scheduleId);
		sqlDelete(NAMESPACE + ".delete-alarm", scheduleId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#createRecurrences(com.lgcns.ikep4.lightpack.planner.model.Recurrences)
	 */
	public void createRecurrences(Recurrences recurrences) {
		sqlInsert(NAMESPACE + ".insertRecurrences", recurrences);
	}

	public void scheduleMove(Map<String, Object> params) {
			sqlUpdate(NAMESPACE + ".scheduleMove", params);
	}
	public void scheduleMoveAllday(Map<String, Object> params) {
		sqlUpdate(NAMESPACE + ".scheduleMoveAllday", params);
	}
	public void scheduleMoveTime(Map<String, Object> params) {
		sqlUpdate(NAMESPACE + ".scheduleMoveTime", params);
	}
	public void scheduleMoveCall(Map<String, Object> params) {
			sqlSelectForObject(NAMESPACE + ".moveCall", params);
	}

	public void scheduleResize(Map<String, Object> params) {
		sqlUpdate(NAMESPACE + ".scheduleResize", params);
	}

	public Mandator getTrustee(String userId) {
		return (Mandator) sqlSelectForObject(NAMESPACE + ".getTrustee", userId);
	}

	public void createMandator(Mandator mandator) {
		sqlInsert(NAMESPACE + ".insertMandator", mandator);
	}

	public void deleteMandator(String mandatorId) {
		sqlDelete(NAMESPACE + ".deleteMandator", mandatorId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getMyMandators(String trusteeId) {
		return sqlSelectForList(NAMESPACE + ".getMyMandators", trusteeId);
	}

	public void createHoliday(Holiday holiday) {
		sqlInsert(NAMESPACE + ".insertHoliday", holiday);
	}

	public Holiday getHoliday(String holidayId) {
		return (Holiday) sqlSelectForObject(NAMESPACE + ".getHoliday", holidayId);
	}

	@SuppressWarnings("rawtypes")
	public List getHolidayList() {
		return sqlSelectForList(NAMESPACE + ".getHolidayList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getHolidayList(Map<String, Object> params) {
		return sqlSelectForList(NAMESPACE + ".getHolidayByPeriod", params);
	}

	public void updateHoliday(Holiday holiday) {
		sqlUpdate(NAMESPACE + ".updateHoliday", holiday);
	}

	public void deleteHoliday(String[] holidayIdList) {
		sqlDelete(NAMESPACE + ".deleteHoliday", holidayIdList);
	}

	@Deprecated
	public void scheduleMoveAfterEvents(Map<String, Object> params) throws SQLException {
		SqlMapClient sqlMap = getSqlMapClientTemplate().getSqlMapClient();
		try {
			sqlMap.startTransaction();
			sqlMap.startBatch();
			sqlUpdate(NAMESPACE + ".insertMoveAfterEvents", params);
			sqlUpdate(NAMESPACE + ".updateMoveAfterEvents", params);
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} finally {
			sqlMap.endTransaction();
		}
	}

	public void updatePrevRecurrenceEndDate(String scheduleId, Date prevStart, Date prevEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("prevStart", prevStart);
		params.put("prevEnd", prevEnd);
		
		sqlUpdate(NAMESPACE + ".updatePrevRecurrenceEndDate", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#insertNextRecurrence(java.lang.String, java.util.Date, java.util.Date, java.util.Date)
	 */
	public void insertNextRecurrence(String scheduleId, Date nextStart, Date nextEnd, Date start) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("nextStart", nextStart);
		params.put("nextEnd", nextEnd);
		params.put("start", start);
		
		sqlUpdate(NAMESPACE + ".insertNextRecurrence", params);
	}
	
	public void insertNextRecurrence(EventInfoVO eventInfo) {
		sqlUpdate(NAMESPACE + ".insertNextRecurrence", eventInfo);
	}
	
	@SuppressWarnings("rawtypes")
	public void insertNextRecurrence(Map sco) {
		sqlUpdate(NAMESPACE + ".insertNextRecurrence", sco);
	}

//	public void insertNewWeeklyRecurrence(Map sco) {
//		sqlUpdate(NAMESPACE + ".insertNewWeeklyRecurrence", sco);
//	}
//
//	public List<Map<String, Object>> readMySharedScheduleList(Map<String, Object> params) {
//		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".readMySharedScheduleList", params);
//	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#updateScheduleDate(java.lang.String, java.util.Map)
	 */
	public void updateScheduleDate(String scheduleId, Date start, Date end) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", start);
		params.put("end", end);
		sqlUpdate(NAMESPACE + ".updateScheduleDate", params);
	}
	public void updateScheduleUpdateDisplay(String scheduleId, String updateDisplay) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("updateDisplay", updateDisplay);

		sqlUpdate(NAMESPACE + ".updateScheduleUpdateDisplay", params);
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#updateRepeatDate(java.lang.String, java.util.Map)
	 */
	public void shiftNextRepeatDate(String scheduleId, Date nextStart, Date nextEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", nextStart);
		params.put("end", nextEnd);
		sqlUpdate(NAMESPACE + ".shiftLeftRepeatDate", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#shiftRightRepeatDate(java.lang.String, java.util.Date, java.util.Date)
	 */
	public void shiftPrevRepeatDate(String scheduleId, Date prevStart, Date prevEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", prevStart);
		params.put("end", prevEnd);
		sqlUpdate(NAMESPACE + ".shiftPrevRepeatDate", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#copySchedule(java.lang.String, java.util.Date, java.util.Date)
	 */
	public void copySchedule(String scheduleId, String newScheduleId, Date start, Date end) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("newScheduleId", newScheduleId);
		params.put("start", start);
		params.put("end", end);
		sqlUpdate(NAMESPACE + ".copySchedule", params);
	}
	
	public void copyMySchedule(String scheduleId, String newScheduleId, String userId, String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("newScheduleId", newScheduleId);
		params.put("userId", userId);
		params.put("userName", userName);
		sqlUpdate(NAMESPACE + ".copyMySchedule", params);
	}
	
	public void copyTeamSchedule(String scheduleId, String newScheduleId, String userId, String userName, String groupId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("newScheduleId", newScheduleId);
		params.put("userId", userId);
		params.put("userName", userName);
		params.put("groupId", groupId);
		sqlUpdate(NAMESPACE + ".copyTeamSchedule", params);
	}
	
	public void copyTeamSapSchedule(String awart, String atextCode, String scheduleId, String newScheduleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("newScheduleId", newScheduleId);
		params.put("categoryId", atextCode);
		params.put("awart", awart);
		sqlUpdate(NAMESPACE + ".copyTeamSapSchedule", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#updateRepeatScheduleDate(java.lang.String, java.util.Date, java.util.Date)
	 */
	public void updateRepeatScheduleDate(String scheduleId, Date start, Date end) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", start);
		params.put("end", end);
		sqlUpdate(NAMESPACE + ".updateRepeatScheduleDate", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#deleteAfterRecurrences(java.lang.String, java.util.Date)
	 */
	public void deleteAfterRecurrences(String scheduleId, Date start) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", start);
		sqlUpdate(NAMESPACE + ".deleteAfterRecurrences", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#getCountRepeatEvents(java.lang.String)
	 */
	public int getCountRepeatEvents(String scheduleId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + ".getCountRepeatEvents", scheduleId);
		return count.intValue();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#deleteCurrenctRepeat(java.lang.String, java.util.Date)
	 */
	public void deleteCurrenctRepeat(String scheduleId, Date start) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", start);
		sqlUpdate(NAMESPACE + ".deleteCurrenctRepeat", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#deleteAfterRepeat(java.lang.String, java.util.Date)
	 */
	public void deleteAfterRepeat(String scheduleId, Date start) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("start", start);
		sqlUpdate(NAMESPACE + ".deleteAfterRepeat", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#updateAfterRepeatScheduleId(java.lang.String, java.lang.String, java.util.Date)
	 */
	public void updateAfterRepeatScheduleId(String scheduleId, String newScheduleId, Date start) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("newScheduleId", newScheduleId);
		params.put("start", start);
		sqlUpdate(NAMESPACE + ".updateAfterRepeatScheduleId", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#updateToDefaultCategory(java.lang.String[])
	 */
	public void updateToDefaultCategory(String[] cid) {
		if(cid.length == 0) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", cid);
		params.put("defaultCid", PlannerCategory.DEFAULT_CATEGORY_ID);
		sqlUpdate(NAMESPACE + ".updateToDefaultCategory", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#getMandator(java.lang.String)
	 */
	public User getMandator(String mandatorId) {
		return (User) sqlSelectForObject(NAMESPACE + ".getMandator", mandatorId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#getUserInfo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getUserInfo(String userId) {
		return (Map<String, String>) sqlSelectForObject(NAMESPACE + ".getUserInfo", userId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#isFirstRepeatRow(java.lang.String, java.util.Date)
	 */
	public int isFirstRepeatRow(String scheduleId, Date repeatStartDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		params.put("repeatStartDate", repeatStartDate);
		
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + ".isFirstRepeatRow", params);
		return count.intValue();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Schedule get(String id) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("scheduleId", id);
		return (Schedule) sqlSelectForObject(NAMESPACE + ".select", param);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#findUser(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> findUserByName(String userName, int maxCount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("maxCount", maxCount);
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".findUserByName", params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#isExistName(java.lang.String, java.lang.String)
	 */
	public boolean isExistName(String nation, String holidayName) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("nation", nation);
		param.put("holidayName", holidayName);
		int mcnt = (Integer) sqlSelectForObject(NAMESPACE + ".isExistHolidayName", param);
		return mcnt == 1 ? true : false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao#updateRepeatScheduleId(java.lang.String, java.lang.String)
	 */
	public void updateRepeatScheduleId(String newScheduleId, String oldScheduleId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("newScheduleId", newScheduleId);
		params.put("oldScheduleId", oldScheduleId);
		sqlUpdate(NAMESPACE + ".updateRepeatScheduleId", params);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#selectByPeriod(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Schedule> exportSchedule(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectExportSchedule", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForMeetingRoom(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForMeetingRoom", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForCartooletc(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForCartooletc", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectMeetingRoomByReserveList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectMeetingRoomByReserveList", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectCartooletcByReserveList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectCartooletcByReserveList", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByFavorite(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForMeetingRoomByFavorite", params);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForCartooletcByFavorite(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForCartooletcByFavorite", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByRecent(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForMeetingRoomByRecent", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectByPeriodForCartooletcByRecent(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectByPeriodForCartooletcByRecent", params);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Schedule> getCompanyScheduleWaitList() {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectCompanyScheduleWaitList");
	}
	
	@SuppressWarnings("unchecked")
	public List<FavoriteTarget> selectFavoriteTeam(String userId){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectFavoriteTeam", userId);
	}
	
	@SuppressWarnings("unchecked")
	public List<FavoriteTarget> selectFavoriteUser(String userId){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectFavoriteUser", userId);
	}
	
	public void updateCompanyScheduleApprove(String[] scheduleIdList) {
		sqlDelete(NAMESPACE + ".updateCompanyScheduleApprove", scheduleIdList);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> portalQuickMenuCountAll(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".portalQuickMenuCountAll", params);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#searchSchedule(ScheduleSearchCondition)
	 */
	public int searchScheduleCount(ScheduleSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + ".searchScheduleCount", searchCondition);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ScheduleNewDao#searchSchedule(ScheduleSearchCondition)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchSchedule(ScheduleSearchCondition searchCondition) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".searchSchedule", searchCondition);
	}
	
	public void updateScheduleSyncData(ScheduleDetail scheduleDetail){
		this.sqlInsert(NAMESPACE + ".updateScheduleSyncData",  scheduleDetail);
	}
	
	@SuppressWarnings("unchecked")
	public List<Schedule> sapScheduleList() {
		return (List<Schedule>)this.sqlSelectForList(NAMESPACE + ".sapScheduleList");
	}
	
	public void insertSapSchedule(Schedule schedule){
		
		this.sqlInsert(NAMESPACE + ".insertSapSchedule",  schedule);
	}
	
	public void insertFavoriteTeam(Schedule schedule){
		
		this.sqlInsert(NAMESPACE + ".insertFavoriteTeam",  schedule);
	}
	
	public void insertFavoriteUser(Schedule schedule){
		
		this.sqlInsert(NAMESPACE + ".insertFavoriteUser",  schedule);
	}
	
	public void deleteFavoriteSetting(Schedule schedule) {
		sqlDelete(NAMESPACE + ".deleteFavoriteSetting", schedule);
	}
	
	public void updateScheduleSyncDataEnd(){
		this.sqlInsert(NAMESPACE + ".updateScheduleSyncDataEnd");
	}

}
