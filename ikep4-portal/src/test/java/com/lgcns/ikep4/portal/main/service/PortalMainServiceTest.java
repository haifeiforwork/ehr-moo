package com.lgcns.ikep4.portal.main.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.support.user.member.model.User;

public class PortalMainServiceTest extends BaseServiceTestCase {
	
	private User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUserId("admin");
		user.setUserName("관리자");
	}
	
	@Autowired
	private PortalMainService portalMainService;
	
	@Test
	public void testReadPortletMain() {
		PortalMain result = portalMainService.readPortletMain(user.getUserId(), "ko", "100000126387", user);
		
		assertNotNull(result);
	}
}
