/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import java.util.HashMap;
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
import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.InstanceAdministrationService;

/**
 * 워크플로우 - 현황관리 - 인스턴스 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: InstanceAdministrationController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin")
@SessionAttributes(value="instanceAdministration")
public class InstanceAdministrationController extends BaseController{
	
	@Autowired
	private InstanceAdministrationService instanceAdministrationService;
	
	/*
	 * 인스턴스 관리 화면
	*/
	@RequestMapping("/instanceAdministration.do")
	public ModelAndView processAdministration(AdminInstanceSearchCondition adminInstanceSearchCondition){
		ModelAndView modelAndView = new ModelAndView();

		if(adminInstanceSearchCondition.getSortColumn() == null){
			adminInstanceSearchCondition.setSortColumn("CREATE_DATE");
			adminInstanceSearchCondition.setSortType("DESC");
		}
		
		/*
		 * 파티션 조회(ComboBox)
		 */
		modelAndView.addObject("listComboPartition", instanceAdministrationService.listComboPartition());
		
		/*
		 * 인스턴스 조회 한페이지에 보여줄 건수 설정
		 */
		adminInstanceSearchCondition.setPagePerRecord(adminInstanceSearchCondition.getPagePerRecord());
		
		/*
		 * 인스턴스 리스트 조회
		 */
		SearchResult<AdminInstance> instanceSearchResult = this.instanceAdministrationService.listInstance(adminInstanceSearchCondition);
		
		modelAndView.addObject("instanceSearchResult", instanceSearchResult);
		modelAndView.addObject("instanceSearchCondition", instanceSearchResult.getSearchCondition());
		modelAndView.setViewName("workflow/admin/instanceAdministration");
		return modelAndView;
	}
	
	/*
	 * 인스턴스 상태값 변경
	*/ 
	@RequestMapping(method = RequestMethod.POST, value = "/updateInstanceState.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Integer updateInstanceState(@RequestParam("instanceId") String instanceId,@RequestParam("instanceState") String instanceState){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("instanceId", instanceId.split(":"));
		params.put("instanceState", instanceState);
		try{
			return instanceAdministrationService.updateInstanceState(params);
		}catch(Exception ex){
			throw new IKEP4AjaxException("code",ex);
		}
	}
}
