package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;

public class PortalServiceTest extends BaseServiceTestCase{

	@Autowired
	private ACLService aclService;
	
	private Portal portal;
	
	private Portal updatePortal;
	
	private PortalSearchCondition searchCondition;
	
	private List<I18nMessage> i18nMessageListSystem;
	
	private List<I18nMessage> i18nMessageListPage;
	
	private List<I18nMessage> i18nMessageSystemUrlList;
	
	private List<I18nMessage> i18nMessageList;
	
	private PortalPage portalPage;
	
	private PortalSystemUrl portalSystemUrl;
	
	private PortalSystem portalSystem;
	
	@Before
	public void setUp() {
		searchCondition = new PortalSearchCondition();
		
		portal = new Portal();
		
		portal.setPortalId("1");
		portal.setPortalName("테스트 포탈");
		portal.setDescription("테스트 포탈");
		portal.setDefaultOption(0);
		portal.setLoginImageId("2");
		portal.setLogoImageId("3");
		portal.setPortalHost("test.lgcns.com");
		portal.setPortalPath("/portalPath");
		portal.setActive(1);
		portal.setRegisterId("admin");
		portal.setRegisterName("관리자");
		portal.setUpdaterId("admin");
		portal.setUpdaterName("관리자");
		portal.setDefaultLocaleCode("ko");
		
		updatePortal = new Portal();
		
		updatePortal.setPortalId("1");
		updatePortal.setPortalName("update테스트 포탈");
		updatePortal.setDescription("update테스트 포탈");
		updatePortal.setDefaultOption(0);
		updatePortal.setLoginImageId("2");
		updatePortal.setLogoImageId("3");
		updatePortal.setPortalHost("ikep.lgcns.com");
		updatePortal.setPortalPath("/updatePortalPath");
		updatePortal.setActive(0);
		updatePortal.setRegisterId("admin");
		updatePortal.setRegisterName("관리자");
		updatePortal.setUpdaterId("admin");
		updatePortal.setUpdaterName("관리자");
		updatePortal.setDefaultLocaleCode("en");
		updatePortal.setAddAdmins("[{\"userId\":\"createUserId\",\"userPassword\":\"portal\",\"userName\":\"createUserName\"}]");
		
		//시스템 생성시 다국어 등록
		i18nMessageListSystem = new ArrayList<I18nMessage>();
		
		portalSystem = new PortalSystem();
		portalSystem.setPortalId(portal.getPortalId());
		portalSystem.setSystemCode("1");
		portalSystem.setSystemName("Portal");
		portalSystem.setDescription("Portal");
		portalSystem.setSystemType("ITEM");
		portalSystem.setMainView(1);
		portalSystem.setUrlType("URL");
		portalSystem.setUrl("/portal/main/portalMain.do");
		portalSystem.setSortOrder("0000000000002");
		portalSystem.setTarget("INNER");
		portalSystem.setParentSystemCode("S000000");
		portalSystem.setOwnerId("admin");
		portalSystem.setRegisterId("admin");
		portalSystem.setRegisterName("관리자");
		portalSystem.setUpdaterId("admin");
		portalSystem.setUpdaterName("관리자");
		portalSystem.setI18nMessageList(i18nMessageListSystem);
		
		ACLResourcePermission aCLResourcePermissionReadSystem = new ACLResourcePermission();
		aCLResourcePermissionReadSystem.setOpen(1);
		
		PortalSecurity portalSecuritySystem = new PortalSecurity();
		portalSecuritySystem.setClassName("Portal-System");
		portalSecuritySystem.setOperationName("READ");
		portalSecuritySystem.setOwnerId("admin");
		portalSecuritySystem.setOwnerName("관리자");
		portalSecuritySystem.setAclResourcePermissionRead(aCLResourcePermissionReadSystem);
		
		portalSystem.setSecurity(portalSecuritySystem);
		
		//페이지 생성시 다국어 등록
		i18nMessageListPage = new ArrayList<I18nMessage>();
		
		portalPage = new PortalPage();
		portalPage.setPageId("1");
		portalPage.setPageName("PortalPage");
		portalPage.setCommon(1);
		portalPage.setOwnerId("admin");
		portalPage.setRegisterId("admin");
		portalPage.setRegisterName("관리자");
		portalPage.setUpdaterId("admin");
		portalPage.setUpdaterName("관리자");
		portalPage.setI18nMessageList(i18nMessageListPage);
		
		ACLResourcePermission aCLResourcePermissionReadPage = new ACLResourcePermission();
		aCLResourcePermissionReadPage.setOpen(1);
		
		PortalSecurity portalSecurityPage = new PortalSecurity();
		portalSecurityPage.setClassName("Portal-Page");
		portalSecurityPage.setOperationName("READ");
		portalSecurityPage.setOwnerId("admin");
		portalSecurityPage.setOwnerName("관리자");
		portalSecurityPage.setAclResourcePermissionRead(aCLResourcePermissionReadPage);
		
		portalPage.setSecurity(portalSecurityPage);
		
		//VO에서 받아온 메세지를 사용
		i18nMessageList = i18nMessageService.fillMandatoryField(portalPage.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPage.getPageId());
		
		portalSystemUrl = new PortalSystemUrl();
		portalSystemUrl.setSystemUrlId("1");
		portalSystemUrl.setUrl("/portal/main/body.do?pageId="+portalPage.getPageId());
		portalSystemUrl.setSystemUrlName(portalPage.getPageName());
		portalSystemUrl.setRegisterId("admin");
		portalSystemUrl.setRegisterName("관리자");
		portalSystemUrl.setUpdaterId("admin");
		portalSystemUrl.setUpdaterName("관리자");
		
		// 필수 입력 정보를 채운다 .(시스템 URL)
		i18nMessageSystemUrlList = new ArrayList<I18nMessage>();
	}
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	//포탈 로고 이미지 ID 조회
	@Test
	public void testReadLogoImageId() {
		String result = portalService.readLogoImageId(portal.getPortalId());
		
		assertNotNull(result);
	}
	
	//포탈 생성
	@Test
	public void testCreatePortal() {
		portalService.createPortal(portal);
		Portal result = portalService.readPortal(portal.getPortalId());
		
		assertNotNull(result);
	}
	
	//포탈 조회
	@Test
	public void testReadPortal() {
		portalService.createPortal(portal);
		Portal result = portalService.readPortal(portal.getPortalId());
		
		assertNotNull(result);
	}
	
	//포탈 수정
	@Test
	public void testUpdatePortal() {
		portalService.createPortal(portal);
		
		updatePortal.setPortalId(portal.getPortalId());
		
		portalService.updatePortal(updatePortal);
		Portal updateResult = portalService.readPortal(updatePortal.getPortalId());
		
		assertEquals(updatePortal.getDefaultLocaleCode(), updateResult.getDefaultLocaleCode());
	}
	
	//포탈 리스트
	@Test
	public void testListPortal() {
		portalService.createPortal(portal);
		SearchResult<Portal> result = portalService.listPortal(searchCondition);
		
		assertNotNull(result);
	}
	
	//포탈 리스트
	@Test
	public void testListPortalAdminPermission() {
		
		portalService.createPortal(portal);
		
		updatePortal.setPortalId(portal.getPortalId());
		
		portalService.updatePortal(updatePortal);
		
		String className = "Portal";
		String resourceName = "Portal";
		String operationName = "MANAGE";
		
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
		
		// 리소스에 대한 권한 정보를 읽어 온다.
		aclResourcePermission = aclService.getSystemPermission(className, resourceName, operationName);
		
		// 권한에 대한 상세정보를 조회 한다.
		if(aclResourcePermission != null)
		{
			aclResourcePermission = portalService.listPortalAdminPermission(aclResourcePermission, portal.getPortalId());
		}
		
		assertTrue(aclResourcePermission.getAssignUserDetailList().size() > 0);
	}
	
	//포탈 삭제
	@Test
	public void testRemovePortal() {
		portalService.createPortal(portal);
		portalService.removePortal(portal.getPortalId());
		Portal result = portalService.readPortal(portal.getPortalId());
		
		assertNull(result);
	}
	
	//도메인으로 포탈 조회
	@Test
	public void testReadPortalDomain() {
		portalService.createPortal(portal);
		Portal result = portalService.readPortalDomain(portal.getPortalHost());
		
		assertNotNull(result);
	}
	
	//디폴트 포탈 조회
	@Test
	public void testReadPortalDefault() {
		Portal result = portalService.readPortalDefault();
		
		assertNotNull(result);
	}
	
	//ContextPath 로 포탈 조회
	@Test
	public void testReadPortalContextPath() {
		portalService.createPortal(portal);
		Portal result = portalService.readPortalContextPath(portal.getPortalPath());
		
		assertNotNull(result);
	}
}
