/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.intelligentsearch.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.servicepack.intelligentsearch.web.model.IntelligentConst;
import com.lgcns.ikep4.servicepack.intelligentsearch.web.model.MagicNumUtils;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.service.HistoryService;
import com.lgcns.ikep4.support.searchpreprocessor.service.SpSnRelationService;
import com.lgcns.ikep4.support.searchpreprocessor.util.Criteria;
import com.lgcns.ikep4.support.searchpreprocessor.util.DateUtil;
import com.lgcns.ikep4.support.searchpreprocessor.util.HistoryCons;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageUtil;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;

/**
 * 
 * IntelligentSearch
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: IntelligentSearchController.java 16464 2011-08-30 04:48:07Z
 *          giljae $
 */

@Controller
@RequestMapping(value = "/servicepack/intelligentsearch")
public class IntelligentSearchController extends ISBaseController {

	@Autowired
	HistoryService historyService;

	@Autowired
	SpSnRelationService spSnRelationService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	@Autowired
	private AssessmentManagementUserPviService assessmentManagementUserPviService;

	@Autowired
	private AssessmentManagementGroupPviService assessmentManagementGroupPviService;

	@Autowired
	private GroupService groupService;

	/**
	 * IntelligentSearch 초기화면
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index")
	public ModelAndView search() {
		String startIndex = "0";
		String next = "10";

		User user = getUser();
		ModelAndView mav = new ModelAndView(
				"/servicepack/intelligentsearch/index");

		// List<Tag> popularTagList =
		// getResultTag(HistoryCons.SEARCH_PREPROCESSOR_OPTION_DAY, startIndex,
		// next, HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG, user); //인기테그
		// List<Tag> recommendTagList =
		// getResultTag(HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH,
		// startIndex, "20", HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG,
		// user);//추천태그
		List<History> historyList = getResults(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH_PRE_3, startIndex,
				next, HistoryCons.SEARCH_PREPROCESSOR_HISTORY, user);// 내검색이력
		// List<History> colleagueList =
		// getResults(HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK, startIndex,
		// next, HistoryCons.SEARCH_PREPROCESSOR_COLLEAGUE,user);//동료검색어
		List<History> recommendList = getResults(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND, user);// 추천검색어
		List<History> popularList = getResults(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_POPULAR, user);// 인기검색어
		// List<History> relatedList = new ArrayList<History>();// 연관검색어
		// List<Tag> myTagList = getMyListTag(user);

		// PVI
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", user.getUserId());
		param.put("localeCode", user.getLocaleCode());

		Group result = groupService.selectUserRootGroup(param);

		int compayPvi = assessmentManagementGroupPviService
				.getCompanyPvi(result.getRootGroupId());
		mav.addObject("compayPvi", compayPvi);

		AssessmentManagementUserPvi assessment = assessmentManagementUserPviService
				.getByUserId(user.getUserId());
		mav.addObject("assessment", assessment);
		// power user
		List<AssessmentManagementUserPvi> topFiveRankList = assessmentManagementUserPviService
				.listPowerUsers(user.getPortalId(), MagicNumUtils.ROW_TOP_FIVE);

		// mav.addObject("popularTagList", popularTagList);
		// mav.addObject("recommendTagList", recommendTagList);
		mav.addObject("historyList", historyList);
		// mav.addObject("colleagueList", colleagueList);
		mav.addObject("recommendList", recommendList);
		mav.addObject("popularList", popularList);
		// mav.addObject("relatedList", relatedList);
		// mav.addObject("myTagList", myTagList);

		mav.addObject("topFiveRankList", topFiveRankList);

		return mav;
	}

	/**
	 * IntelligentSearch main
	 * 
	 * @param searchKeyword
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchResult(
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			HttpServletRequest request) {
		String startIndex = "0";
		String next = "10";

		User user = getUser();
		History history = new History();

		if (StringUtils.hasText(searchKeyword)) {
			HttpSession session = request.getSession();
			history.setPrefixSearchKeywordId((String) session
					.getAttribute("prefixSearchKeywordId"));

			history.setPortalId(user.getPortalId());
			history.setSearchKeyword(searchKeyword);
			history.setRegisterId(user.getUserId());
			history.setRegisterName(user.getUserName());
			history.setUpdaterId(user.getUserId());
			history.setUpdaterName(user.getUserName());
			historyService.create(history);

			// 새로운 값으로 변경한다.

			session.setAttribute("prefixSearchKeywordId",
					history.getSearchKeywordId());

		}

		ModelAndView mav = new ModelAndView(
				"/servicepack/intelligentsearch/search");

		// List<Tag> popularTagList =
		// getResultTag(HistoryCons.SEARCH_PREPROCESSOR_OPTION_DAY, startIndex,
		// next, HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG, user); //인기테그
		// List<Tag> recommendTagList =
		// getResultTag(HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH,
		// startIndex, "20", HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG,
		// user);//추천태그
		List<History> historyList = getResults(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH_PRE_3, startIndex,
				next, HistoryCons.SEARCH_PREPROCESSOR_HISTORY, user);// 내검색이력
		// List<History> colleagueList =
		// getResults(HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK, startIndex,
		// next, HistoryCons.SEARCH_PREPROCESSOR_COLLEAGUE,user);//동료검색어
		List<History> recommendList = getResults(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND, user);// 추천검색어
		List<History> popularList = getResults(
				HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_POPULAR, user);// 인기검색어

		SearchUtil searchUtil = new SearchUtil();
		Criteria criteria = searchUtil.createCriteria();

		if (StringUtils.hasText(searchKeyword)) {
			criteria.addCriterion("h.SEARCH_KEYWORD like", searchKeyword,
					"searchKeyword");
			criteria.addCriterion("h.portal_id=", user.getPortalId(),
					"portalId");
		}

		// List<History> relatedList =
		// historyService.getRelatedList(searchUtil);
		// 연관검색어
		// List<Tag> myTagList = getMyListTag(user);
		List<User> proTagList = getProListTag(user, searchKeyword);

		// PVI

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", user.getUserId());
		param.put("localeCode", user.getLocaleCode());

		Group result = groupService.selectUserRootGroup(param);

		int compayPvi = assessmentManagementGroupPviService
				.getCompanyPvi(result.getRootGroupId());
		mav.addObject("compayPvi", compayPvi);

		AssessmentManagementUserPvi assessment = assessmentManagementUserPviService
				.getByUserId(user.getUserId());
		mav.addObject("assessment", assessment);
		// power user
		List<AssessmentManagementUserPvi> topFiveRankList = assessmentManagementUserPviService
				.listPowerUsers(user.getPortalId(), MagicNumUtils.ROW_TOP_FIVE);

		// mav.addObject("popularTagList", popularTagList);
		// mav.addObject("recommendTagList", recommendTagList);
		mav.addObject("historyList", historyList);
		// mav.addObject("colleagueList", colleagueList);
		mav.addObject("recommendList", recommendList);
		mav.addObject("popularList", popularList);
		// mav.addObject("relatedList", relatedList);
		// mav.addObject("myTagList", myTagList);
		mav.addObject("proTagList", proTagList);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("topFiveRankList", topFiveRankList);

		return mav;
	}

	/**
	 * 인기검색어 ajax콜
	 * 
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @return
	 */
	@RequestMapping(value = "/ajax/popular")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<History> searchPopular(
			@RequestParam(value = "searchOption", required = false) String searchOption,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "next", required = false) String next) {

		User user = getUser();
		List<History> historyList = new ArrayList<History>();
		historyList = getResults(searchOption, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_POPULAR, user);// 인기검색어

		return historyList;
	}

	/**
	 * 인기테그 ajax콜
	 * 
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @return
	 */
	@RequestMapping(value = "/ajax/populartag")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Tag> searchPopularTag(
			@RequestParam(value = "searchOption", required = false) String searchOption,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "next", required = false) String next) {

		User user = getUser();
		List<Tag> historyList = new ArrayList<Tag>();
		historyList = getResultTag(searchOption, startIndex, next,
				HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG, user);

		return historyList;
	}

	/**
	 * 나의태그
	 * 
	 * @param user
	 * @return
	 */
	private List<Tag> getMyListTag(User user) {
		Tag tagSearch = new Tag();
		tagSearch.setPortalId(user.getPortalId()); // 포탈 ID
		// tagSearch.setTagItemType(TagConstants.ITEM_TYPE_FORUM); //item type
		tagSearch.setRegisterId(user.getUserId()); // 검색할 사용자 id
		tagSearch.setStartRowIndex(0); // 게시물 가져올 시작 숫자
		tagSearch.setEndRowIndex(MagicNumUtils.NEXT_PAGE); // 게시물 끝숫자

		List<Tag> list = tagService.list(tagSearch);

		return list;
	}

	/**
	 * 검색어에 해당하는 프로태그
	 * 
	 * @param user
	 * @param searchKeyword
	 * @return
	 */
	private List<User> getProListTag(User user, String searchKeyword) {
		List<User> userList = new ArrayList<User>();

		// 검색어가 있을 경우에만 가져온다.
		if (StringUtils.hasText(searchKeyword)) {
			Tag tagSearch = new Tag();
			tagSearch.setPortalId(user.getPortalId()); // 포탈 ID
			tagSearch.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO); // item
																			// type
			tagSearch.setTagName(searchKeyword); // 태그 이름
			tagSearch.setStartRowIndex(0); // 게시물 가져올 시작 숫자
			tagSearch.setEndRowIndex(IntelligentConst.RELATION_LIMIT_COUNT
					+ MagicNumUtils.ROW_PAGE); // 게시물 끝숫자

			List<Tag> proList = tagService.listTagSearch(tagSearch);

			for (Tag tag : proList) {
				User proUser = userService.read(tag.getTagItemId());
				userList.add(proUser);
			}
		}
		return userList;
	}

	/**
	 * 일반검색어
	 * 
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @param process
	 * @param user
	 * @return
	 */
	private List<History> getResults(String searchOption, String startIndex,
			String next, String process, User user) {
		List<History> result = new ArrayList<History>();

		try {
			PageUtil pageUtil = new PageUtil(startIndex, next);
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setStartIndex(pageUtil.getStartIndex());
			searchUtil.setEndIndex(pageUtil.getStartIndex()
					+ pageUtil.getNext());
			searchUtil.setUser(user);

			searchUtil.setCategory(process);

			Criteria criteria = searchUtil.createCriteria();
			if (!process.equals(HistoryCons.SEARCH_PREPROCESSOR_POPULAR_REAL)) {
				if (searchOption
						.equals(HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK)) {
					criteria.addCriterionForJDBCDate("h.REGIST_DATE >=",
							DateUtil.prevWeek(1), "registDate");
				} else if (searchOption
						.equals(HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH)) {
					criteria.addCriterionForJDBCDate("h.REGIST_DATE >=",
							DateUtil.prevMonth(1), "registDate");
				} else if (searchOption
						.equals(HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH_PRE_3)) {
					criteria.addCriterionForJDBCDate("h.REGIST_DATE >=",
							DateUtil.prevMonth(MagicNumUtils.MONTH_3),
							"registDate");
				} else {
					criteria.addCriterionForJDBCDate("h.REGIST_DATE >=",
							DateUtil.getToday(), "registDate");
				}
			}

			criteria.addCriterion("h.portal_id=", user.getPortalId(),
					"portalId");

			if (process.equals(HistoryCons.SEARCH_PREPROCESSOR_HISTORY)) {
				criteria.addCriterion("h.REGISTER_ID =", user.getUserId(),
						"registerId");
				searchUtil.setOrderByClause("h.REGIST_DATE desc");
			} else {
				if (process.equals(HistoryCons.SEARCH_PREPROCESSOR_COLLEAGUE)
						|| process
								.equals(HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND)) {
					SearchUtil searchUtil2 = new SearchUtil();
					searchUtil2.setUser(user);
					searchUtil2
							.setEndIndex(IntelligentConst.RELATION_LIMIT_COUNT);
					List<User> relationUserList = spSnRelationService
							.getSnRelationList(searchUtil2);

					List<String> relationTemp = new ArrayList<String>();
					// relationTemp.add(user.getUserId());

					for (User userInfo : relationUserList) {
						relationTemp.add(userInfo.getUserId());
					}

					searchUtil.setRelationUserList(relationTemp);
					criteria.addCriterion("h.REGISTER_ID in", relationTemp,
							"registerId");
				}

				searchUtil.setOrderByClause("FREQUENCY_COUNT desc");
			}

			result = historyService.getMainList(searchUtil);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return result;
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
			searchUtil.setEndIndex(IntelligentConst.RELATION_LIMIT_COUNT);
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

	@RequestMapping(value = "/deleteHistory.do")
	public @ResponseBody
	String deleteHistory(String searchHistoryId) {

		try {
			historyService.delete(searchHistoryId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}

	@RequestMapping(value = "/deleteHistoryAll.do")
	public @ResponseBody
	String deleteHistoryAll() {

		try {

			User user = getUser();

			historyService.removeAllByRegister(user.getUserId());

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}

	@RequestMapping(value = "/expertTagList.do")
	public ModelAndView expertTagList(String userId) {

		ModelAndView mav = new ModelAndView(
				"/servicepack/intelligentsearch/expertTagList");

		try {

			List<Tag> expertTagList = tagService.listTagByItemId(userId,
					TagConstants.ITEM_TYPE_PROFILE_PRO);
			mav.addObject("expertTagList", expertTagList);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

}
