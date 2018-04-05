/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.portlet.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.model.BoardPermission;
import com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포틀릿 공용 게시판 컨트롤 클래스.
 */
@Controller
@RequestMapping(value = "/lightpack/board/portlet/markBoard")
public class MarkBoardController extends BaseController{

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;
	
	/** The board item service. */
	@Autowired
	private BoardPortletService boardPortletService;

	/** The board item service. */
	@Autowired
	private BoardItemService boardItemService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;

	/** The Constant DEFAULT_PAGE_PER_RECORD. */
//	private final static Integer DEFAULT_PAGE_PER_RECORD = 10;


	/**
	 * 포틀릿 일반 화면 담당 메소드
	 *
	 * @return the model and view
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(String portletConfigId) {
		ModelAndView mav = new ModelAndView("lightpack/board/portlet/markBoard/normalView");
		User user = (User)getRequestAttribute("ikep.user");
		
		//String registerId = user.getUserId();
		List<Board> boardList = this.boardPortletService.listBySelectedBoardForMarkBoard(portletConfigId);

		mav.addObject("boardList", boardList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}


	/**
	 * 투데이터 포커스 제공 비동기 처리 메서드
	 *
	 * @return the list
	 */
	@RequestMapping(value = "/listBoardItem.do")
	public ModelAndView listBoardItem(@RequestParam("boardId") String boardId,@RequestParam("setcount") Integer setcount) {
		List<BoardItem> boardItemList = null;

		if (setcount.equals(null) || setcount.equals("")){
			setcount = 3;
		}
		
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		
		User user = (User)getRequestAttribute("ikep.user");		
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setBoardId(boardId);
		searchCondition.setRecordCount(setcount == null ? 10 : setcount);
		
		Board board = this.boardAdminService.readBoard(searchCondition.getBoardId());
		
		BoardPermission boardPermission = this.initPermission(user, board);
	
		searchCondition.setAdmin(boardPermission.getIsSystemAdmin());		

		boardItemList = this.boardItemService.listRecentBoardItem(searchCondition);

		ModelAndView mav = new ModelAndView("lightpack/board/portlet/markBoard/boardItemList");

		mav.addObject("boardItemList", boardItemList);
		mav.addObject("boardId", boardId);

		return mav;

	}

	//설정화면
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(String portletConfigId) {
		User user = (User)getRequestAttribute("ikep.user");	
		Portal portal = (Portal)getRequestAttribute("ikep.portal");

		
		List<Board> boardList = this.boardPortletService.listBoardTypeBoardmark(portletConfigId, user.getUserId(), portal.getPortalId());
		ModelAndView mav =  new ModelAndView("lightpack/board/portlet/markBoard/configView");

		mav.addObject("boardList", boardList);
		mav.addObject("portletConfigId", portletConfigId);
		return mav;

	}

	//설정화면
	@RequestMapping(value = "/updatePortlet.do")
	public @ResponseBody void updatePortletMark(@RequestParam("boardId") String[] boardIdList, String portletConfigId, String viewCount) {
		try {
			User user = (User)getRequestAttribute("ikep.user");
			this.boardPortletService.deletePortletMark(portletConfigId);
			this.boardPortletService.createPortletMark(Arrays.asList(boardIdList), user.getUserId(),portletConfigId,viewCount);
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("BBS", user.getUserId());
	}

	public boolean isWritePermission(User user, String boardId) {
		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "WRITE", boardId, user.getUserId());
	}

	public boolean isReadPermission(User user, String boardId) {
		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "READ", boardId, user.getUserId());
	}
	
	private BoardPermission initPermission(User user, Board board) {
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Boolean isWritePermission = this.isWritePermission(user, board.getBoardId());

		Boolean isReadPermission = this.isReadPermission(user, board.getBoardId());

		// 관리자의 경우 쓰기 권한과 읽기 권한을 True 한다.
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			// 쓰기권한자에게 쓰기 권한과 읽기 권한을 True 한다.
		} else if (isWritePermission) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		}

		// 게시판 읽기가 공개인 경우 읽기 권한을 True 한다.
		if (board.getReadPermission() == 1) {
			isReadPermission = Boolean.TRUE;

		}

		// 게시판 쓰기가 공개인 경우 쓰기, 읽기권한을 True 한다.
		if (board.getWritePermission() == 1) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		}
		
		//System.out.println("@@@@@@@@@@@@@@@@@isSystemAdmin:"+isSystemAdmin);
		//System.out.println("@@@@@@@@@@@@@@@@@isWritePermission:"+isWritePermission);
		//System.out.println("@@@@@@@@@@@@@@@@@isReadPermission:"+isReadPermission);
		
		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}
}