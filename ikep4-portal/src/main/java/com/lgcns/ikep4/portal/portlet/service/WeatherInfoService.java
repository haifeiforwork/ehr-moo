package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;

/**
 * 
 * 날씨 포틀릿 정보 Service
 *
 * @author 임종상
 * @version $Id: WeatherInfoService.java 19343 2012-06-20 01:25:39Z malboru80 $
 */
@Transactional
public interface WeatherInfoService extends GenericService<WeatherInfo, String> {
	
	/**
	 * 날씨 정보 등록
	 */
	public void createWeatherInfo();
	
	/**
	 * 설정한 날씨 정보 리스트
	 * @param map 
	 * @return List<WeatherInfo> 날씨 정보 목록
	 */
	public List<WeatherInfo> listWeatherInfo(Map<String, Object> map);
	
	/**
	 * Google Api 에서 바로 날씨 정보 리스트
	 * @param map 
	 * @return List<WeatherInfo> 날씨 정보 목록
	 */
	public List<Map> listWeatherInfoRealTime(Map<String, Object> map);
}