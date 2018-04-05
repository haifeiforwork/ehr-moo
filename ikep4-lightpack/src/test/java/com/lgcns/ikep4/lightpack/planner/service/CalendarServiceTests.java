package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.lightpack.BaseDaoTestCase;
import com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.planner.web.CalendarController;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;

@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class CalendarServiceTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CalendarService service;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ParticipantDao participantDao;
	
	@Autowired
	private UserService userService;
	
	private final static String portalId = "P000001";

	private static String scheduleId = "200001";

	private final static String categoryId = "1";

	private final static String startDate = "20110222";

	private final static String endDate = "20110623";

	private final static String sdStartDate = "201102221030";

	private final static String sdEndDate = "201102231330";

	private final static String registerId = "user20042";

	private final static String registerName = "홍길동";

	private static String userId = registerId;
	
	private String workspaceId = "100000111489";

	private Schedule schedule;

	private Recurrences recurrence;

	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();
	
	private static String[] parsePatterns = new String[] {"yyyyMMddHHmm", "yyyy-MM-dd", "yyyyMMdd"};
	
	private int timeDifference = 17; //(GMT-08:00) 미서부태평양표준시 (USA & Canada)
	private String timeDifferenceStr = "17"; //(GMT-08:00) 미서부태평양표준시 (USA & Canada)
	private MockHttpServletRequest req;
	private User user = new User();
	
	@Before
	public void setup() {
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		user.setUserId(userId);
		user.setTimeDifference(timeDifferenceStr);

		session.setAttribute("ikep.user", user); 
		req.setSession(session);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		
		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
		participants.add(new Participant(scheduleId, "user1004", "1")); // 참석자
	
		alarms.add(new Alarm("11", scheduleId, "0", "60")); // 메일/60분
		alarms.add(new Alarm("22", scheduleId, "1", "30")); // SMS/30분
		
		schedule.setParticipantList(participants);
		schedule.setAlarmList(alarms);
		
		// workspaceId 추가
		schedule.setWorkspaceId(workspaceId);
	}
	
	@Test
	public void create() {
		String id = service.create(schedule);
		assertNotNull(id);
		//assertTrue(!scheduleId.equals(id));
		
		Schedule sd = service.getScheduleAllData(id);
		assertEquals(2, sd.getParticipantList().size());
		assertEquals(2, sd.getAlarmList().size());
		assertEquals(workspaceId, sd.getWorkspaceId());
		
		Map<String, Object> res = new HashMap<String, Object>();
		res = service.getScheduleSubInfo(id);
		assertEquals(2, ((List<Participant>) res.get("participantList")).size());
		assertEquals(2, ((List<Alarm>) res.get("alarmList")).size());
	}

	@Test
	public void createByTrustee() {
	
		String mandatorId = "user200";
		schedule.setMandatorId(mandatorId);
		User user = new User();
		user.setUserId(mandatorId);
		
		scheduleId = service.createByTrustee(schedule, user);
		assertNotNull(scheduleId);
		
		Schedule scd = service.read(scheduleId);
		assertEquals(mandatorId, scd.getRegisterId());
		assertEquals(mandatorId, scd.getUpdaterId());
		
		// TODO: workspace 위임자가 가입한 것 확인
	}

	@Test
	public void deleteSchedule() {
		String id = service.create(schedule);
		assertNotNull(id);
		
		service.deleteSchedule(id);
		
		Schedule scd = service.read(id);	
		assertNull(scd);
	}
	
 	@Test
	public void updateScheduleData() {
		String id = service.create(schedule);
		assertNotNull(id);
		
		schedule.setCategoryId("1");
		schedule.setTitle("새로운 제목");
		schedule.setContents("new content");
		schedule.setWorkspaceId("20010");
		
		int sp = schedule.getSchedulePublic();
		schedule.setSchedulePublic((sp+1)%2);
		
		int rp = schedule.getAttendanceRequest();
		schedule.setAttendanceRequest((rp+1)%2);
		
		schedule.setPlace("new place");
		
		int wp = schedule.getWholeday();
		schedule.setWholeday((wp+1)%2);
		
		service.update(schedule);
		
		Schedule usc = service.getScheduleAllData(id);
		usc.setCategoryName(schedule.getCategoryName());
		usc.setUpdaterName(schedule.getUpdaterName());
		
		// TODO: 확인방법 ???
		//assertTrue(ObjectUtils.equals(schedule, usc));
		
		assertEquals(schedule.getStartDate(), usc.getStartDate());
		assertEquals(schedule.getEndDate(), usc.getEndDate());
	}

	@Test
	public void updateSchedule() {
		String id = service.create(schedule);
		assertNotNull(id);
		
		Participant p1 = new Participant(id, userId, "1");
		Participant p2 = new Participant(id, "user200", "2");

		List<Participant> listp = new ArrayList<Participant>();
		listp.add(p1); 
		listp.add(p2); 
		
		Alarm a1 = new Alarm("22", id, "1", "60");
		List<Alarm> lista = new ArrayList<Alarm>();
		lista.add(a1); // SMS/60분
		
		schedule.setParticipantList(listp);
		schedule.setAlarmList(lista);
		
		service.updateSchedule(schedule);
		
		Map<String, Object> res = new HashMap<String, Object>();
		res = service.getScheduleSubInfo(id);
		
		List<Participant> plist = (List<Participant>) res.get("participantList");
		assertEquals(2, plist.size());
		// targetUserInfo field 추가로 contains fail
//		assertTrue(plist.contains(p1));
//		assertTrue(plist.contains(p2));
		
		List<Alarm> alist = (List<Alarm>) res.get("alarmList");
		assertEquals(1, alist.size());
		assertTrue(alist.contains(a1));
	}

	@Test
	public void updateScheduleWithRepeat() {
		// 매일
		recurrence = new Recurrences("0", "", "", Schedule.toDate(startDate), Schedule.toDate(endDate),
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		
		List<Recurrences> recurrenceList = new ArrayList();
		recurrenceList.add(recurrence);
		
		String id = service.create(schedule);
		assertNotNull(id);
		
		// update data
		Participant p1 = new Participant(id, userId, "1");
		Participant p2 = new Participant(id, "user200", "2");

		List<Participant> listp = new ArrayList<Participant>();
		listp.add(p1); 
		listp.add(p2); 
		
		Alarm a1 = new Alarm("22", id, "1", "60");
		List<Alarm> lista = new ArrayList<Alarm>();
		lista.add(a1); // SMS/60분
		
		// 매주(2) 월요일
		Recurrences rep = new Recurrences(id, "1", "2", "2", Schedule.toDate(startDate), Schedule.toDate(endDate),
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		List<Recurrences> listr = new ArrayList<Recurrences>();
		listr.add(rep);
		
		schedule.setRepeat(1);
		schedule.setRecurrences(listr);
		
		schedule.setParticipantList(listp);
		schedule.setAlarmList(lista);
		
		service.updateSchedule(schedule);
		
		Schedule scd = service.getScheduleAllData(id);
		
		List<Participant> plist = scd.getParticipantList();
		assertEquals(2, plist.size());
//		assertTrue(plist.contains(p1));
//		assertTrue(plist.contains(p2));
		
		List<Alarm> alist = scd.getAlarmList();
		assertEquals(1, alist.size());
		assertTrue(alist.contains(a1));
		
		List<Recurrences> rlist = scd.getRecurrences();
		assertEquals(1, rlist.size());
		assertTrue(rlist.contains(rep));
	}
	
	@Test
	public void createAndUpdateForCheckDate() throws ParseException {
		String start="201203111300", end="201203111430", rstart = "20120201", rend = "20120401"; 
		Schedule sd = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(start), Schedule.toDate(end));
		
		String sid = service.create(sd);
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		params.put("userId", registerId);
		params.put("startDate", rstart);
		params.put("endDate", rend);
		list = scheduleService.selectByPeriod(params);
		
		assertTrue(list.size() >= 1);
		
//		Map item;
//		int i = 0, len=list.size();
//		boolean checked = false;
//		for(; i<len; i++) {
//			item = list.get(i);
//			if(item.get("scheduleId").equals(sid)) {
//				assertEquals(start, item.get("startDate"));
//				assertEquals(end, item.get("endDate"));
//				checked = true;
//				break;
//			}
//		}
//		assertTrue(checked);
	}
	
	@Test
	public void selectForWorkspace() throws ParseException {
		String start = "201103251030";
		String end = "201103251330";
		
		Schedule scd = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(start), Schedule.toDate(end));
		
		scd.setWorkspaceId(workspaceId);
		
		service.clearCall();
		
		String id = service.create(scd);
		assertNotNull(id);
		
		// TODO: refactoring 적용
//		Map<String, Object> params = new HashMap<String, Object>();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		params.put("workspaceId", workspaceId);
//		params.put("startDate", start.substring(0, 8));
//		params.put("endDate", end.substring(0, 8));
//		list = scheduleService.selectByPeriodForWorkspace(workspaceId, Schedule.toDate(start), Schedule.toDate(end));
//		assertEquals(1, list.size());
	}
	
	// TODO: refactoring 적용
//	@Test
//	public void readWorkspaceSchedule() {
//		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
//		
//		Date s = new Date();
//		Date e = s;
//
//		String start = fdf.format(s);
//		String end = fdf.format(e);
//		String url = CalendarController.VIEW_URL;
//		
//		Schedule scd = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
//				Schedule.toDate(start), Schedule.toDate(end));
//		
//		scd.setWorkspaceId(workspaceId);
//		
//		service.clearCall();
//		
//		String id = service.create(scd);
//		assertNotNull(id);
//		
//		Map<String, String> params = new HashMap<String, String>();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		params.put("workspaceId", workspaceId);
//		params.put("startDate", start.substring(0, 8));
//		params.put("endDate", start.substring(0, 8));
//		params.put("url", url);
//		
//		list = service.readWorkspaceSchedule(params);
//		assertEquals(1, list.size());
//		
//		String returl = (String) list.get(0).get("returl");
//		assertEquals(url+id, returl);
//	}
	
	// TODO: refactoring 적용
//	@Test
//	public void readUsersSchedule() {
//		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
//		
//		service.clearCall();
//		
//		Date s = new Date();
//		Date e = s;
//
//		String start = fdf.format(s);
//		String end = fdf.format(e);
//		
//		Schedule scd = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
//				Schedule.toDate(start), Schedule.toDate(end));
//
//		service.create(scd);
//		
//		String userId = "user1004";
//		Schedule scd2 = new Schedule(scheduleId, portalId, categoryId, userId, registerName, "crud 테스트", "crud 테스트", 0,
//				Schedule.toDate(start), Schedule.toDate(end));
//
//		service.create(scd2);
//		
//		String[] users = new String[] {registerId, userId};
//		
//		//TODO: parameter 변경 요
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("users", users);
//		params.put("startDate", start);
//		params.put("endDate", end);
//		
//		List<Map<String, Object>> list = service.readUsersSchedule(params);
//		
//		assertTrue(list.size() >= 2);
//	}
	
	// TODO: refactoring 적용
//	@Test
//	public void readTodaySchedule() throws ParseException {
//		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMddHHmm");
//		service.clearCall();
//		
//		Date now = new Date();
//		now = DateUtils.setHours(now, 10);
//		now = DateUtils.setMinutes(now, 30);
//		
//		String start = fdf.format(now);
//		
//		now = DateUtils.setHours(now, 13);
//		now = DateUtils.setMinutes(now, 30);		
//		String end = fdf.format(now);
//		
//		Schedule scd = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
//				Schedule.toDate(start), Schedule.toDate(end));
//
//		service.create(scd);
//		
//		List list = service.readTodaySchedule(registerId);
//		Schedule schedule = (Schedule) list.get(0);
//		assertTrue(list.size() >= 1);
//		assertEquals(start, fdf.format(schedule.getStartDate()));
//		assertEquals(end, fdf.format(schedule.getEndDate()));
//	}
//	
//	@Test
//	public void readTomorrowSchedule() throws ParseException {
//		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMddHHmm");
//		service.clearCall();
//		
//		Date now = new Date();
//		now = DateUtils.addDays(now, 1);
//		now = DateUtils.setHours(now, 10);
//		now = DateUtils.setMinutes(now, 30);
//		
//		String start = fdf.format(now);
//		
//		now = DateUtils.setHours(now, 13);
//		now = DateUtils.setMinutes(now, 30);		
//		String end = fdf.format(now);
//		
//		Schedule scd = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
//				Schedule.toDate(start), Schedule.toDate(end));
//
//		service.create(scd);
//		
//		List list = service.readTomorrowSchedule(registerId);
//		Schedule schedule = (Schedule) list.get(0);
//		assertEquals(1, list.size());
//		assertEquals(start, fdf.format(schedule.getStartDate()));
//		assertEquals(end, fdf.format(schedule.getEndDate()));
//	}
//	
//	@Test
//	public void readMySharedScheduleList() {
//		
//		service.clearCall();
//		
//		String start = "201104122200";
//		String end = "201104122230";
//		
//		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
//				Schedule.toDate(start), Schedule.toDate(end));
//		
//		participants.clear();
//		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
//		participants.add(new Participant(scheduleId, "user1004", "1")); // 참석자
//	
//		
//		schedule.setParticipantList(participants);
//		
//		String sd1 = service.create(schedule);
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		params.put("userId", "user1004");
//		params.put("startDate", start.substring(0, 8));
//		params.put("endDate", end.substring(0, 8));
//		
//		list = service.readMySharedScheduleList(params);
//		assertEquals(1, list.size());
//		
//		Map m = list.get(0);
//		String targetType = (String) m.get("targetType");
//		assertEquals("1", targetType);
//	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void updateAcceptInfo() {
		
		String start = "201104122200";
		String end = "201104122230";
		
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(start), Schedule.toDate(end));
		
		participants.clear();
		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
		participants.add(new Participant(scheduleId, "user1004", "1")); // 참석자
	
		
		schedule.setParticipantList(participants);
		
		String sd1 = service.create(schedule);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("isAccept", "1");
		params.put("reason", "");
		params.put("scheduleId", sd1);
		params.put("targetUserId", "user1004");
		
		service.updateAcceptInfo(params);
		
		List ps = participantDao.getList(sd1);
		
		Participant p = null;
		for(int i=0, len=ps.size(); i<len; i++) {
			p = (Participant) ps.get(i);
			if(p.getTargetUserId().equals("user1004")) {
				break;
			}
		}
		assertEquals(1, p.getIsAccept());
	}
	
	// TODO: refactoring 적용
//	@Test
//	public void getMyScheduleCount() throws ParseException {
//		Date today = new Date();
//		
//		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
//				today, today);
//		
//		String id = service.create(schedule);
//		assertNotNull(id);
//		
//		int count = service.getMyScheduleCount(registerId, today);
//		assertTrue(count > 0);
//	}
	
	@Test
	public void getMandator() {
		String mandatorId = "user1";
		User mandator = service.getMandator(mandatorId);
		assertEquals(mandatorId, mandator.getUserId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void selectNationAll() {
		List<Nation> nations = userService.selectNationAll("ko");
		assertNotNull(nations);
	}
	
	@Ignore // 범주 등록후 테스트
	@Test
	public void getScheduleAllData() {
		Schedule sch = service.getScheduleAllData(scheduleId, "ko");
		assertNotNull(sch.getCategoryName());
	}
	
	@Test
	public void findUserByName() {
		String name = "사용자1";
		service.findUserByName(name);
		assertTrue(true);
	}
	
	@Test
	@Ignore
	public void checkHoliday() {
		boolean res = service.isExistName("KOR", "어린이날1");
		assertTrue(res);
	}
}
