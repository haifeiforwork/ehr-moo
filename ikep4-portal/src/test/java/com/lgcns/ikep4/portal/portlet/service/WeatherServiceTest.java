package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.portlet.model.Weather;

public class WeatherServiceTest extends BaseServiceTestCase {

	private Weather weather;
	
	private Weather updateWeather;
	
	private PortalPortletConfig portalPortletConfig;
	
	private PortalPageLayout portalPageLayout;
	
	@Before
	public void setUp() {
		portalPageLayout = new PortalPageLayout();
		portalPageLayout.setPageLayoutId("1");
		portalPageLayout.setPageId("100000126387");
		portalPageLayout.setLayoutId("100000000393");
		portalPageLayout.setOwnerId("admin");
		portalPageLayout.setColIndex(1);
		portalPageLayout.setWidth(32.5);
		portalPageLayout.setRegisterId("admin");
		portalPageLayout.setRegisterName("관리자");
		portalPageLayout.setUpdaterId("admin");
		portalPageLayout.setUpdaterName("관리자");
		
		
		portalPortletConfig = new PortalPortletConfig();
		portalPortletConfig.setPortletConfigId("1");
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setPageLayoutId("1");
		portalPortletConfig.setPortletId("100000713667");
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("admin");
		portalPortletConfig.setRegisterName("관리자");
		portalPortletConfig.setUpdaterId("admin");
		portalPortletConfig.setUpdaterName("관리자");
		
		
		weather = new Weather();
		
		weather.setPortletConfigId("1");
		weather.setCityList("10,20");
		weather.setRegisterId("admin");
		weather.setRegisterName("관리자");
		weather.setUpdaterId("admin");
		weather.setUpdaterName("관리자");
		
		
		updateWeather = new Weather();
		
		updateWeather.setPortletConfigId("1");
		updateWeather.setCityList("30,40");
		updateWeather.setRegisterId("admin");
		updateWeather.setRegisterName("관리자");
		updateWeather.setUpdaterId("admin");
		updateWeather.setUpdaterName("관리자");
	}
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Test
	public void testReadWeather() {
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		weatherService.createWeather(weather);
		Weather result = weatherService.readWeather(weather.getPortletConfigId());
		
		assertNotNull(result);
	}
	
	@Test
	public void testCreateWeather() {
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		weatherService.createWeather(weather);
		Weather result = weatherService.readWeather(weather.getPortletConfigId());
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateWeather() {
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		weatherService.createWeather(weather);
		
		updateWeather.setPortletConfigId(weather.getPortletConfigId());
		
		weatherService.updateWeather(updateWeather);
		
		Weather updateResult = weatherService.readWeather(updateWeather.getPortletConfigId());
		
		assertEquals(updateWeather.getCityList(), updateResult.getCityList());
	}
}
