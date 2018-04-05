package com.lgcns.ikep4.approval.collaboration.userauthmgnt.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.model.UserAuthMgnt;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.service.UserAuthMgntService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 사용자 권한 관리 Controller
 * 
 * @author pjh
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/collaboration/userauthmgnt")
public class UserAuthMgntController extends BaseController {
	
	/** The userAuthMgnt service. */
	@Autowired
	private UserAuthMgntService userAuthMgntService;
	
	/** The CommonCode service. */
	@Autowired
	private CommonCodeService commonCodeService;
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User getSessionUser() {
		
		return (User)this.getRequestAttribute("ikep.user");
	}
	
	/**
	 * 사용자 권한 관리 목록조회 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/listUserAuthMgntView.do")
	public ModelAndView listUserAuthMgntView( UserAuthMgntSearchCondition searchCondition,
												@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
												@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn
												) throws Exception{
		
		//화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        SearchResult<UserAuthMgnt> searchResult= null;
        ModelAndView modelAndView = new ModelAndView("/approval/collaboration/userauthmgnt/listUserAuthMgntView");
        
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType", "searchUserId", "searchUserName", "searchWorkGbnCd", "searchGroupId", "searchGroupName", "isAdmin" );
        } else {
        	ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
        	tempSearchConditionString = searchConditionString;
        }
        User user = getSessionUser();
        boolean isEnableSystemAdm = userAuthMgntService.isSystemAdmin( getSessionUser());
        
        searchCondition.setSessionGoupId( user.getGroupId());
        
        searchResult = userAuthMgntService.getUserAuthMgntList( searchCondition, getSessionUser());
		
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("grpCd", "C00001");	// 업무구분
        List<CommonCode> workGnbCdList = commonCodeService.getCommonCodeList( paramMap);
        
        modelAndView.addObject( "searchResult", searchResult);
		modelAndView.addObject( "workGnbCdList", workGnbCdList);
		modelAndView.addObject( "isEnableSystemAdm", isEnableSystemAdm);
		modelAndView.addObject( "searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject( "searchConditionString", tempSearchConditionString);
		modelAndView.addObject( "boardCode", new BoardCode());
		
		return modelAndView;
	}
	
	/**
	 * 사용자 권한 관리 등록 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/editUserAuthMgntView.do")
	public ModelAndView createUserAuthMgntView( UserAuthMgntSearchCondition searchCondition ) throws Exception{
		
		UserAuthMgnt userAuthMgnt = null;
		User loginUser = getSessionUser();
		boolean isEnableSystemAdm = userAuthMgntService.isSystemAdmin( loginUser);
		boolean isEnableSystemGroupAdm = userAuthMgntService.isSystemGroup( loginUser);
		
		if( isEnableSystemGroupAdm && isEnableSystemAdm) {
			throw new Exception( messageSource.getMessage( "ui.approval.userauthmgnt.message.permission.denied", null, Locale.getDefault()));
		}
		
		if( StringUtils.equals( "modify", searchCondition.getViewMode() ) ) {
			
			userAuthMgnt = userAuthMgntService.getUserAuthMgnt( searchCondition, getSessionUser());
		}else{
			userAuthMgnt = new UserAuthMgnt();
			userAuthMgnt.setSaveBntActive(true);
		}
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/userauthmgnt/editUserAuthMgntView");
		
        List<CommonCode> workGnbCdList = commonCodeService.getCommonCodeList( "C00001");
		
		modelAndView.addObject( "workGnbCdList", workGnbCdList);
		modelAndView.addObject( "ynList", UserAuthMgnt.getYnList());
		modelAndView.addObject( "isEnableSystemAdm", isEnableSystemAdm);
		modelAndView.addObject( "userAuthMgnt", userAuthMgnt);
		modelAndView.addObject( "searchCondition", searchCondition);
		
		return modelAndView;
	}
	
	/**
	 * 사용자 권한 관리 등록
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxCreateUserAuthMgnt", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxCreateUserAuthMgnt( @ValidEx UserAuthMgnt userAuthMgnt, BindingResult result){
		
		try {
			
			if (result.hasErrors()) {
				throw new IKEP4AjaxException( "", new Exception("userAuthMgnt Create Valid-Error"));
			}
			userAuthMgntService.createUserAuthMgnt( userAuthMgnt, getSessionUser());
		}catch(IKEP4AjaxValidationException e) {
			throw e;
		}catch(Exception e) {
			throw new IKEP4AjaxException( "userAuthMgnt", e);
		}
	}
	
	/**
	 * 사용자 권한 관리 수정
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserAuthMgnt", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxUpdateUserAuthMgnt( @ValidEx UserAuthMgnt userAuthMgnt, BindingResult result){
		
		try {
			
			if ( result.hasErrors()) {
				throw new IKEP4AjaxException("", new Exception("userAuthMgnt Create Valid-Error"));
			}
			userAuthMgntService.updateUserAuthMgnt( userAuthMgnt, getSessionUser());
		}catch(IKEP4AjaxValidationException e) {
			throw e;
		}catch(Exception e) {
			throw new IKEP4AjaxException( "userAuthMgnt", e);
		}
	}
	
	/**
	 * 사용자 권한 관리 삭제
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxDelUserAuthMgnt", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxDelUserAuthMgnt( @RequestParam String deleteList){
		
		try {
			
			userAuthMgntService.deleteUserAuthMgnt( deleteList, getSessionUser());
		}catch(Exception e) {
			throw new IKEP4AjaxException( "userAuthMgnt", e);
		}
	}
	
}
