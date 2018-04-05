package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;

public class PortalPageServiceTest extends BaseServiceTestCase{
	
	private PortalPage portalPage;
	
	private PortalPage updatePortalPage;
	
	private PortalSecurity security;
	
	private PortalSecurity updateSecurity;
	
	private PortalSystemUrl portalSystemUrl;
	
	private List<I18nMessage> i18nMessageList;
	
	private List<I18nMessage> i18nMessageSystemUrlList;
	
	private String userLocaleCode;
	
	private List<I18nMessage> i18nMessageListPage;
	
	private PortalPageSearchCondition searchCondition;
	
	private User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUserId("user1");
		user.setUserName("사용자1");
		
		searchCondition = new PortalPageSearchCondition();
		
		i18nMessageListPage = new ArrayList<I18nMessage>();
		
		I18nMessage i18nMessage1 = new I18nMessage();
		i18nMessage1.setFieldName("pageName");
		i18nMessage1.setLocaleCode("ko");
		i18nMessage1.setItemMessage("TEST PAGE ko");
		
		i18nMessageListPage.add(i18nMessage1);
		
		portalPage = new PortalPage();
		
		portalPage.setPageId("1");
		portalPage.setPageName("TEST PAGE ko");
		portalPage.setCommon(1);
		portalPage.setSystemCode("S000001");
		portalPage.setOwnerId("user1");
		portalPage.setRegisterId("user1");
		portalPage.setRegisterName("사용자1");
		portalPage.setUpdaterId("user1");
		portalPage.setUpdaterName("사용자1");
		portalPage.setI18nMessageList(i18nMessageListPage);
		
		security = new PortalSecurity();
		security.setAclResourcePermissionRead(new ACLResourcePermission());
		security.setResourceName(portalPage.getPageId());
		security.setResourceDescription(portalPage.getPageName());
		security.setClassName("Portal-Page");
		security.setOperationName("READ");
		security.setOwnerId("user1");
		security.setOwnerName("사용자1");
		
		portalPage.setSecurity(security);
		
		portalSystemUrl = new PortalSystemUrl();
		portalSystemUrl.setSystemUrlId("1");
		portalSystemUrl.setUrl("/portal/main/body.do?pageId="+portalPage.getPageId());
		portalSystemUrl.setSystemUrlName(portalPage.getPageName());
		portalSystemUrl.setRegisterId("user1");
		portalSystemUrl.setRegisterName("사용자1");
		portalSystemUrl.setUpdaterId("user1");
		portalSystemUrl.setUpdaterName("사용자1");
		
		i18nMessageSystemUrlList = new ArrayList<I18nMessage>();
		
		for(I18nMessage i18nMessage : i18nMessageListPage) {
			I18nMessage i18nMessageSystemUrl = new I18nMessage();
			
			i18nMessageSystemUrl.setItemMessage(i18nMessage.getItemMessage());
			i18nMessageSystemUrl.setLocaleCode(i18nMessage.getLocaleCode());
			i18nMessageSystemUrl.setFieldName("systemUrlName");
			i18nMessageSystemUrl.setItemId(portalSystemUrl.getSystemUrlId());
			i18nMessageSystemUrl.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_PORTAL);
			i18nMessageSystemUrl.setRegisterId("user1");
			i18nMessageSystemUrl.setRegisterName("사용자1");
			i18nMessageSystemUrl.setUpdaterId("user1");
			i18nMessageSystemUrl.setUpdaterName("사용자1");
			
			i18nMessageSystemUrlList.add(i18nMessageSystemUrl);	
		}
		
		portalSystemUrl.setI18nMessageList(i18nMessageSystemUrlList);
		
		updatePortalPage = new PortalPage();
		updatePortalPage.setPageId("1");
		updatePortalPage.setPageName("update TEST PAGE ko");
		updatePortalPage.setCommon(1);
		updatePortalPage.setSystemCode("S000001");
		updatePortalPage.setOwnerId("user1");
		updatePortalPage.setRegisterId("user1");
		updatePortalPage.setRegisterName("사용자1");
		updatePortalPage.setUpdaterId("user10");
		updatePortalPage.setUpdaterName("사용자10");
		
		updateSecurity = new PortalSecurity();
		updateSecurity.setAclResourcePermissionRead(new ACLResourcePermission());
		updateSecurity.setResourceName(updatePortalPage.getPageId());
		updateSecurity.setResourceDescription(updatePortalPage.getPageName());
		updateSecurity.setClassName("Portal-Page");
		updateSecurity.setOperationName("READ");
		updateSecurity.setOwnerId("user1");
		updateSecurity.setOwnerName("사용자1");
		
		updatePortalPage.setSecurity(updateSecurity);
				
		userLocaleCode = "ko";
	}
	
	@Autowired
	private PortalPageService portalPageService;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Test
	public void testCreatePage() {
		i18nMessageList = i18nMessageService.fillMandatoryField(i18nMessageListPage, IKepConstant.ITEM_TYPE_CODE_PORTAL, "1");
		
		portalPageService.createPage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl);
		
		PortalPage result = portalPageService.readPage(portalPage.getPageId(), userLocaleCode);
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdatePage() {
		i18nMessageList = i18nMessageService.fillMandatoryField(i18nMessageListPage, IKepConstant.ITEM_TYPE_CODE_PORTAL, "1");
		
		portalPageService.createPage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl);
		
		portalPageService.updatePage(i18nMessageList, updatePortalPage, i18nMessageSystemUrlList, portalSystemUrl, false);
		
		PortalPage updateResult = portalPageService.readPage(portalPage.getPageId(), userLocaleCode);
		
		assertEquals(updatePortalPage.getUpdaterId(), updateResult.getUpdaterId());
	}
	
	@Test
	public void testReadPage() {
		i18nMessageList = i18nMessageService.fillMandatoryField(portalPage.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPage.getPageId());
		
		portalPageService.createPage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl);
		
		PortalPage result = portalPageService.readPage(portalPage.getPageId(), userLocaleCode);
		
		assertNotNull(result);
	}
	
	@Test
	public void testRemovePage() {
		i18nMessageList = i18nMessageService.fillMandatoryField(i18nMessageListPage, IKepConstant.ITEM_TYPE_CODE_PORTAL, "1");
		
		portalPageService.createPage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl);
		
		portalPageService.removePage(portalPage.getPageId());
		
		PortalPage result = portalPageService.readPage(portalPage.getPageId(), userLocaleCode);
		
		assertNull(result);
	}

	@Test
	public void testListPage(){
		SearchResult<PortalPage> result = portalPageService.listPage(searchCondition);
		
		assertNotNull(result);
	}
	
	@Test
	public void testReadPageId() {
		String result = portalPageService.readPageId("P000001");
		
		assertNotNull(result);
	}
	
	@Test
	public void testReadPortletDefaultMain() {
		String portalId = "P000001";
		PortalMain result = portalPageService.readPortletDefaultMain("admin", userLocaleCode, portalPage.getPageId(), user, portalId);
		
		assertNotNull(result);
	}
}