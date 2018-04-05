/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.service.AwardAdminService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시판 공통 화면 컨트롤러
 *
 * @author 최현식
 * @version $Id: AwardCommonController.java 16683 2011-09-26 08:45:32Z yu_hs $
 */
@Controller
@RequestMapping(value = "/lightpack/award/awardCommon")
public class AwardCommonController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The award admin service. */
	@Autowired
	private AwardAdminService awardAdminService;
	
	@Autowired
	private AwardItemService awardItemService;

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
	 * 세션에서 포털 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private Portal readPortal() {
		return (Portal)this.getRequestAttribute("ikep.portal");
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
	@RequestMapping(value = "/awardView")
	public ModelAndView awardItemView(
			@RequestParam(value="awardRootId", defaultValue="0", required=false) String awardRootId,
			@RequestParam(value="awardId", required=false) String awardId,
			@RequestParam(value="itemId",  required=false) String itemId,
			@RequestParam(value="popupYn", defaultValue="false") Boolean popupYn

	) {
		User user = this.readUser();
		//List<Award> awardList = this.awardAdminService.listByAwardRootId(awardRootId, this.readPortal().getPortalId());
		List<Award> awardList = this.awardAdminService.listByAwardRootIdPermission(awardRootId, this.readPortal().getPortalId(),user.getUserId());
		
		boolean isMenuTree = awardAdminService.isAwardMenuTree();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		ModelAndView modelAndView = new ModelAndView()
		.addObject("awardList", awardList)
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("awardId", awardId)
		.addObject("itemId", itemId)
		.addObject("user", user)
		.addObject("popupYn", popupYn)
		.addObject("isMenuTree", isMenuTree);

		return modelAndView;
	}
	
	@RequestMapping("/getAwardItemCount")
	public @ResponseBody String getAwardItemCount(@RequestParam(value="awardId", required=false) String awardId) {
		String result = "";
		try {
			AwardItem awardItem = new AwardItem();
			User user = this.readUser();
			awardItem.setRegisterId(user.getUserId());
			awardItem.setAwardId(awardId);
			int count = awardItemService.getAwardItemCount(awardItem);
			result = "{\"count\":" + count + "}"; 
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	@RequestMapping("/getAwardItemsCount")
	public @ResponseBody String getAwardItemsCount(@RequestParam(value="awardId", required=false) String awardId) {
		String result = "";
		try {
			AwardItem awardItem = new AwardItem();
			User user = this.readUser();
			Boolean isSystemAdmin = isSystemAdmin(user);
			awardItem.setAdmin(isSystemAdmin);
			awardItem.setRegisterId(user.getUserId());
			int count = 0;
			int tempCount = 0;
			String [] tempAwardId = awardId.split(",");
			for(int i=0;i<tempAwardId.length;i++){
				awardItem.setAwardId(tempAwardId[i]);
				if("100006240370".equals(tempAwardId[i])){
					boolean awardAdminRole = false;
					awardAdminRole = aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, "ADMIN", tempAwardId[i], user.getUserId());
					awardItem.setAwardAdmin(awardAdminRole);
					tempCount = awardItemService.getHappyAwardItemCount(awardItem);
				}else{
					tempCount = awardItemService.getAwardItemCount(awardItem);
				}
				count = count+tempCount;
			}
			
			
			result = "{\"count\":" + count + "}"; 
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}


	/**
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param popupYn the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readLinkAwardView")
	public ModelAndView readLinkAwardView(@RequestParam("awardId") String awardId) {
		Award award = this.awardAdminService.readAward(awardId);

		return new ModelAndView().addObject("award", award);
	}
}
