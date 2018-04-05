package com.lgcns.ikep4.collpack.collaboration.workspace.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.CongratulationsDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Congratulations;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.CongratulationsService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포틀릿 축하합니다 Service 구현 클래스
 *
 * @author 박철종
 * @version $Id: CongratulationsServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("wsCongratulationsService")
public class CongratulationsServiceImpl extends GenericServiceImpl<Congratulations, String> implements CongratulationsService {

	@Autowired
	private CongratulationsDao congratulationsDao;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	/**
	 * 기녕일 목록
	 */
	public List<Congratulations> listCongratulations(String workspaceId, User user,String resultColumn) {
		
		// 현재 시스템 시각을 사용자 시간대로 변환된 시각(Date)를 리턴함
        Date date = timeZoneSupportService.convertTimeZone();     
        
		List<Congratulations> list = null;
		Map<String, String> map = new HashMap<String, String>();
		
		
		Calendar today = Calendar.getInstance();
		today.setTime(date);
	        
		String startDate = getStartDate(today);
		String endDate = getEndDate(today, "MONTH");
	        
		map.put("resultColumn", resultColumn);
		map.put("portalId", user.getPortalId());
		map.put("workspaceId", workspaceId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
			
		list = congratulationsDao.listCongratulations(map);

		
		return list;
	}
	
	/**

	public List<Congratulations> weddingAnnivList(String portletConfigId, User user) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portletConfigId", portletConfigId);
		param.put("propertyName", "PERIOD");
		param.put("userId", user.getUserId());	
		
		Congratulations congratulations = new Congratulations();
		congratulations = congratulationsDao.getPortletConfigByUserId(param);
		
		// 현재 시스템 시각을 사용자 시간대로 변환된 시각(Date)를 리턴함
        Date date = timeZoneSupportService.convertTimeZone();   
        
		List<Congratulations> list = null;
		Map<String, String> map = new HashMap<String, String>();
		
		if(congratulations == null || (congratulations != null && congratulations.getPropertyValue().equals("WEEK"))) {
			Calendar today = Calendar.getInstance();
	        today.setTime(date);
	        
			String startDate = getStartDate(today);
	        String endDate = getEndDate(today, "WEEK");
	        
	        map.put("resultColumn", "WEDDING_ANNIV");
			map.put("portalId", user.getPortalId());
			map.put("groupId", user.getGroupId());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			list = congratulationsDao.list(map);
		} else if(congratulations != null && congratulations.getPropertyValue().equals("MONTH")) {
			Calendar today = Calendar.getInstance();
	        today.setTime(date);
	        
			String startDate = getStartDate(today);
	        String endDate = getEndDate(today, "MONTH");
	        
	        map.put("resultColumn", "WEDDING_ANNIV");
			map.put("portalId", user.getPortalId());
			map.put("groupId", user.getGroupId());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			list = congratulationsDao.list(map);
		}
		
		return list;
	}***/
	

	/**
	 * 조회 시작일
	 */
	public String getStartDate(Calendar calendar) {
		
		String startDate = "";
		Calendar startCalendar = calendar;
	
		String startDay = String.valueOf(startCalendar.get(Calendar.DATE));
		String startMonth = String.valueOf(startCalendar.get(Calendar.MONTH) + 1);
		String startYear = String.valueOf(startCalendar.get(Calendar.YEAR));
				   
		if(startDay.length() == 1) {
			startDay = "0" + startDay;   
		}
		                      
		if(startMonth.length() == 1) {
			startMonth = "0" + startMonth;
		}

		startDate = startYear+"-"+startMonth+"-"+startDay; 
		
		return startDate;
		
	}
	/**
	 * 조회 종료일
	 * @param calendar
	 * @param period
	 * @return
	 */
	public String getEndDate(Calendar calendar, String period) {
		
		String endDate = "";
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
			
			endDate = endYear+"-"+endMonth+"-"+endDay;
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
			
			endDate = endYear+"-"+endMonth+"-"+endDay; 
		}
		
		return endDate;
		
	}

}