package com.lgcns.ikep4.approval.work.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.approval.admin.test.service.BaseServiceTestCase;
import com.lgcns.ikep4.wfapproval.work.dao.ApFormPortletDao;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;

public class ApFormPortletServiceTest extends BaseServiceTestCase {

	@Autowired
	private ApFormPortletDao apFormPortletDao;
	
	private ApFormPortlet apFormPortlet;
	
	@Before
	public void setUp() throws Exception {
		apFormPortlet = new ApFormPortlet();
		
		apFormPortlet.setFormId("111111111111");
		apFormPortlet.setUserId("user1");
		
		this.apFormPortletDao.create(apFormPortlet);
	}

	@Test
	public void testListApFormPortlet() {
		apFormPortlet.setRowCount(3);
		
		List<ApFormPortlet> result = this.apFormPortletDao.listApFormPortlet(apFormPortlet);
		assertFalse(result.isEmpty());
	}

	@Test
	public void testAddApFormPortlet() {
		if(this.apFormPortletDao.exists(apFormPortlet)){
			this.apFormPortletDao.update(apFormPortlet);
			List<ApFormPortlet> result = this.apFormPortletDao.listApFormPortlet(apFormPortlet);
			assertTrue(result.get(0).getUseCount().equals(2));
		}else{
			
		}
	}

}
