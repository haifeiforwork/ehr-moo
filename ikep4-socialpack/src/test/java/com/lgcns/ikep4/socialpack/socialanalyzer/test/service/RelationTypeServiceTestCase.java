package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationTypePk;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationTypeService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class RelationTypeServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private RelationTypeService relationTypeService;
	
	private RelationType relationType;
	
	private RelationTypePk relationTypePk;
	
	@Before
	public void setUp() {
		this.relationType = new RelationType();
		
		this.relationType.setPortalId("P00001");
		this.relationType.setTypeCode("X");
		this.relationType.setTypeName("X Test");
		this.relationType.setTypeWeight(1);
		this.relationType.setValidMonth(1);
		this.relationType.setRegisterId("user1"); 
		this.relationType.setRegisterName("user1"); 
		this.relationType.setRegistDate(new Date());
		this.relationType.setUpdaterId("user1"); 
		this.relationType.setUpdaterName("user1");  
		this.relationType.setUpdateDate(new Date());

		this.relationTypePk = relationTypeService.create(this.relationType);
	}
	
	@Test
	public void testCreate() {
		RelationType result = relationTypeService.read(this.relationTypePk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = relationTypeService.exists(this.relationTypePk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		RelationType result = relationTypeService.read(this.relationTypePk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		relationTypeService.delete(this.relationTypePk);
		RelationType result = relationTypeService.read(this.relationTypePk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.relationType.setTypeWeight(2);
		relationTypeService.update(this.relationType);
		
		RelationType result = relationTypeService.read(this.relationTypePk);
		
		Assert.assertEquals(this.relationType.getTypeWeight(), result.getTypeWeight());
	}
	
	/////////////////////////////////////////////
}
