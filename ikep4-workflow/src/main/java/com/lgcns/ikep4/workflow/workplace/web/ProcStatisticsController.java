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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.ProcStatisticsService;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;

/**
 * WorkPlace 컨트롤러
 * 
 * @author 이재경
 * @version $Id: ProcStatisticsController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/workflow/workplace/statistics")
public class ProcStatisticsController extends BaseController {

	@Autowired
	private ProcStatisticsService procStatisticsService;
	
	@Autowired
	private WorkplaceListService workplaceListService;
	
	/**
	 * Workplace 프로세스별 통계
	 * @return
	 */
	@RequestMapping(value = "/procStatistics.do")
	public ModelAndView procStatisticsDetail(WorkplaceSearchCondition workplaceSearchCondition){
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/statistics/procStatistics");
		
		mav.addObject("processBean", (ProcessBean)procStatisticsService.procStatisticsDetail(workplaceSearchCondition));
		mav.addObject("workplaceSearchCondition", workplaceSearchCondition);
		mav.addObject("workplaceCode", new WorkplaceCode());

		workplaceSearchCondition.setQueryId("selectProcess");
		mav.addObject("processList", (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition));
		mav.addObject("processRunningCount", this.procStatisticsService.procStatisticsRunningCount(workplaceSearchCondition));
		mav.addObject("processCompleteCount", this.procStatisticsService.procStatisticsCompleteCount(workplaceSearchCondition));
		
		return mav;
	}
	
	@RequestMapping("/processVerList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<HashMap<?, ?>> readeUser(@RequestParam("processId") String processId) {
		List<HashMap<?, ?>> result = null;
	    try {
	    	WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();
	    	workplaceSearchCondition.setQueryId("selectProcessVer");
	    	workplaceSearchCondition.setProcessId(processId);
	    	result = (List<HashMap<?, ?>>)this.workplaceListService.selectListHashMap(workplaceSearchCondition);
	    } 
	    catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}
}