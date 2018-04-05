/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategoryPK;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapListService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.support.tagging.service.TagService;


/**
 * Knowledge Map KnowledgeList controller
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListController.java 15690 2011-06-28 06:14:45Z
 *          jins02 $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgehub/category")
public class KnowledgeMapListController extends CustomTreeController {

	@Autowired
	private KnowledgeMapListService knowledgeMapListService;

	@Autowired
	private TagService tagService;

	/**
	 * [Ajax] 카테고리별 Knowledge List
	 * 
	 * @param categoryId - 카테고리ID
	 * @param categoryName - 카테고리명
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgeListViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView knowledgeListViewAjax(@RequestParam("categoryId") String categoryId,
			@RequestParam("categoryName") String categoryName, SessionStatus status) {
		
		// Category
		KnowledgeMapCategoryPK pk = new KnowledgeMapCategoryPK();
		pk.setCategoryId(categoryId);
		KnowledgeMapCategory knowledgeMapCategory = categoryService.read(pk);
		
		// Category LIST
		List<KnowledgeMapCategory> categoryList = categoryService.listByCategoryParentId(categoryId);

		// 리스트 정보
		KnowledgeMapBlockPageCondition pageCondition = new KnowledgeMapBlockPageCondition();
		int countKnowledgeItem = knowledgeMapListService.countByCategoryId(categoryId);
		pageCondition.setTotalCount(countKnowledgeItem);
		pageCondition.setCategoryId(categoryId);
		List<KnowledgeMapList> knowledgeMapList = listKnowledgePage(pageCondition);

		// view 연결
		ModelAndView mav = new ModelAndView("collpack/knowledgehub/category/knowledgeList");

		mav.addObject("categoryId", categoryId);
		mav.addObject("categoryName", knowledgeMapCategory.getCategoryName());
		mav.addObject("categoryList", categoryList);
		mav.addObject("pageCondition", pageCondition);
		mav.addObject("knowledgeList", knowledgeMapList);

		return mav;
	}

	/**
	 * Knowledge 페이징
	 * 
	 * @param pageCondition
	 * @return List<KnowledgeList>
	 */
	private List<KnowledgeMapList> listKnowledgePage(KnowledgeMapBlockPageCondition pageCondition) {
		pageCondition.calculate();

		List<KnowledgeMapList> knowledgeMapList = knowledgeMapListService.listByCategoryIdPage(pageCondition);

		// Locale 설정
		if (!isSameLocale()) {
			for (KnowledgeMapList item : knowledgeMapList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		// 요약보기 일때만 Tag 정보를 가져온다
		if (1 == pageCondition.getViewType()) {
			for (KnowledgeMapList item : knowledgeMapList) {
				item.setTagList(tagService.listTagByItemId(item.getItemId(), item.getItemType()));
			}
		}

		return knowledgeMapList;
	}

	/**
	 * [Ajax] Knowledge 페이징
	 * 
	 * @param pageCondition - 페이징 조건
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgePage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView knowledgePage(KnowledgeMapBlockPageCondition pageCondition, BindingResult result,
			SessionStatus status) {

		try {
			List<KnowledgeMapList> knowledgeMapList = listKnowledgePage(pageCondition);

			ModelAndView mav = new ModelAndView("collpack/knowledgehub/common/knowledgePageList");

			mav.addObject("pageCondition", pageCondition);
			mav.addObject("knowledgeList", knowledgeMapList);

			status.setComplete();

			return mav;
		} catch (Exception ex) {
			throw new IKEP4AjaxException("knowledgePage", ex);
		}
	}

}
