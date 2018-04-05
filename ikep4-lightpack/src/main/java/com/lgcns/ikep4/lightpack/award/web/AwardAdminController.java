/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.Award.AwardTypeAward;
import com.lgcns.ikep4.lightpack.award.model.Award.CategoryTypeAward;
import com.lgcns.ikep4.lightpack.award.model.Award.LinkTypeAward;
import com.lgcns.ikep4.lightpack.award.model.AwardItemCategory;
import com.lgcns.ikep4.lightpack.award.service.AwardAdminService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemCategoryService;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeNode;

/**
 * 게시판 관리 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/award/awardAdmin")
public class AwardAdminController extends BaseController {

	/** The award admin service. */
	@Autowired
	private AwardAdminService awardAdminService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CacheService cacheService;
	
	/** The award item Category service. */
	@Autowired
	private AwardItemCategoryService awardItemCategoryService;

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User)this.getRequestAttribute("ikep.user");
	}
	
	
	private Portal readPortal() {
		return (Portal)this.getRequestAttribute("ikep.portal");
	}

	/**
	 * 세션에서 로그인 사용자의 포털 ID를 가져온다.
	 *
	 * @return  로그인 사용자의 포털 ID
	 */
	private String getPortalId() {
		User user = this.readUser();
		return user.getPortalId();
	}


	@ModelAttribute("awardCode")
	public BoardCode setBoardCode() {
		return new BoardCode();
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
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 *
	 * @param awardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listAwardView")
	public ModelAndView listAwardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardRootId") String awardRootId,
			@RequestParam(value="popupYn", defaultValue="false") Boolean popupYn) {

		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();


		Boolean isSystemAdmin = this.isSystemAdmin(user);

		List<Award> awardList = this.awardAdminService.listByAwardRootId(awardRootId, this.getPortalId());
		
		boolean isMenuTree = awardAdminService.isAwardMenuTree();

		return new ModelAndView()
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("awardRootId", awardRootId)
		.addObject("awardList", awardList)
		.addObject("popupYn", popupYn)
		.addObject("awardRootId", awardRootId)
		.addObject("isMenuTree", isMenuTree);
	}

	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 *
	 * @param awardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listChildrenAward")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildrenAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}


		List<Award> awardList = null;

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		
		User user = this.readUser();
		Portal portal = this.readPortal();


		try {
			awardList = this.awardAdminService.listChildrenAward(awardId, portal.getPortalId());
			
			String awardName = "";

			for(Award award : awardList) {
				
				if(portal.getDefaultLocaleCode().equals(user.getLocaleCode())){
					awardName = award.getAwardName();
				} else {
					awardName = award.getAwardEnglishName();
				}
				
				treeNodeList.add( new TreeNode(award.getAwardId(), null, awardName, "", (award.getHasChildren() > 0 ? "closed" : "leaf"), award));
			}

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}


		return treeNodeList;
	}

	/**
	 * 게시판 목록 조회 화면 컨트롤 메서드
	 *
	 * @param awardRootId 루트 게시판 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listCurrentLevelAward")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Award> listCurrentLevelAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}


		List<Award> awardList = null;

		try {
			awardList = this.awardAdminService.listChildrenAward(awardId);


		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return awardList;
	}


	/**
	 * 게시판 상세조회 화면 컨트롤 메서드
	 *
	 * @param awardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readAwardView")
	public ModelAndView readAwardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId

	) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Award award = this.awardAdminService.readAward(awardId);

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		return new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("award", award);
	}

	/**
	 * 게시판 생성 화면 컨트롤 메서드
	 *
	 * @param awardRootId 루트 게시판 ID
	 * @param awardParentId 부모 게시판 ID
	 * @param awardType 게시판 타입게시판 ( 0 : 게시글 등록용, 1 : URL링크용게시판, 2 : 게시판 카테고리 구분용 Dummy게시판)
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createAwardView")
	public ModelAndView createAwardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardRootId") String awardRootId,
			@RequestParam(value="awardParentId") String awardParentId,
			@RequestParam(value="awardType") String awardType) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Award award = null;

		if(this.getModelAttribute("award") == null) {
			award = new Award();

			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			String defaultRestrictionType = prop.getProperty("ikep4.support.fileupload.defaultRestrictionType");


			award.setAwardRootId(awardRootId);
			award.setAwardType(awardType);
			award.setAwardParentId(awardParentId);
			award.setRestrictionType(defaultRestrictionType);
			award.setPortalId(this.getPortalId());
			award.setAwardDelete(Award.AWARD_DELETE_SERVICING);

		} else {
			award = (Award)this.getModelAttribute("award");
		}

		award.setParentList(this.awardAdminService.listParentAward(awardParentId));

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		//List<AwardItemCategory> awardItemCategoryList = null;
    	//awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem();
		//return this.bindResult(new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("award", award).addObject("awardItemCategoryList",awardItemCategoryList));

		return this.bindResult(new ModelAndView().addObject("isSystemAdmin", isSystemAdmin).addObject("award", award));

	}
	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 *
	 * @param award 게시판 생성 화면에서 전달된 Award 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createLinkTypeAward")
	public ModelAndView createLink(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=LinkTypeAward.class) Award award,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("award", award, result);
			return new ModelAndView("forward:/lightpack/award/awardAdmin/createAwardView.do")
			.addObject("awardRootId", award.getAwardRootId())
			.addObject("awardParentId", award.getPortalId())
			.addObject("awardType", award.getAwardType());
		}

		award.setPortalId(this.getPortalId());
		award.setAwardDelete(Award.AWARD_DELETE_SERVICING);
		//---------------------------------------------------------------
		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(award, user.getUserId(), user.getUserName());

		String awardId = this.awardAdminService.createAward(award);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardAdmin/readAwardView.do").addObject("awardId", awardId);
	}

	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 *
	 * @param award 게시판 생성 화면에서 전달된 Award 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createCategoryTypeAward")
	public ModelAndView createCategoryTypeAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=CategoryTypeAward.class) Award award,
			BindingResult result,
			SessionStatus status) {

		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("award", award, result);
			return new ModelAndView("forward:/lightpack/award/awardAdmin/createAwardView.do")
			.addObject("awardRootId", award.getAwardRootId())
			.addObject("awardParentId", award.getPortalId())
			.addObject("awardType", award.getAwardType());
		}
	
		award.setPortalId(this.getPortalId());
		award.setAwardDelete(Award.AWARD_DELETE_SERVICING);
		//---------------------------------------------------------------
		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(award, user.getUserId(), user.getUserName());

		String awardId = this.awardAdminService.createAward(award);


		status.setComplete();
		
		return new ModelAndView("redirect:/lightpack/award/awardAdmin/readAwardView.do").addObject("awardId", awardId);
	}


	/**
	 * 게시판 생성 처리 동기 컨트롤 메서드
	 *
	 * @param award 게시판 생성 화면에서 전달된 Award 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createAwardTypeAward")
	public ModelAndView createAwardTypeAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=AwardTypeAward.class) Award award,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("award", award, result);
			return new ModelAndView("forward:/lightpack/award/awardAdmin/createAwardView.do")
			.addObject("awardRootId", award.getAwardRootId())
			.addObject("awardParentId", award.getPortalId())
			.addObject("awardType", award.getAwardType());
		}

		award.setPortalId(this.getPortalId());
		award.setAwardDelete(Award.AWARD_DELETE_SERVICING);
		//---------------------------------------------------------------
		User user = this.readUser();

		ModelBeanUtil.bindRegisterInfo(award, user.getUserId(), user.getUserName());
		String awardId = this.awardAdminService.createAward(award);
		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardAdmin/readAwardView.do").addObject("awardId", awardId);
	}




	/**
	 * 게시판 수정 화면 컨트롤 메서드
	 *
	 * @param awardId 게시판 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateAwardView")
	public ModelAndView updateAwardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Award award = null;
		if(this.getModelAttribute("award") == null) {
			award = this.awardAdminService.readAward(awardId);
		} else {
			award = (Award)this.getModelAttribute("award");
		}
		AwardItemCategory categoryAwardId = new AwardItemCategory();
		categoryAwardId.setAwardId(award.getAwardId());
		
		List<AwardItemCategory> awardItemCategoryList = null;

    	awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem(categoryAwardId);
		
		return new ModelAndView().addObject("award", award).addObject("awardItemCategoryList",awardItemCategoryList);
	}


	/**
	 * 게시판 이름 변경 수정 처리 비동기 컨트롤 메서드
	 *
	 * @param awardId 수정대상 게시판 ID
	 * @param awardName 수정대상 게시판 이름
	 */
	@RequestMapping(value = "/updateAwardName")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateAwardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId, @RequestParam(value="awardName") String awardName) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Award award = this.awardAdminService.readAward(awardId);

		User user = this.readUser();
		Portal portal = this.readPortal();
			
		if(portal.getDefaultLocaleCode().equals(user.getLocaleCode())){
			award.setAwardName(awardName);
		} else {
			award.setAwardEnglishName(awardName);
		}

		award.setPortalId(this.getPortalId());

		ModelBeanUtil.bindUpdaterInfo(award, user.getUserId(), user.getUserName());

		this.awardAdminService.updateAward(award);

	}

	/**
	 * 게시판 위치 이동 처리 비동기 컨트롤 메서드
	 *
	 * @param awardId 이동 게시판 ID
	 * @param awardParentId 이동 게시판의 부모 ID
	 * @param sortOrder 이동 위치
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateMoveAward")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateMoveAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId, @RequestParam(value="awardParentId", required=false) String awardParentId, @RequestParam(value="sortOrder") Integer sortOrder) {

		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Award award = new Award();

		award.setAwardId(awardId);
		award.setSortOrder(sortOrder);
		award.setAwardParentId(awardParentId);
		award.setPortalId(this.getPortalId());

		User user = (User)this.getRequestAttribute("ikep.user");

		ModelBeanUtil.bindUpdaterInfo(award, user.getUserId(), user.getUserName());

		this.awardAdminService.updateAwardMove(award);

	}


	/**
	 * 게시판 수정 처리 동기 컨트롤 메서드
	 *
	 * @param award 게시판 수정 화면에서 전달된 Award 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateLinkTypeAward")
	public ModelAndView updateLinkTypeAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=LinkTypeAward.class) Award award,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("award", award, result);
			return new ModelAndView("forward:/lightpack/award/awardAdmin/updateAwardView.do").addObject("awardId", award.getAwardId());
		}

		User user = this.readUser();
		ModelBeanUtil.bindUpdaterInfo(award, user.getUserId(), user.getUserName());

		award.setPortalId(this.getPortalId());

		this.awardAdminService.updateAward(award);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardAdmin/readAwardView.do").addObject("awardId", award.getAwardId());
	}
	/**
	 * 게시판 수정 처리 동기 컨트롤 메서드
	 *
	 * @param award 게시판 수정 화면에서 전달된 Award 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateCategoryTypeAward")
	public ModelAndView updateCategoryTypeAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=CategoryTypeAward.class) Award award,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			System.out.println("111111111111111111111111111111111111111");
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("award", award, result);
			return new ModelAndView("forward:/lightpack/award/awardAdmin/updateAwardView.do").addObject("awardId", award.getAwardId());
		}

		User user = this.readUser();
		ModelBeanUtil.bindUpdaterInfo(award, user.getUserId(), user.getUserName());

		award.setPortalId(this.getPortalId());
		System.out.println("22222222222222222222222222222222222");
		this.awardAdminService.updateAward(award);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardAdmin/readAwardView.do").addObject("awardId", award.getAwardId());
	}
	/**
	 * 게시판 수정 처리 동기 컨트롤 메서드
	 *
	 * @param award 게시판 수정 화면에서 전달된 Award 객체 모델 클래스
	 * @param result 바인딩결과
	 * @param status 세션상태클래스
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateAwardTypeAward")
	public ModelAndView updateAwardTypeAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@ValidEx(groups=AwardTypeAward.class) Award award,
			BindingResult result,
			SessionStatus status) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("award", award, result);
			return new ModelAndView("forward:/lightpack/award/awardAdmin/updateAwardView.do").addObject("awardId", award.getAwardId());
		}

		User user = this.readUser();
		ModelBeanUtil.bindUpdaterInfo(award, user.getUserId(), user.getUserName());

		award.setPortalId(this.getPortalId());

		this.awardAdminService.updateAward(award);

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardAdmin/readAwardView.do").addObject("awardId", award.getAwardId());
	}

	/**
	 * 게시판 삭제 처리 비동기 컨트롤 메서드
	 *
	 * @param awardId 삭제 대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteAwardAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteAwardAjax(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		this.awardAdminService.physicalDeleteAward(awardId);
		
		// public award 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-publicAward-portlet");
	}

	/**
	 * 게시판 삭제 처리 동기 컨트롤 메서드
	 *
	 * @param awardId 삭제대상 게시판 ID
	 */
	@RequestMapping(value = "/deleteAward")
	public ModelAndView deleteAward(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {"MANAGE"}, resourceName = Award.AWARD_RESOURCE_NAME)) AccessingResult accessResult,
			@RequestParam(value="awardId") String awardId) {
		//게시글관리 권한이 있는지 없는지 확인
		if( !accessResult.isAccess() ) {
			throw new IKEP4AuthorizedException();
		}

		Award award = this.awardAdminService.readAward(awardId);

		this.awardAdminService.physicalDeleteAward(awardId);
		
		// public award 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-publicAward-portlet");

		return new ModelAndView("redirect:/lightpack/award/awardAdmin/listAwardView.do").addObject("awardRootId", award.getAwardRootId());
	}
	
	/**
	 * 게시판 메뉴 형태 변경
	 * 
	 * @param isTree
	 */
	@RequestMapping(value = "/changeAwardMenuType")
	public void changeAwardMenuType(@RequestParam(value="isTree") Boolean isTree, HttpServletResponse res) {
		try {
			awardAdminService.updateAwardMenuType(isTree);
			res.getWriter().print("{rsult:\"OK\"}");
		} catch(IOException e) {
			
		}
	}
	
	@RequestMapping(value = "/listCategoryView")
	public ModelAndView listCategoryView(@RequestParam(value = "awardId") String awardId) {

		AwardItemCategory categoryAwardId = new AwardItemCategory();
		categoryAwardId.setAwardId(awardId);

		List<AwardItemCategory> awardItemCategoryList = null;
		awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem(categoryAwardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("awardItemCategoryList", awardItemCategoryList);
		model.addObject("categoryAwardId", categoryAwardId);
		return model;
	}
	
	/**
	 * 게시판 카테고리 등록
	 * @param awardItemCategory
	 * @param result
	 * @param statu
	 */
	@RequestMapping(value = "/createCategoryAward")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryAward(AwardItemCategory awardItemCategory ,@RequestParam(value = "awardId") String awardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = awardItemCategory.getAddNameList();
		String delIdList = awardItemCategory.getDelIdList();
		String updateNameList = awardItemCategory.getUpdateNameList();
		String updateIdList = awardItemCategory.getUpdateIdList();

		AwardItemCategory receiveCategoryNmList = new AwardItemCategory();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setAwardId(awardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(awardItemCategory.getAlignList());

		List<AwardItemCategory> list = new ArrayList<AwardItemCategory>();
		list.add(receiveCategoryNmList);
		this.awardItemCategoryService.insertCategoryNm(list);
		
		return "success";
	}
}
