/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.portlet.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.Calendar;
import java.util.Date;
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

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortletCongratulationsDao 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortletCongratulationsDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortletCongratulationsDaoTest extends BaseDaoTestCase {

	@Autowired
	private CongratulationsDao congratulationsDao;

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
	
	private Congratulations congratulationsUpdate;
	
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
		portalPageLayout.setRegisterId("admin");
		portalPageLayout.setRegisterName("admin");
		portalPageLayout.setUpdaterId("admin");
		portalPageLayout.setUpdaterName("admin");
			
		portalPortletConfig = new PortalPortletConfig();
		portalPortletConfig.setPortletConfigId("9876543210");
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setPageLayoutId("1234567890");
		portalPortletConfig.setPortletId("100000713667");
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("admin");
		portalPortletConfig.setRegisterName("admin");
		portalPortletConfig.setUpdaterId("admin");
		portalPortletConfig.setUpdaterName("admin");
		
		congratulationsCreate = new Congratulations();
		congratulationsCreate.setPortletConfigId("9876543210");
		congratulationsCreate.setPropertyName("PERIOD");
		congratulationsCreate.setPropertyValue("WEEK");
		congratulationsCreate.setRegisterId("admin");
		congratulationsCreate.setUpdaterId("admin");
		congratulationsCreate.setUserId("admin");
		
		congratulationsUpdate = new Congratulations();
		congratulationsUpdate.setPortletConfigId("9876543210");
		congratulationsUpdate.setPropertyName("PERIOD");
		congratulationsUpdate.setPropertyValue("MONTH");
		congratulationsUpdate.setRegisterId("admin");
		congratulationsUpdate.setUpdaterId("admin");
		congratulationsUpdate.setUserId("admin");
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.CongratulationsDao#getPortletConfigByUserId(java.util.Map)}.
	 */
	@Test
	public void testGetPortletConfigByUserId() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		congratulationsDao.createUserConfig(congratulationsCreate);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", congratulationsCreate.getPortletConfigId());
		param.put("propertyName", "PERIOD");
		param.put("userId", congratulationsCreate.getUserId());
		
		assertNotNull(congratulationsDao.getPortletConfigByUserId(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.CongratulationsDao#list(java.util.Map)}.
	 */
	@Test
	public void testList() {
		
		User userInfo = userDao.get("admin");
		
		String pattern = "yyyy-MM-dd";
        // 현재 시스템 시각을 사용자 시간대로 변환하고 pattern 형태의 문자열을 리턴함, 예제에서는 yyyy-MM-dd
        String currentDate = timeZoneSupportService.convertTimeZoneToString(pattern);
        
		userInfo.setBirthday(currentDate);
		userInfo.setWeddingAnniv(currentDate);
		userInfo.setTeamName("Test Team");
		
		userDao.update(userInfo);
		
		User userParam = new User();
		userParam = userInfo;
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		congratulationsDao.createUserConfig(congratulationsCreate);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", congratulationsCreate.getPortletConfigId());
		param.put("propertyName", "PERIOD");
		param.put("userId", userParam.getUserId());	
		
		Congratulations congratulations = new Congratulations();
		congratulations = congratulationsDao.getPortletConfigByUserId(param);
		
		String period = congratulations.getPropertyValue();
		
		// 현재 시스템 시각을 사용자 시간대로 변환된 시각(Date)를 리턴함
        Date date = timeZoneSupportService.convertTimeZone();   
        
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        
        String startDate = "";
		Calendar startCalendar = today;
	
		String startDay = String.valueOf(startCalendar.get(Calendar.DATE));
		String startMonth = String.valueOf(startCalendar.get(Calendar.MONTH) + 1);
				   
		if(startDay.length() == 1) {
			startDay = "0" + startDay;   
		}
		                      
		if(startMonth.length() == 1) {
			startMonth = "0" + startMonth;
		}
		
		startDate = startMonth+"-"+startDay;
		
		String endDate = "";
		Calendar endCalendar = today;
		
		if(period == null || period.equals("WEEK")) {
			endCalendar.add(Calendar.DATE, 7);
	
			String endDay = String.valueOf(endCalendar.get(Calendar.DATE));
			String endMonth = String.valueOf(endCalendar.get(Calendar.MONTH) + 1);
			
			if(endDay.length() == 1) {
				endDay = "0" + endDay;   
			}
			                      
			if(endMonth.length() == 1) {
				endMonth = "0" + endMonth;
			}
			
			endDate = endMonth+"-"+endDay;
		} else if(period.equals("MONTH")) {
			endCalendar.add(Calendar.MONTH, 1);
			
			String endDay = String.valueOf(endCalendar.get(Calendar.DATE));
			String endMonth = String.valueOf(endCalendar.get(Calendar.MONTH) + 1);
			
			if(endDay.length() == 1) {
				endDay = "0" + endDay;   
			}
			
			if(endMonth.length() == 1) {
				endMonth = "0" + endMonth;
			}
			
			endDate = endMonth+"-"+endDay;
		}
        
		List<Congratulations> list = null;
		Map<String, String> map = new HashMap<String, String>();
		
		if(congratulations == null || (congratulations != null && congratulations.getPropertyValue().equals("WEEK"))) {   
	        map.put("resultColumn", "WEDDING_ANNIV");
			map.put("portalId", userParam.getPortalId());
			map.put("groupId", userParam.getGroupId());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			list = congratulationsDao.list(map);
		} else if(congratulations != null && congratulations.getPropertyValue().equals("MONTH")) {
	        map.put("resultColumn", "WEDDING_ANNIV");
			map.put("portalId", userParam.getPortalId());
			map.put("groupId", userParam.getGroupId());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			list = congratulationsDao.list(map);
		}
		
		assertNotNull(list);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.CongratulationsDao#createUserConfig(com.lgcns.ikep4.portal.portlet.model.Congratulations)}.
	 */
	@Test
	public void testCreateUserConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		congratulationsDao.createUserConfig(congratulationsCreate);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", congratulationsCreate.getPortletConfigId());
		param.put("propertyName", "PERIOD");
		param.put("userId", congratulationsCreate.getUserId());
		
		assertNotNull(congratulationsDao.getPortletConfigByUserId(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.CongratulationsDao#updateUserConfig(com.lgcns.ikep4.portal.portlet.model.Congratulations)}.
	 */
	@Test
	public void testUpdateUserConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		congratulationsDao.createUserConfig(congratulationsCreate);
		congratulationsDao.updateUserConfig(congratulationsUpdate);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", congratulationsCreate.getPortletConfigId());
		param.put("propertyName", "PERIOD");
		param.put("userId", congratulationsCreate.getUserId());
		
		Congratulations tempCongratulations = new Congratulations();
		tempCongratulations = congratulationsDao.getPortletConfigByUserId(param);
		
		assertNotSame(congratulationsCreate, tempCongratulations);
		
	}
	
}