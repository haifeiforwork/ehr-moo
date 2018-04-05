package com.lgcns.ikep4.approval.work.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.approval.admin.test.dao.BaseDaoTestCase;
import com.lgcns.ikep4.wfapproval.work.dao.ApFormPortletDao;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;


/**
 * TODO Javadoc주석작성
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormPortletDaoTest.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApFormPortletDaoTest extends BaseDaoTestCase {

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
	public void testExistsApFormPortlet() {
		assertTrue(this.apFormPortletDao.exists(apFormPortlet));
	}

	@Test
	public void testCreate() {
		apFormPortlet.setRowCount(3);
		
		List<ApFormPortlet> result = this.apFormPortletDao.listApFormPortlet(apFormPortlet);
		assertFalse(result.isEmpty());
	}

	@Test
	public void testUpdate() {
		apFormPortlet.setUseCount(3);
		this.apFormPortletDao.update(apFormPortlet);
		List<ApFormPortlet> result = this.apFormPortletDao.listApFormPortlet(apFormPortlet);
		
		assertTrue(result.get(0).getUseCount().equals(2));
	}

}
