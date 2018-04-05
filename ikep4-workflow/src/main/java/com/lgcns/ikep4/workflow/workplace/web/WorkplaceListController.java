/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;

/**
 * WorkPlace 컨트롤러
 * 
 * @author 이재경
 * @version $Id: WorkplaceListController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/workflow/workplace/worklist")
public class WorkplaceListController extends BaseController {

	@Autowired
	private WorkplaceListService workplaceListService;
	
	/**
	 * Workplace 업무시작
	 * @return
	 */
	@RequestMapping(value = "/startWorkList.do")
	public ModelAndView workPlaceStartWorkList(WorkplaceSearchCondition workplaceSearchCondition) {
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/worklist/startWorkList");

		workplaceSearchCondition.setQueryId("startProcList");
		
		SearchResult<ProcessBean> searchResult = workplaceListService.workplaceProcessList(workplaceSearchCondition);
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());

		workplaceSearchCondition.setQueryId("selectPartition");
		mav.addObject("partitionClass", (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition));
		
		return mav;
	}
	
	/**
	 * Workplace 나의 업무목록
	 * @return
	 */
	@RequestMapping(value = "/myWorkList.do")
	public ModelAndView workPlaceMyWorkList(WorkplaceSearchCondition workplaceSearchCondition
											, HttpServletRequest request
											, HttpServletResponse response) {
		
		if( request.getParameter("persistence") != null ){
			workplaceSearchCondition = (WorkplaceSearchCondition)getRequestAttribute("searchcondition");
		}
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/worklist/myWorkList");

		workplaceSearchCondition.setQueryId("todoList");
		
		User user = (User)this.getRequestAttribute("ikep.user");
		workplaceSearchCondition.setUserId(user.getUserId());
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		setRequestAttribute("searchcondition", searchResult.getSearchCondition());
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());
		
		workplaceSearchCondition.setQueryId("selectPartition");
		mav.addObject("partitionClass", (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition));
		
		return mav;
	}
	
	/**
	 * Workplace 진행중 업무목록
	 * @return
	 */
	@RequestMapping(value = "/progressWorkList.do")
	public ModelAndView workPlaceProgressWorkList(WorkplaceSearchCondition workplaceSearchCondition) {
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/worklist/progressWorkList");
		
		workplaceSearchCondition.setQueryId("runningList");
		
		User user = (User)this.getRequestAttribute("ikep.user");
		workplaceSearchCondition.setUserId(user.getUserId());
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());
		
		workplaceSearchCondition.setQueryId("selectPartition");
		mav.addObject("partitionClass", (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition));
		
		return mav;
	}
	
	/**
	 * Workplace 에러 업무목록
	 * @return
	 */
	@RequestMapping(value = "/errorWorkList.do")
	public ModelAndView workPlaceErrorWorkList(WorkplaceSearchCondition workplaceSearchCondition) {
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/worklist/errorWorkList");
		
		workplaceSearchCondition.setQueryId("errorList");
		
		User user = (User)this.getRequestAttribute("ikep.user");
		workplaceSearchCondition.setUserId(user.getUserId());
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());
		
		workplaceSearchCondition.setQueryId("selectPartition");
		mav.addObject("partitionClass", (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition));
		
		return mav;
	}
	
	/**
	 * Workplace 완료된 업무목록
	 * @return
	 */
	@RequestMapping(value = "/doneWorkList.do")
	public ModelAndView workPlaceDoneWorkList(WorkplaceSearchCondition workplaceSearchCondition) {
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/worklist/doneWorkList");
		
		workplaceSearchCondition.setQueryId("completeList");
		
		User user = (User)this.getRequestAttribute("ikep.user");
		workplaceSearchCondition.setUserId(user.getUserId());
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());

		workplaceSearchCondition.setQueryId("selectPartition");
		mav.addObject("partitionClass", (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition));
		
		return mav;
	}
}