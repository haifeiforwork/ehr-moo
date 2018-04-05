package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationPolicy;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationPolicyService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class RelationPolicyServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private RelationPolicyService relationPolicyService;
	
	private RelationPolicy relationPolicy;
	
	private String pk;
	
	@Before
	public void setUp() {
		this.relationPolicy = new RelationPolicy();
		
		this.relationPolicy.setPortalId("P00001");
		this.relationPolicy.setdTeamMemberWeight(1);  
		this.relationPolicy.setdTeamLeaderWeight(1);  
		this.relationPolicy.setdFavoriteWeight(1);    
		this.relationPolicy.setdFollowingWeight(1);  
		this.relationPolicy.setdFollowerWeight(1);    
		this.relationPolicy.setdBothFollowingWeight(1); 
		this.relationPolicy.setcMailWeight(1);          
		this.relationPolicy.setcMailMax(1);             
		this.relationPolicy.setcMessageMax(1);          
		this.relationPolicy.setcMessageWeight(1);       
		this.relationPolicy.setcSmsMax(1);              
		this.relationPolicy.setcSmsWeight(1);           
		this.relationPolicy.setcGuestbookMax(1);        
		this.relationPolicy.setcGuestbookWeight(1);     
		this.relationPolicy.setfFollowerWeight(1);      
		this.relationPolicy.setfFollowingWeight(1);     
		this.relationPolicy.setfBothFollowingWeight(1); 
		this.relationPolicy.setfMyLinereplyMax(1);      
		this.relationPolicy.setfMyLinereplyWeight(1);   
		this.relationPolicy.setfMyMentionMax(1);        
		this.relationPolicy.setfMyMentionWeight(1);     
		this.relationPolicy.setfYourLinereplyMax(1);    
		this.relationPolicy.setfYourLinereplyWeight(1); 
		this.relationPolicy.setfYourMentionMax(1);      
		this.relationPolicy.setfYourMentionWeight(1);   
		this.relationPolicy.setfBothMbloggroupMax(1);   
		this.relationPolicy.setfBothMbloggroupWeight(1);
		this.relationPolicy.seteExpertTagMax(1);       
		this.relationPolicy.seteExpertTagWeight(1);     
		this.relationPolicy.seteInterestTagMax(1);  
		this.relationPolicy.seteInterestTagWeight(1);   
		this.relationPolicy.seteDocTagMax(1);     
		this.relationPolicy.seteDocTagWeight(1);        
		this.relationPolicy.seteBothAnswerMax(1);      
		this.relationPolicy.seteBothAnswerWeight(1);
		this.relationPolicy.setRegisterId("user1"); 
		this.relationPolicy.setRegisterName("user1"); 
		this.relationPolicy.setRegistDate(new Date());
		this.relationPolicy.setUpdaterId("user1"); 
		this.relationPolicy.setUpdaterName("user1");  
		this.relationPolicy.setUpdateDate(new Date());

		
		this.pk = relationPolicyService.create(this.relationPolicy);
	}
	
	@Test
	public void testCreate() {
		RelationPolicy result = relationPolicyService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = relationPolicyService.exists(this.pk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		RelationPolicy result = relationPolicyService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		relationPolicyService.delete(this.pk);
		RelationPolicy result = relationPolicyService.read(this.pk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.relationPolicy.setdTeamMemberWeight(2);
		relationPolicyService.update(this.relationPolicy);
		
		RelationPolicy result = relationPolicyService.read(this.pk);
		
		Assert.assertEquals(this.relationPolicy.getdTeamMemberWeight(), result.getdTeamMemberWeight());
	}
	
	/////////////////////////////////////////////
}
