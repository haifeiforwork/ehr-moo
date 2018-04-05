/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessDash;
import com.lgcns.ikep4.workflow.admin.service.DashBoardAdministrationService;

/**
 * 워크플로우 - 현황관리 - 데시보드
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: DashBoardAdministrationController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin")
@SessionAttributes(value="dashBoardAdministration")
public class DashBoardAdministrationController extends BaseController{
	
	@Autowired
	private DashBoardAdministrationService dashBoardAdministrationService;
	
	/*
	 * 데시보드 관리 화면
	*/
	
	@RequestMapping("/dashBoardAdministration.do")
	public ModelAndView dashboardAdministration(@RequestParam(value="searchCondition", defaultValue="1" ) String searchCondition){
		ModelAndView modelAndView = new ModelAndView("workflow/admin/dashBoardAdministration");
		/*
		 * searchCondition 조건
		 */
		modelAndView.addObject("searchCondition",searchCondition);
		
		/*
		 * 파티션 전체 조회건수
		 */
		modelAndView.addObject("partitionCount",dashBoardAdministrationService.partitionCount());
		
		/*
		 * 프로세스 전체 조회건수
		 */
		modelAndView.addObject("processCount",dashBoardAdministrationService.processCount());
		
		/*
		 * 인스턴스 현황 
		 */
		modelAndView.addObject("instanceStateCount",dashBoardAdministrationService.instanceStateCount());
		
		/*
		 * 프로세스별 인스턴스 진행 현황
		 */
		modelAndView.addObject("listCurrentInstance",dashBoardAdministrationService.listCurrentInstance());
		
		/*
		 * 프로세스별  인스턴스건수
		 */
		List<AdminProcessDash> processInstanceCountList	= dashBoardAdministrationService.processInstanceCount(searchCondition);
		String strProcessInstanceCount		= "[";
		for(int i=0;i<processInstanceCountList.size();i++) {
			AdminProcessDash adminProcessDash	= processInstanceCountList.get(i);
			if(i==0) {
				strProcessInstanceCount			+= "[\"" + adminProcessDash.getProcessName() + "\"," + adminProcessDash.getProcessCount() + "]";
			} else {
				strProcessInstanceCount			+= ", [\"" + adminProcessDash.getProcessName() + "\"," + adminProcessDash.getProcessCount() + "]";
			}
		}
		strProcessInstanceCount		+= "]";
		
		modelAndView.addObject("processInstanceCount",strProcessInstanceCount);
		
		/*
		 * 프로세스별  인스턴스 누적현황
		*/
		modelAndView.addObject("listAccumulateInstance",dashBoardAdministrationService.listAccumulateInstance(searchCondition));
		
		return modelAndView;
	}
}
