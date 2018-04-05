/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardCode;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardPermission;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board.BoardTypeBoard;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board.CategoryTypeBoard;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board.LinkTypeBoard;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeNode;


/**
 * 게시판 관리 컨트롤 클래스
 */
@Controller("cfBardAdminController")
@RequestMapping(value = "/lightpack/cafe/board/boardAdmin")
public class BoardAdminController extends BaseController {

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ACLService aclService;

	private static final String DEFAULT_BOARD_ROOT_ID = "0";

	private static final String CAFE_MANAGER = "Cafe";

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User) this.getRequestAttribute("ikep.user");
	}

	/**
	 * 세션에서 로그인 사용자의 포털 ID를 가져온다.
	 * 
	 * @return 로그인 사용자의 포털 ID
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
		return this.aclService.isSystemAdmin(CAFE_MANAGER, user.getUserId());
	}

	/**
	 * 게시판 권한 체크
	 * 
	 * @param user
	 * @param board
	 * @return
	 */
	private BoardPermission initPermission(User user, String cafeId) {

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Boolean isWritePermission = false;
		Boolean isReadPermission = false;

		/** 시스템 Admin **/
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}
		// 시샵, 운영진 체크
		Member member = new Member();
		member.setCafeId(cafeId);
		member.setMemberId(user.getUserId());

		member = memberService.read(member);

		if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
			isSystemAdmin = Boolean.TRUE;
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}

	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param boardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBoardView")
	public ModelAndView listBoardView(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardRootId") String boardRootId) {

		User user = this.readUser();

		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", cafeId);
		map.put("boardRootId", DEFAULT_BOARD_ROOT_ID);

		List<Board> boardList = this.boardAdminService.listByBoardRootId(map);

		return new ModelAndView().addObject("isSystemAdmin", permission.getIsSystemAdmin()).addObject("cafeId", cafeId)
				.addObject("boardList", boardList).addObject("boardRootId", boardRootId);
	}

	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param boardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listChildrenBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<TreeNode> listChildrenBoard(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId) {

		User user = this.readUser();

		BoardPermission permission = new BoardPermission(false, false, false);
		// Board readBoard = this.boardAdminService.readBoard(boardId);
		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		List<Board> boardList = null;

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		try {
			Map<String, String> boardMap = new HashMap<String, String>();
			boardMap.put("cafeId", cafeId);
			boardMap.put("boardId", boardId);

			boardList = this.boardAdminService.listChildrenBoard(boardMap);

			for (Board board : boardList) {
				treeNodeList.add(new TreeNode(board.getBoardId(), null, board.getBoardName(), "", (board
						.getHasChildren() > 0 ? "closed" : "leaf"), board));
			}

		} catch (Exception exception) {
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
	public @ResponseBody
	List<Board> listCurrentLevelBoard(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId) {
		User user = this.readUser();

		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		List<Board> boardList = null;

		try {
			Map<String, String> boardMap = new HashMap<String, String>();
			boardMap.put("cafeId", cafeId);
			boardMap.put("boardId", boardId);

			// boardList = this.boardAdminService.listChildrenBoard(boardId);
			boardList = this.boardAdminService.listChildrenBoard(boardMap);

		} catch (Exception exception) {
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
	public ModelAndView readBoardView(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId

	) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		Board board = this.boardAdminService.readBoard(boardId);

		return new ModelAndView().addObject("isSystemAdmin", permission.getIsSystemAdmin()).addObject("cafeId", cafeId)
				.addObject("board", board);
	}

	/**
	 * 게시판 생성 화면 컨트롤 메서드
	 * 
	 * @param boardRootId 루트 게시판 ID
	 * @param boardParentId 부모 게시판 ID
	 * @param boardType 게시판 타입게시판 ( 0 : 게시글 등록용, 1 : URL링크용게시판, 2 : 게시판 카테고리 구분용
	 *            Dummy게시판)
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createBoardView")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView createBoardView(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardRootId") String boardRootId,
			@RequestParam(value = "boardParentId") String boardParentId,
			@RequestParam(value = "boardType") String boardType) {
		try {
			User user = this.readUser();
			BoardPermission permission = new BoardPermission(false, false, false);

			// 관리자 권한 체크
			permission = initPermission(user, cafeId);

			// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
			if (!permission.getIsSystemAdmin()) {
				throw new IKEP4AuthorizedException();
			}

			Board board = null;

			if (this.getModelAttribute("board") == null) {
				board = new Board();

				Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
				String defaultRestrictionType = prop.getProperty("ikep4.support.fileupload.defaultRestrictionType");

				board.setCafeId(cafeId);
				board.setBoardRootId(boardRootId);
				board.setBoardType(boardType);
				board.setBoardParentId(boardParentId);
				board.setRestrictionType(defaultRestrictionType);
				board.setPortalId(this.getPortalId());

			} else {
				board = (Board) this.getModelAttribute("board");
			}

			Map<String, String> boardMap = new HashMap<String, String>();
			boardMap.put("cafeId", cafeId);
			boardMap.put("boardId", boardParentId);

			board.setParentList(this.boardAdminService.listParentBoard(boardMap));

			return this.bindResult(new ModelAndView().addObject("isSystemAdmin", permission.getIsSystemAdmin())
					.addObject("cafeId", cafeId).addObject("board", board));
		} catch (Exception ex) {
			throw new IKEP4AjaxException("knowledgePage", ex);
		}

	}

	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param board 게시판 생성 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/createLinkTypeBoard")
	public ModelAndView createLink(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@ValidEx(groups = LinkTypeBoard.class) Board board, BindingResult result, SessionStatus status) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardAdmin/createBoardView.do")
					.addObject("boardRootId", board.getBoardRootId()).addObject("boardParentId", board.getPortalId())
					.addObject("cafeId", cafeId).addObject("boardType", board.getBoardType());
		}

		board.setPortalId(this.getPortalId());

		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());

		String boardId = this.boardAdminService.createBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
	}

	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param board 게시판 생성 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/createCategoryTypeBoard")
	public ModelAndView createCategoryTypeBoard(@RequestParam(value = "cafeId") String cafeId,
			AccessingResult accessResult, @ValidEx(groups = CategoryTypeBoard.class) Board board, BindingResult result,
			SessionStatus status) {

		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}
		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardAdmin/createBoardView.do")
					.addObject("boardRootId", board.getBoardRootId()).addObject("boardParentId", board.getPortalId())
					.addObject("cafeId", cafeId).addObject("boardType", board.getBoardType());
		}

		board.setPortalId(this.getPortalId());
		// ---------------------------------------------------------------

		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());

		String boardId = this.boardAdminService.createBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
	}

	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param board 게시판 생성 화면에서 전달된 Board 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/createBoardTypeBoard")
	public ModelAndView createBoardTypeBoard(@RequestParam(value = "cafeId") String cafeId,
			AccessingResult accessResult, @ValidEx(groups = BoardTypeBoard.class) Board board, BindingResult result,
			SessionStatus status) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardAdmin/createBoardView.do")
					.addObject("boardRootId", board.getBoardRootId()).addObject("boardParentId", board.getPortalId())
					.addObject("cafeId", cafeId).addObject("boardType", board.getBoardType());
		}

		board.setPortalId(this.getPortalId());
		// ---------------------------------------------------------------

		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());
		if (board.getVersionManage() == 1)
			board.setWiki(board.getVersionManage());

		String boardId = this.boardAdminService.createBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
	}

	/**
	 * 게시판 수정 화면 컨트롤 메서드
	 * 
	 * @param boardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateBoardView")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView updateBoardView(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);
		try {
			// 관리자 권한 체크
			permission = initPermission(user, cafeId);

			// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
			if (!permission.getIsSystemAdmin()) {
				throw new IKEP4AuthorizedException();
			}
			Board board = null;

			if (this.getModelAttribute("board") == null) {
				board = this.boardAdminService.readBoard(boardId);
			} else {
				board = (Board) this.getModelAttribute("board");
			}
			return new ModelAndView().addObject("cafeId", cafeId).addObject("board", board);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("knowledgePage", ex);
		}
	}

	/**
	 * 게시판 이름 변경 수정 처리 비동기 컨트롤 메서드
	 * 
	 * @param boardId 수정대상 게시판 ID
	 * @param boardName 수정대상 게시판 이름
	 */
	@RequestMapping(value = "/updateBoardName")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateBoardName(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId, @RequestParam(value = "boardName") String boardName) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}
		Board board = this.boardAdminService.readBoard(boardId);

		board.setBoardName(boardName);

		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		this.boardAdminService.updateBoard(board, user);

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
	public @ResponseBody
	void updateMoveBoard(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId,
			@RequestParam(value = "boardParentId", required = false) String boardParentId,
			@RequestParam(value = "sortOrder") Integer sortOrder) {

		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}
		Board board = new Board();
		board.setBoardId(boardId);
		board.setSortOrder(sortOrder);
		board.setBoardParentId(boardParentId);

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
	public ModelAndView updateLinkTypeBoard(@RequestParam(value = "cafeId") String cafeId,
			AccessingResult accessResult, @ValidEx(groups = LinkTypeBoard.class) Board board, BindingResult result,
			SessionStatus status) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardAdmin/updateBoardView.do").addObject("cafeId",
					cafeId).addObject("boardId", board.getBoardId());
		}

		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		this.boardAdminService.updateBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
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
	public ModelAndView updateCategoryTypeBoard(@RequestParam(value = "cafeId") String cafeId,
			AccessingResult accessResult, @ValidEx(groups = CategoryTypeBoard.class) Board board, BindingResult result,
			SessionStatus status) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardAdmin/updateBoardView.do").addObject("cafeId",
					cafeId).addObject("boardId", board.getBoardId());
		}

		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		this.boardAdminService.updateBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
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
	public ModelAndView updateBoardTypeBoard(@RequestParam(value = "cafeId") String cafeId,
			AccessingResult accessResult, @ValidEx(groups = BoardTypeBoard.class) Board board, BindingResult result,
			SessionStatus status) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}
		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardAdmin/updateBoardView.do").addObject("cafeId",
					cafeId).addObject("boardId", board.getBoardId());
		}

		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());
		if (board.getVersionManage() == 1)
			board.setWiki(board.getVersionManage());
		this.boardAdminService.updateBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
	}

	/**
	 * 게시판 삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param boardId 삭제 대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteBoardAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void deleteBoardAjax(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}

		// this.boardAdminService.physicalDeleteBoard(boardId);
		this.boardAdminService.logicalDeleteBoard(boardId);
	}

	/**
	 * 게시판 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param boardId 삭제대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteBoard")
	public ModelAndView deleteBoard(@RequestParam(value = "cafeId") String cafeId, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId) {
		User user = this.readUser();
		BoardPermission permission = new BoardPermission(false, false, false);

		// 관리자 권한 체크
		permission = initPermission(user, cafeId);

		// Cafe 관리자 권한 체크 - Cafe 관리자/시샵/운영진 체크
		if (!permission.getIsSystemAdmin()) {
			throw new IKEP4AuthorizedException();
		}
		Board board = this.boardAdminService.readBoard(boardId);

		// this.boardAdminService.physicalDeleteBoard(boardId);
		this.boardAdminService.logicalDeleteBoard(boardId);
		return new ModelAndView("redirect:/lightpack/cafe/board/boardAdmin/listBoardView.do").addObject("cafeId",
				cafeId).addObject("boardRootId", board.getBoardRootId());
	}
}
