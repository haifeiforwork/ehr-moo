/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 김종철
 * @version $Id: WorkspacePortletLayoutDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WorkspacePortletLayoutDao extends GenericDao<WorkspacePortletLayout, String> {

	/**
	 * Workspace에서 설정한 포틀릿 목록
	 * @param workspaceId
	 * @return
	 */
	public List<WorkspacePortletLayout> listByWorkspace(String workspaceId);
	/**
	 * Workspace에서 설정한 포틀릿 목록
	 * @param workspaceId
	 * @return
	 */
	public List<WorkspacePortletLayout> listLayoutByWorkspace(String workspaceId);
	/**
	 * Workspace에서 설정한 포틀릿 건수 조회 
	 * @param workspaceId
	 * @return
	 */
	public Integer countByWorkspace(String workspaceId);
	
	/**
	 * 해당 Workspace에서 포틀릿 레이아웃 ID 에 해당 되는 값 삭제 
	 * @param portletLayoutId
	 */
	public void physicalDelete(String portletLayoutId);
	
	/**
	 * 해당 Workspace에서 전체 포틀릿 레이아웃을 비운다.
	 * @param workspaceId
	 */
	public void physicalDeleteByWorkspaceId(String workspaceId);
	
}
