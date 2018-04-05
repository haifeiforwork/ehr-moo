/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.engine.model.DelegateBean;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: ProcStatisticsServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ProcStatisticsServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private ProcStatisticsService procStatisticsService;
	
	private WorkplaceSearchCondition workplaceSearchCondition;
	
	@Before
	public void setUp(){
		
		workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setProcessId("APPROVAL_PROCESS");
	}
	
	@Test
	@Rollback(false)
	public void delegateDetail() {
		assertNotNull((ProcessBean)procStatisticsService.procStatisticsDetail(this.workplaceSearchCondition));
	}
	
	@Test
	@Rollback(false)
	public void procStatisticsRunningCount() {
		assertNotNull(procStatisticsService.procStatisticsRunningCount(this.workplaceSearchCondition));
	}
	
	@Test
	@Rollback(false)
	public void procStatisticsCompleteCount() {
		assertNotNull(procStatisticsService.procStatisticsCompleteCount(this.workplaceSearchCondition));
	}
}