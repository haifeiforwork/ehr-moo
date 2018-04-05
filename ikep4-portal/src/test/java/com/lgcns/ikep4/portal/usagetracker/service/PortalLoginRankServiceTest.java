package com.lgcns.ikep4.portal.usagetracker.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalLoginRankDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;

public class PortalLoginRankServiceTest extends BaseServiceTestCase {

	private PortalLoginRank portalLoginRank;
	
	@Before
	public void setUp() {
		portalLoginRank = new PortalLoginRank();
		
		portalLoginRank.setUserId("admin");
		portalLoginRank.setPortalId("P000001");
		portalLoginRank.setIpAddress("127.0.0.1");
		portalLoginRank.setUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C)");
		portalLoginRank.setBrowser("Internet Explorer 8");
	}
	
	@Autowired
	private PortalLoginRankService portalLoginRankService;
	
	@Autowired
	private PortalLoginRankDao portalLoginRankDao;
	
	@Test
	public void testCreateLoginRank() {
		int usage = portalLoginRankService.createLoginLog(portalLoginRank);
		
		if(usage == 0) {
			PortalLoginRank result = portalLoginRankDao.getLoginLog(portalLoginRank.getLoginHistoryId());
			assertNotNull(result);
		}
	}
}
