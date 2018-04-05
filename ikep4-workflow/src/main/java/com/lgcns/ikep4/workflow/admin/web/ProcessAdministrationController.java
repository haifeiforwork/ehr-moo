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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminActivity;
import com.lgcns.ikep4.workflow.admin.model.AdminActivitySearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminService;
import com.lgcns.ikep4.workflow.admin.service.ProcessAdministrationService;

/**
 *  워크플로우 - 현황관리 - 프로세스 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: ProcessAdministrationController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin")
@SessionAttributes(value="processAdministration")
public class ProcessAdministrationController extends BaseController{
	
	@Autowired
	private ProcessAdministrationService processAdministrationService;
	
	
	/*
	 * 프로세스 관리 화면
	*/
	@RequestMapping("/processAdministration.do")
	public ModelAndView processAdministration(AdminProcessSearchCondition processSearchCondition,AdminActivitySearchCondition activitySearchCondition){
		ModelAndView modelAndView = new ModelAndView();
		
		/*
		 * 파티션 조회(ComboBox)
		 */
		modelAndView.addObject("listComboPartition", processAdministrationService.listComboPartition());
		
		/*
		 * 프로세스 조회 한페이지에 보여줄 건수 설정
		 */
		processSearchCondition.setPagePerRecord(processSearchCondition.getPagePerRecord());
		
		/*
		 * 프로세스 리스트 조회
		 */
		SearchResult<AdminProcess> processSearchResult = this.processAdministrationService.listProcess(processSearchCondition);
		
		/*
		 * 액티비티 조회 한페이지에 보여줄 건수 설정
		 */
		activitySearchCondition.setPagePerRecord(activitySearchCondition.getPagePerRecord());
		
		/*
		 * 액티비티 리스트 조회
		 */
		SearchResult<AdminActivity> activitySearchResult = this.processAdministrationService.listActivity(activitySearchCondition);
		
		modelAndView.addObject("activitySearchResult", activitySearchResult);
		modelAndView.addObject("activitySearchCondition", activitySearchResult.getSearchCondition());
		
		modelAndView.addObject("processSearchResult", processSearchResult);
		modelAndView.addObject("processSearchCondition", processSearchResult.getSearchCondition());
		modelAndView.setViewName("workflow/admin/processAdministration");
		
		return modelAndView;
	}
	
	/*
	 * 프로세스 조회(ComboBox)
	*/
	@RequestMapping(method = RequestMethod.POST, value = "/listComboProcess.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<String> listComboProcess(@RequestParam("partitionId") String partitionId){
		try{
			return processAdministrationService.listComboProcess(partitionId);
		}catch(Exception ex){
			throw new IKEP4AjaxException("code", ex);
		}
	}
	 
	/*
	 * 프로세스 상태값 변경
	*/ 
	@RequestMapping(method = RequestMethod.POST, value = "/updateProcessState.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Integer updateProcessState(@RequestParam("processId") String processId,@RequestParam("processState") String processState){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("processId", processId.split(":"));
		params.put("processState", processState);
		try{
			return processAdministrationService.updateProcessState(params);
		}catch(Exception ex){
			throw new IKEP4AjaxException("code", ex);
		}
	}
	
	/*
	 * 프로세스 상세화면
	*/
	@RequestMapping("/processAdministrationDetail.do")
	public ModelAndView processAdministrationDetail(@RequestParam("partitionId") String partitionId,@RequestParam("processId") String processId,@RequestParam("processVer") String processVer){
		ModelAndView modelAndView = new ModelAndView();

		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", processId);
		params.put("processVer", processVer);
		/*
		 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
		 */
		modelAndView.addObject("listProcessDetail", processAdministrationService.listProcessDetail(params)) ;
		modelAndView.addObject("partitionId",partitionId);
		modelAndView.setViewName("workflow/admin/processAdministrationDetail");
		return modelAndView;
	}
	
	/*
	 * 프로세스 상세화면 프로세스 상세화면 적용일자 수정
	*/
	@RequestMapping("/updateProcessApplyDate.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Integer updateProcessApplyDate(@RequestParam("processId") String processId,@RequestParam("processVer") String processVer,@RequestParam("applyStartDate") String applyStartDate,@RequestParam("applyEndDate") String applyEndDate){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", processId);
		params.put("processVer", processVer);
		params.put("applyStartDate", applyStartDate);
		params.put("applyEndDate", applyEndDate);
		
		try{
			return processAdministrationService.updateProcessApplyDate(params);
		}catch(Exception ex){
			throw new IKEP4AjaxException("code", ex);
		}
	}
}
