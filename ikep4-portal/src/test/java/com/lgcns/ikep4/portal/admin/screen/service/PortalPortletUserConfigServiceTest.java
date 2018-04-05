/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포틀릿 사용자별 설정 테스트 케이스
 *
 * @author 한승환
 * @version $Id: PortalPortletUserConfigServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPortletUserConfigServiceTest  extends BaseServiceTestCase {

	@Autowired
	PortalPortletUserConfigService portalPortletUserConfigService;

	private MockHttpServletRequest req; // HttpServletRequest Mock을 선언합니다.
	
	String portletConfigId;
	
	String userDefinePropertyName = "TEST_P";
	
	@Autowired
	PortalPortletConfigService portalPortletConfigService;
	
	@Autowired
	PortalPortletDao portalPortletDao;
	
	@Autowired
	PortalPageLayoutDao portalPageLayoutDao;
	
	@Autowired
	PortalPageDao portalPageDao;
	
	@Before
	public void setUp() throws Exception {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		
		String systemCode = "S000001";
		String portalId = "P000001";
		String localeCode = "ko";
		
		//페이지 아이디 조회
		String pageId = portalPageDao.getPageId("S000001");
		//페이지 레이아웃 조회
		List<PortalPageLayout> layoutList = portalPageLayoutDao.listPageLayout(pageId);
		 
		String pageLayoutId = layoutList.get(0).getPageLayoutId();
		
		PortalPortletConfig portalPortletConfig = new PortalPortletConfig();
		
		portalPortletConfig.setPageLayoutId(pageLayoutId);
		portalPortletConfig.setPortletId("100000713658");
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("testuser");
		portalPortletConfig.setRegisterName("테스터1");
		portalPortletConfig.setUpdaterId("testuser");
		portalPortletConfig.setUpdaterName("테스터1");

		
		
		portalPortletConfigService.createPortletConfig(portalPortletConfig, systemCode, portalId, localeCode);
		
		List<PortalPortlet> pageLayoutPortletList = portalPortletDao.listPageLayoutPortlet(portalPortletConfig.getPageLayoutId(), systemCode, localeCode, "portletName");
		
		//포틀릿 ConfigId 조회
		portletConfigId = pageLayoutPortletList.get(0).getPortletConfigId();
		
		
		
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
		user.setUserId("testuser");
		user.setUserName("테스터1");
		
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	        // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
	}


	@After
	public void tearDown() throws Exception {
		
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#setListCount(java.lang.String, int)}.
	 */
	@Test
	public void testSetListCount() {
		portalPortletUserConfigService.setListSize(portletConfigId,3);
		int listCount = portalPortletUserConfigService.readListSize(portletConfigId);
		Assert.assertEquals(3, listCount);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#setPortletUserConfig(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetPortletUserConfig() {
		portalPortletUserConfigService.setPortletUserConfig(portletConfigId,userDefinePropertyName,"5");
		String propertyValue = portalPortletUserConfigService.readPortalPortletUserConfig(portletConfigId,userDefinePropertyName);
		assertEquals(propertyValue,"5");
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#readListCount(java.lang.String)}.
	 */
	@Test
	public void testReadListCount() {
		portalPortletUserConfigService.setListSize(portletConfigId,3);
		int listCount = portalPortletUserConfigService.readListSize(portletConfigId);
		Assert.assertEquals(3, listCount);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#readPortalPortletUserConfig(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadPortalPortletUserConfig() {
		portalPortletUserConfigService.setPortletUserConfig(portletConfigId,userDefinePropertyName,"5");
		String propertyValue = portalPortletUserConfigService.readPortalPortletUserConfig(portletConfigId,userDefinePropertyName);
		assertEquals(propertyValue,"5");
	}
}
