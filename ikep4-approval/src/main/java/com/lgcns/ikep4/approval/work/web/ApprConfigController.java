/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.Code;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.approval.work.model.ApprSign;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprEntrustService;
import com.lgcns.ikep4.approval.work.service.ApprNoticeService;
import com.lgcns.ikep4.approval.work.service.ApprSignService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 환경 설정
 * 
 * @author 서지혜
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/config")
@SessionAttributes("entrust")
public class ApprConfigController extends BaseController {

	@Autowired
	private ApprEntrustService apprEntrustService;

	@Autowired
	private ApprNoticeService apprNoticeService;

	@Autowired
	private ApprSignService apprSignService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private UserService userService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ACLService aclService;

	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}

	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}

	/**
	 * 위임 관리 조회
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateEntrustForm")
	public ModelAndView updateEntrustForm(ApprEntrust apprEntrust) {

		ModelAndView mav = new ModelAndView("/approval/work/config/updateEntrustForm");

		try {

			if (apprEntrust == null) {
				apprEntrust = new ApprEntrust();
			}

			// 세션의 user id를 가져와서 bean에 셋팅한다.
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			// apprEntrust.setUserId(user.getUserId());

			ApprEntrust apprEntrustDetail = new ApprEntrust();

			ApprEntrust entrust = new ApprEntrust();
			entrust.setUserId(user.getUserId());
			entrust.setPortalId(portal.getPortalId());
			entrust.setUsage("1");
			// apprEntrustDetail = apprEntrustService.entrustDetail(entrust);

			// apprEntrustService.isEntrust("admin", "user4");

			// if( apprEntrustDetail == null ){ apprEntrustDetail = new
			// ApprEntrust();}

			mav.addObject("apprEntrust", apprEntrustDetail);

			List<Code<String>> usageList = apprEntrust.getEntrustClassList();
			mav.addObject("usageList", usageList);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 위임자 저장
	 * 
	 * @param model
	 * @param apprEntrust
	 * @return
	 */
	@RequestMapping(value = "/entrustSetSave.do", method = RequestMethod.POST)
	public String entrustSetSave(Model model, ApprEntrust apprEntrust) {

		try {
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			apprEntrust.setUserId(user.getUserId());
			apprEntrust.setPortalId(portal.getPortalId());

			apprEntrust.setStartDate(DateUtil.toCalendar(apprEntrust.getStartDateStr().replace(".", ""), 00, 00, 00)
					.getTime());
			apprEntrust.setEndDate(DateUtil.toCalendar(apprEntrust.getEndDateStr().replace(".", ""), 23, 59, 59)
					.getTime());

			ApprEntrust entrust = new ApprEntrust();
			entrust.setUserId(user.getUserId());
			entrust.setPortalId(portal.getPortalId());
			entrust.setUsage("1");

			// ApprEntrust apprEntrustDetail =
			// apprEntrustService.entrustDetail(entrust);

			/*
			 * if (apprEntrustDetail != null) { //
			 * if(apprEntrustDetail.getUsage().equals("1")) {
			 * apprEntrustService.entrustUpdate(apprEntrust); // }else { // 위임
			 * 상태만 변경 // apprEntrustService.entrustUpdateUsage(apprEntrust); //
			 * } } else { apprEntrustService.entrustCreate(apprEntrust); }
			 */

			// apprEntrustService.entrustUpdateUsage(apprEntrust);

			apprEntrust.setUsage("1");
			apprEntrustService.entrustCreate(apprEntrust);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "redirect:/approval/work/config/updateEntrustForm.do";
	}

	/**
	 * 위임 히스토리
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateEntrustList.do")
	public String updateEntrustList(Model model, ApprListSearchCondition apprListSearchCondition) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			apprListSearchCondition.setUserId(user.getUserId());
			apprListSearchCondition.setPortalId(portal.getPortalId());

			// List<ApprEntrust> entrustList =
			// apprEntrustService.entrustList(apprEntrust);
			SearchResult<ApprEntrust> searchResult = apprEntrustService.listBySearchCondition(apprListSearchCondition);

			model.addAttribute("searchResult", searchResult);
			model.addAttribute("searchCondition", searchResult.getSearchCondition());
			model.addAttribute("isSystemAdmin", this.isSystemAdmin(user));
			model.addAttribute("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "/approval/work/config/updateEntrustList";
	}

	/**
	 * 위임해제
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteEntrust.do")
	public @ResponseBody
	String deleteEntrust(String[] entrustIds) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			ApprEntrust apprEntrust = new ApprEntrust();
			apprEntrust.setUserId(user.getUserId());
			apprEntrust.setPortalId(portal.getPortalId());

			apprEntrustService.entrustUpdateUsage(apprEntrust, entrustIds);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 위임 관리 조회POP
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateUnitEntrustForm")
	public ModelAndView updateUnitEntrustForm(@RequestParam(value = "entrustId", required = false) String entrustId) {

		ModelAndView mav = new ModelAndView("/approval/work/config/updateUnitEntrustForm");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			ApprEntrust apprEntrustDetail = new ApprEntrust();

			ApprEntrust entrust = new ApprEntrust();
			// entrust.setUserId(user.getUserId());
			entrust.setPortalId(portal.getPortalId());
			entrust.setEntrustId(entrustId);
			entrust.setUsage("1");
			apprEntrustDetail = apprEntrustService.entrustDetail(entrust);

			// if( apprEntrustDetail == null ){ apprEntrustDetail = new
			// ApprEntrust();}

			mav.addObject("apprEntrust", apprEntrustDetail);

			List<Code<String>> usageList = apprEntrustDetail.getEntrustClassList();
			mav.addObject("usageList", usageList);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 결재 알람 관리
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateSetAlramForm")
	public ModelAndView updateSetAlramForm(ApprNotice apprNotice) {

		ModelAndView mav = new ModelAndView("/approval/work/config/updateSetAlramForm");

		try {
			if (apprNotice == null) {
				apprNotice = new ApprNotice();
			}

			// 세션의 user id를 가져와서 bean에 셋팅한다.
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			ApprNotice apprNoticeDetail = new ApprNotice();
			apprNoticeDetail = apprNoticeService.noticeDetail(user.getUserId(), portal.getPortalId());

			// if( apprEntrustDetail == null ){ apprEntrustDetail = new
			// ApprEntrust();}

			mav.addObject("apprNotice", apprNoticeDetail);

			List<Code<String>> usageList = apprNotice.getUsageList();
			mav.addObject("usageList", usageList);

			List<Code<String>> methodList = apprNotice.getMethodList();
			mav.addObject("methodList", methodList);

			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 알림 저장 저장
	 * 
	 * @param model
	 * @param apprNotice
	 * @return
	 */
	@RequestMapping(value = "/noticeSetSave.do", method = RequestMethod.POST)
	public String noticeSetSave(Model model, ApprNotice apprNotice) {

		try {
			String noticeYFlag = "1";
			String noticeNFlag = "0";

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			apprNotice.setUserId(user.getUserId());
			apprNotice.setPortalId(portal.getPortalId());
			
			if(StringUtil.isEmpty(apprNotice.getNoticeMethod())) {
				apprNotice.setNoticeMethod("0");
			}

			if (apprNotice.getNoticeArrival() == null || !apprNotice.getNoticeArrival().equals(noticeYFlag))
				apprNotice.setNoticeArrival(noticeNFlag);
			if (apprNotice.getNoticeFinish() == null || !apprNotice.getNoticeFinish().equals(noticeYFlag))
				apprNotice.setNoticeFinish(noticeNFlag);
			if (apprNotice.getNoticeReject() == null || !apprNotice.getNoticeReject().equals(noticeYFlag))
				apprNotice.setNoticeReject(noticeNFlag);
			if (apprNotice.getNoticeReturn() == null || !apprNotice.getNoticeReturn().equals(noticeYFlag))
				apprNotice.setNoticeReturn(noticeNFlag);

			ApprNotice apprNoticeDetail = apprNoticeService.noticeDetail(user.getUserId(), portal.getPortalId());

			if (apprNoticeDetail != null) {

				// if(apprNoticeDetail.getUsage().equals("1")) {
				apprNoticeService.noticeUpdate(apprNotice);
				// }else {
				// 위임 상태만 변경
				// apprNoticeService.noticeUpdateUsage(apprNotice);
				// }

			} else {
				apprNoticeService.noticeCreate(apprNotice);
			}

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "redirect:/approval/work/config/updateSetAlramForm.do";
	}

	/**
	 * 사인이미지 등록 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprSignForm")
	public ModelAndView apprSignForm() {

		ModelAndView mav = new ModelAndView("/approval/work/config/apprSignForm");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			ApprSign apprSign = apprSignService.selectUserSign(user.getUserId());

			mav.addObject("sign", apprSign);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 사인이미지 리스트
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/apprSignList.do")
	public String apprSignList(Model model, ApprListSearchCondition apprListSearchCondition) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			apprListSearchCondition.setUserId(user.getUserId());
			apprListSearchCondition.setPortalId(portal.getPortalId());

			// List<ApprSign> signList = apprSignService.select(apprSign);
			SearchResult<ApprSign> searchResult = apprSignService.listBySearchCondition(apprListSearchCondition);

			model.addAttribute("searchResult", searchResult);
			model.addAttribute("searchCondition", searchResult.getSearchCondition());
			model.addAttribute("isSystemAdmin", this.isSystemAdmin(user));
			model.addAttribute("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "/approval/work/config/apprSignList";
	}

	/**
	 * 사인이미지 등록
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveApprSign.do")
	public @ResponseBody
	String saveApprSign(String fileId) {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			ApprSign apprSign = new ApprSign();
			apprSign.setSignFileId(fileId);
			apprSign.setIsDefault("0");
			apprSign.setUserId(user.getUserId());
			apprSign.setRegisterId(user.getUserId());
			apprSign.setRegisterName(user.getUserName());

			apprSignService.create(apprSign);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 기본사인 이미지 변경
	 * 
	 * @param signId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changeApprSignDefault.do")
	public @ResponseBody
	String changeApprSignDefault(String signId) {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			ApprSign apprSign = new ApprSign();
			apprSign.setUserId(user.getUserId());
			apprSign.setSignId(signId);

			apprSignService.changeDefault(apprSign);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 기본사인 이미지 삭제
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clearApprSignDefault.do")
	public @ResponseBody
	String clearApprSignDefault() {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			ApprSign apprSign = new ApprSign();
			apprSign.setUserId(user.getUserId());

			apprSignService.clearDefault(apprSign);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 사인 이미지 삭제
	 * 
	 * @param signId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteApprSign.do")
	public @ResponseBody
	String deleteApprSign(String[] signIds) {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			ApprSign apprSign = new ApprSign();
			apprSign.setUserId(user.getUserId());

			apprSignService.delete(signIds);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 결재비밀번호 변경 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprPasswordForm.do")
	public ModelAndView apprPasswordForm() {

		ModelAndView mav = new ModelAndView("/approval/work/config/apprPasswordForm");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 결재 비밀번호 변경
	 * 
	 * @param nowApprPassword
	 * @param newApprPassword
	 * @return
	 */
	@RequestMapping(value = "/changeApprPassword.do")
	public @ResponseBody
	String changeApprPassword(String nowApprPassword, String newApprPassword) {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			User apprUser = userService.read(user.getUserId());

			if (EncryptUtil.encryptSha(nowApprPassword).equals(apprUser.getApprovalPassword())
					|| StringUtil.isEmpty(apprUser.getApprovalPassword())) {

				apprUser.setApprovalPassword(EncryptUtil.encryptSha(newApprPassword));
				apprSignService.changeApprPassword(apprUser);

			} else {
				return "nowPasswordError";
			}

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 수임현황
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listApprSignUser.do")
	public ModelAndView listSignUser(@RequestParam(value = "userId", required = false) String userId,
			ApprListSearchCondition apprListSearchCondition) {

		ModelAndView mav = new ModelAndView("/approval/work/config/listApprSignUser");

		try {
			if (apprListSearchCondition == null)
				apprListSearchCondition = new ApprListSearchCondition();

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			String listType = "listApprSignUser";

			apprListSearchCondition.setListType(listType);
			apprListSearchCondition.setPortalId(portal.getPortalId());
			apprListSearchCondition.setSearchSignUserId(user.getUserId());

			SearchResult<ApprEntrust> searchResult = this.apprEntrustService
					.listBySearchCondition(apprListSearchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("commonCode", ApprList.getPageNumList());
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

}
