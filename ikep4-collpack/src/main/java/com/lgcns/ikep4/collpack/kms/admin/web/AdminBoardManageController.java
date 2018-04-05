package com.lgcns.ikep4.collpack.kms.admin.web;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardCode;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminBoardManageService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeNode;
/**
 * AdminBoardManage Controller
 * 
 * @author 정애란(tseliot@lgcns.com)
 * @version 
 */

@Controller
@RequestMapping(value = "/collpack/kms/admin/board")
@SessionAttributes("board")
public class AdminBoardManageController extends BaseController {
	
	
	@Autowired
	private AdminBoardManageService adminBoardManageService;
	
	@Autowired
	private ACLService aclService;
	
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
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param boardRootId 루트 게시판 Id
	 * @param isKnowHow 업무노하우게시판 : 0, 일반정보게시판 :1
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBoardView")
	public ModelAndView listBoardView(@RequestParam(value = "boardRootId", required = false ) String boardRootId,
			@RequestParam(value = "isKnowHow" ) String isKnowHow) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> paramMap = getParamBoardMap(isKnowHow, boardRootId);
				
		List<Board> boardList = adminBoardManageService.listByBoardRootId(paramMap);
		
		String pageTitle = getPageTitle(isKnowHow);
		
		return new ModelAndView()
				.addObject("boardList", boardList)
				.addObject("boardRootId", boardRootId).addObject("pageTitle", pageTitle);
	}

	private String getPageTitle(String isKnowHow) {
		String pageTitle = "info";		
		
		if(isKnowHow.equals(Board.BD_KNOWHOW)){
			pageTitle = "knowhow";
		}else if(isKnowHow.equals(Board.BD_SITE)){
			pageTitle = "site";
		}else if(isKnowHow.equals("3")){
			pageTitle = "origin";
		}
		return pageTitle;
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin() {
		User user = this.readUser();
		return this.aclService.isSystemAdmin(Board.KMS_MANAGER, user.getUserId());
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
	List<TreeNode> listChildrenBoard(@RequestParam(value = "isKnowHow") String isKnowHow, @RequestParam(value = "boardId") String boardId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}

		List<Board> boardList = null;

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		try {
			Map<String, String> boardMap = getParamBoardMap(isKnowHow, boardId);

			boardList = adminBoardManageService.listChildrenBoard(boardMap);

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
	@RequestMapping(value = "/listChildrenBoardMenu")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<TreeNode> listChildrenBoardMenu(@RequestParam(value = "isKnowHow") String isKnowHow, @RequestParam(value = "boardId") String boardId) {

		List<Board> boardList = null;

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		try {
			Map<String, String> boardMap = getParamBoardMap(isKnowHow, boardId);
			boardList = adminBoardManageService.listChildrenBoard(boardMap);

			for (Board board : boardList) {
				treeNodeList.add(new TreeNode(board.getBoardId(), null, board.getBoardName(), "", (board
						.getHasChildren() > 0 ? "closed" : "leaf"), board));
			}

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return treeNodeList;
	}

	private Map<String, String> getParamBoardMap(String isKnowHow, String boardId) {
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowHow", isKnowHow);
		boardMap.put("boardId", boardId);
		return boardMap;
	}
	
	
	/**
	 * 게시판 상세조회 화면 컨트롤 메서드
	 * 
	 * @param boardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readBoardView")
	public ModelAndView readBoardView(@RequestParam(value = "isKnowHow") String isKnowHow, @RequestParam(value = "boardId") String boardId,AccessingResult accessResult) {
		
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> paramMap = getParamBoardMap(isKnowHow, boardId);
		
		Board board = adminBoardManageService.readBoard(paramMap);
		String pageTitle = getPageTitle(isKnowHow);
		
		return new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("board", board)
				.addObject("pageTitle", pageTitle);
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
	public ModelAndView createBoardView(@RequestParam(value = "isKnowHow") String isKnowHow,
			AccessingResult accessResult, @RequestParam(value = "boardRootId") String boardRootId,
			@RequestParam(value = "boardParentId") String boardParentId,
			@RequestParam(value = "boardType") String boardType) {

		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}

		Board board = null;

		if (this.getModelAttribute("board") == null) {
			board = new Board();

			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			String defaultRestrictionType = prop.getProperty("ikep4.support.fileupload.defaultRestrictionType");

			board.setIsKnowHow(isKnowHow);
			board.setBoardRootId(boardRootId);
			board.setBoardType(boardType);
			board.setBoardParentId(boardParentId);
			board.setRestrictionType(defaultRestrictionType);
			board.setPortalId(this.getPortalId());

		} else {
			board = (Board) this.getModelAttribute("board");
		}

		Map<String, String> boardMap = getParamBoardMap(isKnowHow, boardParentId);
		board.setParentList(adminBoardManageService.listParentBoard(boardMap));		

		return this.bindResult(new ModelAndView().addObject("board", board).addObject("pageTitle", getPageTitle(isKnowHow)));

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
	public ModelAndView createLink(Board board, BindingResult result,SessionStatus status) {
		return execCreateBoard(board.getIsKnowHow(), board, result, status);
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
	public ModelAndView createCategoryTypeBoard(Board board, BindingResult result,SessionStatus status) {
		return execCreateBoard(board.getIsKnowHow(), board, result, status);
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
	public ModelAndView createBoardTypeBoard(Board board, BindingResult result,SessionStatus status) {
		return execCreateBoard(board.getIsKnowHow(), board, result, status);
	}	
	
	
	private ModelAndView execCreateBoard(String isKnowHow, Board board, BindingResult result, SessionStatus status) {
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}
		
		User user = readUser();

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/collpack/kms/admin/board/createBoardView.do")
					.addObject("boardRootId", board.getBoardRootId()).addObject("boardParentId", board.getPortalId())
					.addObject("isKnowHow", isKnowHow).addObject("boardType", board.getBoardType());
		}

		board.setPortalId(this.getPortalId());
		ModelBeanUtil.bindRegisterInfo(board, user.getUserId(), user.getUserName());
		String boardId = adminBoardManageService.createBoard(board, user);
		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/admin/board/readBoardView.do")
				.addObject("isKnowHow", isKnowHow).addObject("leftBoardMenuReload", true)
				.addObject("boardId", boardId);
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
	void updateBoardName(@RequestParam(value = "isKnowHow") String isKnowHow, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId, @RequestParam(value = "boardName") String boardName) {
		/*
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}
		*/
		Board board = adminBoardManageService.readBoard(getParamBoardMap(isKnowHow, boardId));
		board.setBoardName(boardName);		
		User user = readUser();
		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		adminBoardManageService.updateBoard(board, user);

	}	
	
	/**
	 * 게시판 위치 이동 처리 비동기 컨트롤 메서드
	 * 
	 * @param boardId 이동 게시판 ID
	 * @param boardParentId 이동 게시판의 부모 ID
	 * @param sortOrder 이동 위치
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateMoveBoard.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateMoveBoard(Board board) {
		/*
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}
						
		*/
		try {
			
			User user = readUser();
			ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());
			adminBoardManageService.updateBoardMove(board, getParamBoardMap(board.getIsKnowHow(), board.getBoardId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	
	/**
	 * 게시판 수정 화면 컨트롤 메서드
	 * 
	 * @param boardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateBoardView")
	public ModelAndView updateBoardView(@RequestParam(value = "isKnowHow") String isKnowHow, @RequestParam(value = "boardId") String boardId, 
			AccessingResult accessResult, @ModelAttribute Board board, BindingResult bindResult) {
		
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}
		String pageTitle = getPageTitle(isKnowHow);
		
		return new ModelAndView().addObject("board", board).addObject("pageTitle", pageTitle);
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
	public ModelAndView updateLinkTypeBoard(@ModelAttribute Board board, BindingResult result, SessionStatus status) {
		
		return execUpdateBoard(board.getIsKnowHow(), board, result, status);
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
	public ModelAndView updateCategoryTypeBoard(@ModelAttribute Board board, BindingResult result, SessionStatus status) {
		
		return execUpdateBoard(board.getIsKnowHow(), board, result, status);
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
	public ModelAndView updateBoardTypeBoard(@ModelAttribute Board board, BindingResult result, SessionStatus status) {
		return execUpdateBoard(board.getIsKnowHow(), board, result, status);
		
	}
	
	private ModelAndView execUpdateBoard(String isKnowHow, Board board, BindingResult result, SessionStatus status) {
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("board", board, result);
			return new ModelAndView("forward:/collpack/kms/admin/board/updateBoardView.do").addObject(
					"isKnowHow", isKnowHow).addObject("boardId", board.getBoardId());
		}

		User user = (User) this.getRequestAttribute("ikep.user");
		ModelBeanUtil.bindUpdaterInfo(board, user.getUserId(), user.getUserName());

		adminBoardManageService.updateBoard(board, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/admin/board/readBoardView.do")
				.addObject("isKnowHow", isKnowHow)
				//.addObject("leftBoardMenuReload", true)
				.addObject("boardId", board.getBoardId());
	}
	
	/**
	 * 게시판 삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param boardId 삭제 대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteBoardAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void deleteBoardAjax(@RequestParam(value = "isKnowHow") String isKnowHow, AccessingResult accessResult,
			@RequestParam(value = "boardId") String boardId) {
		
		/*
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}

		*/
		// this.boardAdminService.physicalDeleteBoard(boardId);
		try {
			Map<String, String> paramMap = getParamBoardMap(isKnowHow, boardId);
			adminBoardManageService.logicalDeleteBoard(paramMap);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 게시판 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param boardId 삭제대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteBoard")
	public ModelAndView deleteBoard(@RequestParam(value = "isKnowHow") String isKnowHow,
			AccessingResult accessResult, @RequestParam(value = "boardId") String boardId) {
		boolean isSystemAdmin = isSystemAdmin();
		if(!isSystemAdmin){
			throw new IKEP4AuthorizedException();
		}
	
		Map<String, String> paramMap = getParamBoardMap(isKnowHow, boardId);
		
		Board board = adminBoardManageService.readBoard(paramMap);

		// this.boardAdminService.physicalDeleteBoard(boardId);
		adminBoardManageService.logicalDeleteBoard(paramMap);
		return new ModelAndView("redirect:/collpack/kms/admin/board/listBoardView.do")
				.addObject("isKnowHow", isKnowHow).addObject("leftBoardMenuReload", true)
				.addObject("boardRootId", board.getBoardRootId());
	}	
	
}
