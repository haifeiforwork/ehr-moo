/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprReception;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprReceptionService;
import com.lgcns.ikep4.approval.work.model.ApprDisplay;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.model.GroupType;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.group.service.GroupTypeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 접수담당자 관리 Controller
 * 
 * @author 서지혜
 * @version $Id$
 */

@Controller
@RequestMapping(value = "/approval/admin/reception")
@SessionAttributes("apprReception")
public class ApprReceptionController extends BaseController {

	@Autowired
	private ApprReceptionService apprReceptionService;

	@Autowired
	private ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupTypeService groupTypeService;

	private static final String GROUP_TYPE_ID = "G0001";

	@Autowired
	private ACLService aclService;

	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}

	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}

	/**
	 * 그룹 관리 전체 화면의 시작(상단의 그룹 타입만 가져옴)
	 * 
	 * @param groupTypeId 그룹타입 ID
	 * @param status SessionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateReceptionForm.do")
	public ModelAndView updateReceptionForm(@RequestParam(value = "groupId", required = false) String groupId) {

		ModelAndView mav = new ModelAndView("/approval/admin/reception/updateReceptionForm");
		String groupTypeId = "";
		/*
		 * if (apprReception == null) { apprReception = new ApprReception(); }
		 */
		// 세션의 user id를 가져와서 bean에 셋팅한다.
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Group group = new Group();
		group.setGroupTypeId(groupTypeId);

		List<Map<String, Object>> list = getOrgGroupAndUser(null, StringUtil.nvl(groupTypeId, GROUP_TYPE_ID));
		List<GroupType> groupTypeList = groupTypeService.selectForList();

		Map<String, Object> items = new HashMap<String, Object>();
		items.put("items", list);

		ApprReception apprReception = new ApprReception();
		
		mav.addObject("groupTypeList", groupTypeList);
		mav.addObject("userLocaleCode", user.getLocaleCode());
		mav.addObject("groupTypeId", StringUtil.nvl(groupTypeId, GROUP_TYPE_ID));
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
		mav.addObject("apprReception", apprReception);
		

		return mav;
	}

	/**
	 * 그룹 트리를 구성하기 위해 필요한 아이템을 가져오는 함수
	 * 
	 * @param groupId 조회할 그룹 ID
	 * @param groupTypeId 그룹타입 ID
	 * @return list 그룹 목록
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String groupTypeId) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		// searchgroup.setRegisterId(sessionuser.getUserId());
		searchgroup.setGroupTypeId(groupTypeId);
		searchgroup.setPortalId(portal.getPortalId());

		List<Group> groupList = groupService.selectOrgGroupByGroupTypeId(searchgroup);
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", group.getGroupName());
			map.put("code", group.getGroupId());
			map.put("sortOrder", group.getSortOrder());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			map.put("portalId", portal.getPortalId());
			list.add(map);
		}

		return list;
	}

	/**
	 * 우측의 폼 화면에 들어가는 정보를 가져온다. 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 해당 항목의
	 * Code를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param groupId 상세정보를 요청받은 그룹 ID
	 * @param parentGroupId 선택된 그룹의 부모 그룹 ID
	 * @param model Model 객체
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String getForm(String groupId, String parentGroupId, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		Group group = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("groupTypeId", GROUP_TYPE_ID);

		int rootGroupCount = groupService.getRootGroupCount(map);
		
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", user.getUserId());
		List<Group> groupList = groupService.selectUserGroup(groupMap);
		
		if(!isSystemAdmin(user)) {
			if(groupId == null) {
				groupId = user.getGroupId();
			}
		}
		
		if (groupId != null && !"".equals(groupId)) {
			group = new Group();
			
			group = groupService.read(groupId);
			group.setCheckCodeFlag("modify");
			List<User> userList = group.getUserList();
			String createFlag = "modify";

			model.addAttribute("userList", userList);
			model.addAttribute("createFlag", createFlag);

			List<ApprReception> receptionList = apprReceptionService.listByReception(groupId);
			model.addAttribute("receptionList", receptionList);

		} else if (groupId == null) {
			group = new Group();

			group.setParentGroupId(parentGroupId);
			group.setSortOrder(groupService.getMaxSortOrder());
			group.setGroupTypeId(GROUP_TYPE_ID);
			group.setViewOption("1");

			String createFlag = "new";
			model.addAttribute("createFlag", createFlag);
			
		} else if (groupId.equals("")) {
			group = new Group();
			Group tempGroup = groupService.read(parentGroupId);
			group.setGroupId("tempGroupId");
			group.setParentGroupId(parentGroupId);
			group.setParentGroupName(tempGroup.getGroupName());
			group.setGroupTypeId(tempGroup.getGroupTypeId());
			group.setSortOrder(groupService.getMaxSortOrder());
			group.setViewOption("1");

			String createFlag = "new";
			model.addAttribute("createFlag", createFlag);
		}
		
		ApprReception apprReception = new ApprReception();
		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("group", group);
		model.addAttribute("groupList", groupList);
		model.addAttribute("rootGroupCount", rootGroupCount);
		model.addAttribute("apprReception", apprReception);
		
		return "approval/admin/reception/form";
	}
	
	/**
	 * 우측의 폼 화면에 들어가는 정보를 가져온다. 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 해당 항목의
	 * Code를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param groupId 상세정보를 요청받은 그룹 ID
	 * @param parentGroupId 선택된 그룹의 부모 그룹 ID
	 * @param model Model 객체
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/receptionForm.do", method = RequestMethod.GET)
	public ModelAndView receptionForm(String groupId,Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		Group group = null;
		
		ModelAndView mav = new ModelAndView("/approval/admin/reception/receptionForm");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("groupTypeId", GROUP_TYPE_ID);
		
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", user.getUserId());
		List<Group> groupList = groupService.selectUserGroup(groupMap);
		
		if(!isSystemAdmin(user)) {
			if(groupId == null) {
				groupId = user.getGroupId();
			}
		}

		int rootGroupCount = groupService.getRootGroupCount(map);
		
		if (groupId != null && !"".equals(groupId)) {
			group = new Group();
			
			group = groupService.read(groupId);
			group.setCheckCodeFlag("modify");
			List<User> userList = group.getUserList();
			String createFlag = "modify";

			model.addAttribute("userList", userList);
			model.addAttribute("createFlag", createFlag);

			List<ApprReception> receptionList = apprReceptionService.listByReception(groupId);
			model.addAttribute("receptionList", receptionList);

		}
		
		ApprReception apprReception = new ApprReception();
		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("group", group);
		mav.addObject("groupList", groupList);
		mav.addObject("rootGroupCount", rootGroupCount);
		mav.addObject("apprReception", apprReception);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		return mav;
	}

	/**
	 * 그룹중 선택한 접수 담당자정보 가져오기
	 * 
	 * @param userId 그룹타입 ID
	 * @param status SessionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getGroupUserInfo.do")
	public @ResponseBody
	User getGroupUserInfo(String userId) {

		User userInfo = userService.read(userId);

		return userInfo;
	}

	@RequestMapping(value = "/updateReception.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateReception(SessionStatus status,ApprReception apprReception) {

		String rtn = "";

		try {

			User user = (User) getRequestAttribute("ikep.user");

			apprReception.setRegisterId(user.getUserId());
			apprReception.setRegisterName(user.getUserName());
			apprReception.setPortalId(user.getPortalId());
			apprReceptionService.createApprReceptionSave(apprReception);

			status.setComplete();
			rtn = "OK";

		} catch (Exception ex) {

			throw new IKEP4AjaxException("updateReception", ex);
		}

		return rtn;
	}

}
