package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.WriteUser;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.WriteUserService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class WriteUserServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private WriteUserService writeUserService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	
	private WriteUser writeUser;
	
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
		
		this.writeUser = new WriteUser();
		
		this.writeUser.setCategoryId("FIRST_CATEGORY");
		this.writeUser.setWriteUserId("TESTER");
		this.writeUser.setWriteUserName("TESTER"); 
		this.writeUser.setWriteUserTeamName("TEAM"); 
		this.writeUser.setWriteUserJobTitleName("JOB"); 
		this.writeUser.setWriteUserEnglishName("TESTER"); 
		this.writeUser.setWriteUserTeamEnglishName("TEAM"); 
		this.writeUser.setWriteUserJobTitleEnglishName("JOB");
		
		writeUserService.create(writeUser);
	}
	
	@Test
	@Ignore
	public void testCreate() {
	}
	
	@Test
	public void testExists() {
		boolean exists = writeUserService.exists(this.writeUser); 
		
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
	public void testListWriteUser() {
		List<WriteUser> result = writeUserService.listWriteUser("FIRST_CATEGORY");
		
		Assert.assertNotNull(result);
	}	
	
	@Test
	public void testWriteUserYn() {
		String result = writeUserService.writeUserYn("TESTER");
		
		Assert.assertNotNull(result);
	}
}
