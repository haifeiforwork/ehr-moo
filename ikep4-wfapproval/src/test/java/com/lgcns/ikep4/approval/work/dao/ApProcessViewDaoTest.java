package com.lgcns.ikep4.approval.work.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.approval.admin.test.dao.BaseDaoTestCase;
import com.lgcns.ikep4.wfapproval.work.dao.ApProcessViewDao;
import com.lgcns.ikep4.wfapproval.work.model.ApProcessViewData;


public class ApProcessViewDaoTest extends  BaseDaoTestCase  {
	
	@Autowired
	private ApProcessViewDao apProcessViewDao;
	
	private String apprId;

	
	@Before
	public void setUp() throws Exception {
		this.apprId = "100000691100";
	}
	
	@Test
	public void testListApProcessViewData() {
		List<ApProcessViewData> apProcessViewDataList = (List<ApProcessViewData>)apProcessViewDao.listApProcessViewData(this.apprId);
		assertNotNull(apProcessViewDataList);
	}

}
