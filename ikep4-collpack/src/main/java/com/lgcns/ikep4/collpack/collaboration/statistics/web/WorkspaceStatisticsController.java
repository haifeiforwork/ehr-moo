/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.statistics.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.statistics.model.WorkspaceStatistics;
import com.lgcns.ikep4.collpack.collaboration.statistics.search.StatisticsSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.statistics.service.WorkspaceStatisticsService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Statistics Controller
 * 
 * @author 홍정관
 * @version $Id: WorkspaceStatisticsController.java 12739 2011-05-23 09:31:13Z
 *          happyi1018 $
 */
@Controller
@RequestMapping(value = "/collpack/collaboration/statistics")
@SessionAttributes("workspaceStatistics")
public class WorkspaceStatisticsController extends BaseController {

	@Autowired
	private WorkspaceStatisticsService workspaceStatisticsService;

	@Autowired
	private WorkspaceService workspaceService;

	// @Autowired
	// private FileService fileService;
	/**
	 * Collaboration Statistics
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/collStatisticsListView")
	public ModelAndView collStatisticsListView(String workspaceId, StatisticsSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {

		Workspace workspace = new Workspace();
		workspace = workspaceService.getWorkspace(workspaceId);

		if (workspace.getTeamId() != null) {
			searchCondition.setGroupId(workspace.getTeamId());
		}

		if (searchCondition.getWorkspaceId() == null) {
			searchCondition.setWorkspaceId(workspaceId);
		}

		SearchResult<WorkspaceStatistics> searchResult = workspaceStatisticsService
				.listStatisticsByCollaboration(searchCondition);

		ModelAndView mav = new ModelAndView();
		mav.addObject("workspace", workspace);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("searchResult", searchResult);
		return mav;
	}

	/**
	 * Member Statistics
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/memberStatisticsListView")
	public ModelAndView memberStatisticsListView(String workspaceId, StatisticsSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		if (searchCondition.getWorkspaceId() == null) {
			searchCondition.setWorkspaceId(workspaceId);
		}

		Workspace workspace = new Workspace();
		workspace = workspaceService.getWorkspace(workspaceId);

		if (workspace.getTeamId() != null) {
			searchCondition.setGroupId(workspace.getTeamId());
		}

		SearchResult<WorkspaceStatistics> searchResult = workspaceStatisticsService
				.listStatisticsByMember(searchCondition);

		ModelAndView mav = new ModelAndView();
		mav.addObject("workspace", workspace);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("searchResult", searchResult);
		return mav;
	}

	/**
	 * WS 통계 엑셀다운로드
	 * 
	 * @param response
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping("/excelDownColl")
	public ModelAndView excelDownColl(HttpServletResponse response, StatisticsSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		ExcelViewModel excel = new ExcelViewModel();
		try {

			Workspace workspace = new Workspace();
			workspace = workspaceService.getWorkspace(searchCondition.getWorkspaceId());
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");

			if (workspace.getTeamId() != null) {
				searchCondition.setGroupId(workspace.getTeamId());
			}
			// String fileName = "";
			List<WorkspaceStatistics> statisticsList = workspaceStatisticsService
					.excelListForCollaboration(searchCondition);
			/**
			 * List<Object> dataList = new ArrayList<Object>(); for
			 * (WorkspaceStatistics statisticsData : statisticsList) {
			 * if(StringUtil.isEmpty(statisticsData.getDocCount())){
			 * statisticsData.setDocCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getWeeklyCount())){
			 * statisticsData.setWeeklyCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getHitCount())){
			 * statisticsData.setHitCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getRecommendCount())){
			 * statisticsData.setRecommendCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getLineReplyCount())){
			 * statisticsData.setLineReplyCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getFavoriteCount())){
			 * statisticsData.setFavoriteCount("0"); }
			 * dataList.add(statisticsData); } LinkedHashMap<String, String>
			 * titleMap = new LinkedHashMap<String, String>();
			 * if(!user.getLocaleCode().equals(portal.getDefaultLocaleCode())){
			 * //En titleMap.put("yearMonth", "YearMonth");
			 * titleMap.put("docCount", "DocCount"); titleMap.put("weeklyCount",
			 * "WeeklyCount"); titleMap.put("hitCount", "HitCount");
			 * titleMap.put("recommendCount", "RecommendCount");
			 * titleMap.put("lineReplyCount", "LineReplyCount");
			 * titleMap.put("favoriteCount", "Favorite");
			 * titleMap.put("visitRatio", "VisitRate");
			 * titleMap.put("writeRatio", "WriteRate");
			 * titleMap.put("lineReplyRatio", "LineReplyRate");
			 * titleMap.put("weeklyRatio", "WeeklyRate"); titleMap.put("pviAvg",
			 * "PviAvg"); titleMap.put("cviAvg", "CviAvg"); fileName =
			 * "WorkspaceActivityStatistics_"
			 * +DateUtil.getToday("yyyy-MM-dd")+".xlsx"; }else{ //Ko
			 * titleMap.put("yearMonth", "기간"); titleMap.put("docCount",
			 * "게시글 등록"); titleMap.put("weeklyCount", "주간보고등록");
			 * titleMap.put("hitCount", "조회"); titleMap.put("recommendCount",
			 * "추천"); titleMap.put("lineReplyCount", "댓글");
			 * titleMap.put("favoriteCount", "Favorite");
			 * titleMap.put("visitRatio", "회원방문율"); titleMap.put("writeRatio",
			 * "게시글작성율"); titleMap.put("lineReplyRatio", "댓글작성율");
			 * titleMap.put("weeklyRatio", "주간보고작성율"); titleMap.put("pviAvg",
			 * "팀원평균 PVI"); titleMap.put("cviAvg", "팀원평균 CVI"); fileName =
			 * "Workspace활동통계_"+DateUtil.getToday("yyyy-MM-dd")+".xlsx"; }
			 * ExcelUtil.saveExcel(titleMap, dataList, fileName, response);
			 **/
			String fileName = "";
			fileName = "MemberActivityStatistics_" + DateUtil.getToday("yyyy-MM-dd") + ".xls";

			excel.setFileName(fileName);
			excel.setSheetName("Sheet");
			if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
				excel.setTitle("Workspace Activities Statistics");
			} else {
				excel.setTitle("Workspace 활동통계");
			}
			excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); // ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT
																						// 기본은
																						// 이값

			if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
				excel.addColumn("YearMonth", "yearMonth", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("DocCount 등록", "docCount", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("WeeklyCount", "weeklyCount", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("HitCount", "hitCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("RecommendCount", "recommendCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("LineReplyCount", "lineReplyCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("Favorite", "favoriteCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("VisitRate", "visitRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("WriteRate", "writeRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("LineReplyRate", "lineReplyRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("WeeklyRate", "weeklyRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);

				fileName = "WorkspaceActivityStatistics_" + DateUtil.getToday("yyyy-MM-dd") + ".xlsx";
			} else {

				excel.addColumn("기간", "yearMonth", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("게시글 등록", "docCount", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("주간보고등록", "weeklyCount", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("조회", "hitCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("추천", "recommendCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("댓글", "lineReplyCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("Favorite", "favoriteCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("회원방문율", "visitRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("게시글작성율", "writeRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("댓글작성율", "lineReplyRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("주간보고작성율", "weeklyRatio", 15, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				fileName = "Workspace활동통계_" + DateUtil.getToday("yyyy-MM-dd") + ".xlsx";
			}

			excel.setDataList(statisticsList);
		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 통계 excelDownColl...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel);
	}

	/**
	 * 팀원 통계 엑셀다운로드
	 * 
	 * @param response
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping("/excelDownMember")
	public ModelAndView excelDownMember(HttpServletResponse response, StatisticsSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {

		ExcelViewModel excel = new ExcelViewModel();
		try {

			Workspace workspace = new Workspace();
			workspace = workspaceService.getWorkspace(searchCondition.getWorkspaceId());
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");

			if (workspace.getTeamId() != null) {
				searchCondition.setGroupId(workspace.getTeamId());
			}

			List<WorkspaceStatistics> statisticsList = workspaceStatisticsService.excelListForMember(searchCondition);
			/*
			 * List<Object> dataList = new ArrayList<Object>(); for
			 * (WorkspaceStatistics statisticsData : statisticsList) {
			 * if(StringUtil.isEmpty(statisticsData.getDocCount())){
			 * statisticsData.setDocCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getWeeklyCount())){
			 * statisticsData.setWeeklyCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getHitCount())){
			 * statisticsData.setHitCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getRecommendCount())){
			 * statisticsData.setRecommendCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getLineReplyCount())){
			 * statisticsData.setLineReplyCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getFavoriteCount())){
			 * statisticsData.setFavoriteCount("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getPvi())){
			 * statisticsData.setPvi("0"); }
			 * if(StringUtil.isEmpty(statisticsData.getCvi())){
			 * statisticsData.setCvi("0"); } dataList.add(statisticsData); }
			 * String fileName = ""; LinkedHashMap<String, String> titleMap =
			 * new LinkedHashMap<String, String>();
			 * if(!user.getLocaleCode().equals(portal.getDefaultLocaleCode())){
			 * titleMap.put("userEnglishName", "Name");
			 * titleMap.put("jobTitleEnglishName", "JobTitle");
			 * titleMap.put("levelEnglishName", "MemberLevel");
			 * titleMap.put("teamEnglishName", "TeamName");
			 * titleMap.put("docCount", "DocCount"); titleMap.put("weeklyCount",
			 * "WeeklyCount"); titleMap.put("hitCount", "HitCount");
			 * titleMap.put("recommendCount", "RecommendCount");
			 * titleMap.put("lineReplyCount", "LineReplyCount");
			 * titleMap.put("favoriteCount", "Favorite"); titleMap.put("pvi",
			 * "PVI"); titleMap.put("cvi", "CVI"); fileName =
			 * "MemberActivityStatistics_"
			 * +DateUtil.getToday("yyyy-MM-dd")+".xlsx"; }else{
			 * titleMap.put("userName", "이름"); titleMap.put("jobTitleName",
			 * "직급"); titleMap.put("levelName", "역할"); titleMap.put("teamName",
			 * "부서"); titleMap.put("docCount", "게시글 등록");
			 * titleMap.put("weeklyCount", "주간보고등록"); titleMap.put("hitCount",
			 * "조회"); titleMap.put("recommendCount", "추천");
			 * titleMap.put("lineReplyCount", "댓글");
			 * titleMap.put("favoriteCount", "Favorite"); titleMap.put("pvi",
			 * "PVI"); titleMap.put("cvi", "CVI"); fileName =
			 * "Member활동통계_"+DateUtil.getToday("yyyy-MM-dd")+".xlsx"; }
			 * ExcelUtil.saveExcel(titleMap, dataList, fileName, response);
			 */
			String fileName = "";
			fileName = "MemberActivityStatistics_" + DateUtil.getToday("yyyy-MM-dd") + ".xls";

			excel.setFileName(fileName);
			excel.setSheetName("Sheet");
			if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
				excel.setTitle("Members Activities Statistics");
			} else {
				excel.setTitle("회원 활동통계");
			}
			excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); // ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT
																						// 기본은
																						// 이값

			if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
				excel.addColumn("Name", "userEnglishName", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("JobTitle", "jobTitleEnglishName", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("MemberLevel", "levelEnglishName", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("TeamName", "teamEnglishName", 40, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("DocCount", "docCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("WeeklyCount", "weeklyCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("HitCount", "hitCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("RecommendCount", "recommendCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("LineReplyCount", "lineReplyCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,
						"", ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("Favorite", "favoriteCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);

				fileName = "MemberActivityStatistics_" + DateUtil.getToday("yyyy-MM-dd") + ".xlsx";
			} else {

				excel.addColumn("이름", "userName", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("직급", "jobTitleName", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("역할", "levelName", 20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("부서", "teamName", 40, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("게시글 등록", "docCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("주간보고등록", "weeklyCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("조회", "hitCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("추천", "recommendCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("댓글", "lineReplyCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("Favorite", "favoriteCount", 10, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING, "",
						ExcelModelConstants.ALIGN_LEFT);

				fileName = "Member활동통계_" + DateUtil.getToday("yyyy-MM-dd") + ".xlsx";
			}

			excel.setDataList(statisticsList);

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 팀원 통계 엑셀다운로드 ...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel);
	}
}