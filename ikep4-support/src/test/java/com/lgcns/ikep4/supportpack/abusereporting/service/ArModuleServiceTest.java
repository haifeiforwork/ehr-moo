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

import com.lgcns.ikep4.support.abusereporting.model.ArModule;
import com.lgcns.ikep4.support.abusereporting.service.ArModuleService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * ArModuleService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: ArModuleServiceTest.java 16259 2011-08-18 05:40:01Z giljae $
 */

public class ArModuleServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private ArModuleService arModuleService;
	
	private ArModule arModule;

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

		arModule = new ArModule();
		
		pk = "UT";
			
		arModule.setModuleCode(pk);
		arModule.setModuleName("Unit Test");
		arModule.setRegisterId(user.getUserId());
		arModule.setRegisterName(user.getUserName());
		arModule.setUpdaterId(user.getUserId());
		arModule.setUpdaterName(user.getUserName());

		arModuleService.create(arModule);
	}

	@Test
	public void create() {
		ArModule arModule = arModuleService.read(this.pk);
		assertNotNull(arModule);	
	}

	@Test
	public void read() {
		ArModule arModule = arModuleService.read(this.pk);
		assertNotNull(arModule);	
		
	}

	@Test
	public void delete() {
		arModuleService.delete(this.pk);
		ArModule arModule = this.arModuleService.read(this.pk);
		assertNull(arModule);
	}

	@Test
	public void list() {
		List<ArModule> list = this.arModuleService.list();
		assertNotNull(list);
	}

}
