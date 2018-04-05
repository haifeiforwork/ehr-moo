package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityTypePk;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityTypeService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class SocialityTypeServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private SocialityTypeService socialityTypeService;
	
	private SocialityType socialityType;
	
	private SocialityTypePk socialityTypePk;
	
	@Before
	public void setUp() {
		this.socialityType = new SocialityType();
		this.socialityType.setTypeCode("X");  
		this.socialityType.setPortalId("P00001");
		this.socialityType.setTypeName("X test");    
		this.socialityType.setTypeWeight(10);       
		this.socialityType.setValidMonth(1);   
		this.socialityType.setRegisterId("user1");  
		this.socialityType.setRegisterName("user1");
		this.socialityType.setRegistDate(new Date());  
		this.socialityType.setUpdaterId("user1");   
		this.socialityType.setUpdaterName("user1"); 
		this.socialityType.setUpdateDate(new Date()); 
		
		this.socialityTypePk = socialityTypeService.create(this.socialityType);
	}
	
	@Test
	public void testCreate() {
		SocialityType result = socialityTypeService.read(this.socialityTypePk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = socialityTypeService.exists(this.socialityTypePk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		SocialityType result = socialityTypeService.read(this.socialityTypePk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		socialityTypeService.delete(this.socialityTypePk);
		SocialityType result = socialityTypeService.read(this.socialityTypePk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.socialityType.setValidMonth(2);
		socialityTypeService.update(this.socialityType);
		
		SocialityType result = socialityTypeService.read(this.socialityTypePk);
		
		Assert.assertEquals(this.socialityType.getValidMonth(), result.getValidMonth());
	}
	
	/////////////////////////////////////////////
}
