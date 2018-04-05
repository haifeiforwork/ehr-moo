package com.lgcns.ikep4.portal.admin.screen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPopup;
import com.lgcns.ikep4.portal.admin.screen.search.PortalPopupSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPopupService;
import com.lgcns.ikep4.portal.main.model.PortalCode;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;

/**
 * 
 * 포탈 팝업 관리
 *
 * @author 임종상
 * @version $Id: PortalPopupController.java 375 2011-06-13 09:26:38Z dev07 $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/popup")
public class PortalPopupController extends BaseController {
	
	@Autowired
	private PortalPopupService portalPopupService; 
	
	/**
	 * 포탈 팝업 목록
	 * @param accessResult
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listPopup.do")
	public ModelAndView listPopup(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			PortalPopupSearchCondition searchCondition, Model model) {

		ModelAndView mav = new ModelAndView();
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		searchCondition.setPortalId(portalId);
		
		PortalCode portalCode = new PortalCode();
		
		SearchResult<PortalPopup> searchResult = portalPopupService.listPopup(searchCondition);
		
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);
		
		return mav;
	}
	
	/**
	 * 포탈 팝업 등록폼
	 * @param accessResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/createPopupForm.do", method = RequestMethod.GET)
	public ModelAndView createPopupForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model) {
		
		ModelAndView mav = new ModelAndView();
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		model.addAttribute("today", DateUtil.getToday("yyyy.MM.dd"));
		
		return mav;
	}
	
	/**
	 * 포탈 팝업 등록
	 * @param accessResult
	 * @param portalPopup
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/createPopup.do", method = RequestMethod.POST)
	public @ResponseBody String createPopup(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@ValidEx PortalPopup portalPopup, BindingResult result) {

		try {
			if (!accessResult.isAccess()) { // true : 승인, false : 비승인
				throw new IKEP4AjaxException("", new Exception());
			}
			
			if(result.hasErrors()) {
				throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
			}
			
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			String popupStartDate = portalPopup.getPopupStartDate().replaceAll("\\.", "");
			String popupEndDate = portalPopup.getPopupEndDate().replaceAll("\\.", "");
			
			portalPopup.setPortalId(portalId);
			portalPopup.setPopupStartDate(popupStartDate);
			portalPopup.setPopupEndDate(popupEndDate);
			portalPopup.setRegisterId(user.getUserId());
			portalPopup.setRegisterName(user.getUserName());
			portalPopup.setUpdaterId(user.getUserId());
			portalPopup.setUpdaterName(user.getUserName());
			
			if("C".equals(portalPopup.getPopupType())) {
				portalPopup.setContents(replaceContentsHtmlBodyRemove(portalPopup.getContents()));
				portalPopup.setPopupUrl("");
			} else {
				portalPopup.setContents("");
			}
			
			portalPopupService.createPopup(portalPopup);
			
			return portalPopup.getPopupId();
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}
	}
	
	/**
	 * 포탈 팝업 조회
	 * @param accessResult
	 * @param popupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readPopup.do", method = RequestMethod.GET)
	public ModelAndView readPopup(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String popupId, Model model) {

		ModelAndView mav = new ModelAndView();
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalPopup portalPopup = portalPopupService.readPopup(popupId);

		model.addAttribute("portalPopup", portalPopup);
		
		return mav;
	}
	
	/**
	 * 포탈 팝업 삭제
	 * @param accessResult
	 * @param popupId
	 * @return
	 */
	@RequestMapping(value = "/removePopup.do", method = RequestMethod.POST)
	public String removePopup(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String popupId) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		portalPopupService.removePopup(popupId);
		
		return "redirect:/portal/admin/screen/popup/listPopup.do";
	}
	
	/**
	 * 포탈 팝업 수정폼
	 * @param accessResult
	 * @param popupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updatePopupForm.do", method = RequestMethod.GET)
	public ModelAndView updatePopupForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String popupId, Model model) {
		
		ModelAndView mav = new ModelAndView();

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalPopup portalPopup = portalPopupService.readPopup(popupId);
		
		model.addAttribute("portalPopup", portalPopup);
		
		return mav;
	}
	
	/**
	 * 포탈 팝업 수정
	 * @param accessResult
	 * @param portalPopup
	 * @return
	 */
	@RequestMapping(value = "/updatePopup.do", method = RequestMethod.POST)
	public @ResponseBody String updatePopup(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@ValidEx PortalPopup portalPopup, BindingResult result) {

		try {
			if (!accessResult.isAccess()) { // true : 승인, false : 비승인
				// IKEP4AuthorizedException을 throw하면 common/notAuthorized.jsp 로 페이지 전환이 된다.
				throw new IKEP4AjaxException("", new Exception());
			}
			
			if(result.hasErrors()) {
				throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
			}
			
			User user = (User) getRequestAttribute("ikep.user");
			
			String popupStartDate = portalPopup.getPopupStartDate().replaceAll("\\.", "");
			String popupEndDate = portalPopup.getPopupEndDate().replaceAll("\\.", "");
			
			portalPopup.setPopupStartDate(popupStartDate);
			portalPopup.setPopupEndDate(popupEndDate);
			portalPopup.setUpdaterId(user.getUserId());
			portalPopup.setUpdaterName(user.getUserName());
			
			if("C".equals(portalPopup.getPopupType())) {
				portalPopup.setContents(replaceContentsHtmlBodyRemove(portalPopup.getContents()));
				portalPopup.setPopupUrl("");
			} else {
				portalPopup.setContents("");
			}
			
			portalPopupService.updatePopup(portalPopup);
			
			return portalPopup.getPopupId();
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}
	}
	
	/**
	 * 팝업 IFRAME 페이지
	 * @param popupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mainPopup.do")
	public ModelAndView mainPopup(@RequestParam String popupId) {

		ModelAndView mav = new ModelAndView();
		
		PortalPopup portalPopup = portalPopupService.readPopup(popupId);
		
		mav.addObject("portalPopup", portalPopup);
		
		return mav;
	}
	
	public String replaceContentsHtmlBodyRemove(String contents) {
		String str = contents;

		str = str.replaceAll("<html>", "");
		str = str.replaceAll("</html>", "");
		str = str.replaceAll("<header>", "");
		str = str.replaceAll("</header>", "");
		str = str.replaceAll("<body>", "");
		str = str.replaceAll("</body>", "");
		str = str.replaceAll("<HTML>", "");
		str = str.replaceAll("</HTML>", "");
		str = str.replaceAll("<HEADER>", "");
		str = str.replaceAll("</HEADER>", "");
		str = str.replaceAll("<BODY>", "");
		str = str.replaceAll("</BODY>", "");
		
		return str;
	}
}
