/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Workspace Service
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceService.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Transactional
public interface WorkspaceService extends GenericService<Workspace, String> {

	/**
	 * workspace 목록
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	public SearchResult<Workspace> listBySearchCondition(WorkspaceSearchCondition searchCondition);

	/**
	 * 내가 가입한 WS 목록/ 나의 Workspace 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Workspace> listBySearchConditionPersonal(WorkspaceSearchCondition searchCondition);

	/**
	 * 팀 Workspace 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Workspace> listBySearchConditionGroupHierarchy(WorkspaceSearchCondition searchCondition);

	/**
	 * 랜덤 Workspace 목록 4건
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Workspace> randomWorkspace(String portalId);

	/**
	 * 타입별 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listWorkspaceByType(Map<String, String> map);

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
	 * 나의 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listMyCollaboration(Map<String, String> map);
	
	public List<Workspace> listMyScheduleCollaboration(Map<String, String> map);
	/**
	 * 나의 Workspace 목록 for Mobile
	 */
	public List<Workspace> listMyCollaborationForMobile(Map<String, String> map);

	/**
	 * 신규 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listNewCollaboration(Map<String, String> map);

	/**
	 * 나의 운영진 이상의 Workspace 목록 - 게시판 이관 대상 Workspace 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> listWorkspaceManager(Map<String, String> map);

	/**
	 * workspace 미개설 그룹정보
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Workspace> listBySearchConditionGroup(WorkspaceSearchCondition searchCondition);

	/**
	 * workspace 그룹 조직원 정보
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Workspace> listGroupMembers(String groupId);

	/**
	 * workspace 폐쇄대기 그룹정보
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Workspace> listBySearchConditionCloseGroup(WorkspaceSearchCondition searchCondition);

	/**
	 * 타입별 Workspace 갯수
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> countWorkspaceType(Map<String, String> map);

	/**
	 * 그룹 타입의 Workspace 갯수
	 * 
	 * @param map
	 * @return
	 */
	public List<Workspace> countWorkspaceTypeOrg(Map<String, String> map);

	/**
	 * workspace 생성
	 * 
	 * @param workspace
	 * @return
	 */
	public String createWorkspace(Workspace object, User user);

	/**
	 * workspace 생성
	 * 
	 * @param portalId
	 * @param user
	 * @param object
	 * @return
	 */
	public String createWorkspace(String portalId, User user, Workspace object);

	/**
	 * 팀 workspace 생성
	 * 
	 * @param user
	 * @param groupIds
	 */
	public void createOrgWorkspace(User user, List<String> groupIds);

	/**
	 * Workspace 방문 기록 등록
	 * 
	 * @param map
	 */
	public void createWorkspaceVisit(Map<String, String> map);

	/**
	 * workspace 조회
	 * 
	 * @param object
	 * @return
	 */
	public Workspace read(Workspace object);

	/**
	 * workspace 조회
	 * 
	 * @param workspaceId
	 * @return
	 */
	public Workspace getWorkspace(String workspaceId);

	/**
	 * 디폴트 workspace 조회
	 * 
	 * @param map
	 * @return
	 */
	public Workspace getDefaultWorkspace(Map<String, String> map);

	/**
	 * workspace OrgTreeNode
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Workspace> getOrgGroup(Map<String, String> map);

	/**
	 * workspace 중복여부 체크
	 * 
	 * @param workspaceId
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
	 * workspace 존재유무
	 * 
	 * @param object
	 * @return
	 */
	public boolean exists(Workspace object);

	/**
	 * Workspace 수정
	 * 
	 * @param workspace
	 * @param user
	 */
	public void updateWorkspace(Workspace workspace, User user);

	/**
	 * Workspace 정보 수정
	 * 
	 * @param workspace
	 * @param user
	 */
	public void updateWorkspaceInfo(Workspace workspace, User user);

	/**
	 * Workspace 소개정보 수정
	 * 
	 * @param workspace
	 * @param user
	 */
	public void updateWorkspaceIntro(Workspace workspace, User user);

	/**
	 * Workspace 상태정보 수정
	 * 
	 * @param workspaceIds
	 * @param workspaceStatus
	 * @param user
	 */
	public void updateWorkspaceStatus(List<String> workspaceIds, String workspaceStatus, User user);

	/**
	 * Workspace 상태정보 수정
	 * 
	 * @param portal
	 * @param user
	 * @param workspaceIds
	 * @param workspaceStatus
	 */
	public void updateWorkspaceStatus(Portal portal, User user, List<String> workspaceIds, String workspaceStatus);

	/**
	 * workspace 상태변경( 개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param workspace
	 */
	public void updateStatus(Workspace object);

	/**
	 * workspace 물리적 삭제
	 * 
	 * @param workspaceId
	 */
	public void physicalDelete(String workspaceId);

	/**
	 * workspace 물리적 삭제
	 * 
	 * @param workspaceIds
	 */
	public void physicalDelete(List<String> workspaceIds);

	/**
	 * Workspace 삭제된 목록 - 배치 프로그램
	 * 
	 * @return
	 */
	public List<Workspace> listAllWorkspaceDelete();

	/**
	 * 방문 기록 정보 삭제 - 미사용 ???
	 * 
	 * @param workspaceId
	 */
	// public void physicalDeleteWorkspaceVisit(String workspaceId);

	/**
	 * 미개설된 팀 Workspace 생성 배치작업
	 * 
	 * @param userId
	 */
	public void syncTeamWorkspace(String userId);

	/**
	 * 신규 사용자의 팀 Workspace 자동 등록 - 배치 작업
	 * 
	 * @param userId
	 */
	public void syncUserWorkspace(String userId);

	/**
	 * 해당 Workspace 삭제 - 배치 작업
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
	 * 배치 Job으로 삭제된 Workspace 영구 삭제
	 */
	public void batchDeleteWorkspace();

	/**
	 * 갤러리 이미지 조회
	 * 
	 * @param workspaceId
	 * @return
	 */
	public SearchResult<Workspace> getWorkspaceImageFile(WorkspaceSearchCondition workspaceSearchCondition);
	
	public SearchResult<Workspace> listBySearchConditionMobile(WorkspaceSearchCondition workspaceSearchCondition);
	
	public void updateCollMenuList(Map<String, Object> param);
	
	public SearchResult<Workspace> listBySearchConditionPlannerForMobile(WorkspaceSearchCondition workspaceSearchCondition);
	
	public void updatePlannerMenuList(Map<String, Object> param);
	
	public List<Workspace> getPresentPlannerMenuList(String userId);
	
	public String myTeamWorkspace(String userId);
	
}
