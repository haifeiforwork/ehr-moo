/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.group.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.model.GroupType;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.group.service.GroupTypeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 그룹 관리 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupController.java 18280 2012-04-24 22:59:01Z arthes $
 */
@Controller
@RequestMapping(value = "/portal/admin/group/group")
public class GroupController extends BaseController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupTypeService groupTypeService;

	private static final String GROUP_TYPE_ID = "G0001";

	/**
	 * 그룹 관리 전체 화면의 시작(상단의 그룹 타입만 가져옴)
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param groupTypeId 그룹타입 ID
	 * @param status SessionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping("/getList.do")
	public ModelAndView list(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String groupTypeId, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/group/group/list");
		User sessionUser = (User) getRequestAttribute("ikep.user");
		
		Group group = new Group();
		group.setGroupTypeId(groupTypeId);

		List<Map<String, Object>> list = getOrgGroupAndUser(null, StringUtil.nvl(groupTypeId, GROUP_TYPE_ID));
		List<GroupType> groupTypeList = groupTypeService.selectForList();

		Map<String, Object> items = new HashMap<String, Object>();
		items.put("items", list);

		mav.addObject("groupTypeList", groupTypeList);
		mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
		mav.addObject("groupTypeId", StringUtil.nvl(groupTypeId, GROUP_TYPE_ID));

		status.setComplete();

		return mav;
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
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String groupId, String parentGroupId, Model model) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("groupTypeId", GROUP_TYPE_ID);

		int rootGroupCount = groupService.getRootGroupCount(map);
		
		Group group = null;

		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();

		if (groupId != null && !"".equals(groupId)) {

			group = groupService.read(groupId);
			group.setCheckCodeFlag("modify");
			List<User> userList = group.getUserList();
			String createFlag = "modify";

			model.addAttribute("userList", userList);
			model.addAttribute("createFlag", createFlag);

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

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("group", group);
		model.addAttribute("rootGroupCount", rootGroupCount);

		return "portal/admin/group/group/form";
	}

	/**
	 * 그룹을 신규 등록하거나 수정한다.(그룹 생성/수정 후 USER_GROUP 테이블도 업데이트) ID가 중복되는 경우 수정, 중복되지
	 * 않는 경우 생성 프로세스를 진행한다. 생성, 수정이 끝난 후에는 해당 그룹의 ID를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param group 신규/수정 등록하고자 하는 그룹 객체
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return id/groupId 최종 등록한 그룹의 상세 정보를 가져오기 위한 그룹 ID
	 */
	@RequestMapping(value = "/createGroup.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Group group, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// UserList에 등록/수정 관련 정보 추가
		List<User> tempUserList = group.getUserList();
		User sessionuser = (User) getRequestAttribute("ikep.user");

		if (tempUserList != null) {
			for (int i = 0; i < tempUserList.size(); i++) {
				User tempUser = tempUserList.get(i);
				tempUser.setGroupId(group.getGroupId());
				tempUser.setRegisterId(sessionuser.getUserId());
				tempUser.setRegisterName(sessionuser.getUserName());
				tempUser.setUpdaterId(sessionuser.getUserId());
				tempUser.setUpdaterName(sessionuser.getUserName());
			}
		}

		String id = group.getGroupId();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean isCodeExist = groupService.exists(id);

		if (isCodeExist) {
			group.setPortalId(portal.getPortalId());
			group.setGroupTypeId(request.getParameter("groupTypeId"));
			group.setRegisterId(sessionuser.getUserId());
			group.setRegisterName(sessionuser.getUserName());
			group.setUpdaterId(sessionuser.getUserId());
			group.setUpdaterName(sessionuser.getUserName());

			groupService.update(group);

			status.setComplete();

			return id;

		} else {
			if(StringUtil.isEmpty(group.getParentGroupId())) {
				group.setParentGroupId(null);
			}
			
			group.setChildGroupCount("0");
			group.setSortOrder(request.getParameter("sortOrder"));
			group.setPortalId(portal.getPortalId());
			group.setGroupTypeId(request.getParameter("groupTypeId"));
			group.setRegisterId(sessionuser.getUserId());
			group.setRegisterName(sessionuser.getUserName());
			group.setUpdaterId(sessionuser.getUserId());
			group.setUpdaterName(sessionuser.getUserName());
			group.setViewOption(request.getParameter("viewOption"));
			group.setFullPath(request.getParameter("fullPath"));

			String groupId = groupService.create(group);

			status.setComplete();

			return groupId;
		}

	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을
	 * 반환한다. (화면/기능 변경으로 현재 사용하지 않음)
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param id 중복을 체크할 그룹 ID
	 * @return String 중복 유무를 알려주는 문자열 Flag
	 */
	@RequestMapping(value = "/checkId.do")
	public @ResponseBody
	String checkId(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String id) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		boolean result = groupService.exists(id);

		if (result) {

			return "duplicated";
		} else {

			return "success";
		}
	}

	/**
	 * 그룹을 삭제한다.
	 * 
	 * @param groupId 삭제할 그룹 ID
	 * @param accessResult 사용자 권한체크 결과
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteGroup.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("groupId") String groupId,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Group group = groupService.read(groupId);

		groupService.delete(group);

		return "redirect:/portal/admin/group/group/getList.do";
	}

	/**
	 * 드래그 앤 드랍 후 정렬 순서 업데이트를 위한 함수 드랍한 그룹의 바로 다음에 있는 노드부터 정렬 순서에 1을 더하고 드랍된 그룹의
	 * 정렬 순서를 앞에서 저장해 둔 노드의 정렬 순서로 업데이트한다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param currNodeGroupId 현재 노드의 그룹 ID
	 * @param prevNodeGroupId 이전 노드의 그룹 ID
	 * @param parentGroupId 부모 그룹 ID
	 */
	@RequestMapping(value = "/updateSortOrder.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateSortOrder(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("currNodeGroupId") String currNodeGroupId,
			@RequestParam("prevNodeGroupId") String prevNodeGroupId, @RequestParam("parentGroupId") String parentGroupId) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User sessionUser = (User) this.getRequestAttribute("ikep.user");

		// 이동이 완료된 후 바로 위의 노드(그룹)의 정보
		// 이동 완료 후 하위의 sort_order를 업데이트하기 위해 필요
		Group prevGroup = groupService.read(prevNodeGroupId);
		String prevSortOrder = prevGroup.getSortOrder();
		
		// 이동되기전 그룹 정보, 이전 부모의 Child Count를 업데이트하기위해 필요함
		Group orgGroup = groupService.read(currNodeGroupId);
		String orgParentGroupId = orgGroup.getParentGroupId();

		// 이동이 완료된 그룹 자체를 업데이트하기 위한 정보
		String currGroupSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));

		Group currGroup = new Group();
		currGroup.setGroupId(currNodeGroupId);
		currGroup.setParentGroupId(parentGroupId);
		currGroup.setSortOrder(StringUtils.leftPad(currGroupSortOrder, 13, "0"));
		currGroup.setUpdaterId(sessionUser.getUserId());
		currGroup.setUpdaterName(sessionUser.getUserName());

		groupService.updateGroupMove(prevSortOrder,orgParentGroupId, currGroup);
	}

	/**
	 * 컨텍스트메뉴의 위로 이동을 위한 함수. 해당 그룹을 같은 레벨 안에서 한 단계 위로 내린다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param nextNodeGroupId 다음 노드의 그룹 ID
	 * @param curNodeGroupId 현재 노드의 그룹 ID
	 * @return
	 */
	@RequestMapping(value = "/goUp.do")
	public @ResponseBody
	String goUp(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String prevNodeGroupId, String curNodeGroupId) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		String prevSortOrder = groupService.read(prevNodeGroupId).getSortOrder();
		String curSortOrder = groupService.read(curNodeGroupId).getSortOrder();

		Map<String, String> map = new HashMap<String, String>();
		map.put("prevSortOrder", prevSortOrder);
		map.put("prevNodeGroupId", prevNodeGroupId);
		map.put("curSortOrder", curSortOrder);
		map.put("curNodeGroupId", curNodeGroupId);

		groupService.goUp(map);

		return "";
	}

	/**
	 * 컨텍스트메뉴의 아래로 이동을 위한 함수. 해당 그룹을 같은 레벨 안에서 한 단계 아래로 내린다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param nextNodeGroupId 다음 노드의 그룹 ID
	 * @param curNodeGroupId 현재 노드의 그룹 ID
	 * @return
	 */
	@RequestMapping(value = "/goDown.do")
	public @ResponseBody
	String goDown(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String nextNodeGroupId, String curNodeGroupId) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		String nextSortOrder = groupService.read(nextNodeGroupId).getSortOrder();
		String curSortOrder = groupService.read(curNodeGroupId).getSortOrder();

		Map<String, String> map = new HashMap<String, String>();
		map.put("nextSortOrder", nextSortOrder);
		map.put("nextNodeGroupId", nextNodeGroupId);
		map.put("curSortOrder", curSortOrder);
		map.put("curNodeGroupId", curNodeGroupId);

		groupService.goDown(map);

		return "";
	}

	/**
	 * 트리를 구성하는 함수. 첫번째 로딩에서 두번째 레벨까지 가져오고 '+' 아이콘을 누르면 최초 1회에 한해 그 아래 레벨의 목록을
	 * 가져온다. 그 후에는 저장되어 있는 목록을 보여준다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param groupId 조회할 그룹 ID
	 * @param groupTypeId 그룹타입 ID
	 * @param response HttpServletResponse 객체
	 * @return item 그룹 목록 Map
	 */
	@RequestMapping("/requestGroupChildren.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestGroupChildren(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "groupTypeId", required = false) String groupTypeId, HttpServletResponse response) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			if (groupTypeId == null || "".equalsIgnoreCase(groupTypeId)) {
				list = getOrgGroupAndUser(groupId, GROUP_TYPE_ID);
			} else {
				list = getOrgGroupAndUser(groupId, groupTypeId);
			}

			item.put("items", list);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;
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
	 * MS Office Excel을 이용하여 그룹을 대량 입력하기 위한 양식을 내려받는 함수
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param req HttpServletRequest 객체
	 * @return mav
	 */
	@RequestMapping("/excelForm.do")
	public ModelAndView excelForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			HttpServletRequest req) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/group/group/excelForm");

		// 더블 서밋방지 Token 셋팅
		String token = HttpUtil.setToken(req);
		mav.addObject("token", token);

		return mav;
	}

	/**
	 * 대량 입력을 위한 Excel 파일을 업로드하는 함수
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param file 업로드할 Excel 파일
	 * @param req HttpServletRequest 객체
	 * @return mav
	 */
	@RequestMapping("/excelUpload.do")
	public ModelAndView excelUpload(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("file") CommonsMultipartFile file, HttpServletRequest req) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/group/group/excelResult");

		try {

			// 더블 서밋방지 Token 체크
			if (HttpUtil.isValidToken(req)) {

				InputStream inp = file.getInputStream();

				User user = (User) getRequestAttribute("ikep.user");
				Portal portal = (Portal) getRequestAttribute("ikep.portal");

				String className = "com.lgcns.ikep4.support.user.group.model.Group";

				List<Object> list = ExcelUtil.readExcel(className, inp, 1);

				int successCount = 0;
				int failCount = 0;
				List<Group> groupList = new ArrayList<Group>();

				for (Object obj : list) {

					try {

						Group group = (Group) obj;
						
						if(group.getGroupId()==null){
							group.setSuccessYn("N");
							group.setErrMsg("GroupId field is empty : Check file template or row data");
							groupList.add(group);
							failCount++;
							continue;
						}
						if(group.getGroupName()==null){
							group.setSuccessYn("N");
							group.setErrMsg("GroupName field is empty : Check file template or row data");
							groupList.add(group);
							failCount++;
							continue;
						}
						if(group.getGroupEnglishName()==null){
							group.setSuccessYn("N");
							group.setErrMsg("GroupEnglishName field is empty : Check file template or row data");
							groupList.add(group);
							failCount++;
							continue;
						}

						if(group.getParentGroupId()!=null && group.getGroupId().equals(group.getParentGroupId())){
							group.setSuccessYn("N");
							group.setErrMsg("GroupId is equal to ParentGroupId : Cannot be inserted, check row data");
							groupList.add(group);
							failCount++;
							continue;
						}
						
						group.setRegisterId(user.getUserId());
						group.setRegisterName(user.getUserName());
						group.setUpdaterId(user.getUserId());
						group.setUpdaterName(user.getUserName());

						if (groupService.exists(group.getGroupId())) {
							groupService.updateForExcel(group);
						} else {

							group.setPortalId(portal.getPortalId());
							group.setGroupTypeId(GROUP_TYPE_ID);
							group.setViewOption("1");
							group.setChildGroupCount("0");

							groupService.createForExcel(group);
						}

						group.setSuccessYn("Y");
						successCount++;

					} catch (Exception e) {

						Group group = (Group) obj;
						group.setSuccessYn("N");
						group.setErrMsg(e.getMessage());
						groupList.add(group);
						failCount++;
					}
				}

				mav.addObject("groupList", groupList);
				mav.addObject("totalCount", list.size());
				mav.addObject("successCount", successCount);
				mav.addObject("failCount", failCount);

				// Token 초기화
				String token = HttpUtil.setToken(req);
				mav.addObject("token", token);
			}

		} catch (Exception e) {
			mav.addObject("totalCount", 0);
			return mav;
		}

		return mav;
	}

}
