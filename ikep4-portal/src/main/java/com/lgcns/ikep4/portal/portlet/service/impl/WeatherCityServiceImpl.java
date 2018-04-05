package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.WeatherCityDao;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;
import com.lgcns.ikep4.portal.portlet.service.WeatherCityService;

/**
 * 
 * 날씨 포틀릿 도시 정보 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: WeatherCityServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("weatherCityService")
public class WeatherCityServiceImpl extends GenericServiceImpl<WeatherCity, String> implements WeatherCityService {

	@Autowired
	private WeatherCityDao weatherCityDao;

	/**
	 * 날씨 포틀릿 국가별 도시 리스트 조회
	 * @param nationCode 국가 코드
	 * @return List<WeatherCity> 도시정보 목록
	 */
	public List<WeatherCity> listWeatherNationCity(String nationCode) {
		return weatherCityDao.listWeatherNationCity(nationCode);
	}
	
	
}