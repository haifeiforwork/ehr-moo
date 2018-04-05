/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.who.constants.WhoConstants;
import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.collpack.who.service.WhoService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/who")
@SessionAttributes("who")
public class WhoController extends WhoBaseController {
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	@Autowired
	private WhoService whoService;

	@Autowired
	 private TagService tagService;
	
	/**
	 * 인물등록,수정 화면
	 * 
	 * @param profileId
	 * @param model
	 * @return
	 */		
	@RequestMapping(value = "/formWho.do", method = RequestMethod.GET)
	public String getForm(@RequestParam(value = "profileId", required = false) String profileId, Model model) {
		User user = (User) getRequestAttribute("ikep.user");

		Who who = new Who();
		who.setProfileId(profileId);
		who.setRegisterId(user.getUserId());		
		List<Tag> tagList = null;
		if (profileId != null) {
			tagList = tagService.listTagByItemId(profileId,  TagConstants.ITEM_TYPE_WHO);
			who = whoService.readDetail(who);
		}
		
		model.addAttribute("who", who);	
		model.addAttribute("tagList", tagList);	

		return "/collpack/who/whoForm";		
	}	

	/**
	 * 인물등록
	 * 
	 * @param who
	 * @param result
	 * @param status
	 * @return
	 */		
	@RequestMapping(value = "/createWho.do", method = RequestMethod.POST)
	public String onSubmit(@ValidEx Who who, BindingResult result, SessionStatus status) {
		
		if (result.hasErrors()) {
			return "redirect:/who/formWho.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		who.setPortalId(portal.getPortalId());
		
		String profileId = who.getProfileId();
		String mode = who.getMode();
		int hitCount = who.getHitCount();
		boolean docPopup = who.getDocPopup();
		if (StringUtil.isEmpty(profileId)) {
			who.setRegisterId(user.getUserId());
			who.setRegisterName(user.getUserName());				
			profileId = whoService.create(who, user);			
		} else {
			Who whoApply = new Who();
			if ( "apply".equals(mode) ) {
				whoApply = whoService.read(profileId);
				whoApply.setMode(mode);
				//최신 조회수 셋팅(이전 버전 적용시 조회수는 전체 카운트에서 증가시킴)
				whoApply.setHitCount(hitCount);	
				
				whoApply.setRegisterId(user.getUserId());
				whoApply.setRegisterName(user.getUserName());								
				profileId = whoService.create(whoApply, user);				
			} else {
				who.setRegisterId(user.getUserId());
				who.setRegisterName(user.getUserName());								
				profileId = whoService.create(who, user);				
			}

		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		
		return "redirect:getWho.do?profileId="+profileId+"&docPopup="+docPopup;
	}

	/**
	 * 수정이력적용
	 * 
	 * @param who
	 * @param result
	 * @param status
	 * @return
	 */		
	@RequestMapping(value = "/applyWhoVersion.do", method = RequestMethod.POST)
	public String applyVersion(@Valid Who who, BindingResult result, SessionStatus status) {
		
		if (result.hasErrors()) {
			return "redirect:/who/formWho.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		who.setPortalId(portal.getPortalId());
		
		String profileId = who.getProfileId();
		Who word = whoService.read(profileId);
		who.setName(word.getName());
		who.setMemo(word.getMemo());
		who.setUpdateReason(word.getUpdateReason());

		who.setRegisterId(user.getUserId());
		who.setRegisterName(user.getUserName());	
		who.setMode("apply");
		
		profileId = whoService.create(who, user);			

		// 세션 상태를 complete 하여 이중 전송 방지
		status.setComplete();
		
		return "redirect:getWho.do?profileId="+profileId;
	}
	
	/**
	 * 인물상세조회
	 * 
	 * @param profileId
	 * @param whoSortIndex
	 * @param docPopup
	 * @param isAdmin
	 * @param myInputList
	 * @param myViewList
	 * @return
	 */	
	@RequestMapping(value = "/getWho.do", method = RequestMethod.GET)
	public ModelAndView getView(@RequestParam(value = "profileId", required = false) String profileId, @RequestParam(value = "whoSortIndex", required = false) String whoSortIndex,boolean docPopup, @ModelAttribute("isAdmin") boolean isAdmin,@ModelAttribute("myViewList") SearchResult<Who> myViewList,@ModelAttribute("myInputList") SearchResult<Who> myInputList) {
		User user = (User) getRequestAttribute("ikep.user");
		Who who = new Who();
		who.setProfileId(profileId);
		who.setRegisterId(user.getUserId());
		
		ModelAndView mav = null;
		if ( docPopup ) {
			mav = new ModelAndView("/collpack/who/whoViewNoTile");
		} else {
			mav = new ModelAndView("/collpack/who/whoView");
		}
		if (profileId != null) {
			Who whoResult = whoService.readDetail(who);
			//reload
			SearchResult<Who> myViewListResult = getMyWhoViewList();
			SearchResult<Who> myInputListResult = getMyWhoInputList();
			List<Tag> tagList = tagService.listTagByItemId(profileId,  TagConstants.ITEM_TYPE_WHO);		

			List<Who> whoHistoryList = whoService.selectProfileHistoryList(whoResult.getProfileGroupId());
			
			//관련외부인물
			Tag searchTag = new Tag();
			searchTag.setTagId("");
			searchTag.setTagItemType(TagConstants.ITEM_TYPE_WHO);
			searchTag.setAsTagItemType("");
			searchTag.setTagItemId(whoResult.getProfileId());
			searchTag.setGroupType(whoResult.getProfileGroupId());
			searchTag.setPageIndex(1);
			searchTag.setPagePer(WhoConstants.WHO_TAG_PAGE_PER_RECORD);	
			List<Pro> outerProList = whoService.selectWhoProList(searchTag); //최대 20명
			
			//관련사내인물
			searchTag.setGroupType("");
			searchTag.setTagItemType(TagConstants.ITEM_TYPE_WHO);
			searchTag.setAsTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);

			List<Pro> innerProList = whoService.selectProList(searchTag); //최대 20명


			mav.addObject("who", whoResult);
			mav.addObject("myInputList", myInputListResult);
			mav.addObject("myViewList", myViewListResult);
			mav.addObject("whoHistoryList", whoHistoryList);
			mav.addObject("innerProList", innerProList);
			mav.addObject("outerProList", outerProList);
			mav.addObject("tagList", tagList);	
			mav.addObject("docPopup", docPopup);
			mav.addObject("whoSortIndex", whoSortIndex);
			
		}
		return mav;		
	}

	/**
	 * 인물메인화면
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */	
	@RequestMapping(value = "/main.do")
	public ModelAndView listWhoView(WhoSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {
		User user = (User) getRequestAttribute("ikep.user");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());		

		SearchResult<Who> searchResult = whoService.listWhoBySearchCondition(searchCondition);
		Tag searchTag = new Tag();
		searchTag.setTagId("");
		searchTag.setTagItemId(user.getUserId());
		searchTag.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
		searchTag.setAsTagItemType("");
		searchTag.setPageIndex(1);
		searchTag.setPagePer(WhoConstants.WHO_TAG_PAGE_PER_RECORD);

		List<Pro> proList = whoService.selectProList(searchTag);


		return new ModelAndView("/collpack/who/main")
		.addObject("searchResult", searchResult)
		.addObject("proList", proList)
		.addObject("count", proList.size())
		.addObject("searchCondition", searchResult.getSearchCondition());
	}
	
	@RequestMapping(value = "/getMyWhos.do")
	public ModelAndView listMyWhoView(WhoSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {
		SearchResult<Who> searchResult = whoService.listWhoBySearchCondition(searchCondition);
		
		return new ModelAndView("/collpack/who/myWho")
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition());
	}
	
	/**
	 * 인물메인화면 10개 더보기
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/listMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listMore(WhoSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {
		SearchResult<Who> searchResult = whoService.listWhoBySearchCondition(searchCondition);

		ModelAndView mav = new ModelAndView("/collpack/who/mainMore");
		
		try {
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		return mav;		
	}	
	
	/**
	 * 나의전문인물목록 10개 더보기
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/proListMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView proListMore(WhoSearchCondition searchCondition, @ModelAttribute("isAdmin") boolean isAdmin) {
		User user = (User) getRequestAttribute("ikep.user");

		Tag searchTag = new Tag();
		searchTag.setTagId(searchCondition.getTagId());
		searchTag.setTagItemId(user.getUserId());
		searchTag.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
		searchTag.setPageIndex(1);
		searchTag.setPagePer(WhoConstants.WHO_TAG_PAGE_PER_RECORD);

		List<Pro> proList = whoService.selectProList(searchTag);

		ModelAndView mav = new ModelAndView("/collpack/who/proMore");
		
		try {
			mav.addObject("proList", proList);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		return mav;		
	}
	
	/**
	 * 인물 삭제
	 * 
	 * @param profileId
	 * @return
	 */		
	@RequestMapping(value = "/deleteWho.do", method = RequestMethod.POST)
	public String deleteWord(@RequestParam("profileId") String profileId) {
		whoService.delete(profileId);

		return "redirect:main.do";
	}
	
	/**
	 * 이메일중복여부 체크
	 * 
	 * @param profileId
	 * @return
	 */	
	@RequestMapping(value = "/checkAlreadyMailExists.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody int checkAlreadyMailExists(Who who, @ModelAttribute("isAdmin") boolean isAdmin) {
		int wordCount = 0;
		try {
			wordCount = whoService.checkAlreadyMailExists(who);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		return wordCount;
	}	
}

