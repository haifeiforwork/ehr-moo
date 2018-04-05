package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class ManualCategoryServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ManualCategoryService manualCategoryService;
	
	private ManualCategory manualCategory;
	
	private String pk;
	
	@Before
	public void setUp() {
		this.manualCategory = new ManualCategory();

		this.manualCategory.setCategoryId("FIRST_CATEGORY"); 
		this.manualCategory.setCategoryName("FIRST_CATEGORY");
		this.manualCategory.setCategoryParentId("FIRST_CATEGORY");
		this.manualCategory.setReadPermission(0);
		this.manualCategory.setSortOrder(1); 
		this.manualCategory.setPortalId("POTAL_ID"); 
		this.manualCategory.setRegisterId("TESTER"); 
		this.manualCategory.setRegisterName("TESTER"); 
		this.manualCategory.setRegistDate(new Date());
		this.manualCategory.setChildCount(0);
		this.pk = manualCategoryService.create(this.manualCategory);
		
		this.manualCategory.setCategoryId("SECOND_CATEGORY"); 
		this.manualCategory.setCategoryName("SECOND_CATEGORY");
		this.manualCategory.setCategoryParentId("FIRST_CATEGORY");
		manualCategoryService.create(this.manualCategory);
		
		this.manualCategory.setCategoryId("THIRD_CATEGORY"); 
		this.manualCategory.setCategoryName("THIRD_CATEGORY");
		this.manualCategory.setCategoryParentId("SECOND_CATEGORY");
		manualCategoryService.create(this.manualCategory);
	}
	
	@Test
	public void testCreate() {
		ManualCategory result = manualCategoryService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = manualCategoryService.exists(this.pk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		ManualCategory result = manualCategoryService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		manualCategoryService.delete(this.pk);
		ManualCategory result = manualCategoryService.read(this.pk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.manualCategory = manualCategoryService.read(this.pk);
		this.manualCategory.setCategoryName("FIRST_CATEGORIES");
		manualCategoryService.update(this.manualCategory);
		ManualCategory result = manualCategoryService.read(this.pk);
		
		Assert.assertEquals(this.manualCategory.getCategoryName(), result.getCategoryName());
	}
	
	/////////////////////////////////////////////
	@Test
	public void testListTopCategory() {
		User user = new User();
		user.setUserId("TESTER");
		user.setUserName("TESTER");
		user.setPortalId("POTAL_ID");
		String result = manualCategoryService.listTopCategory(user);

		Assert.assertNotNull(result);
	}	
	@Test
	public void testListChildCategory() {
		List<TreeNode> result = manualCategoryService.listChildCategory("SECOND_CATEGORY");
		
		Assert.assertNotNull(result);
	}		
	@Test
	public void testMoveCategory() {
		manualCategoryService.moveCategory("THIRD_CATEGORY", "FIRST_CATEGORY", "1");
		ManualCategory result = manualCategoryService.read("THIRD_CATEGORY");
		
		Assert.assertNotSame(result.getSortOrder(), "1");
	}			
	@Test
	public void testGetManualCategory() {
		ManualCategory result = manualCategoryService.getManualCategory("THIRD_CATEGORY", "POTAL_ID");
		
		Assert.assertNotNull(result);
	}				
	@Test
	public void testListManualCategoryByPortalId() {
		List<ManualCategory> result = manualCategoryService.listManualCategoryByPortalId("POTAL_ID");
		
		Assert.assertNotNull(result);
	}			
	@Test
	public void testCreateManualCategory() {
		ManualCategory manualCategory1 = new ManualCategory();
		manualCategory1.setCategoryId("FOURTH_CATEGORY"); 
		manualCategory1.setCategoryName("FOURTH_CATEGORY");
		manualCategory1.setCategoryParentId("SECOND_CATEGORY");
		manualCategory1.setReadPermission(0);
		manualCategory1.setSortOrder(2); 
		manualCategory1.setPortalId("POTAL_ID"); 
		manualCategory1.setRegisterId("TESTER"); 
		manualCategory1.setRegisterName("TESTER"); 
		manualCategory1.setRegistDate(new Date());
		manualCategory1.setChildCount(0);
		
		String result = manualCategoryService.createManualCategory(manualCategory1, "", "", "TESTER", "");
		
		Assert.assertNotNull(result);
	}			
	@Test
	public void testUpdateManualCategory() {
		this.manualCategory = manualCategoryService.read(this.pk);
		this.manualCategory.setCategoryName("FIRST_CATEGORIES");
		manualCategoryService.updateManualCategory(this.manualCategory, "", "", "TESTER", "");
		ManualCategory result = manualCategoryService.read(this.pk);
		
		Assert.assertEquals(this.manualCategory.getCategoryName(), result.getCategoryName());
	}			
	@Test
	public void testDeleteManualCategory() {
		manualCategoryService.deleteManualCategory("THIRD_CATEGORY", "POTAL_ID");
		ManualCategory result = manualCategoryService.read("THIRD_CATEGORY");
		
		Assert.assertNull(result);
	}
}
