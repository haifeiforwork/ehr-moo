package com.lgcns.ikep4.portal.admin.screen.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalCode;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포탈 Controller
 *
 * @author 임종상
 * @version $Id: PortalController.java 19462 2012-06-22 07:19:41Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/portal")
public class PortalController extends BaseController {

	@Autowired
	private PortalService portalService;
	
	@Autowired
	private LocaleCodeService localeCodeService;
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 포탈 등록폼
	 * 
	 * @param accessResult 권한체크 결과
	 * @param model 모델객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createPortalForm.do", method = RequestMethod.GET)
	public String createPortalForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		List<LocaleCode> localeCodeList = localeCodeService.list(user.getLocaleCode());
		List<Portal> portalList = portalService.listPortalAll();
		
		model.addAttribute("portal", new Portal());
		model.addAttribute("localeCodeList", localeCodeList);
		model.addAttribute("portalList", portalList);
		
		return "portal/admin/screen/portal/createPortalForm";
	}
	
	/**
	 * 포탈 등록
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portal portal Model
	 * @param result Validation 체크 결과 
	 * @return 포탈 ID
	 */
	@RequestMapping(value = "/createPortal.do", method = RequestMethod.POST)
	public @ResponseBody String createPortal(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@ValidEx Portal portal, BindingResult result) {

		String returnValue = "";
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if(result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		portal.setRegisterId(user.getUserId());
		portal.setRegisterName(user.getUserName());
		portal.setUpdaterId(user.getUserId());
		portal.setUpdaterName(user.getUserName());
		
		portalService.createPortal(portal);
		
		returnValue = portal.getPortalId();
		
		return returnValue;
	}
	
	/**
	 * 포탈 조회
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalId 포탈 ID
	 * @param portal portal Model
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/readPortal.do", method = RequestMethod.GET)
	public String readPortal(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String portalId, Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		Portal portal = portalService.readPortal(portalId);
		
		if(portal != null) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("fieldName", "localeName");
			param.put("userLocaleCode", user.getLocaleCode());
			param.put("localeCode", portal.getDefaultLocaleCode());
			
			Map<String, String> localeParam = new HashMap<String, String>();
			localeParam.put("userLocaleCode", user.getLocaleCode());
			localeParam.put("localeCode", portal.getDefaultLocaleCode());
			LocaleCode localeCode = localeCodeService.localeInfo(localeParam);
			
			if(localeCode != null) {
				portal.setDefaultLocaleName(localeCode.getLocaleName());
			}
			
			String className = "Portal";
			String resourceName = "Portal";
			String operationName = "MANAGE";
			
			ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
			
			// 리소스에 대한 권한 정보를 읽어 온다.
			aclResourcePermission = aclService.getSystemPermission(className, resourceName, operationName);
	
			// 권한에 대한 상세정보를 조회 한다.
			if(aclResourcePermission != null)
			{
				aclResourcePermission = portalService.listPortalAdminPermission(aclResourcePermission, portal.getPortalId());
			}
			
			model.addAttribute("listAdminUser", aclResourcePermission);
			model.addAttribute("countAdminUser", aclResourcePermission.getAssignUserIdList().size());
		}
		
		model.addAttribute("portal", portal);
		
		return "portal/admin/screen/portal/readPortal";
	}
	
	/**
	 * 포탈 수정 폼
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalId 포탈 ID
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updatePortalForm.do", method = RequestMethod.GET)
	public String updatePortalForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String portalId, Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		Portal portal = portalService.readPortal(portalId);
		
		List<LocaleCode> localeCodeList = localeCodeService.list(user.getLocaleCode());
		
		if(portal != null) {
			String className = "Portal";
			String resourceName = "Portal";
			String operationName = "MANAGE";
			
			ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
			
			// 리소스에 대한 권한 정보를 읽어 온다.
			aclResourcePermission = aclService.getSystemPermission(className, resourceName, operationName);
	
			// 권한에 대한 상세정보를 조회 한다.
			if(aclResourcePermission != null)
			{
				aclResourcePermission = portalService.listPortalAdminPermission(aclResourcePermission, portal.getPortalId());
			}
			
			model.addAttribute("listAdminUser", aclResourcePermission);
			model.addAttribute("countAdminUser", aclResourcePermission.getAssignUserIdList().size());
		}
		
		model.addAttribute("portal", portal);
		model.addAttribute("localeCodeList", localeCodeList);
		
		return "portal/admin/screen/portal/updatePortalForm";
	}
	
	/**
	 * 포탈 수정
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portal 포탈 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updatePortal.do", method = RequestMethod.POST)
	public @ResponseBody String updatePortal(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@ModelAttribute Portal portal) {

		String returnValue = "";
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		portal.setUpdaterId(user.getUserId());
		portal.setUpdaterName(user.getUserName());
		
		portalService.updatePortal(portal);
		
		// 포탈 캐시 전체 삭제
		cacheService.removeCacheAllPortal();
		
		returnValue = portal.getPortalId();
		
		return returnValue;
	}
	
	/**
	 * 포탈 목록
	 * 
	 * @param accessResult 권한체크 결과
	 * @param searchCondition 검색조건
	 * @param model 모델 객체 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/listPortal.do")
	public String listPortal(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			PortalSearchCondition searchCondition, Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalCode portalCode = new PortalCode();
		
		SearchResult<Portal> searchResult = portalService.listPortal(searchCondition);
		
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);
		
		return "portal/admin/screen/portal/listPortal";
	}
	
	/**
	 * 포탈 삭제
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalId 포탈 ID 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/removePortal.do", method = RequestMethod.POST)
	public String removePortal(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String portalId) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		portalService.removePortal(portalId);
		
		// 포탈 캐시 전체 삭제
		cacheService.removeCacheAllPortal();
		
		return "redirect:/portal/admin/screen/portal/listPortal.do";
	}
}