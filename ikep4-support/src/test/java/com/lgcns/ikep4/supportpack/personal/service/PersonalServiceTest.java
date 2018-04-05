package com.lgcns.ikep4.supportpack.personal.service;

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

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.support.personal.service.PersonalService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class PersonalServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PersonalService PersonalService;

	@Autowired
	private IdgenService idgenService;

	private Personal Personal;

	private String PersonalId;

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

	}

	@Test
	public void getAllForDocument() {

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();
		searchCondition.setPagePerRecord(10);
		
		SearchResult<Personal> result = PersonalService.getAllForDocument(searchCondition);
	}
	
	@Test
	public void getAllForFile() {

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();
		searchCondition.setPagePerRecord(10);
		
		SearchResult<Personal> result = PersonalService.getAllForFile(searchCondition);
	}
	
	@Test
	public void getAllForComment() {

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();
		searchCondition.setPagePerRecord(10);
		
		SearchResult<Personal> result = PersonalService.getAllForComment(searchCondition);
	}

}
