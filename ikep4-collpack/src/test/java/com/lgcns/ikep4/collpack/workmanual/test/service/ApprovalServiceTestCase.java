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
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.search.ApprovalSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalService;
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
public class ApprovalServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private ManualService manualService;
	@Autowired
	private ManualCategoryService manualCategoryService;
	@Autowired
	private ManualVersionService manualVersionService;
	@Autowired
	private UserService userService; 
	@Autowired
	private ApprovalService approvalService;
	
	private Approval approval;
	
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
		
		ManualVersion manualVersion = new ManualVersion();
		manualVersion.setVersion((float) 1);
		manualVersion.setVersionTitle("TEST Title");              
		manualVersion.setVersionContents("TEST Contents");           
		manualVersion.setVersionAttachCount(0);
		manualVersion.setIsPublic(1);
		manualVersion.setUpdateReason("");
		manualVersion.setApprovalStatus("B");
		manualVersion.setPortalId("P000001");
		manualVersion.setRegisterId("user1000");   
		manualVersion.setRegisterName("user1000");   
		manualVersion.setRegistDate(new Date());
		manualVersion.setIsAbolition(1); 
		manualVersion.setTag("");
		manualVersion.setManualType("S");
		manualVersion.setIndexRowNum(10);
		manualVersion.setFileLinkList(null);
		manualVersion.setFileDataList(null);
		manualVersion.setRegisterEnglishName("user1000");
		manualVersionService.createManualVersion(manualVersion, "FIRST_CATEGORY", "", userService.read("user1000"));
		
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setPortalId("P000001");
		manualSearchCondition.setRegisterId("user1000");
		SearchResult<ManualVersion> searchResult = manualVersionService.listMyManualVersion(manualSearchCondition);
		manualVersion = searchResult.getEntity().get(0);
		
		this.approval = new Approval();

		this.approval.setApprovalId("0001");
		this.approval.setManualId(manualVersion.getManualId());
		this.approval.setVersionId(manualVersion.getVersionId());     
		this.approval.setManualType("A");
		this.approval.setRequestContents("Contents");
		this.approval.setRegisterId("user1000"); 
		this.approval.setRegisterName("user1000"); 
		this.approval.setRegistDate(new Date());
		this.approval.setApprovalStatus("A");
		this.approval.setIndexRowNum(0);
		this.approval.setVersion((float) 1);  
		this.approval.setVersionTitle("Title");
		this.approval.setRegisterEnglishName("user1000"); 
		
		approvalService.create(this.approval);
		this.pk = approval.getApprovalId();
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
	public void testRead() {
		Approval result = approvalService.read(this.pk);
		
		Assert.assertNotNull(result);
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
	public void testListMyApproval() {
		ApprovalSearchCondition approvalSearchCondition = new ApprovalSearchCondition();
		approvalSearchCondition.setUserId("user1000");
		
		SearchResult<Approval> result = approvalService.listMyApproval(approvalSearchCondition);
		
		Assert.assertNotNull(result);
	}
	@Test
	@Ignore
	public void testSendMail() {
	}
	@Test
	public void testReadSubmittingApproval() {
		Approval result = approvalService.readSubmittingApproval(this.approval.getVersionId());
	}
}
