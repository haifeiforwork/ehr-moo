package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUser;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUserPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalUserService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class ApprovalUserServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ApprovalUserService approvalUserService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	
	private ApprovalUser approvalUser;
	
	private ApprovalUserPk approvalUserPk;
	
	@Before
	public void setUp() {
		ManualCategory manualCategory = new ManualCategory();
		manualCategory.setCategoryId("FIRST_CATEGORY"); 
		manualCategory.setCategoryName("FIRST_CATEGORY");
		manualCategory.setCategoryParentId("FIRST_CATEGORY");
		manualCategory.setReadPermission(0);
		manualCategory.setSortOrder(1); 
		manualCategory.setPortalId("POTAL_ID"); 
		manualCategory.setRegisterId("TESTER"); 
		manualCategory.setRegisterName("TESTER"); 
		manualCategory.setRegistDate(new Date());
		manualCategory.setChildCount(0);
		manualCategoryService.create(manualCategory);
		
		this.approvalUser = new ApprovalUser();
		
		this.approvalUser.setCategoryId("FIRST_CATEGORY");
		this.approvalUser.setApprovalLine(1);
		this.approvalUser.setApprovalUserId("TESTER"); 
		this.approvalUser.setApprovalUserName("TESTER"); 
		this.approvalUser.setApprovalUserTeamName("TEAM"); 
		this.approvalUser.setApprovalUserJobTitleName("JOB"); 
		this.approvalUser.setApprovalUserEnglishName("TESTER"); 
		this.approvalUser.setApprovalUserTeamEnglishName("TEAM"); 
		this.approvalUser.setApprovalUserJobTitleEnglishName("JOB"); 
		
		this.approvalUserPk = approvalUserService.create(this.approvalUser);
	}
	
	@Test
	public void testCreate() {
		ApprovalUser result = approvalUserService.read(this.approvalUserPk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = approvalUserService.exists(this.approvalUserPk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		ApprovalUser result = approvalUserService.read(this.approvalUserPk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		approvalUserService.delete(this.approvalUserPk);
		ApprovalUser result = approvalUserService.read(this.approvalUserPk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.approvalUser.setApprovalUserId("TESTER1");
		approvalUserService.update(this.approvalUser);
		
		ApprovalUser result = approvalUserService.read(this.approvalUserPk);
		
		Assert.assertEquals(this.approvalUser.getApprovalUserId(), result.getApprovalUserId());
	}
	
	/////////////////////////////////////////////
	
	@Test
	public void testListApprovalUser() {
		List<ApprovalUser> result = approvalUserService.listApprovalUser("FIRST_CATEGORY");
		
		Assert.assertNotNull(result);
	}	
	
	@Test
	public void testApprovalUserYn() {
		String result = approvalUserService.approvalUserYn("TESTER");
		
		Assert.assertNotNull(result);
	}	
}
