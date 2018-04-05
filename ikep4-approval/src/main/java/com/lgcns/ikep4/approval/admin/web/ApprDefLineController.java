/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;

import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;
import com.lgcns.ikep4.support.user.code.service.JobPositionService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.approval.admin.model.ApprDefLine;
import com.lgcns.ikep4.approval.admin.model.ApprDefLineCode;
import com.lgcns.ikep4.approval.admin.model.ApprSystem;
import com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprDefLineService;
import com.lgcns.ikep4.approval.admin.service.ApprSystemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;

import com.lgcns.ikep4.util.lang.StringUtil;



/**
 * Default 결재선 관리 Controller
 *
 * @author 
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/admin/apprDefLine")
public class ApprDefLineController extends BaseController {
	
	@Autowired
	private	ApprDefLineService	apprDefLineService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	JobDutyService jobDutyService;

	@Autowired
	JobPositionService jobPositionService;
	
	@Autowired
	private ApprSystemService apprSystemService;
	
	
	@Autowired
	private ACLService aclService;
	
	/**
	 * 사용자 Group 정보 컨트롤용 Service
	 */
	@Autowired
	private GroupService groupService;
	
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
	 * 결재선 등록 View
	 * @param selectType
	 * @param controlTabType
	 * @param controlType
	 * @param searchSubFlag
	 * @param defLineId
	 * @return
	 */
	@RequestMapping(value = "/createView.do")
	public ModelAndView createView(@RequestParam(value="selectType", required=true, defaultValue="ALL" ) String selectType
			, @RequestParam(value="controlTabType", required=true, defaultValue="1:0:0:0")  String controlTabType
			, @RequestParam(value="controlType", required=true, defaultValue="ORG" ) String controlType
			, @RequestParam(value="searchSubFlag", required=true, defaultValue="false" ) String searchSubFlag
			, @RequestParam(value="defLineId", required = false) String defLineId
		) {
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ModelAndView	mav			=	new ModelAndView();
		mav.setViewName("/approval/admin/apprDefLine/createView");
		
		ApprDefLine apprDefLine = new ApprDefLine();
		
		if(defLineId!=null && !defLineId.isEmpty()){
			apprDefLine = apprDefLineService.read(defLineId);			
		}
		List<ApprSystem> apprSystemList =  apprSystemService.selectList(portal.getPortalId());
	
		mav.addObject("apprDefLine", apprDefLine);
		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("searchSubFlag", searchSubFlag);
		mav.addObject("apprSystemList", apprSystemList);
		mav.addObject("isSystemAdmin", 			this.isSystemAdmin(user));
		mav.addObject("userLocaleCode", user.getLocaleCode());
		return mav;
	}
	
	/**
	 * Default 결재선 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveDefLine.do", method = RequestMethod.POST)
	public @ResponseBody String ajaxSaveDefLine(@Valid ApprDefLine apprDefLine, BindingResult result,HttpServletRequest request,  SessionStatus status) {
		String returnValue = "";
		try{

			// 세션 객체 가지고 오기
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			apprDefLine.setPortalId(portal.getPortalId());
			
			apprDefLine.setRegisterId(user.getUserId());
			apprDefLine.setRegisterName(user.getUserName());			
			
			//Default 결재선 등록 & 결재선 Flow 정보 등록
			if(apprDefLine.getDefLineId()==null || apprDefLine.getDefLineId().equals(""))
				returnValue=apprDefLineService.create(apprDefLine);
			else 
				apprDefLineService.update(apprDefLine);	
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSaveDefLine", ex);
		}

		return returnValue;
	}	
	
	/**
	 * 결재선 등록 View ( 양식관리 등록 화면에서 호출 )
	 * @param selectType
	 * @param controlTabType
	 * @param controlType
	 * @param searchSubFlag
	 * @param defLineId
	 * @return
	 */
	@RequestMapping(value = "/createPopupView.do")
	public ModelAndView createPopupView(@RequestParam(value="selectType", required=true, defaultValue="ALL" ) String selectType
			, @RequestParam(value="controlTabType", required=true, defaultValue="1:0:0:0")  String controlTabType
			, @RequestParam(value="controlType", required=true, defaultValue="ORG" ) String controlType
			, @RequestParam(value="searchSubFlag", required=true, defaultValue="false" ) String searchSubFlag
			, @RequestParam(value="defLineId", required = false) String defLineId
			, @RequestParam(value="defLineType", required = false) String defLineType
			, @RequestParam(value="systemId", required = false) String systemId
		) {
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ModelAndView	mav			=	new ModelAndView();
		mav.setViewName("/approval/admin/apprDefLine/createPopupView");
		
		ApprDefLine apprDefLine = new ApprDefLine();
		
		if(defLineId!=null && !defLineId.isEmpty()){
			apprDefLine = apprDefLineService.read(defLineId);			
		}
		List<ApprSystem> apprSystemList =  apprSystemService.selectList(portal.getPortalId());
	
		mav.addObject("apprDefLine", apprDefLine);
		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("searchSubFlag", searchSubFlag);
		mav.addObject("apprSystemList", apprSystemList);
		
		mav.addObject("defLineType", defLineType);
		mav.addObject("systemId", systemId);
		mav.addObject("userLocaleCode", user.getLocaleCode());
		return mav;
	}
	
	/**
	 * Default 결재선 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxCreatePopup.do", method = RequestMethod.POST)
	public @ResponseBody String ajaxCreatePopup(@Valid ApprDefLine apprDefLine, BindingResult result,HttpServletRequest request,  SessionStatus status) {
		String returnValue = "";
		try{

			// 세션 객체 가지고 오기
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			apprDefLine.setPortalId(portal.getPortalId());
			
			apprDefLine.setRegisterId(user.getUserId());
			apprDefLine.setRegisterName(user.getUserName());			
			
			//Default 결재선 등록 & 결재선 Flow 정보 등록
			if(apprDefLine.getDefLineId()==null || apprDefLine.getDefLineId().equals(""))
				returnValue=apprDefLineService.create(apprDefLine);
			else 
				apprDefLineService.update(apprDefLine);	
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSaveDefLine", ex);
		}

		return returnValue;
	}
	
	
	/**
	 * Default 결재선 목록조회
	 * @param searchCondition
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listView.do")
	public ModelAndView listView( ApprDefLineSearchCondition searchCondition,
			                            HttpServletRequest request,
			                            HttpServletResponse response) throws ServletException, IOException { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/admin/apprDefLine/listView");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		//searchCondition.setSystemId(searchCondition);
		searchCondition.setPortalId(portal.getPortalId());
		SearchResult<ApprDefLine> searchResult = this.apprDefLineService.listBySearchCondition(searchCondition);			
		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();

		List<ApprSystem> apprSystemList =  apprSystemService.selectList(portal.getPortalId());
		
		modelAndView.addObject("apprSystemList",	apprSystemList);
		modelAndView.addObject("searchResult",		searchResult);
		modelAndView.addObject("searchCondition",	searchResult.getSearchCondition());
		modelAndView.addObject("commonCode",		commonCode);
		modelAndView.addObject("isSystemAdmin", 			this.isSystemAdmin(user));
		return modelAndView;
	}
	
	
	/**
	 * Ajax Default 결재선 결재유형별 목록 조회
	 * @param apprId
	 * @return
	 */
	@RequestMapping("/getApprDefLine.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<ApprDefLine> getApprDefLine(@RequestParam(value="defLineType", required = true) int defLineType,
			@RequestParam(value="systemId", required = false) String systemId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<ApprDefLine>	list	=	null;
		ApprDefLine apprDefLine		=	new ApprDefLine();
		apprDefLine.setPortalId(portal.getPortalId());
		apprDefLine.setSystemId(systemId);
		apprDefLine.setDefLineType(defLineType);
		
		try {
			list = apprDefLineService.listApprDefLineType(apprDefLine);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return list;		
	}	
	
	/**
	 * 결재선 미리보기
	 * @param defLineId
	 * @return
	 */
	@RequestMapping(value = "/previewDefLineView.do")
	public ModelAndView previewDefLineView(@RequestParam(value="defLineId", required = true) String defLineId
		) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView	mav			=	new ModelAndView();
		mav.setViewName("/approval/admin/apprDefLine/previewDefLineView");
		
	
		ApprDefLine apprDefLine = new ApprDefLine();
		
		if(defLineId!=null){
			apprDefLine = apprDefLineService.read(defLineId);			
		}
		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();
		mav.addObject("apprDefLine", apprDefLine);
		mav.addObject("userLocaleCode", user.getLocaleCode());
		mav.addObject("commonCode", commonCode);
		return mav;
	}
		
	/**
	 * 사용자 호칭/직책
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getUserInfo.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	User requestUserJobPosDutyList(@RequestParam(value="userId", required = true) String userId) {
		User user	= new User();
		try {
			user	=	userService.read(userId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return user;		
	}
	
	/**
	 * 직책을 조회 하는 메서드 <br/>
	 * 기준 GroupId를 기준으로 하위의 조직도를 데이타를 조회
	 *
	 * @return 조회된 직책 리스트 Map
	 */
	@RequestMapping("/requestJobDutyList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestJobDutyList() {

		// searchType
		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			list = getJobDutyList();
			item.put("items", list);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;

	}
	
	private List<Map<String, Object>> getJobDutyList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		AdminSearchCondition searchCondition = new AdminSearchCondition();
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("SORT_ORDER");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("DESC");
		}
		searchCondition.setFieldName("jobDutyName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<JobDuty> searchResult = jobDutyService.list(searchCondition);
		

		// 직책정보
		if (searchResult.getEntity() != null) {
			List<JobDuty> jobDutyList = searchResult.getEntity();
			for (JobDuty jobDuty : jobDutyList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (user.getLocaleCode().equals("ko")) {
					map.put("name", jobDuty.getJobDutyName());
				} else {
					map.put("name", jobDuty.getJobDutyEnglishName());
				}
				map.put("code", "");
				map.put("parent", "jobDuty");
				map.put("id", jobDuty.getJobDutyCode());
				map.put("empNo", "");
				map.put("email", "");
				map.put("mobile", "");
				map.put("jobTitle", "");
				map.put("teamName", "");
				map.put("icon", "");
				list.add(map);
			}
		}

		return list;
	}

	
	/**
	 * 부서별 리더를 조회 하는 메서드 <br/>
	 * 기준 GroupId를 기준으로 하위의 조직도를 데이타를 조회
	 *
	 * @return 조회된 부서별 리더 리스트 Map
	 */
	@RequestMapping("/requestLeaderList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestLeaderList(@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "controlType", required = true, defaultValue = "ORG") String controlType,
			@RequestParam(value = "selectType", required = true, defaultValue = "GROUP") String selectType) {

		// searchType
		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			list = getLeaderList(groupId, userId, selectType);
			item.put("items", list);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;

	}
	
	private List<Map<String, Object>> getLeaderList(String groupId, String userId, String selectType) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		User user = (User) getRequestAttribute("ikep.user");
		//String portalId = (String) getRequestAttribute("ikep.portalId");
		
		String searchUserId = "";
		
		if (StringUtil.isEmpty(userId)) {
			searchUserId = user.getUserId();
		} else {
			searchUserId = userId;
		}
 
		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("type", "group");
			map.put("type", "group");	// 그룹의 리더 표시로 type:user 변경처리함
			if (user.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			//map.put("group", "Leader");
			map.put("code", group.getGroupId()); //+":LD");	// 그룹 코드 + ':LD' 추가하여 그룹과 구분함
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}

		return list;
	}
	
	
	/**
	 * Default 결재선 삭제
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteAjax(@RequestParam("defLineIds") List<String> defLineIds,ApprDefLineSearchCondition searchCondition, SessionStatus status) {
		//String returnValue = "";
		try{
			apprDefLineService.delete(defLineIds);	
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteAjaxDefLine", ex);
		}
	}
	
	
}
