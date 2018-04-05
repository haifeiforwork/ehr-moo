/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.member.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceCode;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.base.MailUtil;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Collaboration Member Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceMemberController.java 3908 2011-03-24 00:30:00Z
 *          happyi1018 $
 */

@Controller
@RequestMapping(value = "/collpack/collaboration/member")
@SessionAttributes("collaboration")
public class MemberController extends BaseController {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private MessageOutsideService messageOutsideService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private CacheService cacheService;
	
	private static final String COLLABORATION_MANAGER = "Collaboration";

	/**
	 * Workspace 전체 관리자 여부 조회
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String userId) {
		return aclService.isSystemAdmin(COLLABORATION_MANAGER, userId);
	}

	/**
	 * 회원 가입신청 폼
	 * 
	 * @param searchCondition
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createWorkspaceMemberView.do", method = RequestMethod.GET)
	public ModelAndView createWorkspaceMemberView(MemberSearchCondition searchCondition,
			@RequestParam("workspaceId") String workspaceId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/member/createWorkspaceMemberView");

		Workspace workspace = new Workspace();
		workspace.setWorkspaceId(workspaceId);
		workspace.setPortalId(user.getPortalId());

		workspace = workspaceService.read(workspace);
		Member member = new Member();
		member.setMemberId(user.getUserId());
		member.setMemberName(user.getUserName());
		// Member member = workspaceMemberService.read(workspaceId);
		modelAndView.addObject("workspace", workspace);
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
	@RequestMapping(value = "/createWorkspaceMember.do", method = RequestMethod.POST)
	public @ResponseBody
	String createWorkspaceMember(@Valid Member member, BindingResult result, SessionStatus status) {

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
		member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_WAIT);
		member.setJoinType(WorkspaceConstants.MEMBER_JOIN_TYPE_USER);
		member.setRegisterId(user.getUserId());
		member.setRegisterName(user.getUserName());
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		memberService.create(member);

		// 회원 가입 승인 요청 메일 발송
		Member sysopMember = memberService.getSysop(member.getWorkspaceId());
		if (sysopMember != null) {
			memberService.sendMail(member, user, WorkspaceConstants.MEMBER_STATUS_WAIT);
		}
		status.setComplete();
		return member.getWorkspaceId();
		// return "redirect:/collpack/collaboration/main/Workspace.do?workspaceId=" +
		// member.getWorkspaceId();
	}

	/**
	 * 회원 정보 조회
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/readWorkspaceMemberView.do", method = RequestMethod.GET)
	public ModelAndView readWorkspaceMemberView(MemberSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/member/readWorkspaceMemberView");
		Member member = new Member();
		member.setWorkspaceId(searchCondition.getWorkspaceId());
		member.setMemberId(searchCondition.getMemberId());

		if (searchCondition.getMemberId() != null) {
			member = memberService.read(member);
			modelAndView.addObject("member", member);
		}
		modelAndView.addObject("searchCondition", searchCondition);
		return modelAndView;
	}

	/**
	 * 나의 Collaboration 목록에서 회원탈퇴
	 * 
	 * @param workspaceIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteWorkspaceMemberAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean deleteWorkspaceMemberAjax(@RequestParam("workspaceIds") List<String> workspaceIds, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		try {
			memberService.physicalDelete(workspaceIds, user);
			
			//My Collaboration  포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-myCollaboration-portlet", "Cachemode-myCollaboration-portlet", "Elementkey-myCollaboration-portlet", user.getUserId());
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * 회원 탈퇴신청시
	 * 
	 * @param workspaceId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteMemberAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean deleteMemberAjax(@RequestParam("workspaceId") String workspaceId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		try {
			Member member = new Member();
			member.setWorkspaceId(workspaceId);
			member.setMemberId(user.getUserId());

			memberService.physicalDelete(member);

			//My Collaboration  포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-myCollaboration-portlet", "Cachemode-myCollaboration-portlet", "Elementkey-myCollaboration-portlet", user.getUserId());
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * 나의 Collaboration 목록에서 디폴트 워크스페이스 설정
	 * 
	 * @param workspaceId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceDefaultSet.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateWorkspaceDefaultSet(@RequestParam("workspaceId") String workspaceId, SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		try {
			/**
			 * 기존에 설정된 디폴트 워크스페이스 초기화 새로운 WSID 로 디폴트 설정
			 */
			memberService.updateWorkspaceDefaultSet(portal.getPortalId(), user, workspaceId);

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
	@RequestMapping(value = "/listWorkspaceMemberView.do")
	public ModelAndView listWorkspaceMemberView(MemberSearchCondition searchCondition) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView modelAndView = new ModelAndView();

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());
		workspace = workspaceService.read(workspace);

		modelAndView.setViewName("collpack/collaboration/member/listWorkspaceMemberView");
		if (searchCondition.getListType().equals("Member")) {// 전체회원관리
			if(StringUtil.isEmpty(searchCondition.getMemberLevel())){
				searchCondition.setMemberLevel("");
			}
		} else if (searchCondition.getListType().equals("Join")) {// 가입신청관리
			searchCondition.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_WAIT);
		}

		SearchResult<Member> searchResult = memberService.listBySearchCondition(searchCondition);

		Member member = new Member();
		member.setWorkspaceId(searchCondition.getWorkspaceId());
		member.setMemberId(user.getUserId());
		member = memberService.read(member);

		WorkspaceCode workspaceCode = new WorkspaceCode();
		modelAndView.addObject("member", member);
		modelAndView.addObject("isSystemAdmin", isSystemAdmin(user.getUserId()));
		modelAndView.addObject("workspace", workspace);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;
	}

	/**
	 * 관리자 회원 등록
	 * 
	 * @param searchCondition
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/addNewMemberView.do", method = RequestMethod.GET)
	public ModelAndView addMemberManagerView(MemberSearchCondition searchCondition,
			@RequestParam("workspaceId") String workspaceId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/member/addNewMemberView");

		Workspace workspace = new Workspace();
		workspace.setWorkspaceId(workspaceId);
		workspace.setPortalId(user.getPortalId());

		workspace = workspaceService.read(workspace);

		searchCondition.setPortalId(user.getPortalId());
		modelAndView.addObject("workspace", workspace);
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

		status.setComplete();
		return "redirect:/collpack/collaboration/member/listWorkspaceMemberView.do?workspaceId="
				+ searchCondition.getWorkspaceId() + "&listType=" + searchCondition.getListType();
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
	@RequestMapping(value = "/updateWorkspaceMemberLevel.do", method = RequestMethod.POST)
	public String updateWorkspaceMemberLevel(MemberSearchCondition searchCondition, Member member,
			@RequestParam("memberIds") List<String> memberIds, SessionStatus status) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");

		memberService.updateMemberLevel(searchCondition, memberIds, user);

		status.setComplete();
		return "redirect:/collpack/collaboration/member/listWorkspaceMemberView.do?workspaceId=" + member.getWorkspaceId()
				+ "&listType=" + searchCondition.getListType();
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

			// Portal portal = (Portal) getRequestAttribute("ikep.portal");

			member1 = memberService.updateSysop(searchCondition, memberIds, user);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return member1;

	}

	/**
	 * 회원 영구 삭제
	 * 
	 * @param workspaceId
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteWorkspaceMember.do", method = RequestMethod.POST)
	public String deleteWorkspaceMember(MemberSearchCondition searchCondition,
			@RequestParam("workspaceId") String workspaceId, @RequestParam("memberIds") List<String> memberIds,
			SessionStatus status) {

		memberService.physicalDelete(workspaceId, memberIds);
		
		if(memberIds != null) {
			for (int i = 0; i < memberIds.size(); i++) {
				//My Collaboration  포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-myCollaboration-portlet", "Cachemode-myCollaboration-portlet", "Elementkey-myCollaboration-portlet", memberIds.get(i));
			}
		}

		status.setComplete();
		return "redirect:/collpack/collaboration/member/listWorkspaceMemberView.do?workspaceId=" + workspaceId
				+ "&listType=" + searchCondition.getListType();
	}

	/**
	 * 회원 선택 메일 발송
	 * 
	 * @param searchCondition
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/sendMailMemberView.do", method = RequestMethod.GET)
	public ModelAndView sendMailMemberView(MemberSearchCondition searchCondition,
			@RequestParam("workspaceId") String workspaceId,
			@RequestParam(value = "memberIds", required = false) List<String> memberIds) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/member/sendMailMemberView");

		Workspace workspace = new Workspace();
		workspace.setWorkspaceId(workspaceId);
		workspace.setPortalId(user.getPortalId());

		workspace = workspaceService.read(workspace);
		Member member = new Member();
		member.setMemberId(user.getUserId());
		member.setMemberName(user.getUserName());
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");
		List<Member> memberList = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workspaceId", workspaceId);
		map.put("memberIds", memberIds);

		if (memberIds != null) {
			memberList = memberService.getMemberList(map);
		}
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		modelAndView.addObject("useActiveX", useActiveX);
		modelAndView.addObject("workspace", workspace);
		modelAndView.addObject("member", member);
		modelAndView.addObject("memberList", memberList);

		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 회원 메일 발송
	 * 
	 * @param searchCondition
	 * @param mail
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sendMail.do")
	public String sendMail(String sendType, MemberSearchCondition searchCondition, Mail mail,
			HttpServletRequest request, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		mail.setContent(mail.getContent().replaceAll("\r\n", "<br>"));
		// 메일 보내기
		if (sendType.equals("mail")) {
			try {

				Map dataMap = new HashMap();

				mail.setFromEmail(user.getMail());
				mail.setFromName(user.getUserName());

				List<HashMap> toEmailList = new ArrayList<HashMap>();
				for (String addr : mail.getEmailList()) {
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@addr:"+addr);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", addr);

					toEmailList.add(map);
				}
				mail.setToEmailList(toEmailList);
				
				// 수신자/참조자/비밀참조자 셋팅
				//MailUtil.getMailAddrForList(mail);

				// 메일형식(txt,html,template)
				mail.setMailType(MailConstant.MAIL_TYPE_HTML);

				// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
				mailSendService.sendMail(mail, dataMap, user);

			} catch (Exception exception) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("\r\nWorkspace Mail send failed...... ");

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
				buffer.append("\r\nWorkspace Message send failed...... ");

				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());

				this.log.error(exception.getStackTrace());
			}

		}

		return "redirect:/collpack/collaboration/member/listWorkspaceMemberView.do?workspaceId="
				+ searchCondition.getWorkspaceId() + "&listType=" + searchCondition.getListType();

	}

	/**
	 * 팀 Coll. 폐쇄관리 ( 선택한 WS의 시샵,메일정보)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listSysop.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Member> listSysop(@RequestParam("workspaceIds") List<String> workspaceIds) {

		// Portal portal = (Portal) getRequestAttribute("ikep.portal");
		List<Member> memberList = null;

		try {
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("workspaceIds", workspaceIds);

			memberList = memberService.getSysopList(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return memberList;
	}

}
