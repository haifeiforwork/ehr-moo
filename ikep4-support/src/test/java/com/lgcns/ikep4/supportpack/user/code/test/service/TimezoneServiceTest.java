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
import com.lgcns.ikep4.support.user.code.model.Timezone;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.TimezoneService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class TimezoneServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private TimezoneService timezoneService;
	
	@Autowired
	private I18nMessageService i18nMessageService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private Timezone timezoneCreate;
	private Timezone timezonePost;
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
			i18nMessage.setItemMessage("timezoneName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("timezoneName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		timezoneCreate = new Timezone();
		timezoneCreate.setCodeCheck("success");
		timezoneCreate.setTimezoneId("testTimezoneId");
		timezoneCreate.setTimezoneName(i18nMessageCreate.get(0).getItemMessage());
		timezoneCreate.setDescription("테스트 설명입니다");
		timezoneCreate.setTimeDifference("0");
		timezoneCreate.setSortOrder("00030");
		timezoneCreate.setRegisterId("admin");
		timezoneCreate.setRegisterName("관리자");
		timezoneCreate.setUpdaterId("admin");
		timezoneCreate.setUpdaterName("관리자");
		timezoneCreate.setI18nMessageList(i18nMessageCreate);
		
		timezonePost = new Timezone();
		timezonePost.setCodeCheck("success");
		timezonePost.setTimezoneId("xxxx");
		timezonePost.setTimezoneName("xxxx");
		timezonePost.setDescription("테스트 설명입니다");
		timezonePost.setTimeDifference("0");
		timezonePost.setRegisterId("admin");
		timezonePost.setRegisterName("관리자");
		timezonePost.setUpdaterId("admin");
		timezonePost.setUpdaterName("관리자");
		timezonePost.setI18nMessageList(i18nMessageCreate);

		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("timezoneName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void create() {

		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 timezoneId로 read를 수행하여 Null이 아니면 True
		timezoneService.create(timezoneCreate);
		
		Timezone result = timezoneService.read("testTimezoneId");
		
		assertNotNull(result);
	}
	
	@Test
	public void update() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 timezoneId로 read를 수행하여 Null이 아니면 True
		timezoneService.create(timezoneCreate);
		
		Timezone preTimezone = timezoneService.read("testTimezoneId");
		
		List<I18nMessage> i18nMessageCreate = new ArrayList<I18nMessage>();
		for(int i=0; i<localeCodeList.size(); i++) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("updatedTimezoneName_"+i);
			i18nMessage.setLocaleCode(localeCodeList.get(i));
			i18nMessage.setFieldName("timezoneName");
			
			i18nMessageCreate.add(i18nMessage);
		}
		
		List<I18nMessage> i18nMessageListUpdate = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageListUpdate);
		timezoneCreate.setTimezoneName(i18nMessageListUpdate.get(0).getItemMessage());
		timezoneService.update(timezoneCreate);
		
		Timezone postTimezone = timezoneService.read("testTimezoneId");
		
		assertNotSame(preTimezone.getTimezoneName(), postTimezone.getTimezoneName());
	}
	
	@Test
	public void delete() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 timezoneId로 read를 수행하여 Null이 아니면 True
		timezoneService.create(timezoneCreate);
		
		timezoneService.delete("testTimezoneId");
		
		assertNull(timezoneService.read("testTimezoneId"));
	}
	
	@Test
	public void exist() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 timezoneId로 read를 수행하여 Null이 아니면 True
		timezoneService.create(timezoneCreate);
		
		boolean result = timezoneService.exists("testTimezoneId");
		
		assertTrue(result);
	}
	
	@Test
	public void getMaxSortOrder() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		timezoneCreate.setSortOrder("00030");
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 timezoneId로 read를 수행하여 Null이 아니면 True
		timezoneService.create(timezoneCreate);
		
		String maxSortOrder = timezoneService.getMaxSortOrder();
		int tempMax = Integer.parseInt(maxSortOrder);
		int tempPre = Integer.parseInt(timezoneCreate.getSortOrder());
		
		assertEquals(1, (tempMax-tempPre));
	}
	
	@Test
	public void goUp() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		timezoneCreate.setSortOrder(timezoneService.getMaxSortOrder());
		timezoneService.create(timezoneCreate);
		
		int pastSortOrder = Integer.parseInt(timezoneService.read("testTimezoneId").getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("timezoneId", "testTimezoneId");
		map.put("sortOrder", StringUtil.lpad(String.valueOf(pastSortOrder), 5, "0"));
		
		timezoneService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(timezoneService.read("testTimezoneId").getSortOrder());
		
		assertTrue((pastSortOrder > currentSortOrder) && (Math.abs(pastSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
				timezoneCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezoneCreate.getTimezoneId());
		
		timezoneCreate.setI18nMessageList(i18nMessageList);
		timezoneCreate.setSortOrder(timezoneService.getMaxSortOrder());
		timezoneService.create(timezoneCreate);
		
		int pastSortOrder = Integer.parseInt(timezoneService.read("testTimezoneId").getSortOrder());
		
		List<I18nMessage> i18nMessageListA = i18nMessageService.fillMandatoryField(
				timezonePost.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
				timezonePost.getTimezoneId());
		
		timezonePost.setI18nMessageList(i18nMessageListA);
		timezonePost.setSortOrder(StringUtil.lpad(String.valueOf(pastSortOrder+1), 5, "0"));
		timezoneService.create(timezonePost);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("timezoneId", "testTimezoneId");
		map.put("sortOrder", StringUtil.lpad(String.valueOf(pastSortOrder), 5, "0"));
		
		timezoneService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(timezoneService.read("testTimezoneId").getSortOrder());
		
		assertTrue((pastSortOrder < currentSortOrder) && (Math.abs(pastSortOrder-currentSortOrder)==1));
	}

}
