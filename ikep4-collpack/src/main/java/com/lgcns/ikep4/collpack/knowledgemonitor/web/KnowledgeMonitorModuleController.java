/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Monitor KnowledgeMonitorModule controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/admin")
public class KnowledgeMonitorModuleController extends CustomController {

	@Autowired
	private KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	/**
	 * 관리범위설정 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/moduleManagerView")
	public ModelAndView moduleManagerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean knowledgeMonitorAdmin = isAdmin(user.getUserId());

		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService.listByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgemonitor/admin/moduleManagerView");

		mav.addObject("menuId", "31");
		mav.addObject("knowledgeMonitorAdmin", knowledgeMonitorAdmin);
		mav.addObject("knowledgeMonitorModuleList", knowledgeMonitorModuleList);

		return mav;
	}

	/**
	 * 관리범위 설정값 저장
	 * @param moduleCode (사용자가 선택한 모듈정보가 (,)로 분리된 문자열로 들어온다)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveModule")
	public ModelAndView saveModule(
			@RequestParam(value = "moduleCode", required=false, defaultValue = "") String moduleCode,
			SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		KnowledgeMonitorModule knowledgeMonitorModule = new KnowledgeMonitorModule();
		knowledgeMonitorModule.setPortalId(user.getPortalId());
		knowledgeMonitorModule.setUpdaterId(user.getUserId());
		knowledgeMonitorModule.setUpdaterName(user.getUserName());
		knowledgeMonitorModule.setModuleCodeCommaString(moduleCode);

		knowledgeMonitorModuleService.saveModules(knowledgeMonitorModule);

		status.setComplete();

		// view 연결
		ModelAndView mav = new ModelAndView("redirect:/collpack/knowledgemonitor/admin/moduleManagerView.do");

		return mav;
	}




}
