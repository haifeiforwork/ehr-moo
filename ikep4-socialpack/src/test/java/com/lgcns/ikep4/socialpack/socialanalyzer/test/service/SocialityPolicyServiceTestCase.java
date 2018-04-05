package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityPolicy;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityPolicyService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class SocialityPolicyServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private SocialityPolicyService socialityPolicyService;
	
	private SocialityPolicy socialityPolicy;
	
	private String pk;
	
	@Before
	public void setUp() {
		this.socialityPolicy = new SocialityPolicy();
		
		this.socialityPolicy.setPortalId("P00001");
		this.socialityPolicy.setiFollowerWeight(1);     
		this.socialityPolicy.setiRetweetWeight(1);      
		this.socialityPolicy.setiTweetWeight(1);        
		this.socialityPolicy.setiBlogVisitWeight(1);    
		this.socialityPolicy.setiMaxValue(1);           
		this.socialityPolicy.setfFollowerWeight(1);     
		this.socialityPolicy.setfFollowingWeight(1);    
		this.socialityPolicy.setfBothFollowingWeight(1);
		this.socialityPolicy.setfMblogGroupWeight(1);   
		this.socialityPolicy.setfGuestbookWeight(1);    
		this.socialityPolicy.setfMaxValue(1);  
		this.socialityPolicy.setRegisterId("user1"); 
		this.socialityPolicy.setRegisterName("user1"); 
		this.socialityPolicy.setRegistDate(new Date());
		this.socialityPolicy.setUpdaterId("user1"); 
		this.socialityPolicy.setUpdaterName("user1");  
		this.socialityPolicy.setUpdateDate(new Date());
		this.socialityPolicy.setiProfileVisitWeight(1); 
		
		this.pk = socialityPolicyService.create(this.socialityPolicy);
	}
	
	@Test
	public void testCreate() {
		SocialityPolicy result = socialityPolicyService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = socialityPolicyService.exists(this.pk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		SocialityPolicy result = socialityPolicyService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		socialityPolicyService.delete(this.pk);
		SocialityPolicy result = socialityPolicyService.read(this.pk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.socialityPolicy.setiFollowerWeight(2);
		socialityPolicyService.update(this.socialityPolicy);
		
		SocialityPolicy result = socialityPolicyService.read(this.pk);
		
		Assert.assertEquals(this.socialityPolicy.getiFollowerWeight(), result.getiFollowerWeight());
	}
	
	/////////////////////////////////////////////
}
