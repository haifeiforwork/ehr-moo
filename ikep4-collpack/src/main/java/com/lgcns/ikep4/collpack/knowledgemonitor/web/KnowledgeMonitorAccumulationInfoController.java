/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.web;

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

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulation;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Knowledge Monitor KnowledgeMonitorAccumulationInfo controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorAccumulationInfoController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgemonitor")
public class KnowledgeMonitorAccumulationInfoController extends CustomController {

	@Autowired
	private KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	@Autowired
	private KnowledgeMonitorStatisticsService knowledgeMonitorStatisticsService;

	/**
	 * 검색조건 세팅
	 * @param knowledgeAccumulationSearchCondition - 조회조건
	 * @param knowledgeMonitorModuleList - 사용가능한 모듈 리스트
	 */
	private void settingSearchCondition(KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition,
			List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		// 메뉴에서 온 처음 접근
		if (!knowledgeAccumulationSearchCondition.isNotFirstAccess()) {
			knowledgeAccumulationSearchCondition.setNotFirstAccess(true);

			settingSearchConditionFirstAccess(knowledgeAccumulationSearchCondition, knowledgeMonitorModuleList);
		} else {
			settingSearchConditionNotFirstAccess(knowledgeAccumulationSearchCondition, knowledgeMonitorModuleList);
		}
	}

	/**
	 * 검색조건 세팅 (화면 로딩시)
	 * @param knowledgeAccumulationSearchCondition - 조회조건
	 * @param knowledgeMonitorModuleList - 사용가능한 모듈 리스트
	 */
	private void settingSearchConditionFirstAccess(KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition,
			List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		// 검색대상 몽땅 선택
		StringBuffer sb = new StringBuffer();
		StringBuffer sbExclude = new StringBuffer();
		for (KnowledgeMonitorModule item : knowledgeMonitorModuleList) {
			if (1 == item.getIsUsage()) {
				item.setChecked(true);
				sb.append(CommonConstant.COMMA_SEPARATER);
				sb.append(item.getModuleCode());
			} else {
				sbExclude.append(CommonConstant.COMMA_SEPARATER);
				sbExclude.append(item.getModuleCode());
			}
		}

		if (1 > sb.length()) {
			knowledgeAccumulationSearchCondition.setModuleCodes("");
		} else {
			knowledgeAccumulationSearchCondition.setModuleCodes(sb.substring(1));
		}
		if (1 > sbExclude.length()) {
			knowledgeAccumulationSearchCondition.setExcludeModuleCodes("");
		} else {
			knowledgeAccumulationSearchCondition.setExcludeModuleCodes(sbExclude.substring(1));
		}
	}

	/**
	 * 검색조건 세팅
	 * @param knowledgeAccumulationSearchCondition - 조회조건
	 * @param knowledgeMonitorModuleList - 사용가능한 모듈 리스트
	 */
	private void settingSearchConditionNotFirstAccess(KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition,
			List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		// 폼에서 체크한 모듈 선택
		String[] moduleCodes = StringUtil.nvl(knowledgeAccumulationSearchCondition.getModuleCodes(), "").split(CommonConstant.COMMA_SEPARATER);
		for (String item : moduleCodes) {
			for (KnowledgeMonitorModule moduleItem : knowledgeMonitorModuleList) {
				if (moduleItem.getModuleCode().equals(item)) {
					moduleItem.setChecked(true);
					break;
				}
			}
		}

		// 검색대상에서 빠진 모듈 구하기
		StringBuffer sb = new StringBuffer();
		for (KnowledgeMonitorModule item : knowledgeMonitorModuleList) {
			if (!item.isChecked()) {
				sb.append(CommonConstant.COMMA_SEPARATER);
				sb.append(item.getModuleCode());
			}
		}
		if (1 > sb.length()) {
			knowledgeAccumulationSearchCondition.setExcludeModuleCodes("");
		} else {
			knowledgeAccumulationSearchCondition.setExcludeModuleCodes(sb.substring(1));
		}
	}
	
	/**
	 * 지식축적정보 View
	 * @param knowledgeAccumulationSearchCondition - 검색조건
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgeAccumulationInfo")
	public ModelAndView knowledgeAccumulationInfo(
			KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition, 
			BindingResult result, 
			SessionStatus status) {
		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean knowledgeMonitorAdmin = isAdmin(user.getUserId());

		// 모든정책 리스트
		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService.listByPortalId(portalId);

		// 검색조건 항목 세팅
		settingSearchCondition(knowledgeAccumulationSearchCondition, knowledgeMonitorModuleList);
		knowledgeAccumulationSearchCondition.setPortalId(portalId);
		
		// Chart 리스트
		List<KnowledgeMonitorAccumulationChart> knowledgeMonitorAccumulationChartList = knowledgeMonitorStatisticsService.listChartBySearchCondition(knowledgeAccumulationSearchCondition);

		// 리스트
		String sumText = getMessage("ui.collpack.knowledgemonitor.knowledgeAccumulationInfo.table.list.sum");
		knowledgeAccumulationSearchCondition.setSumText(sumText);
		List<KnowledgeMonitorAccumulation> knowledgeMonitorAccumulationList = knowledgeMonitorStatisticsService.listBySearchCondition(knowledgeAccumulationSearchCondition);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgemonitor/knowledgeAccumulationInfo");

		mav.addObject("menuId", "11");
		mav.addObject("knowledgeMonitorAdmin", knowledgeMonitorAdmin);
		mav.addObject("knowledgeMonitorModuleList", knowledgeMonitorModuleList);
		mav.addObject("searchCondition", knowledgeAccumulationSearchCondition);
		mav.addObject("knowledgeMonitorAccumulationChartList", knowledgeMonitorAccumulationChartList);
		mav.addObject("knowledgeMonitorAccumulationList", knowledgeMonitorAccumulationList);

		return mav;
	}


	/**
	 * 지식축적정보 Excel Download
	 * @param knowledgeAccumulationSearchCondition - 검색조건
	 * @param result
	 * @param status
	 * @param response
	 */
	@RequestMapping(value = "/knowledgeAccumulationInfoExcel")
	public void knowledgeAccumulationInfoExcel(KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition, BindingResult result, SessionStatus status, HttpServletResponse response) {

		// 저장될 Excel 파일명
		String excelFileName = "KnowledgeAccumulation.xlsx";

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 모든정책 리스트
		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService.listByPortalId(portalId);

		// 검색조건 항목 세팅
		settingSearchCondition(knowledgeAccumulationSearchCondition, knowledgeMonitorModuleList);
		knowledgeAccumulationSearchCondition.setPortalId(portalId);

		// 리스트
		String sumText = getMessage("ui.collpack.knowledgemonitor.knowledgeAccumulationInfo.table.list.sum");
		knowledgeAccumulationSearchCondition.setSumText(sumText);
		List<KnowledgeMonitorAccumulation> knowledgeMonitorAccumulationList = knowledgeMonitorStatisticsService.listBySearchCondition(knowledgeAccumulationSearchCondition);

		// 엑셀 타이틀 만들기
		String titleYmd = getMessage("ui.collpack.knowledgemonitor.knowledgeAccumulationInfo.table.title.col1");
		String titleAll = getMessage("ui.collpack.knowledgemonitor.knowledgeAccumulationInfo.table.title.col2");
		String titlePublic = getMessage("ui.collpack.knowledgemonitor.knowledgeAccumulationInfo.table.title.col3");

		LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
		for (KnowledgeMonitorModule item : knowledgeMonitorModuleList) {
			titleMap.put("ymd", titleYmd);
			titleMap.put("publicDocCount", titleAll);
			titleMap.put("allDocCount", titleAll);

			if (item.isChecked()) {
				titleMap.put(item.getModuleCode().toLowerCase() + "PublicDocCount", item.getModuleName()+" "+titlePublic);
				titleMap.put(item.getModuleCode().toLowerCase() + "AllDocCount", item.getModuleName()+" "+titleAll);
			}
		}

		// 엑셀 데이터 만들기
		List<Object> dataList = new ArrayList<Object>();
		for (KnowledgeMonitorAccumulation item : knowledgeMonitorAccumulationList) {
			dataList.add(item);
		}

		ExcelUtil.saveExcel(titleMap, dataList, excelFileName, response);
	}

}
