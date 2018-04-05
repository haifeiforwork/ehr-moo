package com.lgcns.ikep4.portal.portlet.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.WeatherDao;
import com.lgcns.ikep4.portal.portlet.model.Weather;

/**
 * 
 * 날씨 포틀릿 설정 정보 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: WeatherDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */

@Repository("weatherDao")
public class WeatherDaoImpl extends GenericDaoSqlmap<Weather, String> implements WeatherDao {

	/**
	 * 날씨 포틀릿 설정  조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return Weather 날씨 포틀릿 설정 정보
	 */
	public Weather getWeather(String portletConfigId) {
		return (Weather) sqlSelectForObject("portal.portlet.weather.getWeather", portletConfigId);
	}
	
	/**
	 * 날씨 포틀릿 설정  등록
	 * @param weather 날씨 포틀릿 model
	 */
	public void createWeather(Weather weather) {
		sqlInsert("portal.portlet.weather.createWeather", weather);
	}

	/**
	 * 날씨 포틀릿 설정  수정
	 * @param weather 날씨 포틀릿 model
	 */
	public void updateWeather(Weather weather) {
		sqlUpdate("portal.portlet.weather.updateWeather", weather);
	}
	
	@Deprecated
	public Weather get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	@Deprecated
	public String create(Weather object) {
		return null;
	}

	@Deprecated
	public void update(Weather object) {}

	@Deprecated
	public void remove(String id) {}
}