/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Workspace 에서 선택된 Portlet 값을 제공 하는 Service
 *
 * @author 김종철
 * @version $Id: WorkspacePortletLayoutService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface WorkspacePortletLayoutService extends GenericService<WorkspacePortletLayout, String> {

	/**
	 * 해당 Workspace 에 속한 Portlet 목록
	 * @param workspaceId
	 * @return
	 */
	public List<WorkspacePortletLayout> listLayoutByWorkspace(String workspaceId);
	/**
	 * 처음 Workspace 방문시 Default Portlet List 를 가지고 기본 Portlet Layout을 저장 한다. 
	 * @param workspaceId
	 */
	public void saveDefaultWorkspacePortletLayout(String workspaceId);
	
	/**
	 * 입력 받은Workspace Portlet Layout을 저장한다.
	 * @param workspacePortletLayoutList
	 */
	public void saveWorkspacePortletLayout(String workspaceId, List<WorkspacePortletLayout> workspacePortletLayoutList);
	
	/**
	 * TODO Workspace 포틀릿 정보 삭제
	 * @param workspaceId
	 */
	public void physicalDeleteByWorkspaceId(String workspaceId);
}
