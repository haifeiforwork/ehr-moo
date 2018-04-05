/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardLinereply;
import com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardAdminService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
import com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 댓글 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/award/awardLinereply")
public class AwardLinereplyController extends BaseController {

	/** The award admin service. */
	@Autowired
	private AwardAdminService awardAdminService;

	/** The award linereply service. */
	@Autowired
	private AwardLinereplyService awardLinereplyService;

	@Autowired
	private AwardItemService awardItemService;

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
	 * 댓글 목록 조회 화면 컨트롤 메서드
	 *
	 * @param searchCondition 댓글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listAwardLinereplyView")
	public ModelAndView listAwardLinereplyView(AwardLinereplySearchCondition searchCondition, BindingResult result, SessionStatus status) {
		User user = this.readUser();
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Award award = this.awardAdminService.readAward(searchCondition.getAwardId());

		AwardItem awardItem = this.awardItemService.readAwardItemMasterInfo(searchCondition.getItemId());
		SearchResult<AwardLinereply> searchResult = this.awardLinereplyService.listAwardLinereplyBySearchCondition(searchCondition);

		return new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("user", user)
		.addObject("award", award)
		.addObject("awardItem", awardItem)
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("searchCondition", searchResult.getSearchCondition());
	}


	/**
	 * 댓글 생성 비동기 컨트롤 메서드
	 *
	 * @param awardLinereply 생성 대상 댓글  모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/createAwardLinereply")
	public @ResponseBody void createAwardLinereply(AwardLinereply awardLinereply, BindingResult result, SessionStatus status) {

		try {
			if(result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}

			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(awardLinereply, user.getUserId(), user.getUserName());

			this.awardLinereplyService.createAwardLinereply(awardLinereply);


		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		} finally {
			status.setComplete();
		}


	}


	/**
	 * 댓글의 답변 생성 비동기 컨트롤 메서드
	 * 
	 * @param awardLinereply 생성 대상 댓글의 답변 모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/createReplyAwardLinereply")
	public @ResponseBody void createReplyAwardLinereply(AwardLinereply awardLinereply, BindingResult result, SessionStatus status) {
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}

			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(awardLinereply, user.getUserId(), user.getUserName());

			this.awardLinereplyService.createReplyAwardLinereply(awardLinereply);

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		} finally {
			status.setComplete();
		}

	}


	/**
	 * 댓글 수정 비동기 컨트롤 메서드
	 *
	 * @param awardLinereply 수정 대상 댓글 모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/updateAwardLinereply")
	public @ResponseBody void updateAwardLinereply(AwardLinereply awardLinereply, BindingResult result, SessionStatus status) {
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}
			AwardLinereply readAwardLinereply = this.awardLinereplyService.readAwardLinereply(awardLinereply.getLinereplyId());

			readAwardLinereply.setContents(awardLinereply.getContents());

			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(awardLinereply, user.getUserId(), user.getUserName());

			this.awardLinereplyService.updateAwardLinereply(readAwardLinereply);

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		} finally {
			status.setComplete();
		}

	}


	/**
	 * 댓글 작성자 모드 비동기 컨트롤 메서드
	 *
	 * @param awardLinereply 삭제 대상 댓글 모델 객체
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/userDeleteAwardLinereply")
	public @ResponseBody void userDeleteAwardLinereply(AwardLinereply awardLinereply, BindingResult result, SessionStatus status) {
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", null);
			}
			User user = (User)this.getRequestAttribute("ikep.user");

			ModelBeanUtil.bindRegisterInfo(awardLinereply, user.getUserId(), user.getUserName());

			AwardLinereply awardLinereplyTmp = this.awardLinereplyService.readAwardLinereply(awardLinereply.getLinereplyId());
			
			// 사용자의 댓글인지 확인
			String registerId = awardLinereplyTmp.getRegisterId();
			if(user.getUserId().equals(registerId)) {
				this.awardLinereplyService.userDeleteAwardLinereply(awardLinereply);
			}

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
	@RequestMapping(value = "/adminDeleteAwardLinereply")
	public @ResponseBody void adminDeleteAwardLinereply(@RequestParam("itemId") String itemId, @RequestParam("linereplyId") String linereplyId) {
		
		try {
			User user = (User)this.getRequestAttribute("ikep.user");
			if(isSystemAdmin(user)) {
				this.awardLinereplyService.adminDeleteAwardLinereply(itemId, linereplyId);
			}

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}
}
