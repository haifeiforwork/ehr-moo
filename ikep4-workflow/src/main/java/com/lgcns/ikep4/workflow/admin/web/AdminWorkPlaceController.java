/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;

/**
 * 에러업무목로 (WorkplaceListController : workPlaceErrorWorkList메소드 사용)
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminWorkPlaceController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin/workplace")
public class AdminWorkPlaceController extends BaseController {
	
	@Autowired
	private WorkplaceListService workplaceListService;
	
	/**
	 * Workplace 에러 업무목록
	 * @return
	 */
	@RequestMapping(value = "/errorWorkList.do")
	public ModelAndView workPlaceErrorWorkList(WorkplaceSearchCondition workplaceSearchCondition) {
		
		ModelAndView mav = new ModelAndView("/workflow/admin/worklist/errorWorkList");
		
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
}
