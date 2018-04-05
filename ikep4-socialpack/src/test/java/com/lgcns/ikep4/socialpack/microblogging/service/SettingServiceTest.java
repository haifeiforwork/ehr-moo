package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.model.Search;
import com.lgcns.ikep4.socialpack.microblogging.model.Setting;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 
 * SettingService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: SettingServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SettingServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private SettingService settingService;
	
	private Setting setting;

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

		setting = new Setting();

		setting.setUserId(user.getUserId());
		setting.setMaxFeedCount(200);
		setting.setMaxFavoriteCount(200);
		setting.setFeedsAtATime(10);
		setting.setUserId(user.getUserId());
		setting.setUpdaterId(user.getUserId());
		
		if(!settingService.exists(user.getUserId())){
			settingService.create(setting);
		}
	}

	@Test
	public void create() {
		Setting setting = settingService.read(user.getUserId());
		assertNotNull(setting);	
	}

	@Test
	public void read() {
		Setting setting = settingService.read(user.getUserId());
		assertNotNull(setting);	
		
	}

	@Test
	public void exists() {
		boolean exist = settingService.exists(user.getUserId());
		assertTrue(exist);			
	}
	
	@Test
	public void delete() {
		settingService.delete(user.getUserId());
		Setting setting = this.settingService.read(user.getUserId());
		assertNull(setting);
	}

	@Test
	public void update() {
		setting.setMaxFavoriteCount(300);
		settingService.update(setting);
		Setting returnSetting = this.settingService.read(user.getUserId());
		assertEquals(300, returnSetting.getMaxFavoriteCount());	
	}
	
}
