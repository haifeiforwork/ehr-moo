package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;

public class WeatherInfoServiceTest extends BaseServiceTestCase {

	private HashMap<String, Object> map;
	
	private List<String> cityIdList;
	
	@Autowired
	private WeatherInfoService weatherInfoService;
	
	
	@Before
	public void setUp() {
		cityIdList = new ArrayList<String>();
		cityIdList.add("100000805882");
		
		map = new HashMap<String, Object>();
		
		map.put("cityIdList", cityIdList);
		map.put("localeCode", "ko");
	}
	
	
	@Test
	//@Rollback(false)
	public void testCreateWeatherInfo() throws Exception {
		weatherInfoService.createWeatherInfo();
		
		List<WeatherInfo> result = weatherInfoService.listWeatherInfo(map);
		
		assertNotNull(result);
	}
	
	@Test
	//@Ignore
	public void testListWeatherInfo() {
		List<WeatherInfo> result = weatherInfoService.listWeatherInfo(map);
		
		assertNotNull(result);
	}
}
