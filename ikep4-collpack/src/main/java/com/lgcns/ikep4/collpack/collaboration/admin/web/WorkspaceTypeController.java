/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.web;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 카테고리 관리 Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceTypeController.java 3449 2011-03-19 08:04:08Z
 *          happyi1018 $
 */

@Controller
@RequestMapping(value = "/collpack/collaboration/admin/workspaceType")
@SessionAttributes("collaboration")
public class WorkspaceTypeController extends BaseController {

	@Autowired
	private WorkspaceTypeService workspaceTypeService;

	/**
	 * 포탈별 workspace 유형목록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceTypeView.do")
	public ModelAndView listWorkspaceTypeView() {

		User user = (User) getRequestAttribute("ikep.user");
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");

		WorkspaceType workspaceType = new WorkspaceType();
		ModelAndView modelAndView = new ModelAndView(
				"collpack/collaboration/admin/workspaceType/listWorkspaceTypeView");

		List<WorkspaceType> workspaceTypeList = workspaceTypeService
				.listWorkspaceTypeAll(user.getPortalId(), "Y");

		List<WorkspaceType> countWorkspaceTypeList = workspaceTypeService
				.countWorkspaceByType(user.getPortalId());

		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		modelAndView
				.addObject("countWorkspaceTypeList", countWorkspaceTypeList);
		modelAndView.addObject("workspaceType", workspaceType);
		modelAndView.addObject("size", workspaceTypeList.size());

		return modelAndView;
	}

	/**
	 * workspace 유형 목록 + 조직
	 * 
	 * @param searchCondition
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/listWorkspaceType.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<WorkspaceType> listWorkspaceType() {

		List<WorkspaceType> workspaceTypeList = null;
		try {
			User user = (User) getRequestAttribute("ikep.user");

			workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(
					user.getPortalId(), "Y");
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return workspaceTypeList;
	}

	/**
	 * 워크스페이스 유형저장
	 * 
	 * @param workspaceType
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createWorkspaceType.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String onSubmit(@Valid WorkspaceType workspaceType, BindingResult result) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		String typeId = workspaceType.getTypeId();
		try {

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			if (!StringUtil.isEmpty(typeId)) {
				workspaceType.setPortalId(portal.getPortalId());
				workspaceTypeService.update(workspaceType);
			} else {
				workspaceType
						.setIsOrganization(WorkspaceConstants.TYPE_ORANIZATION_FLAG);
				workspaceType.setPortalId(portal.getPortalId());
				workspaceType.setRegisterId(user.getUserId());
				workspaceType.setRegisterName(user.getUserName());

				typeId = workspaceTypeService.create(workspaceType);
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}

		return typeId;
	}

	/**
	 * 유형 순서 변경
	 * 
	 * @param typeIds
	 * @return
	 */
	@RequestMapping(value = "/updateWorkspaceTypeOrder.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String applyWorkspaceTypeOrder(@RequestParam("typeIds") String typeIds) {

		User user = (User) getRequestAttribute("ikep.user");
		try {
			// 권한 체크
			// if(!isAdmin){
			// throw new IKEP4AjaxException("Access Error", null);
			// }

			workspaceTypeService.updateWorkspaceTypeOrder(typeIds,
					user.getPortalId());

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}

		return "success";
	}

	/**
	 * 워크스페이스 유형 임시 삭제
	 * 
	 * @param categoryId
	 * @param categoryName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteWorkspaceType.do", method = RequestMethod.GET)
	public String deleteWorkspaceType(@RequestParam("typeId") String typeId,
			SessionStatus status) {
		// 세션 객체 가지고 오기
		// User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		// for (int i = 0; i < typeIds.size(); i++) {
		WorkspaceType workspaceType = new WorkspaceType();
		workspaceType.setPortalId(portal.getPortalId());
		workspaceType.setTypeId(typeId);

		workspaceTypeService.logicalDelete(workspaceType);
		// }
		status.setComplete();
		return "redirect:/collpack/collaboration/admin/workspaceType/listWorkspaceTypeView.do";
	}

}
