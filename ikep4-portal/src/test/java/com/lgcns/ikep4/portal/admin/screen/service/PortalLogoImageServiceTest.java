package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;

public class PortalLogoImageServiceTest extends BaseServiceTestCase {
	
	private User user;	
	
	private String fileId;
	
	@Before
	public void setUp() {
		fileId = "LogoImage_Id";
		
		user = new User();
		user.setUserId("admin");
		user.setUserName("관리자");
	}
	
	@Autowired
	private PortalLogoImageService portalLoginImageService;
	
	@Autowired
	private PortalService portalService;
	
	@Test
	public void testUpdateLogoImage() throws Exception {
		
		Portal updatePortal = portalLoginImageService.updateLogoImage(fileId, user, "P000001");
		
		Portal result = portalService.readPortal("P000001");
		
		if(result.getLogoImageId() == null) {
			result.setLogoImageId("");
		}
		
		assertEquals(updatePortal.getLogoImageId(), result.getLogoImageId());
	}
}
