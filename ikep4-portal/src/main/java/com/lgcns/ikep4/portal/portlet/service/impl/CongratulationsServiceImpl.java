package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.CongratulationsDao;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;
import com.lgcns.ikep4.portal.portlet.service.CongratulationsService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포틀릿 축하합니다 Service 구현 클래스
 *
 * @author 박철종
 * @version $Id: CongratulationsServiceImpl.java 18288 2012-04-25 00:53:14Z yu_hs $
 */
@Service("congratulationsService")
public class CongratulationsServiceImpl extends GenericServiceImpl<Congratulations, String> implements CongratulationsService {

	@Autowired
	private CongratulationsDao congratulationsDao;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	/**
	 * 축하합니다 포틀릿의 리스트
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param user 사용자 정보
	 * @return List<Congratulations> 축하합니다 포틀릿의 리스트
	 */
	public List<Congratulations> list(String portletConfigId, User user) {
		
		Map<String, String> param = new HashMap<String, String>();
		Congratulations congratulationsConfig = new Congratulations();
		
		param.put("portletConfigId", portletConfigId);
		param.put("propertyName", "PERIOD");
		param.put("userId", user.getUserId());	
		congratulationsConfig = congratulationsDao.getPortletConfigByUserId(param);
		
	    String strSearchPeriod = "WEEK";
		
		if(congratulationsConfig != null)
		{
			strSearchPeriod = congratulationsConfig.getPropertyValue();
		}
		
		param.put("portletConfigId", portletConfigId);
		param.put("propertyName", "USERTYPE");
		param.put("userId", user.getUserId());	
		congratulationsConfig = congratulationsDao.getPortletConfigByUserId(param);
		
	    String strSearchUserType = "JRM";
		
		if(congratulationsConfig != null)
		{
			strSearchUserType = congratulationsConfig.getPropertyValue();
		}
			
		
		// 현재 시스템 시각을 사용자 시간대로 변환된 시각(Date)를 리턴함
        Date date = timeZoneSupportService.convertTimeZone();   
        
		List<Congratulations> list = new ArrayList<Congratulations>();
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", user.getPortalId());
		map.put("groupId", user.getGroupId());
		map.put("userId", user.getUserId());
		map.put("userType", strSearchUserType);
		
		
		//무림은 양,음력 입력을 지원하므로 양력기준의 축하합니다는 양음력 기준으로 변경되어야 한다.
		
		//1. 주간 단위, 월간 단위에 따라 나누어 처리	
			Calendar today = Calendar.getInstance();
					
	        today.setTime(date);

	        //검색년을 가져온다.
			int strTodaysYear = today.get(Calendar.YEAR);
			//검색 시작일을 가져온다
			String startDate = getStartDate(today);
			
			//검색 종료일을 가져온다
	        String[] endDate = getEndDate(today, strSearchPeriod);
	    	
			if(strTodaysYear == Integer.parseInt(endDate[0]))
			{
				//검색 시작일을 설정한다.
				map.put("startDate", startDate);
				map.put("startYearDate", Integer.toString(strTodaysYear) + "-" + startDate);
				
				//검색 종료일을 설정한다.
				map.put("endDate", endDate[1]);		
				map.put("endYearDate", endDate[0] + "-" + endDate[1]);	
				
				List<Congratulations> congGreglist = congratulationsDao.list(map);			
				if(congGreglist.size()>0)
					list.addAll(congGreglist);
			}
			else
			{				
				 //리스트의 정렬을 위해 두번 리스트를 불러온다.
				//검색 시작일을 설정한다.
				map.put("startDate", startDate);
				map.put("startYearDate", Integer.toString(strTodaysYear) + "-" + startDate);
				
				//검색 종료일을 설정한다.
				map.put("endDate", "12-31");		
				map.put("endYearDate", Integer.toString(strTodaysYear) + "-" + "12-31");
				
				List<Congratulations> congGreglist = congratulationsDao.list(map);			
				if(congGreglist.size()>0)
					list.addAll(congGreglist);	
				
				//검색 시작일을 설정한다.
				map.put("startDate", startDate);
				map.put("startYearDate", Integer.toString(strTodaysYear + 1) + "-" + "01-01");
				
				//검색 종료일을 설정한다.
				map.put("endDate", endDate[1]);		
				map.put("endYearDate", endDate[0] + "-" + endDate[1]);					
				
				List<Congratulations> congGreglist1 = congratulationsDao.list(map);			
				if(congGreglist1.size()>0)
					list.addAll(congGreglist1);	
			}
		
		return list;
	}
	
	/**
	 * 축하합니다 포틀릿의 설정 정보
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param userId 사용자 아이디
	 * @return Congratulations 축하합니다 포틀릿의 설정 정보
	 */
	public Congratulations getCongratulationsConfig(String portletConfigId, String userId, String propertyName) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", portletConfigId);
		param.put("propertyName", propertyName);
		param.put("userId", userId);	
		
		Congratulations congratulations = new Congratulations();
		congratulations = congratulationsDao.getPortletConfigByUserId(param);
		
		return congratulations;
		
	}
	
	/**
	 * 축하합니다 포틀릿의 설정 저장
	 * @param congratulations 설정 정보
	 * @return String 포틀릿 설정 아이디
	 */
	public String saveCongratulationsConfig(Congratulations congratulations) {
		
		String returnValue = "";
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", congratulations.getPortletConfigId());
		param.put("propertyName", congratulations.getPropertyName());//"PERIOD");
		param.put("userId", congratulations.getRegisterId());
		
		// propertyValue 값이 null 인 경우 default로 "WEEK" 값 설정.
		if("PERIOD".equals(congratulations.getPropertyName()) && StringUtil.isEmpty(congratulations.getPropertyValue())) {
			congratulations.setPropertyValue("WEEK");
		}
		
		// propertyValue 값이 null 인 경우 default로 "WEEK" 값 설정.
		if("USERTYPE".equals(congratulations.getPropertyName()) && StringUtil.isEmpty(congratulations.getPropertyValue())) {
			congratulations.setPropertyValue("JRM");
		}
		
		Congratulations temp = new Congratulations();
		temp = congratulationsDao.getPortletConfigByUserId(param);
		
		if(temp == null) {
			returnValue = congratulationsDao.createUserConfig(congratulations);
		} else {
			congratulationsDao.updateUserConfig(congratulations);
			
			returnValue = congratulations.getPortletConfigId();
		}
		
		return returnValue;
		
	}
	
	/**
	 * 사용자 TimeZone에 따른 오늘 날짜
	 * @param calendar 오늘 날짜 정보
	 * @return String 사용자 TimeZone에 따른 오늘 날짜(MM-DD 타입)
	 */
	private String getStartDate(Calendar calendar) {
		
		String startDate = "";
		Calendar startCalendar = calendar;
	
		String startDay = String.valueOf(startCalendar.get(Calendar.DATE));
		String startMonth = String.valueOf(startCalendar.get(Calendar.MONTH) + 1);
				   
		if(startDay.length() == 1) {
			startDay = "0" + startDay;   
		}
		                      
		if(startMonth.length() == 1) {
			startMonth = "0" + startMonth;
		}

		startDate = startMonth+"-"+startDay;
		
		return startDate;
		
	}
	
	/**
	 * 사용자 TimeZone에 따른 대상 기간 설정 별 마지막 날짜
	 * @param calendar 오늘 날짜 정보
	 * @param period 대상 기간
	 * @return String 사용자 TimeZone에 따른 따른 대상 기간 설정 별 마지막 날짜(MM-DD 타입)
	 */
	private String[] getEndDate(Calendar calendar, String period) {
		
		String[] strResult = new String[2];

		Calendar endCalendar = calendar;
		
		if(period.equals("WEEK")) {
			endCalendar.add(Calendar.DATE, 7);
	
			String endDay = String.valueOf(endCalendar.get(Calendar.DATE));
			String endMonth = String.valueOf(endCalendar.get(Calendar.MONTH) + 1);
			String endYear = String.valueOf(endCalendar.get(Calendar.YEAR));
			
			if(endDay.length() == 1) {
				endDay = "0" + endDay;   
			}
			                      
			if(endMonth.length() == 1) {
				endMonth = "0" + endMonth;
			}
			strResult[0] = endYear;
			strResult[1] = endMonth+"-"+endDay;
			
		} else if(period.equals("MONTH")) {
			endCalendar.add(Calendar.MONTH, 1);
			
			String endDay = String.valueOf(endCalendar.get(Calendar.DATE));
			String endMonth = String.valueOf(endCalendar.get(Calendar.MONTH) + 1);
			String endYear = String.valueOf(endCalendar.get(Calendar.YEAR));
			
			if(endDay.length() == 1) {
				endDay = "0" + endDay;   
			}
			
			if(endMonth.length() == 1) {
				endMonth = "0" + endMonth;
			}
			strResult[0] = endYear;
			strResult[1] = endMonth+"-"+endDay; 
		}
		
		return strResult;		
	}

}