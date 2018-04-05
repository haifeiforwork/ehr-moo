package com.lgcns.ikep4.portal.portlet.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;
import com.lgcns.ikep4.portal.portlet.service.CongratulationsService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 축하합니다 포틀릿 controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: CongratulationsController.java 19473 2012-06-25 01:36:52Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/portlet/congratulations")
public class CongratulationsController extends BaseController{

	/**
	 * 축하합니다 포틀릿 service
	 */
	@Autowired
	private CongratulationsService congratulationsService;
	
	/**
	 * TimeZone 관리 service
	 */
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private CacheService cacheService;
	
	
	/**
	 * 축하합니다 포틀릿 페이지
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/congratulations/normalView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String pattern = "MM-dd";
        // 현재 시스템 시각을 사용자 시간대로 변환하고 pattern 형태의 문자열을 리턴함, 예제에서는 yyyy-MM-dd
        String currentDate = timeZoneSupportService.convertTimeZoneToString(pattern);
		
        //축하합니다 목록 캐시에서 조회
        List<Congratulations> list = (List<Congratulations>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
        
        if(list == null ) {
        	list = congratulationsService.list(portletConfigId, user);
        	
        	// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, list);
        } 
        
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("currentDate", currentDate);
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	/**
	 * 축하합니다 포틀릿 설정 페이지
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/congratulations/configView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String pattern = "MM-dd";
        // 현재 시스템 시각을 사용자 시간대로 변환하고 pattern 형태의 문자열을 리턴함, 예제에서는 yyyy-MM-dd
        String currentDate = timeZoneSupportService.convertTimeZoneToString(pattern);
        
        //기간 설정
        Congratulations congratulationsPriodConfig = new Congratulations();
        congratulationsPriodConfig = congratulationsService.getCongratulationsConfig(portletConfigId, user.getUserId(), "PERIOD");
        //보여줄 사용자 유형
        Congratulations congratulationsUserTypeConfig = new Congratulations();
        congratulationsUserTypeConfig = congratulationsService.getCongratulationsConfig(portletConfigId, user.getUserId(),"USERTYPE");
        
        //축하합니다 목록 캐시에서 조회
        List<Congratulations> list = (List<Congratulations>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
        
        if(list == null ) {
        	list= congratulationsService.list(portletConfigId, user);
        	
        	// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, list);
        }
		
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		mav.addObject("congratulationsPriodConfig", congratulationsPriodConfig);
		mav.addObject("congratulationsUserTypeConfig", congratulationsUserTypeConfig);
		mav.addObject("currentDate", currentDate);
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	/**
	 * 축하합니다 포틀릿 최대 크기 페이지
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/congratulations/maxView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String pattern = "MM.dd";
        // 현재 시스템 시각을 사용자 시간대로 변환하고 pattern 형태의 문자열을 리턴함, 예제에서는 yyyy-MM-dd
        String currentDate = timeZoneSupportService.convertTimeZoneToString(pattern);
		
        //축하합니다 목록 캐시에서 조회
        List<Congratulations> list = (List<Congratulations>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
        
        if(list == null ) {
        	list= congratulationsService.list(portletConfigId, user);
        	
        	// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, list);
        }
		
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("currentDate", currentDate);
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	/**
	 * 축하합니다 포틀릿 설정 저장
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param period 축하합니다 대상 기간
	 * @param request HttpServletRequest
	 * @return String 포틀릿 설정 아이디
	 */
	@RequestMapping(value = "/saveCongratulationsConfig.do", method = RequestMethod.POST)
	public @ResponseBody String saveCongratulationsConfig(@RequestParam String portletConfigId, @RequestParam String portletId, @RequestParam String period, @RequestParam String usertype, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		Congratulations congratulations = new Congratulations();
		congratulations.setPortletConfigId(portletConfigId);
		congratulations.setRegisterId(user.getUserId());
		congratulations.setRegisterName(user.getUserName());
		congratulations.setUpdaterId(user.getUserId());
		congratulations.setUpdaterName(user.getUserName());
		
		String returnValue = "";
		
		//기간 구분을 저장
		congratulations.setPropertyName("PERIOD");
		congratulations.setPropertyValue(period);		
		returnValue = congratulationsService.saveCongratulationsConfig(congratulations);
		
		//대상구분
		congratulations.setPropertyName("USERTYPE");
		congratulations.setPropertyValue(usertype);		
		returnValue = congratulationsService.saveCongratulationsConfig(congratulations);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent(portletId, portletConfigId);
		
		return returnValue;
	}
}
