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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.model.Search;
import com.lgcns.ikep4.socialpack.microblogging.service.SearchService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 
 * 마이크로블로그 검색 관리
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SearchController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging/search")
public class SearchController extends BaseController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private IdgenService idgenService;

	/**
	 * 조회한 검색어를 저장한다.
	 *
	 * @param search Search 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return 저장한 검색어의 id
	 */
	@RequestMapping("/createSearch")
	public @ResponseBody String onSubmit(@Valid Search search, BindingResult result, SessionStatus status) {

		if (log.isDebugEnabled()) {
			log.debug("search:"+search);
		}

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		
		/*
		 * idgen Service에서 id생성하여 넣기
		 */
		String id = idgenService.getNextId();
		
		search.setSearchId(id);
		search.setSearchUserId(user.getUserId());
		search.setIsSave("0");						// 검색어 저장 여부(0: 저장안됨,1: 저장됨)
		
		searchService.create(search);

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return id;
	}

	/**
	 * 저장된 검색어를 즐겨찾기 상태로 수정한다.
	 *
	 * @param search Search 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return 결과
	 */
	@RequestMapping(value = "/updateSearch.do")
	public @ResponseBody String updateSearch(Search search, BindingResult result, SessionStatus status) {

		if (log.isDebugEnabled()) {
			log.debug("search:"+search);
		}
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		if(null != search){

			if (log.isDebugEnabled()) {
				log.debug("user.getUserId():"+user.getUserId());
			}
			
			search.setSearchUserId(user.getUserId());
			
			// 즐겨찾기 등록일 경우 기존에 즐겨찾기된 검색어로 되어있는지 체크해서, 없을 때만 즐겨찾기된 검색어로 수정처리한다.
			if("1".equals(search.getIsSave()))
			{
				int count = searchService.count(search);
	
				if(count == 0) 
				{
					searchService.update(search);
				}
			}
			else
			{
				searchService.update(search);
			}
		}
		return messageSource.getMessage("ui.socialpack.microblogging.message.saveSuccess", null, new Locale(user.getLocaleCode()));
	}

	/**
	 * 즐겨찾기로 등록된 검색어 리스트를 리턴한다.
	 *
	 * @param search searchUserId를 담은 Search객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listBySearchUserId.do")
	public ModelAndView listBySearchUserId(Search search) {

		if (log.isDebugEnabled()) {
			log.debug("listBySearchUserId search:"+search);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/savedSearchList");
		List<Search> list = searchService.listBySearchUserId(search);
		if (log.isDebugEnabled()) {
			log.debug("listBySearchUserId list:"+list);
		}

		mav.addObject("searchList", list);
		mav.addObject("size", list.size());

		return mav;
	}


}
