/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.admin.dao.WorkspaceTypeDao;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;



/**
 * 카테고리 Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceTypeDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("workspaceTypeDao")
public class WorkspaceTypeDaoImpl extends GenericDaoSqlmap<WorkspaceType, String> implements WorkspaceTypeDao {

	private static final String NAMESPACE = "collpack.collaboration.admin.dao.WorkspaceType.";

	/**
	 * 유형 전체 조회
	 */
	public List<WorkspaceType> listWorkspaceTypeAll(String portalId) {
		return sqlSelectForList(NAMESPACE + "listWorkspaceTypeAll", portalId);
	}

	/**
	 * 유형별 워크스페이스 갯수
	 */
	public List<WorkspaceType> countWorkspaceByType(String portalId) {
		return sqlSelectForList(NAMESPACE + "countWorkspaceByType", portalId);
	}

	/**
	 * 유형별 워크스페이스 목록
	 */
	public List<WorkspaceType> listWorkspaceType(String portalId) {
		return sqlSelectForList(NAMESPACE + "listWorkspaceType", portalId);
	}

	/**
	 * 유형 등록
	 */
	public String create(WorkspaceType object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getTypeId();
	}

	@Deprecated
	public WorkspaceType get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 유형조회
	 */
	public WorkspaceType get(WorkspaceType object) {
		return (WorkspaceType) sqlSelectForObject(NAMESPACE + "get", object);
	}

	/**
	 * 유형이름
	 */
	public String getTypeName(WorkspaceType object) {
		return (String) sqlSelectForObject(NAMESPACE + "getTypeName", object);
	}

	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 유형 존재유무 확인
	 */
	public boolean exists(WorkspaceType object) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", object);
		return count > 0;
	}
	/**
	 * 유형 수정
	 */
	public void update(WorkspaceType object) {
		sqlUpdate(NAMESPACE + "update", object);

	}

	/** 
	 * 유형 순서 변경
	 */
	public void updateWorkspaceTypeOrder(WorkspaceType object) {
		this.sqlUpdate(NAMESPACE + "updateWorkspaceTypeOrder", object);

	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 유형 임시 삭제
	 */
	public void logicalDelete(WorkspaceType object) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", object);

	}

}
