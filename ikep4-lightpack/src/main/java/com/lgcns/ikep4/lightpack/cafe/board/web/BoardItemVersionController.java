/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItemVersion;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemVersionSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardItemVersionService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 게시물 버전 관리
 * 
 * @author happyi1018
 * @version $Id: BoardItemVersionController.java 14298 2011-06-03 02:29:54Z
 *          jghong $
 */
@Controller("cfBoardItemVersionController")
@RequestMapping(value = "/lightpack/cafe/board/boardItemVersion")
public class BoardItemVersionController extends BaseController {

	@Autowired
	private BoardItemVersionService boardItemVersionService;

	/**
	 * 게시물 버전 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listBoardItemVersionView")
	public ModelAndView listBoardItemVersionView(BoardItemVersionSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		// User user = (User)getRequestAttribute("ikep.user");

		SearchResult<BoardItemVersion> searchResult = null;

		searchResult = this.boardItemVersionService.listBoardItemBySearchCondition(searchCondition);

		String viewName = null;
		// view 연결
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("searchResult", searchResult)
		// .addObject("boardCode", new BoardCode())
				.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 게시물 버전별 비교
	 * 
	 * @param searchCondition
	 * @param boardItemVersion
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/compareBoardItemVersionView")
	public ModelAndView compareBoardItemVersionView(BoardItemVersionSearchCondition searchCondition,
			BoardItemVersion boardItemVersion, BindingResult result, SessionStatus status) {

		// User user = (User)getRequestAttribute("ikep.user");

		BoardItemVersion itemVersion = new BoardItemVersion();

		itemVersion = this.boardItemVersionService.compareBoardItemVersion(boardItemVersion.getVersionIds());

		String viewName = null;
		// view 연결
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("boardItemVersion", itemVersion).addObject(
				"searchCondition", searchCondition);
		// .addObject("boardCode", new BoardCode());

		return modelAndView;
	}

	/**
	 * 게시물 선택한 버전으로 되돌리기
	 * 
	 * @param versionId
	 * @return
	 */
	@RequestMapping(value = "/returnBoardItemVersion")
	public @ResponseBody
	int returnBoardItemVersion(@RequestParam("versionId") String versionId) {
		int isSuccess = 0;
		User user = (User) getRequestAttribute("ikep.user");

		try {

			isSuccess = this.boardItemVersionService.returnBoardItemVersion(versionId, user);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return isSuccess;

	}
}
