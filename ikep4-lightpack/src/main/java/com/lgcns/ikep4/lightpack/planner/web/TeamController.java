package com.lgcns.ikep4.lightpack.planner.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceCode;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "lightpack/planner/team")
public class TeamController extends BaseController {
	
	@Autowired
	private WorkspaceTypeService workspaceTypeService;
	
	@Autowired
	private WorkspaceService workspaceService;
	
	/**
	 * 모바일 콜라보레이션 메뉴 관리
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getPlannerTeamMenuList.do")
	public ModelAndView getPlannerTeamMenuList(WorkspaceSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());

		ModelAndView modelAndView = new ModelAndView();

		WorkspaceCode workspaceCode = new WorkspaceCode();

		modelAndView.setViewName("lightpack/planner/team/getPlannerTeamMenuList");
		searchCondition.setMemberId(user.getUserId());
		// Type 전체 조회
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		// 나의 Workspace 목록
		SearchResult<Workspace> searchResult = this.workspaceService.listBySearchConditionPlannerForMobile(searchCondition);

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
	@RequestMapping(value = "/updatePlannerMenuList")
	public @ResponseBody
	void updateBoardMenuList(
		   @RequestParam("plannerId") String plannerId,
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", user.getUserId());
		
		List<String> itemList = new ArrayList<String>();
		for(String item : itemIds) {
			itemList.add(item);
		}
		param.put("itemIds",itemList );
		this.workspaceService.updatePlannerMenuList(param);
	}
}
