/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortalSystemUrlDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemUrlDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalSystemUrlDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalSystemUrlDao portalSystemUrlDAO;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	private MockHttpServletRequest req;
    
    private List<I18nMessage> createI18nMessageList;
    
    private List<I18nMessage> updateI18nMessageList;

	private PortalSystemUrl portalSystemUrlCreate;
	
	private PortalSystemUrl portalSystemUrlUpdate;
	
	private Map<String,String> readParam;
	
	private PortalSystemUrlSearchCondition searchCondition;

	@Before
	public void setUp() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
	    user.setUserId("user1");
	    user.setUserName("user1");
	    
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	    // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		List<LocaleCode> localeCodeList = i18nMessageService.selectLocaleAll();
		
		portalSystemUrlCreate = new PortalSystemUrl();
		portalSystemUrlCreate.setSystemUrlId("1234567890");
		portalSystemUrlCreate.setSystemUrlName("Create PortalSystemUrl");
		portalSystemUrlCreate.setUrl("Create Url");
		portalSystemUrlCreate.setRegisterId("user1");
		portalSystemUrlCreate.setRegisterName("user1");
		//portalSystemUrlCreate.setRegistDate(registDate);
		portalSystemUrlCreate.setUpdaterId("user1");
		portalSystemUrlCreate.setUpdaterName("user1");
		//portalSystemUrlCreate.setUpdateDate(updateDate);
		
		createI18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create PortalSystemUrl("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("systemUrlName");
			i18nMessage.setMessageId("1234567890");
			
			createI18nMessageList.add(i18nMessage);
		}
		
		portalSystemUrlCreate.setI18nMessageList(createI18nMessageList);
		
		portalSystemUrlUpdate = new PortalSystemUrl();
		portalSystemUrlUpdate.setSystemUrlId("1234567890");
		portalSystemUrlUpdate.setSystemUrlName("Update PortalSystemUrl");
		portalSystemUrlUpdate.setUrl("Update Url");
		portalSystemUrlUpdate.setRegisterId("user1");
		portalSystemUrlUpdate.setRegisterName("user1");
		//portalSystemUrlUpdate.setRegistDate(registDate);
		portalSystemUrlUpdate.setUpdaterId("user1");
		portalSystemUrlUpdate.setUpdaterName("user1");
		//portalSystemUrlUpdate.setUpdateDate(updateDate);
		
		updateI18nMessageList = new ArrayList<I18nMessage>();

		readParam = new HashMap<String, String>();
		readParam.put("fieldName", "systemUrlName");
		readParam.put("localeCode", "ko");
		readParam.put("systemUrlId", portalSystemUrlCreate.getSystemUrlId());
		
		searchCondition = new PortalSystemUrlSearchCondition();
		searchCondition.setFieldName("systemUrlName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#listBySearchCondition(com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchConditio)}.
	 */
	@Test
	public void testListBySearchCondition() {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);
		
		List<PortalSystemUrl> result = portalSystemUrlDAO.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#countBySearchCondition(com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchConditio)}.
	 */
	@Test
	public void testCountBySearchCondition() {
		
		Integer tempCount = portalSystemUrlDAO.countBySearchCondition(searchCondition);
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);
		
		Integer count = portalSystemUrlDAO.countBySearchCondition(searchCondition);
		
		assertTrue(count > tempCount);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#read(java.util.Map)}.
	 */
	@Test
	public void testRead() {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);
		
		PortalSystemUrl result = portalSystemUrlDAO.read(readParam);
		
		assertNotNull(result);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#create(com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl)}.
	 */
	@Test
	public void testCreate() {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);	
		
		PortalSystemUrl result = portalSystemUrlDAO.read(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#update(com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl)}.
	 */
	@Test
	public void testUpdate() {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> createI18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(createI18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);
		
		PortalSystemUrl tempCreate = portalSystemUrlDAO.read(readParam);
		
		updateI18nMessageList = i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlUpdate.getSystemUrlId(), "systemUrlName");
		
		for(I18nMessage i18nMessage : updateI18nMessageList) {			
			i18nMessage.setItemMessage("Update PortalSystemUrl("+i18nMessage.getLocaleCode()+")");
		}
		
		portalSystemUrlUpdate.setI18nMessageList(updateI18nMessageList);
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
	    //화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
	    List <I18nMessage> updateI18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlUpdate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlUpdate.getSystemUrlId());

	    //다국어정보를 저장합니다.
	    i18nMessageService.update(updateI18nMessageList);
		
		portalSystemUrlDAO.update(portalSystemUrlUpdate);
		
		PortalSystemUrl tempUpdate = portalSystemUrlDAO.read(readParam);
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);
		
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		
		portalSystemUrlDAO.delete(portalSystemUrlCreate.getSystemUrlId());
		
		PortalSystemUrl result = portalSystemUrlDAO.read(readParam);
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDAO#readSystemUrlId(java.lang.String)}.
	 */
	@Test
	public void testReadSystemUrl() {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrlCreate.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrlCreate.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDAO.create(portalSystemUrlCreate);
		
		String result = portalSystemUrlDAO.readSystemUrlId(portalSystemUrlCreate.getUrl());
		
		assertEquals(portalSystemUrlCreate.getSystemUrlId(), result);
		
	}
}