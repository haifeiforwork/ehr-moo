package com.lgcns.ikep4.approval.work.service;


import static org.junit.Assert.assertNotNull;
import com.lgcns.ikep4.approval.admin.test.service.BaseServiceTestCase;
import com.lgcns.ikep4.wfapproval.work.service.ApProcessViewService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApProcessViewServiceTest  extends BaseServiceTestCase {

	private String apprId;

	@Autowired
	private ApProcessViewService apProcessViewService;
	
	
	@Before
	public void setUp() throws Exception {
		this.apprId = "100000691100";
	}
	
	@Test
	public void testGetApProcessXMLData() {
		//fail("Not yet implemented");
		String apProcessViewXml = apProcessViewService.getApProcessXMLData(apprId);
		assertNotNull(apProcessViewXml);
	}

}
