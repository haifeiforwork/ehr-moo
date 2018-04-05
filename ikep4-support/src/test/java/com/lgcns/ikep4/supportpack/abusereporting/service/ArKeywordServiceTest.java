package com.lgcns.ikep4.supportpack.abusereporting.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.support.abusereporting.model.ArKeyword;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * ArKeywordService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: ArKeywordServiceTest.java 16259 2011-08-18 05:40:01Z giljae $
 */

public class ArKeywordServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private ArKeywordService arKeywordService;
	
	private ArKeyword arKeyword;

	String pk = "";

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		user.setPortalId("P000001");
		session.setAttribute("ikep.user", user);
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		arKeyword = new ArKeyword();
		
		pk = "aabbccddee";
		arKeyword.setKeyword		(pk);
		arKeyword.setModuleCode		("MB");
		arKeyword.setRegisterId		(user.getUserId());
		arKeyword.setRegisterName	(user.getUserName());
		arKeyword.setPortalId		(user.getPortalId());

		arKeywordService.create(arKeyword);
	}

	@Test
	public void create() {
		ArKeyword returnArKeyword = arKeywordService.read(arKeyword);
		assertNotNull(returnArKeyword);	
	}

	@Test
	public void read() {
		ArKeyword returnArKeyword = arKeywordService.read(arKeyword);
		assertNotNull(returnArKeyword);	
	}

	@Test
	public void delete() {
		arKeyword.setKeywords(this.pk);
		arKeywordService.delete(arKeyword);
		ArKeyword returnArKeyword = this.arKeywordService.read(arKeyword);
		assertNull(returnArKeyword);
	}

	@Test
	public void list() {
		ArAbuseSearchCondition arAbuseSearchCondition = new ArAbuseSearchCondition();
		List<String> list = arKeywordService.list(arAbuseSearchCondition);
		assertNotNull(list);	
	}

}
