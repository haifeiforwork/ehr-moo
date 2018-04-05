/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;


/**
 * Workspace DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceDao.java 16487 2011-09-06 01:34:40Z giljae $
 */
public interface WorkspaceDao extends GenericDao<Workspace, String> {

	/**
	 * Workspace 목록
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	List<Workspace> listBySearchCondition(WorkspaceSearchCondition searchCondition);

	/**
	 * Personal Workspace 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<Workspace> listBySearchConditionPersonal(WorkspaceSearchCondition searchCondition);

	/**
	 * 동맹요청 목록
	 * 
	 * @param map
	 * @return
	 */
	List<Workspace> listWorkspaceByType(Map<String, String> map);

	/**
	 * 나의 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listMyCollaboration(Map<String, String> map);
	
	public List<Workspace> listMyScheduleCollaboration(Map<String, String> map);
	
	/**
	 * 나의 WS 목록 for Mobile Planner
	 */
	public List<Workspace> listPlannerTeamForMobile(Map<String, String> map);
	
	/**
	 * 나의 WS 목록 for Mobile
	 */
	public List<Workspace> listMyCollaborationForMobile(Map<String, String> map);

	/**
	 * 새로운 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listNewCollaboration(Map<String, String> map);

	/**
	 * 운영진 이상의 Workspace 목록 - 게시판 이관 대상 Workspace 조회
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listWorkspaceManager(Map<String, String> map);

	/**
	 * 카테고리별 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listWorkspaceByCategory(Map<String, String> map);

	/**
	 * 검색 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listWorkspaceBySearch(Map<String, String> map);

	/**
	 * 랜덤 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public Workspace randomWorkspace(Map<String, String> map);

	/**
	 * 랜덤 Workspace를 위한 최대 랜덤 값을 구하기 위한 갯수
	 * 
	 * @param portalId
	 * @return
	 */
	Integer countRandomWorkspace(String portalId);

	/**
	 * 하위 그룹 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchConditionGroupHierarchy(WorkspaceSearchCondition searchCondition);

	/**
	 * 하위 그룹 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Workspace> listBySearchConditionGroupHierarchy(WorkspaceSearchCondition searchCondition);

	/**
	 * workspace/부서 미개설리스트
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<Workspace> listBySearchConditionGroup(WorkspaceSearchCondition searchCondition);

	/**
	 * workspace/부서 조직원정보
	 * 
	 * @param groupId
	 * @return
	 */
	List<Workspace> listGroupMembers(String groupId);

	/**
	 * workspace/부서 폐쇄대기 리스트
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<Workspace> listBySearchConditionCloseGroup(WorkspaceSearchCondition searchCondition);

	/**
	 * 타입별 Workspace 갯수
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> countWorkspaceType(Map<String, String> map);

	/**
	 * 그룹 Workspace 갯수
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> countWorkspaceTypeOrg(Map<String, String> map);

	/**
	 * workspace/부서 미개설리스트 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionGroup(WorkspaceSearchCondition searchCondition);

	/**
	 * workspace/부서 폐쇄대기 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionCloseGroup(WorkspaceSearchCondition searchCondition);

	/**
	 * Workspace Count
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(WorkspaceSearchCondition searchCondition);

	/**
	 * 사용자가 가입한 WS/ 나의 WS 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionPersonal(WorkspaceSearchCondition searchCondition);

	/**
	 * Workspace 정보 조회
	 * 
	 * @param object
	 * @return
	 */
	public Workspace get(Workspace object);

	/**
	 * Workspace 정보 조회
	 * 
	 * @param workspaceId
	 * @return
	 */
	public Workspace getWorkspace(String workspaceId);

	/**
	 * 디폴트 Workspace 조회
	 * 
	 * @param map
	 * @return
	 */
	public Workspace getDefaultWorkspace(Map<String, String> map);

	/**
	 * workspace/부서 조직도 정보
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Workspace> getOrgGroup(Map<String, String> map);

	/**
	 * Workspace 명 중복여부 체크
	 * 
	 * @param map
	 * @return
	 */
	public boolean checkWorkspaceName(Map<String, String> map);

	/**
	 * Workspace Team ID 중복여부 체크
	 * 
	 * @param teamId
	 * @return
	 */
	public String checkWorkspaceTeam(String teamId);

	/**
	 * 방문 기록 정보 등록
	 * 
	 * @param map
	 */
	public void createWorkspaceVisit(Map<String, String> map);

	/**
	 * 1일간 방문 여부 조회
	 * 
	 * @param map
	 * @return
	 */
	public boolean existsWorkspaceVisit(Map<String, String> map);

	/**
	 * workspace 존재유무
	 * 
	 * @param object
	 * @return
	 */
	public boolean exists(Workspace object);

	/**
	 * Workspace 상태 변경 ( 개설승인,폐쇄신청,폐쇄,개설반려 )
	 * 
	 * @param workspace
	 */
	public void updateStatus(Workspace object);

	/**
	 * 소개 정보 수정
	 * 
	 * @param object
	 */
	public void updateIntro(Workspace object);

	/**
	 * Workspace 영구 삭제처리
	 * 
	 * @param workspaceId
	 */
	public void physicalDelete(String workspaceId);

	/**
	 * 삭제된 Workspace 목록
	 * 
	 * @return
	 */
	public List<Workspace> listAllWorkspaceDelete();

	/**
	 * 해당 Workspace 의 방문 기록 삭제
	 * 
	 * @param workspaceId
	 */
	// public void physicalDeleteWorkspaceVisit(String workspaceId);

	/**
	 * 팀 Workspace 개설 배치
	 * 
	 * @param userId
	 */
	public void syncTeamWorkspace(String userId);

	/**
	 * 신규 사용자 팀 회원에 등록을 위한 배치
	 * 
	 * @param userId
	 */
	public void syncUserWorkspace(String userId);

	/**
	 * 해당 Workspace 삭제 배치
	 * 
	 * @param workspaceId
	 */
	public void deleteWorkspaceBatch(String workspaceId);

	/**
	 * Workspace 메인 팀 Hierarchy 조회
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Workspace> listGroupHierarchy(String groupId);

	/**
	 * 최근 이미지
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	public List<Workspace> getWorkspaceImageFile(WorkspaceSearchCondition workspaceSearchCondition);
	
	public List<Workspace> getPresentCollMenuList(String userId);

	public void deleteCollMenuList(String userId);

	public void insertCollMenuList(Map<String, Object> param);
	
	public List<Workspace> getPresentPlannerMenuList(String userId);

	public void deletePlannerMenuList(String userId);

	public void insertPlannerMenuList(Map<String, Object> param);
	
	public List<Workspace> getTeamPlannerMenuList(String userId);
	
	public void deleteMovePlannerMenuList(Map<String, String> map);
	
	public void insertMovePlannerMenuList(Map<String, String> param);
	
	public String myTeamWorkspace(String userId);
	
}
