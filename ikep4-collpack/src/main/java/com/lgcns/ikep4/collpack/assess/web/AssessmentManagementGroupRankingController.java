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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Assessment Management AssessmentManagementGroupRanking controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupRankingController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess")
public class AssessmentManagementGroupRankingController extends CustomController {

	@Autowired
	private AssessmentManagementGroupPviService assessmentManagementGroupPviService;

	@Autowired
	private AssessmentManagementUserPviService assessmentManagementUserPviService;

	/**
	 * 조직별 평가순위 View
	 * @param assessmentManagementBlockPageCondition - 페이징 조회조건
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/groupRankingView")
	public ModelAndView groupRankingView(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition, BindingResult result, SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		assessmentManagementBlockPageCondition.setPortalId(portalId);

		List<AssessmentManagementGroupPvi> assessmentManagementGroupPviRootList = null;
		List<AssessmentManagementGroupPvi> assessmentManagementGroupPviHierarchyList = null;
		List<AssessmentManagementGroupPvi> assessmentManagementGroupPviList = null;
		List<AssessmentManagementUserPvi> assessmentManagementUserPviList = null;

		// 포털별 최상위 조직
		assessmentManagementGroupPviRootList = assessmentManagementGroupPviService.listRootGroupByPortalId(portalId);

		// Locale 설정
		if (!isSameLocale()) {
			if (null != assessmentManagementGroupPviRootList) {
				for (AssessmentManagementGroupPvi item : assessmentManagementGroupPviRootList) {
					item.setGroupName(item.getGroupEnglishName());
				}
			}
		}

		// 메뉴에서 처음온 접근
		if (StringUtil.isEmpty(assessmentManagementBlockPageCondition.getGroupId())) {
			if (0 < assessmentManagementGroupPviRootList.size()) {
				assessmentManagementBlockPageCondition.setGroupId(assessmentManagementGroupPviRootList.get(0).getGroupId());
			}
			assessmentManagementBlockPageCondition.setReInit(true);
		}

		// groupId 가 있을경우만 실행
		if (!StringUtil.isEmpty(assessmentManagementBlockPageCondition.getGroupId())) {
			// 상단 조직 Hierarchy
			assessmentManagementGroupPviHierarchyList = assessmentManagementGroupPviService.listGroupHierarchy(assessmentManagementBlockPageCondition.getGroupId());

			// 하위조직여부 판단 (있으면 조직별, 없으면 사용자별)
			int childCount = assessmentManagementGroupPviService.countByGroupIdPage(assessmentManagementBlockPageCondition.getGroupId());

			if (0 < childCount) {
				// 조직별 조회
				assessmentManagementBlockPageCondition.setTotalCount(childCount);
				assessmentManagementBlockPageCondition.calculate();

				// 조직별 평가 리스트
				assessmentManagementGroupPviList = assessmentManagementGroupPviService.listByGroupIdPage(assessmentManagementBlockPageCondition);
			} else {
				// 사용자별 조회
				assessmentManagementBlockPageCondition.setTotalCount(assessmentManagementUserPviService.countByGroupIdPage(assessmentManagementBlockPageCondition));
				assessmentManagementBlockPageCondition.calculate();

				// 사용자별 평가 리스트
				assessmentManagementUserPviList = assessmentManagementUserPviService.listByGroupIdPage(assessmentManagementBlockPageCondition);
			}

			// Locale 설정
			if (!isSameLocale()) {
				for (AssessmentManagementGroupPvi item : assessmentManagementGroupPviHierarchyList) {
					item.setGroupName(item.getGroupEnglishName());
				}

				if (null != assessmentManagementGroupPviList) {
					for (AssessmentManagementGroupPvi item : assessmentManagementGroupPviList) {
						item.setGroupName(item.getGroupEnglishName());
					}
				}

				if (null != assessmentManagementUserPviList) {
					for (AssessmentManagementUserPvi item : assessmentManagementUserPviList) {
						item.setUserName(item.getUserEnglishName());
						item.setTeamName(item.getTeamEnglishName());
						item.setJobTitleName(item.getJobTitleEnglishName());
					}
				}
			}
		}

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/groupRankingView");

		mav.addObject("menuId", "21");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		mav.addObject("assessmentManagementGroupPviRootList", assessmentManagementGroupPviRootList);
		mav.addObject("assessmentManagementGroupPviHierarchyList", assessmentManagementGroupPviHierarchyList);
		mav.addObject("assessmentManagementGroupPviList", assessmentManagementGroupPviList);
		mav.addObject("assessmentManagementUserPviList", assessmentManagementUserPviList);
		mav.addObject("pageCondition", assessmentManagementBlockPageCondition);

		return mav;
	}

	/**
	 * 조직별 평가순위 엑셀 저장
	 * @param assessmentManagementBlockPageCondition - 조회조건
	 * @param result
	 * @param status
	 * @param response
	 */
	@RequestMapping(value = "/groupRankingExcel")
	public void groupRankingExcel(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition, BindingResult result, SessionStatus status, HttpServletResponse response) {
		// 저장될 Excel 파일명
		String excelFileName = "GroupAssessmentRanking.xlsx";

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 사용자 평가 리스트
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setExcel(true);

		List<AssessmentManagementUserPvi> assessmentManagementUserPviList = assessmentManagementUserPviService.listByGroupIdPage(assessmentManagementBlockPageCondition);

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
		titleMap.put("rank", getMessage("ui.collpack.assess.groupRanking.table.col1"));
		titleMap.put("userName", getMessage("ui.collpack.assess.groupRanking.table.col91"));
		titleMap.put("jobTitleName", getMessage("ui.collpack.assess.groupRanking.table.col92"));
		titleMap.put("teamName", getMessage("ui.collpack.assess.groupRanking.table.col93"));
		titleMap.put("pvi", getMessage("ui.collpack.assess.groupRanking.table.col3"));
		titleMap.put("contributionScore", getMessage("ui.collpack.assess.groupRanking.table.col4"));
		titleMap.put("participationScore", getMessage("ui.collpack.assess.groupRanking.table.col5"));
		titleMap.put("applicationScore", getMessage("ui.collpack.assess.groupRanking.table.col6"));
		titleMap.put("friendshipScore", getMessage("ui.collpack.assess.groupRanking.table.col7"));
		titleMap.put("influenceScore", getMessage("ui.collpack.assess.groupRanking.table.col8"));

		// 엑셀 데이터 만들기
		List<Object> dataList = new ArrayList<Object>();
		for (AssessmentManagementUserPvi item : assessmentManagementUserPviList) {
			dataList.add(item);
		}

		ExcelUtil.saveExcel(titleMap, dataList, excelFileName, response);
		//ExcelUtil.saveExcel(titleMap, assessmentManagementUserPviList, excelFileName, response);
	}

}
