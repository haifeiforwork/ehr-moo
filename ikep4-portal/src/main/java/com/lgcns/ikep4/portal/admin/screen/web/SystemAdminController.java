package com.lgcns.ikep4.portal.admin.screen.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdmin;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdminSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.SystemAdminService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 시스템 Admin Controller
 *
 * @author 임종상
 * @version $Id: SystemAdminController.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/systemAdmin")
public class SystemAdminController extends BaseController {
	
	@Autowired
	private SystemAdminService systemAdminService;
	
	/**
	 * 시스템 관리자 조회
	 * @param accessResult 권한체크 결과
	 * @param searchCondition 검색 조건
	 * @param saveFlag 저장 플래그
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/readSystemAdmin.do")
	public String readSystemAdmin(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			SystemAdminSearchCondition searchCondition, @RequestParam(value="saveFlag", required=false) String saveFlag, Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalCode portalCode = new PortalCode();
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		searchCondition.setPortalId(portalId);
		
		SearchResult<SystemAdmin> searchResult = systemAdminService.listSystemAdmin(searchCondition);
		
		if (StringUtil.isEmpty(searchCondition.getResourceName()) && searchResult.getEntity() != null && searchResult.getEntity().size() > 0) {
			searchCondition.setResourceName(searchResult.getEntity().get(0).getResourceName());
		}
		
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);
		model.addAttribute("saveFlag", saveFlag);
		
		return "portal/admin/screen/systemAdmin/readSystemAdmin";
	}
	
	/**
	 * 시스템 관리자 수정
	 * @param accessResult 권한체크 결과
	 * @param searchCondition 검색 조건
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updateSystemAdminForm.do")
	public String updateSystemAdminForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			SystemAdminSearchCondition searchCondition, Model model) {
	
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalCode portalCode = new PortalCode();
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		searchCondition.setPortalId(portalId);
		
		SearchResult<SystemAdmin> searchResult = systemAdminService.listSystemAdmin(searchCondition);
		
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);
		
		return "portal/admin/screen/systemAdmin/updateSystemAdminForm";
	}
	
	/**
	 * 시스템 관리자 수정
	 * @param accessResult 권한체크 결과
	 * @param systemAdmin 시스템 관리자 모델
	 * @return 포워딩 정보
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/updateSystemAdmin.do")
	public String updateSystemAdmin(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@ModelAttribute SystemAdmin systemAdmin) throws UnsupportedEncodingException {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		systemAdminService.updateSystemAdmin(systemAdmin, portal.getPortalId());
		
		String encodeResourceName = URLEncoder.encode(systemAdmin.getResourceName().replace("&","%26"), "UTF-8");
		
		return "redirect:/portal/admin/screen/systemAdmin/readSystemAdmin.do?resourceName="+encodeResourceName+"&saveFlag=Y";
	}

}