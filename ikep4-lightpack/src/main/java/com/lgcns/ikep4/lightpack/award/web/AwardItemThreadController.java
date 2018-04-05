/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.service.AwardAdminService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시물 쓰레드 담당 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/award/awardItemThread")
public class AwardItemThreadController extends BaseController {


	/** The award admin service. */
	@Autowired
	private AwardAdminService awardAdminService;

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
	 * 게시글의 쓰레드를 가져오는 메소드
	 *
	 * @param searchCondition 댓글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listReplayItemThreadView")
	public ModelAndView listReplayItemThreadView(
			@RequestParam(value="awardId") String awardId,
			@RequestParam(value="itemId") String itemId,
			@RequestParam(value="itemGroupId") String itemGroupId,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="popupYn", defaultValue="false", required=false) Boolean popupYn
	) {
		User user = this.readUser();
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Award award = this.awardAdminService.readAward(awardId);

		List<AwardItem> awardItemList =  this.awardItemService.listReplayItemThreadByItemId(itemGroupId);

		return new ModelAndView()
		.addObject("awardItemList", awardItemList)
		.addObject("user", user)
		.addObject("award", award)
		.addObject("itemId", itemId)
		.addObject("searchConditionString", searchConditionString)
		.addObject("popupYn", popupYn)
		.addObject("isSystemAdmin", isSystemAdmin);
	}

}
