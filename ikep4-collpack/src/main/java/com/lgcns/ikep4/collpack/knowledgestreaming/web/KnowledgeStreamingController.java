/* 
\ * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService;
import com.lgcns.ikep4.collpack.knowledgestreaming.model.KnowledgeStreamingSearchCondition;
import com.lgcns.ikep4.collpack.knowledgestreaming.model.KnowledgeUse;
import com.lgcns.ikep4.collpack.knowledgestreaming.service.KnowledgeStreamingService;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelItemService;
import com.lgcns.ikep4.support.searchpreprocessor.service.SpSnRelationService;
import com.lgcns.ikep4.support.searchpreprocessor.util.HistoryCons;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageUtil;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Knowledge Monitor KnowledgeMonitorRankingInfo controller
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorRankingInfoController.java 16478 2011-09-01
 *          01:50:46Z handul32 $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgestreaming")
public class KnowledgeStreamingController extends BaseController {

	@Autowired
	private KnowledgeStreamingService knowledgeStreamingService;

	@Autowired
	private KnowledgeMonitorCviPointService knowledgeMonitorCviPointService;

	@Autowired
	private QnaService qnaService;

	@Autowired
	private AssessmentManagementUserPviService assessmentManagementUserPviService;

	@Autowired
	private TagService tagService;

	@Autowired
	SpSnRelationService spSnRelationService;

	@Autowired
	private KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	@Autowired
	private KnowledgeMonitorStatisticsService knowledgeMonitorStatisticsService;

	@Autowired
	private KnowledgeMapPopularListService knowledgeMapPopularListService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private ChannelItemService channelItemService;

	@RequestMapping(value = "/knowledgeStreamingMain.do")
	public ModelAndView knowledgeStreamingMain() {

		ModelAndView mav = new ModelAndView(
				"collpack/knowledgestreaming/knowledgeStreamingMain");

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		// 오늘 등록된 지식 건수
		KnowledgeRankBlockPageCondition knowledgeRankBlockPageCondition = new KnowledgeRankBlockPageCondition();
		knowledgeRankBlockPageCondition.setPortalId(portal.getPortalId());
		knowledgeRankBlockPageCondition.setFromDate(DateUtil.getToday(""));
		knowledgeRankBlockPageCondition.setToDate(DateUtil.getToday(""));
		int toDayKm = knowledgeMonitorCviPointService
				.countByPortalIdModuleCode(knowledgeRankBlockPageCondition);
		mav.addObject("toDayKm", toDayKm);

		// 오늘 답변된 Qna 건수
		Qna qnaSearch = new Qna();
		qnaSearch.setPortalId(portal.getPortalId());
		qnaSearch.setNewDate("1");
		qnaSearch.setQnaType(1);
		int toDayQa = qnaService.listCount(qnaSearch);
		mav.addObject("toDayQa", toDayQa);

		// 사용자 Pvi 점수
		AssessmentManagementUserPvi userPvi = assessmentManagementUserPviService
				.getWithSymbolByUserId(user.getUserId());
		mav.addObject("userPvi", userPvi);

		String startIndex = "0";
		String next = "10";

		// 추천태그
		List<Tag> recommendTagList = getResultTag(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG, user);// 추천태그
		mav.addObject("recommendTagList", recommendTagList);

		// 추천지식
		Map<String, Object> itemMap = tagService.listItemByTagList(
				recommendTagList, 1, 10);
		int count = (Integer) itemMap.get("count");
		List<Tag> recommendTagItemList = (List<Tag>) itemMap.get("list");
		mav.addObject("recommendTagItemList", recommendTagItemList);

		// 인기태그
		/*
		 * List<Tag> popularTagList = getResultTag(
		 * HistoryCons.SEARCH_PREPROCESSOR_OPTION_DAY, startIndex, next,
		 * HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG, user); // 인기테그
		 * mav.addObject("popularTagList", popularTagList);
		 */

		// 전문태그
		List<Tag> expertTagList = tagService.listTagByItemId(user.getUserId(),
				TagConstants.ITEM_TYPE_PROFILE_PRO);
		mav.addObject("expertTagList", expertTagList);

		// 관심테크
		List<Tag> intrestTagList = tagService.listTagByItemId(user.getUserId(),
				TagConstants.ITEM_TYPE_PROFILE_ATTENTION);
		mav.addObject("intrestTagList", intrestTagList);

		// 지식 축적 정보 차트 리스트
		KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition = new KnowledgeAccumulationSearchCondition();
		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService
				.listByPortalId(portal.getPortalId());
		settingSearchCondition(knowledgeAccumulationSearchCondition,
				knowledgeMonitorModuleList);
		knowledgeAccumulationSearchCondition.setPortalId(portal.getPortalId());
		List<KnowledgeMonitorAccumulationChart> knowledgeMonitorAccumulationChartList = knowledgeMonitorStatisticsService
				.listChartBySearchCondition(knowledgeAccumulationSearchCondition);
		mav.addObject("knowledgeMonitorAccumulationChartList",
				knowledgeMonitorAccumulationChartList);

		// 지식 활용 수준 차트 리스트
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("portalId", portal.getPortalId());
		List<KnowledgeUse> knowledgeUseList = knowledgeStreamingService
				.selectWeekly(searchMap);
		mav.addObject("knowledgeUseList", knowledgeUseList);

		KnowledgeStreamingSearchCondition searchCondition = new KnowledgeStreamingSearchCondition();
		searchCondition.setPageIndex(1);
		mav.addObject("searchCondition", searchCondition);

		return mav;
	}

	/**
	 * 테그검색로직
	 * 
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @param process
	 * @param user
	 * @return
	 */
	private List<Tag> getResultTag(String searchOption, String startIndex,
			String next, String process, User user) {
		List<Tag> result = new ArrayList<Tag>();

		PageUtil pageUtil = new PageUtil(startIndex, next);

		Tag tag = new Tag();
		tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY); // 원하는 정렬순서로 바꿀수 있음.
															// TagConstants에
															// 정의되어 있음.

		tag.setPortalId(user.getPortalId());

		if (process.equals(HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG)) {
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setUser(user);
			searchUtil.setEndIndex(10);
			// 태그 가져오기
			List<User> relationUserList = spSnRelationService
					.getSnRelationList(searchUtil);

			List<String> relationTemp = new ArrayList<String>();
			relationTemp.add(user.getUserId());

			for (User userInfo : relationUserList) {
				relationTemp.add(userInfo.getUserId());
			}
			tag.setUserIdList(relationTemp); // 사용자 리스트 (List 형식 이여야 함.)
		}

		tag.setEndRowIndex(pageUtil.getStartIndex() + pageUtil.getNext());
		tag.setStartRowIndex(pageUtil.getStartIndex());

		if (searchOption.equals(HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK)) {
			tag.setLimitDate("7"); // 허용 날짜
		} else if (searchOption
				.equals(HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH)) {
			tag.setLimitDate("30"); // 허용 날짜
		} else {
			tag.setLimitDate("1"); // 허용 날짜
		}

		result = tagService.list(tag);

		return result;

	}

	/**
	 * 인기태그 클라우드
	 * 
	 * @return
	 */
	@RequestMapping("/tag.do")
	public ModelAndView tag() {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Tag tag = new Tag();

		// tag.setTagItemType(TagConstants.ITEM_TYPE_WORKSPACE);
		// 정렬순 TagConstants에 정의되어 있음. - 넣지않으면 인기순으로 가져옴.
		tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);

		tag.setPortalId(portal.getPortalId()); // portalID
		tag.setEndRowIndex(20); // 태그 가져올 개수
		tag.setIsNotSubType(TagConstants.IS_NOT_SUB_TYPE);
		List<Tag> tagList = tagService.list(tag);

		// 아래 소스대로 그대로 사용하면됨.
		return new ModelAndView("support/tagging/tagXml")
				.addObject("tagList", tagList) // 태그
												// 리스트
				.addObject("itemType", tag.getTagItemType()) // item type
				.addObject("subItemType", tag.getTagItemSubType()); // sub item
																	// type

	}

	/**
	 * 검색조건 세팅
	 * 
	 * @param knowledgeAccumulationSearchCondition
	 *            - 조회조건
	 * @param knowledgeMonitorModuleList
	 *            - 사용가능한 모듈 리스트
	 */
	private void settingSearchCondition(
			KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition,
			List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		// 메뉴에서 온 처음 접근
		if (!knowledgeAccumulationSearchCondition.isNotFirstAccess()) {
			knowledgeAccumulationSearchCondition.setNotFirstAccess(true);

			settingSearchConditionFirstAccess(
					knowledgeAccumulationSearchCondition,
					knowledgeMonitorModuleList);
		} else {
			settingSearchConditionNotFirstAccess(
					knowledgeAccumulationSearchCondition,
					knowledgeMonitorModuleList);
		}
	}

	/**
	 * 검색조건 세팅 (화면 로딩시)
	 * 
	 * @param knowledgeAccumulationSearchCondition
	 *            - 조회조건
	 * @param knowledgeMonitorModuleList
	 *            - 사용가능한 모듈 리스트
	 */
	private void settingSearchConditionFirstAccess(
			KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition,
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
			knowledgeAccumulationSearchCondition
					.setModuleCodes(sb.substring(1));
		}
		if (1 > sbExclude.length()) {
			knowledgeAccumulationSearchCondition.setExcludeModuleCodes("");
		} else {
			knowledgeAccumulationSearchCondition
					.setExcludeModuleCodes(sbExclude.substring(1));
		}
	}

	/**
	 * 검색조건 세팅
	 * 
	 * @param knowledgeAccumulationSearchCondition
	 *            - 조회조건
	 * @param knowledgeMonitorModuleList
	 *            - 사용가능한 모듈 리스트
	 */
	private void settingSearchConditionNotFirstAccess(
			KnowledgeAccumulationSearchCondition knowledgeAccumulationSearchCondition,
			List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		// 폼에서 체크한 모듈 선택
		String[] moduleCodes = StringUtil.nvl(
				knowledgeAccumulationSearchCondition.getModuleCodes(), "")
				.split(CommonConstant.COMMA_SEPARATER);
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
			knowledgeAccumulationSearchCondition.setExcludeModuleCodes(sb
					.substring(1));
		}
	}

	/**
	 * 엑티비티스트림 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/subListForKnowledgeStreaming.do")
	public ModelAndView subListForKnowledgeStreaming(
			KnowledgeStreamingSearchCondition searchCondition) {

		ModelAndView mav = null;

		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			// searchCondition
			// .setStartRowIndex((searchCondition.getPageIndex() - 1) * 10 + 1);
			// searchCondition.setEndRowIndex(searchCondition.getPageIndex() *
			// 10);

			int recordCount = 0;
			int currentCount = 0;

			if (searchCondition.getModuleFieldName().equals("Trend")) {

				mav = new ModelAndView(
						"/collpack/knowledgestreaming/subListForKnowledgeStreaming");

				// 인기태그
				List<Tag> tagList = getResultTag(
						HistoryCons.SEARCH_PREPROCESSOR_OPTION_DAY, "0", "10",
						HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG, user);

				Map<String, Object> itemMap = tagService.listItemByTagList(
						tagList, searchCondition.getPageIndex(), 10);

				List<Tag> itemList = (List<Tag>) itemMap.get("list");
				mav.addObject("itemList", itemList);

				recordCount = itemList.size();
				currentCount = (searchCondition.getPageIndex() - 1) * 10
						+ itemList.size();

			} else if (searchCondition.getModuleFieldName().equals("Best")) {

				mav = new ModelAndView(
						"/collpack/knowledgestreaming/subListForKnowledgeStreaming2");

				KnowledgeMapPopularPageCondition pageCondition = new KnowledgeMapPopularPageCondition();

				Date nowDate = timeZoneSupportService.convertTimeZone();
				Date monthFromDate = DateUtil.getRelativeDate(nowDate, 0, -3);
				pageCondition.setToDate(DateUtil.getFmtDateString(nowDate,
						CommonConstant.DATE_FORMAT));
				pageCondition.setFromDate(DateUtil.getFmtDateString(
						monthFromDate, CommonConstant.DATE_FORMAT));

				pageCondition.setPortalId(portal.getPortalId());
				pageCondition.setPage(searchCondition.getPageIndex());
				pageCondition
						.setStartOrder((searchCondition.getPageIndex() - 1) * 10 + 1);
				pageCondition
						.setEndOrder((searchCondition.getPageIndex()) * 10);
				pageCondition.setIsGoodDoc("Y");
				// pageCondition.calculate();

				List<KnowledgeMapList> knowledgeMapList = knowledgeMapPopularListService
						.listByPortalIdPage(pageCondition);
				mav.addObject("itemList", knowledgeMapList);

				recordCount = knowledgeMapList.size();
				currentCount = (searchCondition.getPageIndex() - 1) * 10
						+ knowledgeMapList.size();

			} else if (searchCondition.getModuleFieldName().equals("New")) {

				mav = new ModelAndView(
						"/collpack/knowledgestreaming/subListForKnowledgeStreaming2");

				KnowledgeMapPopularPageCondition pageCondition = new KnowledgeMapPopularPageCondition();

				Date nowDate = timeZoneSupportService.convertTimeZone();
				Date monthFromDate = DateUtil.getRelativeDate(nowDate, 0, -3);
				pageCondition.setToDate(DateUtil.getFmtDateString(nowDate,
						CommonConstant.DATE_FORMAT));
				pageCondition.setFromDate(DateUtil.getFmtDateString(
						monthFromDate, CommonConstant.DATE_FORMAT));

				pageCondition.setPortalId(portal.getPortalId());
				pageCondition.setPage(searchCondition.getPageIndex());
				pageCondition
						.setStartOrder((searchCondition.getPageIndex() - 1) * 10 + 1);
				pageCondition
						.setEndOrder((searchCondition.getPageIndex()) * 10);
				// pageCondition.calculate();

				List<KnowledgeMapList> knowledgeMapList = knowledgeMapPopularListService
						.listByPortalIdPage(pageCondition);
				mav.addObject("itemList", knowledgeMapList);

				recordCount = knowledgeMapList.size();
				currentCount = (searchCondition.getPageIndex() - 1) * 10
						+ knowledgeMapList.size();

			} else if (searchCondition.getModuleFieldName().equals("Expert")) {

				mav = new ModelAndView(
						"/collpack/knowledgestreaming/subListForKnowledgeStreaming");

				// 전문태그
				List<Tag> tagList = tagService.listTagByItemId(
						user.getUserId(), TagConstants.ITEM_TYPE_PROFILE_PRO);
				mav.addObject("expertTagList", tagList);

				Map<String, Object> itemMap = tagService.listItemByTagList(
						tagList, searchCondition.getPageIndex(), 10);

				List<Tag> itemList = (List<Tag>) itemMap.get("list");
				mav.addObject("itemList", itemList);

				recordCount = itemList.size();
				currentCount = (searchCondition.getPageIndex() - 1) * 10
						+ itemList.size();

			} else if (searchCondition.getModuleFieldName().equals("Interest")) {

				mav = new ModelAndView(
						"/collpack/knowledgestreaming/subListForKnowledgeStreaming");

				// 관심테크
				List<Tag> tagList = tagService.listTagByItemId(
						user.getUserId(),
						TagConstants.ITEM_TYPE_PROFILE_ATTENTION);
				mav.addObject("intrestTagList", tagList);

				Map<String, Object> itemMap = tagService.listItemByTagList(
						tagList, searchCondition.getPageIndex(), 10);

				List<Tag> itemList = (List<Tag>) itemMap.get("list");
				mav.addObject("itemList", itemList);

				recordCount = itemList.size();
				currentCount = (searchCondition.getPageIndex() - 1) * 10
						+ itemList.size();

			} else if (searchCondition.getModuleFieldName().equals("RSS")) {

				mav = new ModelAndView(
						"/collpack/knowledgestreaming/subListForKnowledgeStreaming3");

				ChannelSearchCondition rssSearchCondition = new ChannelSearchCondition();

				rssSearchCondition.setPageIndex(searchCondition.getPageIndex());
				rssSearchCondition.setOwnerId(user.getUserId());
				if (StringUtil.isEmpty(rssSearchCondition.getSortColumn())) {
					rssSearchCondition.setSortColumn("ITEM_PUBLISH_DATE");
				}
				if (StringUtil.isEmpty(rssSearchCondition.getSortType())) {
					rssSearchCondition.setSortType("DESC");
				}

				SearchResult<ChannelItem> searchResult = channelItemService
						.getAll(rssSearchCondition);
				if (searchResult.isEmptyRecord()) {
					mav.addObject("itemList", null);
					recordCount = 0;
					currentCount = 0;
				} else {
					mav.addObject("itemList", searchResult.getEntity());

					recordCount = searchResult.getEntity().size();
					currentCount = (searchCondition.getPageIndex() - 1) * 10
							+ searchResult.getEntity().size();
				}

			}

			searchCondition.setRecordCount(recordCount);
			searchCondition.setCurrentCount(currentCount);

			mav.addObject("searchCondition", searchCondition);

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}
}
