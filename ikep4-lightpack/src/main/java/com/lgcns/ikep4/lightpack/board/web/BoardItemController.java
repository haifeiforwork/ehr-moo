/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItem.CreateItemGroup;
import com.lgcns.ikep4.lightpack.board.model.BoardItem.CreateReplyItemGroup;
import com.lgcns.ikep4.lightpack.board.model.BoardItem.ModifyItemGroup;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.model.BoardPermission;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemCategoryService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemReaderService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.searchpreprocessor.util.DateUtil;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
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
@Controller
@RequestMapping(value = "/lightpack/board/boardItem")
public class BoardItemController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;

	/** The board item service. */
	@Autowired
	private BoardItemService boardItemService;
	
	/** The board item service. */
	@Autowired
	private BoardItemDao boardItemDao;
	
	/** The board item service. */
	@Autowired
	private BoardItemCategoryService boardItemCategoryService;

	/** The acl service. */
	@Autowired
	private ACLService aclService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private UserConfigService userConfigService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
    private UserDao userDao;

	@Autowired
	private BoardItemReaderService boardItemReaderService;
	
	private final static int DEFAULT_INTERVAL = 5;
	
	private final String SUFFIX = ".properties";

	private int interval = DEFAULT_INTERVAL;

	private static String fileUrl;
	
	private static final String DEFAULT_BOARD_ROOT_ID = "0";
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
	
	public boolean isAdminPermission(User user, String boardId) {
		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "ADMIN", boardId, user.getUserId());
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

	private BoardPermission initPermission(User user, Board board) {
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Boolean isWritePermission = this.isWritePermission(user, board.getBoardId());

		Boolean isReadPermission = this.isReadPermission(user, board.getBoardId());
		
		Boolean isAdminPermission = this.isAdminPermission(user, board.getBoardId());

		// 관리자의 경우 쓰기 권한과 읽기 권한을 True 한다.
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			// 쓰기권한자에게 쓰기 권한과 읽기 권한을 True 한다.
		} else if (isWritePermission) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		} 
		
		if (isAdminPermission) {
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

	/**
	 * 게시글 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBoardItemView")
	public ModelAndView listBoardItemView(
			 BoardItemSearchCondition searchCondition,
			 @RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "isSystemAdmin", defaultValue = "false") Boolean isSystemAdmin,
			@CookieValue(value = "viewMode", defaultValue = "") String viewMode

	) {
		User user = this.readUser();
		isSystemAdmin = this.isSystemAdmin(user);
		
		if(!"".equals(viewMode)) searchCondition.setViewMode(viewMode);
		String tempSearchConditionString = null;
		if(isSystemAdmin){
			searchCondition.setAdmin(isSystemAdmin);
		}
		
		
		

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "wordId", "admin");
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@1:"+tempSearchConditionString);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@2:"+tempSearchConditionString);
		}

		

		Portal portal = this.readPortal();

		// 게시판 관리 정보를 가져온다.
		Board board = this.boardAdminService.readBoard2(searchCondition.getBoardId());
		searchCondition.setBoardAdmin(false);
		searchCondition.setBoardAdminUse(false);
		boolean boardAdminRole = false;
		if(board.getAdminPermission()==0){
			boardAdminRole = aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "ADMIN", board.getBoardId(), user.getUserId());
			searchCondition.setBoardAdminUse(true);
		}
		
		if(boardAdminRole){
			searchCondition.setBoardAdmin(boardAdminRole);
		}
		
		Boolean tempPopupYn = Boolean.FALSE;

		if (BooleanUtils.isTrue(popupYn)) {
			tempPopupYn = Boolean.TRUE;

		} else {
			tempPopupYn = Boolean.FALSE;
			tempPopupYn = board.getDocPopup() == 1;
		}

		searchCondition.setPopupYn(tempPopupYn);

		// Board 설정 정보에 보관되어 있는 정보 SearchCondition에 매핑
		if (searchCondition.getViewMode() == null) {
			// 게시판 정보에서 뷰모드 설정
			searchCondition.setViewMode(board.getListType());
			if(searchCondition.getPagePerRecord()==null){
				searchCondition.setPagePerRecord(board.getPageNum());
			}
		}

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

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
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 개인화 설정이 없으면 게시판설정의 페이지 정보를 저장한다.
		if (createUserConfig) {
			searchCondition.setPagePerRecord(userConfig.getPageCount() == null ? board.getPageNum() : userConfig
					.getPageCount());
			searchCondition.setLayoutType(userConfig.getLayout());
		} else {
			searchCondition.setPagePerRecord(board.getPageNum());
		}

		// 게시판 권한 정보를 설정한다.
		//BoardPermission boardPermission = this.initPermission(user, board);
		if(searchCondition.getAdmin()==null) searchCondition.setAdmin(false);
		
		BoardPermission boardPermission = new BoardPermission(searchCondition.getAdmin(), true, true);
		searchCondition.setUserId(user.getUserId());

		SearchResult<BoardItem> searchResult =null;
		if("100000793108".equals(board.getBoardId())||"100000793116".equals(board.getBoardId())){//공문공지이거나 일반공지 이면
			searchResult = this.boardItemService.listBoardItemBySearchCondition2(searchCondition);
		}else if("100006240370".equals(board.getBoardId())){
			if(!StringUtil.isEmpty(searchCondition.getSearchWorkplace())){
				String[] tempWords = searchCondition.getSearchWorkplace().split(",");
				List<String> tempWorkspaces = new ArrayList<String>();
				for(int i=0;i<tempWords.length;i++){
					tempWorkspaces.add(i, tempWords[i]);
				}
				searchCondition.setSearchWorkplaces(tempWorkspaces);
			}
			
			searchResult = this.boardItemService.listBoardItemBySearchCondition3(searchCondition);
		}else{
			searchResult = this.boardItemService.listBoardItemBySearchCondition(searchCondition);
		}
		
		BoardItemCategory categoryBoardId = new BoardItemCategory();
		categoryBoardId.setBoardId(searchCondition.getBoardId());
		
		List<BoardItemCategory> boardItemCategoryList = null;
		boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
		
		
		ModelAndView modelAndView = new ModelAndView().addObject("board", board).addObject("user", user)
				.addObject("boardCode", new BoardCode()).addObject("userConfig", userConfig)
				.addObject("itemId", itemId).addObject("permission", boardPermission)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", tempPopupYn).addObject("boardItemCategoryList", boardItemCategoryList)
				.addObject("boardAdminRole", boardAdminRole);
 
		return modelAndView;
	}
	
	@RequestMapping(value = "/deleteBoardItemListView")
	public ModelAndView deleteBoardItemListView(
			 BoardItemSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@CookieValue(value = "viewMode", defaultValue = "") String viewMode
	) {
		User user = this.readUser();
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		if(!"".equals(viewMode)) searchCondition.setViewMode(viewMode);
		String tempSearchConditionString = null;
		if(isSystemAdmin) searchCondition.setAdmin(isSystemAdmin);

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "wordId", "admin");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		

		Portal portal = this.readPortal();

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

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
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 게시판 권한 정보를 설정한다.
		SearchResult<BoardItem> searchResult =null;
		searchResult = this.boardItemService.deleteListBoardItemBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView().addObject("user", user)
				.addObject("userConfig", userConfig)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString);
 
		return modelAndView;
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
		SearchResult<BoardItemReader> searchResult = this.boardItemReaderService.listReaderBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", tempSearchConditionString)
		.addObject("boardCode", new BoardCode());
		
		return modelAndView;
	}	
	
	@RequestMapping(value = "/listBoardView")
	public ModelAndView listBoardView(BoardItemReaderSearchCondition searchCondition,
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
		SearchResult<BoardItemReader> searchResult = this.boardItemReaderService.listBoardBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", tempSearchConditionString)
		.addObject("boardCode", new BoardCode());
		
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
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0") String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {

		Boolean temPopupYn = Boolean.TRUE;

		User user = this.readUser();

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}

		Boolean hasAdminPermission = this.aclService.hasSystemPermission(Board.BOARD_PERMISSION_CLASS_NAME, "MANAGE",
				Board.BOARD_RESOURCE_NAME, user.getUserId());

		Boolean hasBoardPermission = this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, new String[] {
				"READ", "WRITE", "MANAGE" }, boardItem.getBoardId(), user.getUserId());

		AccessingResult accessResult = new AccessingResult();

		if (hasAdminPermission || hasBoardPermission) {
			accessResult.setResult(Boolean.TRUE);
		} else {
			accessResult.setResult(Boolean.FALSE);
		}

		ModelAndView modelAndView = this.readBoardItemView(boardItem.getBoardId(), accessResult, itemId, layoutType,
				null, temPopupYn, portletYn, viewMode);

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
	public ModelAndView readBoardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"READ", "WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0", required = false) String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		boolean boardAdminRole = false;
		if(board.getAdminPermission()==0){
			boardAdminRole = aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "ADMIN", board.getBoardId(), user.getUserId());
		}
		//System.out.println("##############################"+board.getContentsReadPermission());

		String readerView ="";
		if(board.getContentsReadPermission()==1){//독서자 설정 게시판이고 
			BoardItemReader boardItemReader =new BoardItemReader();
			boardItemReader.setBoardItemId(boardItem.getItemId());
			boardItemReader.setReaderId(user.getUserId());
			//System.out.println("##############################selectReaderCount:"+this.boardItemReaderService.selectReaderCount(boardItemReader));
			if(this.boardItemReaderService.selectReaderCount(boardItemReader)==0){//독서자 설정이 없으면 독서자 체크하지 않는다.
				readerView="hidden";
			}else{
				//System.out.println("##############################readerFlag:"+this.boardItemReaderService.readerFlag(boardItemReader));
				if( ( this.boardItemReaderService.readerFlag(boardItemReader)==0 ) && ( !isSystemAdmin ) && ( !(boardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 어드민, 등록자가 아니면
	
					throw new IKEP4AuthorizedException();
				}
			}
		}
		
		// 조회 카운트를 증가 시킨다.
		BoardReference boardReference = new BoardReference();

		boardReference.setRegisterId(user.getUserId());
		boardReference.setItemId(itemId);

		this.boardItemService.updateHitCount(boardReference);
		
		

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}

		// 게시기간이 지난 아이템의 처리
		if (getDayDiff(boardItem.getEndDate(), DateUtil.getToday()) < 0 && !this.isSystemAdmin(user)
				&& !boardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.expiredItem");
		}

		// 게시기간이 시작되지 않은 아이템의 처리
		if (getDayDiff(boardItem.getStartDate(), DateUtil.getToday()) > 0 && !this.isSystemAdmin(user)
				&& !boardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.preItem");
		}



		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "lightpack/board/boardItem/readBoardItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "lightpack/board/boardItem/readBoardItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "lightpack/board/boardItem/readBoardItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}

		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		
		boolean mntrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "MNT");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			mntrole = true;
		}

		boolean ecmrole = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(map1);
		if(ecmRole > 0){
			ecmrole = true;
		}
		
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("boardItem", boardItem)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("layoutType", layoutType).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("portletYn", portletYn).addObject("fileDataListJson", fileDataListJson)
				.addObject("readerView", readerView)
				.addObject("viewMode", viewMode).addObject("isFavorite", isFavorite)
				.addObject("mntrole", mntrole)
				.addObject("ecmrole", ecmrole)
				.addObject("boardAdminRole", boardAdminRole);

		if (popupYn) {
			modelAndView.setViewName("lightpack/board/boardItem/readBoardItemPopupView");
		}

		return modelAndView;
	}
	
	
	@RequestMapping(value = "/readDeleteBoardItemView")
	public ModelAndView readDeleteBoardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"READ", "WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0", required = false) String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		//System.out.println("##############################"+board.getContentsReadPermission());

		String readerView ="";
		if(board.getContentsReadPermission()==1){//독서자 설정 게시판이고 
			BoardItemReader boardItemReader =new BoardItemReader();
			boardItemReader.setBoardItemId(boardItem.getItemId());
			boardItemReader.setReaderId(user.getUserId());
			//System.out.println("##############################selectReaderCount:"+this.boardItemReaderService.selectReaderCount(boardItemReader));
			if(this.boardItemReaderService.selectReaderCount(boardItemReader)==0){//독서자 설정이 없으면 독서자 체크하지 않는다.
				readerView="hidden";
			}else{
				//System.out.println("##############################readerFlag:"+this.boardItemReaderService.readerFlag(boardItemReader));
				if( ( this.boardItemReaderService.readerFlag(boardItemReader)==0 ) && ( !isSystemAdmin ) && ( !(boardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 어드민, 등록자가 아니면
	
					throw new IKEP4AuthorizedException();
				}
			}
		}
		
		// 조회 카운트를 증가 시킨다.
		BoardReference boardReference = new BoardReference();

		boardReference.setRegisterId(user.getUserId());
		boardReference.setItemId(itemId);

		this.boardItemService.updateHitCount(boardReference);
		
		

		if (boardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}

		// 삭제 게시물에 대한 처리
		if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}

		// 게시기간이 지난 아이템의 처리
		if (getDayDiff(boardItem.getEndDate(), DateUtil.getToday()) < 0 && !this.isSystemAdmin(user)
				&& !boardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.expiredItem");
		}

		// 게시기간이 시작되지 않은 아이템의 처리
		if (getDayDiff(boardItem.getStartDate(), DateUtil.getToday()) > 0 && !this.isSystemAdmin(user)
				&& !boardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.preItem");
		}



		// 이동할 뷰를 선택한다.
		String viewName = "lightpack/board/boardItem/readDeleteBoardItemView";

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}

		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		
		boolean mntrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "MNT");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			mntrole = true;
		}

		boolean ecmrole = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(map1);
		if(ecmRole > 0){
			ecmrole = true;
		}
		
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("boardItem", boardItem)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("layoutType", layoutType).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("portletYn", portletYn).addObject("fileDataListJson", fileDataListJson)
				.addObject("readerView", readerView)
				.addObject("viewMode", viewMode).addObject("isFavorite", isFavorite)
				.addObject("mntrole", mntrole)
				.addObject("ecmrole", ecmrole);


		return modelAndView;
	}
	
	@RequestMapping(value = "/readBoardItemPrintView")
	public ModelAndView readBoardItemPrintView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"READ", "WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0", required = false) String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
		
		//System.out.println("##############################"+board.getContentsReadPermission());

		String readerView ="";
		if(board.getContentsReadPermission()==1){//독서자 설정 게시판이고 
			BoardItemReader boardItemReader =new BoardItemReader();
			boardItemReader.setBoardItemId(boardItem.getItemId());
			boardItemReader.setReaderId(user.getUserId());
			//System.out.println("##############################selectReaderCount:"+this.boardItemReaderService.selectReaderCount(boardItemReader));
			if(this.boardItemReaderService.selectReaderCount(boardItemReader)==0){//독서자 설정이 없으면 독서자 체크하지 않는다.
				readerView="hidden";
			}else{
				//System.out.println("##############################readerFlag:"+this.boardItemReaderService.readerFlag(boardItemReader));
				if( ( this.boardItemReaderService.readerFlag(boardItemReader)==0 ) && ( !isSystemAdmin ) && ( !(boardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 어드민, 등록자가 아니면
	
					throw new IKEP4AuthorizedException();
				}
			}
		}
		
		



		// 이동할 뷰를 선택한다.
		String viewName = "";

		viewName = "lightpack/board/boardItem/readBoardItemPrintView";

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (boardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}

		
		boolean mntrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "MNT");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			mntrole = true;
		}

		
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("boardItem", boardItem)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("layoutType", layoutType).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("portletYn", portletYn).addObject("fileDataListJson", fileDataListJson)
				.addObject("readerView", readerView)
				.addObject("mntrole", mntrole);

		if (popupYn) {
			modelAndView.setViewName("lightpack/board/boardItem/readBoardItemPopupView");
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
	public ModelAndView createBoardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("boardItem") == null) {
			Date date = Calendar.getInstance().getTime();

			boardItem = new BoardItem();
			boardItem.setBoardId(boardId);
			boardItem.setStartDate(date);
			boardItem.setToDate(date);

			Properties boardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

			// #게시기간 단위 설정 (year,month,day)
			String itemPeriodType = boardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
			// #게시기간 설정
			int itemPeriod = Integer.valueOf(boardprop.getProperty("lightpack.board.boardItem.itemPeriod"));

			if ("year".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(DateUtils.addYears(date, itemPeriod), -1));
			} else if ("month".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
			} else if ("day".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(date, itemPeriod));
			} else {
				boardItem.setEndDate(DateUtils.addYears(date, 1));
			}
			
			boardItem.setDisStartDate(date);
			boardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
			

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
		
		BoardItemCategory categoryBoardId = new BoardItemCategory();
		categoryBoardId.setBoardId(boardId);
		
		boolean ecmrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ECM");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			ecmrole = true;
		}
		
		List<BoardItemCategory> boardItemCategoryList = null;
			boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("user", user)
				.addObject("permission", this.initPermission(user, board))
				.addObject("fileDataListJson", fileDataListJson)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("ecmrole", ecmrole)
				.addObject("useActiveX", useActiveX).addObject("board", board).addObject("boardItemCategoryList",boardItemCategoryList));
				//.addObject("useActiveX", useActiveX).addObject("board", board).addObject("boardItemCategoryList",boardItemCategoryList));
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
	public ModelAndView updateBoardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		Properties boardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

		// #게시기간 단위 설정 (year,month,day)
		String itemPeriodType = boardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
		// #게시기간 설정
		int itemPeriod = Integer.valueOf(boardprop.getProperty("lightpack.board.boardItem.itemPeriod"));
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}
		List<String> workplaceList = null;
		if("100006240370".equals(boardItem.getBoardId())){
			workplaceList = boardItemService.workplaceList(itemId);
		}
		Date date = Calendar.getInstance().getTime();
		boardItem.setToDate(date);
		
		if(boardItem.getDisEndDate() == null || boardItem.getItemDisplay() == 0){
			boardItem.setDisStartDate(date);
			boardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
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
		
		BoardItemCategory categoryBoardId = new BoardItemCategory();
		categoryBoardId.setBoardId(boardId);
		
		List<BoardItemCategory> boardItemCategoryList = null;
			boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
			
			boolean ecmrole = false;
			Map<String, String> emap = new HashMap<String, String>();
			emap.put("userId", user.getUserId());
			emap.put("roleName", "ECM");
			int ecmRole = userDao.getUserRoleCheck(emap);
			if(ecmRole > 0){
				ecmrole = true;
			}
		
		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("user", user)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson)
				.addObject("boardItemCategoryList",boardItemCategoryList))
				.addObject("ecmrole", ecmrole).addObject("workplaceList", workplaceList);
	}

	
	@RequestMapping(value = "/updateDeleteBoardItemView")
	public ModelAndView updateDeleteBoardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		Properties boardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

		// #게시기간 단위 설정 (year,month,day)
		String itemPeriodType = boardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
		// #게시기간 설정
		int itemPeriod = Integer.valueOf(boardprop.getProperty("lightpack.board.boardItem.itemPeriod"));
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		BoardItem boardItem = null;

		if (this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem) this.getModelAttribute("boardItem");
		}
		Date date = Calendar.getInstance().getTime();
		boardItem.setToDate(date);
		
		if(boardItem.getDisEndDate() == null || boardItem.getItemDisplay() == 0){
			boardItem.setDisStartDate(date);
			boardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
		}
		
		boardItem.setEndDate(DateUtils.addDays(DateUtils.addYears(date, itemPeriod), -1));

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
		
		BoardItemCategory categoryBoardId = new BoardItemCategory();
		categoryBoardId.setBoardId(boardId);
		
		List<BoardItemCategory> boardItemCategoryList = null;
			boardItemCategoryList = this.boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
			
			boolean ecmrole = false;
			Map<String, String> emap = new HashMap<String, String>();
			emap.put("userId", user.getUserId());
			emap.put("roleName", "ECM");
			int ecmRole = userDao.getUserRoleCheck(emap);
			if(ecmRole > 0){
				ecmrole = true;
			}
		
		return this.bindResult(new ModelAndView().addObject("boardItem", boardItem).addObject("user", user)
				.addObject("board", board).addObject("permission", this.initPermission(user, board))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson)
				.addObject("boardItemCategoryList",boardItemCategoryList))
				.addObject("ecmrole", ecmrole);
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
	public ModelAndView createReplyBoardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

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

	
	
	 
	  @RequestMapping("/getMailControl.do")
	  public @ResponseBody Map<String, Object> 
	getWaitTime() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int mailWaitTime = -1;
			int mailWaitCount = this.boardItemService.getMailWaitCount();
			if(mailWaitCount > 0){
				mailWaitTime = this.boardItemService.getMailWaitTime();
			}
			result.put("mailWaitTime", mailWaitTime);
			result.put("success", "success");
		} catch(Exception ex) {
			result.put("success", "fail");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
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
	public ModelAndView createBoardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @ValidEx(groups = { CreateItemGroup.class }) BoardItem boardItem,
			AccessingResult accessResult, BindingResult result, SessionStatus status) {

		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/board/boardItem/createBoardItemView.do?boardId="
					+ boardItem.getBoardId());
		}

		User user = this.readUser();

		boardItem.setPortalId(user.getPortalId());

		if(boardItem.getAnonymous()==1){
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), boardItem.getRegisterName());
		}else{
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		}
		// 타임존 처리
		Date startDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getStartDate());
		Date endDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getEndDate());

		endDate = DateUtils.addDays(endDate, 1);
		endDate = DateUtils.addSeconds(endDate, -1);

		boardItem.setStartDate(startDate);
		
		if(boardItem.getItemForever()==1){
			String[] patterns= {"yyyyMMdd"};
			try {
				boardItem.setEndDate(DateUtils.parseDate("99991231",patterns ));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			boardItem.setEndDate(endDate);
		}
		if(boardItem.getDisStartDate()!=null){
			Date disStartDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getDisStartDate());
			Date disEndDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getDisEndDate());
			boardItem.setDisStartDate(disStartDate);
			boardItem.setDisEndDate(disEndDate);
		}

		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);
		String wordId   = boardItem.getWordId();
		String wordName = boardItem.getWordName();

		boardItem.setWordId(wordId);
		boardItem.setWordName(wordName);
		boardItem.setDisplayName(boardItem.getRegisterName());
		
		String itemId = this.boardItemService.createBoardItem(boardItem, user);
		
		if("100006240370".equals(boardItem.getBoardId())){
			boardItem.setItemId(itemId);
			boardItemService.saveBoardItemWorkplace(boardItem);
		}
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");

		status.setComplete();

		String viewName = null;

		if (boardItem.getPopupYn()) {
			viewName = "redirect:/lightpack/board/boardItem/listBoardItemView.do";
			if("1".equals(boardItem.getTempSave())) {
				viewName = "redirect:/basicpack/board/boardItem/listTempSaveItemView.do";
			}

		} else {
			viewName = "redirect:/lightpack/board/boardItem/readBoardItemView.do";
		}

		return new ModelAndView(viewName).addObject("boardId", boardItem.getBoardId()).addObject("itemId", itemId)
				.addObject("searchConditionString", boardItem.getSearchConditionString())
				.addObject("popupYn", boardItem.getPopupYn());
	}

	
	
	@RequestMapping(value = "/listTempSaveItemView")
	public ModelAndView listTempSaveItemView(@RequestParam(value = "searchConditionString", required = false) String searchConditionString, 	@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn, BoardItemSearchCondition searchCondition) {
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pagePerRecord", "pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		User user = this.readUser();
		Portal portal = this.readPortal();
		
		searchCondition.setUserId(user.getUserId());
		Boolean tempPopupYn = Boolean.FALSE;

		if (BooleanUtils.isTrue(popupYn)) {
			tempPopupYn = Boolean.TRUE;

		} else {
			tempPopupYn = Boolean.FALSE;
		}

		searchCondition.setPopupYn(tempPopupYn);
	
		SearchResult<BoardItem> searchResult = this.boardItemService.listTempSaveItemBySearchCondition(searchCondition);

		return new ModelAndView().addObject("searchResult", searchResult)
								 .addObject("searchCondition", searchResult.getSearchCondition())
								 .addObject("searchConditionString", tempSearchConditionString)
								 .addObject("popupYn", tempPopupYn)
								 .addObject("boardCode", new BoardCode());
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
	public ModelAndView createReplyBoardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @ValidEx(groups = { CreateReplyItemGroup.class }) BoardItem boardItem,
			AccessingResult accessResult, BindingResult result, SessionStatus status) {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/board/boardItem/createReplyBoardItemView.do?itemId="
					+ boardItem.getItemParentId());
		}

		User user = this.readUser();

		if(boardItem.getAnonymous()==1){
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), boardItem.getRegisterName());
		}else{
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		}

		BoardItem parent = this.boardItemService.readBoardItemMasterInfo(boardItem.getItemParentId());

		boardItem.setStartDate(parent.getStartDate());
		boardItem.setEndDate(parent.getEndDate());
		boardItem.setItemDisplay(parent.getItemDisplay());
		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);

		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());
		boardItem.setDisplayName(boardItem.getRegisterName());

		String itemId = this.boardItemService.createReplyBoardItem(boardItem, user);
		
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardItem/readBoardItemView.do")
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
	public ModelAndView updateBoardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @ValidEx(groups = { ModifyItemGroup.class }) BoardItem boardItem,
			AccessingResult accessResult, BindingResult result, SessionStatus status) {

		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/board/boardItem/updateBoardItemView.do?itemId="
					+ boardItem.getItemId());
		}

		User user = this.readUser();

		if(boardItem.getAnonymous()==1){
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), boardItem.getRegisterName());
		}else{
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
		}
		
		if(StringUtil.isEmpty(boardItem.getItemParentId())){
			// 타임존 처리
			Date startDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getStartDate());
			Date endDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getEndDate());
	
			endDate = DateUtils.addDays(endDate, 1);
			endDate = DateUtils.addSeconds(endDate, -1);
	
			
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@"+boardItem.getTempSave());
			
			
			boardItem.setStartDate(startDate);
			if(boardItem.getItemForever()==1){
				String[] patterns= {"yyyyMMdd"};
				try {
					boardItem.setEndDate(DateUtils.parseDate("99991231",patterns ));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				boardItem.setEndDate(endDate);
			}
		}
		if(boardItem.getDisStartDate()!=null){
			Date disStartDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getDisStartDate());
			Date disEndDate = this.timeZoneSupportService.convertServerTimeZone(boardItem.getDisEndDate());
			boardItem.setDisStartDate(disStartDate);
			boardItem.setDisEndDate(disEndDate);
		}
		
		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());
		
		String wordId   = boardItem.getWordId();
		String wordName = boardItem.getWordName();

		boardItem.setWordId(wordId);
		boardItem.setWordName(wordName);
		
		boardItem.setDisplayName(boardItem.getUpdaterName());

		this.boardItemService.updateBoardItem(boardItem, user);
		
		if("100006240370".equals(boardItem.getBoardId())){
			boardItemService.saveBoardItemWorkplace(boardItem);
		}
		
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/board/boardItem/readBoardItemView.do")
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
	public ModelAndView userDeleteBoardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		this.boardItemService.userDeleteBoardItem(boardItem);

		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");
		
		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/lightpack/board/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/board/boardItem/listBoardItemView.do";
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
	void adminMultiDeleteBoardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = { "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {

		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		try {
			this.boardItemService.adminMultiDeleteBoardItem(itemIds);
			
			// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");

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
	public ModelAndView adminDeleteBoardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = { "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			@RequestParam("itemId") String itemId, AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		this.boardItemService.adminDeleteBoardItem(boardItem);
		
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");

		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/lightpack/board/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/board/boardItem/listBoardItemView.do";
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
	
	@RequestMapping(value = "/mailSending")
	public @ResponseBody
	Integer mailSending(@RequestParam("itemId") String itemId) {
		Integer mailWaitYn = null;

		try {
			mailWaitYn = this.boardItemService.getMailWaitYn(itemId);
			if(mailWaitYn > 0){
				boardItemService.updateMailWaitYn(itemId);
				try {
					Date jobTime = new Date();
					
					if(this.fileUrl == null) {
						Properties prop = loadProperties("/configuration/common-conf.properties", Thread.currentThread().getContextClassLoader());
						Properties prop2 = loadProperties("/configuration/fileupload-conf.properties", Thread.currentThread().getContextClassLoader());
						this.fileUrl = prop.getProperty("ikep4.baseUrl") + prop2.getProperty("ikep4.support.fileupload.downloadurl");
					}
					//boardItemService.doBoardItemNotiJobSchedule(jobTime, interval, fileUrl);
					boardItemService.doBoardItemNotiInstant(itemId);
				} catch (Exception e) {
					log.error(e.getLocalizedMessage());
					throw new IKEP4ApplicationException("", e);
				}
			}
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return mailWaitYn;

	}
	
	
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
					IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

			if (userConfig == null) {
				userConfig = new UserConfig();
				// 개인화 설정 정보를 저장한다.
				userConfig.setLayout(layoutType);
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

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
			User user = (User)getRequestAttribute("ikep.user");		
			
			BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();

			searchCondition.setUserId(user.getUserId());
			searchCondition.setBoardId(boardId);
			searchCondition.setRecordCount(count);
			
			Board board = this.boardAdminService.readBoard(searchCondition.getBoardId());
			
			BoardPermission boardPermission = this.initPermission(user, board);
		
			searchCondition.setAdmin(boardPermission.getIsSystemAdmin());		
			
			boardItemList = this.boardItemService.listRecentBoardItem(searchCondition);

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

	private static long MILLSECS_PER_DAY = 1000 * 60 * 60 * 24;

	public static int getDayDiff(Date a, Date b) {
		long d = DateUtils.truncate(a, Calendar.DATE).getTime() - DateUtils.truncate(b, Calendar.DATE).getTime();
		return (int) (d / MILLSECS_PER_DAY);
	}
	
	/**
	 * 게시물 이동시 게시판 Tree 팝업
	 * 
	 * @param workspaceId
	 * @param orgBoardId
	 * @return
	 */
	@RequestMapping(value = "/viewBoardTree")
	public ModelAndView viewBoardTree(@RequestParam("boardParentId") String boardParentId,
			@RequestParam("orgBoardId") String orgBoardId) {
		String boardTreeJson = boardTreeJson();

		ModelAndView model = new ModelAndView();
		model.setViewName("lightpack/board/boardItem/viewBoardTree");
		model.addObject("orgBoardId", orgBoardId);
		model.addObject("boardTreeJson", boardTreeJson);
		return model;

	}
	
	/**
	 * 게시판 Tree Json
	 * 
	 * @param workspaceId
	 * @return
	 */
	private String boardTreeJson() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("boardRootId", DEFAULT_BOARD_ROOT_ID);

		List<Board> boardList = boardAdminService.listByBoardRootIdMap(map);
		return TreeMaker.init(boardList, "boardId", "boardParentId", "boardName").create().toJsonString();

	}
	
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
	
	private Properties loadProperties(String nameParam, ClassLoader loaderParam) {

		String name = nameParam;
		ClassLoader loader = loaderParam;

		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}

		Properties result = null;

		InputStream in = null;

		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}

			name = name.replace('.', '/');

			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}

			in = loader.getResourceAsStream(name);

			if (in != null) {
				result = new Properties();
				result.load(in);
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
					// 예외 무시
					log.error("InputStream of Property close error", ignore);
				}
			}
		}

		if (result == null) {
			throw new IllegalArgumentException("Could not load [" + name + "] as a classloader resource");
		}

		return result;
	}
	
	private String getBoardTypeFlag(String tempSave, Date startDate) {
		String BoardTypeFlag = "";
		
		if("1".equals(tempSave)) {
			BoardTypeFlag = "T";
		} 
//		  else if(startDate != null){
//			Date todayDate = new Date();
//			
//			String todayDateString = com.lgcns.ikep4.common.util.lang.DateUtil.getFmtDateString(todayDate, "yyyyMMddHHmmss");
//			String startDateString = com.lgcns.ikep4.common.util.lang.DateUtil.getFmtDateString(startDate, "yyyyMMddHHmmss");
//			
//			if(Long.parseLong(startDateString) > Long.parseLong(todayDateString)) {
//				BoardTypeFlag = "R";
//			}
//		}
		
		return BoardTypeFlag;
	}
	
	/**
	 * 모바일 게시판 메뉴 관리
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getBoardMenuList")
	public ModelAndView getBoardMenuList(
			 BoardItemSearchCondition searchCondition,
			 @RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "isSystemAdmin", defaultValue = "false") Boolean isSystemAdmin,
			@CookieValue(value = "viewMode", defaultValue = "") String viewMode

	) {
		
		if(!"".equals(viewMode)) searchCondition.setViewMode(viewMode);
		String tempSearchConditionString = null;
		if(isSystemAdmin) searchCondition.setAdmin(isSystemAdmin);

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "boardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "wordId", "admin");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		User user = this.readUser();

		Portal portal = this.readPortal();

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

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
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 게시판 권한 정보를 설정한다.
		if(searchCondition.getAdmin()==null) searchCondition.setAdmin(false);
		
		BoardPermission boardPermission = new BoardPermission(searchCondition.getAdmin(), true, true);
		searchCondition.setUserId(user.getUserId());

		SearchResult<BoardItem> searchResult =null;
		searchResult = this.boardItemService.getBoardMenuList(searchCondition);
		
		
		// #########################임시로........... 해논 구간.#########################
		List<Board> boardList = this.boardAdminService.listByBoardRootIdPermission("0", this.readPortal().getPortalId(),user.getUserId());
		List<BoardItem> boardItemList = new ArrayList<BoardItem>();
		for(Board entity : boardList) {
			BoardItem boardItem = new BoardItem();
			if(!"2".equals(entity.getBoardType())) {
				boardItem.setBoardId(entity.getBoardId());
				boardItem.setBoardName(entity.getBoardName());
				boardItemList.add(boardItem);
			} 
		}
		List<BoardItem> presentBoardItemList = this.boardItemDao.getPresentBoardMenuList(searchCondition.getUserId());
		for(BoardItem boardItem : boardItemList) {
			for(BoardItem boardItem1 : presentBoardItemList) {
				if(boardItem.getBoardId().equals(boardItem1.getBoardId())) {
					boardItem.setTempSave("1");
				}
			}
		}
		searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		//######################### 여기까지 #########################
		
		
		ModelAndView modelAndView = new ModelAndView().addObject("user", user)
				.addObject("boardCode", new BoardCode()).addObject("userConfig", userConfig)
				.addObject("itemId", itemId).addObject("permission", boardPermission)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", Boolean.FALSE);
 
		return modelAndView;
	}
	
	/**
	 * 모바일 게시판 메뉴 관리 UPDATE
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/updateBoardMenuList")
	public @ResponseBody
	void updateBoardMenuList(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Board.BOARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Board.BOARD_ACL_CLASS_NAME, operationName = { "MANAGE" }, resourceName = "boardId") }) @RequestParam("boardId") String boardId,
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {
		
		User user = this.readUser();
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", user.getUserId());
		
		List<String> itemList = new ArrayList<String>();
		for(String item : itemIds) {
			itemList.add(item);
		}
		param.put("itemIds",itemList );
		this.boardItemService.updateBoardMenuList(param);
	}

}
