package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ReadUser;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.ReadUserService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class ReadUserServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ReadUserService readUserService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	
	private ReadUser readUser;
	
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
		
		this.readUser = new ReadUser();
		
		this.readUser.setCategoryId("FIRST_CATEGORY");
		this.readUser.setReadUserId("TESTER"); 
		this.readUser.setReadUserName("TESTER"); 
		this.readUser.setReadUserTeamName("TEAM"); 
		this.readUser.setReadUserJobTitleName("JOB"); 
		this.readUser.setReadUserEnglishName("TESTER"); 
		this.readUser.setReadUserTeamEnglishName("TEAM"); 
		this.readUser.setReadUserJobTitleEnglishName("JOB");
		
		readUserService.create(readUser);
	}
	
	@Test
	@Ignore
	public void testCreate() {
	}
	
	@Test
	public void testExists() {
		boolean exists = readUserService.exists(this.readUser); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	@Ignore
	public void testRead() {
	}
	
	@Test
	@Ignore
	public void testDelete() {
	}
	
	@Test
	@Ignore
	public void testUpdate() {
	}
	
	/////////////////////////////////////////////
	
	@Test
	public void testListReadUser() {
		List<ReadUser> result = readUserService.listReadUser("FIRST_CATEGORY");
		
		Assert.assertNotNull(result);
	}
}
