package com.lgcns.ikep4.servicepack.seamlessmessage.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageAdmin;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessmessageAdminService;

public class SeamlessmessageAdminServiceTest extends BaseServiceTestCase {

	@Autowired
	private SeamlessmessageAdminService seamlessmessageAdminService;
	
	@Before
	public void setUp() throws Exception {}

	@Test
	public void testCreateSeamlessmessageAdmin() {
		SeamlessmessageAdmin seamlessmessageAdmin = new SeamlessmessageAdmin();
		seamlessmessageAdmin.setPortalId("P000001");
		seamlessmessageAdmin.setMaxImapCount(100);
		this.seamlessmessageAdminService.create(seamlessmessageAdmin);
		SeamlessmessageAdmin result = this.seamlessmessageAdminService.read("P000001");
		assertNotNull(result);
	}

}
