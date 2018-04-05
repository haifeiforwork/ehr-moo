/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemUrlService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 시스템 Url 관리 Controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemUrlController.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Controller
@RequestMapping(value = "/admin/screen/systemurl")
@SessionAttributes("systemurl")
public class PortalSystemUrlController extends BaseController {
	
	/**
	 * 시스템 URL 관리 service
	 */
	@Autowired
	private PortalSystemUrlService portalSystemUrlService;
	
	/**
	 * 아이디 생성 service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 다국어 관리 service
	 */
	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * 시스템 URL 관리 리스트
	 * @param portalSystemUrl 시스템 URL model
	 * @param searchCondition PortalSystemUrlSearchCondition
	 * @param accessResult 접근 가능 여부
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalSystemUrlList.do")
	public ModelAndView getPortalSystemUrlList(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortalSystemUrl portalSystemUrl, PortalSystemUrlSearchCondition searchCondition, 
			AccessingResult accessResult, HttpServletRequest request) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/systemurl/list");
		
		try {
			PortalCode portalCode = new PortalCode();
			
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			searchCondition.setFieldName("systemUrlName");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portalId);
			
			SearchResult<PortalSystemUrl> searchResult = portalSystemUrlService.listBySearchCondition(searchCondition);
	
			String id = request.getParameter("tempId");
			
			PortalSystemUrl systemUrl = new PortalSystemUrl();
			
			if(!StringUtil.isEmpty(id)) {
				Map<String,String> param = new HashMap<String, String>();
				param.put("fieldName", "systemUrlName");
				param.put("localeCode", user.getLocaleCode());
				param.put("systemUrlId", id);
				
				systemUrl = portalSystemUrlService.read(param);
				
				//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
		        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
				systemUrl.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "systemUrlName"));
				mav.addObject("portalSystemUrl", systemUrl);
				mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
			} else {
				//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
				systemUrl.setI18nMessageList(i18nMessageService.makeInitLocaleList("systemUrlName"));
				mav.addObject("portalSystemUrl", systemUrl);
				// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
				mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
			}
			
			mav.addObject("userLocaleCode", user.getLocaleCode());
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("portalCode", portalCode);
			mav.addObject("portalSystemUrl", systemUrl);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
		
	}
	
	/**
	 * 시스템 URL 수정 폼
	 * @param systemUrlId 시스템 URL 아이디
	 * @param portalSystemUrl 시스템 URL model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalSystemUrlForm.do")
	public ModelAndView getPortalSystemUrlForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String systemUrlId, PortalSystemUrl portalSystemUrl, BindingResult result, 
			SessionStatus status, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "systemUrlName");
		param.put("localeCode", userLocaleCode);
		param.put("systemUrlId", systemUrlId);
		
		ModelAndView mav;
		
		if (result.hasErrors())	{
			mav = new ModelAndView("portal/admin/screen/systemurl/list");
		} else	{
			
			mav = new ModelAndView("portal/admin/screen/systemurl/portalSystemUrlForm");
			
			String id = portalSystemUrl.getSystemUrlId();
			
			PortalSystemUrl systemUrl = new PortalSystemUrl();
			
			if(!StringUtil.isEmpty(id))
			{
				systemUrl = portalSystemUrlService.read(param);
				
				//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
		        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
				systemUrl.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "systemUrlName"));
				mav.addObject("portalSystemUrl", systemUrl);
				mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
			} else {
				//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
				systemUrl.setI18nMessageList(i18nMessageService.makeInitLocaleList("systemUrlName"));
				mav.addObject("portalSystemUrl", systemUrl);
				// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
				mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
			}
			
			mav.addObject("portalSystemUrl", systemUrl);
			mav.addObject("userLocaleCode", userLocaleCode);
			
			status.setComplete();
			
		}
		
		return mav;
	}
	
	/**
	 * 시스템 URL 생성
	 * @param portalSystemUrl 시스템 URL 정보
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return String 생성된 시스템 URL 아이디
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalSystemUrlCreate.do", method = RequestMethod.POST)
	public @ResponseBody String portalSystemUrlCreate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalSystemUrl portalSystemUrl, BindingResult result, SessionStatus status, 
			HttpServletRequest request, AccessingResult accessResult) {
		
		String returnValue = "";
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{
			String portalId = (String) getRequestAttribute("ikep.portalId");
			String systemUrlId = idgenService.getNextId();
			
			portalSystemUrl.setSystemUrlId(systemUrlId);
			portalSystemUrl.setPortalId(portalId);
			
			User user = (User) getRequestAttribute("ikep.user");
			
			ModelBeanUtil.bindRegisterInfo(portalSystemUrl, user.getUserId(), user.getUserName());
			
			returnValue = portalSystemUrlService.create(portalSystemUrl);
			
			status.setComplete();
		}
		
		return returnValue;
		
	}
	
	/**
	 * 시스템 URL 수정
	 * @param portalSystemUrl  시스템 URL 정보
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param model Model 객체
	 * @param accessResult 접근 가능 여부
	 * @return String 수정된 시스템 URL 아이디
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalSystemUrlUpdate.do", method = RequestMethod.POST)
	public @ResponseBody String portalSystemUrlUpdate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalSystemUrl portalSystemUrl, BindingResult result, SessionStatus status, 
			HttpServletRequest request, Model model, AccessingResult accessResult) {
		
		String returnValue = "";
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{
			String systemUrlId = portalSystemUrl.getSystemUrlId();
			
			User user = (User) getRequestAttribute("ikep.user");
			String userLocaleCode = user.getLocaleCode();
			
			ModelBeanUtil.bindRegisterInfo(portalSystemUrl, user.getUserId(), user.getUserName());
			
			portalSystemUrlService.update(portalSystemUrl);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("fieldName", "systemUrlName");
			param.put("localeCode", userLocaleCode);
			param.put("systemUrlId", systemUrlId);
			
			PortalSystemUrl systemUrl = new PortalSystemUrl();
			
			systemUrl = portalSystemUrlService.read(param);
			
			returnValue = systemUrl.getSystemUrlId();
			
			status.setComplete();
			
			model.addAttribute("portalSystemUrl", systemUrl);
		}
		
		return returnValue;
	}
	
	/**
	 * 시스템 URL 삭제
	 * @param portalSystemUrl 시스템 URL 정보
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/portalSystemUrlDelete.do", method = RequestMethod.POST)
	public @ResponseBody String portalSystemUrlDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortalSystemUrl portalSystemUrl, BindingResult result, 
			SessionStatus status, HttpServletRequest request, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{
			
			String id = portalSystemUrl.getSystemUrlId();
			
			portalSystemUrlService.delete(id);
			
			status.setComplete();
		}
		
		return "";
	}
	
	/**
	 * 시스템 URL 팝업 리스트
	 * @param searchCondition PortalSystemUrlSearchCondition
	 * @param accessResult 접근 가능 여부
	 * @return
	 */
	@RequestMapping(value = "/popupPortalSystemUrlList.do")
	public ModelAndView getPopupPortalSystemUrlList(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortalSystemUrlSearchCondition searchCondition, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/systemurl/popupList");
		
		try {
			PortalCode portalCode = new PortalCode();
			
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			searchCondition.setFieldName("systemUrlName");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portalId);
			
			SearchResult<PortalSystemUrl> searchResult = portalSystemUrlService.listBySearchCondition(searchCondition);
	
			mav.addObject("userLocaleCode", user.getLocaleCode());
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("portalCode", portalCode);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
}