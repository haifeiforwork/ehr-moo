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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 시스템  Controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemController.java 17712 2012-03-28 06:40:15Z unshj $
 */
@Controller
@RequestMapping(value = "/admin/screen/system")
@SessionAttributes("systemap")
public class PortalSystemController extends BaseController {

	/**
	 * 시스템 관리 service
	 */
	@Autowired
	private PortalSystemService portalSystemService;
	
	/**
	 * 다국어 관리 service
	 */
	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * 포탈 시스템 관리 메인 페이지
	 * @param portalSystem 시스템 model
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/portalSystemMain.do")
	public ModelAndView portalSystemMain(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortalSystem portalSystem, HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/system/main");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		String id = request.getParameter("tempId");
		
		PortalSystem system = new PortalSystem();
		
		if(!StringUtil.isEmpty(id))
		{
			Map<String,String> param = new HashMap<String, String>();
			param.put("fieldName", "systemName");
			param.put("localeCode", user.getLocaleCode());
			param.put("systemCode", id);
			
			system = portalSystemService.read(param);
			
			//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
	        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
	        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
			system.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "systemName"));
			mav.addObject("portalSystem", system);
			mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
		} else {
			//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
	        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
			system.setI18nMessageList(i18nMessageService.makeInitLocaleList("systemName"));
			mav.addObject("portalSystem", system);
			// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
			mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		}
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("systemCode", id);
		paramMap.put("portalId", portal.getPortalId());
		
		int childCount = portalSystemService.getChildCount(paramMap);
			
		mav.addObject("userLocaleCode", user.getLocaleCode());
		mav.addObject("portalSystem", system);
		mav.addObject("childCount", childCount);
		
		return mav;
	}
	
	/**
	 * 시스템 트리 목록
	 * @param systemCode 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return Map<String, Object> 시스템 트리 목록
	 */
	@RequestMapping("/getPortalSystemTree.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> getPortalSystemTree(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam(value = "systemCode", required = false) String systemCode, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Map<String, Object> item = new HashMap<String, Object>();
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		try {			
			Map<String, String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("fieldName", "systemName");
			param.put("localeCode", user.getLocaleCode());		
			
			if(!StringUtil.isEmpty(systemCode)) {
				param.put("parentSystemCode", systemCode);
			}
			
			List<PortalSystem> portalSystemList = portalSystemService.treeList(param);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			for(PortalSystem portalSystem : portalSystemList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "system");
				map.put("code", portalSystem.getSystemCode());
				map.put("name", portalSystem.getSystemName());
				map.put("parent", portalSystem.getParentSystemCode());
				map.put("sortOrder", portalSystem.getSortOrder());
				map.put("level", portalSystem.getDepthLevel());
				
				Map<String,String> paramMap = new HashMap<String, String>();
				paramMap.put("systemCode", portalSystem.getSystemCode());
				paramMap.put("portalId", portal.getPortalId());
				
				int childCount = portalSystemService.getChildCount(paramMap);
				
				map.put("hasChild", childCount);
				list.add(map);
			}

			item.put("items", list);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;
	}
	
	/**
	 * 시스템 등록 및 수정 폼
	 * @param systemCode 시스템 코드
	 * @param portalSystem 시스템 model
	 * @param result BindingResult
	 * @param status BindingResult
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalSystemForm.do")
	public ModelAndView getPortalSystemForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String systemCode, PortalSystem portalSystem, BindingResult result, 
			SessionStatus status, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav;
		
		if (result.hasErrors())	{
			mav = new ModelAndView("portal/admin/screen/system/main");
		} else	{
			
			mav = new ModelAndView("portal/admin/screen/system/portalSystemForm");
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			String userLocaleCode = user.getLocaleCode();
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("fieldName", "systemName");
			param.put("localeCode", userLocaleCode);
			param.put("systemCode", systemCode);
			
			String id = portalSystem.getSystemCode();
			
			PortalSystem system = new PortalSystem();
			
			if(!StringUtil.isEmpty(id))
			{
				system = portalSystemService.read(param);
				
				//makeExistLocaleList("아이템 타입코드","아이템 아이디","field1,field2")); 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
		        //기존 등록된 정보를 화면에 뿌릴수 있는 형태로 Data를 가져옵니다.
				system.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id, "systemName"));
				mav.addObject("portalSystem", system);
				mav.addObject("localeSize",i18nMessageService.selectLocaleAll().size());
			} else {
				//makeInitLocaleList("field1","field2") 다국어 메세지로 저장할 필드명 지정 VO 변수명과 동일하게 지정하며,
		        //"," 구분자로 n개 지정가능(다국어로 저장할 필드가 화면에 여러개일때 n개지정)
				system.setI18nMessageList(i18nMessageService.makeInitLocaleList("systemName"));
				mav.addObject("portalSystem", system);
				// locale의 size 지정 (위에서 지정한 필드의 개수와는 상관없음 , en,ko,jp로 설정되어 있다면 3)
				mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
			}
			
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("systemCode", id);
			paramMap.put("portalId", portal.getPortalId());
			
			int childCount = portalSystemService.getChildCount(paramMap);
			
			mav.addObject("portalSystem", system);
			mav.addObject("childCount", childCount);
			mav.addObject("userLocaleCode", userLocaleCode);
			
			status.setComplete();

		}
		
		return mav;
	}
	
	/**
	 * 시스템 등록 및 수정 폼의 시스템 코드 중복 확인
	 * @param systemCode 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String 시스템 코드 중복 여부(중복:duplicated, 중복아님:success)
	 */
	@RequestMapping(value = "/checkSystemCode.do", method = RequestMethod.POST)
	public @ResponseBody String checkSystemCode(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String systemCode, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		boolean result = portalSystemService.exists(systemCode);

		if(result) {
			return "duplicated";
		} else {
			return "success";
		}
	}
	
	/**
	 * 시스템 생성
	 * @param portalSystem 시스템 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return String 생성된 시스템 코드
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalSystemCreate.do", method = RequestMethod.POST)
	public @ResponseBody String portalSystemCreate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalSystem portalSystem, BindingResult result, SessionStatus status, 
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
			User user = (User) getRequestAttribute("ikep.user");
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");		
			
			portalSystem.setPortalId(portal.getPortalId());
			portalSystem.setOwnerId(user.getUserId());
			
			ModelBeanUtil.bindRegisterInfo(portalSystem, user.getUserId(), user.getUserName());

			returnValue = portalSystemService.create(portalSystem);
			
			status.setComplete();
		}
		
		return returnValue;
	}
	
	/**
	 * 시스템 수정
	 * @param portalSystem 시스템 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param model Model 객체
	 * @param accessResult 접근 가능 여부
	 * @return String 수정된 시스템 코드
	 * @throws Exception
	 */
	@RequestMapping(value = "/portalSystemUpdate.do", method = RequestMethod.POST)
	public @ResponseBody String portalSystemUpdate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortalSystem portalSystem, BindingResult result, SessionStatus status, 
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
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			String userLocaleCode = user.getLocaleCode();
			String systemCode = portalSystem.getSystemCode();
		    
			portalSystem.setPortalId(portal.getPortalId());
			
			ModelBeanUtil.bindRegisterInfo(portalSystem, user.getUserId(), user.getUserName());
			
			portalSystemService.update(portalSystem);
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("fieldName", "systemName");
			param.put("localeCode", userLocaleCode);
			param.put("systemCode", systemCode);
			
			PortalSystem system = new PortalSystem();
			
			system = portalSystemService.read(param);
			
			returnValue = portalSystem.getSystemCode();
			
			status.setComplete();
			
			model.addAttribute("portalSystem", system);
		}
		
		return returnValue;
	}
	
	/**
	 * 시스템 삭제
	 * @param systemCode 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/portalSystemDelete.do", method = RequestMethod.POST)
	public @ResponseBody String portalSystemDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String systemCode, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		portalSystemService.delete(systemCode);
		
		return "";
	}
	
	/**
	 * 시스템 트리 목록 우클릭 메뉴의 위로 이동
	 * @param prevNodeId 트리 순서상 앞에 위치하는 시스템 코드
	 * @param curNodeId 이동하고자 하는 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/moveUpPortalSystem.do", method = RequestMethod.POST)
	public @ResponseBody String moveUpPortalSystem(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String prevNodeId, String curNodeId, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		Map<String,String> preParam = new HashMap<String, String>();
		Map<String,String> curParam = new HashMap<String, String>();
		
		preParam.put("fieldName", "systemName");
		preParam.put("localeCode", userLocaleCode);
		preParam.put("systemCode", prevNodeId);
		
		curParam.put("fieldName", "systemName");
		curParam.put("localeCode", userLocaleCode);
		curParam.put("systemCode", curNodeId);
		
		String prevSortOrder = portalSystemService.read(preParam).getSortOrder();
		String curSortOrder = portalSystemService.read(curParam).getSortOrder();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("prevSortOrder", prevSortOrder);
		map.put("prevNodeId", prevNodeId);
		map.put("curSortOrder", curSortOrder);
		map.put("curNodeId", curNodeId);
		map.put("updaterId", user.getUserId());
		map.put("updaterName", user.getUserName());

		portalSystemService.moveUpPortalSystem(map);

		return "";
		
	}
	
	/**
	 * 시스템 트리 목록 우클릭 메뉴의 아래로 이동
	 * @param nextNodeId 트리 순서상 뒤에 위치하는 시스템 코드
	 * @param curNodeId 이동하고자 하는 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/moveDownPortalSystem.do", method = RequestMethod.POST)
	public @ResponseBody String moveDownPortalSystem(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String nextNodeId, String curNodeId, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		Map<String,String> nextParam = new HashMap<String, String>();
		Map<String,String> curParam = new HashMap<String, String>();
		
		nextParam.put("fieldName", "systemName");
		nextParam.put("localeCode", userLocaleCode);
		nextParam.put("systemCode", nextNodeId);
		
		curParam.put("fieldName", "systemName");
		curParam.put("localeCode", userLocaleCode);
		curParam.put("systemCode", curNodeId);
		
		String nextSortOrder = portalSystemService.read(nextParam).getSortOrder();
		String curSortOrder = portalSystemService.read(curParam).getSortOrder();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("nextSortOrder", nextSortOrder);
		map.put("nextNodeId", nextNodeId);
		map.put("curSortOrder", curSortOrder);
		map.put("curNodeId", curNodeId);
		map.put("updaterId", user.getUserId());
		map.put("updaterName", user.getUserName());

		portalSystemService.moveDownPortalSystem(map);

		return "";
		
	}
	
	/**
	 * 시스템 트리 목록의  드래그 앤 드랍 이동
	 * @param currNodeSystemCode 이동하고자 하는 시스템 코드
	 * @param currParentSystemCode 이동하고자 하는 시스템 코드의 상위 시스템 코드
	 * @param prevNodeSystemCode 트리 순서상 앞에 위치하는 시스템 코드
	 * @param prevParentSystemCode 트리 순서상 앞에 위치하는 시스템 코드의 상위 시스템 코드
	 * @param accessResult 접근 가능 여부
	 */
	@RequestMapping(value = "/moveDndPortalSystem.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateSortOrder(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("currNodeSystemCode") String currNodeSystemCode, 
			@RequestParam("currParentSystemCode") String currParentSystemCode,
			@RequestParam("prevNodeSystemCode") String prevNodeSystemCode,
			@RequestParam("prevParentSystemCode") String prevParentSystemCode,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		if(currParentSystemCode.equals(prevNodeSystemCode)) {	
			
			Map<String, String> paramForRead = new HashMap<String, String>();
			paramForRead.put("systemCode", prevNodeSystemCode);
			PortalSystem prevPortalSystem  = portalSystemService.read(paramForRead);
			String prevSortOrder = prevPortalSystem.getSortOrder();
			String currPortalSystemSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));	
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("sortOrder", StringUtils.leftPad(currPortalSystemSortOrder, 13, "0"));
			param.put("systemCode", currNodeSystemCode);
			param.put("parentSystemCode", currParentSystemCode);
			param.put("updaterId", user.getUserId());
			param.put("updaterName", user.getUserName());
			param.put("prevSortOrder", prevSortOrder);
			portalSystemService.moveDndOneNode(param);
			
		} else if(currParentSystemCode.equals(prevParentSystemCode)) {
			Map<String, String> paramForRead = new HashMap<String, String>();
			paramForRead.put("systemCode", prevNodeSystemCode);
			PortalSystem prevPortalSystem  = portalSystemService.read(paramForRead);
			String prevSortOrder = prevPortalSystem.getSortOrder();
			String currPortalSystemSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));	
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("sortOrder", StringUtils.leftPad(currPortalSystemSortOrder, 13, "0"));
			param.put("systemCode", currNodeSystemCode);
			param.put("parentSystemCode", currParentSystemCode);
			param.put("prevSystemCode", prevNodeSystemCode);
			param.put("updaterId", user.getUserId());
			param.put("updaterName", user.getUserName());
			param.put("prevSortOrder", prevSortOrder);
			
			portalSystemService.moveDndSameNode(param);
		} else {
			Map<String, String> paramForRead = new HashMap<String, String>();
			paramForRead.put("systemCode", prevNodeSystemCode);
			PortalSystem prevPortalSystem  = portalSystemService.read(paramForRead);
			String prevSortOrder = prevPortalSystem.getSortOrder();
			String currPortalSystemSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));	
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("sortOrder", StringUtils.leftPad(currPortalSystemSortOrder, 13, "0"));
			param.put("systemCode", currNodeSystemCode);
			param.put("parentSystemCode", currParentSystemCode);
			param.put("updaterId", user.getUserId());
			param.put("updaterName", user.getUserName());
			param.put("prevSortOrder", prevSortOrder);
			
			portalSystemService.moveDndOtherNode(param);
		}
		
	}
	
}