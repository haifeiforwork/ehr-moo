package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortletCongratulationsService 테스트 케이스
 *
 * @author 박철종
 * @version $Id: PortletCongratulationsServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortletCongratulationsServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private CongratulationsService congratulationsService;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
    
    private MockHttpServletRequest req;
    
    private PortalPortletConfig portalPortletConfig;
	
	private PortalPageLayout portalPageLayout;
	
	private Congratulations congratulationsCreate;
	
	@Before
	public void setUp() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
	    user.setUserId("user1");
	    user.setUserName("user1");
	    user.setTimezoneId("GMT000000000");
	    user.setTimeDifference("0");
	    user.setLocaleCode("ko");
	    
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	    // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
        
        portalPageLayout = new PortalPageLayout();
		portalPageLayout.setPageLayoutId("1234567890");
		portalPageLayout.setPageId("100000126387");
		portalPageLayout.setLayoutId("100000000393");
		portalPageLayout.setOwnerId("user1");
		portalPageLayout.setColIndex(1);
		portalPageLayout.setWidth(32.5);
		portalPageLayout.setRegisterId("user1");
		portalPageLayout.setRegisterName("user1");
		portalPageLayout.setUpdaterId("user1");
		portalPageLayout.setUpdaterName("user1");
			
		portalPortletConfig = new PortalPortletConfig();
		portalPortletConfig.setPortletConfigId("9876543210");
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setPageLayoutId("1234567890");
		portalPortletConfig.setPortletId("100000713667");
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("user1");
		portalPortletConfig.setRegisterName("user1");
		portalPortletConfig.setUpdaterId("user1");
		portalPortletConfig.setUpdaterName("user1");
		
		congratulationsCreate = new Congratulations();
		congratulationsCreate.setPortletConfigId("9876543210");
		congratulationsCreate.setPropertyName("PERIOD");
		congratulationsCreate.setPropertyValue("WEEK");
		congratulationsCreate.setRegisterId("user1");
		congratulationsCreate.setUpdaterId("user1");
		congratulationsCreate.setUserId("user1");
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.CongratulationsService#list(java.lang.String, com.lgcns.ikep4.support.user.member.model.User)}.
	 */
	@Test
	public void testList() {
		
		User userInfo = userDao.get("admin");
		
		String pattern = "MM.dd";
        // 현재 시스템 시각을 사용자 시간대로 변환하고 pattern 형태의 문자열을 리턴함, 예제에서는 yyyy-MM-dd
        String currentDate = timeZoneSupportService.convertTimeZoneToString(pattern);
        
		userInfo.setBirthday(currentDate);
		userInfo.setWeddingAnniv(currentDate);
		userInfo.setTeamName("Test Team");
		
		userDao.update(userInfo);
		
		User userParam = new User();
		userParam = userInfo;
		
		assertNotNull(congratulationsService.list(null, userParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.CongratulationsService#getCongratulationsConfig(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetCongratulationsConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		congratulationsService.saveCongratulationsConfig(congratulationsCreate);
		
		assertNotNull(congratulationsService.getCongratulationsConfig(portalPortletConfig.getPortletConfigId(), congratulationsCreate.getRegisterId(), "priod"));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.CongratulationsService#saveCongratulationsConfig(com.lgcns.ikep4.portal.portlet.model.Congratulations)}.
	 */
	@Test
	public void testSaveCongratulationsConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		congratulationsService.saveCongratulationsConfig(congratulationsCreate);
		
		assertNotNull(congratulationsService.getCongratulationsConfig(portalPortletConfig.getPortletConfigId(), congratulationsCreate.getRegisterId(), "priod"));
		
	}
	
}