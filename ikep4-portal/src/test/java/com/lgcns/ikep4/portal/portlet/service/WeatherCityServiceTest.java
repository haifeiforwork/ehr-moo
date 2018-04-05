package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;

public class WeatherCityServiceTest extends BaseServiceTestCase {

	@Before
	public void setUp() {
		
	}
	
	@Autowired
	private WeatherCityService weatherCityService;
	
	@Test
	public void testListWeatherNationCity() {
		List<WeatherCity> result = weatherCityService.listWeatherNationCity("KOR");
		
		assertNotNull(result);
	}
}
