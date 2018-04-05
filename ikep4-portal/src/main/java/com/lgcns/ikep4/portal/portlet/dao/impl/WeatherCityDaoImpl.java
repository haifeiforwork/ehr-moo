package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.WeatherCityDao;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;

/**
 * 
 * 날씨 포틀릿 도시 정보 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: WeatherCityDaoImpl.java 17444 2012-03-12 06:54:06Z arthes $
 */
@Repository("weatherCityDao")
public class WeatherCityDaoImpl extends GenericDaoSqlmap<WeatherCity, String> implements WeatherCityDao {

	/**
	 * 날씨 포틀릿 도시 리스트
	 * @return List<WeatherCity> 날씨 포틀릿 도시 목록
	 */
	public List<WeatherCity> listWeatherCity() {
		return sqlSelectForList("portal.portlet.weatherCity.listWeatherCity");
	}
	
	
	/**
	 * 날씨 포틀릿 도시 리스트
	 * @return List<WeatherCity> 날씨 포틀릿 도시 목록
	 */
	public List<WeatherCity> listWeatherCityByIds(List<String> cityIdList) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("cityIdList", cityIdList);
		return sqlSelectForList("portal.portlet.weatherCity.listWeatherCityByIds",map);
	}
	
	/**
	 * 날씨 포틀릿 국가별 도시 리스트
	 * @param nationCode 국가 코드
	 * @return List<WeatherCity> 날씨 포틀릿 국가별 도시 목록
	 */
	public List<WeatherCity> listWeatherNationCity(String nationCode) {
		return sqlSelectForList("portal.portlet.weatherCity.listWeatherNationCity", nationCode);
	}
	
	@Deprecated
	public WeatherCity get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(WeatherCity object) {
		return null;
	}

	@Deprecated
	public void update(WeatherCity object) {}
	
	@Deprecated
	public void remove(String id) {}
}