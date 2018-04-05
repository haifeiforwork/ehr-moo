package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ReadGroup;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.ReadGroupService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class ReadGroupServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ReadGroupService readGroupService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	
	private ReadGroup readGroup;
	
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
		
		this.readGroup = new ReadGroup();
		
		this.readGroup.setCategoryId("FIRST_CATEGORY");
		this.readGroup.setReadGroupId("TESTER_GROUP"); 
		this.readGroup.setPortalId("POTAL_ID"); 
		this.readGroup.setReadGroupName("TESTER_GROUP"); 
		this.readGroup.setReadGroupEnglishName("TESTER_GROUP"); 
		
		readGroupService.create(readGroup);
	}
	
	@Test
	@Ignore
	public void testCreate() {
	}
	
	@Test
	public void testExists() {
		boolean exists = readGroupService.exists(this.readGroup); 
		
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
	public void testListReadGroup() {
		List<ReadGroup> result = readGroupService.listReadGroup("FIRST_CATEGORY", "POTAL_ID");
		
		Assert.assertNotNull(result);
	}
}
