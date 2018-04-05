/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.web;

import java.util.ArrayList;
import java.util.List;

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

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletLayoutService;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Workspace Layout Controller
 * 
 * @author 김종철
 * @version $id$
 */
@Controller
@RequestMapping(value = "/collpack/collaboration/workspacelayout")
@SessionAttributes("collaboration")
public class WorkspacePortletController extends BaseController {

	@Autowired
	private WorkspacePortletService workspacePortletService;

	@Autowired
	private WorkspacePortletLayoutService workspacePortletLayoutService;

	@Autowired
	private ACLService aclService;

	/**
	 * WS 전체관리자 여부 권한 확인
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin(
				WorkspaceConstants.ITEM_TYPE_NAME, user.getUserId());
		// isSystemAdmin = true;
		return isSystemAdmin;
	}

	/**
	 * Workspace 포틀릿 설정 페이지
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/layoutManageView.do", method = RequestMethod.GET)
	public ModelAndView layoutManageView(
			@RequestParam(value = "workspaceId", required = false) String workspaceId) {

		ModelAndView mav = new ModelAndView(
				"collpack/collaboration/workspacelayout/layoutManageView");

		// 포틀릿 전체 리스트
		List<WorkspacePortlet> workspacePortletList = workspacePortletService
				.listAllWorkspacePortlet(workspaceId);
		mav.addObject("workspacePortletList", workspacePortletList);

		// 내가 저장하고 있는 Portlet Layout
		List<WorkspacePortletLayout> workspacePortletLayoutList = workspacePortletLayoutService
				.listLayoutByWorkspace(workspaceId);
		mav.addObject("workspacePortletLayoutList", workspacePortletLayoutList);

		mav.addObject("workspaceId", workspaceId);

		return mav;
	}

	/**
	 * 레이아웃 정보 저장
	 * 
	 * @param workspaceId
	 * @param portletId
	 * @param rowIndex
	 */
	@RequestMapping(value = "/saveLayoutManage.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void saveLayoutManage(
			@RequestParam(value = "workspaceId", required = false) String workspaceId,
			@RequestParam(value = "portletId", required = false) String[] portletId,
			@RequestParam(value = "rowIndex", required = false) String[] rowIndex) {

		try {

			List<WorkspacePortletLayout> workspacePortletLayoutList = new ArrayList<WorkspacePortletLayout>();

			int colIndex1 = 0;
			if (portletId != null) {
				for (int i = 0; i < portletId.length; i++) {

					WorkspacePortletLayout workspacePortletLayout = new WorkspacePortletLayout();

					List<String> portletIds = StringUtil.getTokens(
							portletId[i], "@");// portletId/boardId

					workspacePortletLayout.setPortletId(portletIds.get(0));

					if (portletIds.size() > 1 && portletIds.get(1) != null
							&& !portletIds.get(1).equals("")) { // 게시판 포틀릿 확인
						workspacePortletLayout.setBoardId(portletIds.get(1));
						workspacePortletLayout.setIsBoardPortlet(1);
					} else {
						workspacePortletLayout.setIsBoardPortlet(0);
					}
					if (Integer.parseInt(rowIndex[i]) == 0) {
						colIndex1++;
					}
					workspacePortletLayout.setColIndex(colIndex1);

					workspacePortletLayout.setRowIndex(Integer
							.parseInt(rowIndex[i]) + 1); // index 를 1부터

					workspacePortletLayoutList.add(workspacePortletLayout);
				}
			}

			workspacePortletLayoutService.saveWorkspacePortletLayout(
					workspaceId, workspacePortletLayoutList);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);

		}

	}

}
