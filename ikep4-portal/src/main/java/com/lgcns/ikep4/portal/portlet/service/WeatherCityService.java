package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;

/**
 * 
 * 날씨 포틀릿 도시 정보 Service
 *
 * @author 임종상
 * @version $Id: WeatherCityService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface WeatherCityService extends GenericService<WeatherCity, String> {
	
	/**
	 * 날씨 포틀릿 국가별 도시 리스트 조회
	 * @param nationCode 국가 코드
	 * @return List<WeatherCity> 도시정보 목록
	 */
	public List<WeatherCity> listWeatherNationCity(String nationCode);
}