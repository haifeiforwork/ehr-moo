/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicy;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicyPK;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPolicyService;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;

/**
 * Assessment Management AssessmentManagementUserRanking controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserRankingController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess")
public class AssessmentManagementUserRankingController extends CustomController {

	@Autowired
	private AssessmentManagementUserPviService assessmentManagementUserPviService;

	@Autowired
	private AssessmentManagementPolicyService assessmentManagementPolicyService;

	/**
	 * 사용자 평가순위 View
	 * @param assessmentManagementBlockPageCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/userRanking")
	public ModelAndView userRanking(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition, BindingResult result, SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		// 사용자 평가 리스트
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setTotalCount(assessmentManagementUserPviService.countByPortalIdPage(assessmentManagementBlockPageCondition));
		assessmentManagementBlockPageCondition.calculate();

		List<AssessmentManagementUserPvi> assessmentManagementUserPviList = assessmentManagementUserPviService.listByPortalIdPage(assessmentManagementBlockPageCondition);

		// Locale 설정
		if (!isSameLocale()) {
			for (AssessmentManagementUserPvi item : assessmentManagementUserPviList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/userRanking");

		mav.addObject("menuId", "11");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		mav.addObject("assessmentManagementUserPviList", assessmentManagementUserPviList);
		mav.addObject("pageCondition", assessmentManagementBlockPageCondition);

		return mav;
	}

	/**
	 * 사용자 평가순위 Excel Download
	 * @param assessmentManagementBlockPageCondition
	 * @param result
	 * @param status
	 * @param response
	 */
	@RequestMapping(value = "/userRankingExcel")
	public void userRankingExcel(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition, BindingResult result, SessionStatus status, HttpServletResponse response) {
		// 저장될 Excel 파일명
		String excelFileName = "UserAssessmentRanking.xlsx";

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 사용자 평가 리스트
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setExcel(true);

		List<AssessmentManagementUserPvi> assessmentManagementUserPviList = assessmentManagementUserPviService.listByPortalIdPage(assessmentManagementBlockPageCondition);

		// Locale 설정
		if (!isSameLocale()) {
			for (AssessmentManagementUserPvi item : assessmentManagementUserPviList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		// 엑셀 타이틀 만들기
		LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
		titleMap.put("rank", getMessage("ui.collpack.assess.userRanking.table.col1"));
		titleMap.put("userName", getMessage("ui.collpack.assess.userRanking.table.col2"));
		titleMap.put("jobTitleName", getMessage("ui.collpack.assess.userRanking.table.col91"));
		titleMap.put("teamName", getMessage("ui.collpack.assess.userRanking.table.col3"));
		titleMap.put("pvi", getMessage("ui.collpack.assess.userRanking.table.col4"));
		titleMap.put("contributionScore", getMessage("ui.collpack.assess.userRanking.table.col5"));
		titleMap.put("participationScore", getMessage("ui.collpack.assess.userRanking.table.col6"));
		titleMap.put("applicationScore", getMessage("ui.collpack.assess.userRanking.table.col7"));
		titleMap.put("friendshipScore", getMessage("ui.collpack.assess.userRanking.table.col8"));
		titleMap.put("influenceScore", getMessage("ui.collpack.assess.userRanking.table.col9"));

		// 엑셀 데이터 만들기
		List<Object> dataList = new ArrayList<Object>();
		for (AssessmentManagementUserPvi item : assessmentManagementUserPviList) {
			dataList.add(item);
		}

		ExcelUtil.saveExcel(titleMap, dataList, excelFileName, response);
		//ExcelUtil.saveExcel(titleMap, assessmentManagementUserPviList, excelFileName, response);
	}

	/**
	 * 프로파일에서 요청하는 사용자 PVI점수
	 * @param userId - 조회할 사용자ID
	 * @param portalId - 포털ID
	 * @return
	 */
	@RequestMapping(value="/getUserPivInfo")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getUserPivInfo(String userId, String portalId) {

		// 평가 기준
		AssessmentManagementPolicy assessmentManagementPolicy = assessmentManagementPolicyService.read(new AssessmentManagementPolicyPK(portalId));

		// 사용자별 점수 미치 심볼정보
		AssessmentManagementUserPvi assessmentManagementUserPvi = assessmentManagementUserPviService.getWithSymbolByUserId(userId);

		int pviScore = 0;
		int pviStep = 0;
		int pviRank = 0;
		int contributionMax = 0;
		int participationMax = 0;
		int applicationMax = 0;
		int friendshipMax = 0;
		int leadershipMax = 0;
		int contributionScore = 0;
		int participationScore = 0;
		int applicationScore = 0;
		int friendshipScore = 0;
		int leadershipScore = 0;

		if (null != assessmentManagementPolicy) {
			contributionMax = assessmentManagementPolicy.getContributionMax();
			participationMax = assessmentManagementPolicy.getParticipationMax();
			applicationMax = assessmentManagementPolicy.getApplicationMax();
			friendshipMax = assessmentManagementPolicy.getFriendshipMax();
			leadershipMax = assessmentManagementPolicy.getLeadershipMax();
		}

		if (null != assessmentManagementUserPvi) {
			pviScore = assessmentManagementUserPvi.getPvi();
			pviStep = assessmentManagementUserPvi.getStep();
			pviRank = assessmentManagementUserPvi.getRank();

			contributionScore = assessmentManagementUserPvi.getContributionScore();
			participationScore = assessmentManagementUserPvi.getParticipationScore();
			applicationScore = assessmentManagementUserPvi.getApplicationScore();
			friendshipScore = assessmentManagementUserPvi.getFriendshipScore();
			leadershipScore = assessmentManagementUserPvi.getInfluenceScore();
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("portalId", portalId); // 포털ID
		jsonObj.put("userId", userId);  // 사용자ID
		jsonObj.put("pviScore", pviScore);  // PVI 점수
		jsonObj.put("pviStep", pviStep);  // 0~9 단계 Symbol Step
		jsonObj.put("pviRank", pviRank);  // PVI 랭킹점수

		jsonObj.put("contributionMax", contributionMax);  // 기여점수 (만점)
		jsonObj.put("participationMax", participationMax);  // 참여점수 (만점)
		jsonObj.put("applicationMax", applicationMax);  // 활용점수 (만점)
		jsonObj.put("friendshipMax", friendshipMax);  // 친화점수 (만점)
		jsonObj.put("leadershipMax", leadershipMax);  // 주도점수 (만점)

		jsonObj.put("contributionScore", contributionScore);  // 기여점수
		jsonObj.put("participationScore", participationScore);  // 참여점수
		jsonObj.put("applicationScore", applicationScore);  // 활용점수
		jsonObj.put("friendshipScore", friendshipScore);  // 친화점수
		jsonObj.put("leadershipScore", leadershipScore);  // 주도점수

		return jsonObj.toString();
	}

}
