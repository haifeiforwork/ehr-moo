/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.collpack.dictionary.service.DictionaryService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;

import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 모두 상속받는 controller
 *
 * @author 서혜숙 
 * @version $$Id: DictionaryBaseController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@RequestMapping(value = "/collpack/dictionary")
public class DictionaryBaseController extends BaseController {

	@Autowired
	private DictionaryService dictionaryService;
	
	 @Autowired
	 ACLService aclService;
	
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
        
		User user = (User) getRequestAttribute("ikep.user");
		
		String sysName = "CorVoca";
        String userId = user.getUserId();
        boolean isSystemAdmin = aclService.isSystemAdmin(sysName, userId);
        
        return isSystemAdmin;
        
	}
	
	/**
	 * 권한체크 - 본인자신이 쓴것인지 관리자인지
	 * @param isAdmin
	 * @param qnaRegisterId
	 * @param qnaId
	 */
	public void accessCheck(boolean isAdmin, String registerId){

		User user = (User) getRequestAttribute("ikep.user");
		
		//권한체크
		if(!(registerId.equals(user.getUserId()) || isAdmin)){
			throw new IKEP4AjaxException("Access Error", null);
		}
	}
	
	/**
	 * 공통으로 가져갈 내가 등록한 용어 리스트
	 * @return
	 */
	@ModelAttribute("myInputList")
	public SearchResult<Dictionary> getMyDictionaryInputList(HttpServletRequest request) {
		
		SearchResult<Dictionary> searchResult = new SearchResult<Dictionary>(new DictionarySearchCondition());
		
		String requestUri = request.getRequestURI();
		
		if(!requestUri.contains("checkAlreadyExists.do") ){
			
			User user = (User) getRequestAttribute("ikep.user");
			
			DictionarySearchCondition searchCondition = new DictionarySearchCondition();
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			searchCondition.setPortalId(portal.getPortalId());		
	
			searchCondition.setRegisterId(user.getUserId());
			searchCondition.setMode("myInputList");
	
			searchResult = this.dictionaryService.listDictionaryBySearchCondition(searchCondition);
			
		} 
		
		return searchResult;
	}
	
	/**
	 * 공통으로 가져갈 내가 조회한 용어 리스트
	 * @return
	 */
	@ModelAttribute("myViewList")
	public SearchResult<Dictionary> getMyDictionaryViewList(HttpServletRequest request) {
		
		SearchResult<Dictionary> searchResult = new SearchResult<Dictionary>(new DictionarySearchCondition());
		
		String requestUri = request.getRequestURI();
		
		if(!requestUri.contains("checkAlreadyExists.do") ){
		
			User user = (User) getRequestAttribute("ikep.user");
			
			DictionarySearchCondition searchCondition = new DictionarySearchCondition();
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			searchCondition.setPortalId(portal.getPortalId());		
	
			searchCondition.setViewId(user.getUserId());
			searchCondition.setMode("myViewList");
	
			searchResult = this.dictionaryService.listDictionaryBySearchCondition(searchCondition);
			
		}
		return searchResult;
	}	
}
