package com.lgcns.ikep4.portal.portlet.service.impl;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.WeatherCityDao;
import com.lgcns.ikep4.portal.portlet.dao.WeatherInfoDao;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;
import com.lgcns.ikep4.portal.portlet.service.WeatherInfoService;
import com.lgcns.ikep4.portal.portlet.util.WeatherUtil;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;

/**
 * 
 * 포틀릿 날씨 정보 service 구현 클래스
 *
 * @author 임종상
 * @version $Id: WeatherInfoServiceImpl.java 19577 2012-07-02 05:35:25Z malboru80 $
 */
@Service("weatherInfoService")
public class WeatherInfoServiceImpl extends GenericServiceImpl<WeatherInfo, String> implements WeatherInfoService {

	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	private WeatherCityDao weatherCityDao;
	
	@Autowired
	private WeatherInfoDao weatherInfoDao;
	
	String changeLocale;
	
	Map<String, String> resultTemp;
	
	/**
	 * 설정한 날씨 정보 리스트
	 * @param map 
	 * @return List<WeatherInfo> 날씨 정보 목록
	 */
	public List<WeatherInfo> listWeatherInfo(Map<String, Object> map) {
		return weatherInfoDao.listWeatherInfo(map);
	}
	
	/**
	 * 설정한 날씨 정보 리스트
	 * @param map 
	 * @return List<WeatherInfo> 날씨 정보 목록
	 */
	public List<Map> listWeatherInfoRealTime(Map<String, Object> map) {
		
		
		List<Map> listWeather = new ArrayList<Map>();

		//도시 리스트 반복
		List<WeatherCity> cityIdList = weatherCityDao.listWeatherCityByIds((List<String>) map.get("cityIdList"));

		String hl = map.get("hl").toString();
		for(int i = 0; i < cityIdList.size(); i++) {
			
		if(hl.equals("jp")){
			hl = "ja";
		}
		
		WeatherCity weatherCity = cityIdList.get(i);
		
		String uurl="http://www.google.com/ig/api?weather="+weatherCity.getCityEnglishName()+"&ie=utf-8&oe=utf-8&hl="+hl;

		try
		{
        URL url = new URL(uurl);
        
        WeatherUtil weatherUtil = new WeatherUtil();
        resultTemp = new HashMap<String, String>();

        SAXBuilder builder = new SAXBuilder(); 
        
        Document doc = builder.build(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
        Element root = doc.getRootElement();
        Element elWW = root.getChild("weather");
        
        List firstList = elWW.getChildren();

        for(int k=0; k<firstList.size();k++)
        {
        	
            Element tempEl=(Element)firstList.get(k);
            List tempList = tempEl.getChildren();
            for(Object obj : tempList){
            	Attribute ele = (Attribute)obj;

                if(ele!=null && tempEl.getName().equals("forecast"))
                {
                	setForecastConditions(ele, weatherUtil, k);
                }
                else
                {
                	setNoForecastConditions(ele, weatherUtil, weatherCity);
                }
            }
        }
		}catch(Exception e){
		
		}
		
		listWeather.add(resultTemp);
       
	}
	return listWeather;
}
	
	/**
	 * 날씨 정보 등록
	 */
	public void createWeatherInfo() {
		
		try {
			//도시 리스트
			List<WeatherCity> weatherCityList = weatherCityDao.listWeatherCity();
			
			//로케일  리스트
			List<LocaleCode> localeCodeList = i18nMessageService.selectLocaleAll();
			
			//기존 날씨 정보 삭제
			weatherInfoDao.removeWeatherInfoAll();
			
			WeatherUtil weatherUtil = new WeatherUtil();
			
			//도시 리스트 반복
			for(int i = 0; i < weatherCityList.size(); i++) {
				
				WeatherCity weatherCity = weatherCityList.get(i);
				
				//로케일 리스트 반복
				for(int j = 0; j < localeCodeList.size(); j++) {
					
					LocaleCode localeCode = localeCodeList.get(j);
					
					resultTemp = new HashMap<String, String>();
					
					resultTemp.put("cityId", weatherCity.getCityId());
					resultTemp.put("englishName", weatherCity.getCityEnglishName());
					resultTemp.put("registerId", "admin");
					resultTemp.put("registerName", "관리자");
					resultTemp.put("updaterId", "admin");
					resultTemp.put("updaterName", "관리자");
					
					setLocaleCode(localeCode);
					
					String uurl= "http://weather.service.msn.com/data.aspx?weadegreetype=C&culture=" + changeLocale+"&weasearchstr=" + URLEncoder.encode(weatherCity.getCityEnglishName(),"UTF-8");
		            URL url = new URL(uurl);
		            
		            //String xml = FileCopyUtils.copyToString(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8")); 
		            SAXBuilder builder = new SAXBuilder(); 
		            
		            Document doc = builder.build(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
		            Element root = doc.getRootElement();
		            Element elWW = root.getChild("weather");
		            
		            List firstList = elWW.getChildren();	
		            
		            for(int k=0; k<firstList.size();k++)
		            {
		                Element tempEl=(Element)firstList.get(k);
		                List tempList = tempEl.getAttributes();		                
		 
		                for(Object obj : tempList){
		                	Attribute ele = (Attribute)obj;
		
		                    if(ele!=null && tempEl.getName().equals("forecast"))
		                    {
		                    	setForecastConditions(ele, weatherUtil, k);
		                    }
		                    else
		                    {
		                    	setNoForecastConditions(ele, weatherUtil, weatherCity);
		                    }
		                }
		            }
		            
		            //날씨 정보 저장
		            insertWeatherInfo();
				}
			}
		} catch (Exception e) {
			log.debug("날씨 포틀릿 데이터 등록 에러", e);
		}
	}
	
	/**
	 * 로케일 코드 셋팅
	 * @param localeCode 로케일 코드 
	 */
	private void setLocaleCode(LocaleCode localeCode) {
		
		if("ch".equals(localeCode.getLocaleCode())) {
			changeLocale="zh-SG";
            resultTemp.put("localeCode", "ch");
        } else if("jp".equals(localeCode.getLocaleCode())) {
        	changeLocale="ja-JP";
            resultTemp.put("localeCode", "jp");
        }
        else if("ko".equals(localeCode.getLocaleCode())) {
        	changeLocale="ko-KR";
            resultTemp.put("localeCode", "ko");        	
        } else {
        	changeLocale= "en-US";
            resultTemp.put("localeCode", "en");
        }
	}
	
	/**
	 * 날씨 정보 저장
	 *
	 */
	private void insertWeatherInfo() {
		if(resultTemp.get("cityId")!=null && !resultTemp.get("cityId").equals("")) {
        	//날씨 정보 저장
        	weatherInfoDao.createWeatherInfo(resultTemp);
        }
	}
	
	/**
	 * ForecastConditions resultTemp 셋팅
	 * @param ele
	 * @param weatherUtil
	 * @param k
	 */
	private void setForecastConditions(Attribute ele, WeatherUtil weatherUtil, int k) {
		if(ele.getName().equals("low"))
        {
			resultTemp.put("low" + (k-1), ele.getValue());			
        }
		else if(ele.getName().equals("high"))
        {
			resultTemp.put("high" + (k-1), ele.getValue());			
        }
		else if(ele.getName().equals("skycodeday"))
        {
			resultTemp.put("icon"+ (k-1), weatherUtil.getSmallWeatherIcon(ele.getValue()));
        }
		else if(ele.getName().equals("skytext"))
        {
			resultTemp.put("condition"+ (k-1), ele.getValue());
        } 
		else if(ele.getName().equals("date"))
        {
			resultTemp.put("day_of_week"+ (k-1), ele.getValue());
        }    	
		
		//if(ele.getName().equals("icon"))
	    //{
	    //	String[] tempArray= ele.getName().getAttributeValue("data").split("/");
	   // 	if(tempArray!=null && tempArray.length>3) {
	   // 		resultTemp.put(ele.getName()+ (k-1), weatherUtil.makeWeatherIconSmall(tempArray[4]));
	   // 	} else {
	   // 		resultTemp.put(ele.getName()+ (k-1), weatherUtil.makeWeatherIconSmall("mostly_sunny.gif"));
	   // 	}
	   // }
	   // else
	   // {
	   //     resultTemp.put(ele.getName()+ (k-1), ele.getAttributeValue("data"));
	   // }
	}
	
	/**
	 * NoForecastConditions resultTemp 셋팅
	 * @param ele
	 * @param weatherUtil
	 */
	private void setNoForecastConditions(Attribute ele, WeatherUtil weatherUtil, WeatherCity weatherCity) {
		if(ele.getName().equals("temperature"))
        {
			resultTemp.put("temp_c", ele.getValue());
			float ftemp = 0;
			try {
				ftemp = ((9/5) * ele.getIntValue()) + 32;
			} catch (DataConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			resultTemp.put("temp_f", String.format("%.1f", ftemp));
        }
		else if(ele.getName().equals("skycode"))
        {
			resultTemp.put("icon", weatherUtil.getWeatherIcon(ele.getValue()));
        }
		else if(ele.getName().equals("skytext"))
        {
			resultTemp.put("condition", ele.getValue());
        }
    	else if(ele.getName().equals("observationpoint"))
        {
    		resultTemp.put("cityId", weatherCity.getCityId() );
   			resultTemp.put("cityName", weatherCity.getCityName() );
            resultTemp.put("cityEnglishName", weatherCity.getCityEnglishName() );
        }
    	else if(ele.getName().equals("winddisplay"))
        {
    		resultTemp.put("wind_condition", ele.getValue());
        }
    	else if(ele.getName().equals("humidity"))
        {
    		resultTemp.put("humidity", ele.getValue() + "%");
        }
	}
	
	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
}