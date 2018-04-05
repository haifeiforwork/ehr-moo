package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Sociality;
import com.lgcns.ikep4.socialpack.socialanalyzer.search.SocialAnalyzerSearchCondition;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */          
public class SocialityServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private SocialityService socialityService;
	
	/////////////////////////////////////////////
	@Test
	public void testListSociality() {
		SearchResult<Sociality> result = socialityService.listSociality(new SocialAnalyzerSearchCondition());
		
		Assert.assertNotNull(result);
	}	
	@Test
	public void testGetMyRanking() {
		Integer result = socialityService.getMyRanking("user1");
		
		Assert.assertNotNull(result);
	}	
	@Test
	@Ignore
	public void testBatchSociality() {
		socialityService.batchSociality();
	}
}
