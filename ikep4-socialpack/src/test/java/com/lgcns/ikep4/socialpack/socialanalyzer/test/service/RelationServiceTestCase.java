package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.socialpack.socialanalyzer.model.Relation;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */          
public class RelationServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private RelationService relationService;
	
	/////////////////////////////////////////////
	@Test
	public void testListPerson() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", "user1");
		map.put("type", "D");
		List<Relation> result = relationService.listPerson(map);
		
		Assert.assertNotNull(result);
	}	
	@Test
	public void testListGroup() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", "user1");
		map.put("type", "D");
		List<Relation> result = relationService.listGroup(map);
		
		Assert.assertNotNull(result);
	}		
	@Test
	@Ignore
	public void testBatchRelation() {
		relationService.batchRelation();
	}
}
