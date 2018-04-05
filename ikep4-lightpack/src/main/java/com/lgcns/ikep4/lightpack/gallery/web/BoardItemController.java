/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.lightpack.gallery.model.BoardItem;
import com.lgcns.ikep4.lightpack.gallery.model.BoardItem.CreateItemGroup;
import com.lgcns.ikep4.lightpack.gallery.model.BoardItem.ModifyItemGroup;
import com.lgcns.ikep4.lightpack.gallery.model.BoardReference;
import com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.gallery.service.BoardItemService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시글 컨트롤 클래스
 */
@Controller("pfBoardItemController")
@RequestMapping(value = "/lightpack/gallery/boardItem")
public class BoardItemController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());



	/** The board item service. */
	@Autowired
	private BoardItemService boardItemService;

	/** The acl service. */
	@Autowired
	private ACLService aclService;

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
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User)this.getRequestAttribute("ikep.user");
	}
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private Portal readPortal() {
		return (Portal)this.getRequestAttribute("ikep.portal");
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
			@RequestParam(value="targetUserId", required=false) String targetUserId,
			@RequestParam(value="itemId", required=false) String itemId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false") Boolean popupYn

	) {
		Long past = System.currentTimeMillis();

		String tempSearchConditionString = null;
		searchCondition.setTargetUserId(targetUserId);
		
		if(StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition,
					"targetUserId",
					"pageIndex",
					"searchColumn",
					"searchWord",
					"sortColumn",
					"sortType",
					"viewMode",
					"popupYn"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		User user = this.readUser();
		//Portal portal = this.readPortal();

		Boolean tempPopupYn = Boolean.FALSE;
		searchCondition.setPopupYn(tempPopupYn);


		searchCondition.setUserId(user.getUserId());
		SearchResult<BoardItem> searchResult = this.boardItemService.listBoardItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView()
		.addObject("user", user)
		.addObject("boardCode", new BoardCode())
		.addObject("targetUserId", targetUserId)
		.addObject("itemId", itemId)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchResult", searchResult)
		.addObject("searchConditionString", tempSearchConditionString)
		.addObject("popupYn", tempPopupYn);
		
		Long present = System.currentTimeMillis();

		this.log.info("목록 갯수" + searchResult.getSearchCondition().getPagePerRecord() + "소모시간 : " + (present - past));

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
	public ModelAndView readBoardItemView(BoardItemSearchCondition searchCondition,
			@RequestParam("targetUserId") String targetUserId,
			@RequestParam("pageIndex") String pageIndex,
			@RequestParam("itemId") String itemId,
			@RequestParam(value="layoutType", defaultValue="layoutNormal", required=false) String layoutType,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false", required=false) Boolean popupYn,
			@RequestParam(value="portletYn", defaultValue="false", required=false) Boolean portletYn) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		//조회 카운트를 증가 시킨다.
		BoardReference boardReference = new BoardReference();

		boardReference.setRegisterId(user.getUserId());
		boardReference.setItemId(itemId);

		this.boardItemService.updateHitCount(boardReference);

		//게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		//이동할 뷰를 선택한다.
		String viewName = "";

		viewName = "lightpack/gallery/boardItem/readBoardItemView";

		//ObjectMapper mapper = new ObjectMapper();

		//파일 목록을 JSON으로 변환한다.
		//String fileDataListJson = null;

		//if(boardItem.getFileDataList()!= null ) {
		//	fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		//}
		// 파일 정보 조회(기본이미지)
		//List<FileData> fileDataList = fileService.getItemFile(boardItem.getItemId(), "N");
		//boardItem.setFileDataList(fileDataList);
		
		// 갤러리 목록
		//BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		/*String tempSearchConditionString = null;
		if(StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition,
					"targetUserId",
					"pageIndex",
					"searchColumn",
					"searchWord",
					"sortColumn",
					"sortType",
					"viewMode",
					"popupYn"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}*/
		Boolean tempPopupYn = Boolean.FALSE;
		searchCondition.setPopupYn(tempPopupYn);
		
		searchCondition.setTargetUserId(targetUserId);
		searchCondition.setActionType("search");
		SearchResult<BoardItem> searchResult = this.boardItemService.listBoardItemBySearchConditionView(searchCondition);
		
		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String mailUrl = prop.getProperty("ikep4.server.url.mail");
		String epUrl = prop.getProperty("ikep4.server.url.ep");
		
		ModelAndView modelAndView = new ModelAndView(viewName)
		.addObject("boardItem", boardItem)
		.addObject("layoutType", layoutType)
		.addObject("user", user)
		.addObject("mailUrl", mailUrl)
		.addObject("epUrl", epUrl)		
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("portletYn", portletYn)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchResult", searchResult)		
		.addObject("targetUserId",targetUserId);
		//.addObject("fileDataListJson", fileDataListJson);

		if(popupYn) {
			modelAndView.setViewName("lightpack/gallery/boardItem/readBoardItemPopupView");
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
	public ModelAndView createBoardItemView(@RequestParam("targetUserId") String targetUserId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false", required=false) Boolean popupYn
	) throws JsonGenerationException, JsonMappingException, IOException {

		User user = this.readUser();

		BoardItem boardItem = null;

		if(this.getModelAttribute("boardItem") == null) {
			Date date = Calendar.getInstance().getTime();
			boardItem = new BoardItem();
		} else {
			boardItem = (BoardItem)this.getModelAttribute("boardItem");
		}

		ObjectMapper mapper = new ObjectMapper();

		//파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if(boardItem.getFileDataList()!= null ) {
			fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		}
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		return this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("targetUserId",targetUserId)
		.addObject("user", user)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
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
	public ModelAndView updateBoardItemView(@RequestParam("targetUserId") String targetUserId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false", required=false) Boolean popupYn,
			@RequestParam("itemId") String itemId
	) throws JsonGenerationException, JsonMappingException, IOException {


		User user = this.readUser();

		BoardItem boardItem = null;

		if(this.getModelAttribute("board") == null) {
			boardItem = this.boardItemService.readBoardItem(itemId);

		} else {
			boardItem = (BoardItem)this.getModelAttribute("boardItem");
		}
		//ObjectMapper mapper = new ObjectMapper();

		//파일 목록을 JSON으로 변환한다.
		//String fileDataListJson = null;

		//if(boardItem.getFileDataList()!= null ) {
		//	fileDataListJson = mapper.writeValueAsString(boardItem.getFileDataList());
		//}
		// 파일 정보 조회(기본이미지)
		//List<FileData> fileDataList = fileService.getItemFile(boardItem.getItemId(), "N");
		//boardItem.setFileDataList(fileDataList);
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return  this.bindResult(new ModelAndView()
		.addObject("boardItem", boardItem)
		.addObject("targetUserId",targetUserId)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("useActiveX", useActiveX));
		//.addObject("fileDataListJson", fileDataListJson));
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
	public ModelAndView createBoardItem(@ValidEx(groups={CreateItemGroup.class}) BoardItem boardItem,
			BindingResult result,
			SessionStatus status, HttpServletRequest request) {

		User user = this.readUser();

		boardItem.setPortalId(user.getPortalId());

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		boardItem.setItemDelete(BoardItem.STATUS_SERVICING);

		//String itemId = this.boardItemService.createBoardItem(boardItem, user);

		String itemId = null;
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			itemId = this.boardItemService.createBoardItem(boardItem, user,fileList);
			//portalLoginImageService.updateLoginImage(fileList, editorAttach, user, portalId);
		} catch (Exception e) {  
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			}		
		}
		status.setComplete();

		String viewName = null;

		if(boardItem.getPopupYn()) {
			viewName = "redirect:/lightpack/gallery/boardItem/listBoardItemView.do";

		} else {
			viewName = "redirect:/lightpack/gallery/boardItem/readBoardItemView.do";
		}

		return new ModelAndView(viewName)
		.addObject("itemId", itemId)
		.addObject("targetUserId",user.getUserId())
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("popupYn", boardItem.getPopupYn())	
		.addObject("pageIndex", 1);	
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
	public ModelAndView updateBoardItem(@ValidEx(groups={ModifyItemGroup.class}) BoardItem boardItem,
			BindingResult result,
			SessionStatus status, HttpServletRequest request) {

		if (result.hasErrors()) {
			this.setErrorAttribute("boardItem", boardItem, result);
			return new ModelAndView("forward:/lightpack/gallery/boardItem/updateBoardItemView.do?itemId=" + boardItem.getItemId());
		}

		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());


		//포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");

			
			this.boardItemService.updateBoardItem(boardItem, user,fileList);
		} catch (Exception e) {  
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			} 
		}
		
		//this.boardItemService.updateBoardItem(boardItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/gallery/boardItem/readBoardItemView.do")
		.addObject("targetUserId",user.getUserId())
		.addObject("itemId", boardItem.getItemId())
		.addObject("searchConditionString", boardItem.getSearchConditionString())
		.addObject("popupYn",  boardItem.getPopupYn())
		.addObject("pageIndex",  1);
	}


	/**
	 * 게시글 삭제 처리 동기 컨트롤 메서드
	 *
	 * @param itemId 삭제 대상 게시글 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/userDeleteBoardItem")
	public ModelAndView userDeleteBoardItem(@RequestParam("targetUserId") String targetUserId,
			@RequestParam("itemId") String itemId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", required=false) Boolean popupYn
	) {

		User user = this.readUser();

		BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(itemId);

		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		this.boardItemService.userDeleteBoardItem(boardItem);


		String viewName = null;

		if(popupYn) {
			viewName = "redirect:/lightpack/gallery/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/gallery/boardItem/listBoardItemView.do";
		}

		return new ModelAndView(viewName)
		.addObject("targetUserId",targetUserId)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 일괄삭제 처리 비동기 컨트롤 메서드
	 *
	 * @param itemIds 삭제대상 게시글 ID 배열
	 */
	@RequestMapping(value = "/adminMultiDeleteBoardItem")
	public @ResponseBody void adminMultiDeleteBoardItem(@RequestParam("targetUserId") String targetUserId,
			@RequestParam("itemId") String[] itemIds
	) {

		try {
			this.boardItemService.adminMultiDeleteBoardItem(itemIds);

		} catch(Exception exception) {
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
	public ModelAndView adminDeleteBoardItem(@RequestParam("targetUserId") String targetUserId,
			@RequestParam("itemId") String itemId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", required=false) Boolean popupYn
	) {

		BoardItem boardItem = this.boardItemService.readBoardItem(itemId);

		this.boardItemService.adminDeleteBoardItem(boardItem);

		String viewName = null;

		if(popupYn) {
			viewName = "redirect:/lightpack/gallery/boardItem/boardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/gallery/boardItem/listBoardItemView.do";
		}

		return new ModelAndView(viewName)
		.addObject("targetUserId",targetUserId)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 삭제의 경우 팝업 모드일 경우 삭제 후 부모 창에 있는 게시글목록을 갱신해야 하는데 사용하는 URL
	 *
	 * @param itemId the item id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/boardItemResult")
	public ModelAndView boardItemResult(
			@RequestParam("targetUserId") String targetUserId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false", required=false) Boolean popupYn) {

		return new ModelAndView()
		.addObject("targetUserId",targetUserId)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn);
	}



}
