/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.meetingroom.service.EquipmentService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * 기자재 관리용 Controller
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: EquipmentController.java 16243 2011-08-18 04:10:43Z cjpark $
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/equipment")
@SessionAttributes("equipment")
public class EquipmentController extends BaseController {
	
	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private EquipmentService equipmentService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private ApproveService approveService;

	/**
	 * searchCondition에 맞춰 상단의 목록을 가져옴
	 * 
	 * @param searchCondition 목록 검색 조건
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @param status SesstionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getEquipmentList.do")
	public ModelAndView getEquipmentList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) MeetingRoomSearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/equipment/equipmentList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Equipment equipment = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String equipmentId = request.getParameter("tempEquipmentId");

			if (equipmentId != null && !"".equalsIgnoreCase(equipmentId)) {

				equipment = equipmentService.read(equipmentId);
				equipment.setStatus("modify");

				mav.addObject("oldSortOrder", equipment.getSortOrder());
			} else {
				equipment = new Equipment();
				equipment.setSortOrder(equipmentService.getMaxSortOrder());
				equipment.setStatus("success");
			}

			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<Equipment> searchResult = equipmentService.list(searchCondition);
			
			BoardCode boardCode = new BoardCode();
			
			Boolean isSystemAdmin = this.isSystemAdmin(sessionUser);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("userId", sessionUser.getUserId());
			
			Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
			
			mav.addObject("isSystemAdmin", isSystemAdmin);
			mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("equipment", equipment);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	/**
	 * 하단의 폼 화면에 들어가는 정보를 가져온다.
	 * 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 
	 * 해당 항목의 아이디를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param equipmentId 상세정보를 요청받은 기자재 아이디
	 * @param model Model 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/getEquipmentForm.do", method = RequestMethod.GET)
	public String getEquipmentForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) String equipmentId,
			Model model, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}

		Equipment equipment = null;

		if (equipmentId != null && !"".equalsIgnoreCase(equipmentId)) {
			
			equipment = equipmentService.read(equipmentId);
			equipment.setStatus("modify");

			model.addAttribute("oldSortOrder", equipment.getSortOrder());
		} else {
			
			equipment = new Equipment();
			equipment.setSortOrder(equipmentService.getMaxSortOrder());
			equipment.setStatus("success");
		}
		
		model.addAttribute("equipment", equipment);

		return "/lightpack/meetingroom/admin/equipment/equipmentForm";
	}

	/**
	 * 기자재를 신규로 등록하거나 수정한다.
	 * 아이디가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 ItemType의 아이디를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param 기자재 신규/수정 등록하고자 하는 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return 최종 등록한 기자재의 상세 정보를 가져오기 위한 Code
	 */
	@RequestMapping(value = "/createEquipment.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) @ValidEx Equipment equipment,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String equipmentId = equipment.getEquipmentId();
		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean isExist = equipmentService.exists(equipmentId);

		// equipmentId 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isExist) {

			equipment.setPortalId(portal.getPortalId());
			ModelBeanUtil.bindRegisterInfo(equipment,sessionUser.getUserId(), sessionUser.getUserName());

			equipmentService.update(equipment);

		} else {

			equipment.setEquipmentId(this.idgenService.getNextId());
			equipment.setSortOrder(request.getParameter("sortOrder"));
			equipment.setPortalId(portal.getPortalId());
			ModelBeanUtil.bindRegisterInfo(equipment,sessionUser.getUserId(), sessionUser.getUserName());

			equipmentService.create(equipment);
		}

		status.setComplete();

		return equipmentId;

	}

	/**
	 * 해당 기자재를 삭제한다.
	 * 
	 * @param equipmentId 삭제할 기자재 아이디
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteEquipment.do")
	public String deleteEquipment(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) @RequestParam("equipmentId") String equipmentId,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}

		equipmentService.delete(equipmentId);

		return "redirect:/lightpack/meetingroom/equipment/getEquipmentList.do";
	}

	/**
	 * 해당 기자재 목록에서 한 단계 위로 올린다.
	 * 
	 * @param equipmentId 목록에서 한 단계 위로 올릴 기자재의 아이디
	 * @param sortOrder 해당 호칭에 새로 업데이트될 SortOrder
	 * @param accessResult 사용자 권한체크 결과
	 * @return String
	 */
	@RequestMapping(value = "/goUp.do")
	public @ResponseBody
	String goUp(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) String equipmentId,
			String sortOrder, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("equipmentId", equipmentId);
		map.put("sortOrder", sortOrder);

		equipmentService.goUp(map);

		return "";
	}

	/**
	 * 해당 기자재를 목록에서 한 단계 아래로 내린다.
	 * 
	 * @param equipmentId 목록에서 한 단계 아래로 내릴 기자재의 아이디
	 * @param sortOrder 해당 호칭에 새로 업데이트될 SortOrder
	 * @param accessResult 사용자 권한체크 결과
	 * @return String
	 */
	@RequestMapping(value = "/goDown.do")
	public @ResponseBody
	String goDown(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) String equipmentId,
			String sortOrder, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("equipmentId", equipmentId);
		map.put("sortOrder", sortOrder);

		equipmentService.goDown(map);

		return "";
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
	
	public boolean isMeetingRoomManager(Map<String, String> param) {
		
		return this.approveService.isMeetingRoomManager(param);
	}

}