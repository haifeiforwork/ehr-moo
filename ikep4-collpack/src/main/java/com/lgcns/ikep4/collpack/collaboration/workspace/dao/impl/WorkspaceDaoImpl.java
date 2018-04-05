/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspaceDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;


/**
 * Workspace Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceDaoImpl.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Repository("workspaceDao")
public class WorkspaceDaoImpl extends GenericDaoSqlmap<Workspace, String> implements WorkspaceDao {

	private static final String NAMESPACE = "collpack.collaboration.workspace.dao.Workspace.";

	/**
	 * 검색조건에 의한 workspace 목록
	 */
	public List<Workspace> listBySearchCondition(WorkspaceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	/**
	 * 검색조건에 의한 workspace 갯수
	 */
	public Integer countBySearchCondition(WorkspaceSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	public List<Workspace> listBySearchConditionPersonal(WorkspaceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionPersonal", searchCondition);
	}

	/**
	 * 사용자 WS 목록 갯수
	 */
	public Integer countBySearchConditionPersonal(WorkspaceSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionPersonal", searchCondition);
	}

	/**
	 * 타입별 WS 목록
	 */
	public List<Workspace> listWorkspaceByType(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listWorkspaceByType", map);
	}

	/**
	 * 카테고리별 WS 목록
	 */
	public List<Workspace> listWorkspaceByCategory(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listWorkspaceByCategory", map);
	}

	/**
	 * 검색 WS 목록
	 */
	public List<Workspace> listWorkspaceBySearch(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listWorkspaceBySearch", map);
	}

	/**
	 * 나의 WS 목록
	 */
	public List<Workspace> listMyCollaboration(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listMyCollaboration", map);
	}
	
	public List<Workspace> listMyScheduleCollaboration(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listMyScheduleCollaboration", map);
	}
	
	/**
	 * 나의 WS 목록 for Mobile
	 */
	public List<Workspace> listMyCollaborationForMobile(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listMyCollaborationForMobile", map);
	}
	
	/**
	 * 나의 WS 목록 for Mobile Planner
	 */
	public List<Workspace> listPlannerTeamForMobile(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listPlannerTeamForMobile", map);
	}

	/**
	 * 신규 WS 목록
	 */
	public List<Workspace> listNewCollaboration(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listNewCollaboration", map);
	}

	/**
	 * 랜덤 WS 목록
	 */
	public Workspace randomWorkspace(Map<String, String> map) {
		// return this.sqlSelectForList(NAMESPACE + "randomWorkspace", map);
		return (Workspace) sqlSelectForObject(NAMESPACE + "randomWorkspace", map);
	}

	/**
	 * 랜덤 WS 최대치를 구하기
	 */
	public Integer countRandomWorkspace(String portalId) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countRandomWorkspace", portalId);
	}

	/**
	 * 운영진 이상 WS 목록
	 */
	public List<Workspace> listWorkspaceManager(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listWorkspaceManager", map);
	}

	/**
	 * 팀 WS 하위 갯수
	 */
	public Integer countBySearchConditionGroupHierarchy(WorkspaceSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionGroupHierarchy", searchCondition);
	}

	/**
	 * 팀 WS 하위 목록
	 */
	public List<Workspace> listBySearchConditionGroupHierarchy(WorkspaceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionGroupHierarchy", searchCondition);
	}

	/**
	 * workspace/부서 미개설부서 리스트
	 */
	public List<Workspace> listBySearchConditionGroup(WorkspaceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionGroup", searchCondition);
	}

	/**
	 * workspace/부서 폐쇄대기 리스트
	 */
	public List<Workspace> listBySearchConditionCloseGroup(WorkspaceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionCloseGroup", searchCondition);
	}

	/**
	 * workspace/부서 조직원정보
	 */
	public List<Workspace> listGroupMembers(String groupId) {

		return sqlSelectForList(NAMESPACE + "listGroupMembers", groupId);
	}

	/**
	 * 타입별 WS 갯수
	 */
	public List<Workspace> countWorkspaceType(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "countWorkspaceByType", map);
	}

	/**
	 * 타입별 WS 갯수 - 그룹
	 */
	public List<Workspace> countWorkspaceTypeOrg(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "countWorkspaceByTeam", map);
	}

	/**
	 * workspace/부서 미개설부서 리스트 카운트
	 */
	public Integer countBySearchConditionGroup(WorkspaceSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionGroup", searchCondition);
	}

	/**
	 * workspace/부서 폐쇄대기 카운트
	 */
	public Integer countBySearchConditionCloseGroup(WorkspaceSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionCloseGroup", searchCondition);
	}

	/**
	 * workspace 생성
	 */
	public String create(Workspace object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getWorkspaceId();
	}

	/**
	 * Workspace 방문 기록 등록
	 */
	public void createWorkspaceVisit(Map<String, String> map) {
		sqlInsert(NAMESPACE + "createWorkspaceVisit", map);
	}

	/**
	 * Workspace 방문 기록 존재유무 - 일별
	 */
	public boolean existsWorkspaceVisit(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "existsWorkspaceVisit", map);
		return count > 0;
	}

	@Deprecated
	public Workspace get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * workspace 정보 조회
	 */
	public Workspace get(Workspace object) {
		return (Workspace) sqlSelectForObject(NAMESPACE + "get", object);

	}

	/**
	 * workspace 정보 조회
	 */
	public Workspace getWorkspace(String workspaceId) {
		return (Workspace) sqlSelectForObject(NAMESPACE + "getWorkspace", workspaceId);

	}

	/**
	 * workspace 디폴트 정보 조회
	 */
	public Workspace getDefaultWorkspace(Map<String, String> map) {
		return (Workspace) sqlSelectForObject(NAMESPACE + "getDefaultWorkspace", map);
	}

	/**
	 * workspace/부서 조직도 정보
	 */
	public List<Workspace> getOrgGroup(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "selectOrgGroup", map);
	}

	/**
	 * 중복된 Workspace 이름 검색
	 */
	public boolean checkWorkspaceName(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "checkWorkspaceName", map);
		return count < 1;
	}

	/**
	 * 중복된 Workspace Team Id
	 * 
	 * @param teamId
	 * @return
	 */
	public String checkWorkspaceTeam(String teamId) {
		return (String) sqlSelectForObject(NAMESPACE + "checkWorkspaceTeam", teamId);

	}

	@Deprecated
	public boolean exists(String workspaceId) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * workspace 존재유무
	 */
	public boolean exists(Workspace object) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", object);
		return count > 0;
	}

	/**
	 * workspace 정보 수정
	 */
	public void update(Workspace object) {
		this.sqlUpdate(NAMESPACE + "update", object);
	}

	/**
	 * Workspace 소개글 수정
	 */
	public void updateIntro(Workspace object) {
		this.sqlUpdate(NAMESPACE + "updateIntro", object);
	}

	/**
	 * workspace 상태 변경(개설,폐쇄신청,폐쇄,개설반려)
	 */
	public void updateStatus(Workspace object) {
		this.sqlUpdate(NAMESPACE + "updateStatus", object);

	}

	/**
	 * workspace 물리적 삭제
	 */
	public void physicalDelete(String workspaceId) {
		sqlDelete(NAMESPACE + "physicalDelete", workspaceId);

	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 폐쇄된 WS 목록 구하기 - 배치 ( WS 삭제 )
	 */
	public List<Workspace> listAllWorkspaceDelete() {
		return this.sqlSelectForList(NAMESPACE + "listAllWorkspaceDelete");
	}

	/**
	 * 미개설된 팀 Workspace 생성 -배치
	 */
	public void syncTeamWorkspace(String userId) {
		sqlSelectForObject(NAMESPACE + "syncTeamWorkspace", userId);
	}

	/**
	 * 신규 사용자 팀 Workspace 회원 등록 - 배치
	 */
	public void syncUserWorkspace(String userId) {
		sqlSelectForObject(NAMESPACE + "syncUserWorkspace", userId);
	}

	/**
	 * 해당 Workspace 영구 삭제
	 */
	public void deleteWorkspaceBatch(String workspaceId) {
		sqlSelectForObject(NAMESPACE + "deleteWorkspaceBatch", workspaceId);
	}

	/**
	 * 하위 그룹 목록
	 */
	public List<Workspace> listGroupHierarchy(String groupId) {
		return this.sqlSelectForList(NAMESPACE + "listGroupHierarchy", groupId);
	}

	public List<Workspace> getWorkspaceImageFile(WorkspaceSearchCondition workspaceSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWorkspaceImageFile", workspaceSearchCondition);
	}

	public List<Workspace> getPresentCollMenuList(String userId) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getPresentCollMenuList", userId);
	}

	public void deleteCollMenuList(String userId) {
		// TODO Auto-generated method stub
		this.sqlDelete(NAMESPACE + "deleteCollMenuList", userId);
	}

	public void insertCollMenuList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		this.sqlInsert(NAMESPACE + "insertCollMenuList", param);
	}

	public List<Workspace> getPresentPlannerMenuList(String userId) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getPresentPlannerMenuList", userId);
	}

	public void deletePlannerMenuList(String userId) {
		// TODO Auto-generated method stub
		this.sqlDelete(NAMESPACE + "deletePlannerMenuList", userId);
	}

	public void insertPlannerMenuList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		this.sqlInsert(NAMESPACE + "insertPlannerMenuList", param);
	}
	public List<Workspace> getTeamPlannerMenuList(String userId) {
		// TODO Auto-generated method stub
		return this.sqlSelectForList(NAMESPACE + "getTeamPlannerMenuList", userId);
	}
	
	public void deleteMovePlannerMenuList(Map<String, String> param ) {
		// TODO Auto-generated method stub
		this.sqlDelete(NAMESPACE + "deleteMovePlannerMenuList", param);
	}
	
	public void insertMovePlannerMenuList(Map<String, String> param) {
		// TODO Auto-generated method stub
		this.sqlInsert(NAMESPACE + "insertMovePlannerMenuList", param);
	}
	
	public String myTeamWorkspace(String userId) {
		return (String) sqlSelectForObject(NAMESPACE + "myTeamWorkspace", userId);

	}
}
