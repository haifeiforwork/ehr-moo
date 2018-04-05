/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 소셜 블로그 답글 관련 Controller
 *
 * @author 이형운
 * @version $Id: SocialBoardLinereplyController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/socialblog")
//@SessionAttributes("socialBoardLinereply") 
public class SocialBoardLinereplyController extends BaseController {

 
	/**
	 * 블로그 댓글 정보 관리 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardLinereplyService socialBoardLinereplyService;
	
	/**
	 * 블로그 관리자 권한 체크 컨트롤용 Service
	 */
    @Autowired
    private ACLService aclService; 
    
	/**
	 * 소셜 블로그 포스팅에 대한 시스템 관리자 접근 권한 확인
	 * 
	 * @param user 사용자 정보 객체
	 * @return 시스템 관리자 접근 권한 확인
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user.getUserId());
		//isSystemAdmin = true;
		return isSystemAdmin;
	}
    
    /**
     * 세션의 로그인 User 정보
     * @return 세션에서 받아온 User 정보 객체
     */
    private User readUser() {  
    	return (User)getRequestAttribute("ikep.user"); 
    }
    
    /**
     * 블로그 댓글 리스트 조회 
     * @param searchCondition 블로그 댓글 조회용 객체
     * @return 블로그 댓글 리스트 View
     */
	@RequestMapping(value = "/listSocialBoardLinereplyView.do")
	public ModelAndView listSocialBoardLinereplyView(SocialBoardLinereplySearchCondition searchCondition) {   
		User user = readUser();
		Boolean isSystemAdmin = isSystemAdmin(user);
		
		SearchResult<SocialBoardLinereply> searchResult = socialBoardLinereplyService.listSocialBoardLinereplyBySearchCondition(searchCondition); 
	 
		return new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("user", user)
		.addObject("isSystemAdmin", isSystemAdmin)
		.addObject("linereplySearchCondition", searchResult.getSearchCondition()); 
		
	} 
 
	/**
	 * 댓글 작성용 입력 폼 FORM 반환 한다. 
	 * @param itemId 댓글이 작성될 포스팅 Item ID
	 * @return 댓글 입력 폼 
	 */
	@RequestMapping(value = "/inputSocialBoardLinereply.do")
	public ModelAndView inputSocialBoardLinereply(@RequestParam("itemId") String itemId) {
		
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		SocialBoardLinereply socialBoardLinereply = new SocialBoardLinereply();
		socialBoardLinereply.setItemId(itemId);
		socialBoardLinereply.setRegisterId(user.getUserId());
		socialBoardLinereply.setRegisterName(user.getRegisterName());

		return new ModelAndView().addObject("socialBoardLinereply", socialBoardLinereply);

	}
	
	/**
	 * 블로그 댓글 저장 
	 * @param socialBoardLinereply 댓글 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/createSocialBoardLinereply")
	public @ResponseBody void createSocialBoardLinereply( @Valid SocialBoardLinereply socialBoardLinereply, BindingResult result, SessionStatus status) {
		
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxValidationException(result, messageSource);
			}  
			
			User user = (User)getRequestAttribute("ikep.user");  
			ModelBeanUtil.bindRegisterInfo(socialBoardLinereply, user.getUserId(), user.getUserName());
			socialBoardLinereplyService.createSocialBoardLinereply(socialBoardLinereply);
		
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
			
		} finally {
			status.setComplete();
		}
		
		
	}
	
	/**
	 * 블로그 글에 댓글 저장 
	 * @param socialBoardLinereply 댓글 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/createReplySocialBoardLinereply")
	public @ResponseBody void createReplySocialBoardLinereply(@Valid SocialBoardLinereply socialBoardLinereply, BindingResult result, SessionStatus status) {
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxValidationException(result, messageSource);
			}  
			
			User user = (User)getRequestAttribute("ikep.user");  
			ModelBeanUtil.bindRegisterInfo(socialBoardLinereply, user.getUserId(), user.getUserName());
			socialBoardLinereplyService.createReplySocialBoardLinereply(socialBoardLinereply);
		
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
			
		} finally {
			status.setComplete();
		}
		
	}
	
	
	/**
	 * 블로그 댓글 수정
	 * @param socialBoardLinereply 댓글 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/updateSocialBoardLinereply")
	public @ResponseBody void updateBoardLinereply( @Valid SocialBoardLinereply socialBoardLinereply, BindingResult result, SessionStatus status) {
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxValidationException(result, messageSource);
			}  
			
			SocialBoardLinereply readSocialBoardLinereply = socialBoardLinereplyService.readSocialBoardLinereply(socialBoardLinereply.getLinereplyId());
			readSocialBoardLinereply.setContents(socialBoardLinereply.getContents());
			
			User user = (User)getRequestAttribute("ikep.user");  
			ModelBeanUtil.bindRegisterInfo(socialBoardLinereply, user.getUserId(), user.getUserName());
			socialBoardLinereplyService.updateSocialBoardLinereply(readSocialBoardLinereply);
			
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
			
		} finally {
			status.setComplete();
		}
		 
	}
	
	/**
	 * 블로그 댓글 삭제
	 * @param socialBoardLinereply 댓글 정보 객체
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/userDeleteSocialBoardLinereply")
	public @ResponseBody void userDeleteBoardLinereply( SocialBoardLinereply socialBoardLinereply, SessionStatus status) {  
		try {
			
			User user = (User)getRequestAttribute("ikep.user");  
			ModelBeanUtil.bindRegisterInfo(socialBoardLinereply, user.getUserId(), user.getUserName());
			socialBoardLinereplyService.userDeleteSocialBoardLinereply(socialBoardLinereply);
			
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
			
		}  
	} 
	
	/**
	 * 블로그 댓글 관리자 삭제
	 * @param itemId 삭제할 포스팅 Item Id
	 * @param linereplyId 삭제할 댓글 Id
	 */
	@RequestMapping(value = "/adminDeleteSocialBoardLinereply")
	public @ResponseBody void adminDeleteBoardLinereply(@RequestParam("itemId") String itemId, @RequestParam("linereplyId") String linereplyId) { 
		try {  
			socialBoardLinereplyService.adminDeleteSocialBoardLinereply(itemId, linereplyId); 
			
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
			
		} 
	}  
	
}
