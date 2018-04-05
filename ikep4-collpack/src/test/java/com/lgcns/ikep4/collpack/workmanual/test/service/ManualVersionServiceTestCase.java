package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualVersionService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class ManualVersionServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ManualService manualService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	@Autowired
	private ManualVersionService manualVersionService;
	@Autowired
	private UserService userService; 
	
	private ManualVersion manualVersion;
	
	private String pk;
	
	private MockHttpServletRequest req;
	private User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUserId("user1000");
		user.setUserName("사용자1000");
		user.setLocaleCode("ko");

		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성
		session.setAttribute("ikep.user", user);
		req.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		ManualCategory manualCategory = new ManualCategory();
		manualCategory.setCategoryId("FIRST_CATEGORY"); 
		manualCategory.setCategoryName("FIRST_CATEGORY");
		manualCategory.setCategoryParentId("FIRST_CATEGORY");
		manualCategory.setReadPermission(0);
		manualCategory.setSortOrder(1); 
		manualCategory.setPortalId("P000001"); 
		manualCategory.setRegisterId("user1000"); 
		manualCategory.setRegisterName("user1000"); 
		manualCategory.setRegistDate(new Date());
		manualCategory.setChildCount(0);
		manualCategoryService.create(manualCategory);
		
		this.manualVersion = new ManualVersion();
		this.manualVersion.setVersion((float) 1);
		this.manualVersion.setVersionTitle("TEST Title");              
		this.manualVersion.setVersionContents("TEST Contents");           
		this.manualVersion.setVersionAttachCount(0);
		this.manualVersion.setIsPublic(1);
		this.manualVersion.setUpdateReason("");
		this.manualVersion.setApprovalStatus("A");
		this.manualVersion.setPortalId("P000001");
		this.manualVersion.setRegisterId("user1000");   
		this.manualVersion.setRegisterName("user1000");   
		this.manualVersion.setRegistDate(new Date());
		this.manualVersion.setIsAbolition(1); 
		this.manualVersion.setTag("");
		this.manualVersion.setManualType("S");
		this.manualVersion.setIndexRowNum(10);
		this.manualVersion.setFileLinkList(null);
		this.manualVersion.setFileDataList(null);
		this.manualVersion.setRegisterEnglishName("user1000");
		manualVersionService.createManualVersion(manualVersion, "FIRST_CATEGORY", "", userService.read("user1000"));
		
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setRegisterId("user1000");
		SearchResult<ManualVersion> searchResult = manualVersionService.listMyManualVersion(manualSearchCondition);

		this.manualVersion = searchResult.getEntity().get(0);
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
	public void testListMyManualVersion() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");  
		manualSearchCondition.setRegisterId("user1000");
		SearchResult<ManualVersion> result = manualVersionService.listMyManualVersion(manualSearchCondition);
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testListManualVersion() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setManualId(this.manualVersion.getManualId()); 
		SearchResult<ManualVersion> result = manualVersionService.listManualVersion(manualSearchCondition);
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testCreateRedoManualVersion() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setManualId(this.manualVersion.getManualId());
		manualSearchCondition.setRegisterId("user1000");
		manualSearchCondition.setVersionId(this.manualVersion.getVersionId()); 
		manualVersionService.createRedoManualVersion(manualSearchCondition, userService.read("user1000"));
		ManualVersion result = manualVersionService.getManualVersionBymanualId(this.manualVersion.getManualId(), "P000001");
		
		Assert.assertNotSame((float) 1, result.getVersion());
	}
	@Test
	public void testReadManualVersion() {
		ManualVersion result = manualVersionService.readManualVersion(this.manualVersion.getVersionId(), "P000001");
		 
		Assert.assertNotNull(result);
	}
	@Test
	public void testCountSubmittingManualVersion() {
		int result = manualVersionService.countSubmittingManualVersion(this.manualVersion.getManualId(), "P000001");

		Assert.assertNotNull(result);
	}
	@Test
	public void testDeleteManualVersion() {
		manualVersionService.deleteManualVersion(this.manualVersion.getVersionId(), "P000001", "user1000", "user1000");
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setCategoryId("FIRST_CATEGORY");
		SearchResult<Manual> result = manualService.listCategoryManual(manualSearchCondition);
		
		Assert.assertEquals((int)result.getRecordCount(), 0);
	}
	@Test
	public void testUpdateManualVersion() {
		manualVersionService.updateManualVersion(this.manualVersion, userService.read("user1000"));
		ManualVersion result = manualVersionService.getManualVersionBymanualId(this.manualVersion.getManualId(), "P000001");
		
		Assert.assertNotSame(this.manualVersion.getVersion(), result.getVersion());
	}
	@Test
	public void testCreateApproval() {
		Approval approval = new Approval();
		approval.setApprovalId("0001");
		approval.setManualId(this.manualVersion.getManualId());
		approval.setVersionId(this.manualVersion.getVersionId());
		approval.setVersion((float) 1);
		approval.setManualType("A");
		approval.setRequestContents("상신");
		approval.setRegisterId("user1000");
		approval.setRegisterName("user1000");
		approval.setRegistDate(new Date());
		approval.setApprovalStatus("A");
		String result = manualVersionService.createApproval(approval, userService.read("user1000"));
	
		Assert.assertNotNull(result);
	}
	@Test
	public void testCancelApproval() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setRegisterId("user1000");
		SearchResult<ManualVersion> searchResult1 = manualVersionService.listMyManualVersion(manualSearchCondition);
		int iSize = searchResult1.getRecordCount();
		
		Approval approval = new Approval();
		approval.setApprovalId("0001");
		approval.setManualId(this.manualVersion.getManualId());
		approval.setVersionId(this.manualVersion.getVersionId());
		approval.setVersion((float) 1);
		approval.setManualType("A");
		approval.setRequestContents("상신");
		approval.setRegisterId("user1000");
		approval.setRegisterName("user1000");
		approval.setRegistDate(new Date());
		approval.setApprovalStatus("A");
		manualVersionService.createApproval(approval, userService.read("user1000"));
	
		manualVersionService.cancelApproval(this.manualVersion.getVersionId(), userService.read("user1000"), "A");
		
		SearchResult<ManualVersion> searchResult2 = manualVersionService.listMyManualVersion(manualSearchCondition);
		int jSize = searchResult2.getRecordCount();
		
		Assert.assertEquals(iSize, jSize);
	}
	@Test
	public void testGetManualVersionBymanualId() {
		ManualVersion result = manualVersionService.getManualVersionBymanualId(this.manualVersion.getManualId(), "P000001");
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testCreateManualVersion() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setRegisterId("user1000");
		SearchResult<ManualVersion> searchResult1 = manualVersionService.listMyManualVersion(manualSearchCondition);
		int iSize = searchResult1.getRecordCount();

		manualVersionService.createManualVersion(manualVersion, "FIRST_CATEGORY", "", userService.read("user1000"));

		SearchResult<ManualVersion> searchResult2 = manualVersionService.listMyManualVersion(manualSearchCondition);
		int jSize = searchResult2.getRecordCount();
		
		Assert.assertNotSame(iSize, jSize);
	}
}
