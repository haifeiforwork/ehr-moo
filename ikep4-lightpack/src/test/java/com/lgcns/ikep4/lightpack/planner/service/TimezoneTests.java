/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: TimezoneTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class TimezoneTests extends AbstractTransactionalJUnit4SpringContextTests {
	private int timeDifference = 17; //(GMT-08:00) 미서부태평양표준시 (USA & Canada)
	private String timeDifferenceStr = "17"; //(GMT-08:00) 미서부태평양표준시 (USA & Canada)
	private MockHttpServletRequest req;
	private User user = new User();
	
	@Autowired
    private TimeZoneSupportService ts;
	
	@Before
	public void setup() {
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		user.setUserId("user2108");
		user.setTimeDifference(timeDifferenceStr);

		session.setAttribute("ikep.user", user); 
		req.setSession(session);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
	}
	
	@Test
	public void allDay() {
		// Client -> server -> Client
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 5, 14); // 6.14
		
		Date clientDate = c.getTime();
		
		Date currentClientDate = ts.convertTimeZone();
		Date addedClientDate = Utils.addDateTime(clientDate, currentClientDate);

		Date savedDate = ts.convertServerTimeZone(addedClientDate);
		/////////////////////////////////////////////////////
		
		
		Date currentServerDate = ts.currentServerTime();
		Date addedDate = Utils.addDateTime(savedDate, currentServerDate);
		Date readDate = ts.convertTimeZone(addedDate);
		readDate = DateUtils.truncate(readDate, Calendar.DATE);
		
		assertTrue(clientDate.compareTo(readDate) == 0);
	}
	
	@Test
	public void toServertimezone() {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 5, 14, 10, 30); // 6.14 10:30
		Date clientDate = c.getTime();

		Date savedDate = ts.convertServerTimeZone(clientDate);
		
		Date readDate = ts.convertTimeZone(savedDate);
		
		assertTrue(clientDate.compareTo(readDate) == 0);
	}
}
