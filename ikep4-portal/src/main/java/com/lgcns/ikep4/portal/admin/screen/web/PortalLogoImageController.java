package com.lgcns.ikep4.portal.admin.screen.web;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.service.PortalLogoImageService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 로고 이미지 Controller
 *
 * @author 임종상
 * @version $Id: PortalLogoImageController.java 17495 2012-03-13 08:45:02Z yruyo $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/logoImage")
public class PortalLogoImageController extends BaseController {
	
	@Autowired
	private PortalLogoImageService portalLogoImageService;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 로고 이미지 수정 폼
	 * 
	 * @param accessResult 권한체크 결과
	 * @param saveFlag 저장 플래그
	 * @param model 모델객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updateLogoImageForm.do", method = RequestMethod.GET)
	public String updateLogoImageForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value="saveFlag", required=false) String saveFlag, Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		Portal portal = portalService.readPortal(portalId);
		
		//로고 이미지 체크 없으면 디폴트 이미지 보여준다.
		String logoImageYn = "N";
		
		if(portal != null) {
			if(!StringUtil.isEmpty(portal.getLogoImageId())) {
				FileData fileData = fileService.read(portal.getLogoImageId());
				
				if(fileData != null) {
					File file = new File(fileData.getFilePhysicalPath());
					
					if (file.exists()) {
						logoImageYn = "Y"; 
					}
				}
			}
			
			model.addAttribute("portalName", portal.getPortalName());
			model.addAttribute("logoImageId", portal.getLogoImageId());
		}
		
		Date today = new Date();
		
		model.addAttribute("today", today);
		model.addAttribute("saveFlag", saveFlag);
		model.addAttribute("logoImageYn", logoImageYn);
		
		return "portal/admin/screen/logoImage/updateLogoImageForm";
	}
	
	/**
	 * 로고 이미지 수정
	 * 
	 * @param accessResult 권한체크 결과
	 * @param fileId 업로드된 fileId
	 * @return 처리결과
	 */
	@RequestMapping(value = "/updateLogoImage.do")
	public @ResponseBody Map<String, Object> updateLogoImage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String fileId) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		try {
			portalLogoImageService.updateLogoImage(fileId, user, portalId);
			result.put("status", "success");
		} catch (Exception e) {  
			result.put("status", "fail"); 
		}
		
		return result;
	}
	
	/**
	 * 로그인 이미지 변경 취소
	 * 
	 * @param accessResult 권한체크 결과
	 * @param fileId 업로드된 fileId
	 * @return 처리 결과
	 */
	@RequestMapping(value = "/cancelLogoImage.do")
	public @ResponseBody Map<String, Object> cancelLogoImage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String fileId) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if(fileId != null && !StringUtil.isEmpty(fileId)) {
				fileService.removeFile(fileId);
			}
			result.put("status", "success");
		} catch (Exception e) {  
			result.put("status", "fail");
		}
		
		return result;
	}
	
	/**
	 * 로고 이미지 초기화
	 * 
	 * @param accessResult 권한체크 결과
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/resetLogoImage.do")
	public String resetLogoImage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		try {
			portalLogoImageService.resetLogoImage(user, portalId);
		} catch (Exception e) {  
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			} 
		}
		
		return "redirect:/portal/admin/screen/logoImage/updateLogoImageForm.do?saveFlag=Y";
	}
}
