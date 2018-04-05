/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.WorkListService;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순
 * @version $Id: WorkListController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
public class WorkListController {
	
	Log log = LogFactory.getLog(WorkListController.class);
	
	@Autowired
	private WorkListService workListService;	

	//********************************************************************************************
	/**
	 * TODO Javadoc주석작성
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/todolist.do", method = RequestMethod.GET)
	public ModelAndView getView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("workflow/workplace/todoList");
	}	
	

	
	
	//********************************************************************************************
	
	
	/**
	 * TODO Javadoc주석작성
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/todolistTest.do", method = RequestMethod.GET)
	public ModelAndView workflowTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		ModelAndView mav = new ModelAndView("workflow/workplace/todoList");
		log.info("# todoList start");
		String userId			= request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
		log.info("# todoList start0 : " + userId);
		List<WorkItemBean> list = workListService.getTodoList(userId);
		mav.addObject("title", "To-Do 리스트");
		mav.addObject("todoList", list);
		mav.addObject("size", "10");	
		mav.addObject("count", list.size());
		mav.addObject("userId", userId);	
		return mav;
	}
	
	// ********************************************************************************************
	
	/**
	 * TODO Javadoc주석작성
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/runningTest.do", method = RequestMethod.GET)
	public ModelAndView workflowRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		ModelAndView mav = new ModelAndView("workflow/workplace/runningList");
		log.info("# runningList start");
		String userId			= request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
		log.info("# runningList start0 : " + userId);
		List<WorkItemBean> list = workListService.getRunningList(userId);
		mav.addObject("title", "진행중 리스트");
		mav.addObject("todoList", list);
		mav.addObject("size", list.size());	
		mav.addObject("count", list.size());	
		mav.addObject("userId", userId);	
		return mav;
	}
	
	//********************************************************************************************
	/**
	 * TODO Javadoc주석작성
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/deployTest.do", method = RequestMethod.GET)
	public String workflowDeploy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		return "workflow/workplace/workflow";
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/deployTest.do", method = RequestMethod.POST)
	public String workflowPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		return "workflow/workplace/workflow";
	}

}
