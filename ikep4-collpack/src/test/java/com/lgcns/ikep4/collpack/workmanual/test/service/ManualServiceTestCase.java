package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualVersionService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class ManualServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ManualService manualService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	@Autowired
	private ManualVersionService manualVersionService;
	
	private Manual manual;
	
	private String pk;
	
	@Before
	public void setUp() {
		ManualCategory manualCategory = new ManualCategory();
		manualCategory.setCategoryId("FIRST_CATEGORY"); 
		manualCategory.setCategoryName("FIRST_CATEGORY");
		manualCategory.setCategoryParentId("FIRST_CATEGORY");
		manualCategory.setReadPermission(0);
		manualCategory.setSortOrder(1); 
		manualCategory.setPortalId("POTAL_ID"); 
		manualCategory.setRegisterId("user1"); 
		manualCategory.setRegisterName("user1"); 
		manualCategory.setRegistDate(new Date());
		manualCategory.setChildCount(0);
		manualCategoryService.create(manualCategory);
		
		this.manual = new Manual();
		
		this.manual.setManualId("0000000000001");           
		this.manual.setCategoryId("FIRST_CATEGORY");         
		this.manual.setManualType("A");
		this.manual.setVersion((float) 0.1);            
		this.manual.setTitle("TEST Title");              
		this.manual.setContents("TEST Contents");           
		this.manual.setAttachCount(0);       
		this.manual.setHitCount(0);          
		this.manual.setLinereplyCount(0);    
		this.manual.setPortalId("POTAL_ID");           
		this.manual.setRegisterId("user1");   
		this.manual.setRegisterName("user1");      
		this.manual.setRegistDate(new Date());        
		this.manual.setUpdaterId("user1");      
		this.manual.setUpdaterName("user1");       
		this.manual.setUpdateDate(new Date());             
		this.manual.setIndexRowNum(3);       
		this.manual.setJobTitleName("JOB");       
		//this.manual.setTagList();            
		this.manual.setUpdaterEnglishName("user1");    
		this.manual.setJobTitleEnglishName("JOB");

		this.pk = manualService.create(this.manual);
		
		ManualVersion manualVersion = new ManualVersion();
		manualVersion.setVersionId("00000000000001");
		manualVersion.setManualId("0000000000001");
		manualVersion.setVersion((float) 0.1);
		manualVersion.setVersionTitle("TEST Title");              
		manualVersion.setVersionContents("TEST Contents");           
		manualVersion.setVersionAttachCount(0);
		manualVersion.setIsPublic(0);
		manualVersion.setUpdateReason("");
		manualVersion.setApprovalStatus("A");
		manualVersion.setPortalId("POTAL_ID");
		manualVersion.setRegisterId("user1");   
		manualVersion.setRegisterName("user1");   
		manualVersion.setRegistDate(new Date());
		manualVersion.setIsAbolition(0); 
		//manualVersion.setTag();
		manualVersion.setManualType("S");
		manualVersion.setIndexRowNum(10);
		//manualVersion.setFileLinkList();
		//manualVersion.setFileDataList();
		manualVersion.setRegisterEnglishName("user1");
		manualVersionService.create(manualVersion);
	}
	
	@Test
	@Ignore
	public void testCreate() {
	}
	
	@Test
	@Ignore
	public void testExists() {
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
	public void testListCategoryManual() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("POTAL_ID");
		manualSearchCondition.setCategoryId("FIRST_CATEGORY");
		
		SearchResult<Manual> result = manualService.listCategoryManual(manualSearchCondition);
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testCountManual() {
		int result = manualService.countManual("POTAL_ID");
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testListManual() {
		List<Manual> result = manualService.listManual("POTAL_ID", 3);
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testReadManual() {
		Manual result = manualService.readManual("0000000000001", "POTAL_ID", "user1");
		
		Assert.assertNotNull(result);
	}
	@Test
	@Ignore
	public void testListPopularTag() {
	}
}
