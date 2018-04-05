/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

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

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace.CreateWorkspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace.UpdateWorkspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceCode;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.collpack.common.exception.JsonException;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 워크스페이스 Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceController.java 11340 2011-05-16 12:39:41Z happyi1018
 *          $
 */

@Controller
@RequestMapping(value = "/collpack/collaboration/workspace")
@SessionAttributes("workspace")
public class WorkspaceController extends BaseController {

	@Autowired
	private WorkspaceTypeService workspaceTypeService;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private TagService tagService;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private CacheService cacheService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	private static final String COLLABORATION_MANAGER = "Collaboration";

	/**
	 * Workspace 전체 관리자 여부 조회
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String userId) {
		return aclService.isSystemAdmin(COLLABORATION_MANAGER, userId);
	}

	/**
	 * Collaboration 서브메인 > Administrator >개설 /폐쇄 관리 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceWaitingView.do")
	public ModelAndView listWorkspaceWaitingView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());
		WorkspaceCode workspaceCode = new WorkspaceCode();

		// 개설 대기
		if (searchCondition.getListType() != null && searchCondition.getListType().equals("WaitingOpen")) {
			searchCondition.setWorkspaceStatus(WorkspaceConstants.WORKSPACE_STATUS_WAITING_OPEN);
		} else { // 폐쇄대기
			searchCondition.setWorkspaceStatus(WorkspaceConstants.WORKSPACE_STATUS_WAITING_CLOSE);
		}
		SearchResult<Workspace> searchResult = this.workspaceService.listBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("collpack/collaboration/workspace/listWorkspaceWaitingView");
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;

	}

	/**
	 * Collaboration 서브메인 > Workspace Type >workspace Type 별 workspace 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceTypeView.do")
	public ModelAndView listWorkspaceTypeView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		// 워크스페이스 타입정보
		WorkspaceType workspaceType = new WorkspaceType();
		workspaceType.setPortalId(portal.getPortalId());
		workspaceType.setTypeId(searchCondition.getTypeId());
		workspaceType = workspaceTypeService.read(workspaceType);

		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setTypeId(searchCondition.getTypeId());

		/**
		 * 타입 카테고리/조직별 워크스페이스 수
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("typeId", searchCondition.getTypeId());

		ModelAndView modelAndView = new ModelAndView();
		WorkspaceCode workspaceCode = new WorkspaceCode();
		SearchResult<Workspace> searchResult = null;

		// 카테고리별 WS 수

		if (workspaceType == null) {

			List<WorkspaceType> countWorkspaceTypeList = workspaceTypeService
					.listWorkspaceTypeAll(portal.getPortalId());
			modelAndView.addObject("countWorkspaceTypeList", countWorkspaceTypeList);

		} else {
			if (workspaceType.getIsOrganization() == 0) {
				List<Workspace> countWorkspaceTypeList = workspaceService.countWorkspaceType(map);
				modelAndView.addObject("countWorkspaceTypeList", countWorkspaceTypeList);

			} else { // 조직별 워크스페이스 수
				if (searchCondition.getGroupId() != null && !searchCondition.getGroupId().equals("")) {
					map.put("groupId", searchCondition.getGroupId());
					Group group = groupDao.get(searchCondition.getGroupId());
					modelAndView.addObject("group", group);
				}
				List<Workspace> countGroupList = workspaceService.countWorkspaceTypeOrg(map);
				modelAndView.addObject("countGroupList", countGroupList);

			}
		}

		// Type별 Workspace 조회
		searchResult = this.workspaceService.listBySearchCondition(searchCondition);
		// Type 이름 조회

		String typeName = "";
		if (workspaceType == null) {
			typeName = "Total";
		} else {
			typeName = workspaceTypeService.getTypeName(workspaceType);
		}

		modelAndView.setViewName("collpack/collaboration/workspace/listWorkspaceTypeView");
		modelAndView.addObject("workspaceType", workspaceType);
		modelAndView.addObject("workspaceCode", workspaceCode);
		modelAndView.addObject("typeName", typeName);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());

		return modelAndView;
	}

	/**
	 * Collaboration 서브메인 > Personal > 나의 Workspace 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listMyCollaborationView.do")
	public ModelAndView listMyCollaborationView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());

		ModelAndView modelAndView = new ModelAndView();

		WorkspaceCode workspaceCode = new WorkspaceCode();

		modelAndView.setViewName("collpack/collaboration/workspace/listMyCollaborationView");
		searchCondition.setMemberId(user.getUserId());
		// Type 전체 조회
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		// 나의 Workspace 목록
		SearchResult<Workspace> searchResult = this.workspaceService.listBySearchConditionPersonal(searchCondition);

		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;
	}

	/**
	 * 외부 Unit > 나의 Workspace 목록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/myWorkspace.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Workspace> myWorkspace(@RequestParam("userId") String userId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		List<Workspace> workspaceList = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portal.getPortalId());
			map.put("memberId", userId);
			// 나의 Workspace 목록 Ajax 리턴
			workspaceList = workspaceService.listMyCollaboration(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return workspaceList;
	}
	
	@RequestMapping(value = "/myWorkspaceMenu.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Workspace> myWorkspaceMenu(@RequestParam("userId") String userId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		List<Workspace> workspaceList = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portal.getPortalId());
			map.put("memberId", userId);
			// 나의 Workspace 목록 Ajax 리턴
			workspaceList = workspaceService.listMyScheduleCollaboration(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return workspaceList;
	}

	/**
	 * Collaboration 서브메인 > Administrator > 폐쇄된 Coll. 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listCloseWorkspaceView.do")
	public ModelAndView listCloseWorkspaceView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setListUrl("listCloseWorkspaceView.do");

		ModelAndView modelAndView = new ModelAndView();
		WorkspaceCode workspaceCode = new WorkspaceCode();

		modelAndView.setViewName("collpack/collaboration/workspace/listCloseWorkspaceView");
		searchCondition.setWorkspaceStatus(WorkspaceConstants.WORKSPACE_STATUS_CLOSE);
		// Type 전체 조회
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		// 폐쇄된 Workspace 목록 조회
		SearchResult<Workspace> searchResult = this.workspaceService.listBySearchCondition(searchCondition);

		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;
	}

	/**
	 * Collaboration 서브메인 > Personal > 나의 신청 내역 > 회원 가입 WS 목록 or 내가 신청한
	 * workspace 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listMyApplicationView.do")
	public ModelAndView listMyApplicationView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());

		ModelAndView modelAndView = new ModelAndView();
		WorkspaceCode workspaceCode = new WorkspaceCode();

		modelAndView.setViewName("collpack/collaboration/workspace/listMyApplicationView");
		// Type 전체 조회
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);

		SearchResult<Workspace> searchResult = null;
		// 내가 가입한 Workspace 목록 ( 가입대기 ~ 시샵 )
		if (searchCondition.getListType() != null && searchCondition.getListType().equals("listAppMember")) {
			searchCondition.setMemberId(user.getUserId());
			searchResult = this.workspaceService.listBySearchConditionPersonal(searchCondition);
		} else { // 내가 신청한 Workspace 목록
			searchCondition.setRegisterId(user.getUserId());
			searchResult = this.workspaceService.listBySearchCondition(searchCondition);
		}

		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;
	}

	/**
	 * Collaboration 메인 OR Collaboration 서브메인 > workspace 개설신청
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createWorkspaceView.do", method = RequestMethod.GET)
	public String createWorkspaceView(WorkspaceSearchCondition searchCondition, Model model) {

		Workspace workspace = new Workspace();

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		// Type 목록 조회
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceType(portal.getPortalId());
		//System.out.println("###############################"+searchCondition.getTypeId());
		
		searchCondition.setListUrl("listMyApplicationView.do");

		model.addAttribute("toDay", timeZoneSupportService.convertTimeZone());
		model.addAttribute("workspace", workspace);
		model.addAttribute("workspaceTypeList", workspaceTypeList);
		model.addAttribute("searchCondition", searchCondition);

		return "collpack/collaboration/workspace/createWorkspaceView";
	}

	/**
	 * Collaboration 메인 OR Collaboration 서브메인 > workspace 개설신청 > workspaceName
	 * 중복체크
	 * 
	 * @param workspaceName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkWorkspaceName.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean checkWorkspaceName(@RequestParam("workspaceName") String workspaceName, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean checkName = false;

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portal.getPortalId());
			map.put("workspaceName", workspaceName);
			// workspace 이름 중복 체크
			checkName = workspaceService.checkWorkspaceName(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return checkName;

	}

	/**
	 * 개별 Workspace > 팀 workspace 정보수정의 workspace Team 중복체크
	 * 
	 * @param workspaceName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkWorkspaceTeam.do")
	public @ResponseBody
	String checkWorkspaceTeam(@RequestParam("teamId") String teamId, Model model) {

		String checkName = null;
		try {
			// 조직인 경우 WS 정보수정시 팀변경을 위한 팀 개설여부 확인 (개설대기,개설,폐쇄대기는 팀변경이 불가)
			checkName = workspaceService.checkWorkspaceTeam(teamId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return checkName;

	}

	/**
	 * workspace 등록내용 저장
	 * 
	 * @param workspace
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createWorkspace.do", method = RequestMethod.POST)
	public @ResponseBody
	String createWorkspace(Model model, @ValidEx(groups = { CreateWorkspace.class }) Workspace workspace,
			WorkspaceSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		// Workspace 생성 ( 회원, 권한 그룹, 권한그룹 사용자 )
		String workspaceId = workspaceService.createWorkspace(portal.getPortalId(), user, workspace);
		status.setComplete();
		return workspaceId;
	}

	/**
	 * workspace 내용조회
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readWorkspaceView.do")
	public String readWorkspaceView(WorkspaceSearchCondition searchCondition, Model model) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());

		if (workspace.getWorkspaceId() != null) {
			workspace = workspaceService.read(workspace);
			model.addAttribute("workspace", workspace);
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspace.getWorkspaceId());
		map.put("memberType", "member");
		// 회원 정보 조회
		workspace.setMemberList(memberService.listWorkspaceMember(map));

		// 태그 정보 조회
		List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(), WorkspaceConstants.ITEM_TYPE_NAME);
		workspace.setTagList(tagList);
		// 파일 정보 조회(기본이미지)
		List<FileData> fileDataList = fileService.getItemFile(workspace.getWorkspaceId(), "N");
		workspace.setFileDataList(fileDataList);

		Boolean isSystemAdmin = isSystemAdmin(user.getUserId());
		model.addAttribute("isSystemAdmin", isSystemAdmin);
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("workspace", workspace);

		return "collpack/collaboration/workspace/readWorkspaceView";
	}

	/**
	 * 타입별 카테고리별 Tree 제공 (동맹 요청 등록시 호출)
	 * 
	 * @param typeId
	 * @param categoryId
	 * @param isOrganization
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceBySearch.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> listWorkspaceBySearch(@RequestParam("keyword") String keyword, HttpServletResponse response) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		searchCondition.setPortalId(portal.getPortalId());

		/** 트리정보 **/
		Map<String, Object> item = new HashMap<String, Object>();
		List<Map<String, Object>> workspaceList = null;

		workspaceList = getWorkspaceBySearch(portal.getPortalId(), keyword, user.getLocaleCode());

		item.put("items", workspaceList);

		return item;
	}

	/**
	 * 카테고리별 Workspace 목록
	 * 
	 * @param portalId
	 * @param typeId
	 * @param categoryId
	 * @return
	 */
	private List<Map<String, Object>> getWorkspaceBySearch(String portalId, String keyword, String localeCode) {

		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("portalId", portalId);
		searchMap.put("workspaceName", keyword);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<Workspace> workspaceList = workspaceService.listWorkspaceBySearch(searchMap);

		for (Workspace workspace : workspaceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("isOrganization", workspace.getIsOrganization());
			if (localeCode.toUpperCase().equals("KO"))
				map.put("name", "[" + workspace.getTypeName() + "] " + workspace.getWorkspaceName());// +"("+workspace.getCountWorkspace()+")");
			else
				map.put("name", "[" + workspace.getTypeEnglishName() + "] " + workspace.getWorkspaceName());// +"("+workspace.getCountWorkspace()+")");

			if (workspace.getIsOrganization() == 0) {
				map.put("code", workspace.getWorkspaceId());
			} else {
				map.put("code", workspace.getTeamId());
			}
			map.put("groupTypeId", workspace.getTypeId());
			map.put("parent", workspace.getCategoryId());
			map.put("hasChild", 0);
			map.put("hasWorkspace", "1");
			/** 동맹요청 등록/조회 화면 내용 */
			map.put("workspaceId", workspace.getWorkspaceId());
			map.put("workspaceName", workspace.getWorkspaceName());
			map.put("sysopName", workspace.getSysopName());
			map.put("openDate", workspace.getOpenDate());
			map.put("groupName", workspace.getGroupName());

			list.add(map);
		}

		return list;
	}

	/**
	 * 타입별 카테고리별 Tree 제공 (동맹 요청 등록시 호출)
	 * 
	 * @param typeId
	 * @param categoryId
	 * @param isOrganization
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceByType.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> listWorkspaceByType(@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "isOrganization", required = false) String isOrganization,
			HttpServletResponse response) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setTypeId(typeId);

		/** 트리정보 **/
		Map<String, Object> item = new HashMap<String, Object>();
		List<Map<String, Object>> workspaceList = null;

		if (isOrganization.equals("0")) {
			if (categoryId == null || categoryId.equals("")) {
				/** 카테고리별 초기로딩 트리 구성 **/
				workspaceList = getCategoryByType(portal.getPortalId(), typeId, user);
			} else {
				/** 카테고리별 하위 WS 목록 **/
				workspaceList = getWorkspaceByCategory(portal.getPortalId(), typeId, categoryId);
			}
		} else {
			if (categoryId == null || categoryId.equals("")) {
				// Team 타입의 초기 로딩 트리 구성
				workspaceList = getOrgGroup(null, searchCondition.getListType());
			} else {
				// Team 하위 WS 목록
				workspaceList = getOrgGroup(categoryId, searchCondition.getListType());
			}
		}

		item.put("items", workspaceList);

		return item;
	}

	/**
	 * 타입별 카테고리 목록
	 * 
	 * @param portalId
	 * @param typeId
	 * @return
	 */
	private List<Map<String, Object>> getCategoryByType(String portalId, String typeId, User user) {

		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		searchCondition.setPortalId(portalId);
		searchCondition.setTypeId(typeId);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("portalId", portalId);
		mapCategory.put("typeId", typeId);
		// 카테고리별 WS 갯수 조회
		List<Workspace> countWorkspaceTypeList = workspaceService.countWorkspaceType(mapCategory);

		for (Workspace workspace : countWorkspaceTypeList) {

			// 타입별 워크스페이스 갯수에서 ALL 부분을 제외
			if (workspace.getCategoryName() != null && workspace.getCategoryName().equals("ALL")) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (workspace.getCategoryId().equals("0")) {
				if (user.getLocaleCode().toUpperCase().equals("KO")) {
					map.put("name", "카테고리 없음(" + workspace.getCountWorkspace() + ")");
				} else {
					map.put("name", "No Category(" + workspace.getCountWorkspace() + ")");
				}
			} else {
				map.put("name", workspace.getCategoryName() + "(" + workspace.getCountWorkspace() + ")");
			}
			map.put("code", workspace.getCategoryId());
			map.put("groupTypeId", workspace.getTypeId());
			map.put("parent", workspace.getCategoryId());
			map.put("hasChild", workspace.getCountWorkspace());
			map.put("hasWorkspace", "");
			list.add(map);
		}

		return list;
	}

	/**
	 * 카테고리별 Workspace 목록
	 * 
	 * @param portalId
	 * @param typeId
	 * @param categoryId
	 * @return
	 */
	private List<Map<String, Object>> getWorkspaceByCategory(String portalId, String typeId, String categoryId) {

		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("portalId", portalId);
		mapCategory.put("typeId", typeId);
		mapCategory.put("categoryId", categoryId);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<Workspace> workspaceList = workspaceService.listWorkspaceByCategory(mapCategory);

		for (Workspace workspace : workspaceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", workspace.getWorkspaceName());// +"("+workspace.getCountWorkspace()+")");
			map.put("code", workspace.getWorkspaceId());
			map.put("groupTypeId", workspace.getTypeId());
			map.put("parent", workspace.getCategoryId());
			map.put("hasChild", workspace.getCountWorkspace());
			map.put("hasWorkspace", "1");
			/** 동맹요청 등록/조회 화면 내용 */
			map.put("workspaceId", workspace.getWorkspaceId());
			map.put("workspaceName", workspace.getWorkspaceName());
			map.put("sysopName", workspace.getSysopName());
			map.put("openDate", workspace.getOpenDate());
			map.put("groupName", workspace.getGroupName());

			list.add(map);
		}

		return list;
	}

	/**
	 * 동맹 정보 조회
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readWorkspaceAllianceView.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Workspace readWorkspaceAllianceView(@RequestParam(value = "workspaceId", required = false) String workspaceId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Workspace workspace = new Workspace();

		try {
			workspace.setPortalId(portal.getPortalId());
			workspace.setWorkspaceId(workspaceId);

			workspace = workspaceService.read(workspace);

			if (workspace != null) {
				workspace.setStrDate(DateUtil.getFmtDateString(workspace.getOpenDate(), "yyyy-MM-dd"));
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return workspace;
	}

	/**
	 * workspace 수정폼
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceView.do")
	public String updateWorkspaceView(WorkspaceSearchCondition searchCondition, Model model) {
		/**
		 * 워크스페이스 수정시 멤버회원 수정만 시샵은 남김
		 */
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());

		if (workspace.getWorkspaceId() != null) {
			workspace = workspaceService.read(workspace);
		}

		workspace.setWorkspaceTypeList(workspaceTypeService.listWorkspaceType(portal.getPortalId()));
		searchCondition.setIsOrganization(workspace.getIsOrganization());

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspace.getWorkspaceId());
		map.put("memberType", "member");

		workspace.setMemberList(memberService.listWorkspaceMember(map));

		List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(), WorkspaceConstants.ITEM_TYPE_NAME);
		workspace.setTagList(tagList);

		List<FileData> fileDataList = fileService.getItemFile(workspace.getWorkspaceId(), "N");
		workspace.setFileDataList(fileDataList);

		model.addAttribute("workspace", workspace);
		model.addAttribute("searchCondition", searchCondition);

		return "collpack/collaboration/workspace/updateWorkspaceView";
	}

	/**
	 * workspace 수정내용 저장
	 * 
	 * @param workspace
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspace.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateWorkspace(@ValidEx(groups = { UpdateWorkspace.class }) Workspace workspace,
			WorkspaceSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource);
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
		}

		User user = (User) getRequestAttribute("ikep.user");

		workspaceService.updateWorkspace(workspace, user);

		status.setComplete();
		return workspace.getWorkspaceId();
	}

	/**
	 * 개별 Workspace > Administration > workspace 정보수정폼
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceInfoView.do")
	public String updateWorkspaceInfoView(WorkspaceSearchCondition searchCondition, Model model) {
		/**
		 * 워크스페이스 수정시 멤버회원 수정만 시샵은 남김
		 */
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());

		if (workspace.getWorkspaceId() != null) {
			workspace = workspaceService.read(workspace);
		}

		// 일반 Workspace
		if (workspace.getIsOrganization() != 1) {
			workspace.setWorkspaceTypeList(workspaceTypeService.listWorkspaceType(portal.getPortalId()));
		}
		model.addAttribute("workspace", workspace);
		searchCondition.setIsOrganization(workspace.getIsOrganization());
		model.addAttribute("searchCondition", searchCondition);

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspace.getWorkspaceId());
		map.put("memberType", "member");

		List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(), WorkspaceConstants.ITEM_TYPE_NAME);
		workspace.setTagList(tagList);

		List<FileData> fileDataList = fileService.getItemFile(workspace.getWorkspaceId(), "N");
		workspace.setFileDataList(fileDataList);

		workspace.setMemberList(memberService.listWorkspaceMember(map));
		model.addAttribute("workspace", workspace);

		return "collpack/collaboration/workspace/updateWorkspaceInfoView";
	}

	/**
	 * 개별 Workspace > Administration >workspace 정보수정(멤버 제외)
	 * 
	 * @param workspace
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceInfo.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateWorkspaceInfo(@ValidEx(groups = { UpdateWorkspace.class }) Workspace workspace,
			WorkspaceSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource);
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
		}

		User user = (User) getRequestAttribute("ikep.user");

		workspace.setPortalId(user.getPortalId());

		workspaceService.updateWorkspaceInfo(workspace, user);

		status.setComplete();
		return workspace.getWorkspaceId();
	}

	/**
	 * 개별 Workspace > Administration >workspace 소개화면 편집
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceIntroView.do")
	public String updateWorkspaceIntroView(WorkspaceSearchCondition searchCondition, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());

		if (workspace.getWorkspaceId() != null) {
			workspace = workspaceService.read(workspace);
		}

		model.addAttribute("workspace", workspace);
		model.addAttribute("searchCondition", searchCondition);

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspace.getWorkspaceId());
		map.put("memberType", "member");

		model.addAttribute("workspace", workspace);
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		model.addAttribute("useActiveX", useActiveX);

		return "collpack/collaboration/workspace/updateWorkspaceIntroView";
	}

	/**
	 * 개별 Workspace > Administration >workspace 소개화면 수정(멤버 제외)
	 * 
	 * @param workspace
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceIntro.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateWorkspaceIntro(Workspace workspace, WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource);
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
		}
		if (result.hasErrors()) {
			return "forward:/collpack/collaboration/workspace/updateWorkspaceIntroView.do";
		}
		User user = (User) getRequestAttribute("ikep.user");

		workspace.setPortalId(user.getPortalId());
		workspaceService.updateWorkspaceIntro(workspace, user);

		status.setComplete();

		return workspace.getWorkspaceId();

	}

	/**
	 * Collaboration 서브메뉴 > Administrator > 팀 Coll. 개설/폐쇄 관리 > 팀 Coll. 개설 관리 메인
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listWaitingOpenOrgView.do")
	public ModelAndView listWaitingOpenOrgView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView modelAndView = new ModelAndView();

		WorkspaceCode workspaceCode = new WorkspaceCode();

		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setListType("WaitingOpenOrg");

		modelAndView.setViewName("collpack/collaboration/workspace/listWaitingOpenOrgView");
		// 트리정보
		String rootString = "";
		List<Map<String, Object>> root = getOrgGroup("", searchCondition.getListType());
		rootString = getJSONString(root);

		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("workspaceCode", workspaceCode);

		modelAndView.addObject("rootString", rootString);
		return modelAndView;
	}

	/**
	 * Collaboration 서브메뉴 > Administrator > 팀 Coll. 개설/폐쇄 관리 > 팀 Coll. 개설 관리>
	 * 미개설/하위 팀 리스트
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/resultWaitingOpenOrgList.do")
	public ModelAndView resultWaitingOpenOrgList(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setPortalId(user.getPortalId());

		ModelAndView modelAndView = new ModelAndView();
		WorkspaceCode workspaceCode = new WorkspaceCode();
		modelAndView.setViewName("collpack/collaboration/workspace/resultWaitingOpenOrgListView");
		searchCondition.setPagePerRecord(searchCondition.getPagePerRecord());

		try {

			SearchResult<Workspace> searchResult = null;

			if (StringUtil.isEmpty(searchCondition.getGroupId())) {
				searchResult = this.workspaceService.listBySearchConditionGroup(searchCondition);
			} else {
				searchResult = this.workspaceService.listBySearchConditionGroupHierarchy(searchCondition);
			}
			modelAndView.addObject("searchResult", searchResult);
			modelAndView.addObject("workspaceList", searchResult.getEntity());
			modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
			modelAndView.addObject("workspaceCode", workspaceCode);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return modelAndView;
	}

	/**
	 * Collaboration 서브메뉴 > Administrator > 팀 Coll. 개설/폐쇄 관리 > 팀 Coll. 폐쇄관리 리스트
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listWaitingCloseOrgView.do")
	public ModelAndView listWaitingCloseOrgView(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setPortalId(user.getPortalId());

		ModelAndView modelAndView = new ModelAndView();
		WorkspaceCode workspaceCode = new WorkspaceCode();

		try {
			modelAndView.setViewName("collpack/collaboration/workspace/listWaitingCloseOrgView");

			SearchResult<Workspace> searchResult = this.workspaceService
					.listBySearchConditionCloseGroup(searchCondition);

			modelAndView.addObject("searchResult", searchResult);
			modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
			modelAndView.addObject("workspaceCode", workspaceCode);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return modelAndView;

	}

	/**
	 * Collaboration 서브메뉴 > Administrator > 팀 Coll. 개설/폐쇄 관리 > Workspace 미개설 조직
	 * 생성처리
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createOrgWorkspaceOpen.do")
	public String createWorkspaceOrg(@RequestParam("groupIds") List<String> groupIds, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		workspaceService.createOrgWorkspace(user, groupIds);

		status.setComplete();
		return "redirect:/collpack/collaboration/workspace/listWaitingOpenOrgView.do";

	}

	/**
	 * Collaboration 서브메뉴 > Administrator > 팀 Coll. 개설/폐쇄 관리 > 하위 팀 Tree 목록
	 * 
	 * @param searchCondition
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/requestGroupChildren.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestGroupChildren(@RequestParam(value = "groupId", required = false) String groupId,
			String listType, Model model) {
		/** 트리정보 **/
		Map<String, Object> item = new HashMap<String, Object>();

		List<Map<String, Object>> child = getOrgGroup(groupId, listType);
		// String childString = "";
		// childString = getJSONString(child); 한글깨짐 현상
		item.put("items", child);
		return item;
	}

	/**
	 * 해당 그룹의 하위 그룹 조회
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
			if (workspace.getCountWorkspace() > 0) { // 개설시 Tree Icon
				map.put("icon", "teamopenon");
			} else {
				map.put("icon", "teamopenoff");
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * JSON 자료 생성
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
	 * Collaboration 서브메뉴 > Administrator > workspace 상태변경내용 저장(개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param workspaceIds
	 * @param workspaceStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceStatus.do", method = RequestMethod.POST)
	public String updateWorkspaceStatus(@RequestParam("workspaceIds") List<String> workspaceIds,
			@RequestParam("workspaceStatus") String workspaceStatus, WorkspaceSearchCondition searchCondition,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		workspaceService.updateWorkspaceStatus(workspaceIds, workspaceStatus, user);

		status.setComplete();
		return "redirect:/collpack/collaboration/workspace/listWorkspaceWaitingView.do?listType="
				+ searchCondition.getListType() + "&listUrl=" + searchCondition.getListUrl();
	}

	/**
	 * Collaboration 서브메뉴 > Administrator > workspace 상태변경내용 저장(개설반려)
	 * 
	 * @param workspaceIds
	 * @param workspaceStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceCloseRejectStatus.do", method = RequestMethod.POST)
	public String updateWorkspaceCloseRejectStatus(@RequestParam("workspaceIds") List<String> workspaceIds,
			@RequestParam("workspaceStatus") String workspaceStatus, WorkspaceSearchCondition searchCondition,
			SessionStatus status) {
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		workspaceService.updateWorkspaceStatus(workspaceIds, "Reject", user);

		status.setComplete();
		return "redirect:/collpack/collaboration/workspace/listWorkspaceWaitingView.do?listType="
				+ searchCondition.getListType() + "&listUrl=" + searchCondition.getListUrl();
	}

	/**
	 * Collaboration 서브메뉴 > Administrator > workspace 상태변경내용 저장(개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param workspaceIds
	 * @param workspaceStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceStatusAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateWorkspaceStatusAjax(@RequestParam("workspaceIds") List<String> workspaceIds,
			@RequestParam("workspaceStatus") String workspaceStatus, WorkspaceSearchCondition searchCondition,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		try {
			workspaceService.updateWorkspaceStatus(portal, user, workspaceIds, workspaceStatus);
			
			// workspace 폐쇄승인시
			if("C".equals(workspaceStatus)) {
				//My Collaboration  포틀릿 contents 캐시 Element 전체삭제
				cacheService.removeCacheElementPortletContentAll("Cachename-myCollaboration-portlet");
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

	}

	/**
	 * 개별 Workspace > Administration > workspace 폐쇄신청
	 * 
	 * @param workspaceIds
	 * @param workspaceStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceCloseStatusAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean updateWorkspaceCloseStatusAjax(@RequestParam("workspaceId") String workspaceId,
			WorkspaceSearchCondition searchCondition, SessionStatus status) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		try {

			Workspace workspace = new Workspace();

			workspace.setPortalId(portal.getPortalId());
			workspace.setWorkspaceId(workspaceId);
			workspace.setWorkspaceStatus(WorkspaceConstants.WORKSPACE_STATUS_WAITING_CLOSE);

			workspace.setUpdaterId(user.getUserId());
			workspace.setUpdaterName(user.getUserName());

			workspaceService.updateStatus(workspace);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * workspace 부서 Workspace 상태변경내용 저장(ONLY 폐쇄)
	 * 
	 * @param workspaceIds
	 * @param workspaceStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateOrgWorkspaceCloseStatus.do", method = RequestMethod.GET)
	public String updateOrgWorkspaceStatus(@RequestParam("workspaceIds") List<String> workspaceIds,
			WorkspaceSearchCondition searchCondition, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		workspaceService.updateWorkspaceStatus(workspaceIds, "C", user);

		//My Collaboration  포틀릿 contents 캐시 Element 전체삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-myCollaboration-portlet");
		
		status.setComplete();
		return "redirect:/collpack/collaboration/workspace/listWaitingCloseOrgView.do?listType="
				+ searchCondition.getListType();
	}

	/**
	 * workspace 영구 삭제처리 ( 플래그값 변경 WORKSPACE_STATUS = ED , 배치 프로그램으로 영구 삭제처리 함)
	 * 
	 * @param workspaceIds
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteWorkspace.do", method = RequestMethod.POST)
	public String deleteWorkspace(@RequestParam("workspaceIds") List<String> workspaceIds,
			WorkspaceSearchCondition searchCondition, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		workspaceService.updateWorkspaceStatus(workspaceIds, "ED", user);
		status.setComplete();

		// return
		// "redirect:/collpack/collaboration/workspace/listWorkspaceWaitingView.do?listType="
		// + searchCondition.getListType() + "&listUrl=" +
		// searchCondition.getListUrl();
		return "redirect:/collpack/collaboration/workspace/listCloseWorkspaceView.do?listType=Close";
	}

	/**
	 * workspace 영구 삭제처리 ( 플래그값 변경 WORKSPACE_STATUS = ED , 배치 프로그램으로 영구 삭제처리 함)
	 * 
	 * @param workspaceIds
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteWorkspaceAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void deleteWorkspaceAjax(@RequestParam("workspaceIds") List<String> workspaceIds,
			WorkspaceSearchCondition searchCondition, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		try {
			workspaceService.updateWorkspaceStatus(workspaceIds, "ED", user);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
	}

	@RequestMapping(value = "/getWorkspaceImage.do")
	public ModelAndView getWorkspaceImage(String workspaceId) {

		ModelAndView mav = new ModelAndView("collpack/collaboration/workspace/listWorkspaceImage");

		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		searchCondition.setWorkspaceId(workspaceId);

		mav.addObject("searchCondition", searchCondition);

		return mav;
	}

	@RequestMapping(value = "/getSubListWorkspaceImage.do")
	public ModelAndView getSubLiatWorkspaceImage(WorkspaceSearchCondition workspaceSearchCondition) {

		ModelAndView mav = new ModelAndView("/collpack/collaboration/workspace/subListWorkspaceImage");

		try {

			workspaceSearchCondition.setPagePerRecord(10);
			SearchResult<Workspace> searchResult = workspaceService.getWorkspaceImageFile(workspaceSearchCondition);

			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchResult", searchResult);

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}
	
	/**
	 * 모바일 콜라보레이션 메뉴 관리
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getMyCollaborationList.do")
	public ModelAndView getMyCollaborationList(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setMemberId(user.getUserId());

		ModelAndView modelAndView = new ModelAndView();

		WorkspaceCode workspaceCode = new WorkspaceCode();

		modelAndView.setViewName("collpack/collaboration/workspace/getMyCollaborationList");
		searchCondition.setMemberId(user.getUserId());
		// Type 전체 조회
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		// 나의 Workspace 목록
		SearchResult<Workspace> searchResult = this.workspaceService.listBySearchConditionMobile(searchCondition);

		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workspaceCode", workspaceCode);

		return modelAndView;
	}
	
	/**
	 * 모바일 콜라보레이션 메뉴 관리 UPDATE
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/updateCollMenuList")
	public @ResponseBody
	void updateBoardMenuList(
		   @RequestParam("collaborationId") String collaborationId,
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", user.getUserId());
		
		List<String> itemList = new ArrayList<String>();
		for(String item : itemIds) {
			itemList.add(item);
		}
		param.put("itemIds",itemList );
		this.workspaceService.updateCollMenuList(param);
	}

}