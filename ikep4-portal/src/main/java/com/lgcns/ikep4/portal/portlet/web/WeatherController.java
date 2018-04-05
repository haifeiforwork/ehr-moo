package com.lgcns.ikep4.portal.portlet.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.Weather;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;
import com.lgcns.ikep4.portal.portlet.service.WeatherCityService;
import com.lgcns.ikep4.portal.portlet.service.WeatherInfoService;
import com.lgcns.ikep4.portal.portlet.service.WeatherService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.code.service.NationService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value = "/portal/portlet/weather")
public class WeatherController extends BaseController{

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private WeatherInfoService weatherInfoService;

	@Autowired
	private NationService nationService;

	@Autowired
	private WeatherCityService weatherCityService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 날씨 포틀릿 조회(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView mav = new ModelAndView("portal/portlet/weather/normalView");

		//날씨 목록 캐시에서 조회
		List<WeatherInfo> weatherInfoList = (List<WeatherInfo>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(weatherInfoList == null) {
		
			//포틀릿 설정 정보 조회
			Weather weather = weatherService.readWeather(portletConfigId);
	
			if(weather != null && !StringUtil.isEmpty(weather.getCityList())) {
				//설정 정보 있을때
				String[] cityIdCut = weather.getCityList().split(",");

				List<String> cityIdList = Arrays.asList(cityIdCut);
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("cityIdList", cityIdList);
				map.put("localeCode", user.getLocaleCode());

				weatherInfoList = weatherInfoService.listWeatherInfo(map);
				
				// 캐시에 저장
				cacheService.addCacheElementPortletContent(portletId, portletConfigId, weatherInfoList);
			}
		}
		
		String today = DateUtil.getTodayDateTime("yyyyMMdd");
		
		mav.addObject("month1", DateUtil.getNextDate(today, 1, "yyyyMMdd").substring(4, 6));
		mav.addObject("day1", DateUtil.getNextDate(today, 1, "yyyyMMdd").substring(6, 8));
		mav.addObject("month2", DateUtil.getNextDate(today, 2, "yyyyMMdd").substring(4, 6));
		mav.addObject("day2", DateUtil.getNextDate(today, 2, "yyyyMMdd").substring(6, 8));
		mav.addObject("month3", DateUtil.getNextDate(today, 3, "yyyyMMdd").substring(4, 6));
		mav.addObject("day3", DateUtil.getNextDate(today, 3, "yyyyMMdd").substring(6, 8));
		mav.addObject("month4", DateUtil.getNextDate(today, 4, "yyyyMMdd").substring(4, 6));
		mav.addObject("day4", DateUtil.getNextDate(today, 4, "yyyyMMdd").substring(6, 8));
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("weatherInfoList", weatherInfoList);

		return mav;
	}

	/**
	 * 날씨 포틀릿 설정(configView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView mav = new ModelAndView("portal/portlet/weather/configView");

		//날씨 목록 캐시에서 조회
		List<WeatherInfo> weatherInfoList = (List<WeatherInfo>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(weatherInfoList == null) {
			//포틀릿 설정 정보 조회
			Weather weather = weatherService.readWeather(portletConfigId);

			if(weather != null && !StringUtil.isEmpty(weather.getCityList())) {
				//설정 정보 있을때
				String[] cityIdCut = weather.getCityList().split(",");
	
				List<String> cityIdList = Arrays.asList(cityIdCut);
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("cityIdList", cityIdList);
				map.put("localeCode", user.getLocaleCode());
	
				weatherInfoList = weatherInfoService.listWeatherInfo(map);
				
				// 캐시에 저장
				cacheService.addCacheElementPortletContent(portletId, portletConfigId, weatherInfoList);
			}
		}
		
		//국가 리스트 조회
		List<Nation> weatherNationList = nationService.listAll(user.getLocaleCode());

		List<WeatherCity> weatherCityList = null;

		if(weatherNationList.size() > 0) {
			Nation weatherNation = weatherNationList.get(0);

			//도시 리스트 조회
			weatherCityList = weatherCityService.listWeatherNationCity(weatherNation.getNationCode());
		}

		
		String today = DateUtil.getTodayDateTime("yyyyMMdd");
		
		mav.addObject("month1", today.substring(4, 6));
		mav.addObject("day1", today.substring(6, 8));
		mav.addObject("month2", DateUtil.getNextDate(today, 1, "yyyyMMdd").substring(4, 6));
		mav.addObject("day2", DateUtil.getNextDate(today, 1, "yyyyMMdd").substring(6, 8));
		mav.addObject("month3", DateUtil.getNextDate(today, 2, "yyyyMMdd").substring(4, 6));
		mav.addObject("day3", DateUtil.getNextDate(today, 2, "yyyyMMdd").substring(6, 8));
		mav.addObject("month4", DateUtil.getNextDate(today, 3, "yyyyMMdd").substring(4, 6));
		mav.addObject("day4", DateUtil.getNextDate(today, 3, "yyyyMMdd").substring(6, 8));
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		mav.addObject("weatherNationList", weatherNationList);
		mav.addObject("weatherCityList", weatherCityList);
		mav.addObject("weatherInfoList", weatherInfoList);

		return mav;
	}

	/**
	 * 날씨 포틀릿 나라별 도시 리스트 조회
	 * @param nationCode 국가 코드
	 * @return result 도시 정보 목록
	 */
	@RequestMapping("/listWeatherNationCity.do")
	public @ResponseBody List<WeatherCity> listWeatherNationCity(@RequestParam String nationCode) {
		List<WeatherCity> result = null;

		result = weatherCityService.listWeatherNationCity(nationCode);
		
		return result;
	}

	/**
	 * 날씨 포틀릿 설정 등록
	 * @param weather 날씨 포틀릿 model
	 * @return success 메세지
	 */
	@RequestMapping(value = "/createWeather.do")
	public @ResponseBody String createWeather(@ModelAttribute Weather weather, @RequestParam String portletId) {

		User user = (User) getRequestAttribute("ikep.user");
		
		weather.setRegisterId(user.getUserId());
		weather.setRegisterName(user.getUserName());
		weather.setUpdaterId(user.getUserId());
		weather.setUpdaterName(user.getUserName());
		
		//포틀릿 설정 정보 조회
		Weather weatherCheck = weatherService.readWeather(weather.getPortletConfigId());
		
		if(weatherCheck == null) {
			//기존 등록이 없으면 인서트
			weatherService.createWeather(weather);
		} else {
			//기존 등록이 있으면 업데이트
			weatherService.updateWeather(weather);
		}
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent(portletId, weather.getPortletConfigId());
		
		return "success";
	}
	
	/**
	 * 날씨 데이터 저장
	 *
	 */
	@RequestMapping(value = "/weatherInfo.do")
	public void weatherInfo() {
		weatherInfoService.createWeatherInfo();
	}
	
}