package com.lgcns.ikep4.util.ical;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.WeekDayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lgcns.ikep4.util.ical.ICalUtil;
import com.lgcns.ikep4.util.ical.model.IAlarm;
import com.lgcns.ikep4.util.ical.model.ICalendar;
import com.lgcns.ikep4.util.ical.model.IDur;
import com.lgcns.ikep4.util.ical.model.IEvent;
import com.lgcns.ikep4.util.ical.model.IRecur;


public class ICalTest {

	Log log = LogFactory.getLog(getClass());

	@Test
	public void dummyTest() throws Exception {
		log.debug("delete this");
	}

	public void setUp() {}

	public void read() throws Exception {

		String fileName = "ical4j_simple1.ics";

		InputStream inp = getClass().getResourceAsStream(fileName);

		ICalendar ical = ICalUtil.readICalendar(inp);

		log.debug(ical.toString());

	}

	@Test
	public void write() throws Exception {

		MockHttpServletResponse response = new MockHttpServletResponse();

		String fileName = "test.ics";

		// Calendar 셋팅
		ICalendar icalendar = new ICalendar();

		// Calendar의 Event 셋팅
		List<IEvent> ieventList = new ArrayList<IEvent>();
		IEvent ievent = new IEvent();

		ievent.setUid("1");
		ievent.setSummary("test 11");
		ievent.setDtstart(new DateTime());
		ievent.setDtend(new DateTime());

		//ievent.setOrganizer(new String("Mailto:B@example.com"));
		//ievent.addAttendeeList(new String("Mailto:B@example3.com"));
		//ievent.addAttendeeList(new String("CN='handul1한 과장 Hausys Child 1';RSVP=FALSE:mailto:null"));

		// Event의 반복설정 셋팅
		IRecur irecur = new IRecur();
		irecur.setFrequency(Recur.WEEKLY);
		irecur.setInterval(new Integer(1));

		WeekDayList dayList = new WeekDayList();
		dayList.add(WeekDay.SU);
		dayList.add(WeekDay.MO);
		irecur.setDayList(dayList);

		ievent.setIrecur(irecur);

		// Event의 알람 셋팅
		List<IAlarm> ialarmList = new ArrayList<IAlarm>();
		IAlarm ialarm = new IAlarm();

		ialarm.setAction("EMAIL");
		ialarm.setSummary("email noti...");
		ialarm.setDescription("email noti...");
		//ialarm.addAttendeeList(new String("Mailto:B@example5.com"));
		//ialarm.addAttendeeList(new String("CN='handul1한 과장 Hausys Child 1';RSVP=FALSE:mailto:null"));

		IDur idur = new IDur();
		idur.setDays(0);
		idur.setHours(3);
		idur.setMinutes(0);
		idur.setSeconds(0);

		ialarm.setIdur(idur);

		ialarmList.add(ialarm);

		ievent.setIalarmList(ialarmList);

		ieventList.add(ievent);

		icalendar.setIeventList(ieventList);

		// 파일 저장
		ICalUtil.saveICalendar(icalendar, fileName, response);

	}

}
