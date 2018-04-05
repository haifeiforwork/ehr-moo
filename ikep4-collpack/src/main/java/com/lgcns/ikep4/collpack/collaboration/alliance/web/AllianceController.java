/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.search.AllianceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.alliance.service.AllianceService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceCode;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.collpack.common.exception.JsonException;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 동맹관리 Controller
 * 
 * @author 김종철
 * @version $Id: AllianceController.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/collaboration/alliance")
@SessionAttributes("collaboration")
public class AllianceController extends BaseController {

	@Autowired
	private AllianceService allianceService;

	@Autowired
	private WorkspaceTypeService workspaceTypeService;

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * 동맹관리 목록(전체/요청받은/요청한동맹목록)
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listAllianceView.do")
	public ModelAndView listAllianceView(AllianceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Workspace workspace = new Workspace();

		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());
		workspace = workspaceService.read(workspace);

		searchCondition.setPortalId(portal.getPortalId());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("collpack/collaboration/alliance/listAllianceView");

		SearchResult<Alliance> searchResult = this.allianceService.listBySearchCondition(searchCondition);

		WorkspaceCode workspaceCode = new WorkspaceCode();

		modelAndView.addObject("workspace", workspace);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;
	}

	/**
	 * 동맹 정보 조회 (동맹 정보 입력시 필요한 사항만)
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readAllianceView.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Alliance readAllianceView(@RequestParam(value = "workspaceId", required = false) String workspaceId,
			@RequestParam(value = "toWorkspaceId", required = false) String toWorkspaceId,
			@RequestParam(value = "isOrganization", required = false) String isOrganization) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		Alliance alliance = new Alliance();
		try {

			alliance.setPortalId(portal.getPortalId());

			if (isOrganization != null && isOrganization.equals("0")) {
				alliance.setWorkspaceId(workspaceId);
				alliance.setToWorkspaceId(toWorkspaceId);
				alliance = allianceService.read(alliance);

			} else if (isOrganization.equals("1")) {
				alliance.setWorkspaceId(workspaceId);
				// alliance.setToWorkspaceId(toWorkspaceId);
				alliance.setTeamId(toWorkspaceId); // Org 그룹 용
				alliance = allianceService.getOrgAlliance(alliance);

			}

			if (portal.getDefaultLocaleCode() != null && !portal.getDefaultLocaleCode().equals(user.getLocaleCode())) {
				if (alliance.getGroupEnglishName() != null) {
					alliance.setGroupName(alliance.getGroupEnglishName());
				}
				if (alliance.getSysopEnglishName() != null) {
					alliance.setSysopName(alliance.getSysopEnglishName());
				}
			}

			if (alliance != null) {
				alliance.setStrDate(DateUtil.getFmtDateString(alliance.getOpenDate(), "yyyy-MM-dd"));
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return alliance;
	}

	/**
	 * 동맹 등록폼
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createAllianceView.do")
	public String createAllianceView(AllianceSearchCondition searchCondition,
			@RequestParam("parentListType") String parentListType, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		Alliance alliance = new Alliance();
		alliance.setWorkspaceId(searchCondition.getWorkspaceId());
		alliance.setPortalId(portal.getPortalId());

		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		// 트리정보
		String rootString = "";
		List<Map<String, Object>> root = getOrgGroup(null, searchCondition.getListType());
		rootString = getJSONString(root);

		model.addAttribute("rootString", rootString);
		model.addAttribute("workspaceTypeList", workspaceTypeList);
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("alliance", alliance);
		model.addAttribute("parentListType", parentListType);
		return "collpack/collaboration/alliance/createAllianceView";
	}

	/**
	 * 동맹 신청시 조직도 표시
	 * 
	 * @param groupId
	 * @param listType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroup(String groupId, String listType) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, String> groupMap = new HashMap<String, String>();
		groupMap.put("portalId", portal.getPortalId());
		groupMap.put("groupId", groupId);

		List<Workspace> groupNode = workspaceService.getOrgGroup(groupMap);

		for (Workspace workspace : groupNode) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (workspace.getChildGroupCount() > 0) {
				if (portal.getDefaultLocaleCode() != null && portal.getDefaultLocaleCode().equals(user.getLocaleCode())) {
					map.put("name", workspace.getGroupName() + "(" + workspace.getCountChildWorkspace() + "/"
							+ workspace.getChildGroupCount() + ")");
				} else {
					map.put("name", workspace.getGroupEnglishName() + "(" + workspace.getCountChildWorkspace() + "/"
							+ workspace.getChildGroupCount() + ")");
				}
			} else {
				if (portal.getDefaultLocaleCode() != null && portal.getDefaultLocaleCode().equals(user.getLocaleCode())) {
					map.put("name", workspace.getGroupName());
				} else {
					map.put("name", workspace.getGroupEnglishName());
				}
			}
			map.put("code", workspace.getGroupId());
			map.put("groupTypeId", workspace.getGroupTypeId());
			map.put("parent", workspace.getParentGroupId());
			map.put("hasChild", workspace.getChildGroupCount());
			map.put("hasWorkspace", workspace.getCountWorkspace());
			// 개설시 Tree Icon
			if (workspace.getCountWorkspace() > 0) {
				map.put("icon", "teamopenon");
			} else {
				map.put("icon", "teamopenoff"); // 미개설시 Tree Icon
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 데이터 JSON 처리
	 * 
	 * @param obj
	 * @return
	 */
	public String getJSONString(Object obj) {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = objectMapper.writeValueAsString(obj);
		} catch (Exception ex) {
			throw new JsonException();
		}

		return jsonString;
	}

	/**
	 * 동맹 등록 처리
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createAlliance.do", method = RequestMethod.POST)
	public @ResponseBody
	String createAlliance(@Valid Alliance alliance, BindingResult result) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		alliance.setPortalId(portal.getPortalId());

		alliance.setStatus("0"); // 동맹요청
		alliance.setRegisterId(user.getUserId());
		alliance.setRegisterName(user.getUserName());
		alliance.setUpdaterId(user.getUserId());
		alliance.setUpdaterName(user.getUserName());
		String allianceId = allianceService.create(alliance, user);

		// status.setComplete();

		return allianceId;
	}

	/**
	 * 동맹 상태 변경
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateAllianceStatus.do")
	public String updateAllianceStatus(AllianceSearchCondition searchCondition, Alliance alliance,
			@RequestParam("allianceIds") List<String> allianceIds, SessionStatus status) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");

		allianceService.updateStatus(searchCondition, allianceIds, user);

		status.setComplete();
		return "redirect:/collpack/collaboration/alliance/listAllianceView.do?listType=" + searchCondition.getListType()
				+ "&workspaceId=" + searchCondition.getWorkspaceId();
	}

	/**
	 * 동맹 요청취소
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteAlliance.do")
	public String deleteAlliance(AllianceSearchCondition searchCondition,
			@RequestParam("allianceIds") List<String> allianceIds, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		allianceService.physicalDelete(allianceIds, user);

		status.setComplete();
		return "redirect:/collpack/collaboration/alliance/listAllianceView.do?listType=" + searchCondition.getListType()
				+ "&workspaceId=" + searchCondition.getWorkspaceId();
	}

	/**
	 * 동맹 소개
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/helpAllianceView.do")
	public String helpAllianceView(Model model) {

		return "collpack/collaboration/alliance/helpAllianceView";
	}
}
