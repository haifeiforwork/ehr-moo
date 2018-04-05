package com.lgcns.ikep4.socialpack.microblogging.service;

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
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 
 * AddonService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: AddonServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class AddonServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private AddonService addonService;
	
	private Addon addon;

	String pk = "";
	String displayCode = "";
	String sourceLink = "";

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		user.setPortalId("P000001");
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		addon = new Addon();

		pk = StringUtil.randomStr(Constant.ADDON_RANDOMSTR_SIZE) + addonService.getSeq();
		displayCode = "{url:"+pk+"}";
		sourceLink = "http://ikep.lgcns.com:9999/ikep4-webapp";

		addon.setAddonCode(pk);
		addon.setAddonType("0");
		addon.setDisplayCode(displayCode);
		addon.setSourceLink(sourceLink);

		addonService.create(addon);
	}

	@Test
	public void create() {
		Addon addon = addonService.read(this.pk);
		assertNotNull(addon);	
	}

	@Test
	public void read() {
		Addon addon = addonService.read(this.pk);
		assertNotNull(addon);	
		
	}

	@Test
	public void selectBySourceLink() {
		Addon addon = addonService.selectBySourceLink(this.sourceLink);
		assertNotNull(addon);	
	}

	@Test
	public void selectByDisplayCode() {
		Addon addon = addonService.selectByDisplayCode(this.displayCode);
		assertNotNull(addon);	
	}

	@Test
	public void getSeq() {
		int seq = addonService.getSeq();
		assertTrue(-1 < seq);
	}

	@Test
	public void delete() {
		addonService.delete(this.pk);
		Addon addon = this.addonService.read(this.pk);
		assertNull(addon);
	}

}
