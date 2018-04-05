package com.lgcns.ikep4.portal.portlet.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;

/**
 * 
 * 날씨 포틀릿 도시 정보 DAO
 *
 * @author 임종상
 * @version $Id: WeatherCityDao.java 17444 2012-03-12 06:54:06Z arthes $
 */
public interface WeatherCityDao extends GenericDao<WeatherCity, String> {
	
	/**
	 * 날씨 포틀릿 도시 리스트
	 * @return List<WeatherCity> 날씨 포틀릿 도시 목록
	 */
	public List<WeatherCity> listWeatherCity();
	
	/**
	 * 날씨 포틀릿 도시 리스트
	 * @return List<WeatherCity> 날씨 포틀릿 도시 목록
	 */
	public List<WeatherCity> listWeatherCityByIds(List<String> cityIdList);
	
	/**
	 * 날씨 포틀릿 국가별 도시 리스트
	 * @param nationCode 국가 코드
	 * @return List<WeatherCity> 날씨 포틀릿 국가별 도시 목록
	 */
	public List<WeatherCity> listWeatherNationCity(String nationCode);
}