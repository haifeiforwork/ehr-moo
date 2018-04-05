package com.lgcns.ikep4.supportpack.user.userTheme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.supportpack.user.member.service.BaseServiceTestCase;

public class UserThemeServiceTest extends BaseServiceTestCase {
	
	UserTheme userTheme;
	
	UserTheme updateUserTheme;
	
	@Before
	public void setUp() {
		userTheme = new UserTheme();
		userTheme.setThemeId("100000640004");
		userTheme.setUserId("user1234");
		userTheme.setThemeName("테스트 테마");
		userTheme.setDescription("테스트 테마");
		userTheme.setPortalId("P000001");
		userTheme.setDefaultOption(0);
		userTheme.setCssPath("theme01");
		userTheme.setPreviewImageId("img_theme_01.gif");
		userTheme.setRegisterId("user1234");
		userTheme.setRegisterName("사용자1234");
		userTheme.setUpdaterId("user1234");
		userTheme.setUpdaterName("사용자1234");
		
		updateUserTheme = new UserTheme();
		updateUserTheme.setThemeId("100000640011");
		updateUserTheme.setUserId("user1234");
		updateUserTheme.setThemeName("test 테스트 테마");
		updateUserTheme.setDescription("test 테스트 테마");
		updateUserTheme.setPortalId("P000001");
		updateUserTheme.setDefaultOption(0);
		updateUserTheme.setCssPath("theme01");
		updateUserTheme.setPreviewImageId("img_theme_01.gif");
		updateUserTheme.setRegisterId("user1234");
		updateUserTheme.setRegisterName("사용자1234");
		updateUserTheme.setUpdaterId("user1234");
		updateUserTheme.setUpdaterName("사용자1234");
	}
	
	@Autowired
	private UserThemeService userThemeService;
	
	@Test
	public void testReadUserTheme() {
		if(userThemeService.readUserTheme(updateUserTheme.getUserId()) == null) {
			userThemeService.createUserTheme(userTheme);
		}
		
		UserTheme result = userThemeService.readUserTheme(userTheme.getUserId());
		
		assertNotNull(result);
	}

	@Test
	public void testCreateUserTheme() {
		if(userThemeService.readUserTheme(updateUserTheme.getUserId()) == null) {
			userThemeService.createUserTheme(userTheme);
		}
		
		UserTheme result = userThemeService.readUserTheme(userTheme.getUserId());
		
		assertNotNull(result);
	}

	@Test
	public void testUpdateUserTheme() {
		if(userThemeService.readUserTheme(updateUserTheme.getUserId()) == null) {
			userThemeService.createUserTheme(userTheme);
		}
		
		userThemeService.updateUserTheme(updateUserTheme);
		UserTheme result = userThemeService.readUserTheme(updateUserTheme.getUserId());
		
		assertEquals(updateUserTheme.getThemeId(), result.getThemeId());
	}
	
	@Test
	public void testReadTheme() {
		UserTheme result = userThemeService.readTheme(userTheme.getThemeId(), userTheme.getPortalId());
		
		assertNotNull(result);
	}
	
	@Test
	public void testReadDefaultTheme() {
		UserTheme result = userThemeService.readDefaultTheme(userTheme.getPortalId());
		
		assertNotNull(result);
	}
}
