/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.approval.admin.model.ApprStat;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.approval.admin.service.ApprStatService;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.code.Code;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.web.TreeMaker;


/**
 * 통계 Controller
 * 
 * @author 유승목
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/admin/apprStat")
public class ApprStatController extends BaseController {

	@Autowired
	private ApprStatService apprStatService;

	@Autowired
	private ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ApprCodeService apprCodeService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private GroupService groupService;

	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}

	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}

	/**
	 * 리드타임 통계
	 * 
	 * @param apprListSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/timeStatForm.do")
	public ModelAndView timeStatForm(ApprListSearchCondition apprListSearchCondition) {

		ModelAndView mav = new ModelAndView("/approval/admin/apprStat/timeStatForm");

		try {
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			String portalId = (String) getRequestAttribute("ikep.portalId");

			Map map = new HashMap();
			map.put("userId", user.getUserId());
			Group rootGroup = groupService.selectUserRootGroup(map);

			mav.addObject("rootGroup", rootGroup);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portalId));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 리드타임 통계
	 * 
	 * @return
	 */
	@RequestMapping(value = "/timeStat.do")
	public ModelAndView timeStat(ApprListSearchCondition apprListSearchCondition, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/approval/admin/apprStat/timeStat");

		try {

			if (apprListSearchCondition == null) {
				apprListSearchCondition = new ApprListSearchCondition();
			}

			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			String portalId = (String) getRequestAttribute("ikep.portalId");

			apprListSearchCondition.setPortalId(portalId);
			if (apprListSearchCondition.getSearchStartYear() == null
					|| apprListSearchCondition.getSearchEndYear() == null) {

				String curDay = DateUtil.getToday("");
				String prevDay = DateUtil.getPrevMonthDate(curDay.replaceAll("-", ""), 6, "");

				apprListSearchCondition.setSearchStartYear(prevDay.substring(0, 4));
				apprListSearchCondition.setSearchStartMonth(prevDay.substring(5, 7));
				apprListSearchCondition.setSearchEndYear(curDay.substring(0, 4));
				apprListSearchCondition.setSearchEndMonth(curDay.substring(5, 7));
			}

			if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
				apprListSearchCondition.setSortColumn("cntSum");
				apprListSearchCondition.setSortType("DESC");
			}

			if (StringUtil.isEmpty(apprListSearchCondition.getSearchStatisType())) {
				apprListSearchCondition.setSearchStatisType("0");
			}

			setSearchYmList(apprListSearchCondition);

			SearchResult<ApprStat> searchResult = apprStatService.getTimeStatList(apprListSearchCondition);

			if (StringUtil.isEmpty(apprListSearchCondition.getIsExcel())) {

				mav.addObject("searchResult", searchResult);
				mav.addObject("searchCondition", searchResult.getSearchCondition());
				mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
				mav.addObject("isReadAll", this.isReadAll(portalId));
				mav.addObject("commonCode", ApprList.getPageNumList());

				setCodeList(mav);
			} else {
				LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
				titleMap.put("groupName", "");
				setTitleMap(apprListSearchCondition, titleMap);
				if (apprListSearchCondition.getSearchStatisType().equals("0")) {
					titleMap.put("cntSum", "Total");
				} else {
					titleMap.put("timeSum", "Total");
				}

				List<Object> excelList = new ArrayList<Object>();
				for (ApprStat apprStat : searchResult.getEntity()) {
					excelList.add(apprStat);
				}

				// 엑셀 파일 저장
				ExcelUtil.saveExcel(titleMap, excelList, "excel_" + DateUtil.getToday("") + ".xlsx", response);
			}

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 사용자별 통계
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userStat.do")
	public ModelAndView userStat(ApprListSearchCondition apprListSearchCondition, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/approval/admin/apprStat/userStat");

		try {

			if (apprListSearchCondition == null) {
				apprListSearchCondition = new ApprListSearchCondition();
			}

			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			String portalId = (String) getRequestAttribute("ikep.portalId");

			apprListSearchCondition.setPortalId(portalId);
			if (apprListSearchCondition.getSearchStartYear() == null
					|| apprListSearchCondition.getSearchEndYear() == null) {

				String curDay = DateUtil.getToday("");
				String prevDay = DateUtil.getPrevMonthDate(curDay.replaceAll("-", ""), 6, "");

				apprListSearchCondition.setSearchStartYear(prevDay.substring(0, 4));
				apprListSearchCondition.setSearchStartMonth(prevDay.substring(5, 7));
				apprListSearchCondition.setSearchEndYear(curDay.substring(0, 4));
				apprListSearchCondition.setSearchEndMonth(curDay.substring(5, 7));
			}

			if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
				apprListSearchCondition.setSortColumn("cntSum");
				apprListSearchCondition.setSortType("DESC");
			}

			if (StringUtil.isEmpty(apprListSearchCondition.getSearchStatisType())) {
				apprListSearchCondition.setSearchStatisType("0");
			}

			setSearchYmList(apprListSearchCondition);

			SearchResult<ApprStat> searchResult = apprStatService.getUserStatList(apprListSearchCondition);

			if (StringUtil.isEmpty(apprListSearchCondition.getIsExcel())) {

				mav.addObject("searchResult", searchResult);
				mav.addObject("searchCondition", searchResult.getSearchCondition());
				mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
				mav.addObject("isReadAll", this.isReadAll(portalId));
				mav.addObject("commonCode", ApprList.getPageNumList());

				setCodeList(mav);

			} else {
				LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
				titleMap.put("approverName", "");
				setTitleMap(apprListSearchCondition, titleMap);
				if (apprListSearchCondition.getSearchStatisType().equals("0")) {
					titleMap.put("cntSum", "Total");
				} else {
					titleMap.put("timeSum", "Total");
				}

				List<Object> excelList = new ArrayList<Object>();
				for (ApprStat apprStat : searchResult.getEntity()) {
					excelList.add(apprStat);
				}

				// 엑셀 파일 저장
				ExcelUtil.saveExcel(titleMap, excelList, "excel_" + DateUtil.getToday("") + ".xlsx", response);
			}

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 양식별 통계
	 * 
	 * @return
	 */
	@RequestMapping(value = "/formStat.do")
	public ModelAndView formStat() {

		ModelAndView mav = new ModelAndView("/approval/admin/apprStat/formStat");

		try {

			// session
			User user = (User) getRequestAttribute("ikep.user");

			// category root 코드 아이디를 구함
			String formParentId = StringUtil.nvl(
					apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_CATEGORY, user.getPortalId()), "");

			// category tree json 생성
			String categoryTreeJson = "{}";
			if (!"".equals(formParentId)) {
				List<ApprCode> apprCodeList = apprCodeService.getApprCodeList(formParentId, user.getLocaleCode(), "9");
				categoryTreeJson = TreeMaker.init(apprCodeList, "codeId", "parentCodeId", "childCount").create()
						.toJsonString();
			}

			// 디폴트 검색 조건 셋팅
			ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();
			apprListSearchCondition.setFormParentId(formParentId);
			apprListSearchCondition.setTopFormParentId(formParentId);

			ApprCode apprCode = apprCodeService.readApprCode(formParentId);
			if (apprCode != null) {
				apprListSearchCondition.setFormParentName("ko".equals(user.getLocaleCode()) ? apprCode.getCodeKrName()
						: apprCode.getCodeEnName());
			}
			// set return info
			mav.addObject("searchCondition", apprListSearchCondition);
			mav.addObject("categoryTreeJson", categoryTreeJson);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(user.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 카테고리별 양식별 통계
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ajaxFormStat.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView ajaxFormStat(ApprListSearchCondition apprListSearchCondition, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/approval/admin/apprStat/ajaxFormStat");

		try {

			if (apprListSearchCondition == null) {
				apprListSearchCondition = new ApprListSearchCondition();
			}

			// session
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");

			apprListSearchCondition.setPortalId(portalId);
			if (apprListSearchCondition.getSearchStartYear() == null
					|| apprListSearchCondition.getSearchEndYear() == null) {

				String curDay = DateUtil.getToday("");
				String prevDay = DateUtil.getPrevMonthDate(curDay.replaceAll("-", ""), 6, "");

				apprListSearchCondition.setSearchStartYear(prevDay.substring(0, 4));
				apprListSearchCondition.setSearchStartMonth(prevDay.substring(5, 7));
				apprListSearchCondition.setSearchEndYear(curDay.substring(0, 4));
				apprListSearchCondition.setSearchEndMonth(curDay.substring(5, 7));
			}

			setSearchYmList(apprListSearchCondition);

			SearchResult<ApprStat> searchResult = apprStatService.getFormStatList(apprListSearchCondition);

			if (StringUtil.isEmpty(apprListSearchCondition.getIsExcel())) {

				mav.addObject("searchResult", searchResult);
				mav.addObject("searchCondition", searchResult.getSearchCondition());
				mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
				mav.addObject("isReadAll", this.isReadAll(user.getPortalId()));

				setCodeList(mav);

			} else {
				LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
				titleMap.put("formName", "");
				setTitleMap(apprListSearchCondition, titleMap);

				List<Object> excelList = new ArrayList<Object>();
				for (ApprStat apprStat : searchResult.getEntity()) {
					excelList.add(apprStat);
				}

				// 엑셀 파일 저장
				ExcelUtil.saveExcel(titleMap, excelList, "excel_" + DateUtil.getToday("") + ".xlsx", response);
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxFormStat", ex);
		}
		return mav;
	}

	/**
	 * 양식별 통계 엑셀 다운로드
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excelFormStat.do")
	@ResponseStatus(HttpStatus.OK)
	public void excelFormStat(ApprListSearchCondition apprListSearchCondition, HttpServletResponse response) {

			if (apprListSearchCondition == null) {
				apprListSearchCondition = new ApprListSearchCondition();
			}
			
			// session
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
	
			apprListSearchCondition.setPortalId(portalId);
			if (apprListSearchCondition.getSearchStartYear() == null
					|| apprListSearchCondition.getSearchEndYear() == null) {
	
				String curDay = DateUtil.getToday("");
				String prevDay = DateUtil.getPrevMonthDate(curDay.replaceAll("-", ""), 6, "");
	
				apprListSearchCondition.setSearchStartYear(prevDay.substring(0, 4));
				apprListSearchCondition.setSearchStartMonth(prevDay.substring(5, 7));
				apprListSearchCondition.setSearchEndYear(curDay.substring(0, 4));
				apprListSearchCondition.setSearchEndMonth(curDay.substring(5, 7));
			}
	
			setSearchYmList(apprListSearchCondition);
	
			SearchResult<ApprStat> searchResult = apprStatService.getFormStatList(apprListSearchCondition);

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
			titleMap.put("formName", "양식명");
			setTitleMap(apprListSearchCondition, titleMap);
			titleMap.put("totSum", "합계");

			List<Object> excelList = new ArrayList<Object>();
			for (ApprStat apprStat : searchResult.getEntity()) {
				excelList.add(apprStat);
			}

			// 엑셀 파일 저장
			ExcelUtil.saveExcel(titleMap, excelList, "formexcel_" + DateUtil.getToday("") + ".xlsx", response);
		
	}
	
	private void setCodeList(ModelAndView mav) {

		List<Code> yearList = new ArrayList<Code>();
		List<Code> monthList = new ArrayList<Code>();
		List<Code> typeList = new ArrayList<Code>();

		String curYear = DateUtil.getToday("").substring(0, 4);
		for (int i = 2000; i <= Integer.parseInt(curYear); i++) {
			yearList.add(new Code<String>("" + i, "" + i));
		}

		for (int i = 1; i <= 12; i++) {
			if (i < 10) {
				monthList.add(new Code<String>("0" + i, "0" + i));
			} else {
				monthList.add(new Code<String>("" + i, "" + i));
			}
		}

		typeList.add(new Code<String>("0", "ui.approval.common.searchCondition.searchStatisType0"));
		typeList.add(new Code<String>("1", "ui.approval.common.searchCondition.searchStatisType1"));

		mav.addObject("yearList", yearList);
		mav.addObject("monthList", monthList);
		mav.addObject("typeList", typeList);

	}

	private void setSearchYmList(ApprListSearchCondition apprListSearchCondition) {

		int startYear = Integer.parseInt(apprListSearchCondition.getSearchStartYear());
		int endYear = Integer.parseInt(apprListSearchCondition.getSearchEndYear());
		int startMonth = Integer.parseInt(apprListSearchCondition.getSearchStartMonth());
		int endMonth = Integer.parseInt(apprListSearchCondition.getSearchEndMonth());

		List<String> list = new ArrayList<String>();
		if (startYear == endYear) {
			for (int j = startMonth; j <= endMonth; j++) {
				if (j < 10) {
					list.add(startYear + "-0" + j);
				} else {
					list.add(startYear + "-" + j);
				}
			}
		}

		if (startYear != endYear) {
			for (int j = startMonth; j <= 12; j++) {
				if (j < 10) {
					list.add(startYear + "-0" + j);
				} else {
					list.add(startYear + "-" + j);
				}
			}

			endYear = startYear + 1;
			if ((13 - startMonth) + endMonth > 12) {
				endMonth = 12 - (13 - startMonth);
			}
			for (int j = 1; j <= endMonth; j++) {
				if (j < 10) {
					list.add(endYear + "-0" + j);
				} else {
					list.add(endYear + "-" + j);
				}
			}

			apprListSearchCondition.setSearchEndYear("" + endYear);
			if (endMonth < 10) {
				apprListSearchCondition.setSearchEndMonth("0" + endMonth);
			} else {
				apprListSearchCondition.setSearchEndMonth("" + endMonth);
			}

		}

		if (list.size() >= 1) {
			apprListSearchCondition.setYm1(list.get(0));
		}
		if (list.size() >= 2) {
			apprListSearchCondition.setYm2(list.get(1));
		}
		if (list.size() >= 3) {
			apprListSearchCondition.setYm3(list.get(2));
		}
		if (list.size() >= 4) {
			apprListSearchCondition.setYm4(list.get(3));
		}
		if (list.size() >= 5) {
			apprListSearchCondition.setYm5(list.get(4));
		}
		if (list.size() >= 6) {
			apprListSearchCondition.setYm6(list.get(5));
		}
		if (list.size() >= 7) {
			apprListSearchCondition.setYm7(list.get(6));
		}
		if (list.size() >= 8) {
			apprListSearchCondition.setYm8(list.get(7));
		}
		if (list.size() >= 9) {
			apprListSearchCondition.setYm9(list.get(8));
		}
		if (list.size() >= 10) {
			apprListSearchCondition.setYm10(list.get(9));
		}
		if (list.size() >= 11) {
			apprListSearchCondition.setYm11(list.get(10));
		}
		if (list.size() >= 12) {
			apprListSearchCondition.setYm12(list.get(11));
		}

		apprListSearchCondition.setYmSize(list.size());

		apprListSearchCondition.setSearchStartDate(startYear + "-" + apprListSearchCondition.getSearchStartMonth()
				+ "-01");
		if ((endMonth + 1) < 10) {
			apprListSearchCondition.setSearchEndDate(endYear + "-0" + (endMonth + 1) + "-01");
		} else {
			if ((endMonth + 1) < 13) {
				apprListSearchCondition.setSearchEndDate(endYear + "-" + (endMonth + 1) + "-01");
			} else {
				apprListSearchCondition.setSearchEndDate((endYear + 1) + "-01-01");
			}
		}
	}

	private void setTitleMap(ApprListSearchCondition apprListSearchCondition, LinkedHashMap<String, String> titleMap) {

		if (!StringUtil.isEmpty(apprListSearchCondition.getYm1())) {
			titleMap.put("cnt1", apprListSearchCondition.getYm1());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm2())) {
			titleMap.put("cnt2", apprListSearchCondition.getYm2());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm3())) {
			titleMap.put("cnt3", apprListSearchCondition.getYm3());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm4())) {
			titleMap.put("cnt4", apprListSearchCondition.getYm4());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm5())) {
			titleMap.put("cnt5", apprListSearchCondition.getYm5());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm6())) {
			titleMap.put("cnt6", apprListSearchCondition.getYm6());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm7())) {
			titleMap.put("cnt7", apprListSearchCondition.getYm7());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm8())) {
			titleMap.put("cnt8", apprListSearchCondition.getYm8());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm9())) {
			titleMap.put("cnt9", apprListSearchCondition.getYm9());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm10())) {
			titleMap.put("cnt10", apprListSearchCondition.getYm10());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm11())) {
			titleMap.put("cnt11", apprListSearchCondition.getYm11());
		}
		if (!StringUtil.isEmpty(apprListSearchCondition.getYm12())) {
			titleMap.put("cnt12", apprListSearchCondition.getYm12());
		}

	}
}
