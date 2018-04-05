/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;


/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: WorkplaceListDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class WorkplaceListDaoTest extends BaseDaoTestCase {
	
	@Autowired
	private WorkplaceListDao workplaceListDao;
	
	private WorkplaceSearchCondition workplaceSearchCondition;
	private ProcessBean processBean;
	
	@Before
	public void setUp() {
		workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setQueryId("todoList");
		workplaceSearchCondition.setUserId("user1");
		
		processBean = new ProcessBean();
		processBean.setProcessId("APPROVAL_PROCESS");
	}

	@Test
	@Rollback(false)
	public void listBySearchCondition() {
		List<?> workItemList = this.workplaceListDao.listBySearchCondition(this.workplaceSearchCondition);  
		assertNotNull(workItemList);
	}

	@Test
	@Rollback(false)
	public void countBySearchCondition() {
		Integer count = this.workplaceListDao.countBySearchCondition(this.workplaceSearchCondition);
		assertNotNull(count);
	}
	
	@Test
	@Rollback(false)
	public void readProcInfo() {
		ProcessBean processBean = (ProcessBean)this.workplaceListDao.readProcInfo(this.processBean);
		assertNotNull(processBean);
	}
}