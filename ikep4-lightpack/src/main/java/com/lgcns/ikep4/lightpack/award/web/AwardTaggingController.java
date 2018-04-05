/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.search.RelatedAwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardTaggingService;

/**
 * 댓글 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/award/awardTagging")
public class AwardTaggingController {

	@Autowired
	private AwardTaggingService awardTaggingService;


	@RequestMapping(value = "listRelatedAwardItemView")
	public ModelAndView listRelatedAwardItemView(
			RelatedAwardItemSearchCondition searchCondition,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false") Boolean popupYn
	) {
		SearchResult<AwardItem> searchResult = this.awardTaggingService.listRelatedAwardItemBySearchCondition(searchCondition);

		return new ModelAndView()
		.addObject("popupYn", popupYn)
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", searchConditionString);
	}

}
