/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Knowledge Monitor KnowledgeMonitorRankingInfo controller
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorRankingInfoController.java 16478 2011-09-01
 *          01:50:46Z handul32 $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgemonitor")
public class KnowledgeMonitorRankingInfoController extends CustomController {

	@Autowired
	private KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	@Autowired
	private KnowledgeMonitorCviPointService knowledgeMonitorCviPointService;

	/**
	 * 검색,페이징조건 세팅
	 * 
	 * @param knowledgeRankBlockPageCondition
	 * @param knowledgeMonitorModuleList
	 */
	private void settingSearchCondition(KnowledgeRankBlockPageCondition knowledgeRankBlockPageCondition,
			List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		// 메뉴에서 온 처음 접근
		if (!knowledgeRankBlockPageCondition.isNotFirstAccess()) {

			// 검색대상 몽땅 선택
			StringBuffer sb = new StringBuffer();
			for (KnowledgeMonitorModule item : knowledgeMonitorModuleList) {
				item.setChecked(true);
				sb.append(CommonConstant.COMMA_SEPARATER);
				sb.append(item.getModuleCode());
			}
			knowledgeRankBlockPageCondition.setModuleCodes(sb.substring(1));

			knowledgeRankBlockPageCondition.setNotFirstAccess(true);
		} else {
			// 폼에서 체크한 모듈 선택
			String[] moduleCodes = StringUtil.nvl(knowledgeRankBlockPageCondition.getModuleCodes(), "").split(
					CommonConstant.COMMA_SEPARATER);
			for (String item : moduleCodes) {
				for (KnowledgeMonitorModule moduleItem : knowledgeMonitorModuleList) {
					if (moduleItem.getModuleCode().equals(item)) {
						moduleItem.setChecked(true);
						break;
					}
				}
			}
		}
	}

	/**
	 * 지식순위정보 View
	 * 
	 * @param knowledgeRankBlockPageCondition - 페이징 조회 조건
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgeRankingInfoView")
	public ModelAndView knowledgeRankingInfoView(KnowledgeRankBlockPageCondition knowledgeRankBlockPageCondition,
			BindingResult result, SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean knowledgeMonitorAdmin = isAdmin(user.getUserId());

		// 사용중인정책 리스트
		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService
				.listUsableByPortalId(portalId);

		// 검색조건 항목 세팅
		settingSearchCondition(knowledgeRankBlockPageCondition, knowledgeMonitorModuleList);

		if (knowledgeRankBlockPageCondition.getItemType().equals("month")) {
			knowledgeRankBlockPageCondition.setFromDate(knowledgeRankBlockPageCondition.getFromYear()
					+ knowledgeRankBlockPageCondition.getFromMonth() + "01");
			knowledgeRankBlockPageCondition.setToDate(knowledgeRankBlockPageCondition.getToYear()
					+ knowledgeRankBlockPageCondition.getToMonth()
					+ String.valueOf(DateUtil.getLastDate(
							Integer.parseInt(knowledgeRankBlockPageCondition.getToYear()),
							Integer.parseInt(knowledgeRankBlockPageCondition.getToMonth()))));
		}

		knowledgeRankBlockPageCondition.setPortalId(portalId);
		knowledgeRankBlockPageCondition.setTotalCount(knowledgeMonitorCviPointService
				.countByPortalIdModuleCode(knowledgeRankBlockPageCondition));
		knowledgeRankBlockPageCondition.calculate();

		// 지식 페이징 조회
		List<KnowledgeMonitorCviPoint> knowledgeMonitorCviPointList = knowledgeMonitorCviPointService
				.listByPortalIdModuleCodePage(knowledgeRankBlockPageCondition);

		// URL 설정
		for (KnowledgeMonitorCviPoint item : knowledgeMonitorCviPointList) {
			item.setItemUrl(getModuleURL(item.getModuleCode()) + item.getItemId());
		}

		// Locale 설정
		if (!isSameLocale()) {
			for (KnowledgeMonitorCviPoint item : knowledgeMonitorCviPointList) {
				item.setUserName(item.getUserEnglishName());
			}
		}

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgemonitor/knowledgeRankingInfoView");

		mav.addObject("menuId", "21");
		mav.addObject("knowledgeMonitorAdmin", knowledgeMonitorAdmin);
		mav.addObject("knowledgeMonitorModuleList", knowledgeMonitorModuleList);
		mav.addObject("pageCondition", knowledgeRankBlockPageCondition);
		mav.addObject("knowledgeMonitorCviPointList", knowledgeMonitorCviPointList);

		return mav;
	}

}
