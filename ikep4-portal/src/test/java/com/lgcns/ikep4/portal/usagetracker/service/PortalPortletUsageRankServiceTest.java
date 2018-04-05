package com.lgcns.ikep4.portal.usagetracker.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalPortletUsageRankDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalPortletUsageRank;

public class PortalPortletUsageRankServiceTest extends BaseServiceTestCase {

	private PortalPortletUsageRank portalPortletUsageRank;
	
	@Before
	public void setUp() {
		portalPortletUsageRank = new PortalPortletUsageRank();
		
		portalPortletUsageRank.setPortletId("1");
		portalPortletUsageRank.setOwnerId("admin");
		portalPortletUsageRank.setAction("0");
		portalPortletUsageRank.setPortalId("P000001");
		portalPortletUsageRank.setRegisterId("admin");
		portalPortletUsageRank.setRegisterName("관리자");
	}
	
	@Autowired
	private PortalPortletUsageRankService portalPortletUsageRankService;
	
	@Autowired
	private PortalPortletUsageRankDao portalPortletUsageRankDao;
	
	@Test
	public void testCreatePortletLog() {
		int usage = portalPortletUsageRankService.createPortletLog(portalPortletUsageRank);
		
		if(usage == 0) {
			PortalPortletUsageRank result = portalPortletUsageRankDao.getPortletLog(portalPortletUsageRank.getPortletHistoryId());
			assertNotNull(result);
		}
	}
}
