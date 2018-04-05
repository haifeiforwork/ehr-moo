package com.lgcns.ikep4.socialpack.peopleconnection.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.peopleconnection.model.ExternalExpert;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * PeopleConnectionService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: PeopleConnectionServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class PeopleConnectionServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private PeopleConnectionService peopleConnectionService;
	

	String pk = "";

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		session.setAttribute("ikep.user", user);
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

	}

	@Test
	public void listByProfileIdList() {
		List<String> externalExpertProfileIdList = new ArrayList<String>();
		List<ExternalExpert> list = this.peopleConnectionService.listByProfileIdList(externalExpertProfileIdList);
	}

}
