package com.lgcns.ikep4.portal.portlet.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.Weather;

/**
 * 
 * 날씨 포틀릿 설정 Service
 *
 * @author 임종상
 * @version $Id: WeatherService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface WeatherService extends GenericService<Weather, String> {
	
	/**
	 * 날씨 설정 조회
	 * @param portletConfigId 포틀릿 config ID 
	 * @return Weather 날씨 설정 정보
	 */
	public Weather readWeather(String portletConfigId);
	
	/**
	 * 날씨 설정 등록
	 * @param weather 날씨 포틀릿 model
	 */
	public void createWeather(Weather weather);
	
	/**
	 * 날씨 설정 수정
	 * @param weather 날씨 포틀릿 model
	 */
	public void updateWeather(Weather weather);
}
