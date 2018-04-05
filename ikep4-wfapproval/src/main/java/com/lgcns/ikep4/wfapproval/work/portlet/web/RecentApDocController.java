/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.portlet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.wfapproval.common.model.CommonCode;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;

/**
 * TODO Javadoc주석작성
 *
 * @author 
 * @version $Id: RecentApDocController.java 16234 2011-08-18 02:44:36Z giljae $
 */

@Controller
@RequestMapping(value = "/wfwfapproval/work/portlet/recentApDoc")
public class RecentApDocController extends BaseController{
	
	@Autowired
	private WorkplaceListService workplaceListService;

	//기본화면
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("wfapproval/work/portlet/recentApDoc/normalView");
		
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();

		String listType  = "todoList";
		
		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setPagePerRecord(5);
		workplaceSearchCondition.setQueryId(listType);
		workplaceSearchCondition.setUserId(userId);

		SearchResult<WorkItemBean> searchResult = this.workplaceListService.workplaceWorkList(workplaceSearchCondition);	

		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("apList", searchResult);
		
		return mav;
	}
	
	//설정화면
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("wfapproval/work/portlet/recentApDoc/configView");
		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
	
	//최대화면
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView( @RequestParam(value="pagePerRecord",required=false) String pagePerRecord,
								 @RequestParam(value="pageIndex",required=false) String pageIndex,
								 @RequestParam String portletConfigId ) {
		
		ModelAndView mav = new ModelAndView("wfapproval/work/portlet/recentApDoc/maxView");

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();

		String listType  = "todoList";
        
		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setQueryId(listType);
		workplaceSearchCondition.setUserId(userId);
		if( pagePerRecord != null )	workplaceSearchCondition.setPagePerRecord(new Integer(pagePerRecord));
		if( pageIndex != null )		workplaceSearchCondition.setPageIndex(new Integer(pageIndex));

		SearchResult<WorkItemBean> searchResult = this.workplaceListService.workplaceWorkList(workplaceSearchCondition);	

		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("apList",          searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode",   new WorkplaceCode());
		mav.addObject("listType",        listType);
		mav.addObject("commonCode", new CommonCode());
		return mav;
	}
}