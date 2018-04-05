/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.statistics.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.statistics.dao.WorkspaceStatisticsDao;
import com.lgcns.ikep4.collpack.collaboration.statistics.model.WorkspaceStatistics;
import com.lgcns.ikep4.collpack.collaboration.statistics.search.StatisticsSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.statistics.service.WorkspaceStatisticsService;
import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspaceDao;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 
 *
 * @author
 * @version $Id: WorkspaceStatisticsServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("workspaceStatisticsService")
public class WorkspaceStatisticsServiceImpl extends GenericServiceImpl<WorkspaceStatistics, String> implements WorkspaceStatisticsService {
	@Autowired 
	WorkspaceStatisticsDao workspaceStatisticsDao;
	@Autowired 
	WorkspaceDao workspaceDao;

	/**
	 * WS 통계목록
	 */
	public SearchResult<WorkspaceStatistics> listStatisticsByCollaboration(StatisticsSearchCondition searchCondition) {
		
		if(searchCondition.getGroupValue()== null){
			searchCondition.setGroupValue("A");
		}
		if(searchCondition.getMemberFlag()== null){
			searchCondition.setMemberFlag("A");
		}
		
		if(!StringUtil.isEmpty(searchCondition.getStartYear())&&!StringUtil.isEmpty(searchCondition.getStartMonth())){
			String startYear  = searchCondition.getStartYear();
			String startMonth  = "";
			if(searchCondition.getStartMonth().length()==1){
				startMonth = "0"+searchCondition.getStartMonth();
			}else{
				startMonth = searchCondition.getStartMonth();
			}
			String startPeriodFlag = startYear+startMonth;
			searchCondition.setStartPeriodFlag(startPeriodFlag);
		}
		
		if(!StringUtil.isEmpty(searchCondition.getEndYear())&&!StringUtil.isEmpty(searchCondition.getEndMonth())){
			String endYear  = searchCondition.getEndYear();
			String endMonth  = "";
			if(searchCondition.getEndMonth().length()==1){
				endMonth = "0"+searchCondition.getEndMonth();
			}else{
				endMonth = searchCondition.getEndMonth();
			}
			String endPeriodFlag = endYear+endMonth;
			searchCondition.setEndPeriodFlag(endPeriodFlag);
		}
		
		if(searchCondition.getStartPeriodFlag() == null || searchCondition.getEndPeriodFlag() == null){
			//  기본 기간조건 접속 현재월 기준
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM"); 
			String tempPeriod = df.format(d); 

			searchCondition.setStartPeriodFlag(tempPeriod);
			searchCondition.setEndPeriodFlag(tempPeriod);
		}
		
		
		List<WorkspaceStatistics> statisticsList = this.workspaceStatisticsDao.listStatisticsByCollaboration(searchCondition);
		
		String startYear = searchCondition.getStartPeriodFlag().substring(0, 4);
		String startMonth = searchCondition.getStartPeriodFlag().substring(4);
		if(startMonth.startsWith("0")){
			startMonth = startMonth.substring(1);
		}
		String endYear = searchCondition.getEndPeriodFlag().substring(0, 4);
		String endMonth = searchCondition.getEndPeriodFlag().substring(4);
		if(endMonth.startsWith("0")){
			endMonth = endMonth.substring(1);
		}
		
		searchCondition.setStartYear(startYear);
		searchCondition.setStartMonth(startMonth);
		searchCondition.setEndYear(endYear);
		searchCondition.setEndMonth(endMonth);
		
		SearchResult<WorkspaceStatistics> searchResult = null;
		searchResult = new SearchResult<WorkspaceStatistics>(statisticsList, searchCondition);
		
		return searchResult;
	}

	/**
	 * 멤버 통계 목록
	 */
	public SearchResult<WorkspaceStatistics> listStatisticsByMember(StatisticsSearchCondition searchCondition) {
		
		if(searchCondition.getGroupValue()== null){
			searchCondition.setGroupValue("A");
		}
		if(searchCondition.getMemberFlag()== null){
			searchCondition.setMemberFlag("A");
		}
		
		if(!StringUtil.isEmpty(searchCondition.getStartYear())&&!StringUtil.isEmpty(searchCondition.getStartMonth())){
			String startYear  = searchCondition.getStartYear();
			String startMonth  = "";
			if(searchCondition.getStartMonth().length()==1){
				startMonth = "0"+searchCondition.getStartMonth();
			}else{
				startMonth = searchCondition.getStartMonth();
			}
			String startPeriodFlag = startYear+startMonth;
			searchCondition.setStartPeriodFlag(startPeriodFlag);
		}
		
		if(!StringUtil.isEmpty(searchCondition.getEndYear())&&!StringUtil.isEmpty(searchCondition.getEndMonth())){
			String endYear  = searchCondition.getEndYear();
			String endMonth  = "";
			if(searchCondition.getEndMonth().length()==1){
				endMonth = "0"+searchCondition.getEndMonth();
			}else{
				endMonth = searchCondition.getEndMonth();
			}
			String endPeriodFlag = endYear+endMonth;
			searchCondition.setEndPeriodFlag(endPeriodFlag);
		}
		
		if(searchCondition.getStartPeriodFlag() == null || searchCondition.getEndPeriodFlag() == null){
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM"); 
			String tempPeriod = df.format(d); 

			searchCondition.setStartPeriodFlag(tempPeriod);
			searchCondition.setEndPeriodFlag(tempPeriod);
		}

		List<WorkspaceStatistics> statisticsList = this.workspaceStatisticsDao.listStatisticsByMember(searchCondition);
		
		String startYear = searchCondition.getStartPeriodFlag().substring(0, 4);
		String startMonth = searchCondition.getStartPeriodFlag().substring(4);
		if(startMonth.startsWith("0")){
			startMonth = startMonth.substring(1);
		}
		String endYear = searchCondition.getEndPeriodFlag().substring(0, 4);
		String endMonth = searchCondition.getEndPeriodFlag().substring(4);
		if(endMonth.startsWith("0")){
			endMonth = endMonth.substring(1);
		}
		
		searchCondition.setStartYear(startYear);
		searchCondition.setStartMonth(startMonth);
		searchCondition.setEndYear(endYear);
		searchCondition.setEndMonth(endMonth);
		
		SearchResult<WorkspaceStatistics> searchResult = null;
		searchResult = new SearchResult<WorkspaceStatistics>(statisticsList, searchCondition);
		
		return searchResult;
	}
	
	

	/**
	 * WS 통계 엑셀다운로드
	 */
	public List<WorkspaceStatistics> excelListForCollaboration(StatisticsSearchCondition searchCondition){
		if(searchCondition.getGroupValue()== null){
			searchCondition.setGroupValue("A");
		}
		if(searchCondition.getMemberFlag()== null){
			searchCondition.setMemberFlag("A");
		}
		
		if(!StringUtil.isEmpty(searchCondition.getStartYear())&&!StringUtil.isEmpty(searchCondition.getStartMonth())){
			String startYear  = searchCondition.getStartYear();
			String startMonth  = "";
			if(searchCondition.getStartMonth().length()==1){
				startMonth = "0"+searchCondition.getStartMonth();
			}else{
				startMonth = searchCondition.getStartMonth();
			}
			String startPeriodFlag = startYear+startMonth;
			searchCondition.setStartPeriodFlag(startPeriodFlag);
		}
		
		if(!StringUtil.isEmpty(searchCondition.getEndYear())&&!StringUtil.isEmpty(searchCondition.getEndMonth())){
			String endYear  = searchCondition.getEndYear();
			String endMonth  = "";
			if(searchCondition.getEndMonth().length()==1){
				endMonth = "0"+searchCondition.getEndMonth();
			}else{
				endMonth = searchCondition.getEndMonth();
			}
			String endPeriodFlag = endYear+endMonth;
			searchCondition.setEndPeriodFlag(endPeriodFlag);
		}
		
		if(searchCondition.getStartPeriodFlag() == null || searchCondition.getEndPeriodFlag() == null){
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM"); 
			String tempPeriod = df.format(d); 

			searchCondition.setStartPeriodFlag(tempPeriod);
			searchCondition.setEndPeriodFlag(tempPeriod);
		}
		
		List<WorkspaceStatistics> statisticsList = this.workspaceStatisticsDao.listStatisticsByCollaboration(searchCondition);
		
		return statisticsList; 
	}
	/**
	 * 통계정보 배치 Job
	 */
	public void syncWorkspaceStatistics() {
		workspaceStatisticsDao.syncWorkspaceStatistics();
	}	

	/**
	 * 멤버 통계 엑셀다운로드
	 */
	public List<WorkspaceStatistics> excelListForMember(StatisticsSearchCondition searchCondition) {
		
		if(searchCondition.getGroupValue()== null){
			searchCondition.setGroupValue("A");
		}
		if(searchCondition.getMemberFlag()== null){
			searchCondition.setMemberFlag("A");
		}
		
		if(!StringUtil.isEmpty(searchCondition.getStartYear())&&!StringUtil.isEmpty(searchCondition.getStartMonth())){
			String startYear  = searchCondition.getStartYear();
			String startMonth  = "";
			if(searchCondition.getStartMonth().length()==1){
				startMonth = "0"+searchCondition.getStartMonth();
			}else{
				startMonth = searchCondition.getStartMonth();
			}
			String startPeriodFlag = startYear+startMonth;
			searchCondition.setStartPeriodFlag(startPeriodFlag);
		}
		
		if(!StringUtil.isEmpty(searchCondition.getEndYear())&&!StringUtil.isEmpty(searchCondition.getEndMonth())){
			String endYear  = searchCondition.getEndYear();
			String endMonth  = "";
			if(searchCondition.getEndMonth().length()==1){
				endMonth = "0"+searchCondition.getEndMonth();
			}else{
				endMonth = searchCondition.getEndMonth();
			}
			String endPeriodFlag = endYear+endMonth;
			searchCondition.setEndPeriodFlag(endPeriodFlag);
		}
		
		if(searchCondition.getStartPeriodFlag() == null || searchCondition.getEndPeriodFlag() == null){
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM"); 
			String tempPeriod = df.format(d); 

			searchCondition.setStartPeriodFlag(tempPeriod);
			searchCondition.setEndPeriodFlag(tempPeriod);
		}

		return this.workspaceStatisticsDao.listStatisticsByMember(searchCondition);

	}
	
	

	//public void physicalDeleteStatistics(String workspaceId) {
	//	workspaceStatisticsDao.physicalDeleteStatistics(workspaceId);
	//}	
}
