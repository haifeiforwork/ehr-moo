/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import java.util.ArrayList;
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

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 포탈 메뉴 Controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalMenuController.java 19467 2012-06-22 07:23:23Z malboru80 $
 */
@Controller
@RequestMapping(value = "/admin/screen/menu")
@SessionAttributes("menu")
public class PortalMenuController extends BaseController {
	
	/**
	 * 메뉴 관리  service
	 */
	@Autowired
	private PortalMenuService portalMenuService;
	
	/**
	 * 시스템 관리 service
	 */
	@Autowired
	private PortalSystemService portalSystemService;
	
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
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 포탈 메뉴 관리 메인 페이지
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalMenuMain.do")
	public ModelAndView portalMenuMain(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/menu/main");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
			
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", portal.getPortalId());
		param.put("fieldName", "menuName");
		param.put("localeCode", user.getLocaleCode());	
			
		List<PortalMenu> portalMenuList = portalMenuService.list(param);
		
		mav.addObject("portalMenuList", portalMenuList);
		mav.addObject("userLocaleCode", user.getLocaleCode());
				
		return mav;
	}
	
	/**
	 * 메뉴 관리 등록 및 수정폼의 메뉴 아이콘 미리보기
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalMenuIconPreView.do")
	public ModelAndView portalMenuIconPreView(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/menu/portalMenuIconPreView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		mav.addObject("userLocaleCode", user.getLocaleCode());
				
		return mav;
	}
	
	/**
	 * 메뉴 생성 및 수정폼
	 * @param menuId 메뉴 아이디
	 * @param portalMenu 메뉴 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalMenuForm.do")
	public ModelAndView portalMenuForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String menuId, PortalMenu portalMenu, BindingResult result, 
			SessionStatus status, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "menuName");
		param.put("localeCode", userLocaleCode);
		param.put("menuId", menuId);
		
		ModelAndView mav;
		
		PortalSystem portalSystem = null;
		
		if (result.hasErrors())	{
			mav = new ModelAndView("portal/admin/screen/menu/main");
		} else	{
			mav = new ModelAndView("portal/admin/screen/menu/portalMenuForm");
			
			String id = portalMenu.getMenuId();
			
			PortalMenu menu = new PortalMenu();
			
			if(!StringUtil.isEmpty(id))
			{
				menu = portalMenuService.read(param);
				
				Map<String,String> systemParam = new HashMap<String, String>();
				systemParam.put("fieldName", "systemName");
				systemParam.put("localeCode", userLocaleCode);
				systemParam.put("systemCode", portalMenu.getSystemCode());
				
				portalSystem = portalSystemService.read(systemParam);
				
				//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
		        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
				menu.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "menuName"));
				mav.addObject("portalMenu", menu);
				mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
			} else {
				//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
				menu.setI18nMessageList(i18nMessageService.makeInitLocaleList("menuName"));
				mav.addObject("portalMenu", menu);
				// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
				mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
			}
			
			mav.addObject("portalMenu", menu);
		}
		
		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("portalSystem", portalSystem);
		
		status.setComplete();
				
		return mav;
	}
	
	/**
	 * 메뉴 생성
	 * @param portalMenu 메뉴 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return Map<String, String> 생성된 메뉴 아이디, 부모 아이디
	 */
	@RequestMapping(value = "/portalMenuCreate.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> portalMenuCreate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalMenu portalMenu, BindingResult result, SessionStatus status, 
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
			User user = (User) getRequestAttribute("ikep.user");
			
			String menuId = idgenService.getNextId();
			
			portalMenu.setMenuId(menuId);
			
			ModelBeanUtil.bindRegisterInfo(portalMenu, user.getUserId(), user.getUserName());
			
			String systemCode = "";
			
			if(StringUtil.isEmpty(portalMenu.getSystemCode())) {
				systemCode = portalSystemService.getSystemCode("Portal", user.getPortalId());
			}
			
			if(!StringUtil.isEmpty(systemCode)) {
				portalMenu.setSystemCode(systemCode);
				
				if(StringUtil.isEmpty(portalMenu.getParentMenuId())) {
					portalMenu.setParentMenuId(null);
				}
				
				menuId = portalMenuService.create(portalMenu);
				
				// 사용자별 메뉴 캐시 Element 전체 삭제
				cacheService.removeCacheElementAll("menu");
				
				returnValue.put("menuId", menuId);
				returnValue.put("parentMenuId", portalMenu.getParentMenuId());
				returnValue.put("action", "success");
			} else {
				returnValue.put("action", "fail");
			}
			
			status.setComplete();
		}
		
		return returnValue;
		
	}
	
	/**
	 * 메뉴 수정
	 * @param portalMenu 메뉴 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param model Model 객체
	 * @param accessResult 접근 가능 여부
	 * @return Map<String, String> 수정된 메뉴 아이디
	 */
	@RequestMapping(value = "/portalMenuUpdate.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> portalMenuUpdate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalMenu portalMenu, BindingResult result, SessionStatus status, 
			HttpServletRequest request, Model model, AccessingResult accessResult) {
		
		Map<String, String> returnValue = new HashMap<String, String>();
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{
			String menuId = portalMenu.getMenuId();
			
			User user = (User) getRequestAttribute("ikep.user");
			String userLocaleCode = user.getLocaleCode();
			
			ModelBeanUtil.bindRegisterInfo(portalMenu, user.getUserId(), user.getUserName());
			
			String systemCode = "";
			
			if(StringUtil.isEmpty(portalMenu.getSystemCode())) {
				systemCode = portalSystemService.getSystemCode("Portal", user.getPortalId());
			}
			
			if(!StringUtil.isEmpty(systemCode)) {
				portalMenu.setSystemCode(systemCode);
				
				if(StringUtil.isEmpty(portalMenu.getParentMenuId())) {
					portalMenu.setParentMenuId(null);
				}
				
				portalMenuService.update(portalMenu);
				
				// 사용자별 메뉴 캐시 Element 전체 삭제
				cacheService.removeCacheElementAll("menu");
				
				Map<String,String> param = new HashMap<String, String>();
				param.put("fieldName", "menuName");
				param.put("localeCode", userLocaleCode);
				param.put("menuId", menuId);
				
				PortalMenu menu = new PortalMenu();
				menu = portalMenuService.read(param);
				
				returnValue.put("menuId", menu.getMenuId());
				returnValue.put("action", "success");
			} else {
				returnValue.put("action", "fail");
			}
			
			status.setComplete();
		}
		
		return returnValue;
		
	}
	
	/**
	 * 메뉴 삭제
	 * @param menuId 메뉴 아이디
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/menu/portalMenuMain.do
	 */
	@RequestMapping(value = "/portalMenuDelete.do", method = RequestMethod.POST)
	public @ResponseBody String portalMenuDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("menuId") String menuId, SessionStatus status, 
			HttpServletRequest request, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		portalMenuService.delete(menuId);
		
		// 사용자별 메뉴 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("menu");
		
		status.setComplete();
		
		return "redirect:/admin/screen/menu/portalMenuMain.do";
	}
	
	/**
	 * 상위 메뉴의 우측으로 이동 또는 하위 메뉴의 하단으로 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param parentMenuId 상위 메뉴 아이디(상위 메뉴:값 없음, 하위 메뉴:상위 메뉴)
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/menu/portalMenuMain.do
	 */
	@RequestMapping(value = "/moveNextPortalMenu.do", method = RequestMethod.POST)
	public @ResponseBody String moveNextPortalMenu(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("startIndex") int startIndex, 
			@RequestParam("moveIndex") int moveIndex, 
			@RequestParam("parentMenuId") String parentMenuId, 
			AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", portal.getPortalId());
		param.put("updaterId", user.getUserId());
		param.put("updaterName", user.getUserName());
		
		if(StringUtil.isEmpty(parentMenuId)) {
			param.put("parentMenuId", null);
		} else {
			param.put("parentMenuId", parentMenuId);
		}

		portalMenuService.moveNextPortalMenu(startIndex, moveIndex, param);
		
		// 사용자별 메뉴 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("menu");

		return "redirect:/admin/screen/menu/portalMenuMain.do";
		
	}
	
	/**
	 * 상위 메뉴의 좌측으로 이동 또는 하위 메뉴의 상단으로 이동
	 * @param startIndex 이동 전 위치
	 * @param moveIndex 이동 후 위치
	 * @param parentMenuId 상위 메뉴 아이디(상위 메뉴:값 없음, 하위 메뉴:상위 메뉴)
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/menu/portalMenuMain.do
	 */
	@RequestMapping(value = "/movePrevPortalMenu.do", method = RequestMethod.POST)
	public @ResponseBody String movePrevPortalMenu(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("startIndex") int startIndex, 
			@RequestParam("moveIndex") int moveIndex, 
			@RequestParam("parentMenuId") String parentMenuId, 
			AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", portal.getPortalId());
		param.put("updaterId", user.getUserId());
		param.put("updaterName", user.getUserName());
		
		if(StringUtil.isEmpty(parentMenuId)) {
			param.put("parentMenuId", null);
		} else {
			param.put("parentMenuId", parentMenuId);
		}

		portalMenuService.movePrevPortalMenu(startIndex, moveIndex, param);
		
		// 사용자별 메뉴 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("menu");

		return "redirect:/admin/screen/menu/portalMenuMain.do";
		
	}
	
	/**
	 * 하위 메뉴의 다른 상위 메뉴로의 이동
	 * @param moveIndex 이동 전 위치
	 * @param parentMenuId 이동 후 속한 상위 메뉴의 아이디
	 * @param menuId 이동 후 위치
	 * @param accessResult 접근 가능 여부
	 * @return String redirect url /admin/screen/menu/portalMenuMain.do
	 */
	@RequestMapping(value = "/moveOtherPortalMenu.do", method = RequestMethod.POST)
	public @ResponseBody String moveOtherPortalMenu(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("moveIndex") int moveIndex, 
			@RequestParam("parentMenuId") String parentMenuId, 
			@RequestParam("menuId") String menuId, 
			AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", portal.getPortalId());
		param.put("updaterId", user.getUserId());
		param.put("updaterName", user.getUserName());
		param.put("parentMenuId", parentMenuId);
		param.put("menuId", menuId);

		portalMenuService.moveOtherPortalMenu(moveIndex, param);
		
		// 사용자별 메뉴 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("menu");

		return "redirect:/admin/screen/menu/portalMenuMain.do";
		
	}
	
	
	
	/**
	 * 메뉴 URL 팝업 리스트
	 * @param searchCondition PortalSystemUrlSearchCondition
	 * @param accessResult 접근 가능 여부
	 * @return
	 */
	@RequestMapping(value = "/popupPortalMenuUrlList.do")
	public ModelAndView getPopupPortalMenuUrlList(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/menuurl/popupList");
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
				
			Map<String, String> param = new HashMap<String, String>();
			param.put("systemName", "Portal");
			param.put("portalId", portal.getPortalId());
			param.put("fieldName", "menuName");
			param.put("localeCode", user.getLocaleCode());	
			
			List<PortalMenu> portalMenuList = portalMenuService.list(param);
			
			mav.addObject("portalMenuList", portalMenuList);
			mav.addObject("userLocaleCode", user.getLocaleCode());
			
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	
	
	/**
	 * 메뉴 URL 메시지 정보 처리
	 * @param searchCondition PortalSystemUrlSearchCondition
	 * @param accessResult 접근 가능 여부
	 * @return
	 */
	@RequestMapping(value = "/popupPortalMenuMessage.do")
	public @ResponseBody List<String> getPopupList(String menuId) {
		List<String> menuName = new ArrayList<String>();
		try {	
			List<I18nMessage> i18nMessageList = i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, menuId, "menuName");			
			
			int size = i18nMessageService.selectLocaleAll().size();
			
			for(int i=0 ; i< size; i++){
				menuName.add(i18nMessageList.get(i).getItemMessage());
				
			}
		}catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return menuName;
	}

	
	
	
	
	
}