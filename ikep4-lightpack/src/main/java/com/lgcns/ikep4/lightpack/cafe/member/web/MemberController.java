/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.member.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.base.MailUtil;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeCode;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeConstants;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.search.MemberSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.portal.main.model.Portal;


/**
 * Collaboration Member Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeMemberController.java 3908 2011-03-24 00:30:00Z happyi1018
 *          $
 */

@Controller("cfMemberController")
@RequestMapping(value = "/lightpack/cafe/member")
@SessionAttributes("cafe")
public class MemberController extends BaseController {

	@Autowired
	private CafeService cafeService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private MessageOutsideService messageOutsideService;
	
	@Autowired
	private CacheService cacheService;

	/**
	 * 회원 가입신청 폼
	 * 
	 * @param searchCondition
	 * @param cafeId
	 * @return
	 */
	@RequestMapping(value = "/createCafeMemberView.do", method = RequestMethod.GET)
	public ModelAndView createCafeMemberView(MemberSearchCondition searchCondition,
			@RequestParam("cafeId") String cafeId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/member/createCafeMemberView");

		Cafe cafe = new Cafe();
		cafe.setCafeId(cafeId);
		cafe.setPortalId(user.getPortalId());

		cafe = cafeService.read(cafeId);
		Member member = new Member();
		member.setMemberId(user.getUserId());
		member.setMemberName(user.getUserName());

		modelAndView.addObject("cafe", cafe);
		modelAndView.addObject("member", member);
		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 회원 가입 신청 내용 저장
	 * 
	 * @param member
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createCafeMember.do", method = RequestMethod.POST)
	public @ResponseBody
	String createCafeMember(@Valid Member member, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");

		member.setMemberId(user.getUserId());
		member.setMemberLevel(CafeConstants.MEMBER_STATUS_WAIT);
		member.setJoinType(CafeConstants.MEMBER_JOIN_TYPE_USER);
		member.setRegisterId(user.getUserId());
		member.setRegisterName(user.getUserName());
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		memberService.create(member);

		status.setComplete();
		return member.getCafeId();
	}

	/**
	 * 회원 정보 조회
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/readCafeMemberView.do", method = RequestMethod.GET)
	public ModelAndView readCafeMemberView(MemberSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/member/readCafeMemberView");
		Member member = new Member();
		member.setCafeId(searchCondition.getCafeId());
		member.setMemberId(searchCondition.getMemberId());

		if (searchCondition.getMemberId() != null) {
			member = memberService.read(member);
			modelAndView.addObject("member", member);
		}
		modelAndView.addObject("searchCondition", searchCondition);
		return modelAndView;
	}

	/**
	 * 회원탈퇴
	 * 
	 * @param cafeIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteCafeMemberAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean deleteCafeMemberAjax(@RequestParam("cafeIds") List<String> cafeIds, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		try {
			memberService.physicalDelete(cafeIds, user);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * 회원 탈퇴신청시
	 * 
	 * @param cafeId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteMemberAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean deleteMemberAjax(@RequestParam("cafeId") String cafeId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		try {
			Member member = new Member();
			member.setCafeId(cafeId);
			member.setMemberId(user.getUserId());

			memberService.physicalDelete(member);
			
			//My Cage  포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-myCafe-portlet", "Cachemode-myCafe-portlet", "Elementkey-myCafe-portlet", member.getMemberId());
				
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * 나의 Collaboration 목록에서 디폴트 워크스페이스 설정
	 * 
	 * @param cafeId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeDefaultSet.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateCafeDefaultSet(@RequestParam("cafeId") String cafeId, SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		try {
			/**
			 * 기존에 설정된 디폴트 워크스페이스 초기화 새로운 WSID 로 디폴트 설정
			 */
			memberService.updateCafeDefaultSet(portal.getPortalId(), user, cafeId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
	}

	/**
	 * 회원 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/listCafeMemberView.do")
	public ModelAndView listCafeMemberView(MemberSearchCondition searchCondition) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		ModelAndView modelAndView = new ModelAndView();

		Cafe cafe = new Cafe();
		cafe.setPortalId(portal.getPortalId());
		cafe.setCafeId(searchCondition.getCafeId());
		cafe = cafeService.read(searchCondition.getCafeId());

		modelAndView.setViewName("lightpack/cafe/member/listCafeMemberView");
		if (searchCondition.getListType().equals("Member")) {// 전체회원관리
			searchCondition.setMemberLevel("");
		} else if (searchCondition.getListType().equals("Join")) {// 가입신청관리
			searchCondition.setMemberLevel(CafeConstants.MEMBER_STATUS_WAIT);
		}

		SearchResult<Member> searchResult = memberService.listBySearchCondition(searchCondition);

		CafeCode cafeCode = new CafeCode();

		modelAndView.addObject("cafe", cafe);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("cafeCode", cafeCode);

		return modelAndView;
	}

	/**
	 * 관리자 회원 등록
	 * 
	 * @param searchCondition
	 * @param cafeId
	 * @return
	 */
	@RequestMapping(value = "/addNewMemberView.do", method = RequestMethod.GET)
	public ModelAndView addMemberManagerView(MemberSearchCondition searchCondition,
			@RequestParam("cafeId") String cafeId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/member/addNewMemberView");

		Cafe cafe = new Cafe();
		cafe.setCafeId(cafeId);
		cafe.setPortalId(user.getPortalId());

		cafe = cafeService.read(cafeId);

		searchCondition.setPortalId(user.getPortalId());
		modelAndView.addObject("cafe", cafe);
		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 관리자 정/준회원 등록처리
	 * 
	 * @param searchCondition
	 * @param memberIds
	 * @param associateIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/addNewMember.do", method = RequestMethod.POST)
	public String addNewMember(MemberSearchCondition searchCondition,
			@RequestParam(value = "memberIds", required = false) List<String> memberIds,
			@RequestParam(value = "associateIds", required = false) List<String> associateIds, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		/** 정회원/준회원 등록 **/
		memberService.addNewMember(searchCondition, user, memberIds, associateIds);
		
		if(memberIds != null) {
			for(int i = 0; i < memberIds.size(); i++) {
				//My Cage  포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-myCafe-portlet", "Cachemode-myCafe-portlet", "Elementkey-myCafe-portlet", memberIds.get(i));
			}
		}
		
		if(associateIds != null) {
			for(int i = 0; i < associateIds.size(); i++) {
				//My Cage  포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-myCafe-portlet", "Cachemode-myCafe-portlet", "Elementkey-myCafe-portlet", associateIds.get(i));
			}
		}

		status.setComplete();
		return "redirect:/lightpack/cafe/member/listCafeMemberView.do?cafeId=" + searchCondition.getCafeId()
				+ "&listType=" + searchCondition.getListType();
	}

	/**
	 * 회원 등급 수정
	 * 
	 * @param searchCondition
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateMemberLevel.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean updateMemberLevel(MemberSearchCondition searchCondition, Member member,
			@RequestParam("memberIds") List<String> memberIds, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		try {

			// Portal portal = (Portal) getRequestAttribute("ikep.portal");
			memberService.updateMemberLevel(searchCondition, memberIds, user);
			
			if(memberIds != null) {
				for(int i = 0; i < memberIds.size(); i++) {
					//My Cage  포틀릿 contents 캐시 Element 삭제
					cacheService.removeCacheElementPortletContent("Cachename-myCafe-portlet", "Cachemode-myCafe-portlet", "Elementkey-myCafe-portlet", memberIds.get(i));
				}
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * 회원 등급수정
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeMemberLevel.do", method = RequestMethod.POST)
	public String updateCafeMemberLevel(MemberSearchCondition searchCondition, Member member,
			@RequestParam("memberIds") List<String> memberIds, SessionStatus status) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");

		memberService.updateMemberLevel(searchCondition, memberIds, user);

		status.setComplete();
		return "redirect:/lightpack/cafe/member/listCafeMemberView.do?cafeId=" + member.getCafeId() + "&listType="
				+ searchCondition.getListType();
	}

	/**
	 * 시샵 지정
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateSysop.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Member updateSysop(MemberSearchCondition searchCondition, Member member,
			@RequestParam("memberIds") List<String> memberIds, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		Member member1 = new Member();
		try {

			member1 = memberService.updateSysop(searchCondition, memberIds, user);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return member1;

	}

	/**
	 * 회원 영구 삭제
	 * 
	 * @param cafeId
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteCafeMember.do", method = RequestMethod.POST)
	public String deleteCafeMember(MemberSearchCondition searchCondition, @RequestParam("cafeId") String cafeId,
			@RequestParam("memberIds") List<String> memberIds, SessionStatus status) {

		memberService.physicalDelete(cafeId, memberIds);
		
		if(memberIds != null) {
			for(int i = 0; i < memberIds.size(); i++) {
				//My Cage  포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-myCafe-portlet", "Cachemode-myCafe-portlet", "Elementkey-myCafe-portlet", memberIds.get(i));
			}
		}

		status.setComplete();
		return "redirect:/lightpack/cafe/member/listCafeMemberView.do?cafeId=" + cafeId + "&listType="
				+ searchCondition.getListType();
	}

	/**
	 * 회원 메일 보내기
	 * 
	 * @param searchCondition
	 * @param cafeId
	 * @return
	 */
	@RequestMapping(value = "/sendMailMemberView.do", method = RequestMethod.GET)
	public ModelAndView sendMailMemberView(MemberSearchCondition searchCondition,
			@RequestParam(value = "cafeId", required = false) String cafeId,
			@RequestParam(value = "memberIds", required = false) List<String> memberIds) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/member/sendMailMemberView");

		Cafe cafe = new Cafe();
		cafe.setCafeId(cafeId);
		cafe.setPortalId(user.getPortalId());

		cafe = cafeService.read(cafeId);
		Member member = new Member();
		member.setMemberId(user.getUserId());
		member.setMemberName(user.getUserName());

		List<Member> memberList = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cafeId", cafeId);
		map.put("memberIds", memberIds);

		if (memberIds != null) {
			memberList = memberService.getMemberList(map);
		}

		modelAndView.addObject("cafe", cafe);
		modelAndView.addObject("member", member);
		modelAndView.addObject("memberList", memberList);

		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	@RequestMapping(value = "/sendMailInviteView.do", method = RequestMethod.GET)
	public ModelAndView sendMailInviteView(@RequestParam(value = "cafeId", required = false) String cafeId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/member/sendMailMemberView");

		Cafe cafe = new Cafe();
		cafe.setCafeId(cafeId);
		cafe.setPortalId(user.getPortalId());

		cafe = cafeService.read(cafeId);

		modelAndView.addObject("cafe", cafe);

		return modelAndView;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sendMail.do")
	public String sendMail(String sendType, String cafeId, Mail mail, HttpServletRequest request, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		// 메일 보내기
		if (sendType.equals("mail")) {
			try {

				Map dataMap = new HashMap();

				mail.setFromEmail(user.getMail());
				mail.setFromName(user.getUserName());

				// 수신자/참조자/비밀참조자 셋팅
				MailUtil.getMailAddrForList(mail);

				// 메일형식(txt,html,template)
				mail.setMailType(MailConstant.MAIL_TYPE_HTML);

				// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
				mailSendService.sendMail(mail, dataMap, user);

			} catch (Exception exception) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("\r\nCafe Mail send failed...... ");

				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());

				this.log.error(exception.getStackTrace());
			}
			// 쪽지 보내기
		} else {

			try {

				Message message = new Message();

				message.setSenderId(user.getUserId());
				message.setSenderName(user.getUserName());
				message.setContents(mail.getContent());
				message.setReceiverList(MailUtil.getMessageAddrForList(mail));

				this.messageOutsideService.sendMessageOutside(message, user);

			} catch (Exception exception) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("\r\nCafe Message send failed...... ");

				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());

				this.log.error(exception.getStackTrace());
			}

		}

		return "redirect:/lightpack/cafe/member/listCafeMemberView.do?cafeId=" + cafeId + "&listType=Member";

	}

	/**
	 * 팀 Coll. 폐쇄관리 ( 선택한 WS의 시샵,메일정보)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listSysop.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Member> listSysop(@RequestParam("cafeIds") List<String> cafeIds) {

		// Portal portal = (Portal) getRequestAttribute("ikep.portal");
		List<Member> memberList = null;

		try {
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("cafeIds", cafeIds);

			memberList = memberService.getSysopList(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return memberList;
	}

}
