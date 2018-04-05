package com.lgcns.ikep4.supportpack.recent.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;
import com.lgcns.ikep4.support.recent.service.RecentService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class RecentServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private RecentService recentService;

	@Autowired
	private IdgenService idgenService;

	private Recent Recent;

	private String RecentId;

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

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.getAllForDocument(searchCondition);
	}

	@Test
	public void getAllForPeople() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.getAllForPeople(searchCondition);
	}

	@Test
	public void getSummaryForDocument() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.getSummaryForDocument(searchCondition);
	}

	@Test
	public void getSummaryForPeople() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.getSummaryForPeople(searchCondition);
	}

	@Test
	public void selectCollaboration() {

		Map map = new HashMap();

		List<Recent> result = recentService.selectCollaboration(map);
	}
	
	@Test
	public void selectCafe() {

		Map map = new HashMap();

		List<Recent> result = recentService.selectCafe(map);
	}
	
	@Test
	public void selectMicroblog() {

		Map map = new HashMap();

		List<Recent> result = recentService.selectMicroblog(map);
	}
	
	@Test
	public void selectFollower() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.selectFollower(searchCondition);
	}
	
	@Test
	public void selectFollowing() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.selectFollowing(searchCondition);
	}
	
	@Test
	public void selectIntimate() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.selectIntimate(searchCondition);
	}
	
	@Test
	public void selectCollaborationMember() {

		RecentSearchCondition searchCondition = new RecentSearchCondition();

		SearchResult<Recent> result = recentService.selectCollaborationMember(searchCondition);
	}

}
