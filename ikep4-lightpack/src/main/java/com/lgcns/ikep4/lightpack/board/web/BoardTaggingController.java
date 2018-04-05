/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardTaggingService;

/**
 * 댓글 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/board/boardTagging")
public class BoardTaggingController {

	@Autowired
	private BoardTaggingService boardTaggingService;


	@RequestMapping(value = "listRelatedBoardItemView")
	public ModelAndView listRelatedBoardItemView(
			RelatedBoardItemSearchCondition searchCondition,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false") Boolean popupYn
	) {
		SearchResult<BoardItem> searchResult = this.boardTaggingService.listRelatedBoardItemBySearchCondition(searchCondition);

		return new ModelAndView()
		.addObject("popupYn", popupYn)
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", searchConditionString);
	}

}
