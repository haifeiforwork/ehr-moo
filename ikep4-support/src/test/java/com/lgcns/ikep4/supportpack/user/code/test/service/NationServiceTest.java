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
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.NationService;
import com.lgcns.ikep4.support.user.member.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class NationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private NationService nationService;
	
	@Autowired
	private I18nMessageService i18nMessageService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private Nation nationCreate;
	private Nation nationPost;
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
			i18nMessage.setItemMessage("nationName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("nationName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		nationCreate = new Nation();
		nationCreate.setCodeCheck("success");
		nationCreate.setNationCode("testNationCode");
		nationCreate.setNationName(i18nMessageCreate.get(0).getItemMessage());
		nationCreate.setRegisterId("admin");
		nationCreate.setRegisterName("관리자");
		nationCreate.setUpdaterId("admin");
		nationCreate.setUpdaterName("관리자");
		nationCreate.setI18nMessageList(i18nMessageCreate);
		
		nationPost = new Nation();
		nationPost.setCodeCheck("success");
		nationPost.setNationCode("xxxx");
		nationPost.setNationName("xxxx");
		nationPost.setRegisterId("admin");
		nationPost.setRegisterName("관리자");
		nationPost.setUpdaterId("admin");
		nationPost.setUpdaterName("관리자");
		nationPost.setI18nMessageList(i18nMessageCreate);

		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("nationName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void create() {

		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				nationCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				nationCreate.getNationCode());
		
		nationCreate.setI18nMessageList(i18nMessageList);
		
		//국가코드를 생성한 뒤에 생성한 국가코드의 nationCode로 read를 수행하여 Null이 아니면 True
		nationService.create(nationCreate);
		
		Nation result = nationService.read("testNationCode");
		
		assertNotNull(result);
	}
	
	@Test
	public void update() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				nationCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				nationCreate.getNationCode());
		
		nationCreate.setI18nMessageList(i18nMessageList);
		
		//국가코드를 생성한 뒤에 생성한 국가코드의 nationCode로 read를 수행하여 Null이 아니면 True
		nationService.create(nationCreate);
		
		Nation preNation = nationService.read("testNationCode");
		
		List<I18nMessage> i18nMessageCreate = new ArrayList<I18nMessage>();
		for(int i=0; i<localeCodeList.size(); i++) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("updatedNationName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("nationName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		List<I18nMessage> i18nMessageListUpdate = i18nMessageService.fillMandatoryField(
				nationCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				nationCreate.getNationCode());
		
		nationCreate.setI18nMessageList(i18nMessageListUpdate);
		nationCreate.setNationName(i18nMessageListUpdate.get(0).getItemMessage());
		nationService.update(nationCreate);
		
		Nation postNation = nationService.read("testNationCode");
		
		assertNotSame(preNation.getNationName(), postNation.getNationName());
	}
	
	@Test
	public void delete() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				nationCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				nationCreate.getNationCode());
		
		nationCreate.setI18nMessageList(i18nMessageList);
		
		//국가코드를 생성한 뒤에 생성한 국가코드의 nationCode로 read를 수행하여 Null이 아니면 True
		nationService.create(nationCreate);
		
		nationService.delete("testNationCode");
		
		assertNull(nationService.read("testNationCode"));
	}
	
	@Test
	public void exist() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				nationCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				nationCreate.getNationCode());
		
		nationCreate.setI18nMessageList(i18nMessageList);
		
		//국가코드를 생성한 뒤에 생성한 국가코드의 nationCode로 read를 수행하여 Null이 아니면 True
		nationService.create(nationCreate);
		
		boolean result = nationService.exists("testNationCode");
		
		assertTrue(result);
	}

}
