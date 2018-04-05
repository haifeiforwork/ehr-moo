/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.expertnetwork.constant.ExpertNetworkConstant;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellow;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkInterest;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopular;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkFellowService;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkInterestService;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPopularService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Expert Network Main controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkMainController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/expertnetwork")
public class ExpertNetworkMainController extends CustomTreeController {

	@Autowired
	private TagService tagService;

	@Autowired
	private ExpertNetworkPopularService popularService;

	@Autowired
	private ExpertNetworkInterestService interestService;

	@Autowired
	private ExpertNetworkFellowService fellowService;

	/**
	 * [Ajax]
	 * 카테고리 List 반환 (자식 Category)
	 * @param categoryParentId - 카테고리ID
	 * @return List<TreeNode> - 자식카테고리
	 */
	@RequestMapping(value = "/listChildMenuCategorys")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildMenuCategorys(@RequestParam("categoryParentId") String categoryParentId) {

		List<ExpertNetworkCategory> categoryList = null;
		List<TreeNode> treeNodeList = null;

    	try {
    		categoryList = categoryService.listAndChildCountByCategoryParentId(categoryParentId);
    		treeNodeList = listMenuCategory2TreeNodes(categoryList);
        	return treeNodeList;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("listChildMenuCategorys", ex);
    	}
	}


	/**
	 * Expert Network 좌측메뉴 View <br/>
	 * Expert Network 메인 URL (iframe 적용)
	 * @param status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/expertNetworkMenuView")
	public ModelAndView expertNetworkMenuView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String userId = user.getUserId();

		// 관리자 권한
		boolean expertAdmin = isAdmin(userId);

		// 좌측메뉴에서 사용하는 Tree 정보
		String menuRootCategory = getMenuLevelOneCategorysJSON(user);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/expertnetwork/expertNetworkMenuView");

		mav.addObject("menuRootCategory", menuRootCategory);
		mav.addObject("expertAdmin", expertAdmin);

		return mav;
	}

	/**
	 * 화면 공통처리 부분 <br/>
	 *  1. 상단 인기태그
	 * @param mav
	 * @param portalId
	 */
	private void commonViewSetting(ModelAndView mav, String portalId) {
		// 인기태그
		Tag popularTag = new Tag();
		popularTag.setPortalId(portalId);
		popularTag.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
		popularTag.setEndRowIndex(ExpertNetworkConstant.SEARCH_BAR_TAG_COUNT);
		List<Tag> tagListPopular = tagService.listTagByItemType(popularTag);

		mav.addObject("tagListPopular", tagListPopular);
	}


	/**
	 * Expert Network 메인 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/expertNetworkMainView")
	public ModelAndView expertNetworkMainView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String userId = user.getUserId();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean expertAdmin = isAdmin(userId);

		// 좌측메뉴에서 사용하는 Tree 정보
		String menuRootCategory = getMenuLevelOneCategorysJSON(user);

		// Popular List
		List<ExpertNetworkPopular> popularList = popularService.listByPortalId(portalId);
		// Locale 설정
		if (!isSameLocale()) {
			// Popular List
			for (ExpertNetworkPopular item : popularList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		// 동료전문가 페이징처리
		ExpertNetworkPageCondition interestPageCondition = new ExpertNetworkPageCondition();
		interestPageCondition.setUserId(userId);
		interestPageCondition.setCountOfPage(ExpertNetworkConstant.MAIN_COUNF_OF_PAGE);
		interestPageCondition.setTotalCount(interestService.countByUserId(userId));
		List<ExpertNetworkInterest> interestList = listInterestPage(interestPageCondition);

		// 관심분야전문가 페이징처리
		ExpertNetworkPageCondition fellowPageCondition = new ExpertNetworkPageCondition();
		fellowPageCondition.setUserId(userId);
		fellowPageCondition.setCountOfPage(ExpertNetworkConstant.MAIN_COUNF_OF_PAGE);
		fellowPageCondition.setTotalCount(fellowService.countByUserId(userId));
		List<ExpertNetworkFellow> fellowList = listFellowPage(fellowPageCondition);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/expertnetwork/expertNetworkMainView");

		commonViewSetting(mav, portalId);

		mav.addObject("menuRootCategory", menuRootCategory);
		mav.addObject("expertAdmin", expertAdmin);
		mav.addObject("popularList", popularList);
		mav.addObject("interestPageCondition", interestPageCondition);
		mav.addObject("fellowPageCondition", fellowPageCondition);
		mav.addObject("interestList", interestList);
		mav.addObject("fellowList", fellowList);

		return mav;
	}

	/**
	 * 동료전문가 페이징 조회
	 * @param pageCondition
	 * @return List<ExpertInterest>
	 */
	private List<ExpertNetworkInterest> listInterestPage(ExpertNetworkPageCondition pageCondition) {
		pageCondition.calculate();

		List<ExpertNetworkInterest> interestList = interestService.listByUserIdPage(pageCondition);

		for (ExpertNetworkInterest item : interestList) {
			item.setTagList(tagService.listTagByItemId(item.getExpertUserId(), TagConstants.ITEM_TYPE_PROFILE_PRO));
		}

		// Locale 설정
		if (!isSameLocale()) {
			for (ExpertNetworkInterest item : interestList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}
		
		return interestList;
	}

	/**
	 * [Ajax]
	 * 동료전문가 페이징 조회
	 * @param pageCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/interestPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView interestPage(ExpertNetworkPageCondition pageCondition, BindingResult result, SessionStatus status) {
		try {

			List<ExpertNetworkInterest> interestList = listInterestPage(pageCondition);

			// view 연결
			ModelAndView mav = new ModelAndView("/collpack/expertnetwork/interestPage");

			mav.addObject("interestList", interestList);

			status.setComplete();

			return mav;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("interestPageAjax", ex);
    	}
	}

	/**
	 * 관심분야 전문가 페이징 조회
	 * @param pageCondition
	 * @return
	 */
	private List<ExpertNetworkFellow> listFellowPage(ExpertNetworkPageCondition pageCondition) {
		pageCondition.calculate();

		List<ExpertNetworkFellow> fellowList = fellowService.listByUserIdPage(pageCondition);

		for (ExpertNetworkFellow item : fellowList) {
			item.setTagList(tagService.listTagByItemId(item.getExpertUserId(), TagConstants.ITEM_TYPE_PROFILE_PRO));
		}

		// Locale 설정
		if (!isSameLocale()) {
			for (ExpertNetworkFellow item : fellowList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		return fellowList;
	}

	/**
	 * [Ajax]
	 * 관심분야 전문가 페이징 조회
	 * @param pageCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/fellowPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView fellowPage(ExpertNetworkPageCondition pageCondition, BindingResult result, SessionStatus status) {
		try {

			List<ExpertNetworkFellow> fellowList = listFellowPage(pageCondition);

			// view 연결
			ModelAndView mav = new ModelAndView("/collpack/expertnetwork/fellowPage");

			mav.addObject("fellowList", fellowList);

			status.setComplete();

			return mav;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("fellowPageAjax", ex);
    	}
	}

}
