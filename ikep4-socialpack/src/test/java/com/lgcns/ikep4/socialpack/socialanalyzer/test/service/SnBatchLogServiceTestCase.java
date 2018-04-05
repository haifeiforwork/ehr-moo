package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.socialpack.socialanalyzer.model.BatchLog;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SnBatchLogService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */          
public class SnBatchLogServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private SnBatchLogService snBatchLogService;
	
	/////////////////////////////////////////////
	@Test
	public void testListBatchLog() {
		List<BatchLog> result = snBatchLogService.listBatchLog("201105");
		
		Assert.assertNotNull(result);
	}	
}
