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
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategoryPK;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Expert Network ExpertList controller
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListController.java 15689 2011-06-28 06:14:41Z
 *          jins02 $
 */
@Controller
@RequestMapping(value = "/collpack/expertnetwork/category")
public class ExpertNetworkListController extends CustomTreeController {

	@Autowired
	private TagService tagService;

	@Autowired
	private ExpertNetworkListService expertlistService;

	@Autowired
	private ExpertNetworkCategoryService expertNetworkCategoryService;

	/**
	 * [Ajax] 카테고리 별 전문가 목록 View
	 * 
	 * @param pageCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/expertListViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView expertListViewAjax(ExpertNetworkBlockPageCondition pageCondition, BindingResult result,
			SessionStatus status) {
		// Session 정보
		User user = getSessionUser();

		// 관리자 권한
		boolean expertAdmin = isAdmin(user.getUserId());
		
		ExpertNetworkCategoryPK pk = new ExpertNetworkCategoryPK();
		pk.setCategoryId(pageCondition.getCategoryId());
		ExpertNetworkCategory expertNetworkCategory = expertNetworkCategoryService.read(pk);
		
		// 페이징 정보 생성
		pageCondition.setExpertAdmin(expertAdmin);
		pageCondition.setReInit(true);
		pageCondition.setCategoryName(expertNetworkCategory.getCategoryName());

		List<ExpertNetworkList> expertItemList = listExpertPage(pageCondition);

		// view 연결
		ModelAndView mav = new ModelAndView("/collpack/expertnetwork/category/expertList");

		mav.addObject("pageCondition", pageCondition);
		mav.addObject("expertItemList", expertItemList);

		status.setComplete();

		return mav;
	}

	/**
	 * 카테고리 별 전문가 목록 페이징
	 * 
	 * @param pageCondition
	 * @return List<ExpertList>
	 */
	private List<ExpertNetworkList> listExpertPage(ExpertNetworkBlockPageCondition pageCondition) {
		// 데이터 변경여부 판단
		if (pageCondition.isReInit()) {
			pageCondition.setTotalCount(expertlistService.countByCategoryId(pageCondition.getCategoryId()));
			pageCondition.setReInit(false);
		}

		pageCondition.calculate();

		List<ExpertNetworkList> expertItemList = expertlistService.listByCategoryIdPage(pageCondition);

		for (ExpertNetworkList item : expertItemList) {
			item.setTagList(tagService.listTagByItemId(item.getUserId(), TagConstants.ITEM_TYPE_PROFILE_PRO));
		}

		// Locale 설정
		if (!isSameLocale()) {
			for (ExpertNetworkList item : expertItemList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		return expertItemList;
	}

	/**
	 * [Ajax] 카테고리 별 전문가 목록 페이징
	 * 
	 * @param pageCondition - 페이징 조회조건
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/expertPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView expertPage(ExpertNetworkBlockPageCondition pageCondition, BindingResult result,
			SessionStatus status) {
		try {

			List<ExpertNetworkList> expertItemList = listExpertPage(pageCondition);

			// view 연결
			ModelAndView mav = new ModelAndView("/collpack/expertnetwork/category/expertPage");

			mav.addObject("pageCondition", pageCondition);
			mav.addObject("expertItemList", expertItemList);

			status.setComplete();

			return mav;
		} catch (Exception ex) {
			throw new IKEP4AjaxException("expertPageAjax", ex);
		}
	}

	/**
	 * [Ajax] 전문가 지정
	 * 
	 * @param selectItem - 선택된 전문가(콤마구분자 형식)
	 * @param categoryId - 카테고리ID
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/expertCreate")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void expertCreate(@RequestParam("selectItem") String selectItem, @RequestParam("categoryId") String categoryId,
			SessionStatus status) {

		// Session 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		try {
			String[] selectItems = selectItem.split(CommonConstant.COMMA_SEPARATER);

			ExpertNetworkList expertNetworkList = new ExpertNetworkList();
			expertNetworkList.setCategoryId(categoryId);
			expertNetworkList.setIsAuthorized(ExpertNetworkConstant.AUTHORIZED_FROM_ADMIN); // 관리자
																							// 입력여부
																							// (
																							// 0:관리자직접입력,
																							// 1:
																							// 배치입력,
																							// 2:
																							// 관리자해제)
			expertNetworkList.setMatchingScore(ExpertNetworkConstant.MAX_MATCHING_SCORE); // 사내인증전문가
																							// 무조건
																							// 100점
			expertNetworkList.setRegisterId(user.getUserId());
			expertNetworkList.setRegisterName(user.getUserName());

			for (int i = 0; i < selectItems.length; i++) {
				expertNetworkList.setUserId(selectItems[i]);
				expertlistService.update(expertNetworkList);
			}

			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("expertCreateAjax", ex);
		}
	}

	/**
	 * [Ajax] 전문가 해제
	 * 
	 * @param selectItem - 선택된 전문가(콤마구분자 형식)
	 * @param categoryId - 카테고리ID
	 * @param status
	 */
	@RequestMapping(value = "/expertDelete")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void expertDelete(@RequestParam("selectItem") String selectItem, @RequestParam("categoryId") String categoryId,
			SessionStatus status) {

		// Session 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		try {
			String[] selectItems = selectItem.split(CommonConstant.COMMA_SEPARATER);

			ExpertNetworkList expertNetworkList = new ExpertNetworkList();
			expertNetworkList.setCategoryId(categoryId);
			expertNetworkList.setIsAuthorized(ExpertNetworkConstant.AUTHORIZED_FROM_ADMIN_DELETE); // 관리자
																									// 입력여부
																									// (
																									// 0:관리자직접입력,
																									// 1:
																									// 배치입력,
																									// 2:
																									// 관리자해제)
			expertNetworkList.setRegisterId(user.getUserId());
			expertNetworkList.setRegisterName(user.getUserName());

			for (int i = 0; i < selectItems.length; i++) {
				expertNetworkList.setUserId(selectItems[i]);
				expertlistService.updateAuthorized(expertNetworkList);
			}

			status.setComplete();
		} catch (Exception ex) {
			throw new IKEP4AjaxException("expertDeleteAjax", ex);
		}
	}

	/**
	 * [Ajax] 전문가 추가등록
	 * 
	 * @param addExpertId
	 * @param categoryId
	 * @param status
	 */
	@RequestMapping(value = "/expertAppend")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void expertAppend(@RequestParam("addExpertId") String addExpertId, @RequestParam("categoryId") String categoryId,
			SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		ExpertNetworkList expertNetworkList = new ExpertNetworkList();
		expertNetworkList.setCategoryId(categoryId);
		expertNetworkList.setIsAuthorized(ExpertNetworkConstant.AUTHORIZED_FROM_ADMIN); // 관리자
																						// 입력여부
																						// (
																						// 0:관리자직접입력,
																						// 1:
																						// 배치입력,
																						// 2:
																						// 관리자해제)
		expertNetworkList.setMatchingScore(ExpertNetworkConstant.MAX_MATCHING_SCORE); // 사내인증전문가
																						// 무조건
																						// 100점
		expertNetworkList.setRegisterId(user.getUserId());
		expertNetworkList.setRegisterName(user.getUserName());

		try {
			String[] selectUserIds = addExpertId.split(CommonConstant.COMMA_SEPARATER);

			for (int i = 0; i < selectUserIds.length; i++) {
				expertNetworkList.setUserId(selectUserIds[i]);
				expertlistService.createOrUpdateExpertList(expertNetworkList);
			}

			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("expertAppendAjax", ex);
		}
	}

}
