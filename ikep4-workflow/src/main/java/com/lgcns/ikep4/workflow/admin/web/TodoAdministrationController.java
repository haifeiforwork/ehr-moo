/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminTodo;
import com.lgcns.ikep4.workflow.admin.model.AdminTodoSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.TodoAdministrationService;

/**
 * 워크플로우 - 현황관리 - 업무관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: TodoAdministrationController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin")
@SessionAttributes(value="todoAdministration")
public class TodoAdministrationController extends BaseController{
	@Autowired
	private TodoAdministrationService todoAdministrationService;
	
	/*
	 * 업무 관리 화면
	*/
	@RequestMapping("/todoAdministration.do")
	public ModelAndView todoAdministration(AdminTodoSearchCondition adminTodoSearchCondition,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		
		if(adminTodoSearchCondition.getSortColumn() == null){
			adminTodoSearchCondition.setSortColumn("CREATE_DATE");
			adminTodoSearchCondition.setSortType("DESC");
		}
		
		/*
		 * 업무관리 조회 한페이지에 보여줄 건수 설정
		 */
		adminTodoSearchCondition.setPagePerRecord(adminTodoSearchCondition.getPagePerRecord());
		
		/*
		 * 업무관리 리스트 조회
		 */
		SearchResult<AdminTodo> todoSearchResult = this.todoAdministrationService.listTodo(adminTodoSearchCondition);
		modelAndView.addObject("getPath",request.getContextPath());
		modelAndView.addObject("todoSearchResult", todoSearchResult);
		modelAndView.addObject("todoSearchCondition", todoSearchResult.getSearchCondition());
		modelAndView.setViewName("workflow/admin/todoAdministration");
		
		return modelAndView;
	}
}
