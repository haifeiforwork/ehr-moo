package com.lgcns.ikep4.supportpack.user.code.test.service;

import static org.junit.Assert.*;

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
import com.lgcns.ikep4.support.user.code.model.ClassCode;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.ClassCodeService;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class ClassCodeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private ClassCodeService classCodeService;
	
	@Autowired
	private I18nMessageService i18nMessageService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private ClassCode classCodeCreate;
	private ClassCode classCodeUpdate;
	private List<String> localeCodeList;
	
	private AdminSearchCondition searchCondition;
	
	private MockHttpServletRequest req; // HttpServletRequest Mock을 선언합니다.
	
	@Before
	public void setMethod() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
		user.setUserId("testCaseId");
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
			i18nMessage.setItemMessage("className_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("className");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		classCodeCreate = new ClassCode();
		classCodeCreate.setCodeCheck("success");
		classCodeCreate.setClassId("testClassId");
		classCodeCreate.setClassName(i18nMessageCreate.get(0).getItemMessage());
		classCodeCreate.setDescription("테스트 설명입니다");
		classCodeCreate.setRegisterId("admin");
		classCodeCreate.setRegisterName("관리자");
		classCodeCreate.setUpdaterId("admin");
		classCodeCreate.setUpdaterName("관리자");
		classCodeCreate.setI18nMessageList(i18nMessageCreate);
		
		classCodeUpdate = new ClassCode();
		classCodeUpdate.setCodeCheck("modify");
		classCodeUpdate.setClassId("testClassId");
		classCodeUpdate.setClassName("테스트Update");
		classCodeUpdate.setDescription("테스트 설명 수정본입니다");
		classCodeUpdate.setRegisterId("admin");
		classCodeUpdate.setRegisterName("관리자");
		classCodeUpdate.setUpdaterId("admin");
		classCodeUpdate.setUpdaterName("관리자");

		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("className");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void create() {
		
		// 뷰에서 넘겨준 classcode와 같은 형태의 classcode 모델 생성(setMethod에서)
		// 그 classcode를 이용해서 i18nMessageList 생성
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				classCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				classCodeCreate.getClassId());
		
		// i18nMessageList 세팅
		classCodeCreate.setI18nMessageList(i18nMessageList);
		
		classCodeService.create(classCodeCreate);
		
		ClassCode result = classCodeService.read(classCodeCreate.getClassId());
		
		assertNotNull("testClassId", result.getClassId());
		
	}
	
	@Test
	public void update() {
		
		List<I18nMessage> i18nMessageListCreate = i18nMessageService.fillMandatoryField(
				classCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				classCodeCreate.getClassId());
		
		classCodeCreate.setI18nMessageList(i18nMessageListCreate);
		
		classCodeService.create(classCodeCreate);
		
		List<I18nMessage> i18nMessageCreate = new ArrayList<I18nMessage>();
		for(int i=0; i<localeCodeList.size(); i++) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("updatedClassName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("className");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		List<I18nMessage> i18nMessageListUpdate = i18nMessageService.fillMandatoryField(
				classCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				classCodeCreate.getClassId());
		
		classCodeCreate.setI18nMessageList(i18nMessageListUpdate);
		classCodeCreate.setClassName("UpdatedName");
		
		classCodeService.update(classCodeCreate);
		
		String result = classCodeService.read(classCodeCreate.getClassId()).getClassName();
		
		assertEquals("UpdatedName", result);
		
	}
	
	@Test
	public void remove() {
		
		// 뷰에서 넘겨준 classcode와 같은 형태의 classcode 모델 생성(setMethod에서)
		// 그 classcode를 이용해서 i18nMessageList 생성
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				classCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				classCodeCreate.getClassId());
		
		// i18nMessageList 세팅
		classCodeCreate.setI18nMessageList(i18nMessageList);
		
		classCodeService.create(classCodeCreate);
		
		classCodeService.delete(classCodeCreate.getClassId());
		
		assertNull(classCodeService.read(classCodeCreate.getClassId()));		
	}
	
	@Test
	public void exists() {
		
		// 뷰에서 넘겨준 classcode와 같은 형태의 classcode 모델 생성(setMethod에서)
		// 그 classcode를 이용해서 i18nMessageList 생성
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				classCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				classCodeCreate.getClassId());
		
		// i18nMessageList 세팅
		classCodeCreate.setI18nMessageList(i18nMessageList);
		
		classCodeService.create(classCodeCreate);
		
		boolean isExists = classCodeService.exists(classCodeCreate.getClassId());
		
		assertTrue(isExists);
	}
	
}
