/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.web.BaseController;

/**
 * Import, Export Controller
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: ImExportController.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Controller
@RequestMapping(value = "lightpack/planner/calendar/imexport")
public class ImExportController extends BaseController {
//
//	@Autowired
//	private IcalService service;
//	
//	@RequestMapping("/export")
//	public ModelAndView exportView(
//			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Planner", operationName = { "MANAGE" }, resourceName = "Planner")) AccessingResult result) {
//		if (result.isAccess()) {
//			setRequestAttribute("isPlannerAdmin", true);
//		} else {
//			setRequestAttribute("isPlannerAdmin", false);
//		}
//		return new ModelAndView("lightpack/planner/calendar/export");
//	}O
//	
//	@RequestMapping("/doExport")
//	public void doExport(@RequestParam("start") long start, 
//			@RequestParam("end") long end, HttpServletResponse response) {
//
//		try {
//			List<ICalendar> list = service.getICalendarList(getUser().getUserId(), start, end);
//		} catch (Exception ex) {
//			// throw new Ikep4AjaxException("", null, ex);
//		}
//	}
//	
//	private User getUser() {
//		return (User) getRequestAttribute("ikep.user");
//	}
}
