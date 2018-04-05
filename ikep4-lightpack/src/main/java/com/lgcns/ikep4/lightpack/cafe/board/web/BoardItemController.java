/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem.CreateItemGroup;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem.CreateReplyItemGroup;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem.ModifyItemGroup;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardPermission;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeMaker;


/**
 * 게시글 컨트롤 클래스
 */
@Controller("cfBoardItemController")
@RequestMapping(value = "/lightpack/cafe/board/boardItem")
public class BoardItemController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;

	/** The board item service. */
	@Autowired
	private BoardItemService boardItemService;

	/** The acl service. */
	@Autowired
	private ACLService aclService;

	@Autowired
	private UserConfigService userConfigService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;

	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin(Board.CAFE_MANAGER, user.getUserId());
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
		member.setCafeId(board.getCafeId());
		member.setMemberId(user.getUserId());

		member = memberService.read(member);

		if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
			isSystemAdmin = Boolean.TRUE;
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

		/** Write Check **/

		if (board.getWritePermission() == 1) {
			isWritePermission = Boolean.TRUE;
		} else {

			if (member != null && !member.getMemberLevel().equals("")) {
				if (board.getWritePermission() == 2 && Integer.parseInt(member.getMemberLevel()) <= 4) { // 준회원
																											// 이상
					isWritePermission = true;
				} else if (board.getWritePermission() == 3 && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
																												// 이상
					isWritePermission = true;
				} else if (board.getWritePermission() == 4 && Integer.parseInt(member.getMemberLevel()) <= 2) { // 운영진
																												// 이상
					isWritePermission = true;
				}

			}
		}
		// 쓰기권한자에게 쓰기 권한과 읽기 권한을 True 한다.
		if (isWritePermission) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

		/** Read Check **/

		if (board.getReadPermission() == 1) {
			isReadPermission = Boolean.TRUE;
		} else {
			if (member != null && !member.getMemberLevel().equals("")) {
				if (board.getReadPermission() == 2 && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
																										// 이상
					isReadPermission = true;
				} else if (board.getReadPermission() == 3 && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
																												// 이상
					isReadPermission = true;
				} else if (board.getReadPermission() == 4) { // 개별 설정
					isReadPermission = this.isReadPermission(user, board.getBoardId());
				}

			}
		}
		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
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
			@RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn

	) {
		Long past = System.currentTimeMillis();

		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn",
					"cafeId", "isAll");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		User user = this.readUser();

		Portal portal = this.readPortal();

		// 게시판 관리 정보를 가져온다.
		Board board = null;
		if (StringUtil.isEmpty(searchCondition.getIsAll())) {
			board = this.boardAdminService.readBoard(searchCondition.getBoardId());
		} else {
			board = new Board();
			board.setBoardName(messageSource.getMessage(
					"message.lightpack.cafe.board.boardItem.listBoardView.list.recentBoardItem", null,
					new Locale(user.getLocaleCode())));
			board.setRss(0);
		}

		Boolean tempPopupYn = Boolean.FALSE;

		if (StringUtil.isEmpty(searchCondition.getIsAll())) {
			if (BooleanUtils.isTrue(popupYn)) {
				tempPopupYn = Boolean.TRUE;

			} else {
				tempPopupYn = Boolean.FALSE;
				tempPopupYn = board.getDocPopup() == 1;
			}
		}

		searchCondition.setPopupYn(tempPopupYn);

		// Board 설정 정보에 보관되어 있는 정보 SearchCondition에 매핑
		if (searchCondition.getViewMode() == null) {
			// 게시판 정보에서 뷰모드 설정
			if (StringUtil.isEmpty(searchCondition.getIsAll())) {
				searchCondition.setLayoutType("layoutNormal");
				searchCondition.setViewMode(board.getListType());
				searchCondition.setPagePerRecord(board.getPageNum());
			} else {
				searchCondition.setViewMode("1");
				//searchCondition.setPagePerRecord(10);
			}
		}

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_CAFE, portal.getPortalId());

		Boolean createUserConfig = (userConfig != null);

		if (SearchCondition.ACTION_PAGE_PER_RECORD_CHANAGE.equals(searchCondition.getActionType())) {
			if (createUserConfig) {
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				this.userConfigService.updateUserConfig(userConfig);

			} else {
				userConfig = new UserConfig();

				// 개인화 설정 정보를 저장한다.
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_CAFE);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 개인화 설정이 없으면 게시판설정의 페이지 정보를 저장한다.
		if (createUserConfig) {
			if (StringUtil.isEmpty(searchCondition.getIsAll())) {
				searchCondition.setPagePerRecord(userConfig.getPageCount() == null ? board.getPageNum() : userConfig
						.getPageCount());
			}
			searchCondition.setLayoutType(userConfig.getLayout());
		} else {
			if (StringUtil.isEmpty(searchCondition.getIsAll())) {
				searchCondition.setPagePerRecord(board.getPageNum());
			}
		}

		// 게시판 권한 정보를 설정한다.
		BoardPermission boardPermission = null;
		if (StringUtil.isEmpty(searchCondition.getIsAll())) {
			boardPermission = this.initPermission(user, board);
			searchCondition.setAdmin(boardPermission.getIsSystemAdmin());
		}

		searchCondition.setUserId(user.getUserId());
		SearchResult<BoardItem> searchResult = this.boardItemService.listBoardItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView().addObject("board", board).addObject("user", user)
				.addObject("boardCode", new BoardCode()).addObject("userConfig", userConfig)
				.addObject("itemId", itemId).addObject("permission", boardPermission)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", tempPopupYn);

		Long present = System.currentTimeMillis();

		this.log.info("목록 갯수" + searchResult.getSearchCondition().getPagePerRecord() + "소모시간 : " + (present - past));

		return modelAndView;
	}

	/**
	 * 게시글 다른 파트에서 사용하게 위해서 boardId를 받지 않는 URL
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param popupYn the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readBoardItemLinkView")
	public ModelAndView readBoardItemLinkView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		Boolean temPopupYn = Boolean.TRUE;

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);
		
		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.cafe.common.boardItem.deletedItem");
		}

		ModelAndView modelAndView = this.readBoardItemView(boardItem.getBoardId(), itemId, layoutType, null,
				temPopupYn, portletYn);

		return modelAndView;
	}

	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param popupYn the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readBoardItemView")
	public ModelAndView readBoardItemView(@RequestParam("boardId") String boardId,
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);
		
		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.cafe.common.boardItem.deletedItem");
		}

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());

		BoardPermission boardPermission = this.initPermission(user, board);
		if (!boardPermission.getIsReadPermission()) {
			throw new IKEP4AuthorizedException();
		}

		// 조회 카운트를 증가 시킨다.
		BoardReference boardReference = new BoardReference();

		boardReference.setRegisterId(user.getUserId());
		boardReference.setItemId(itemId);

		this.boardItemService.updateHitCount(boardReference);

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.cafe.common.boardItem.deletedItem");
		}

		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "lightpack/cafe/board/boardItem/readBoardItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "lightpack/cafe/board/boardItem/readBoardItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "lightpack/cafe/board/boardItem/readBoardItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("boardItem", boardItem)
				.addObject("board", board).addObject("permission", boardPermission).addObject("layoutType", layoutType)
				.addObject("user", user).addObject("searchConditionString", searchConditionString)
				.addObject("popupYn", popupYn).addObject("portletYn", portletYn)
				.addObject("fileDataListJson", fileDataListJson).addObject("isFavorite", isFavorite);

		if (popupYn) {
			modelAndView.setViewName("lightpack/cafe/board/boardItem/readBoardItemPopupView");
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

		Board board = this.boardAdminService.readBoard(boardId);

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

		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("user", user)
				.addObject("permission", this.initPermission(user, board))
				.addObject("fileDataListJson", fileDataListJson)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("board", board));
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
	@RequestMapping(value = "/createBoardItemViewForMain")
	public ModelAndView createBoardItemViewForMain(@RequestParam(value = "cafeId", required = false) String cafeId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		Member member = new Member();
		member.setCafeId(cafeId);
		member.setMemberId(user.getUserId());
		member = memberService.read(member);

		boolean isSysAdmin = isSystemAdmin(user);

		List<Board> boardList = new ArrayList<Board>();

		List<Board> list = boardAdminService.getCafeWriteBoard(cafeId);

		// 카페 멤버별로 권한있는 게시판리스트 셋팅
		for (Board board : list) {

			board.setBoardName(board.getBoardName().substring(1));

			if (isSysAdmin) {
				boardList.add(board);
			} else {
				if (board.getWritePermission() == 1) {
					boardList.add(board);
				} else {
					if (member != null) {
						if (board.getWritePermission() == 2) {
							if (Integer.parseInt(member.getMemberLevel()) <= 4) {
								boardList.add(board);
							}
						} else if (board.getWritePermission() == 3) {
							if (Integer.parseInt(member.getMemberLevel()) <= 3) {
								boardList.add(board);
							}
						} else if (board.getWritePermission() == 4) {
							if (Integer.parseInt(member.getMemberLevel()) <= 2) {
								boardList.add(board);
							}
						}
					}
				}
			}
		}

		if (boardList.size() < 1) {
			throw new IKEP4AuthorizedException();
		}

		BoardItem boardItem = new BoardItem();
		Board board = new Board();
		board.setCafeId(cafeId);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("boardList", boardList)
				.addObject("user", user).addObject("permission", this.initPermission(user, board))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX));
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

		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("user", user)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson));
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

		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("user", user)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson));
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
	public ModelAndView createBoardItem(@ValidEx(groups = { CreateItemGroup.class }) BoardItem boardItem,
			BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardItem/createBoardItemView.do?boardId="
					+ boardItem.getBoardId());
		}

		User user = this.readUser();

		boardItem.setPortalId(user.getPortalId());

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);

		String itemId = this.boardItemService.createBoardItem(boardItem, user);

		status.setComplete();

		String viewName = null;

		if (boardItem.getPopupYn()) {
			viewName = "redirect:/lightpack/cafe/board/boardItem/listBoardItemView.do";

		} else {
			viewName = "redirect:/lightpack/cafe/board/boardItem/readBoardItemView.do";
		}

		return new ModelAndView(viewName).addObject("boardId", boardItem.getBoardId()).addObject("itemId", itemId)
				.addObject("searchConditionString", boardItem.getSearchConditionString())
				.addObject("popupYn", boardItem.getPopupYn());
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

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardItem/createReplyBoardItemView.do?itemId="
					+ boardItem.getItemParentId());
		}

		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		BoardItem parent = this.boardItemService.readBoardItemMasterInfo(boardItem.getItemParentId());

		boardItem.setStartDate(parent.getStartDate());
		boardItem.setEndDate(parent.getEndDate());
		boardItem.setItemDisplay(parent.getItemDisplay());
		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);

		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		String itemId = this.boardItemService.createReplyBoardItem(boardItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardItem/readBoardItemView.do")
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

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/cafe/board/boardItem/updateBoardItemView.do?itemId="
					+ boardItem.getItemId());
		}

		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		this.boardItemService.updateBoardItem(boardItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/cafe/board/boardItem/readBoardItemView.do")
				.addObject("boardId", boardItem.getBoardId()).addObject("itemId", boardItem.getItemId())
				.addObject("searchConditionString", boardItem.getSearchConditionString())
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
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {

		User user = this.readUser();

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		this.boardItemService.userDeleteBoardItem(boardItem);

		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/lightpack/cafe/board/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/cafe/board/boardItem/listBoardItemView.do";
		}

		return new ModelAndView(viewName).addObject("boardId", boardItem.getBoardId())
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 일괄삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param itemIds 삭제대상 게시글 ID 배열
	 */
	@RequestMapping(value = "/adminMultiDeleteBoardItem")
	public @ResponseBody
	void adminMultiDeleteBoardItem(@RequestParam("boardId") String boardId, @RequestParam("itemId") String[] itemIds) {

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
			@RequestParam("itemId") String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {

		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		this.boardItemService.adminDeleteBoardItem(boardItem);

		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/lightpack/cafe/board/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/cafe/board/boardItem/listBoardItemView.do";
		}

		return new ModelAndView(viewName).addObject("boardId", boardItem.getBoardId())
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn);
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

			// 이미 존재하면 -1를 리턴한다.
			if (this.boardItemService.exsitRecommend(boardRecommend)) {
				currentRecommendCount = -1;
			} else {

				this.boardItemService.updateRecommendCount(boardRecommend);

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
					IKepConstant.ITEM_TYPE_CODE_CAFE, portal.getPortalId());

			if (userConfig == null) {
				userConfig = new UserConfig();
				// 개인화 설정 정보를 저장한다.
				userConfig.setLayout(layoutType);
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_CAFE);

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
	@RequestMapping(value = "/listRecentBoardItem")
	public @ResponseBody
	List<BoardItem> updateUserConfigLayout(@RequestParam("boardId") String boardId, @RequestParam("count") Integer count) {
		List<BoardItem> boardItemList = null;

		try {
			boardItemList = this.boardItemService.listRecentBoardItem(boardId, count);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return boardItemList;
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

	/**
	 * 게시물 이동
	 * 
	 * @param orgBoardId
	 * @param targetBoardId
	 * @param itemIds
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
	 * @param cafeId
	 * @param orgBoardId
	 * @return
	 */
	@RequestMapping(value = "/viewBoardTree")
	public ModelAndView viewBoardTree(@RequestParam("cafeId") String cafeId,
			@RequestParam("orgBoardId") String orgBoardId) {

		String boardTreeJson = boardTreeJson(cafeId);

		ModelAndView model = new ModelAndView();
		model.setViewName("lightpack/cafe/board/boardItem/viewBoardTree");
		model.addObject("cafeId", cafeId);
		model.addObject("orgBoardId", orgBoardId);
		model.addObject("boardTreeJson", boardTreeJson);

		return model;

	}

	/**
	 * 게시판 Tree Json
	 * 
	 * @param cafeId
	 * @return
	 */
	private String boardTreeJson(String cafeId) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", cafeId);
		map.put("boardRootId", "0");

		List<Board> boardList = boardAdminService.listByBoardRootId(map);
		return TreeMaker.init(boardList, "boardId", "boardParentId", "boardName").create().toJsonString();

	}
}
