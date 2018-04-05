/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.meetingroom.service.BuildingFloorService;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.EquipmentService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/cartooletc")
public class CarToolEtcAdminController extends BaseController {
	
	@Autowired
	MeetingRoomService meetingRoomService;
	
	@Autowired
	CartooletcService cartooletcService;
	
	
	@Autowired
	BuildingFloorService buildingFloorService;
	
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	private ApproveService approveService;
	
	@Autowired
	UserDao userDao;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	
	@RequestMapping(value = "/cartooletcMain.do")
	public ModelAndView cartooletcList(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/cartooletc/cartooletcMain");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", null);
		
		//List<BuildingFloor> buildingList = buildingFloorService.buildingList(paramMap);
		
		
		List<Cartooletc> categoryList = cartooletcService.categoryList(paramMap);
		List<Cartooletc> regionList = cartooletcService.regionList(paramMap);

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("userId", user.getUserId());
		
		Boolean isCarToolManager = this.isCarToolManager(param);
		
		String categoryId = request.getParameter("categoryId");
		String regionId = request.getParameter("regionId");
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("isCarToolManager", isCarToolManager);
		mav.addObject("categoryList", categoryList);
		mav.addObject("regionList", regionList);
		mav.addObject("categoryId", categoryId);
		mav.addObject("regionId", regionId);

		return mav;
	}
	
	
	@RequestMapping(value = "/cartooletcList.do")
	public ModelAndView cartooletcListSub(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status, 
			String categoryId, String regionId) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/admin/cartooletc/cartooletcList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		try {

			Cartooletc cartooletc = new Cartooletc();
			cartooletc.setCategoryId(categoryId);
			cartooletc.setRegionId(regionId);
			cartooletc.setPortalId(portal.getPortalId());

			List<Cartooletc> cartooletcList = cartooletcService.select(cartooletc);

			mav.addObject("cartooletcList", cartooletcList);

		} catch (Exception ex) {
			
			ex.printStackTrace();
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/cartooletcForm.do")
	public ModelAndView cartooletcForm(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status, 
			String cartooletcId, Model model) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/cartooletc/cartooletcForm");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		try {
			Cartooletc cartooletc = null;
			

			Map<String,String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("useFlag", null);
			
			
			List<Cartooletc> categoryList = cartooletcService.categoryList(param);
			List<Cartooletc> regionList = cartooletcService.regionList(param);
			
			
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("regionList", regionList);

			if (cartooletcId != null) {
				cartooletc = cartooletcService.read(cartooletcId);
			} else {
				cartooletc = new Cartooletc();
			
			}
			
			model.addAttribute("cartooletc", cartooletc);

			
			User manager = new User();
			User subManager = new User();
			User lastManager = new User();
			String managerName = "";
			String subManagerName = "";
			String lastManagerName = "";
			
			if(cartooletc != null && !StringUtil.isEmpty(cartooletc.getManagerId())) {
			
				manager = userDao.get(cartooletc.getManagerId());
				
				if(manager != null && !StringUtil.isEmpty(manager.getUserName())) {
					
					managerName = manager.getUserName();
				}
				
				if(manager != null && !StringUtil.isEmpty(manager.getJobTitleName())) {
					
					managerName += " " + manager.getJobTitleName();
				}
				
				if(manager != null && !StringUtil.isEmpty(manager.getTeamName())) {
					
					managerName += " " + manager.getTeamName();
				}
				
			}
			
			if(cartooletc != null && !StringUtil.isEmpty(cartooletc.getSubManagerId())) {
				
				subManager = userDao.get(cartooletc.getSubManagerId());
				
				if(subManager != null && !StringUtil.isEmpty(subManager.getUserName())) {
					
					subManagerName = subManager.getUserName();
				}
				
				if(subManager != null && !StringUtil.isEmpty(subManager.getJobTitleName())) {
					
					subManagerName += " " + subManager.getJobTitleName();
				}
				
				if(subManager != null && !StringUtil.isEmpty(subManager.getTeamName())) {
					
					subManagerName += " " + subManager.getTeamName();
				}
				
			}
			
			if(cartooletc != null && !StringUtil.isEmpty(cartooletc.getLastManagerId())) {
				
				lastManager = userDao.get(cartooletc.getLastManagerId());
				
				if(lastManager != null && !StringUtil.isEmpty(lastManager.getUserName())) {
					
					lastManagerName = lastManager.getUserName();
				}
				
				if(lastManager != null && !StringUtil.isEmpty(lastManager.getJobTitleName())) {
					
					lastManagerName += " " + lastManager.getJobTitleName();
				}
				
				if(lastManager != null && !StringUtil.isEmpty(lastManager.getTeamName())) {
					
					lastManagerName += " " + lastManager.getTeamName();
				}
				
			}
			
			Boolean isSystemAdmin = this.isSystemAdmin(user);
			
			Map<String,String> parameter = new HashMap<String, String>();
			parameter.put("portalId", portal.getPortalId());
			parameter.put("userId", user.getUserId());
			
			Boolean isCarToolManager = this.isCarToolManager(parameter);
			
			model.addAttribute("isSystemAdmin", isSystemAdmin);
			model.addAttribute("isCarToolManager", isCarToolManager);
			model.addAttribute("managerName", managerName);
			model.addAttribute("subManagerName", subManagerName);
			model.addAttribute("lastManagerName", lastManagerName);
		

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/createCartooletc.do")
	public @ResponseBody String createCartooletc(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status,
			@ValidEx Cartooletc cartooletc, BindingResult result) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		try {
			
			if (result.hasErrors()) {
				
				throw new IKEP4AjaxValidationException(result, messageSource);
			}
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			cartooletc.setPortalId(portal.getPortalId());



			if (StringUtil.isEmpty(cartooletc.getCartooletcId())) {
				cartooletcService.create(cartooletc);
			} else {
				cartooletcService.update(cartooletc);
			}

			return "ok";

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
	}
	
	
	@RequestMapping(value = "/deleteCartooletc.do")
	public @ResponseBody
	String deleteCartooletc(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status,
			String[] cartooletcIds) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		try {

			cartooletcService.delete(cartooletcIds);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}
	
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 *
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MeetingRoom", user.getUserId());
	}
	
	public boolean isCarToolManager(Map<String, String> param) {
		
		return this.approveService.isCarToolManager(param);
	}
}