package com.lgcns.ikep4.portal.portlet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.WeatherDao;
import com.lgcns.ikep4.portal.portlet.model.Weather;
import com.lgcns.ikep4.portal.portlet.service.WeatherService;

/**
 * 
 * 날씨 포틀릿 설정 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: WeatherServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("weatherService")
public class WeatherServiceImpl extends GenericServiceImpl<Weather, String> implements WeatherService {

	@Autowired
	private WeatherDao weatherDao;
	
	/**
	 * 날씨 설정 조회
	 * @param portletConfigId 포틀릿 config ID 
	 * @return Weather 날씨 설정 정보
	 */
	public Weather readWeather(String portletConfigId) {
		return weatherDao.getWeather(portletConfigId);
	}

	/**
	 * 날씨 설정 등록
	 * @param weather 날씨 포틀릿 model
	 */
	public void createWeather(Weather weather) {
		weatherDao.createWeather(weather);
	}

	/**
	 * 날씨 설정 수정
	 * @param weather 날씨 포틀릿 model
	 */
	public void updateWeather(Weather weather) {
		weatherDao.updateWeather(weather);
	}
	
}