/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.web;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * MbgroupMember 관리
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupMemberController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging/mbgroupMember")
public class MbgroupMemberController extends BaseController {

	@Autowired
	private MbgroupMemberService mbgroupMemberService;

	@Autowired
	private MessageOutsideService messageOutsideService;

	/**
	 * Group에 여러명의 멤버들을 등록한다.
	 *
	 * @param mbgroupMember Mbgroup의 Member 정보
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createMbgroupMembers.do")
	public @ResponseBody String createMbgroupMembers(MbgroupMember mbgroupMember) {

		if (log.isDebugEnabled()) {
			log.debug("mbgroupMember:"+mbgroupMember);
		}

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		try {
			String[] memberIds = mbgroupMember.getMemberId().split(",");

			if (log.isDebugEnabled()) {
				log.debug("memberIds.length:"+memberIds.length);
			}
			
			MbgroupMember submitMbgroupMember = null;
			for(int i = 0; i < memberIds.length; i++)
			{
				if (log.isDebugEnabled()) {
					log.debug("memberIds["+i+"]:"+memberIds[i]);
				}
				
				submitMbgroupMember = new MbgroupMember();
				submitMbgroupMember.setMbgroupId(mbgroupMember.getMbgroupId());
				submitMbgroupMember.setMemberId(memberIds[i]);

				// 조회해서 기존에 있는 회원이면 제외한다.
				MbgroupMember orgMbgroupMember = mbgroupMemberService.read(submitMbgroupMember);
				if(null == orgMbgroupMember) 
				{
					submitMbgroupMember.setRegisterId(user.getUserId());
					submitMbgroupMember.setRegisterName(user.getUserName());
					submitMbgroupMember.setStatus(Constant.MBGROUP_MEMBER_STATUS_INVITE);
	
					mbgroupMemberService.create(submitMbgroupMember);
				}
				
				// 쪽지 보내기.
				Message message = new Message();
				
				String[] args = { mbgroupMember.getMbgroupId() };
				String contents = messageSource.getMessage("ui.socialpack.microblogging.groupMember.message.invited", args, new Locale(user.getLocaleCode()));

				message.setSenderId(user.getUserId());
				message.setSenderName(user.getUserName());
				message.setContents(contents);
				message.setReceiverList(memberIds[i]);

				messageOutsideService.sendMessageOutside(message, user);

			}
		} catch (Exception e) {
			//e.printStackTrace();
			//mav.addObject("status", messageSource.getMessage("ui.socialpack.microblogging.message.fail", null, new Locale(user.getLocaleCode())));
			return messageSource.getMessage("ui.socialpack.microblogging.message.fail", null, new Locale(user.getLocaleCode()));
		}

		//mav.addObject("status", messageSource.getMessage("ui.socialpack.microblogging.message.success", null, new Locale(user.getLocaleCode())));
		
		return messageSource.getMessage("ui.socialpack.microblogging.message.success", null, new Locale(user.getLocaleCode()));
	}

	/**
	 * 사용자가 해당 Group에서 탈퇴하여, Group Member 테이블에서 삭제한다.
	 *
	 * @param mbgroupMember Mbgroup의 id가 담긴 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/removeMbgroupMember.do")
	public String removeMbgroupMember(MbgroupMember mbgroupMember) {

		if (log.isDebugEnabled()) {
			log.debug("removeMbgroupMember mbgroupMember:" + mbgroupMember);
		}

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		mbgroupMember.setMemberId(user.getUserId());
		
		mbgroupMemberService.delete(mbgroupMember);

		return "redirect:/socialpack/microblogging/privateHome.do?ownerId=" + user.getUserId();
	}

	/**
	 * 사용자가 해당 Group의 초대를 거절하여, Group Member 테이블에서 삭제한다.
	 *
	 * @param mbgroupMember Mbgroup의 id가 담긴 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/rejectMbgroupMember.do")
	public @ResponseBody String rejectMbgroupMember(MbgroupMember mbgroupMember) {

		if (log.isDebugEnabled()) {
			log.debug("rejectMbgroupMember mbgroupMember:" + mbgroupMember);
		}

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		
		mbgroupMember.setMemberId(user.getUserId());
		
		mbgroupMemberService.delete(mbgroupMember);

		return "ok";
	}

	/**
	 * 사용자의  Group에서의 상태를 수정한다. (상태(0 : 초대, 1 : 참여))
	 *
	 * @param mbgroupMember Mbgroup의 id, status가 담긴 객체
	 * @return 결과
	 */
	@RequestMapping(value = "/updateMbgroupMember.do")
	public @ResponseBody String updateMbgroupMember(MbgroupMember mbgroupMember) {

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		mbgroupMember.setMemberId(user.getUserId());
		
		mbgroupMemberService.update(mbgroupMember);

		return "ok";
	}

	/**
	 * Group의 회원 등록용 팝업을 리턴한다.
	 *
	 * @return ModelAndView
	 */
	@RequestMapping("/groupMemberForm.do")
	public ModelAndView groupMemberForm() {

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/groupMemberFormPopup");

		return mav;
	}

	/**
	 * Group 회원의  user객체 리스트를 리턴한다.
	 *
	 * @param mbgroupId와 조회조건 필드
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/mbgroupMemberUserList.do")
	public ModelAndView mbgroupMemberUserList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(user.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("mbgroupMemberUserList =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/userList");
		List<User> list = mbgroupMemberService.mbgroupMemberUserList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("mbgroupMemberUserList list:" + list);
		}

		mav.addObject("userList", list);

		return mav;
	}

	/**
	 * 그룹 마지막 멤버여부를 리턴한다.
	 *
	 * @param mbgroup Id와 member Id를 담은 조회조건 필드
	 * @return 그룹 마지막 멤버여부
	 */
	@RequestMapping(value = "/checkLastGroupMemberYN.do")
	public @ResponseBody String checkLastGroupMemberYN(MbgroupMember mbgroupMember) {

		String result = "N";
		
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		mbgroupMember.setMemberId(user.getUserId());
		mbgroupMember.setStatus(Constant.MBGROUP_MEMBER_STATUS_NORMAL);
		
		result = mbgroupMemberService.checkLastGroupMemberYN(mbgroupMember);

		return result;
	}

}
