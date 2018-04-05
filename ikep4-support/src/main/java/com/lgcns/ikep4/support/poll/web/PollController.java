/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.web;

import java.util.Calendar;
import java.util.Date;
//import java.util.List;
import org.apache.commons.lang.time.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.SearchResult;

import com.lgcns.ikep4.support.poll.constants.PollConstants;
//import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.BoardCode;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;
import com.lgcns.ikep4.support.poll.service.PollService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
 

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙
 * @version $Id: PollController.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Controller
@RequestMapping(value = "/support/poll")
public class PollController extends PollBaseController {
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	ACLService aclService;	
	 
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;	 

	/**
	 * 투표등록,수정 화면
	 * 
	 * @param pollId
	 * @param mode
	 * @param isAdmin
	 * @return
	 */	
	@RequestMapping(value = "/formPoll.do")
	public String getForm(String pollId, @RequestParam(value = "mode", required = false) String mode, Model model, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		Poll poll = null;

		if (pollId != null) {
			poll = new Poll();
			poll.setPollId(pollId);

			poll = pollService.readByPoll(poll);
			//권한체크
			accessCheck(isAdmin, poll.getRegisterId());					
		} else {
			poll = new Poll();
			Date date = Calendar.getInstance().getTime();
			
			poll.setStartDate(date);
			poll.setEndDate(DateUtils.addDays(date, PollConstants.POLL_PERIOD)); //투표기간디폴트 7일
		}
		model.addAttribute("poll", poll);
		model.addAttribute("mode", mode);
		return "/support/poll/pollForm";
	}
	
	/**
	 * 투표결과보기 화면
	 * 
	 * @param pollId
	 * @param viewMode
	 * @param docPopup
	 * @param isAdmin
	 * @return
	 */	
	@RequestMapping(value = "/viewPoll.do")
	public String getView(String pollId, String viewMode 
			,@RequestParam(value = "docPopup", required = false, defaultValue="false") String docPopup
			, Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		User user = (User) getRequestAttribute("ikep.user");
		Poll poll = new Poll();
		Date date = Calendar.getInstance().getTime();
		
		if (pollId != null) {		
			poll.setPollId(pollId);
			poll.setAnswerUserId(user.getUserId());
			poll.setGroupId(user.getGroupId());		
			poll = pollService.readByPoll(poll);	

			poll.setToDate(date);
		} else {
			poll = new Poll();
		}
		poll.setViewMode(viewMode);
		model.addAttribute("docPopup", docPopup);
		model.addAttribute("poll", poll);

		if (pollId != null && "1".equals(poll.getPermissionType()) && "0".equals(poll.getIsTarget()) ) {		
			//권한체크-비공개투표이고,해당대상자가 아닐 경우 결과보기 제한.
			accessCheck(isAdmin, poll.getRegisterId());
		}		
		return (docPopup.equals("false"))?"/support/poll/pollView":"/support/poll/pollViewNoTile";
	}
	
	/**
	 * 투표입력
	 * 
	 * @param pollId
	 * @param result
	 * @param model
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/createPoll.do", method = RequestMethod.POST)
	public String onSubmit(Poll poll, BindingResult result, SessionStatus status, ModelMap model, @ModelAttribute("isAdmin") boolean isAdmin) {

		User user = (User) getRequestAttribute("ikep.user");
		poll.setPortalId(user.getPortalId());		
		poll.setRegisterId(user.getUserId());
		poll.setRegisterName(user.getUserName());
		
		if (result.hasErrors()) {
			return "redirect:/poll/formPoll.do";
		}
		String pollId = poll.getPollId();
		//타임존 처리
		poll.setStartDate(DateUtils.addHours(poll.getStartDate(),0));
		poll.setEndDate(DateUtils.addHours(poll.getEndDate(),PollConstants.POLL_END_HOUR)); //투표종료날짜 23시까지

		Date startDate = this.timeZoneSupportService.convertServerTimeZone(poll.getStartDate());
		Date endDate = this.timeZoneSupportService.convertServerTimeZone(poll.getEndDate());	

		poll.setStartDate(startDate);
		poll.setEndDate(endDate);
	
		if (StringUtil.isEmpty(pollId)) {	//등록일때
			pollId = pollService.create(poll); 		
		} else { //수정일때
			poll.setUpdaterId(user.getUserId());
			poll.setUpdaterName(user.getUserName());			
			pollService.update(poll);
		}			
		 
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return "redirect:pollList.do?isComplete=0";
	}
	
	/**
	 * ajax를 이용한 투표하기
	 * @param poll
	 * @return
	 */
	@RequestMapping("/applyPoll.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String applyPoll(Poll poll) {
		User user = (User) getRequestAttribute("ikep.user");
		poll.setAnswerUserId(user.getUserId());
		
		try {
			pollService.insertResult(poll);
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
		
	}
	/**
	 * ajax를 이용한 투표 마감 오픈
	 * @param poll
	 * @return
	 */
	@RequestMapping("/completePoll.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String completePoll(Poll poll) {
		User user = (User) getRequestAttribute("ikep.user");
		poll.setUpdaterId(user.getUserId());
		poll.setUpdaterName(user.getUserName());
		
		try {
			pollService.updateStatus(poll);
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
		
	}

	/**
	 * ajax를 이용한 투표 삭제
	 * @param poll
	 * @return
	 */
	@RequestMapping("/deletePoll.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String deletePoll(Poll poll) {
		try {
			pollService.delete(poll.getPollId());
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}
	
	/**
	 * 투표 삭제(투표관리화면)
	 * @param poll
	 * @return
	 */
	@RequestMapping("/deleteMultiPoll.do")
	@ResponseStatus(HttpStatus.OK)
	public String deleteMultiPoll(@RequestParam("checkboxPollItem") String[] checkboxPollItem) {
		for (int i = 0; i < checkboxPollItem.length; i++) {
			pollService.delete(checkboxPollItem[i]);
		}
		return "redirect:pollAdminList.do";
	}
	
	/**
	 * 투표목록(투표참여,My Online Poll)
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/pollList.do")
	public ModelAndView listBoardView(PollReceiverSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {   
		if ( searchCondition.getIsComplete() == null || "".equals(searchCondition.getIsComplete()) ) {
			searchCondition.setIsComplete("1"); //default:ing
		}
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setAnswerUserId(user.getUserId());
		searchCondition.setGroupId(user.getGroupId());
		searchCondition.setPortalId(user.getPortalId());

		if ( isAdmin() ) {
			searchCondition.setIsAdmin("1");
		} else {
			searchCondition.setIsAdmin("0");
		}
		
		SearchResult<Poll> searchResult = this.pollService.listPollReceiverBySearchCondition(searchCondition);   
		Date toDate = new Date();

		return new ModelAndView("/support/poll/pollList")
		.addObject("searchResult", searchResult)
		.addObject( "toDate", toDate )
		.addObject("searchCondition", searchResult.getSearchCondition());				
	}

	/**
	 * 투표관리(관리자화면)
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/pollAdminList.do")
	public ModelAndView listAdminView(PollReceiverSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setAnswerUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		
		SearchResult<Poll> searchResult = this.pollService.listPollAdminBySearchCondition(searchCondition);   


		BoardCode boardCode = new BoardCode();
		
		return new ModelAndView("/support/poll/pollAdminList")
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("boardCode", boardCode);
	}
	
	/**
	 * ajax이용한 리스트 가져오기 (추가10건 더보기)
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping("/listMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listMore(PollReceiverSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {		
		User user = (User) getRequestAttribute("ikep.user");
		
		if ( searchCondition.getIsComplete() == null || "".equals(searchCondition.getIsComplete()) ) {
			searchCondition.setIsComplete("1"); //default:ing
		}
		
		searchCondition.setAnswerUserId(user.getUserId());
		searchCondition.setGroupId(user.getGroupId());
		searchCondition.setPortalId(user.getPortalId());
		
		
		if ( isAdmin() ) {
			searchCondition.setIsAdmin("1");
		} else {
			searchCondition.setIsAdmin("0");
		}
		
		SearchResult<Poll> searchResult = this.pollService.listPollReceiverBySearchCondition(searchCondition);  
		
		ModelAndView mav = new ModelAndView("/support/poll/pollMoreList");
		
		try {
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		return mav;
	}	
	
	/**
	 * ajax이용한 이미 투표한 건수 가져오기
	 * @param poll
	 * @param isAdmin
	 * @return
	 */	
	@RequestMapping(value = "/checkAlreadyApply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody int checkAlreadyApply(Poll poll, @ModelAttribute("isAdmin") boolean isAdmin) {
		int checkCount = 0;
		try {
			checkCount = pollService.checkAlreadyApply(poll);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		return checkCount;
	}	

}
