/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;
import com.lgcns.ikep4.workflow.admin.service.AdminParticipantsService;

/**
 * 참여자 정보 설정(팝업)
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin")
@SessionAttributes(value="adminParticipants")
public class AdminParticipantController extends BaseController{
	
	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private AdminParticipantsService adminParticipantsService;
	
	/**
	 * 조직도 팝업을 호출 하는 메서드(AddressbookController : addresbookPopup 메서드 이름만 변경하여 사용함)
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @param controlTabType : PUB:PRI:COL:SNS 순 0(선택안함) 1(선택함)으로 표기  예) PUB COL 선택시 ) 1:0:1:0 
	 * @param controlTabType : PUB:PRI:COL:SNS 순 0(선택안함) 1(선택함)으로 표기  예) PUB COL 선택시 ) 1:0:1:0 
	 * @return
	 */
	@RequestMapping("/participants.do")
	public ModelAndView addresbookPopup(@RequestParam(value="selectType", required=true, defaultValue="GROUP" ) String selectType
									, @RequestParam(value="controlTabType", required=true, defaultValue="0:0:0:0")  String controlTabType
									, @RequestParam(value="controlType", required=true, defaultValue="ORG" ) String controlType
									, @RequestParam(value="selectMaxCnt", required=true, defaultValue="500") String selectMaxCnt
									, @RequestParam(value="searchSubFlag", required=true, defaultValue="false" ) String searchSubFlag
								) {
		ModelAndView mav = new ModelAndView("workflow/admin/popup/participants");
		
		// 첫 팝업 호출시 ORG 타입으로 호출
		List<Map<String, Object>> list = getOrgGroupAndUser(null,selectType);
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("items", list);

		String listJson = "";

		try{
			ObjectMapper mapper = new ObjectMapper(); 
			listJson = mapper.writeValueAsString(items);
		} catch(Exception ex) {
		    throw new IKEP4AjaxException("code", ex);
		}
		
		mav.addObject("deptItems", listJson);
		
		if( !(selectType.equals("GROUP")) ){
			// Tab Display Flag
			if( controlTabType.length() >= 1 && (controlTabType.substring(0,1)).equals("0") ){
				mav.addObject("dispPubTabFlag", "false");
			}else{
				mav.addObject("dispPubTabFlag", "true");
			}
			
			if( controlTabType.length() >= 3 && (controlTabType.substring(2,3)).equals("0") ){
				mav.addObject("dispPriTabFlag", "false");
			}else{
				mav.addObject("dispPriTabFlag", "true");
			}
			
			if( controlTabType.length() >= 3 && (controlTabType.substring(4,5)).equals("0") ){
				mav.addObject("dispColTabFlag", "false");
			}else{
				mav.addObject("dispColTabFlag", "true");
			}
			
			if( controlTabType.length() >= 3 && (controlTabType.substring(6,7)).equals("0") ){
				mav.addObject("dispSnsTabFlag", "false");
			}else{
				mav.addObject("dispSnsTabFlag", "true");
			}
		}else{
			mav.addObject("dispPubTabFlag", "false");
			mav.addObject("dispPriTabFlag", "false");
			mav.addObject("dispColTabFlag", "false");
			mav.addObject("dispSnsTabFlag", "false");
		}
		
		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("selectMaxCnt", selectMaxCnt);
		mav.addObject("searchSubFlag", searchSubFlag);
		
		return mav;
	}
	
	/*
	 * AddressbookController : getOrgGroupAndUser 메소드 그대로 사용
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String selectType) {
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(sessionuser.getUserId());
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		for(Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", group.getGroupName());
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}
		
		if( !(selectType.equals("GROUP"))){
			
			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId,sessionuser.getUserId());
			for(User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				map.put("name", user.getUserName());
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("jobTitle", user.getJobTitleName());
				map.put("teamName", user.getTeamName() );
				list.add(map);
			}
		}
		
		return list;
	}
	
	/**
	 * Role List
	 * @param keyword
	 */
	@RequestMapping("participantsSearchRole.do")
	public ModelAndView requestSearchRole(
			  @RequestParam(value="participantSearchTitle", required=false, defaultValue="all") String participantSearchTitle
			, @RequestParam(value="pageIndex", required=false, defaultValue="1")  Integer pageIndex
			, @RequestParam(value="startRowIndex", required=false, defaultValue="0") Integer startRowIndex
			, @RequestParam(value="endRowIndex", required=false, defaultValue="10") Integer endRowIndex
			, @RequestParam(value="pageCount", required=false, defaultValue="1") Integer pageCount
			)
			{
		AdminParticipantSearchCondition participantSearchCondition = new AdminParticipantSearchCondition();
		participantSearchCondition.setParticipantSearchTitle(participantSearchTitle);
		participantSearchCondition.setPageIndex(pageIndex);
		participantSearchCondition.setStartRowIndex(startRowIndex);
		participantSearchCondition.setEndRowIndex(endRowIndex);
		participantSearchCondition.setPageCount(pageCount);
		ModelAndView modelAndView = new ModelAndView("/workflow/admin/participants/participantsList");
		/*
		 * 롤 조회
		 */
		SearchResult<AdminParticipants> roleSearchResult = this.adminParticipantsService.listRole(participantSearchCondition);
		modelAndView.addObject("roleSearchResult", roleSearchResult);
		modelAndView.addObject("roleSearchCondition", roleSearchResult.getSearchCondition());
		return modelAndView;
	}
}