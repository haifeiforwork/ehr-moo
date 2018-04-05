/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;

/**
 * Knowledge Map KnowledgePopularList controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularListController.java 16774 2011-10-08 01:22:53Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgehub/personal")
public class KnowledgeMapPopularListController extends CustomTreeController {

	@Autowired
	private KnowledgeMapPopularListService knowledgeMapPopularListService;

	@Autowired
	private TagService tagService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	/**
	 * [Ajax]
	 * Knowledge Popular List
	 * @param pageCondition - 페이징 조회조건
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgePopularListViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView knowledgePopularListViewAjax(KnowledgeMapPopularPageCondition pageCondition, BindingResult result, SessionStatus status) {
		try {
			// Session 정보
			User user = getSessionUser();
			String portalId = user.getPortalId();

			// 조회기간 세팅
			if (0 != pageCondition.getSearchFlag()) {
				Date nowDate = timeZoneSupportService.convertTimeZone();
				Date monthFromDate = DateUtil.getRelativeDate(nowDate, 0, pageCondition.getSearchFlag());

				pageCondition.setToDate(DateUtil.getFmtDateString(nowDate, CommonConstant.DATE_FORMAT));
				pageCondition.setFromDate(DateUtil.getFmtDateString(monthFromDate, CommonConstant.DATE_FORMAT));

				pageCondition.setReInit(true);
			}

			// 타임존 설정
			Date convertFromDate = timeZoneSupportService.convertServerTimeZone(pageCondition.getFromDate() + CommonConstant.TIMEZONE_FROM_TIME_VALUE, CommonConstant.TIMEZONE_DATETIME_FORMAT);
			pageCondition.setConvertFromDate(convertFromDate);
			Date convertToDate = timeZoneSupportService.convertServerTimeZone(pageCondition.getToDate() + CommonConstant.TIMEZONE_TO_TIME_VALUE, CommonConstant.TIMEZONE_DATETIME_FORMAT);
			pageCondition.setConvertToDate(convertToDate);

			pageCondition.setPortalId(portalId);
			pageCondition.setIsGoodDoc("Y");
			
			if (pageCondition.isReInit()) {
				pageCondition.setTotalCount(knowledgeMapPopularListService.countByPortalIdPage(pageCondition));
				pageCondition.setReInit(false);
			}
			
			pageCondition.calculate();

			List<KnowledgeMapList> knowledgeMapList = knowledgeMapPopularListService.listByPortalIdPage(pageCondition);

			for (KnowledgeMapList item : knowledgeMapList) {
				item.setTagList(tagService.listTagByItemId(item.getItemId(), item.getItemType()));
			}

			// Locale 설정
			if (!isSameLocale()) {
				for (KnowledgeMapList item : knowledgeMapList) {
					item.setUserName(item.getUserEnglishName());
					item.setTeamName(item.getTeamEnglishName());
					item.setJobTitleName(item.getJobTitleEnglishName());
				}
			}

			// view 연결
			ModelAndView mav = new ModelAndView("collpack/knowledgehub/personal/knowledgePopularList");

			mav.addObject("pageCondition", pageCondition);
			mav.addObject("knowledgeList", knowledgeMapList);
			mav.addObject("isScore", true);

			return mav;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("knowledgePopularListViewAjax", ex);
    	}
	}

}
