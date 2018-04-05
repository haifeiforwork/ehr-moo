package com.lgcns.ikep4.supportpack.activitystream.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.activitystream.service.ActivityCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ActivityCodeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ActivityCodeService activityCodeService;

	@Autowired
	private IdgenService idgenService;

	private ActivityCode activityCode;

	private String activityStreamId;

	User user;

	@Before
	public void setUp() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();

		user = new User();
		user.setUserId("user1");
		user.setUserName("사용자1");
		user.setLocaleCode("ko");

		session.setAttribute("ikep.user", user);
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		activityCode = new ActivityCode();
		activityCode.setUserId("user1");
		activityCode.setConfigCode("MAX_VAL");

	}

	@Test
	public void getAll() {

		List<ActivityCode> result = activityCodeService.select(user);
	}

	@Test
	public void selectConfig() {

		ActivityCode result = activityCodeService.selectConfig(activityCode);
	}

	@Test
	public void selectConfigList() {

		List<ActivityCode> result = activityCodeService.selectConfigList(activityCode);
	}
}
