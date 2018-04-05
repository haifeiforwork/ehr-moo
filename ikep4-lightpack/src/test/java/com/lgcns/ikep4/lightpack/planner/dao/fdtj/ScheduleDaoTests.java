/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.fdtj;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;

/**
 * TODO Javadoc주석작성
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: ScheduleDaoTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class ScheduleDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private ParticipantDao participantDao;

	@Autowired
	private AlarmDao alarmDao;

	private final static String portalId = "P000001";

	private final static String scheduleId = "200003";

	private final static String categoryId = "1";

	private final static String startDate = "20110222";

	private final static String endDate = "20110623";

	private final static String sdStartDate = "201102221030";

	private final static String sdEndDate = "201102231330";

	private final static String registerId = "user1";

	private final static String registerName = "사용자1";

	private static String userId = registerId;

	private Schedule schedule;

	private Recurrences recurrences;

	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();
	
	private boolean isInitialized = false;

	@Before
	public void init() {
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		recurrences = new Recurrences(scheduleId, "0", "", "", Schedule.toDate(startDate), Schedule.toDate(endDate),
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		
		scheduleDao.createSchedule(schedule);
		scheduleDao.createRecurrences(recurrences);
		participantDao.create(participants);
		alarmDao.create(alarms);
		
		isInitialized = true;
	}

	public void selectByPeriod() {
		Map<String, Object> params = new HashMap<String, Object>();
//		
//		Schedule sd = scheduleDao.selectByPeriod(params);
//		assertEquals(scheduleId, sd.getScheduleId());
//		String sdt = DateFormatUtils.format(sd.getRecurrences().get(0).getSdStartDate(), "yyyyMMddHHmm");
//		assertEquals(sdStartDate, sdt);
	}
}
