package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;

public class PortalPageLayoutServiceTest extends BaseServiceTestCase{

	private String pageId;
	
	private String ownerId;
	
	@Before
	public void setUp() {
		pageId = "100000126387";
		ownerId = "user7777";
	}
	
	@Autowired
	private PortalPageLayoutService portalPageLayoutService;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	//페이지 리셋
	@Test
	public void removePortletReset() {
		portalPageLayoutService.removePortletReset(pageId, ownerId);
		
		List<PortalPageLayout> result = portalPageLayoutDao.listOwnerPageLayout(pageId, ownerId);
		
		assertEquals(result.size(), 0);
	}
}
