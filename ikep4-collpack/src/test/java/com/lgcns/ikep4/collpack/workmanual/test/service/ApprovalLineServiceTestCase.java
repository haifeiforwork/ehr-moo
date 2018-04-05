package com.lgcns.ikep4.collpack.workmanual.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLinePk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalLineService;
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
public class ApprovalLineServiceTestCase extends BaseServiceTestCase {
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
	@Autowired
	private ApprovalLineService approvalLineService;
	
	private ApprovalLine approvalLine;
	
	private ApprovalLinePk approvalLinePk;

	private MockHttpServletRequest req;
	private User user;
	
	private Approval approval;
	private ManualVersion manualVersion;
	
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
		
		manualVersion = new ManualVersion();
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
		manualVersion.setIsAbolition(0); 
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
		
		approval = new Approval();
		approval.setApprovalId("0001");
		approval.setManualId(manualVersion.getManualId());
		approval.setVersionId(manualVersion.getVersionId());     
		approval.setManualType("B");
		approval.setRequestContents("Contents");
		approval.setRegisterId("user1000"); 
		approval.setRegisterName("user1000"); 
		approval.setRegistDate(new Date());
		approval.setApprovalStatus("A");
		approval.setIndexRowNum(0);
		approval.setVersion((float) 1);  
		approval.setVersionTitle("Title");
		approval.setRegisterEnglishName("user1000"); 
		approvalService.create(approval);
		
		this.approvalLine = new ApprovalLine();
		
		this.approvalLine.setApprovalId(approval.getApprovalId());      
		this.approvalLine.setApprovalLine(1); 
		this.approvalLine.setApprovalUserId("user1000"); 
		this.approvalLine.setApprovalDate(new Date());
		this.approvalLine.setApprovalResult(""); 
		this.approvalLine.setApprovalComment("");
		this.approvalLine.setApprovalUserName("user1000");
		this.approvalLine.setApprovalUserTeamName("TEAM");
		this.approvalLine.setApprovalUserJobTitleName("JOB");
		this.approvalLine.setApprovalUserEnglishName("user1000"); 
		this.approvalLine.setApprovalUserTeamEnglishName("TEAM");
		this.approvalLine.setApprovalUserJobTitleEnglishName("JOB");
		
		approvalLineService.create(this.approvalLine);
		
		this.approvalLinePk = (ApprovalLinePk)this.approvalLine;
	}
	
	/////////////////////////////////////////////
	@Test
	public void testListApprovalLineByManualId() {
		List<ApprovalLine> result = approvalLineService.listApprovalLineByManualId(approval.getManualId(), "P000001");

		Assert.assertNotNull(result);
	}
	@Test
	public void testListApprovalLineByVersionId() {
		List<ApprovalLine> result = approvalLineService.listApprovalLineByVersionId(approval.getVersionId(), "P000001");

		Assert.assertNotNull(result);
	}
	@Test
	public void testListApprovalLine() {
		List<ApprovalLine> result = approvalLineService.listApprovalLine(this.approvalLine.getApprovalId());

		Assert.assertNotNull(result);
	}
	@Test
	public void testUpdateApprovalLine() {
		this.approvalLine.setApprovalResult("C");
		this.approvalLine.setApprovalComment("Comment");
		approvalLineService.updateApprovalLine(this.approvalLine, userService.read("user1000"));
		List<ApprovalLine> result = approvalLineService.listApprovalLine(this.approvalLine.getApprovalId());
		
		Assert.assertEquals(this.approvalLine.getApprovalResult(), result.get(0).getApprovalResult());
	}
	@Test
	public void testCreateApprovalUser() {
		this.approvalLine.setApprovalLine(2);
		approvalLineService.createApprovalUser(this.approvalLine);
		List<ApprovalLine> result = approvalLineService.listApprovalLine(this.approvalLine.getApprovalId());
		
		Assert.assertEquals(result.size(), 2);
	}
	@Test
	public void testDeleteApprovalUser() {
		approvalLineService.deleteApprovalUser(this.approvalLinePk);
		List<ApprovalLine> result = approvalLineService.listApprovalLine(this.approvalLine.getApprovalId());
		
		Assert.assertEquals(result.size(), 0);
	}
}
