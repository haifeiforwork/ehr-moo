package com.lgcns.ikep4.portal.portlet.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;

/**
 * 
 * 날씨 포틀릿 정보 DAO
 *
 * @author 임종상
 * @version $Id: WeatherInfoDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface WeatherInfoDao extends GenericDao<WeatherInfo, String> {
	
	/**
	 * 날씨 포틀릿 정보 전체 삭제
	 */
	public void removeWeatherInfoAll();
	
	/**
	 * 날씨 포틀릿 정보 등록
	 * @param portletConfigId 포틀릿 config ID
	 */
	public void createWeatherInfo(Map<String, String> weatherMap);
	
	/**
	 * 설정한 날씨 포틀릿 정보 리스트
	 * @param map
	 * @return List<WeatherInfo> 설정한 날씨 포틀릿 정보 목록
	 */
	public List<WeatherInfo> listWeatherInfo(Map<String, Object> map);
}
