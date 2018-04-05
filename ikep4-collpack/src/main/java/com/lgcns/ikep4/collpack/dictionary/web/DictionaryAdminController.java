/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.collpack.dictionary.service.DictionaryService;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;

import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryAdminController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
public class DictionaryAdminController extends DictionaryBaseController {
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/createDictionaryItem.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmit(Dictionary dictionary, @ModelAttribute("isAdmin") boolean isAdmin) {
		String dictionaryId = dictionary.getDictionaryId();
		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			dictionary.setPortalId(portal.getPortalId());
	
			if (StringUtil.isEmpty(dictionaryId)) {
				dictionary.setRegisterId(user.getUserId());
				dictionary.setRegisterName(user.getUserName());	
				dictionaryId = dictionaryService.insertDictionary(dictionary);
			} else {			
				dictionaryService.updateDictionaryName(dictionary);
			}
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		return dictionaryId;
	}
	@RequestMapping(value = "/dictionaryAdminList.do")
	public ModelAndView listDictionaryAdminView(@ModelAttribute("isAdmin") boolean isAdmin) {
		DictionarySearchCondition searchCondition = new DictionarySearchCondition();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());	
		
		List<Dictionary> dictionaryAdminList = dictionaryService.selectDictionarysByCondition(searchCondition);
		
		return new ModelAndView("/collpack/dictionary/dictionaryAdminList")
		.addObject("dictionaryAdminList", dictionaryAdminList)		
		.addObject("size", dictionaryAdminList.size());
	}
	@RequestMapping(value = "/deleteDictionaryItem.do", method = RequestMethod.GET)
	public String remove(@RequestParam("dictionaryId") String dictionaryId, @ModelAttribute("isAdmin") boolean isAdmin) {
	
		
		dictionaryService.deleteDictionary(dictionaryId);

		return "redirect:dictionaryAdminList.do";
	}	
	@RequestMapping(value = "/applyDictionaryOrder.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String applyCategoryOrder(@RequestParam("dictionaryIdes") String dictionaryIdes
									, @ModelAttribute("isAdmin") boolean isAdmin) {
		try {

			
			dictionaryService.updateDictionarySortOrder(dictionaryIdes);

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return "success";
	}	

}

