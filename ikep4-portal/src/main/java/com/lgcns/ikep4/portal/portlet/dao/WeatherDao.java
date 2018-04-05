package com.lgcns.ikep4.portal.portlet.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.Weather;

/**
 * 
 * 날씨 포틀릿 설정 DAO
 *
 * @author 임종상
 * @version $Id: WeatherDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface WeatherDao extends GenericDao<Weather, String> {
	
	/**
	 * 날씨 포틀릿 설정  조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return Weather 날씨 포틀릿 설정 정보
	 */
	public Weather getWeather(String portletConfigId);
	
	/**
	 * 날씨 포틀릿 설정  등록
	 * @param weather 날씨 포틀릿 model
	 */
	public void createWeather(Weather weather);
	
	/**
	 * 날씨 포틀릿 설정  수정
	 * @param weather 날씨 포틀릿 model
	 */
	public void updateWeather(Weather weather);
}