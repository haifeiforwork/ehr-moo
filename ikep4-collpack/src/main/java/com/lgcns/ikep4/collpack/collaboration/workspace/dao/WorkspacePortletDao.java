/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Workspace 포틀릿 Dao
 *
 * @author 김종철
 * @version $Id: WorkspacePortletDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WorkspacePortletDao extends GenericDao<WorkspacePortlet, String> {

	/**
	 * Workspace 에 등록된 포틀릿 전체 목록
	 * @param workspacePortlet
	 * @return
	 */
	public List<WorkspacePortlet> listAllPortlet(WorkspacePortlet workspacePortlet);
	/**
	 * Workspace 에 등록된 포틀릿 갯수
	 * @param workspacePortlet
	 * @return
	 */
	public Integer countAllPortlet(WorkspacePortlet workspacePortlet);
	/**
	 * Workspace 에 등록 가능한 포틀릿 전체 목록 ( 게시판 포함)
	 * @param workspaceId
	 * @return
	 */
	public List<WorkspacePortlet> listAllWorkspacePortlet(String workspaceId);

	/**
	 *  Workspace 에 등록 가능한 포틀릿 전체 갯수 ( 게시판 포함)
	 * @param workspaceId
	 * @return
	 */
	public Integer countAllWorkspacePortlet(String workspaceId);
}
