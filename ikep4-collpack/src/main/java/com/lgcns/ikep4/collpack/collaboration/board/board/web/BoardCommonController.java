/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.web;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Counsel;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 게시판 공통 화면 컨트롤러
 *
 * @author 최현식
 * @version $Id: BoardCommonController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller("wsBoardCommonController")
@RequestMapping(value = "/collpack/collaboration/board/boardCommon")
public class BoardCommonController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;
	
	@Autowired
	private BoardItemService boardItemService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	private static final String DEFAULT_BOARD_ROOT_ID = "0";
	/** The acl service. */
	@Autowired
	private ACLService aclService;

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User)this.getRequestAttribute("ikep.user");
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
	 * 게시글 목록 조회 화면 컨트롤 메서드
	 *
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/boardView")
	public ModelAndView boardItemView(
			@RequestParam(value="workspaceId") String workspaceId,
			@RequestParam(value="boardRootId", defaultValue="0", required=false) String boardRootId,
			@RequestParam(value="boardId", required=false) String boardId,
			@RequestParam(value="itemId",  required=false) String itemId
	
	) {
		User user = this.readUser();
		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("boardRootId", DEFAULT_BOARD_ROOT_ID);
		List<Board> boardList = this.boardAdminService.listByBoardRootId(map);

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		ModelAndView modelAndView = new ModelAndView()
		.addObject("boardList", boardList)
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("boardId", boardId)
		.addObject("itemId", itemId)
		.addObject("user", user);

		return modelAndView;
	}


	/**
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param docPopup the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readLinkBoardView")
	public ModelAndView readLinkBoardView(@RequestParam("boardId") String boardId) {
		Board board = this.boardAdminService.readBoard(boardId);

		return new ModelAndView().addObject("board", board);
	}
	
	@RequestMapping(value = "/listConsultationRequestView")
	public ModelAndView listConsultationRequestView(String startPeriod, String endPeriod, BoardItemSearchCondition boardItemSearchCondition) {
		
		Date today = null;
		Date startDate = null;
		Date endDate = null;
		String tempMonth = "";
		if(StringUtil.isEmpty(startPeriod) || StringUtil.isEmpty(endPeriod)) {
			
			Date clientNow = timeZoneSupportService.convertTimeZone();
			today = DateUtils.truncate(clientNow, Calendar.DATE);
			
			Calendar cal1 = Calendar.getInstance(); 
			Calendar cal2 = Calendar.getInstance(); 
			
			cal1.add(Calendar.MONTH, -1); 
			cal2.add(Calendar.MONTH, +0); 
			
			startDate = cal1.getTime();
			endDate = cal2.getTime();
		} else {
			
			String startYear = startPeriod.substring(0, 4);
			String startMonth = startPeriod.substring(5, 7);
			String startDay = startPeriod.substring(8, 10);
			
			String endYear = endPeriod.substring(0, 4);
			String endMonth = endPeriod.substring(5, 7);
			String endDay = endPeriod.substring(8, 10);
			
			startDate = DateUtil.toDate(startYear + startMonth + startDay);
			startDate = DateUtils.truncate(startDate, Calendar.DATE);
			
			endDate = DateUtil.toDate(endYear + endMonth + endDay);
			endDate = DateUtils.truncate(endDate, Calendar.DATE);
		}
		boardItemSearchCondition.setStartDate(DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
		boardItemSearchCondition.setEndDate(DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		
		
		SearchResult<Map<String, Object>> searchResult = boardItemService.consultationRequestList(boardItemSearchCondition);
		
		BoardCode boardCode = new BoardCode();

		return new ModelAndView().addObject("searchResult", searchResult)
		.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"))
		.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"))
		.addObject("boardCode", boardCode)
		.addObject("searchCondition", boardItemSearchCondition);
		
	}
	
	@RequestMapping(value = "/appointmentPopupView")
	public ModelAndView appointmentPopupView() {
		return new ModelAndView().addObject("nowHour", DateUtil.getToday("HH"));
	}
		
	@RequestMapping(value = "/consultationRequest.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String consultationRequest(Counsel counsel) {	
		User user = this.readUser();
		counsel.setRegisterId(user.getUserId());
		counsel.setRegisterName(user.getUserName());
		
		boardItemService.consultationRequest(counsel, user);
		
		return "success";
	}
}
