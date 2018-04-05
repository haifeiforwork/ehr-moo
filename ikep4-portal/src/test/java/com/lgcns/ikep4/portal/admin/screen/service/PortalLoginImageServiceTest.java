package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;

public class PortalLoginImageServiceTest extends BaseServiceTestCase{
	
	private User user;	
	
	private String fileId;
	
	@Before
	public void setUp() {
		fileId = "LoginImage_Id";
		
		user = new User();
		user.setUserId("admin");
		user.setUserName("관리자");
	}
	
	@Autowired
	private PortalLoginImageService portalLoginImageService;
	
	@Autowired
	private PortalService portalService;
	
	@Test
	public void testUpdateLoginImage() throws Exception {
		
		Portal updatePortal = portalLoginImageService.updateLoginImage(fileId, user, "P000001");
		
		Portal result = portalService.readPortal("P000001");
		
		if(result.getLoginImageId() == null) {
			result.setLoginImageId("");
		}
		
		assertEquals(updatePortal.getLoginImageId(), result.getLoginImageId());
	}
}
