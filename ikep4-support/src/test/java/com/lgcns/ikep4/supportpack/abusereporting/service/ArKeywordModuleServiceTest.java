package com.lgcns.ikep4.supportpack.abusereporting.service;

import static org.junit.Assert.assertNotNull;

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
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * ArKeywordModuleService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: ArKeywordModuleServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */

public class ArKeywordModuleServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;

	
	@Autowired
	private ArKeywordService arKeywordService;
	
	@Autowired
	private ArKeywordModuleService arKeywordModuleService;
	
	private ArKeywordModule arKeywordModule;
	
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

		ArKeyword arKeyword = new ArKeyword();
		
		pk = "bbbbbbdddxxs";
		
		arKeyword.setKeyword(pk);
		arKeyword.setModuleCode("MB");
		arKeyword.setRegisterId(user.getUserId());
		arKeyword.setRegisterName(user.getUserName());
		arKeyword.setPortalId(user.getPortalId());

		arKeywordService.create(arKeyword);

		arKeywordModule = new ArKeywordModule();
		
		arKeywordModule.setKeyword(pk);
		arKeywordModule.setModuleCode("MB");
		arKeywordModule.setPortalId(user.getPortalId());
	}

	@Test
	public void listByKeyword() {
		List<ArModule> list = arKeywordModuleService.listByKeyword(arKeywordModule);
		assertNotNull(list);	
	}

	@Test
	public void listByModuleCode() {
		List<String> list = arKeywordModuleService.listByModuleCode(arKeywordModule);
		assertNotNull(list);	
	}

	@Test
	public void prohibitWordCacheReload() {
		arKeywordModuleService.prohibitWordCacheReload(user.getPortalId());
	}

}
