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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.collpack.knowledgehub.constant.KnowledgeMapConstant;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendListService;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendTagService;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Knowledge Map Main controller
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapMainController.java 15690 2011-06-28 06:14:45Z
 *          jins02 $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgehub")
public class KnowledgeMapMainController extends CustomTreeController {

	@Autowired
	private TagService tagService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private KnowledgeMapPopularListService knowledgeMapPopularListService;

	@Autowired
	private KnowledgeMapRecommendListService knowledgeMapRecommendListService;

	@Autowired
	private KnowledgeMapRecommendTagService knowledgeMapRecommendTagService;

	/**
	 * Knowledge Map 메인 View
	 * 
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgeMapMainView")
	public ModelAndView knowledgeMapMainView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();
		String userId = user.getUserId();

		// 관리자 권한
		boolean knowledgeAdmin = isAdmin(userId);

		// 좌측메뉴에서 사용하는 Tree 정보
		String menuRootCategory = getMenuLevelOneCategorysJSON(user);

		// Tag Cloud
		Tag cloudTag = new Tag();
		cloudTag.setPortalId(portalId);
		cloudTag.setEndRowIndex(KnowledgeMapConstant.MAIN_TAG_CLOUD_COUNT);
		cloudTag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
		cloudTag.setTagItemType(getUsableModules());
		List<Tag> tagListCloud = tagService.listTagByItemType(cloudTag);

		// 인기지식
		KnowledgeMapPopularPageCondition pageCondition = new KnowledgeMapPopularPageCondition();
		pageCondition.setPortalId(portalId);
		pageCondition.setCountOfPage(KnowledgeMapConstant.MAIN_POPULAR_COUNF_OF_PAGE);

		Date nowDate = timeZoneSupportService.convertTimeZone();
		Date monthFromDate = DateUtil.getRelativeDate(nowDate, 0, pageCondition.getSearchFlag());

		pageCondition.setToDate(DateUtil.getFmtDateString(nowDate, CommonConstant.DATE_FORMAT));
		pageCondition.setFromDate(DateUtil.getFmtDateString(monthFromDate, CommonConstant.DATE_FORMAT));

		// 타임존 설정
		//Date convertFromDate = timeZoneSupportService.convertServerTimeZone(pageCondition.getFromDate()
		//		+ CommonConstant.TIMEZONE_FROM_TIME_VALUE, CommonConstant.TIMEZONE_DATETIME_FORMAT);
		//pageCondition.setConvertFromDate(convertFromDate);
		//Date convertToDate = timeZoneSupportService.convertServerTimeZone(pageCondition.getToDate()
		//		+ CommonConstant.TIMEZONE_TO_TIME_VALUE, CommonConstant.TIMEZONE_DATETIME_FORMAT);
		//pageCondition.setConvertToDate(convertToDate);

		// 무조건 1 ~ 4 건만 표시되니 전체개수가 필요없다.
		// pageCondition.setTotalCount(knowledgeMapPopularListService.countByPortalIdPage(pageCondition));
		// pageCondition.calculate();
		pageCondition.setEndOrder(KnowledgeMapConstant.MAIN_POPULAR_COUNF_OF_PAGE);
		pageCondition.setIsGoodDoc("Y");

		List<KnowledgeMapList> knowledgePopularList = knowledgeMapPopularListService.listByPortalIdPage(pageCondition);

		for (KnowledgeMapList item : knowledgePopularList) {
			item.setTagList(tagService.listTagByItemId(item.getItemId(), item.getItemType()));
		}

		// Locale 설정
		if (!isSameLocale()) {
			// 인기지식
			for (KnowledgeMapList item : knowledgePopularList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		// 추천지식
		Tag recommendTag = new Tag();
		recommendTag.setRegisterId(userId);
		recommendTag.setPortalId(portalId);
		recommendTag.setEndRowIndex(KnowledgeMapConstant.MAIN_RECOMMEND_COUNF_OF_PAGE);
		recommendTag.setTagItemType(getUsableModules());
		List<KnowledgeMapList> knowledgeRecommendList = knowledgeMapRecommendListService
				.listByUserIdPageSimple(recommendTag);

		// 추천태그
		// List<KnowledgeMapRecommendTag> knowledgeRecommendTagList =
		// knowledgeMapRecommendTagService.listByUserId(userId,
		// KnowledgeMapConstant.MAIN_RECOMMEND_TAG_COUNF_OF_PAGE);

		// 인기태그
		Tag popularTag = new Tag();
		popularTag.setPortalId(portalId);
		popularTag.setEndRowIndex(KnowledgeMapConstant.MAIN_POPULAR_TAG_COUNF_OF_PAGE);
		popularTag.setTagItemType(getUsableModules());
		// List<Tag> tagListPopular = tagService.listTagByItemType(popularTag);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgehub/knowledgeMapMainView");

		mav.addObject("menuRootCategory", menuRootCategory);
		mav.addObject("knowledgeAdmin", knowledgeAdmin);
		mav.addObject("tagListCloud", tagListCloud);
		mav.addObject("knowledgeList", knowledgePopularList);
		mav.addObject("isScore", true);
		mav.addObject("knowledgeRecommendList", knowledgeRecommendList);
		// mav.addObject("knowledgeRecommendTagList",
		// knowledgeRecommendTagList);
		// mav.addObject("tagListPopular", tagListPopular);
		mav.addObject("usableModules", getUsableModules());

		return mav;
	}

	/**
	 * [Ajax] 카테고리 List 반환 (자식 Category)
	 * 
	 * @param categoryParentId - 부모 카테고리ID
	 * @return
	 */
	@RequestMapping(value = "/listChildMenuCategorys")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<TreeNode> listChildMenuCategorys(@RequestParam("categoryParentId") String categoryParentId) {

		List<KnowledgeMapCategory> categoryList = null;
		List<TreeNode> treeNodeList = null;

		try {
			categoryList = categoryService.listAndChildCountByCategoryParentId(categoryParentId);
			treeNodeList = listMenuCategory2TreeNodes(categoryList);
			return treeNodeList;
		} catch (Exception ex) {
			throw new IKEP4AjaxException("listChildMenuCategorys", ex);
		}
	}

}
