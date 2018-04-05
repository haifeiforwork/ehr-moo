/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;
import com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService;
import com.lgcns.ikep4.portal.main.model.Portal;
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
 * 퀵 메뉴 Controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalQuickMenuController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/admin/screen/quickmenu")
@SessionAttributes("quickmenu")
public class PortalQuickMenuController extends BaseController {
	
	/**
	 * 퀵 메뉴 관리 service
	 */
	@Autowired
	private PortalQuickMenuService portalQuickMenuService;

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
	 * 포탈 퀵 메뉴 관리 메인 페이지
	 * @param portalQuickMenu 포탈 퀵 메뉴 model
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalQuickMenuMain.do")
	public ModelAndView portalQuickMenuMain(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortalQuickMenu portalQuickMenu, HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/quickmenu/main");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
			
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", user.getLocaleCode());	
			
		List<PortalQuickMenu> portalQuickMenuList = portalQuickMenuService.list(param);
		
		String id = request.getParameter("tempId");
		
		PortalQuickMenu quickMenu = new PortalQuickMenu();
		
		if(!StringUtil.isEmpty(id))
		{
			Map<String,String> readParam = new HashMap<String, String>();
			readParam.put("fieldName", "quickMenuName");
			readParam.put("localeCode", user.getLocaleCode());
			readParam.put("quickMenuId", id);
			
			quickMenu = portalQuickMenuService.read(readParam);
			
			//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
	        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
	        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
			quickMenu.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "quickMenuName"));
			mav.addObject("portalQuickMenu", quickMenu);
			mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
		} else {
			//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
	        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
			quickMenu.setI18nMessageList(i18nMessageService.makeInitLocaleList("quickMenuName"));
			mav.addObject("portalQuickMenu", quickMenu);
			// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
			mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		}
		
		mav.addObject("portalQuickMenuList", portalQuickMenuList);
		mav.addObject("userLocaleCode", user.getLocaleCode());
		mav.addObject("portalQuickMenu", quickMenu);
				
		return mav;
	}
	
	/**
	 * 퀵 메뉴 관리 등록 및 수정 폼의 퀵 메뉴 아이콘 미리보기
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalQuickMenuIconPreView.do")
	public ModelAndView portalMenuIconPreView(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/quickmenu/portalQuickMenuIconPreView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		mav.addObject("userLocaleCode", user.getLocaleCode());
				
		return mav;
	}
	
	/**
	 * 퀵 메뉴 관리 등록 및 수정 폼
	 * @param action 이벤트 종류(add:등록폼, modify:수정폼, cancel:초기화)
	 * @param quickMenuId 퀵 메뉴 아이디
	 * @param portalQuickMenu 포탈 퀵 메뉴 model
	 * @param request HttpServletRequest
	 * @param status SessionStatus
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalQuickMenuForm.do")
	public ModelAndView portalQuickMenuForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String action, String quickMenuId, PortalQuickMenu portalQuickMenu, HttpServletRequest request, 
			SessionStatus status, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav;
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userLocaleCode);
		param.put("quickMenuId", quickMenuId);
		
		mav = new ModelAndView("portal/admin/screen/quickmenu/portalQuickMenuForm");
		
		String id = portalQuickMenu.getQuickMenuId();
		
		PortalQuickMenu quickMenu = new PortalQuickMenu();
		
		if(!StringUtil.isEmpty(id))
		{
			quickMenu = portalQuickMenuService.read(param);
			
			//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
	        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
	        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
			quickMenu.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "quickMenuName"));
			mav.addObject("portalQuickMenu", quickMenu);
			mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
		} else {
			//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
	        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
			quickMenu.setI18nMessageList(i18nMessageService.makeInitLocaleList("quickMenuName"));
			mav.addObject("portalQuickMenu", quickMenu);
			// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
			mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		}
		
		if(!StringUtil.isEmpty(action) && !action.equals("cancel")) {
			if(portalQuickMenu.getQuickMenuId() == null) {
				mav.addObject("action", "add");
			} else {
				mav.addObject("action", "modify");
			}
		} else {
			mav.addObject("action", "cancel");
		}
		
		mav.addObject("portalQuickMenu", quickMenu);
		mav.addObject("userLocaleCode", userLocaleCode);
		
		status.setComplete();
				
		return mav;
	}
	
	/**
	 * 퀵 메뉴 생성
	 * @param portalQuickMenu 포탈 퀵 메뉴 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return Map<String, String> 생성된 퀵 메뉴 아이디, 이름
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalQuickMenuCreate.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> portalMenuCreate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalQuickMenu portalQuickMenu, BindingResult result, SessionStatus status, 
			HttpServletRequest request, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Map<String, String> returnValue = new HashMap<String, String>();
				
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{	
			String quickMenuId = idgenService.getNextId();
			
			portalQuickMenu.setQuickMenuId(quickMenuId);
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			portalQuickMenu.setPortalId(portal.getPortalId());
			
			ModelBeanUtil.bindRegisterInfo(portalQuickMenu, user.getUserId(), user.getUserName());
			
			quickMenuId = portalQuickMenuService.create(portalQuickMenu);
			
			Map<String, String> tempParam = new HashMap<String, String>();
			tempParam.put("quickMenuId", quickMenuId);
			tempParam.put("fieldName", request.getParameter("fieldName"));
			tempParam.put("itemTypeCode", request.getParameter("itemTypeCode"));
			
			PortalQuickMenu returnQuickMenu = new PortalQuickMenu();
			returnQuickMenu = portalQuickMenuService.read(tempParam);
			
			returnValue.put("quickMenuId", returnQuickMenu.getQuickMenuId());
			returnValue.put("quickMenuName", returnQuickMenu.getQuickMenuName());
			
			status.setComplete();
		}
		
		return returnValue;
		
	}
	
	/**
	 * 퀵 메뉴 수정
	 * @param portalQuickMenu 포탈 퀵 메뉴 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param model Model 객체
	 * @param accessResult 접근 가능 여부
	 * @return String 수정된 퀵 메뉴 아이디
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalQuickMenuUpdate.do", method = RequestMethod.POST)
	public @ResponseBody String portalQuickMenuUpdate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalQuickMenu portalQuickMenu, BindingResult result, SessionStatus status, 
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
			String quickMenuId = portalQuickMenu.getQuickMenuId();
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			String userLocaleCode = user.getLocaleCode();
			
			portalQuickMenu.setPortalId(portal.getPortalId());
			
			ModelBeanUtil.bindRegisterInfo(portalQuickMenu, user.getUserId(), user.getUserName());
			
			portalQuickMenuService.update(portalQuickMenu);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("fieldName", "menuName");
			param.put("localeCode", userLocaleCode);
			param.put("quickMenuId", quickMenuId);
			
			PortalQuickMenu quickMenu = new PortalQuickMenu();
			
			quickMenu = portalQuickMenuService.read(param);
			
			returnValue = quickMenu.getQuickMenuId();
			
			status.setComplete();
		}
		
		return returnValue;
		
	}
	
	/**
	 * 퀵 메뉴 삭제
	 * @param quickMenuId 포탈 퀵 메뉴 아이디
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/quickmenu/portalQuickMenuMain.do
	 */
	@RequestMapping(value = "/portalQuickMenuDelete.do", method = RequestMethod.POST)
	public @ResponseBody String portalQuickMenuDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("quickMenuId") String quickMenuId, SessionStatus status, 
			HttpServletRequest request, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		portalQuickMenuService.delete(quickMenuId);
		
		status.setComplete();
		
		return "redirect:/admin/screen/quickmenu/portalQuickMenuMain.do";
	}
	
	/**
	 * 퀵 메뉴 하단으로 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동하고자 하는 위치
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/quickmenu/portalQuickMenuMain.do
	 */
	@RequestMapping(value = "/moveNextPortalQuickMenu.do", method = RequestMethod.POST)
	public @ResponseBody String moveNextPortalQuickMenu(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("startIndex") int startIndex, @RequestParam("moveIndex") int moveIndex, 
			AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("updaterId", user.getUserId());
		param.put("updaterName", user.getUserName());

		portalQuickMenuService.moveNextPortalQuickMenu(startIndex, moveIndex, param);

		return "redirect:/admin/screen/quickmenu/portalQuickMenuMain.do";
		
	}
	
	/**
	 * 퀵 메뉴 상단으로 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동하고자 하는 위치
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/quickmenu/portalQuickMenuMain.do
	 */
	@RequestMapping(value = "/movePrevPortalQuickMenu.do", method = RequestMethod.POST)
	public @ResponseBody String movePrevPortalMenu(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("startIndex") int startIndex, @RequestParam("moveIndex") int moveIndex, 
			AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("updaterId", user.getUserId());
		param.put("updaterName", user.getUserName());

		portalQuickMenuService.movePrevPortalQuickMenu(startIndex, moveIndex, param);

		return "redirect:/admin/screen/quickmenu/portalQuickMenuMain.do";
		
	}
	
}