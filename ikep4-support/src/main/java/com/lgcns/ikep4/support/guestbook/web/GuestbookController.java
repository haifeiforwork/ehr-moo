/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;
import com.lgcns.ikep4.support.guestbook.service.GuestbookService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 방명록 조회 등록 삭제를 위한 Controller
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookController.java 19513 2012-06-26 09:36:28Z malboru80 $
 */
@Controller
@RequestMapping(value = "/support/guestbook")
@SessionAttributes("guestbook")
public class GuestbookController extends BaseController {

	/**
	 * 방명록 컨트롤을 위한 서비스 
	 */
	@Autowired
	private GuestbookService guestbookService;
	
	/**
	 * 방명록 신규 생성시 키값 생성을 위해 사용하는 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private CacheService cacheService;

	
	/**
	 * 방명록 메인 페이지 에서 방명록 글과 글에 딸린 댓글을 조회 하기 위한 메서드 
	 * 
	 * @param paramGuestbookSearch 방명록 조회 조건을 포함한 객체
	 * @param paramTargetUserId 방명록 조회 대상 ID
	 * @return 조회된 방명록 결과 View 페이지
	 */
	@RequestMapping(value = "/listGuestbook.do")
	public ModelAndView listGuestbook( GuestbookSearchCondition paramGuestbookSearch
								, @RequestParam(value="targetUserId", required=false ) String paramTargetUserId ) {
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String targetUserId = paramTargetUserId;
		
		if( StringUtil.isEmpty(targetUserId ) ){
			targetUserId = sessionuser.getUserId();
		}
		
		GuestbookSearchCondition guestbookSearch = paramGuestbookSearch;
		if (guestbookSearch == null) {
			guestbookSearch = new GuestbookSearchCondition();
		}
		guestbookSearch.setTargetUserId(targetUserId);
		
		ModelAndView mav = new ModelAndView("/support/guestbook/listguestbook");

		SearchResult<Guestbook> searchResult = guestbookService.listGuestbook(guestbookSearch);
		guestbookSearch.setSessUserLocale(sessionuser.getLocaleCode());
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("targetUserId", targetUserId);
		mav.addObject("size", searchResult.getRecordCount());
		mav.addObject("guestbookSearch", guestbookSearch);
		
		//12.08.24 Francis Choi 탭을 위해 추가 
		mav.addObject("myGalleryUrl", "/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId="+targetUserId);

		return mav;
	}
	
	/**
	 * 프로파일의 방명록에서 목록을 통해 방명록글과 글에 딸린 댓글을 조회 하기 위한 메서드 <br/> 
	 * 프로파일에서 more 버튼 누를때에도 호출 되어서 조회 페이지를 리턴 한다.
	 * 
	 * @param paramGuestbookSearch 방명록 조회 조건을 포함한 객체
	 * @param targetUserId 방명록 조회 대상 ID
	 * @param pageIndex 추가로 가져와야 할 기준 정보 페이지.
	 * @return 조회된 방명록 결과 View 페이지
	 */
	@RequestMapping(value = "/listGuestbookMore.do")
	public ModelAndView listGuestbookMore( GuestbookSearchCondition paramGuestbookSearch
								, @RequestParam("targetUserId") String targetUserId
								, @RequestParam(value = "pageIndex", required = false, defaultValue="1") String pageIndex) {
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		GuestbookSearchCondition guestbookSearch = paramGuestbookSearch;
		
		if (guestbookSearch == null) {
			guestbookSearch = new GuestbookSearchCondition();
		}
		guestbookSearch.setPageIndex(Integer.parseInt(pageIndex));
		guestbookSearch.setTargetUserId(targetUserId);
		guestbookSearch.setSessUserLocale(sessionuser.getLocaleCode());
		
		ModelAndView mav = new ModelAndView("/support/profile/guestbookview");

		SearchResult<Guestbook> searchResult = guestbookService.listGuestbookByGuestId(guestbookSearch);
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("targetUserId", targetUserId);
		mav.addObject("size", searchResult.getRecordCount());
		mav.addObject("guestbookSearch", guestbookSearch);

		return mav;
	}
	
	/**
	 * 방명록 신규 작성 버튼 클릭시 작성 가능한 입력 Form View 를 반환 한다.
	 * @param targetUserId 방명록 대상자 ID
	 * @param viewType PF: 프로파일 , GB : 방명록
	 * @return 작성 가능한 입력 Form View
	 */
	@RequestMapping(value = "/inputGuestbook.do")
	public ModelAndView inputGuestbook(@RequestParam("targetUserId") String targetUserId
									, @RequestParam("viewType") String viewType) {
		
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ModelAndView mav = new ModelAndView("/support/guestbook/guestbookinput");
		//if (guestbookId != null) {
		//	Guestbook guest = guestbookService.read(guestbookId);
			Guestbook guest = new Guestbook();
			guest.setTargetUserId(targetUserId);
			guest.setRegisterId(user.getUserId());
			guest.setRegisterName(user.getUserName());
			mav.addObject("guestbook", guest);
			mav.addObject("viewType", viewType);
		//}
		return mav;
	}
	
	/**
	 * 실제 방명록을 저장 하는 메서드
	 * @param guestbook 방명록 객체 
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 * @return 저장된 방명록 글의 ID
	 */
	@RequestMapping(value = "/createGuestbook.do")
	public @ResponseBody String createGuestbook( @Valid Guestbook guestbook, BindingResult result, SessionStatus status) {
		
		String guestbookId = "";
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}else{
			//if ( !(StringUtil.isEmpty(guestbookId)) ){
			//	guestbookService.update(guestbook);
			//}else{
				try {
					String sGeneratedKey = idgenService.getNextId();
					guestbook.setGuestbookId(sGeneratedKey);
					//guestbook.setParentId("");
					guestbook.setStep(0);
					guestbook.setIndentation(0);
					guestbook.setGroupId(sGeneratedKey);
					
					guestbookService.create(guestbook);
					guestbookId = sGeneratedKey;
					
					// 포틀릿 contents 캐시 Element 삭제
					cacheService.removeCacheElementPortletContent("Cachename-guestbook-portlet", "Cachemode-guestbook-portlet", "Elementkey-guestbook-portlet", guestbook.getTargetUserId());
				} catch (Exception e) {
					throw new IKEP4AjaxException("", e);
				}
				
			//}
		}
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return guestbookId;

	}

	/**
	 * 방명록 삭제시 호출 되는 메서드
	 * @param guestbookId 삭제 하고자 하는 방명록 ID 값
	 * @param targetUserId 방명록 대상자 ID
	 * @param status SessionStatus 값
	 * @return 방명록 삭제 성공값
	 */
	@RequestMapping(value = "/deleteGuestbook.do")
	public @ResponseBody String deleteGuestbook(@RequestParam("guestbookId") String guestbookId, @RequestParam("targetUserId") String targetUserId, SessionStatus status) {

		guestbookService.deleteGuestbook(guestbookId);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-guestbook-portlet", "Cachemode-guestbook-portlet", "Elementkey-guestbook-portlet", targetUserId);
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return "ok" ;

	}


	/**
	 * 방명록 ID 기준으로 해당 방명록에 속한 방명록의 목록 전체를 리턴 한다.
	 * @param guestbookId 방명록 ID
	 * @return 해당 아이디에 해당 하는  또는 하위 방명록  view
	 */
	@RequestMapping(value = "/readGuestbook.do")
	public ModelAndView readGuestbook(@RequestParam("guestbookId") String guestbookId ) {
		ModelAndView mav = new ModelAndView("/support/guestbook/guestbookview");
		if (guestbookId != null) {
			GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
			guestbookSearch.setGuestbookId(guestbookId);
			Guestbook guest = guestbookService.selectGuestbook(guestbookSearch);
			mav.addObject("guestbook", guest);
			mav.addObject("targetUserId", guest.getTargetUserId());
		}
		return mav;
	}

	/**
	 * 방명록에 답글 작성 버튼 클릭시 답글 을 입력할수 있는 Form View 를 리턴 한다.
	 * @param guestbookId 방명록 ID
	 * @param indentation 들여쓰기 할 값
	 * @return 답글을 입력할수 있는 Form View
	 */
	@RequestMapping(value = "/inputGuestbookLineReply.do")
	public ModelAndView inputGuestbookLineReply(@RequestParam("guestbookId") String guestbookId
												, @RequestParam("indentation") String indentation) {
		
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ModelAndView mav = new ModelAndView("/support/guestbook/guestbooklinereplyinput");
		//if (guestbookId != null) {
			GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
			guestbookSearch.setGuestbookId(guestbookId);
			Guestbook parentsguest = guestbookService.selectGuestbook(guestbookSearch);
			Guestbook guest = new Guestbook();
			guest.setRegisterId(user.getUserId());
			guest.setRegisterName(user.getUserName());
			guest.setTargetUserId(parentsguest.getTargetUserId());
			guest.setParentId(parentsguest.getGuestbookId());
			guest.setGroupId(parentsguest.getGroupId());
			guest.setStep(parentsguest.getStep()+1);
			guest.setIndentation( Integer.parseInt(indentation) );
			
			mav.addObject("guestbook", guest);
			mav.addObject("indentation", indentation);
		//}
		return mav;
	}
	
	/**
	 * 방명록에 댓글을 저장할때 사용
	 * @param guestbook 저장할 댓글 방명록 객체
	 * @param result Validation 객체
	 * @param status SessionStatus 값
	 * @return 저장된 댓글의 방명록 ID 값
	 */
	@RequestMapping(value = "/createGuestbookLineReply.do")
	public @ResponseBody String createGuestbookLineReply( @Valid Guestbook guestbook, BindingResult result, SessionStatus status) {
		
		String guestbookId = "";

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}else{
			//if ( !(StringUtil.isEmpty(guestbookId)) ){
			//	guestbookService.update(guestbook);
			//}else{
				try {
					guestbookService.updateStep(guestbook.getGroupId(), guestbook.getStep(), guestbook.getTargetUserId());
					
					String sGeneratedKey = idgenService.getNextId();
					guestbook.setGuestbookId(sGeneratedKey);
					
					guestbookService.create(guestbook);
					guestbookId = sGeneratedKey;
					
				} catch (Exception e) {
					throw new IKEP4AjaxException("", e);
				}
				
			//}
		}
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return guestbookId;

	}
	
	/**
	 * 방명록 댓글을 개별 방명록 ID 별로 단건 조회 반환한다.
	 * @param guestbookId 조회할 댓글의 ID
	 * @return 댓글의 개별 단건 View
	 */
	@RequestMapping(value = "/readGuestbookLineReply.do")
	public ModelAndView readGuestbookLineReply(@RequestParam("guestbookId") String guestbookId) {
		ModelAndView mav = new ModelAndView("/support/guestbook/guestbooklinereplyview");
		if (guestbookId != null) {
			GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
			guestbookSearch.setGuestbookId(guestbookId);
			Guestbook guest = guestbookService.selectGuestbook(guestbookSearch);
			mav.addObject("guestbook", guest);
		}
		return mav;
	}
	
}
