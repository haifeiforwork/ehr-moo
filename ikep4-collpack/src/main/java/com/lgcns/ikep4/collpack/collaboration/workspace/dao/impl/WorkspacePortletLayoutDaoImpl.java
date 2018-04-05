/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletLayoutDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 개별 Workspace 레이아웃 Dao
 *
 * @author 김종철
 * @version $Id: WorkspacePortletLayoutDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("workspacePortletLayoutDao")
public class WorkspacePortletLayoutDaoImpl extends GenericDaoSqlmap<WorkspacePortletLayout, String> implements WorkspacePortletLayoutDao {

	private static final String NAMESPACE = "collpack.collaboration.workspace.dao.WorkspacePortletLayout.";
	
	/**
	 * 포틀릿 레이아웃 조회
	 */
	public WorkspacePortletLayout get(String portletLayoutId) {
		return (WorkspacePortletLayout) sqlSelectForObject(NAMESPACE + "get", portletLayoutId);
	}

	/**
	 * 포틀릿 레이아웃 존재유무
	 */
	public boolean exists(String portletLayoutId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", portletLayoutId);
		return count > 0;
	}

	/**
	 * 포틀릿 레이아웃 등록
	 */
	public String create(WorkspacePortletLayout object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getPortletLayoutId();
	}

	/**
	 * 포틀릿 레이아웃 수정
	 */
	public void update(WorkspacePortletLayout object) {
		sqlUpdate(NAMESPACE + "update", object);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 포틀릿 레이아웃에 등록된 포틀릿 목록
	 */
	public List<WorkspacePortletLayout> listByWorkspace(String workspaceId) {
		return sqlSelectForList(NAMESPACE + "listByworkspace", workspaceId);
	}
	/**
	 * 포틀릿 레이아웃에 등록된 포틀릿 목록
	 */	
	public List<WorkspacePortletLayout> listLayoutByWorkspace(String workspaceId) {
		return sqlSelectForList(NAMESPACE + "listLayoutByWorkspace", workspaceId);
	}
	/**
	 * 포틀릿 레이아웃에 등록된 포틀릿 목록 갯수
	 */
	public Integer countByWorkspace(String workspaceId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByworkspace", workspaceId);
	}

	/**
	 * 포틀릿 삭제
	 */
	public void physicalDelete(String portletLayoutId) {
		sqlDelete(NAMESPACE + "physicalDelete", portletLayoutId); 

	}

	/**
	 * 포틀릿 삭제 - 해당 WS 전체
	 */
	public void physicalDeleteByWorkspaceId(String workspaceId) {
		sqlDelete(NAMESPACE + "physicalDeleteByWorkspaceId", workspaceId); 

	}

}
