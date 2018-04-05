/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminBoardManageService;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.model.BoardAssessItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardCode;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem.CreateReplyItemGroup;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem.ModifyItemGroup;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemCode;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemReader;
import com.lgcns.ikep4.collpack.kms.board.model.BoardPermission;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommend;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardTaggingService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.util.BrowserCheck;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeMaker;


/**
 * 게시글 컨트롤 클래스
 */
@Controller("kmsBoardItemController")
@RequestMapping(value = "/collpack/kms/board/boardItem")
public class BoardItemController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;

	/** The board item service. */
	@Autowired
	private BoardItemService boardItemService;
	
	@Autowired
	private AdminBoardManageService adminBoardManageService;

	/** The acl service. */
	@Autowired
	private ACLService aclService;

	// @Autowired
	// private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private UserConfigService userConfigService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;
	
	@Autowired
	private BoardTaggingService boardTaggingService;
	
	@Autowired
	private AdminPermissionService adminPermissionService;
	
	@Autowired
	private AdminPermissionDao adminPermissionDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PortalService portalService;

	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
	private UserThemeService userThemeService;

	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
    private UserDao userDao;

	private static final String KMS_MANAGER = "Kms";

	private static final String DEFAULT_BOARD_ROOT_ID = "0";
	
	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;
	
	/*
	 * 지식광장의 경우 관계사에게 별도의 로그인아이디를 주어 로그인 후 지식광장 페이지로 redirect 시킴
	 * kms-except-login-id.properties 에 별도관리해야할 아이디와 redirectUrl을 관리한다.
	 * */
	private String[] kmsLoginIds;
	private String kmsRedirectUrl;
	private Properties kmsProperty;

	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin(KMS_MANAGER, user.getUserId());
	}

	/**
	 * 게시판 쓰기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isWritePermission(User user, String boardId) {

		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "WRITE", boardId, user.getUserId());
	}

	/**
	 * 게시판 읽기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isReadPermission(User user, String boardId) {

		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "READ", boardId, user.getUserId());
	}

	/**
	 * 게시물 읽기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isContentsReadPermission(User user, String itemId) {

		return this.aclService.hasContentPermission(Board.BOARD_ITEM_ACL_CLASS_NAME, "READ", itemId, user.getUserId());
	}

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User) this.getRequestAttribute("ikep.user");
	}

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private Portal readPortal() {
		return (Portal) this.getRequestAttribute("ikep.portal");
	}

	/**
	 * 게시판 권한 체크
	 * 
	 * @param user
	 * @param board
	 * @return
	 */
	private BoardPermission initPermission(User user, Board board) {

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Boolean isWritePermission = true;
		Boolean isReadPermission = true;

		/** 시스템 Admin **/
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}
		
//		
//		// 시샵, 운영진 체크
//		Member member = new Member();
//		member.setWorkspaceId(board.getWorkspaceId());
//		member.setMemberId(user.getUserId());
//
//		member = memberService.read(member);
//		if(member == null) {
//			member = new Member();
//			member.setMemberId(user.getUserId());
//			member.setBoardId(board.getBoardId());
//			member = memberService.readForAlliance(member);
//		}
//
//		if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
//			isSystemAdmin = Boolean.TRUE;
//			isWritePermission = Boolean.TRUE;
//			isReadPermission = Boolean.TRUE;
//
//			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
//		}
//
//		/** Write Check **/
//
//		if (board.getWritePermission().equals("1")) {
//			isWritePermission = Boolean.TRUE;
//		} else {
//
//			if (member != null && !member.getMemberLevel().equals("")) {
//				if (board.getWritePermission().equals("2") && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
//																												// 이상
//					isWritePermission = true;
//				} else if (board.getWritePermission().equals("3") && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
//																														// 이상
//					isWritePermission = true;
//				} else if (board.getWritePermission().equals("4")) { // 개별 설정
//					isWritePermission = this.isWritePermission(user, board.getBoardId());
//				}
//
//			}
//		}
//		// 쓰기권한자에게 쓰기 권한과 읽기 권한을 True 한다.
//		if (isWritePermission) {
//			isWritePermission = Boolean.TRUE;
//			isReadPermission = Boolean.TRUE;
//
//			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
//		}
//
//		/** Read Check **/
//
//		if (board.getReadPermission().equals("1")) {
//			isReadPermission = Boolean.TRUE;
//		} else {
//			if (member != null && !member.getMemberLevel().equals("")) {
//				if (board.getReadPermission().equals("2") && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
//																												// 이상
//					isReadPermission = true;
//				} else if (board.getReadPermission().equals("3") && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
//																														// 이상
//					isReadPermission = true;
//				} else if (board.getReadPermission().equals("4")) { // 개별 설정
//					isReadPermission = this.isReadPermission(user, board.getBoardId());
//				}
//
//			}
//		}
		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}

	/**
	 * 게시물 권한 체크
	 * 
	 * @param user
	 * @param workspaceId
	 * @param boardItem
	 * @return
	 */
	private BoardPermission initContentsPermission(User user, String workspaceId, BoardItem boardItem) {

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Boolean isWritePermission = true;
		Boolean isReadPermission = true;

		/** 시스템 Admin **/
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}
		// 시샵, 운영진 체크
		Member member = new Member();
		member.setWorkspaceId(workspaceId);
		member.setMemberId(user.getUserId());

		member = memberService.read(member);
		if(member == null) {
			member = new Member();
			member.setMemberId(user.getUserId());
			member.setBoardId(boardItem.getBoardId());
			member = memberService.readForAlliance(member);
		}

		if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
			isSystemAdmin = Boolean.TRUE;
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

//		if (boardItem.getReadPermission().equals("1")) {
//			isReadPermission = true;
//		} else {
//			if (member != null && !member.getMemberLevel().equals("")) {
//				if (boardItem.getReadPermission().equals("2") && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
//																													// 이상
//					isReadPermission = true;
//				} else if (boardItem.getReadPermission().equals("3") && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
//																															// 이상
//					isReadPermission = true;
//				} else if (boardItem.getReadPermission().equals("4")) { // 개별 설정
//					isReadPermission = this.isContentsReadPermission(user, boardItem.getItemId());
//				}
//				// else
//				// isReadPermission = this.isContentsReadPermission(user,
//				// boardItem.getItemId());
//			}
//		}
		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}
	
	/**
	 * KMS 게시물 권한 체크
	 * 
	 * @param user
	 * @param boardItem
	 * @return
	 */
	private BoardPermission initKmsPermission(User user, BoardItem boardItem) {

		BoardPermission permission = new BoardPermission(this.isSystemAdmin(user), false, false);
		
		/** 등록자 **/
		if(user.getUserId().equals(boardItem.getRegisterId())){
			permission.setIsWritePermission(true);
			permission.setIsReadPermission(true);
		}else{
		
			/** 사무직이 아닐때 **/
			String jobClassCode = user.getJobClassCode();
			if("11".equals(jobClassCode) || "12".equals(jobClassCode) || "21".equals(jobClassCode) || "22".equals(jobClassCode)|| "24".equals(jobClassCode)|| "31".equals(jobClassCode) || checkKmsExceptionId(user.getUserId())){
				permission.setIsReadPermission(true);
				
				/** 등급별 게시물 **/
				String userGrade = user.getInfoGrade();
				String infoGrade = boardItem.getInfoGrade();
				
				if("C".equals(userGrade) && ("A".equals(infoGrade) || "B".equals(infoGrade)) ){
					permission.setIsReadPermission(false);
				}
				if("B".equals(userGrade) && "A".equals(infoGrade) ){
					permission.setIsReadPermission(false);
				}
			
			}
		}
		
		
		/** 시스템 Admin **/
		if (isSystemAdmin(user)) {
			permission.setIsWritePermission(true);
			permission.setIsReadPermission(true);
		}

		return permission;
	}

	/**
	 * 게시글 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBoardItemView")
	public ModelAndView listBoardItemView(BoardItemSearchCondition searchCondition,
			@RequestParam(value = "boardId", required = false) String boardId,
			@RequestParam(value = "isKnowhow", required = false) String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "boardItem") String pageGubun,
			@RequestParam(value = "pageFirst", defaultValue = "false") String pageFirst,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn

	) {
			
			User user = readUser();
			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isKnowhow " + isKnowhow);
			 
			searchCondition.setBoardId(boardId);
			
			Board board = this.boardAdminService.readBoard(boardId);
			searchCondition.setIsKnowhow(board.getIsKnowhow());

			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> board.getIsKnowhow() " + board.getIsKnowhow());
			

			if (searchCondition.getViewMode() == null) {
				searchCondition.setViewMode("0");
			}
			if(pageFirst.equals("true")){
				searchCondition.setPagePerRecord(board.getPageNum());
			}
			
			if(searchCondition.getSearchStartRegDate() == null){
				searchCondition.setSearchStartRegDate("");
			}
			if(searchCondition.getSearchEndRegDate() == null){
				searchCondition.setSearchEndRegDate("");
			}
			
			if(searchCondition.getSearchStartRegDate() == "" && searchCondition.getSearchEndRegDate() != "" ){
				searchCondition.setSearchRegType("2");
			}
			if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() == "" ){
				searchCondition.setSearchRegType("1");
			}
			if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() != "" ){
				searchCondition.setSearchRegType("0");
			}
			
			String tempSearchConditionString = null;

			if (StringUtils.isEmpty(searchConditionString)) {
				tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
						"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", 
						"searchStartRegDate", "searchEndRegDate", "searchStartRegDate1", "searchEndRegDate1", "pagePerRecord", "searchRegType");
//				tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
//						"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord");
			} else {
				ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
				tempSearchConditionString = searchConditionString;
			}
			
			//사용자 정보등급세팅
			searchCondition.setInfoGrage(user.getInfoGrade());
			
			//게시판권한 세팅
			boolean isSystemAdmin = isSystemAdmin(user);

			int boardPermission = 0;
			if (isSystemAdmin) {
				boardPermission = 1;
			} 
			
			searchCondition.setUserId(user.getUserId());
			searchCondition.setAdmin(this.isSystemAdmin(user));

			String viewName = null;
			SearchResult<BoardItem> searchResult = null;
			searchResult = this.boardItemService.listBoardItemBySearchCondition(searchCondition);
			
			//게시물 맵정보를 가져온다.
			//Board board = new Board();
			
			Map<String, String> boardMap = new HashMap<String, String>();
			boardMap.put("isKnowhow", board.getIsKnowhow());
			boardMap.put("boardId", boardId);

			board.setParentList(this.boardAdminService.listParentBoard(boardMap));
			String kmsMapName = this.boardAdminService.getKmsMapName(boardId);
			
			List<Board> boardList = this.boardAdminService.boardList(board.getIsKnowhow());

			ModelAndView modelAndView = new ModelAndView(viewName)
					.addObject("boardCode", new BoardItemCode())
					.addObject("board", board)
					.addObject("user", user)
					.addObject("searchCondition", searchResult.getSearchCondition())
//					.addObject("searchConditionString", searchConditionString)
					.addObject("searchConditionString", tempSearchConditionString)
					.addObject("popupYn", popupYn)
					.addObject("boardPermission", boardPermission)
					.addObject("searchResult", searchResult)
					.addObject("pageGubun", pageGubun)
					.addObject("boardList", boardList)
					.addObject("kmsMapName", kmsMapName);
					
			return modelAndView;
		}

	/**
	 * 게시글 다른 파트에서 사용하게 위해서 boardId를 받지 않는 URL
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readBoardItemLinkView")
	public ModelAndView readBoardItemLinkView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "pageGubun", defaultValue = "boardItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		Boolean temPopupYn = Boolean.TRUE;

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		ModelAndView modelAndView = this.readBoardItemView(boardItem.getBoardId(), itemId, pageGubun, layoutType, null,
				temPopupYn, portletYn);

		return modelAndView;
	}

	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readBoardItemView")
	public ModelAndView readBoardItemView(@RequestParam(value ="boardId", required = false) String boardId,
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "pageGubun", defaultValue = "boardItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {
	
		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);
	
		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());
	
		board.setParentList(this.boardAdminService.listParentBoard(boardMap));	
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		
	
		//BoardPermission boardPermission = this.initPermission(user, board);
		/*if (!boardPermission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}*/
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);
	
		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
	
		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readBoardItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readBoardItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readBoardItemView";

		}
		
		ObjectMapper mapper = new ObjectMapper();
	
		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;
	
		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}
	
		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
	//	.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("pageGubun", pageGubun)
		.addObject("kmsMapName", kmsMapName)
		.addObject("isFavorite", isFavorite)
		.addObject("ecmrole", ecmrole);
	
		if (popupYn) {
			modelAndView.setViewName("collpack/kms/board/boardItem/readBoardItemPopupView");
		}
		
		return modelAndView;
	}

	/**
	 * 게시글 생성 화면 컨트롤 메서드
	 * 
	 * @param boardId 게시판 ID
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/createBoardItemView")
	public ModelAndView createBoardItemView(@RequestParam("boardId") String boardId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("boardItem") == null) {
			Date date = Calendar.getInstance().getTime();

			boardItem = new BoardItem();
			boardItem.setBoardId(boardId);
			boardItem.setStartDate(date);

			Properties boardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

			// #게시기간 단위 설정 (year,month,day)
			String itemPeriodType = boardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
			// #게시기간 설정
			int itemPeriod = Integer.valueOf(boardprop.getProperty("lightpack.board.boardItem.itemPeriod"));

			if ("year".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addYears(date, itemPeriod));
			} else if ("month".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addMonths(date, itemPeriod));
			} else if ("day".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(date, itemPeriod));
			} else {
				boardItem.setEndDate(DateUtils.addYears(date, 1));
			}

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}
		
		BoardItem caution = this.boardItemService.readCaution();
		
		boardItem.setCaution(caution.getCaution());

	//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board boardList = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", isKnowhow);
		boardMap.put("boardId", boardId);
	
		boardList.setParentList(this.boardAdminService.listParentBoard(boardMap));	
	
		Board board = new Board();
		String kmsMapName = "";
		if(!boardId.equals("0")){
			board = this.boardAdminService.readBoard(boardId);
			kmsMapName = this.boardAdminService.getKmsMapName(boardId);
		}
		
		
		BoardPermission permission = null;
		if(this.isSystemAdmin(user)){
			permission = new BoardPermission(true,true,true);
		}else{
			permission = new BoardPermission(false,true,true);
		}
		
		// 게시판 쓰기 권한 체크
		//permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		//if( !permission.getIsWritePermission()) {
		//	throw new IKEP4AuthorizedException();
		//}
		
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("user", user)
		.addObject("boardCode", new BoardCode())
//		.addObject("permission", this.initPermission(user, board))
		.addObject("permission", permission)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX)
		.addObject("board", board)
		.addObject("boardList", boardList)
		.addObject("isSystemAdmin", this.isSystemAdmin(user))
		.addObject("kmsMapName", kmsMapName)
		.addObject("ecmrole", ecmrole));
	}

	/**
	 * 게시글 수정 화면 컨트롤 메서드
	 * 
	 * @param itemId 수정대상 게시글 ID
	 * @return ModelAndView
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/updateBoardItemView")
	public ModelAndView updateBoardItemView(@RequestParam("boardId") String boardId,
			@RequestParam(value = "pageGubun", defaultValue = "boardItem") String pageGubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());

	//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board boardList = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", board.getIsKnowhow());
		boardMap.put("boardId", boardId);
		boardMap.put("itemId", boardItem.getItemId());
		
		boardList.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardId);
		
		BoardPermission permission = new BoardPermission(false,false,false);
		// 게시판 쓰기 권한 체크
		permission = initPermission(user, board);
		
		//게시글 쓰기 권한이 있는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		BoardItem boardItemList = new BoardItem();
		boardItemList.setBoardItemList(this.boardTaggingService.listRelatedBoardItemBySearchCondition(boardMap));

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("user", user)
		.addObject("board", board)
		.addObject("boardCode", new BoardCode())
		.addObject("permission", this.initPermission(user, board))
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("boardList", boardList)
		.addObject("boardItemList", boardItemList)
		.addObject("pageGubun", pageGubun)
		.addObject("isKnowhow", board.getIsKnowhow())
		.addObject("kmsMapName", kmsMapName)
		.addObject("ecmrole", ecmrole));
	}

	/**
	 * 답글 생성 화면 컨트롤 메서드
	 * 
	 * @param itemId 원본 게시글 ID
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/createReplyBoardItemView")
	public ModelAndView createReplyBoardItemView(@RequestParam("boardId") String boardId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("boardItem") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);
			boardItem.setFileLinkList(null);
			boardItem.setEditorFileLinkList(null);
			boardItem.setFileDataList(null);
			boardItem.setEditorFileDataList(null);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		BoardPermission permission = new BoardPermission(false,false,false);
		// 게시판 쓰기 권한 체크
		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("user", user)
		.addObject("board", board)
		.addObject("boardCode", new BoardCode())
		.addObject("permission", this.initPermission(user, board))
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX)
		.addObject("fileDataListJson", fileDataListJson));
	}
	
	@RequestMapping(value = "/listReaderView")
	public ModelAndView listReaderView(BoardItemReaderSearchCondition searchCondition,
			@RequestParam(value = "boardItemId", required = false) String boardItemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString){
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardItemId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}
		SearchResult<BoardItemReader> searchResult = this.boardItemService.listReaderBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", tempSearchConditionString)
		.addObject("boardCode", new BoardCode());
		
		return modelAndView;
	}	

	/**
	 * 게시글 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param boardItem 게시글 생성 화면에서 전달된 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createBoardItem")
	public ModelAndView createBoardItem(BoardItem boardItem,
			SessionStatus status) {
	
		User user = this.readUser();
		
		//사용자 정보등급세팅
		//boardItem.setInfoGrade(user.getInfoGrade());
		
		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		BoardPermission permission = new BoardPermission(false,false,false);
		
		// 게시판 쓰기 권한 체크

		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}				
		
//		if (result.hasErrors()) {
//			this.setErrorAttribute("boardItem", boardItem, result);
//			return new ModelAndView("forward:/collpack/kms/board/boardItem/createBoardItemView.do?boardId="
//					+ boardItem.getBoardId());
//		}

		boardItem.setPortalId(user.getPortalId());

		// 등록자 임시 지정 하기
		String userId = user.getUserId();
		String userName = user.getUserName();		
		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);
		
		boardItem.setItemType("0");
		if(board.getIsKnowhow().equals("0")){
			if(boardItem.getStatus()==1){
				boardItem.setStatus(2);
				boardItem.setExpertId(boardItem.getAssessorId());
				boardItem.setExpertName(boardItem.getAssessorName());
				String title = userName + " 님이 등록한 지식에 대해 전문가 평가를 요청합니다.";
				String content = "<html><body>" + userName + " 님이 등록한 지식에 대해 전문가 평가를 요청합니다.<br/><br/>1. 평가방법<BR>&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; 지식광장 내 전문가 평가 부분 클릭 후 등록된 지식에 대해 중요도/활용도 항목에 각각 높음/보통/낮음 체크<BR><BR>2. 평가 기한<BR>&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; 2일 이내 평가 요청 드리며, 2일 경과 시 관리자 평가 후 오픈</body></html>";
				
				Mail mail = new Mail();
				Map dataMap = new HashMap();
				User userInfo = new User();
				List<HashMap> list = new ArrayList<HashMap>();

				userInfo = userService.read(boardItem.getAssessorId());
				if (!StringUtil.isEmpty(userInfo.getMail()) && !StringUtil.isEmpty(userInfo.getUserName())) {
					// 수신자
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", userInfo.getMail());
					map.put("name", userInfo.getUserName());
					list.add(map);
				}

				mail.setToEmailList(list);

				// 제목
				mail.setTitle(title);
				// 내용
				mail.setContent(content);
				// 메일형식(txt,html,template)
				mail.setMailType(MailConstant.MAIL_TYPE_HTML);

				// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
				String returnMsg = mailSendService.sendMail(mail, dataMap, user);
			}
		}
		if(board.getIsKnowhow().equals("3")){
			boardItem.setAssessorId(user.getUserId());
			boardItem.setAssessorName(user.getUserName());
		}
		String itemId = this.boardItemService.createBoardItem(boardItem, user);

		status.setComplete();
		
		if(boardItem.getRegisterId()!=null && !boardItem.getRegisterId().equals("")) {
			// 등록자 임시 지정 하기
			user.setUserId(userId);
			user.setUserName(userName);
		}
		String viewName = null;
		if (boardItem.getPopupYn()) {
			viewName = "redirect:/collpack/kms/board/boardItem/listBoardItemView.do";

		} else {
			
			if(boardItem.getStatus() == 0){
				viewName = "redirect:/collpack/kms/board/boardItem/readTemporaryItemView.do";
			}else{
				viewName = "redirect:/collpack/kms/board/boardItem/readTemporaryItemView.do";
			}
		}

		return new ModelAndView(viewName)
		.addObject("boardId", boardItem.getBoardId())
		.addObject("itemId", itemId)
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("popupYn", boardItem.getPopupYn())
		.addObject("gubun", boardItem.getStatus());
	}

	/**
	 * 답글 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param boardItem 답변 생성 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param boardItem the board item
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createReplyBoardItem")
	public ModelAndView createReplyBoardItem(@ValidEx(groups = { CreateReplyItemGroup.class }) BoardItem boardItem,
			BindingResult result, SessionStatus status) {

		User user = this.readUser();
		
		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		BoardPermission permission = new BoardPermission(false,false,false);
		
		// 게시판 쓰기 권한 체크

		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/collpack/kms/board/boardItem/createReplyBoardItemView.do?itemId="
					+ boardItem.getItemParentId());
		}


		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		BoardItem parent = this.boardItemService.readBoardItemMasterInfo(boardItem.getItemParentId());

		boardItem.setStartDate(parent.getStartDate());
		boardItem.setEndDate(parent.getEndDate());
		boardItem.setItemDisplay(parent.getItemDisplay());
		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);

		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		boardItem.setItemType("0");
		String itemId = this.boardItemService.createReplyBoardItem(boardItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/board/boardItem/readBoardItemView.do")
				.addObject("boardId", boardItem.getBoardId()).addObject("itemId", itemId)
				.addObject("searchConditionString", boardItem.getSearchConditionString())
				.addObject("popupYn", boardItem.getPopupYn());
	}

	/**
	 * 게시글 수정 처리 동기 컨트롤 메서드
	 * 
	 * @param boardItem 게시글 수정 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateBoardItem")
	public ModelAndView updateBoardItem(@ValidEx(groups = { ModifyItemGroup.class }) BoardItem boardItem,
			BindingResult result, SessionStatus status) {
		
		User user = this.readUser();
		
		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		BoardPermission permission = new BoardPermission(false,false,false);
		
		// 게시판 쓰기 권한 체크

		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/collpack/kms/board/boardItem/updateBoardItemView.do?itemId="
					+ boardItem.getItemId());
		}

		// 등록자 임시 지정 하기
		String userId = user.getUserId();
		String userName = user.getUserName();
//		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		
		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		boardItem.setItemType("0");
		this.boardItemService.updateBoardItem(boardItem, user);

		status.setComplete();
		
		// 등록자 임시 지정 하기
		if(boardItem.getRegisterId()!=null && !boardItem.getRegisterId().equals("")) {
			user.setUserId(userId);
			user.setUserName(userName);
		}
		
		String viewName = "redirect:/collpack/kms/board/boardItem/readBoardItemView.do";
		
		String pageGubun = boardItem.getPageGubun();
		if (pageGubun.equals("searchItem")) {
			viewName = "redirect:/collpack/kms/board/boardItem/readSearchItemView.do";
		}
		if(pageGubun.equals("latestItem")) {
			viewName = "redirect:/collpack/kms/board/boardItem/readLatestItemView.do";
		}
		if(pageGubun.equals("excellenceItem")) {
			viewName = "redirect:/collpack/kms/board/boardItem/readExcellenceItemView.do";
		}
		
		return new ModelAndView(viewName)
		.addObject("boardId", boardItem.getBoardId())
		.addObject("itemId", boardItem.getItemId())
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("isKnowhow", board.getIsKnowhow())
		.addObject("pageGubun", pageGubun)
		.addObject("popupYn", boardItem.getPopupYn());
	}

	/**
	 * 게시글 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param itemId 삭제 대상 게시글 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/userDeleteBoardItem")
	public ModelAndView userDeleteBoardItem(@RequestParam("boardId") String boardId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {

		User user = this.readUser();

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		this.boardItemService.userDeleteBoardItem(boardItem);

		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/collpack/kms/board/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/collpack/kms/board/boardItem/listBoardItemView.do";
		}

		return new ModelAndView(viewName).addObject("boardId", boardItem.getBoardId())
				.addObject("isKnowhow", isKnowhow)
				.addObject("searchConditionString", searchConditionString)
				.addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 일괄삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param itemIds 삭제대상 게시글 ID 배열
	 */
	@RequestMapping(value = "/adminMultiDeleteBoardItem")
	public @ResponseBody
	void adminMultiDeleteBoardItem(@RequestParam("boardItemIds") List<String> itemIds) {

		try {
			this.boardItemService.adminMultiDeleteBoardItem(itemIds);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}

	/**
	 * 게시글 관리자모드 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param itemId the item id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/adminDeleteBoardItem")
	public ModelAndView adminDeleteBoardItem(@RequestParam("boardId") String boardId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {

		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		this.boardItemService.adminDeleteBoardItem(boardItem);
//		this.boardItemService.adminDeleteBoardItem(boardItem, Boolean.FALSE);

		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/collpack/kms/board/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/collpack/kms/board/boardItem/listBoardItemView.do";
		}

		return new ModelAndView(viewName).addObject("boardId", boardItem.getBoardId())
				.addObject("searchConditionString", searchConditionString)
				.addObject("isKnowhow", isKnowhow)
				.addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 삭제의 경우 팝업 모드일 경우 삭제 후 부모 창에 있는 게시글목록을 갱신해야 하는데 사용하는 URL
	 * 
	 * @param itemId the item id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/boardItemResult")
	public ModelAndView boardItemResult(@RequestParam("boardId") String boardId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn) {

		return new ModelAndView().addObject("boardId", boardId)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 추천 비동기 컨트롤 메서드
	 * 
	 * @param itemId 추천 게시글 ID
	 * @return the integer
	 */
	@RequestMapping(value = "/updateRecommendCount")
	public @ResponseBody
	Integer updateRecommendCount(@RequestParam("itemId") String itemId) {
		Integer currentRecommendCount = null;

		try {
			User user = (User) this.getRequestAttribute("ikep.user");

			// 게시물 추천을 한다.
			BoardRecommend boardRecommend = new BoardRecommend();

			boardRecommend.setItemId(itemId);
			boardRecommend.setRegisterId(user.getUserId());
			if(user.getJobClassCode().equals("11")){
				boardRecommend.setScore("3");
			}else if(user.getJobClassCode().equals("21")){
				boardRecommend.setScore("2");
			}else{
				boardRecommend.setScore("1");
			}
			boardRecommend.setFlag("2");

			// 이미 존재하면 -1를 리턴한다.
			if (this.boardItemService.exsitRecommend(boardRecommend)) {
				currentRecommendCount = -1;
			} else {
				
				this.boardItemService.updateRecommendCount(boardRecommend);
				this.boardItemService.updateScore(boardRecommend);

				currentRecommendCount = this.boardItemService.readBoardItem(itemId).getRecommendCount();
			}
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return currentRecommendCount;

	}

	/**
	 * 개인화 설정 레이아웃 모드 설정
	 * 
	 * @param boardId 게시판 ID
	 * @param layout 레이아웃
	 */
	@RequestMapping(value = "/updateUserConfigLayout")
	public @ResponseBody
	void updateUserConfigLayout(@RequestParam("boardId") String boardId, @RequestParam("layoutType") String layoutType) {

		User user = this.readUser();

		Portal portal = this.readPortal();

		try {
			// 개인화 설정 정보를 불러온다.
			UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
					"KMS", portal.getPortalId());

			if (userConfig == null) {
				userConfig = new UserConfig();
				// 개인화 설정 정보를 저장한다.
				userConfig.setLayout(layoutType);
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId("KMS");

				this.userConfigService.createUserConfig(userConfig);
			} else {
				userConfig.setLayout(layoutType);
				this.userConfigService.updateUserConfig(userConfig);

			}

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}

	/**
	 * 개인화 설정 레이아웃 모드 설정
	 * 
	 * @param boardId 게시판 ID
	 * @param layout 레이아웃
	 */
//	@RequestMapping(value = "/listRecentBoardItem")
//	public @ResponseBody
//	List<BoardItem> updateUserConfigLayout(@RequestParam("boardId") String boardId, @RequestParam("count") Integer count) {
//		List<BoardItem> boardItemList = null;
//
//		try {
//			boardItemList = this.boardItemService.listRecentBoardItem(boardId, count);
//
//		} catch (Exception exception) {
//			throw new IKEP4AjaxException("code", exception);
//
//		}
//
//		return boardItemList;
//	}

	/* 5/17 WS 추가 */
	/**
	 * 게시물 이동처리
	 */
	@RequestMapping(value = "/moveBoardItem")
	public @ResponseBody
	void moveBoardItem(@RequestParam("orgBoardId") String orgBoardId,
			@RequestParam("targetBoardId") String targetBoardId, @RequestParam("itemIds") String[] itemIds) {
		User user = (User) getRequestAttribute("ikep.user");

		try {
			this.boardItemService.moveBoardItem(orgBoardId, targetBoardId, itemIds, user);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}

	/**
	 * 게시물 이동시 게시판 Tree 팝업
	 * 
	 * @param workspaceId
	 * @param orgBoardId
	 * @return
	 */
	@RequestMapping(value = "/viewBoardTree")
	public ModelAndView viewBoardTree(@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam("orgBoardId") String orgBoardId) {

		String boardTreeJson = boardTreeJson("0");
		
		String boardTreeJson1 = boardTreeJson("1");
		
		String boardTreeJson2 = boardTreeJson("3");

		ModelAndView model = new ModelAndView();
		model.setViewName("collpack/kms/board/boardItem/viewBoardTree");
		model.addObject("isKnowhow", isKnowhow);
		model.addObject("orgBoardId", orgBoardId);
		model.addObject("boardTreeJson", boardTreeJson);
		model.addObject("boardTreeJson1", boardTreeJson1);
		model.addObject("boardTreeJson2", boardTreeJson2);

		return model;

	}
	
	/**
	 * 지식맵 Tree 팝업
	 * 
	 * @param workspaceId
	 * @param orgBoardId
	 * @return
	 */
	@RequestMapping(value = "/viewBoardTreePopup")
	public ModelAndView viewBoardTreePopup(@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam("orgBoardId") String orgBoardId) {

//		String boardTreeJson = boardTreeJson(isKnowhow);
		
//		String boardTreeJson1 = boardTreeJson("1");

		ModelAndView model = new ModelAndView();
		model.setViewName("collpack/kms/board/boardItem/viewBoardTreePopup");
		model.addObject("isKnowhow", isKnowhow);
//		model.addObject("orgBoardId", orgBoardId);
//		model.addObject("boardTreeJson", boardTreeJson);
//		model.addObject("boardTreeJson1", boardTreeJson1);

		return model;

	}

	/**
	 * 게시판 Tree Json
	 * 
	 * @param workspaceId
	 * @return
	 */
	private String boardTreeJson(String isKnowhow) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("isKnowhow", isKnowhow);
		map.put("boardRootId", DEFAULT_BOARD_ROOT_ID);

		List<Board> boardList = boardAdminService.listByBoardRootId(map);
		return TreeMaker.init(boardList, "boardId", "boardParentId", "boardName").create().toJsonString();

	}

	/**
	 * 메일 뷰 개수 업데이트
	 * 
	 * @param itemId Idea ID
	 * @return
	 */
	@RequestMapping("/updateMailCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateMailCount(@RequestParam("itemId") String itemId) {

		try {
			boardItemService.updateMailCount(itemId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 블로그 뷰 개수 업데이트
	 * 
	 * @param itemId Idea ID
	 * @return
	 */
	@RequestMapping("/updateMblogCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateMblogCount(@RequestParam("itemId") String itemId) {

		try {
			boardItemService.updateMblogCount(itemId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 최신 Board 게시물 리스트
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listLatestItemView")
	public ModelAndView latestListBoardItemView(@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "latestItem") String pageGubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setIsKnowhow(isKnowhow);
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.latestListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult)
				.addObject("isKnowhow", isKnowhow)
				.addObject("pageGubun", pageGubun);

		return modelAndView;
	}
	
	/**
	 * 우수정보 Board 게시물 리스트
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listExcellenceItemView")
	public ModelAndView excellenceListBoardItemView(@RequestParam("isKnowhow") String isKnowhow, BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setIsKnowhow(isKnowhow);
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.excellenceListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult)
				.addObject("isKnowhow", isKnowhow);

		return modelAndView;
	}

	/**
	 * 최신 Board 게시물 읽기
	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readLatestItemView")
	public ModelAndView readLatestItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "latestItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 board map정보를 가져온다.
//		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		
		

		//BoardPermission boardPermission = this.initPermission(user, board);
		/*if (!boardPermission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}*/
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readLatestItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readLatestItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readLatestItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}
		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("pageGubun", pageGubun)
		.addObject("isKnowhow", isKnowhow)
		.addObject("isFavorite", isFavorite)
		.addObject("ecmrole", ecmrole);

		if (popupYn) {
			//modelAndView.setViewName("collpack/collaboration/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readAssessItemView")
	public ModelAndView readAssessItemView(@RequestParam(value ="boardId", required = false) String boardId,
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "pageGubun", defaultValue = "boardItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {
	
		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);
	
		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());
	
		board.setParentList(this.boardAdminService.listParentBoard(boardMap));	
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		
	
		//BoardPermission boardPermission = this.initPermission(user, board);
		/*if (!boardPermission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}*/
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);
	
		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
	
		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readBoardItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readBoardItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readAssessItemView";

		}
		
		ObjectMapper mapper = new ObjectMapper();
	
		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;
	
		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
	
		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
	//	.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("pageGubun", pageGubun)
		.addObject("kmsMapName", kmsMapName)
		.addObject("isFavorite", isFavorite);
	
		if (popupYn) {
			modelAndView.setViewName("collpack/kms/board/boardItem/readBoardItemPopupView");
		}
		
		return modelAndView;
	}
	
	/**
	 * 우수정보 게시물 읽기
	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readExcellenceItemView")
	public ModelAndView readExcellenceItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "excellenceItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 맵정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readExcellenceItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readExcellenceItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readExcellenceItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("pageGubun", pageGubun)
		.addObject("isKnowhow", isKnowhow)
		.addObject("isFavorite", isFavorite)
		.addObject("ecmrole", ecmrole);

		if (popupYn) {
//			modelAndView.setViewName("collpack/collaboration/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	/**
	 * 활용정보 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupRef")
	public ModelAndView popupRef(String name, String column, String isMulti) {

		ModelAndView mav = new ModelAndView("collpack/kms/board/boardItem/popupRef");

		User user = readUser();
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		
		searchCondition.setSearchColumn(column);
		searchCondition.setSearchWord(name);
		
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		
		mav.addObject("workPlaceList", workPlaceList);
		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isMulti", isMulti);
		
		return mav;
	}
	
	@RequestMapping("/getPopupRefList")
	public ModelAndView getPopupRefList(BoardItemSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("collpack/kms/board/boardItem/popupRefList");
		
		User user = readUser();

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		if(searchCondition.getSearchStartRegDate() == "" && searchCondition.getSearchEndRegDate() != "" ){
			searchCondition.setSearchRegType("2");
		}
		if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() == "" ){
			searchCondition.setSearchRegType("1");
		}
		if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() != "" ){
			searchCondition.setSearchRegType("0");
		}
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user));

		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.refListBySearchCondition(searchCondition);
		
		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isMulti", isMulti);

		return mav;
	}
	
	/**
	 * E등급지식 Board 게시물 리스트
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listEinfogradeItemView")
	public ModelAndView einfogradeListBoardItemView(@RequestParam("gubun") String gubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		searchCondition.setGubun(gubun);
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		
		//등록자ID세팅
		searchCondition.setUserId(user.getUserId());

		//관리자여부 세팅
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;
		
		if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
			searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
			searchCondition.setSearchMonth(DateUtil.getToday("MM"));
		}

		searchResult = this.boardItemService.einfogradeListBySearchCondition(searchCondition);
		
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		List<AdminTeamLeader> teamList = null;
		
		if(searchCondition.getWorkPlaceName() !=null ){
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult)
				.addObject("workPlaceList", workPlaceList)
				.addObject("teamList", teamList)
				.addObject("gubun", gubun);

		return modelAndView;
	}
	
	/**
	 * 전문가 Board 게시물 리스트
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listAssessItemView")
	public ModelAndView assessListBoardItemView(@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		
		//전문가ID세팅
		searchCondition.setAssessorId(user.getUserId());

		//관리자여부 세팅
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.assessListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult);

		return modelAndView;
	}
	
	/**
	 * 임시저장 게시물 읽기
	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readTemporaryItemView")
	public ModelAndView readTemporaryItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("gubun") String gubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 맵정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readTemporaryItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readTemporaryItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readTemporaryItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
//		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("gubun", gubun);
//		.addObject("isFavorite", isFavorite);

		if (popupYn) {
			modelAndView.setViewName("collpack/collaboration/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/readKeyInfoItemView")
	public ModelAndView readKeyInfoItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("gubun") String gubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 맵정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		viewName = "collpack/kms/board/boardItem/readKeyInfoItemView";

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
//		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("gubun", gubun);
//		.addObject("isFavorite", isFavorite);

		if (popupYn) {
			modelAndView.setViewName("collpack/collaboration/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	/**
	 * 임시저장 수정 화면 컨트롤 메서드
	 * 
	 * @param itemId 수정대상 게시글 ID
	 * @return ModelAndView
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/updateTemporaryItemView")
	public ModelAndView updateTemporaryItemView(@RequestParam("boardId") String boardId,
			@RequestParam("gubun") String gubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());

	//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board boardList = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", board.getIsKnowhow());
		boardMap.put("boardId", boardId);
		boardMap.put("itemId", boardItem.getItemId());
		
		boardList.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardId);
		
		
		BoardPermission permission = new BoardPermission(false,false,false);
		// 게시판 쓰기 권한 체크
		permission = initPermission(user, board);
		
		//게시글 쓰기 권한이 있는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		BoardItem boardItemList = new BoardItem();
		boardItemList.setBoardItemList(this.boardTaggingService.listRelatedBoardItemBySearchCondition(boardMap));

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		BoardItem caution = this.boardItemService.readCaution();
		
		boardItem.setCaution(caution.getCaution());
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}


		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("user", user)
		.addObject("board", board)
		.addObject("boardCode", new BoardCode())
		.addObject("permission", this.initPermission(user, board))
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("boardList", boardList)
		.addObject("boardItemList", boardItemList)
		.addObject("kmsMapName", kmsMapName)
		.addObject("isSystemAdmin", this.isSystemAdmin(user))
		.addObject("gubun", gubun)
		.addObject("ecmrole", ecmrole));
	}

	/**
	 * 전문가 수정 화면 컨트롤 메서드
	 * 
	 * @param itemId 수정대상 게시글 ID
	 * @return ModelAndView
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/updateAssessItemView")
	public ModelAndView updateAssessItemView(@RequestParam("boardId") String boardId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "pageGubun", defaultValue = "assessItem") String pageGubun,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());

	//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board boardList = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", board.getIsKnowhow());
		boardMap.put("boardId", boardId);
		boardMap.put("itemId", boardItem.getItemId());
		
		boardList.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardId);
		
		BoardPermission permission = new BoardPermission(false,false,false);
		// 게시판 쓰기 권한 체크
		permission = initPermission(user, board);
		
		//게시글 쓰기 권한이 있는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		BoardItem boardItemList = new BoardItem();
		boardItemList.setBoardItemList(this.boardTaggingService.listRelatedBoardItemBySearchCondition(boardMap));
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItemList.setBoardAssessItemList(listAssessItem);
		
		boardItem = getAssessItem(boardItem, listAssessItem);
		

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("user", user)
		.addObject("board", board)
		.addObject("boardCode", new BoardCode())
		.addObject("permission", this.initPermission(user, board))
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("boardList", boardList)
		.addObject("boardItemList", boardItemList)
		.addObject("pageGubun", pageGubun)
		.addObject("kmsMapName", kmsMapName)
		.addObject("ecmrole", ecmrole));
	}
	
	/**
	 * 전문가 설정
	 */
	@RequestMapping(value = "/assessMove")
	public @ResponseBody
	void assessMove( String itemId,
			 String status,
			 String assessorId,
			 String assessorName) {
		
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("itemId", itemId);
		boardMap.put("status", status);
		boardMap.put("assessorId", assessorId);
		boardMap.put("assessorName",  assessorName);
		boardMap.put("updaterId", user.getUserId());
		boardMap.put("updaterName", user.getUpdaterName());

		try {
			this.boardItemService.assessMove(boardMap);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);
		
		String title = boardItem.getRegisterName() + " 님이 등록한 지식에 대해 전문가 평가를 요청합니다.";
		String content = "<html><body>" + boardItem.getRegisterName() + " 님이 등록한 지식에 대해 전문가 평가를 요청합니다.<br/><br/>1. 평가방법<BR>&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; 지식광장 내 전문가 평가 부분 클릭 후 등록된 지식에 대해 중요도/활용도 항목에 각각 높음/보통/낮음 체크<BR><BR>2. 평가 기한<BR>&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; 2일 이내 평가 요청 드리며, 2일 경과 시 관리자 평가 후 오픈</body></html>";
		
		Mail mail = new Mail();
		Map dataMap = new HashMap();
		User userInfo = new User();
		List<HashMap> list = new ArrayList<HashMap>();

		userInfo = userService.read(assessorId);
		if (!StringUtil.isEmpty(userInfo.getMail()) && !StringUtil.isEmpty(userInfo.getUserName())) {
			// 수신자
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("email", userInfo.getMail());
			map.put("name", userInfo.getUserName());
			list.add(map);
		}

		mail.setToEmailList(list);

		// 제목
		mail.setTitle(title);
		// 내용
		mail.setContent(content);
		// 메일형식(txt,html,template)
		mail.setMailType(MailConstant.MAIL_TYPE_HTML);

		// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
		String returnMsg = mailSendService.sendMail(mail, dataMap, user);
		
	}
	
	private BoardItem getAssessItem(BoardItem boardItem, List<BoardAssessItem> listAssessItem){
		
		String[] itemIds 		= new String[listAssessItem.size()];
		int[]assessItems 		= new int[listAssessItem.size()];
		String[] assessNames 	= new String[listAssessItem.size()];
		int[] assessMarks 		= new int[listAssessItem.size()];
		int[] itemDisplays 		= new int[listAssessItem.size()];
		
		for(int i=0 ; i < listAssessItem.size(); i++ ){
			itemIds[i] 		= listAssessItem.get(i).getItemId();
			assessItems[i] 	= listAssessItem.get(i).getAssessItem();
			assessNames[i] 	= listAssessItem.get(i).getAssessName();
			assessMarks[i] 	= listAssessItem.get(i).getAssessMark();
			itemDisplays[i] = listAssessItem.get(i).getItemDisplay();
		}
		boardItem.setItemIds(itemIds);
		boardItem.setAssessItems(assessItems);
		boardItem.setAssessNames(assessNames);
		boardItem.setAssessMarks(assessMarks);
		boardItem.setItemDisplays(itemDisplays);
		
		return boardItem;
	}

	/**
	 * 게시글 수정 처리 동기 컨트롤 메서드
	 * 
	 * @param boardItem 게시글 수정 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateTemporaryItem")
	public ModelAndView updateTemporaryItem(@ValidEx(groups = { ModifyItemGroup.class }) BoardItem boardItem,
			BindingResult result, SessionStatus status) {
		
		User user = this.readUser();
		
		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		boardItem.setIsKnowhow(Integer.parseInt(board.getIsKnowhow()));
		
		BoardPermission permission = new BoardPermission(false,false,false);
		
		// 게시판 쓰기 권한 체크

		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/collpack/kms/board/boardItem/updateTemporaryItemView.do?itemId="
					+ boardItem.getItemId());
		}

		// 등록자 임시 지정 하기
		String userId = user.getUserId();
		String userName = user.getUserName();
//		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		
		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		boardItem.setItemType("0");
		
		if(board.getIsKnowhow().equals("0")){
			if(boardItem.getStatus()==1){
				boardItem.setStatus(2);
				boardItem.setExpertId(boardItem.getAssessorId());
				boardItem.setExpertName(boardItem.getAssessorName());
				String title = userName + " 님이 등록한 지식에 대해 전문가 평가를 요청합니다.";
				String content = "<html><body>" + userName + " 님이 등록한 지식에 대해 전문가 평가를 요청합니다.<br/><br/>1. 평가방법<BR>&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; 지식광장 내 전문가 평가 부분 클릭 후 등록된 지식에 대해 중요도/활용도 항목에 각각 높음/보통/낮음 체크<BR><BR>2. 평가 기한<BR>&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; 2일 이내 평가 요청 드리며, 2일 경과 시 관리자 평가 후 오픈</body></html>";
				
				Mail mail = new Mail();
				Map dataMap = new HashMap();
				User userInfo = new User();
				List<HashMap> list = new ArrayList<HashMap>();

				userInfo = userService.read(boardItem.getAssessorId());
				if (!StringUtil.isEmpty(userInfo.getMail()) && !StringUtil.isEmpty(userInfo.getUserName())) {
					// 수신자
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", userInfo.getMail());
					map.put("name", userInfo.getUserName());
					list.add(map);
				}

				mail.setToEmailList(list);

				// 제목
				mail.setTitle(title);
				// 내용
				mail.setContent(content);
				// 메일형식(txt,html,template)
				mail.setMailType(MailConstant.MAIL_TYPE_HTML);

				// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
				String returnMsg = mailSendService.sendMail(mail, dataMap, user);
			}
		}
		
		if(board.getIsKnowhow().equals("3")){
			boardItem.setAssessorId(user.getUserId());
			boardItem.setAssessorName(user.getUserName());
		}
		this.boardItemService.updateTempBoardItem(boardItem, user);

		status.setComplete();
		
		// 등록자 임시 지정 하기
		if(boardItem.getRegisterId()!=null && !boardItem.getRegisterId().equals("")) {
			user.setUserId(userId);
			user.setUserName(userName);
		}
		
		String viewName = null;
		
		if(boardItem.getStatus() == 0){
			viewName = "redirect:/collpack/kms/board/boardItem/readTemporaryItemView.do";
		}else{
			viewName = "redirect:/collpack/kms/board/boardItem/readTemporaryItemView.do";
		}
		
		return new ModelAndView(viewName)
		.addObject("boardId", boardItem.getBoardId())
		.addObject("itemId", boardItem.getItemId())
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("popupYn", boardItem.getPopupYn())
		.addObject("gubun", boardItem.getStatus());
	}
	
	/**
	 * 관리자 수정 처리 동기 컨트롤 메서드
	 * 
	 * @param boardItem 게시글 수정 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateAssessItem")
	public ModelAndView updateAssessItem(@ValidEx(groups = { ModifyItemGroup.class }) BoardItem boardItem,
			BindingResult result, SessionStatus status) {
		
		User user = this.readUser();
		
		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		boardItem.setIsKnowhow(Integer.parseInt(board.getIsKnowhow()));
		
		BoardPermission permission = new BoardPermission(false,false,false);
		
		// 게시판 쓰기 권한 체크

		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/collpack/kms/board/boardItem/updateAssessItemView.do?itemId="
					+ boardItem.getItemId());
		}

		// 등록자 임시 지정 하기
		String userId = user.getUserId();
		String userName = user.getUserName();
//		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		
		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		boardItem.setItemType("0");
		
		//전문가평가
		if(boardItem.getStatus() > 2){
			if(userId.equals(boardItem.getExpertId()) && boardItem.getStatus() < 5){
				boardItem.setStatus(4);
			}
			//boardItem.setAssessorId(userId);
			//boardItem.setAssessorName(userName);
		}
		
		this.boardItemService.updateBoardItem(boardItem, user, true);
		
		status.setComplete();
		
		// 등록자 임시 지정 하기
		if(boardItem.getRegisterId()!=null && !boardItem.getRegisterId().equals("")) {
			user.setUserId(userId);
			user.setUserName(userName);
		}
		
		if( (Integer.parseInt(boardItem.getOriStatus()) < 3) && ( boardItem.getStatus() > 2) ){
			// 완료시 메시지처리
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String prefix	= "message.collpack.kms.common.messenger.";
			String title 	= messageSource.getMessage( prefix + "alarmTitle", new String[]{user.getUserName()}, Locale.getDefault());
			String grade = "";
			if(boardItem.getInfoGrade().equals("A")){
				grade = "보안";
			}else if(boardItem.getInfoGrade().equals("B")){
				grade = "보안";
			}else if(boardItem.getInfoGrade().equals("C")){
				grade = "공유";
			}else if(boardItem.getInfoGrade().equals("D")){
				grade = "공유";
			}else{
				grade = "미공유";
			}
			String contents = messageSource.getMessage( prefix + "alarmContents", new String[]{boardItem.getOriRegistDate(), boardItem.getTitle(), grade, boardItem.getMark().toString()}, Locale.getDefault());
			String contentsForMobile = messageSource.getMessage( prefix + "alarmContents3", new String[]{boardItem.getOriRegistDate(), boardItem.getTitle(), grade, boardItem.getMark().toString()}, Locale.getDefault());

			//메시지 보내기
			atmc.addMessage(boardItem.getRegisterId(), user.getUserName(), contents.toString(), "", title,"smspop");
			atmc.send();
			//LOCATION: push 메세지 보내기.
			try {
				//sendPushMessage(boardItem.getRegisterId(), contentsForMobile);
			} catch (Exception e) {
				log.error("Error occurs during send push messages : "+e.getMessage());
			}
			
		}else if( (Integer.parseInt(boardItem.getOriStatus()) > 1) && ( boardItem.getStatus() < 2) ){
			// 완료시 메시지처리
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String prefix	= "message.collpack.kms.common.messenger.";
			String title 	= messageSource.getMessage( prefix + "alarmTitle", new String[]{user.getUserName()}, Locale.getDefault());
			String contents = messageSource.getMessage( prefix + "alarmContents2", new String[]{boardItem.getOriRegistDate(), boardItem.getTitle()}, Locale.getDefault());
			String contentsForMobile = messageSource.getMessage( prefix + "alarmContents4", new String[]{boardItem.getOriRegistDate(), boardItem.getTitle()}, Locale.getDefault());

			//메시지 보내기
			atmc.addMessage(boardItem.getRegisterId(), user.getUserName(), contents.toString(), "", title,"smspop");
			//atmc.send(); 취소메시지 안보내도록 주석
			//LOCATION: push 메세지 보내기.
			try {
				//sendPushMessage(boardItem.getRegisterId(), contentsForMobile);
			} catch (Exception e) {
				log.error("Error occurs during send push messages : "+e.getMessage());
			}
		}
		String viewName = null;
		
		if(boardItem.getPageGubun().equals("boardItem")){
			viewName = "redirect:/collpack/kms/board/boardItem/readBoardItemView.do";		
		}else if(boardItem.getPageGubun().equals("searchItem")){
			viewName = "redirect:/collpack/kms/board/boardItem/readSearchItemView.do";
		}else{
			//전문가확인
			if(boardItem.getStatus() == 4){
				
				boolean isSystemAdmin = boardItemService.isAssessor(userId);
				if(isSystemAdmin){
					viewName = "redirect:/collpack/kms/board/boardItem/listAssessItemView.do";
				}else{
					viewName = "redirect:/collpack/kms/main/KmsBody.do";
				}
			}else if(boardItem.getStatus() == 1){
				viewName = "redirect:/collpack/kms/board/boardItem/readAssessItemView.do";
			}else{
				viewName = "redirect:/collpack/kms/board/boardItem/listAssessItemView.do";
			}			
		}
		
		return new ModelAndView(viewName)
		.addObject("boardId", boardItem.getBoardId())
		.addObject("itemId", boardItem.getItemId())
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("pageGubun", boardItem.getPageGubun())
		.addObject("popupYn", boardItem.getPopupYn())
		.addObject("isKnowhow", boardItem.getIsKnowhow());
	}

	@RequestMapping(value = "/getKmsMapName")
	public @ResponseBody
	String getKmsMapName(@RequestParam("boardId") String boardId) {

		String kmsMapName = this.boardAdminService.getKmsMapName(boardId);
		
		return kmsMapName;
	}
	
	/**
	 * 지식조회 Board 게시물 리스트
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listSearchItemView")
	public ModelAndView searchListBoardItemView(@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "searchItem") String pageGubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", 
					"searchStartRegDate", "searchEndRegDate", "searchStartRegDate1", "searchEndRegDate1", "pagePerRecord");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		if(searchCondition.getSearchStartRegDate() == null){
			searchCondition.setSearchStartRegDate("");
		}
		if(searchCondition.getSearchEndRegDate() == null){
			searchCondition.setSearchEndRegDate("");
		}
		
		if(searchCondition.getSearchStartRegDate() == "" && searchCondition.getSearchEndRegDate() != "" ){
			searchCondition.setSearchRegType("2");
		}
		if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() == "" ){
			searchCondition.setSearchRegType("1");
		}
		if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() != "" ){
			searchCondition.setSearchRegType("0");
		}
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setIsKnowhow(isKnowhow);
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;
		
		boolean isSystemAdmin = isSystemAdmin(user);

		int boardPermission = 0;
		if (isSystemAdmin) {
			boardPermission = 1;
		} 

		searchResult = this.boardItemService.searchListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult)
				.addObject("isKnowhow", isKnowhow)
				.addObject("pageGubun", pageGubun)
				.addObject("boardPermission", boardPermission);

		return modelAndView;
	}
	
	/**
	 * 지식조회 게시물 읽기
	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readSearchItemView")
	public ModelAndView readSearchItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "searchItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 맵정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readSearchItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readSearchItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readSearchItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("pageGubun", pageGubun)
		.addObject("isKnowhow", isKnowhow)
		.addObject("isFavorite", isFavorite);

		if (popupYn) {
			modelAndView.setViewName("collpack/collaboration/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/directReadItemView")
	public ModelAndView directReadItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("isKnowhow") String isKnowhow,
			@RequestParam(value = "pageGubun", defaultValue = "searchItem") String pageGubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "suserId", defaultValue = "false", required = false) String suserId,
			HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		Portal portal = null;
		
		portal = portalService.readPortalDefault();
		User user =   userService.read(suserId);
		
		//HttpSession session = request.getSession();
		
		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());
		
		
		authenticationSuccess(request, suserId);

		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 맵정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		viewName = "collpack/kms/board/boardItem/directReadItemView";

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("pageGubun", pageGubun)
		.addObject("isKnowhow", isKnowhow)
		.addObject("isFavorite", isFavorite);

		return modelAndView;
	}
	
	/**
	 * 게시글 인쇄 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readBoardItemPrint")
	public ModelAndView readBoardItemPrint(@RequestParam("itemId") String itemId)
			throws JsonGenerationException, JsonMappingException, IOException {
	
		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItemPrint(itemId);
	
		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 board map정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());
	
		board.setParentList(this.boardAdminService.listParentBoard(boardMap));	
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
	
		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
	
		// 이동할 뷰를 선택한다.
		String viewName = "collpack/kms/board/boardItem/readBoardItemPrint";
		
		ObjectMapper mapper = new ObjectMapper();
	
		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;
	
		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		//활용정보
		RelatedBoardItemSearchCondition searchCondition = new RelatedBoardItemSearchCondition();
		searchCondition.setItemId(itemId);
		SearchResult<BoardItem> searchResult = this.boardTaggingService.listRelatedBoardItemBySearchCondition(searchCondition);
	
		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("user", user)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("searchResult", searchResult)
		.addObject("isSystemAdmin", this.isSystemAdmin(user))
		.addObject("searchCondition", searchResult.getSearchCondition());
	
		return modelAndView;
	}
	
	/**
	 * 임시저장/평가전 Board 게시물 리스트
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listTemporaryItemView")
	public ModelAndView temporaryListBoardItemView(@RequestParam("gubun") String gubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate) {
		User user = readUser();
		
		String tempSearchConditionString = null;
		
		if(!StringUtils.isEmpty(startDate)) {
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
		}

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord", "startDate","endDate");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		searchCondition.setGubun(gubun);
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		
		//등록자ID세팅
		searchCondition.setUserId(user.getUserId());

		//관리자여부 세팅
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.temporaryListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult)
				.addObject("gubun", gubun);

		return modelAndView;
	}
	
	@RequestMapping(value = "/listTargetItemView")
	public ModelAndView listTargetItemView(@RequestParam("gubun") String gubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate) {
		User user = readUser();
		
		String tempSearchConditionString = null;
		
		if(!StringUtils.isEmpty(startDate)) {
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
		}

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		searchCondition.setGubun(gubun);
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		
		//등록자ID세팅
		searchCondition.setUserId(user.getUserId());

		//관리자여부 세팅
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.targetListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult)
				.addObject("gubun", gubun);

		return modelAndView;
	}
	
	
	@RequestMapping(value = "/listKeyInfoItemView")
	public ModelAndView listKeyInfoItemView(@RequestParam("gubun") String gubun,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			BoardItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate) {
		User user = readUser();
		
		String tempSearchConditionString = null;
		
		if(!StringUtils.isEmpty(startDate)) {
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
		}

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "pagePerRecord");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		searchCondition.setGubun(gubun);
		
		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		
		//등록자ID세팅
		searchCondition.setUserId(user.getUserId());

		//관리자여부 세팅
		searchCondition.setAdmin(this.isSystemAdmin(user));

		String viewName = null;
		SearchResult<BoardItem> searchResult = null;

		searchResult = this.boardItemService.keyInfoListBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("boardCode", new BoardItemCode())
				.addObject("user", user)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", popupYn)
				.addObject("searchResult", searchResult)
				.addObject("gubun", gubun);

		return modelAndView;
	}
	
	/**
	 * E등급 게시물 읽기
	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readEinfogradeItemView")
	public ModelAndView readEinfogradeItemView(
			@RequestParam("itemId") String itemId,
			@RequestParam("gubun") String gubun,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();
		
		//게시물 상세정보를 가져온다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		//게시물 맵정보를 가져온다.
		Board board = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
		boardMap.put("boardId", boardItem.getBoardId());

		board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
		
		BoardPermission permission = initKmsPermission(user, boardItem);
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		// 조회 카운트를 증가 시킨다.
		this.boardItemService.updateHitCount(user.getUserId(), itemId);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readEinfogradeItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readEinfogradeItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/board/boardItem/readEinfogradeItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItem = getAssessItem(boardItem, listAssessItem);
		
		// 사용자 즐겨찾기 조회
//		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("board", board)
		.addObject("permission", permission)
//		.addObject("permission", boardPermission)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("kmsMapName", kmsMapName)
		.addObject("gubun", gubun);
//		.addObject("isFavorite", isFavorite);

		if (popupYn) {
			modelAndView.setViewName("collpack/collaboration/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	/**
	 * E등급 수정 화면 컨트롤 메서드
	 * 
	 * @param itemId 수정대상 게시글 ID
	 * @return ModelAndView
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/updateEinfogradeItemView")
	public ModelAndView updateEinfogradeItemView(@RequestParam("boardId") String boardId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "gubun") String gubun,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());

	//게시물 board map정보를 가져온다.
	//	Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		Board boardList = new Board();
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("isKnowhow", board.getIsKnowhow());
		boardMap.put("boardId", boardId);
		boardMap.put("itemId", boardItem.getItemId());
		
		boardList.setParentList(this.boardAdminService.listParentBoard(boardMap));
		String kmsMapName = this.boardAdminService.getKmsMapName(boardId);
		
		BoardPermission permission = new BoardPermission(false,false,false);
		// 게시판 쓰기 권한 체크
		permission = initPermission(user, board);
		
		//게시글 쓰기 권한이 있는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		BoardItem boardItemList = new BoardItem();
		boardItemList.setBoardItemList(this.boardTaggingService.listRelatedBoardItemBySearchCondition(boardMap));
		
		//평가정보LIST
		List<BoardAssessItem> listAssessItem = this.boardItemService.listAssessItem(itemId);
		boardItemList.setBoardAssessItemList(listAssessItem);
		
		boardItem = getAssessItem(boardItem, listAssessItem);
		

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("user", user)
		.addObject("board", board)
		.addObject("boardCode", new BoardCode())
		.addObject("permission", this.initPermission(user, board))
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("boardList", boardList)
		.addObject("boardItemList", boardItemList)
		.addObject("gubun", gubun)
		.addObject("kmsMapName", kmsMapName));
	}
	
	/**
	 * E등급 수정 처리 동기 컨트롤 메서드
	 * 
	 * @param boardItem 게시글 수정 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateEinfogradeItem")
	public ModelAndView updateEinfogradeItem(@ValidEx(groups = { ModifyItemGroup.class }) BoardItem boardItem,
			BindingResult result, SessionStatus status) {
		
		User user = this.readUser();
		
		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		boardItem.setIsKnowhow(Integer.parseInt(board.getIsKnowhow()));
		
		BoardPermission permission = new BoardPermission(false,false,false);
		
		// 게시판 쓰기 권한 체크

		permission = initPermission(user, board);
		
		//게시글 조회의 권한이 있는지 없는지 확인
		if( !permission.getIsWritePermission()) {
			throw new IKEP4AuthorizedException();
		}
		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/collpack/kms/board/boardItem/updateEinfogradeItemView.do?itemId="
					+ boardItem.getItemId());
		}

		// 등록자 임시 지정 하기
		String userId = user.getUserId();
		String userName = user.getUserName();
//		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		
		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		boardItem.setItemType("0");
		
		//전문가평가
		if(boardItem.getStatus() > 2){
			if(userId.equals(boardItem.getExpertId()) && boardItem.getStatus() < 5){
				boardItem.setStatus(4);
			}
			//boardItem.setAssessorId(userId);
			//boardItem.setAssessorName(userName);
		}
		
		this.boardItemService.updateBoardItem(boardItem, user, true);
		
		status.setComplete();
		
		// 등록자 임시 지정 하기
		if(boardItem.getRegisterId()!=null && !boardItem.getRegisterId().equals("")) {
			user.setUserId(userId);
			user.setUserName(userName);
		}
		
		if(Integer.parseInt(boardItem.getOriStatus()) < 3){
			// 완료시 메시지처리
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String prefix	= "message.collpack.kms.common.messenger.";
			String title 	= messageSource.getMessage( prefix + "alarmTitle", new String[]{user.getUserName()}, Locale.getDefault());
			String contents = messageSource.getMessage( prefix + "alarmContents", new String[]{boardItem.getOriRegistDate(), boardItem.getTitle(), boardItem.getInfoGrade(), boardItem.getMark().toString()}, Locale.getDefault());

			//메시지 보내기
			atmc.addMessage(boardItem.getRegisterId(), boardItem.getRegisterName(), contents.toString(), "", title,"smspop");
		}
		String viewName = "redirect:/collpack/kms/board/boardItem/readEinfogradeItemView.do";
		
		return new ModelAndView(viewName)
		.addObject("itemId", boardItem.getItemId())
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("popupYn", boardItem.getPopupYn())
		.addObject("gubun", '1');
	}
	
	private boolean checkKmsExceptionId(String userId) {
		
		if(kmsProperty == null){
			kmsProperty = PropertyLoader.loadProperties("/configuration/kms-except-login-id.properties");			
			//kmsLoginIds = kmsProperty.getProperty("kms.login.id").split(","); 
			kmsRedirectUrl = kmsProperty.getProperty("kms.redirect.url");
		}
		/*
		for(String exceptIds : kmsLoginIds){
			
			if(exceptIds.equals(userId)){
				return true;
			}			
		}*/
		
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", userId);
		emap.put("roleName", "COKM");
		int cokmRole = userDao.getUserRoleCheck(emap);
		if(cokmRole > 0){
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/expertView.do")
	public ModelAndView expertView(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/collpack/kms/board/boardItem/expertView");
		
		User user = (User) getRequestAttribute("ikep.user");
		List<BoardItem> expertList = this.boardItemService.expertList();
		
		mav.addObject("expertList", expertList);
		//mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	//LOCATION:kms 푸쉬메세지 문구 수정.
	public void sendPushMessage(String userId, String alertText) {
		HttpClient client = new DefaultHttpClient();
		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String pushBaseUrl = prop.getProperty("ikep4.push.baseUrl");
		HttpPost request = new HttpPost(pushBaseUrl+"/rest/push/insertPushByItemId");
        request.addHeader("content-type", "application/json");
		try {
	        JSONObject json = new JSONObject();
	        json.put("portalId", "P000001");
	        json.put("userId", userId);
	        json.put("pushType", "K");
	        json.put("alertTitle", "정보알림");
	        json.put("alertText", alertText);
	        json.put("itemId", "");
	        StringEntity input = new StringEntity( json.toString(), "UTF-8");
	        
        	request.setEntity(input); 
        	HttpResponse response = client.execute(request);
        	
        	//verify the valid error code first
            System.out.println("returnCode : "+response.getStatusLine().getStatusCode());

		} catch (Exception e) {
			log.error("Error occurs during send push messages : "+e.getMessage());
		} finally {
			client.getConnectionManager().shutdown();
	    }
	}
	
	private void authenticationSuccess(HttpServletRequest request, String username) {
		HttpSession httpsession = request.getSession(true);
		String portalId = (String) httpsession.getAttribute("ikep.portalId");

		// 사용자 정보 조회
		User user = userService.read(username);

		// 사용자 테마 정보 조회
		UserTheme userThemeCheck = userThemeService.readUserTheme(user.getUserId());
		UserTheme userTheme = null;

		if (userThemeCheck == null) {
			// 사용자 테마 정보 없으면 기본 테마정보 조회
			userTheme = userThemeService.readDefaultTheme(portalId);
		} else {
			userTheme = userThemeService.readTheme(userThemeCheck.getThemeId(), portalId);
		}

		user.setUserTheme(userTheme);
		
		//세션 타임아웃 설정
		long loginTime=System.currentTimeMillis();
		httpsession.setAttribute("ikep.loginTime", loginTime);

		httpsession.setAttribute("ikep.user", user);
		httpsession.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
				new Locale(user.getLocaleCode()));

		BrowserCheck browserCheck = new BrowserCheck();

		String userAgent = request.getHeader("user-agent");
		String browser = browserCheck.getInternetBrowser(userAgent.toLowerCase());

	}
}
