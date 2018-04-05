/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.Board.BoardTypeBoard;
import com.lgcns.ikep4.lightpack.board.model.Board.CategoryTypeBoard;
import com.lgcns.ikep4.lightpack.board.model.Board.LinkTypeBoard;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemCategoryService;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeNode;

/**
 * 게시판 관리 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/board/boardAdmin")
public class BoardAdminController extends BaseController {

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CacheService cacheService;
	
	/** The board item Category service. */
	@Autowired
	private BoardItemCategoryService boardItemCategoryService;

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User)this.getRequestAttribute("ikep.user");
	}
	
	
	private Portal readPortal() {
		return (Portal)this.getRequestAttribute("ikep.portal");
	}

	/**
	 * 세션에서 로그인 사용자의 포털 ID를 가져온다.
	 *
	 * @return  로그인 사용자의 포털 ID
	 */
	private String getPortalId() {
		User user = this.readUser();
		return user.getPortalId();
	}


	@ModelAttribute("boardCode")
	public BoardCode setBoardCode() {
		return new BoardCode();
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


	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 *
	 * @param boardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBoardView")
	public ModelAndView listBoardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardRootId") String boardRootId,
			@RequestParam(value="popupYn", defaultValue="false") Boolean popupYn) {

		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();


		Boolean isSystemAdmin = this.isSystemAdmin(user);

		List<Board> boardList = this.boardAdminService.listByBoardRootId(boardRootId, this.getPortalId());
		
		boolean isMenuTree = boardAdminService.isBoardMenuTree();

		return new ModelAndView()
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("boardRootId", boardRootId)
		.addObject("boardList", boardList)
		.addObject("popupYn", popupYn)
		.addObject("boardRootId", boardRootId)
		.addObject("isMenuTree", isMenuTree);
	}

	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 *
	 * @param boardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listChildrenBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildrenBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}


		List<Board> boardList = null;

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		
		User user = this.readUser();
		Portal portal = this.readPortal();


		try {
			boardList = this.boardAdminService.listChildrenBoard(boardId, portal.getPortalId());
			
			String boardName = "";

			for(Board board : boardList) {
				
				if(portal.getDefaultLocaleCode().equals(user.getLocaleCode())){
					boardName = board.getBoardName();
				} else {
					boardName = board.getBoardEnglishName();
				}
				
				treeNodeList.add( new TreeNode(board.getBoardId(), null, boardName, "", (board.getHasChildren() > 0 ? "closed" : "leaf"), board));
			}

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}


		return treeNodeList;
	}

	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 *
	 * @param boardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listCurrentLevelBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Board> listCurrentLevelBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}


		List<Board> boardList = null;

		try {
			boardList = this.boardAdminService.listChildrenBoard(boardId);


		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return boardList;
	}


	/**
	 * 게시판 상세조회 화면 컨트롤 메서드
	 *
	 * @param boardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readBoardView")
	public ModelAndView readBoardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId

	) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Board board = this.boardAdminService.readBoard(boardId);

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		return new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("board", board);
	}

	/**
	 * 게시판 생성 화면 컨트롤 메서드
	 *
	 * @param boardRootId 루트 게시판 ID
	 * @param boardParentId 부모 게시판 ID
	 * @param boardType 게시판 타입게시판 ( 0 : 게시글 등록용, 1 : URL링크용게시판, 2 : 게시판 카테고리 구분용 Dummy게시판)
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createBoardView")
	public ModelAndView createBoardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardRootId") String boardRootId,
			@RequestParam(value="boardParentId") String boardParentId,
			@RequestParam(value="boardType") String boardType) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Board board = null;

		if(this.getModelAttribute("board") == null) {
			board = new Board();

			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			String defaultRestrictionType = prop.getProperty("ikep4.support.fileupload.defaultRestrictionType");


			board.setBoardRootId(boardRootId);
			board.setBoardType(boardType);
			board.setBoardParentId(boardParentId);
			board.setRestrictionType(defaultRestrictionType);
			board.setPortalId(this.getPortalId());
			board.setBoardDelete(Board.BOARD_DELETE_SERVICING);

		} else {
			board = (Board)this.getModelAttribute("board");
		}

		board.setParentList(this.boardAdminService.listParentBoard(boardParentId));

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		//List<BoardItemCategory> boardItemCategoryList = null;
    	//boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem();
		//return this.bindResult(new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("board", board).addObject("boardItemCategoryList",boardItemCategoryList));

		return this.bindResult(new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("board", board));

	}
	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 *
	 * @param board 게시판 생성 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createLinkTypeBoard")
	public ModelAndView createLink(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=LinkTypeBoard.class) Board board,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/board/boardAdmin/createBoardView.do")
			.addObject("boardRootId", board.getBoardRootId())
			.addObject("boardParentId", board.getPortalId())
			.addObject("boardType", board.getBoardType());
		}

		board.setPortalId(this.getPortalId());
		board.setBoardDelete(Board.BOARD_DELETE_SERVICING);
		//---------------------------------------------------------------
		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());

		String boardId = this.boardAdminService.createBoard(board);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardAdmin/readBoardView.do").addObject("boardId", boardId);
	}

	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 *
	 * @param board 게시판 생성 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createCategoryTypeBoard")
	public ModelAndView createCategoryTypeBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=CategoryTypeBoard.class) Board board,
			BindingResult result,
			SessionStatus status) {

		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/board/boardAdmin/createBoardView.do")
			.addObject("boardRootId", board.getBoardRootId())
			.addObject("boardParentId", board.getPortalId())
			.addObject("boardType", board.getBoardType());
		}
	
		board.setPortalId(this.getPortalId());
		board.setBoardDelete(Board.BOARD_DELETE_SERVICING);
		//---------------------------------------------------------------
		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());

		String boardId = this.boardAdminService.createBoard(board);


		status.setComplete();
		
		return new ModelAndView("redirect:/lightpack/board/boardAdmin/readBoardView.do").addObject("boardId", boardId);
	}


	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 *
	 * @param board 게시판 생성 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createBoardTypeBoard")
	public ModelAndView createBoardTypeBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=BoardTypeBoard.class) Board board,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/board/boardAdmin/createBoardView.do")
			.addObject("boardRootId", board.getBoardRootId())
			.addObject("boardParentId", board.getPortalId())
			.addObject("boardType", board.getBoardType());
		}

		board.setPortalId(this.getPortalId());
		board.setBoardDelete(Board.BOARD_DELETE_SERVICING);
		//---------------------------------------------------------------
		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());
		String boardId = this.boardAdminService.createBoard(board);
		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardAdmin/readBoardView.do").addObject("boardId", boardId);
	}




	/**
	 * 게시판 수정 화면 컨트롤 메서드
	 *
	 * @param boardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateBoardView")
	public ModelAndView updateBoardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Board board = null;
		if(this.getModelAttribute("board") == null) {
			board = this.boardAdminService.readBoard(boardId);
		} else {
			board = (Board)this.getModelAttribute("board");
		}
		BoardItemCategory categoryBoardId = new BoardItemCategory();
		categoryBoardId.setBoardId(board.getBoardId());
		
		List<BoardItemCategory> boardItemCategoryList = null;

    	boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
		
		return new ModelAndView().addObject("board", board).addObject("boardItemCategoryList",boardItemCategoryList);
	}


	/**
	 * 게시판 이름 변경 수정 처리 비동기 컨트롤 메서드
	 *
	 * @param boardId 수정대상 게시판 ID
	 * @param boardName 수정대상 게시판 이름
	 */
	@RequestMapping(value = "/updateBoardName")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateBoardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId, @RequestParam(value="boardName") String boardName) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Board board = this.boardAdminService.readBoard(boardId);

		User user = this.readUser();
		Portal portal = this.readPortal();
			
		if(portal.getDefaultLocaleCode().equals(user.getLocaleCode())){
			board.setBoardName(boardName);
		} else {
			board.setBoardEnglishName(boardName);
		}

		board.setPortalId(this.getPortalId());

		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		this.boardAdminService.updateBoard(board);

	}

	/**
	 * 게시판 위치 이동 처리 비동기 컨트롤 메서드
	 *
	 * @param boardId 이동 게시판 ID
	 * @param boardParentId 이동 게시판의 부모 ID
	 * @param sortOrder 이동 위치
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateMoveBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateMoveBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId, @RequestParam(value="boardParentId", required=false) String boardParentId, @RequestParam(value="sortOrder") Integer sortOrder) {

		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Board board = new Board();

		board.setBoardId(boardId);
		board.setSortOrder(sortOrder);
		board.setBoardParentId(boardParentId);
		board.setPortalId(this.getPortalId());

		User user = (User)this.getRequestAttribute("ikep.user");

		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		this.boardAdminService.updateBoardMove(board);

	}


	/**
	 * 게시판 수정 처리 동기 컨트롤 메서드
	 *
	 * @param board 게시판 수정 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateLinkTypeBoard")
	public ModelAndView updateLinkTypeBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=LinkTypeBoard.class) Board board,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/board/boardAdmin/updateBoardView.do").addObject("boardId", board.getBoardId());
		}

		User user = this.readUser();
		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		board.setPortalId(this.getPortalId());

		this.boardAdminService.updateBoard(board);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardAdmin/readBoardView.do").addObject("boardId", board.getBoardId());
	}
	/**
	 * 게시판 수정 처리 동기 컨트롤 메서드
	 *
	 * @param board 게시판 수정 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateCategoryTypeBoard")
	public ModelAndView updateCategoryTypeBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=CategoryTypeBoard.class) Board board,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			System.out.println("111111111111111111111111111111111111111");
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/board/boardAdmin/updateBoardView.do").addObject("boardId", board.getBoardId());
		}

		User user = this.readUser();
		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		board.setPortalId(this.getPortalId());
		System.out.println("22222222222222222222222222222222222");
		this.boardAdminService.updateBoard(board);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardAdmin/readBoardView.do").addObject("boardId", board.getBoardId());
	}
	/**
	 * 게시판 수정 처리 동기 컨트롤 메서드
	 *
	 * @param board 게시판 수정 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateBoardTypeBoard")
	public ModelAndView updateBoardTypeBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=BoardTypeBoard.class) Board board,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/board/boardAdmin/updateBoardView.do").addObject("boardId", board.getBoardId());
		}

		User user = this.readUser();
		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		board.setPortalId(this.getPortalId());

		this.boardAdminService.updateBoard(board);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardAdmin/readBoardView.do").addObject("boardId", board.getBoardId());
	}

	/**
	 * 게시판 삭제 처리 비동기 컨트롤 메서드
	 *
	 * @param boardId 삭제 대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteBoardAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteBoardAjax(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		this.boardAdminService.physicalDeleteBoard(boardId);
		
		// public board 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-publicBoard-portlet");
	}

	/**
	 * 게시판 삭제 처리 동기 컨트롤 메서드
	 *
	 * @param boardId 삭제대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteBoard")
	public ModelAndView deleteBoard(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Board.BOARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="boardId") String boardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Board board = this.boardAdminService.readBoard(boardId);

		this.boardAdminService.physicalDeleteBoard(boardId);
		
		// public board 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-publicBoard-portlet");

		return new ModelAndView("redirect:/lightpack/board/boardAdmin/listBoardView.do").addObject("boardRootId", board.getBoardRootId());
	}
	
	/**
	 * 게시판 메뉴 형태 변경
	 * 
	 * @param isTree
	 */
	@RequestMapping(value = "/changeBoardMenuType")
	public void changeBoardMenuType(@RequestParam(value="isTree") Boolean isTree, HttpServletResponse res) {
		try {
			boardAdminService.updateBoardMenuType(isTree);
			res.getWriter().print("{rsult:\"OK\"}");
		} catch(IOException e) {
			
		}
	}
	
	@RequestMapping(value = "/listCategoryView")
	public ModelAndView listCategoryView(@RequestParam(value = "boardId") String boardId) {

		BoardItemCategory categoryBoardId = new BoardItemCategory();
		categoryBoardId.setBoardId(boardId);

		List<BoardItemCategory> boardItemCategoryList = null;
		boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("boardItemCategoryList", boardItemCategoryList);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	/**
	 * 게시판 카테고리 등록
	 * @param boardItemCategory
	 * @param result
	 * @param statu
	 */
	@RequestMapping(value = "/createCategoryBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryBoard(BoardItemCategory boardItemCategory ,@RequestParam(value = "boardId") String boardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = boardItemCategory.getAddNameList();
		String delIdList = boardItemCategory.getDelIdList();
		String updateNameList = boardItemCategory.getUpdateNameList();
		String updateIdList = boardItemCategory.getUpdateIdList();

		BoardItemCategory receiveCategoryNmList = new BoardItemCategory();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setBoardId(boardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(boardItemCategory.getAlignList());

		List<BoardItemCategory> list = new ArrayList<BoardItemCategory>();
		list.add(receiveCategoryNmList);
		this.boardItemCategoryService.insertCategoryNm(list);
		
		return "success";
	}
}
