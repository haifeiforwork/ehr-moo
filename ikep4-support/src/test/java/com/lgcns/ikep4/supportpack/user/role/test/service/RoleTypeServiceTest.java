package com.lgcns.ikep4.supportpack.user.role.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class RoleTypeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	

	@Autowired
	private RoleTypeService roleTypeService;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private RoleType roleTypeCreate;
	private RoleType roleTypePost;
	private List<String> localeCodeList;

	private AdminSearchCondition searchCondition;
	
	private MockHttpServletRequest req; // HttpServletRequest Mock을 선언합니다.
	
	@Before
	public void setMethod() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
		user.setUserId("testCaseUserId");
	    user.setUserName("testCaseUserName");
	    
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	        // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		localeCodeList = new ArrayList<String>();
		localeCodeList.add("ko");
		localeCodeList.add("en");
		localeCodeList.add("jp");
		
		List<I18nMessage> i18nMessageCreate = new ArrayList<I18nMessage>();
		for(int i=0; i<localeCodeList.size(); i++) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("roleTypeName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("roleTypeName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		roleTypeCreate = new RoleType();
		roleTypeCreate.setCodeCheck("success");
		roleTypeCreate.setRoleTypeId("testRoleTypeId");
		roleTypeCreate.setRoleTypeName(i18nMessageCreate.get(0).getItemMessage());
		roleTypeCreate.setRegisterId("admin");
		roleTypeCreate.setRegisterName("관리자");
		roleTypeCreate.setUpdaterId("admin");
		roleTypeCreate.setUpdaterName("관리자");
		roleTypeCreate.setI18nMessageList(i18nMessageCreate);
		
		roleTypePost = new RoleType();
		roleTypePost.setCodeCheck("success");
		roleTypePost.setRoleTypeId("xxxx");
		roleTypePost.setRoleTypeName("xxxx");
		roleTypePost.setRegisterId("admin");
		roleTypePost.setRegisterName("관리자");
		roleTypePost.setUpdaterId("admin");
		roleTypePost.setUpdaterName("관리자");
		roleTypePost.setI18nMessageList(i18nMessageCreate);
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("roleTypeName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void create() {

		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				roleTypeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				roleTypeCreate.getRoleTypeId());
		
		roleTypeCreate.setI18nMessageList(i18nMessageList);
		
		//역할타입ID를 생성한 뒤에 생성한 역할타입ID의 roleTypeId로 read를 수행하여 Null이 아니면 True
		roleTypeService.create(roleTypeCreate);
		
		RoleType result = roleTypeService.read("testRoleTypeId");
		
		assertNotNull(result);
	}
	
	@Test
	public void update() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				roleTypeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				roleTypeCreate.getRoleTypeId());
		
		roleTypeCreate.setI18nMessageList(i18nMessageList);
		
		//역할타입ID를 생성한 뒤에 생성한 역할타입ID의 roleTypeId로 read를 수행하여 Null이 아니면 True
		roleTypeService.create(roleTypeCreate);
		
		RoleType preRoleType = roleTypeService.read("testRoleTypeId");
		
		List<I18nMessage> i18nMessageCreate = new ArrayList<I18nMessage>();
		for(int i=0; i<localeCodeList.size(); i++) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("updatedRoleTypeName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("roleTypeName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		List<I18nMessage> i18nMessageListUpdate = i18nMessageService.fillMandatoryField(
				roleTypeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				roleTypeCreate.getRoleTypeId());
		
		roleTypeCreate.setI18nMessageList(i18nMessageListUpdate);
		roleTypeCreate.setRoleTypeName(i18nMessageListUpdate.get(0).getItemMessage());
		roleTypeService.update(roleTypeCreate);
		
		RoleType postRoleType = roleTypeService.read("testRoleTypeId");
		
		assertNotSame(preRoleType.getRoleTypeName(), postRoleType.getRoleTypeName());
	}
	
	@Test
	public void delete() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				roleTypeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				roleTypeCreate.getRoleTypeId());
		
		roleTypeCreate.setI18nMessageList(i18nMessageList);
		
		//역할타입ID를 생성한 뒤에 생성한 역할타입ID의 roleTypeId로 read를 수행하여 Null이 아니면 True
		roleTypeService.create(roleTypeCreate);
		
		roleTypeService.delete("testRoleTypeId");
		
		assertNull(roleTypeService.read("testRoleTypeId"));
	}
	
	@Test
	public void exist() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				roleTypeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				roleTypeCreate.getRoleTypeId());
		
		roleTypeCreate.setI18nMessageList(i18nMessageList);
		
		//역할타입ID를 생성한 뒤에 생성한 역할타입ID의 roleTypeId로 read를 수행하여 Null이 아니면 True
		roleTypeService.create(roleTypeCreate);
		
		boolean result = roleTypeService.exists("testRoleTypeId");
		
		assertTrue(result);
	}
	
	@Test
	public void list() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				roleTypeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				roleTypeCreate.getRoleTypeId());
		
		roleTypeCreate.setI18nMessageList(i18nMessageList);
		
		roleTypeService.create(roleTypeCreate);
		
		searchCondition.setSearchColumn("id");
		searchCondition.setSearchWord("testRoleTypeId");
		
		assertNotNull(roleTypeService.list(searchCondition));
	}
	
}