package com.lgcns.ikep4.portal.portlet.web;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.WeatherCity;
import com.lgcns.ikep4.portal.portlet.model.WorldClock;
import com.lgcns.ikep4.portal.portlet.service.WorldClockService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.http.HttpUtil;

@Controller
@RequestMapping(value = "/portal/portlet/worldClock")
public class WorldClockController extends BaseController {
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private WorldClockService worldClockService;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		
		
		ModelAndView mav = new ModelAndView("portal/portlet/worldClock/normalView");

		User user = (User) getRequestAttribute("ikep.user");		
		
		//설정을 하지 않는 경우에 대비해 초기화
		WorldClock rightWorldClock = new WorldClock();
		WorldClock leftWorldClock = new WorldClock();
		
		Properties prop = PropertyLoader.loadProperties("/configuration/portal-conf.properties");
		rightWorldClock.setCountryName(prop.getProperty("portal.admin.screen.portlet.worldClock.right.country.ko"));
		rightWorldClock.setCityName(prop.getProperty("portal.admin.screen.portlet.worldClock.right.city.ko"));
		rightWorldClock.setCountryEnglishName(prop.getProperty("portal.admin.screen.portlet.worldClock.right.country.en"));
		rightWorldClock.setCityEnglishName(prop.getProperty("portal.admin.screen.portlet.worldClock.right.city.en"));
		leftWorldClock.setCountryName(prop.getProperty("portal.admin.screen.portlet.worldClock.left.country.ko"));
		leftWorldClock.setCityName(prop.getProperty("portal.admin.screen.portlet.worldClock.left.city.ko"));
		leftWorldClock.setCountryEnglishName(prop.getProperty("portal.admin.screen.portlet.worldClock.left.country.en"));
		leftWorldClock.setCityEnglishName(prop.getProperty("portal.admin.screen.portlet.worldClock.left.city.en"));


		//세계시간 설정
		List<WorldClock> myClockSetting = worldClockService.readWorldClock(user.getUserId());
		
		if(!myClockSetting.isEmpty())
		{
			//사용자가 포틀릿 설정을 하지 않는 경우를 대비
			if(myClockSetting.size() == 1)
			{
				if("R".equals(myClockSetting.get(0).getType()))
				{
					rightWorldClock = myClockSetting.get(0);
				}
				else
				{
					leftWorldClock = myClockSetting.get(0);
				}			
			}
			else if(myClockSetting.size() >= 2)
			{	
				if("R".equals(myClockSetting.get(0).getType()))
				{
					rightWorldClock = myClockSetting.get(0);
					leftWorldClock = myClockSetting.get(1);
				}
				else
				{
					rightWorldClock = myClockSetting.get(1);
					leftWorldClock = myClockSetting.get(0);
				}
			}
		}
	
			
		String selectLeftCountryName = "";
		String selectLeftCityName = "";
		String selectRightCountryName = "";
		String selectRightCityName = "";
		
		if("ko".equals(user.getLocaleCode())) {
			selectLeftCountryName = leftWorldClock.getCountryName();
			selectLeftCityName = leftWorldClock.getCityName();
			selectRightCountryName = rightWorldClock.getCountryName();
			selectRightCityName = rightWorldClock.getCityName();
		} else {
			selectLeftCountryName = leftWorldClock.getCountryEnglishName();
			selectLeftCityName = leftWorldClock.getCityEnglishName();
			selectRightCountryName = rightWorldClock.getCountryEnglishName();
			selectRightCityName = rightWorldClock.getCityEnglishName();
		}	


		List<String> countryNameList = new ArrayList<String>();
		List<String> cityNameRList = new ArrayList<String>();
		List<String> cityNameLList = new ArrayList<String>();


		try {
			String path = "/base/swf/portlet/wclock/timedata_"+user.getLocaleCode()+".xml";	
			
			SAXBuilder builder = new SAXBuilder();
			
			Document doc = builder.build(new InputStreamReader(this.getWebApplicationContext().getServletContext().getResourceAsStream(path), "UTF-8"));
					
			Element root = doc.getRootElement();
			List firstList = root.getChildren();
			
			for(int k=0; k<firstList.size();k++)
            {
                Element tempEl=(Element)firstList.get(k);
                String countryName = tempEl.getAttributeValue("name");
                
                countryNameList.add(countryName);
                
                if(countryName.equals(selectRightCountryName)) {
                	List cityElList = tempEl.getChildren();
                	for(int l=0; l<cityElList.size();l++)
                    {
                		Element tempCityEl=(Element)cityElList.get(l);
                        String cityName = tempCityEl.getAttributeValue("name");                        
                        cityNameRList.add(cityName);
                    }
                }
                if(countryName.equals(selectLeftCountryName)){
                	List cityElList = tempEl.getChildren();
                	for(int l=0; l<cityElList.size();l++)
                    {
                		Element tempCityEl=(Element)cityElList.get(l);
                        String cityName = tempCityEl.getAttributeValue("name");                        
                        cityNameLList.add(cityName);
                    }
                }                
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("countryNameList", countryNameList);
		mav.addObject("cityNameRList", cityNameRList);
		mav.addObject("cityNameLList", cityNameLList);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("selectRightCountryName", selectRightCountryName);
		mav.addObject("selectRightCityName", selectRightCityName);
		mav.addObject("selectLeftCountryName", selectLeftCountryName);
		mav.addObject("selectLeftCityName", selectLeftCityName);

		return mav;
	}

	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("portal/portlet/worldClock/configView");

		return mav;
	}

	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("portal/portlet/worldClock/maxView");

		return mav;
	}
	
	/**
	 * 세계시간 설정 저장
	 * @param worldClock
	 * @return
	 */
	@RequestMapping(value = "/createRWorldClock.do")
	public @ResponseBody String createRWorldClock(@RequestParam int countryNameRIndex, @RequestParam int cityNameRIndex, HttpServletRequest request) 
	{		
		return saveWorldClock(countryNameRIndex,cityNameRIndex, "R");
	}
	
	/**
	 * 세계시간 설정 저장
	 * @param worldClock
	 * @return
	 */
	@RequestMapping(value = "/createLWorldClock.do")
	public @ResponseBody String createLWorldClock(@RequestParam int countryNameLIndex, @RequestParam int cityNameLIndex, HttpServletRequest request) 
	{	
		return saveWorldClock(countryNameLIndex,cityNameLIndex, "L");
	}
	
	private String saveWorldClock( int countryNameIndex, int cityNameIndex, String type) 
	{
		
		User user = (User) getRequestAttribute("ikep.user");
				
		WorldClock worldClock = new WorldClock();
		
		try {

            String path = "/base/swf/portlet/wclock/timedata_ko.xml";	
			
			SAXBuilder builder = new SAXBuilder();
			
			Document doc = builder.build(new InputStreamReader(this.getWebApplicationContext().getServletContext().getResourceAsStream(path), "UTF-8"));
		
			
			Element root = doc.getRootElement();
			List firstList = root.getChildren();
			
			Element tempCountryEl=(Element)firstList.get(countryNameIndex);
			worldClock.setCountryName(tempCountryEl.getAttributeValue("name"));
			
			List cityElList = tempCountryEl.getChildren();
			
			Element tempCityEl=(Element)cityElList.get(cityNameIndex);
			worldClock.setCityName(tempCityEl.getAttributeValue("name"));
			
            String path1 = "/base/swf/portlet/wclock/timedata_en.xml";	
			
			SAXBuilder builder1 = new SAXBuilder();
			
			Document doc1 = builder.build(new InputStreamReader(this.getWebApplicationContext().getServletContext().getResourceAsStream(path), "UTF-8"));
						
			Element root1 = doc1.getRootElement();
			List firstList1 = root1.getChildren();
			
			Element tempCountryEl1=(Element)firstList1.get(countryNameIndex);
			worldClock.setCountryEnglishName(tempCountryEl1.getAttributeValue("name"));
			
			List cityElList1 = tempCountryEl1.getChildren();
			
			Element tempCityEl1=(Element)cityElList1.get(cityNameIndex);
			worldClock.setCityEnglishName(tempCityEl1.getAttributeValue("name"));
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		worldClock.setType(type);
		worldClock.setUserId(user.getUserId());
		worldClock.setRegisterId(user.getUserId());
		worldClock.setRegisterName(user.getUserName());
		worldClock.setUpdaterId(user.getUserId());
		worldClock.setUpdaterName(user.getUserName());
		
		
		worldClockService.createWorldClock(worldClock);
	
		return "success";
	}
	
	/**
	 * 세계시간 나라별 도시 리스트 조회
	 * @param nationCode
	 * @return
	 */
	@RequestMapping("/listCountryCity.do")
	public @ResponseBody List<String> listCountryCity(@RequestParam String countryName, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");

		List<String> cityNameList = new ArrayList<String>();
		
		try {			
			String strPath = "/base/swf/portlet/wclock/timedata_"+user.getLocaleCode()+".xml";
	
			SAXBuilder builder = new SAXBuilder();
			
			Document doc = builder.build(new InputStreamReader(this.getWebApplicationContext().getServletContext().getResourceAsStream(strPath), "UTF-8"));
			
			Element root = doc.getRootElement();			
			
			List firstList = root.getChildren();
				
			for(int k=0; k<firstList.size();k++)
            {
                Element tempEl=(Element)firstList.get(k);
                
                if(countryName.equals(tempEl.getAttributeValue("name"))) {
                	List cityElList = tempEl.getChildren();
                	for(int l=0; l<cityElList.size();l++)
                    {
                		Element tempCityEl=(Element)cityElList.get(l);
                        String cityName = tempCityEl.getAttributeValue("name");
                        
                        cityNameList.add(cityName);
                    }
                	
                	break;
                }
            }
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return cityNameList;
	}
}