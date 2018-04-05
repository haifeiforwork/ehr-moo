/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.web;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.kms.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardLinereplyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 댓글 컨트롤 클래스
 */
@Controller("kmsBoardLinereplyController")
@RequestMapping(value = "/collpack/kms/board/boardLinereply")
public class BoardLinereplyController extends BaseController {

	/** The board admin service. */
	@Autowired
	private BoardAdminService boardAdminService;

	/** The board linereply service. */
	@Autowired
	private BoardLinereplyService boardLinereplyService;

	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	/** The board item service. */
	@Autowired
	private BoardItemService boardItemService;
	
	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;

	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 *
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("BBS", user.getUserId());
	}
	
	public boolean isKmsAdmin(User user) {
		return this.aclService.isSystemAdmin("Kms", user.getUserId());
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
	 * 댓글 목록 조회 화면 컨트롤 메서드
	 *
	 * @param searchCondition 댓글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBoardLinereplyView")
	public ModelAndView listBoardLinereplyView(BoardLinereplySearchCondition searchCondition, BindingResult result, SessionStatus status) {
		User user = this.readUser();
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Boolean isKmsAdmin = this.isKmsAdmin(user);
		Board board = this.boardAdminService.readBoard(searchCondition.getBoardId());
		//Board board = null;

		SearchResult<BoardLinereply> searchResult = this.boardLinereplyService.listBoardLinereplyBySearchCondition(searchCondition);

		return new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("user", user)
		.addObject("board", board)
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("isKmsAdmin", isKmsAdmin)
		.addObject("searchCondition", searchResult.getSearchCondition());
	}


	/**
	 * 댓글 생성 비동기 컨트롤 메서드
	 *
	 * @param boardLinereply 생성 대상 댓글  모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/createBoardLinereply")
	public @ResponseBody void createBoardLinereply(BoardLinereply boardLinereply, BindingResult result, SessionStatus status) {

		try {
			if(result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}
			
			Properties commonprop = PropertyLoader
			.loadProperties("/configuration/common-conf.properties");

			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());
			
			if(user.getJobClassCode().equals("11")){
				boardLinereply.setScore("3");
			}else if(user.getJobClassCode().equals("21")){
				boardLinereply.setScore("2");
			}else{
				boardLinereply.setScore("1");
			}
			boardLinereply.setFlag("1");

			this.boardLinereplyService.createBoardLinereply(boardLinereply);
			this.boardLinereplyService.updateScore(boardLinereply);
			
			BoardItem boardItem = boardItemService.readBoardItem(boardLinereply.getItemId());
			String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = "";
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	=DateUtil.getFmtDateString(boardItem.getRegistDate(), "yyyy-MM-dd")+"에 등록한 정보에 댓글이 등록되었습니다.";
			String contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[댓글 작성자 : "+user.getTeamName()+" "+user.getUserName()+" "+user.getJobTitleName()+"]<br>[내용 : "+boardLinereply.getContents()+"]";
			url += boardItem.getRegisterId();
			//메시지 보내기
			atmc.addMessage(boardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title,"smspop");
			atmc.send();


		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		} finally {
			status.setComplete();
		}


	}


	/**
	 * 댓글의 답변 생성 비동기 컨트롤 메서드
	 * 
	 * @param boardLinereply 생성 대상 댓글의 답변 모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/createReplyBoardLinereply")
	public @ResponseBody void createReplyBoardLinereply(BoardLinereply boardLinereply, BindingResult result, SessionStatus status) {
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}

			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			this.boardLinereplyService.createReplyBoardLinereply(boardLinereply);

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		} finally {
			status.setComplete();
		}

	}


	/**
	 * 댓글 수정 비동기 컨트롤 메서드
	 *
	 * @param boardLinereply 수정 대상 댓글 모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/updateBoardLinereply")
	public @ResponseBody void updateBoardLinereply(BoardLinereply boardLinereply, BindingResult result, SessionStatus status) {
		try {
			
			Properties commonprop = PropertyLoader
			.loadProperties("/configuration/common-conf.properties");
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}
			BoardLinereply readBoardLinereply = this.boardLinereplyService.readBoardLinereply(boardLinereply.getLinereplyId());

			readBoardLinereply.setContents(boardLinereply.getContents());

			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			this.boardLinereplyService.updateBoardLinereply(readBoardLinereply);
			
			/*BoardItem boardItem = boardItemService.readBoardItem(readBoardLinereply.getItemId());
			String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = "";
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	=DateUtil.getFmtDateString(boardItem.getRegistDate(), "yyyy-MM-dd")+"에 등록한 정보에 댓글이 수정되었습니다.";
			String contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[댓글 작성자 : "+user.getTeamName()+" "+user.getUserName()+" "+user.getJobTitleName()+"]<br>[내용 : "+readBoardLinereply.getContents()+"]";
			url += boardItem.getRegisterId();
			//메시지 보내기
			atmc.addMessage(boardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title);
			atmc.send();*/

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		} finally {
			status.setComplete();
		}

	}


	/**
	 * 댓글 작성자 모드 비동기 컨트롤 메서드
	 *
	 * @param boardLinereply 삭제 대상 댓글 모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/userDeleteBoardLinereply")
	public @ResponseBody void userDeleteBoardLinereply(BoardLinereply boardLinereply, BindingResult result, SessionStatus status) {
		try {
			
			Properties commonprop = PropertyLoader
			.loadProperties("/configuration/common-conf.properties");
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}
			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			this.boardLinereplyService.userDeleteBoardLinereply(boardLinereply);
			
			/*BoardItem boardItem = boardItemService.readBoardItem(boardLinereply.getItemId());
			String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = "";
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	=DateUtil.getFmtDateString(boardItem.getRegistDate(), "yyyy-MM-dd")+"에 등록한 정보에 댓글이 삭제되었습니다.";
			String contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[댓글 삭제자 : "+user.getTeamName()+" "+user.getUserName()+" "+user.getJobTitleName()+"]";
			url += boardItem.getRegisterId();
			//메시지 보내기
			atmc.addMessage(boardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title);
			atmc.send();*/

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}


	/**
	 * 댓글 관리자 모드 삭제 비동기 컨트롤 메서드
	 *
	 * @param itemId 삭제 대상 댓글의 게시글 ID
	 * @param linereplyId 삭제 대상 댓글 ID
	 */
	@RequestMapping(value = "/adminDeleteBoardLinereply")
	public @ResponseBody void adminDeleteBoardLinereply(@RequestParam("itemId") String itemId, @RequestParam("linereplyId") String linereplyId) {
		try {
			this.boardLinereplyService.adminDeleteBoardLinereply(itemId, linereplyId);

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}
}
