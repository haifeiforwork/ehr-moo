package com.lgcns.ikep4.supportpack.user.code.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class LocaleCodeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private LocaleCodeService localeCodeService;

	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private LocaleCode localeCodeCreate;
	private LocaleCode localeCodePost;
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
			i18nMessage.setItemMessage("localeName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("localeName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		localeCodeCreate = new LocaleCode();
		localeCodeCreate.setCodeCheck("success");
		localeCodeCreate.setLocaleCode("testLocaleCode");
		localeCodeCreate.setLocaleName(i18nMessageCreate.get(0).getItemMessage());
		localeCodeCreate.setDescription("로케일코드 테스트 create");
		localeCodeCreate.setSortOrder("00002");
		localeCodeCreate.setRegisterId("admin");
		localeCodeCreate.setRegisterName("관리자");
		localeCodeCreate.setUpdaterId("admin");
		localeCodeCreate.setUpdaterName("관리자");
		localeCodeCreate.setI18nMessageList(i18nMessageCreate);

		localeCodePost = new LocaleCode();
		localeCodePost.setCodeCheck("success");
		localeCodePost.setLocaleCode("xxxx");
		localeCodePost.setLocaleName("테스트Update");
		localeCodePost.setDescription("로케일코드 테스트 update");
		localeCodePost.setSortOrder("00003");
		localeCodePost.setRegisterId("admin");
		localeCodePost.setRegisterName("관리자");
		localeCodePost.setUpdaterId("admin");
		localeCodePost.setUpdaterName("관리자");
		localeCodePost.setI18nMessageList(i18nMessageCreate);
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("localeName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void create() {

		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 localeCode로 read를 수행하여 Null이 아니면 True
		localeCodeService.create(localeCodeCreate);
		
		LocaleCode result = localeCodeService.read("testLocaleCode");
		
		assertNotNull(result);
	}
	
	@Test
	public void update() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 localeCode로 read를 수행하여 Null이 아니면 True
		localeCodeService.create(localeCodeCreate);
		
		LocaleCode preLocaleCode = localeCodeService.read("testLocaleCode");
		
		List<I18nMessage> i18nMessageCreate = new ArrayList<I18nMessage>();
		for(int i=0; i<localeCodeList.size(); i++) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("updatedLocaleName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("localeCodeName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		List<I18nMessage> i18nMessageListUpdate = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageListUpdate);
		localeCodeCreate.setLocaleName(i18nMessageListUpdate.get(0).getItemMessage());
		localeCodeService.update(localeCodeCreate);
		
		LocaleCode postLocaleCode = localeCodeService.read("testLocaleCode");
		
		assertNotSame(preLocaleCode.getLocaleName(), postLocaleCode.getLocaleName());
	}
	
	@Test
	public void delete() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 localeCode로 read를 수행하여 Null이 아니면 True
		localeCodeService.create(localeCodeCreate);
		
		localeCodeService.remove("testLocaleCode");
		
		assertNull(localeCodeService.read("testLocaleCode"));
	}
	
	@Test
	public void exist() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 localeCode로 read를 수행하여 Null이 아니면 True
		localeCodeService.create(localeCodeCreate);
		
		boolean result = localeCodeService.exists("testLocaleCode");
		
		assertTrue(result);
	}
	
	@Test
	public void getMaxSortOrder() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		localeCodeCreate.setSortOrder("00030");
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 localeCode로 read를 수행하여 Null이 아니면 True
		localeCodeService.create(localeCodeCreate);
		
		String maxSortOrder = localeCodeService.getMaxSortOrder();
		int tempMax = Integer.parseInt(maxSortOrder);
		int tempPre = Integer.parseInt(localeCodeCreate.getSortOrder());
		
		assertEquals(1, (tempMax-tempPre));
	}
	
	@Test
	public void goUp() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		localeCodeCreate.setSortOrder(localeCodeService.getMaxSortOrder());
		localeCodeService.create(localeCodeCreate);
		
		int pastSortOrder = Integer.parseInt(localeCodeService.read("testLocaleCode").getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("localeCode", "testLocaleCode");
		map.put("sortOrder", StringUtil.lpad(String.valueOf(pastSortOrder), 5, "0"));
		
		localeCodeService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(localeCodeService.read("testLocaleCode").getSortOrder());
		
		assertTrue((pastSortOrder > currentSortOrder) && (Math.abs(pastSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				localeCodeCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodeCreate.getLocaleCode());
		
		localeCodeCreate.setI18nMessageList(i18nMessageList);
		localeCodeCreate.setSortOrder(localeCodeService.getMaxSortOrder());
		localeCodeService.create(localeCodeCreate);
		
		int pastSortOrder = Integer.parseInt(localeCodeService.read("testLocaleCode").getSortOrder());
		
		List<I18nMessage> i18nMessageListA = i18nMessageService.fillMandatoryField(
				localeCodePost.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				localeCodePost.getLocaleCode());
		
		localeCodePost.setI18nMessageList(i18nMessageListA);
		localeCodePost.setSortOrder(StringUtil.lpad(String.valueOf(pastSortOrder+1), 5, "0"));
		localeCodeService.create(localeCodePost);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("localeCode", "testLocaleCode");
		map.put("sortOrder", StringUtil.lpad(String.valueOf(pastSortOrder), 5, "0"));
		
		localeCodeService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(localeCodeService.read("testLocaleCode").getSortOrder());
		
		assertTrue((pastSortOrder < currentSortOrder) && (Math.abs(pastSortOrder-currentSortOrder)==1));
	}
	
}
