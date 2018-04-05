/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletFixture;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * PortalPortletService 테스트 케이스
 *
 * @author 한승환
 * @version $Id: PortalPortletServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPortletServiceTest  extends BaseServiceTestCase {


	@Autowired
	private PortalPortletService portalPortletService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	private PortalPortlet portalPortlet;

	private AdminSearchCondition searchCondition;
	
	private String portletId;
	
	private MockHttpServletRequest req; // HttpServletRequest Mock을 선언합니다.
	
	final String CLASS_NAME = "Portal-Portlet";
	
	//final String RESOURCE_NAME = "testresourceName";
	
	final String OPERATION_NAME = "READ";

	
	@Before
	public void setUp() throws Exception {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
		user.setUserId("testuser");
		user.setUserName("테스터1"); 
		

		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	        // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		final String generatedId = idgenService.getNextId();
		
		this.portalPortlet = PortalPortletFixture.fixturePortalPortlet(generatedId);
		this.portalPortlet.setPortletType("JSR168");
		
		
		
		PortalSecurity security = new PortalSecurity();
		
		// 리소스의 타입을 입력한다.
		security.setClassName(CLASS_NAME);
		// 리소스에 대한 설명을 입력한다.
		security.setResourceDescription(OPERATION_NAME);
		// 오퍼레이션 이름을 입력한다.
		security.setOperationName("READ");
		
		security.setOwnerName("admin");
		
		security.setOwnerId("admin");
		
		security.setAddrUserListAll("admin");
	
		ACLResourcePermission aclResourcePermissionRead = new ACLResourcePermission();
		
		aclResourcePermissionRead.setOpen(0);

		security.setAclResourcePermissionRead(aclResourcePermissionRead);
		
		this.portalPortlet.setSecurity(security);
		
		String portalId = "P000001";
		
		this.portletId = portalPortletService.createPortalPortlet(this.portalPortlet);
		this.searchCondition = new AdminSearchCondition();
		this.searchCondition.setStartRowIndex(1);
		this.searchCondition.setStartRowIndex(10);
		this.searchCondition.setPortalId(portalId);
	}

	@After
	public void tearDown() throws Exception {}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService#readPortalPortlet(java.lang.String)}.
	 */
	@Test
	public void testReadPortalPortlet() {
		PortalPortlet result = portalPortletService.readPortalPortlet(this.portletId);
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService#deletePortalPortlet(java.lang.String)}.
	 */
	@Test
	public void testDeletePortalPortlet() {
		int result = portalPortletService.deletePortalPortlet(this.portletId);
		Assert.assertTrue(result > 0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService#createPortalPortlet(com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet)}.
	 */
	@Test
	public void testCreatePortalPortlet() {
		PortalPortlet result = portalPortletService.readPortalPortlet(this.portletId);
		Assert.assertEquals(this.portletId,result.getPortletId());
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService#updatePortalPortlet(com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet)}.
	 */
	@Test
	public void testUpdatePortalPortlet() {
		
		
		portalPortlet.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
				portletId, "portletName,portletDesc"));
		portalPortlet.setPortletId(portletId);
		portalPortlet.getSecurity().setResourceName(portletId);
		portalPortlet.getSecurity().setAddrUserListAll("user1");
		
		ACLResourcePermission aclResourcePermissionReadTemp = new ACLResourcePermission();
		
		aclResourcePermissionReadTemp.setOpen(0);

		portalPortlet.getSecurity().setAclResourcePermissionRead(aclResourcePermissionReadTemp);
		
		int result = portalPortletService.updatePortalPortlet(portalPortlet);
		Assert.assertTrue(result > 0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService#listPortalPortletByCondition(com.lgcns.ikep4.framework.web.SearchCondition)}.
	 */
	@Test
	public void testListPortalPortletByCondition() {
		SearchResult<PortalPortlet> result = portalPortletService.listPortalPortletByCondition(searchCondition);
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService#convertXmlToPortalPortlet(com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet, org.springframework.web.multipart.commons.CommonsMultipartFile, java.lang.String)}.
	 */
	@Ignore ("file 을 이용한 .war 파일 압축해제 및 파싱. file 객체를 가져 올 수 없음.")
	@Test
	public void testConvertXmlToPortalPortlet() {

	}

}
