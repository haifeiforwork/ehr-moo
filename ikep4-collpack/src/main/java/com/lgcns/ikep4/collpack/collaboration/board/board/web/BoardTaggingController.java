/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItem;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardTaggingService;


/**
 * 댓글 컨트롤 클래스
 */
@Controller("wsBoardTaggingController")
@RequestMapping(value = "/collpack/collaboration/board/boardTagging")
public class BoardTaggingController {

	@Autowired
	private BoardTaggingService boardTaggingService;

	/**
	 * 게시판 관련글 목록
	 * 
	 * @param searchCondition
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "listRelatedBoardItemView")
	public ModelAndView listRelatedBoardItemView(RelatedBoardItemSearchCondition searchCondition,
			@RequestParam("workspaceId") String workspaceId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) {
		SearchResult<BoardItem> searchResult = this.boardTaggingService.listRelatedBoardItemBySearchCondition(
				searchCondition, workspaceId);

		return new ModelAndView().addObject("searchResult", searchResult)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", searchConditionString);
	}

}
