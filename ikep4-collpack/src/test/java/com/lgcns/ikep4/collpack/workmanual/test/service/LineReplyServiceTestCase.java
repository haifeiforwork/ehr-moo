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

import com.lgcns.ikep4.collpack.workmanual.model.LineReply;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.LineReplyService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id $
 */
public class LineReplyServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private UserService userService; 
	@Autowired
	private ManualCategoryService manualCategoryService;
	@Autowired
	private ManualService manualService;
	@Autowired
	private LineReplyService lineReplyService;
	
	private LineReply lineReply;
	
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
		
		Manual manual = new Manual();
		manual.setManualId("0000000000001");           
		manual.setCategoryId("FIRST_CATEGORY");         
		manual.setManualType("A");
		manual.setVersion((float) 0.1);            
		manual.setTitle("TEST Title");              
		manual.setContents("TEST Contents");           
		manual.setAttachCount(0);       
		manual.setHitCount(0);          
		manual.setLinereplyCount(0);    
		manual.setPortalId("P000001");           
		manual.setRegisterId("user1000");   
		manual.setRegisterName("user1000");      
		manual.setRegistDate(new Date());        
		manual.setUpdaterId("user1000");      
		manual.setUpdaterName("user1000");       
		manual.setUpdateDate(new Date());             
		manual.setIndexRowNum(3);       
		manual.setJobTitleName("JOB");       
		//manual.setTagList();            
		manual.setUpdaterEnglishName("user1000");    
		manual.setJobTitleEnglishName("JOB");
		manualService.create(manual);
		
		this.lineReply = new LineReply();

		this.lineReply.setLinereplyId("000001");   
		this.lineReply.setLinereplyGroupId("000001");
		//this.lineReply.setLinereplyParentId("000001");
		this.lineReply.setManualId("0000000000001");
		this.lineReply.setStep(0);
		this.lineReply.setIndentation(0);
		this.lineReply.setLinereplyContents("Test Contents");
		this.lineReply.setIsDelete(0);
		this.lineReply.setRegisterId("user1000");
		this.lineReply.setRegisterName("user1000");
		this.lineReply.setRegistDate(new Date());
		this.lineReply.setUpdaterId("user1000");
		this.lineReply.setUpdaterName("user1000");
		this.lineReply.setUpdateDate(new Date());
		this.lineReply.setTeamName("TEAM");
		this.lineReply.setJobTitleName("JOB");
		this.lineReply.setRegisterEnglishName("user1000");
		this.lineReply.setTeamEnglishName("TEAM");
		this.lineReply.setJobTitleEnglishName("JOB");
		
		this.pk = lineReplyService.create(lineReply);
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
		LineReply result = lineReplyService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	@Ignore
	public void testDelete() {
	}
	
	@Test
	public void testUpdate() {
		this.lineReply = lineReplyService.read(this.pk);
		this.lineReply.setLinereplyContents("Test Contents Update");
		lineReplyService.update(this.lineReply);
		LineReply result = lineReplyService.read(this.pk);
		
		Assert.assertEquals(this.lineReply.getLinereplyContents(), result.getLinereplyContents());
	}
	
	/////////////////////////////////////////////
	@Test
	public void testListLineReply() {
		ManualSearchCondition manualSearchCondition = new ManualSearchCondition();
		manualSearchCondition.setManualId("0000000000001");

		SearchResult<LineReply> result = lineReplyService.listLineReply(manualSearchCondition);
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testCreateLineReply() {
		String result = lineReplyService.createLineReply(this.lineReply, "P000001");
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testCreateReplyLineReply() {
		String result = lineReplyService.createReplyLineReply(this.lineReply, "P000001");
		
		Assert.assertNotNull(result);
	}
	@Test
	public void testDeleteLinereplyByUser() {
		lineReplyService.deleteLinereplyByUser("0000000000001", "000001", "user1000", "user1000", "P000001");
		LineReply result = lineReplyService.read(this.pk);
		
		Assert.assertNull(result);
	}
	@Test
	public void testDeleteLinereplyByAdmin() {
		lineReplyService.deleteLinereplyByAdmin("0000000000001", "000001", "P000001");
		LineReply result = lineReplyService.read(this.pk);
		
		Assert.assertNull(result);
	}
}
