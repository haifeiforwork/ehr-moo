package com.lgcns.ikep4.supportpack.searchpreprocessor.service;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.search.SpSearchCondition;
import com.lgcns.ikep4.support.searchpreprocessor.service.BatchLogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BatchLogServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	BatchLogService batchLogService;
	
	@Autowired
	private IdgenService idgenService;
	
	private  BatchLog batchLog = new BatchLog();
	
	@Before
	public void setUpBefore() {
		batchLog.setDescription("test batch");
		batchLog.setEndDate(new Date());
		batchLog.setId(idgenService.getNextId());
		batchLog.setName("test");
		batchLog.setResult("T");
		batchLog.setStartDate(new Date());
	}
	
	
	@Test
	public void listBySearchCondition() {
		
		batchLogService.create(batchLog);
		
		SpSearchCondition searchCondition = new SpSearchCondition();
		SearchResult<BatchLog> list = batchLogService.listBySearchCondition(searchCondition);
		
		Assert.assertNotNull(list);
	}
}
