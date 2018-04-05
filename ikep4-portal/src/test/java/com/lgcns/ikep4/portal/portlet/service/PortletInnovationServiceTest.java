package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.portlet.model.PortletInnovation;
import com.lgcns.ikep4.support.user.member.model.User;

public class PortletInnovationServiceTest extends BaseServiceTestCase {
	
	private User user;
	
	private PortletInnovation portletInnovation; 
	
	private List<MultipartFile> fileList;
	
	private PortalPortletConfig portalPortletConfig;
	
	private PortalPageLayout portalPageLayout;
	
	@Before
	public void setUp() {
		fileList = new ArrayList<MultipartFile>();
		
		user = new User();
		user.setUserId("admin");
		user.setUserName("관리자");
		
		portalPageLayout = new PortalPageLayout();
		portalPageLayout.setPageLayoutId("1");
		portalPageLayout.setPageId("100000126387");
		portalPageLayout.setLayoutId("100000000393");
		portalPageLayout.setOwnerId("admin");
		portalPageLayout.setColIndex(1);
		portalPageLayout.setWidth(32.5);
		portalPageLayout.setRegisterId("admin");
		portalPageLayout.setRegisterName("관리자");
		portalPageLayout.setUpdaterId("admin");
		portalPageLayout.setUpdaterName("관리자");
		
		
		portalPortletConfig = new PortalPortletConfig();
		portalPortletConfig.setPortletConfigId("1");
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setPageLayoutId("1");
		portalPortletConfig.setPortletId("100000715295");
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("admin");
		portalPortletConfig.setRegisterName("관리자");
		portalPortletConfig.setUpdaterId("admin");
		portalPortletConfig.setUpdaterName("관리자");
		
		
		portletInnovation = new PortletInnovation();
		portletInnovation.setPortletConfigId("1");
		portletInnovation.setImageFileId("1");
		portletInnovation.setUrl("/aaa.do");
		portletInnovation.setTarget("INNER");
		portletInnovation.setRegisterId("admin");
		portletInnovation.setRegisterName("관리자");
		portletInnovation.setUpdaterId("admin");
		portletInnovation.setUpdaterName("관리자");
	}
	
	@Autowired
	private PortletInnovationService portletInnovationService;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Test
	public void testCreatePortletInnovation() throws Exception {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		portletInnovationService.createPortletInnovation(portletInnovation, "0", user);
		PortletInnovation result = portletInnovationService.readPortletInnovation(portletInnovation.getPortletConfigId());
		
		assertNotNull(result);
	}

	@Test
	public void testReadPortletInnovation() throws Exception {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		portletInnovationService.createPortletInnovation(portletInnovation, "0", user);
		PortletInnovation result = portletInnovationService.readPortletInnovation(portletInnovation.getPortletConfigId());
		
		assertNotNull(result);
	}
}
