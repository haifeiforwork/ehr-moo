package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.WeatherInfoDao;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;

/**
 * 
 * 날씨 포틀릿 정보 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: WeatherInfoDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("weatherInfoDao")
public class WeatherInfoDaoImpl extends GenericDaoSqlmap<WeatherInfo, String> implements WeatherInfoDao {

	/**
	 * 날씨 포틀릿 정보 전체 삭제
	 */
	public void removeWeatherInfoAll() {
		sqlDelete("portal.portlet.weatherInfo.removeWeatherInfoAll");
	}
	
	/**
	 * 날씨 포틀릿 정보 등록
	 * @param portletConfigId 포틀릿 config ID
	 */
	public void createWeatherInfo(Map<String, String> weatherMap) {
		sqlInsert("portal.portlet.weatherInfo.createWeatherInfo", weatherMap);
	}
	
	/**
	 * 설정한 날씨 포틀릿 정보 리스트
	 * @param map
	 * @return List<WeatherInfo> 설정한 날씨 포틀릿 정보 목록
	 */
	public List<WeatherInfo> listWeatherInfo(Map<String, Object> map) {
		return sqlSelectForList("portal.portlet.weatherInfo.listWeatherInfo", map);
	}
	
	@Deprecated
	public WeatherInfo get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(WeatherInfo object) {
		return null;
	}

	@Deprecated
	public void update(WeatherInfo object) {}

	@Deprecated
	public void remove(String id) {}

}