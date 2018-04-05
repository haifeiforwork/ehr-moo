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

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 개별 Workspace 포틀릿 Dao
 *
 * @author 김종철
 * @version $Id: WorkspacePortletDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("workspacePortletDao")
public class WorkspacePortletDaoImpl extends GenericDaoSqlmap<WorkspacePortlet, String> implements WorkspacePortletDao {

	private static final String NAMESPACE = "collpack.collaboration.workspace.dao.WorkspacePortlet.";
	
	/**
	 * 포틀릿 정보 조회
	 */
	public WorkspacePortlet get(String portletId) {
		return (WorkspacePortlet) sqlSelectForObject(NAMESPACE + "get", portletId);
	}

	/**
	 * 포틀릿 존재유무
	 */
	public boolean exists(String portletId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", portletId);
		return count > 0;
	}

	/**
	 * 포틀릿 생성
	 */
	public String create(WorkspacePortlet object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getPortletId();
	}

	/**
	 * 포틀릿 수정
	 */
	public void update(WorkspacePortlet object) {
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
	 * 전체 포틀릿 가능한 목록
	 */
	public List<WorkspacePortlet> listAllPortlet(WorkspacePortlet workspacePortlet) {
		return sqlSelectForList(NAMESPACE + "listAllPortlet", workspacePortlet);
	}

	/**
	 * 전체 포틀릿 가능 갯수
	 */
	public Integer countAllPortlet(WorkspacePortlet workspacePortlet) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countAllPortlet", workspacePortlet);
	}
	/**
	 * 전체 포틀릿 - 포틀릿 유형
	 */
	public List<WorkspacePortlet> listAllWorkspacePortlet(String workspaceId) {
		return sqlSelectForList(NAMESPACE + "listAllWorkspacePortlet", workspaceId);
	}

	/**
	 * 전체 포틀릿 갯수 - 포틀릿 유형
	 */	
	public Integer countAllWorkspacePortlet(String workspaceId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countAllWorkspacePortlet", workspaceId);
	}
	
}
