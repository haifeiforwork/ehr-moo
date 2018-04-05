/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.web;

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

import com.lgcns.ikep4.collpack.dictionary.constants.DictionaryConstants;
import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.collpack.dictionary.service.DictionaryService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
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
 * @version $Id: DictionaryController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/dictionary")
@SessionAttributes("dictionary")
public class DictionaryController extends DictionaryBaseController {
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	 private TagService tagService;
	
	/**
	 * 용어등록,수정 화면
	 * 
	 * @param wordId
	 * @param viewDictionaryId
	 * @param model
	 * @return
	 */		
	@RequestMapping(value = "/formDictionary.do", method = RequestMethod.GET)
	public String getForm(@RequestParam(value = "wordId", required = false) String wordId, @RequestParam(value = "viewDictionaryId", required = false) String viewDictionaryId, Model model) {
		DictionarySearchCondition searchCondition = new DictionarySearchCondition();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
		
		List<Dictionary> dictionaryAdminList = dictionaryService.selectDictionarysByCondition(searchCondition);
	
		Dictionary dictionary = null;
		List<Tag> tagList = null;
		if (wordId != null) {
			tagList = tagService.listTagByItemId(wordId,  TagConstants.ITEM_TYPE_CONPORATE_VOCA);
			dictionary = dictionaryService.read(wordId);
		} else {
			dictionary = new Dictionary();
		}
		
		model.addAttribute("dictionary", dictionary);	
		model.addAttribute("dictionaryAdminList", dictionaryAdminList);
		model.addAttribute("tagList", tagList);			
		model.addAttribute("viewDictionaryId", viewDictionaryId);	

		return "/collpack/dictionary/dictionaryForm";		
	}
	
	/**
	 * 링크팝업 목록
	 * 
	 * @param searchCondition
	 * @return
	 */		
	@RequestMapping(value = "/linkDictionary.do")
	public ModelAndView getLink(DictionarySearchCondition searchCondition) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
		List<Dictionary> dictionaryList = this.dictionaryService.selectDictionarysByCondition(searchCondition);

		ModelAndView mav = new ModelAndView("/collpack/dictionary/dictionaryLink");
		SearchResult<Dictionary> searchResult = null;
		mav.addObject("dictionaryList", dictionaryList);
		//if (!StringUtil.isEmpty(searchCondition.getSearchWord())) {
			if ( searchCondition.getDictionaryId() == null || "".equals(searchCondition.getDictionaryId())) {
				searchCondition.setDictionaryId(dictionaryService.selectDictionaryId(searchCondition));
			}
			searchCondition.setPagePerRecord(DictionaryConstants.DICTIONARY_LINK_PAGE_PER_RECORD);
			searchResult = this.dictionaryService.listDictionaryBySearchCondition(searchCondition);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
		//}
		return mav;		
	}	

	/**
	 * 용어 등록
	 * 
	 * @param dictionary
	 * @param result
	 * @param status
	 * @return
	 */		
	@RequestMapping(value = "/createDictionary.do", method = RequestMethod.POST)
	public String onSubmit(@Valid Dictionary dictionary, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			return "redirect:/dictionary/formDictionary.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		dictionary.setPortalId(portal.getPortalId());
		
		String wordId = dictionary.getWordId();
		String mode = dictionary.getMode();
		int hitCount = dictionary.getHitCount();
		boolean docPopup = dictionary.getDocPopup();
		if (StringUtil.isEmpty(wordId)) {
			dictionary.setRegisterId(user.getUserId());
			dictionary.setRegisterName(user.getUserName());				
			wordId = dictionaryService.create(dictionary, user);			
		} else {
			Dictionary dictionaryApply = new Dictionary();
			if ( "apply".equals(mode) ) {
				dictionaryApply = dictionaryService.read(wordId);
				dictionaryApply.setMode(mode);
				//최신 조회수 셋팅(이전 버전 적용시 조회수는 전체 카운트에서 증가시킴)
				dictionaryApply.setHitCount(hitCount);
				
				dictionaryApply.setRegisterId(user.getUserId());
				dictionaryApply.setRegisterName(user.getUserName());							
				wordId = dictionaryService.create(dictionaryApply, user);				
			} else {
				dictionary.setRegisterId(user.getUserId());
				dictionary.setRegisterName(user.getUserName());							
				wordId = dictionaryService.create(dictionary, user);				
			}

		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		
		return "redirect:getDictionary.do?wordId="+wordId+"&docPopup="+docPopup;
	}

	/**
	 * 용어이력 적용
	 * 
	 * @param dictionary
	 * @param result
	 * @param status
	 * @return
	 */		
	@RequestMapping(value = "/applyDictionaryVersion.do", method = RequestMethod.POST)
	public String applyVersion(@Valid Dictionary dictionary, BindingResult result, SessionStatus status) {
		
		if (result.hasErrors()) {
			return "redirect:/dictionary/formDictionary.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		dictionary.setPortalId(portal.getPortalId());
		
		String wordId = dictionary.getWordId();
		Dictionary word = dictionaryService.read(wordId);
		dictionary.setWordName(word.getWordName());
		dictionary.setWordEnglishName(word.getWordEnglishName());
		dictionary.setContents(word.getContents());

		dictionary.setRegisterId(user.getUserId());
		dictionary.setRegisterName(user.getUserName());				
		wordId = dictionaryService.create(dictionary, user);			

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		
		return "redirect:getDictionary.do?wordId="+wordId;
	}
	
	/**
	 * 용어상세보기
	 * 
	 * @param wordId
	 * @param docPopup
	 * @param isAdmin
	 * @param myInputList
	 * @param myViewList
	 * @return
	 */	
	@RequestMapping(value = "/getDictionary.do", method = RequestMethod.GET)
	public ModelAndView getView(@RequestParam(value = "wordId", required = false) String wordId, boolean docPopup) {
		User user = (User) getRequestAttribute("ikep.user");
		Dictionary dictionary = new Dictionary();
		dictionary.setWordId(wordId);
		dictionary.setRegisterId(user.getUserId());
		
		ModelAndView mav = null;
		if ( docPopup ) {
			mav = new ModelAndView("/collpack/dictionary/dictionaryViewNoTile");
		} else {
			mav = new ModelAndView("/collpack/dictionary/dictionaryView");
		}
		if (wordId != null) {
			Dictionary dictionaryResult = dictionaryService.readDetail(dictionary);
			
			List<Tag> tagList = tagService.listTagByItemId(wordId,  TagConstants.ITEM_TYPE_CONPORATE_VOCA);		

			List<Dictionary> wordHistoryList = dictionaryService.selectWordHistoryList(dictionaryResult.getWordGroupId());
			mav.addObject("dictionary", dictionaryResult);
			mav.addObject("wordHistoryList", wordHistoryList);
			mav.addObject("tagList", tagList);	
			mav.addObject("docPopup", docPopup);
		}
		return mav;		
	}

	/**
	 * 용어메인화면
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/main.do")
	public ModelAndView listDictionaryView(DictionarySearchCondition searchCondition) {
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
		
		if ( ("".equals(searchCondition.getLocaleCode()) || searchCondition.getLocaleCode() == null) && ("".equals(searchCondition.getWordName()) || searchCondition.getWordName() == null) ) {

			searchCondition.setLocaleCode(user.getLocaleCode()); //첫로딩시
			if ( "".equals(searchCondition.getDictionaryId()) || searchCondition.getDictionaryId() == null ) {

				searchCondition.setDictionaryId(dictionaryService.selectDictionaryId(searchCondition));
			}							

		}

		List<Dictionary> dictionaryList = this.dictionaryService.selectDictionarysByCondition(searchCondition);	
		SearchResult<Dictionary> searchResult = this.dictionaryService.listDictionaryBySearchCondition(searchCondition);

		return new ModelAndView("/collpack/dictionary/main")
		.addObject("searchResult", searchResult)
		.addObject("dictionaryList", dictionaryList)
		.addObject("viewDictionaryId", searchCondition.getDictionaryId())
		.addObject("searchCondition", searchResult.getSearchCondition());
	}
	
	/**
	 * 용어메인화면
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/getMyDictionarys.do")
	public ModelAndView listMyDictionaryView(DictionarySearchCondition searchCondition) {

		SearchResult<Dictionary> searchResult = this.dictionaryService.listDictionaryBySearchCondition(searchCondition);
		
		return new ModelAndView("/collpack/dictionary/myDictionary")
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition());
	}
	
	/**
	 * 용어메인화면 10개 더보기
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping("/listMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listMore(DictionarySearchCondition searchCondition) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
				
		if ( "".equals(searchCondition.getDictionaryId()) || searchCondition.getDictionaryId() == null ) {
				searchCondition.setDictionaryId(dictionaryService.selectDictionaryId(searchCondition));
		}

		SearchResult<Dictionary> searchResult = this.dictionaryService.listDictionaryBySearchCondition(searchCondition);

		ModelAndView mav = new ModelAndView("/collpack/dictionary/mainMore");
		
		try {
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		return mav;		
	}	

	/**
	 * 용어삭제
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/deleteWord.do", method = RequestMethod.POST)
	public String deleteWord(@RequestParam("wordId") String wordId, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		Dictionary dictionaryResult = dictionaryService.read(wordId);
		
		//권한체크
		accessCheck(isAdmin, dictionaryResult.getRegisterId());
		
		dictionaryService.delete(wordId);

		return "redirect:main.do";
	}
	
	/**
	 * 용어메인화면 10개 더보기
	 * 
	 * @param searchCondition
	 * @param isAdmin
	 * @return
	 */		
	@RequestMapping(value = "/checkAlreadyExists.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean checkAlreadyExists(DictionarySearchCondition searchCondition) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
		
		boolean isDuplicate = false;
		try {

			isDuplicate = this.dictionaryService.isDuplicateByWordName(searchCondition);
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		return isDuplicate;
	}	

}

