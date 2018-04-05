/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.model.GroupDetail;


/**
 * 그룹 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupDao.java 18238 2012-04-24 05:36:54Z yu_hs $
 */
public interface GroupDao extends GenericDao<Group, String> {

	/**
	 * 트리를 그리기 위해 조직도에서 그룹 목록 그룹타입별로 조회
	 * 
	 * @param group 조회조건이 있는 그룹 객체
	 * @return
	 */
	public List<Group> selectOrgGroupByGroupTypeId(Group group);

	/**
	 * 그룹에 속해 있는 사용자 목록을 조회
	 * 
	 * @param id 조회할 그룹의 ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectUserInGroup(String groupId);

	/**
	 * 사용자를 그룹에 매핑
	 * 
	 * @param user 그룹에 추가할 사용자 객체
	 */
	public void addUserToGroup(User user);

	/**
	 * 그룹 수정 후에 USER_GROUP 테이블 업데이트
	 * 
	 * @param user 업데이트할 사용자 객체
	 */
	public void updateUserGroup(User user);

	/**
	 * USER_GROUP 테이블에서 해당 그룹-사용자 매핑 정보 삭제
	 * 
	 * @param groupId 매핑정보를 삭제할 그룹 ID
	 */
	public void removeGroupFromUserGroup(String groupId);

	/**
	 * ROLE_GROUP 테이블에서 해당 역할-사용자 매핑 정보 삭제
	 * 
	 * @param groupId 매핑정보를 삭제할 그룹 ID
	 */
	public void deleteGroupFromRole(String groupId);

	/**
	 * GROUP_SYS_PERMISSION 테이블에서 관련 매핑 정보 삭제
	 * 
	 * @param groupId 매핑정보를 삭제할 그룹 ID
	 */
	public void deleteGroupFromSysPermission(String groupId);

	/**
	 * GROUP_CON_PERMISSION 테이블에서 관련 매핑 정보 삭제
	 * 
	 * @param groupId 매핑정보를 삭제할 그룹 ID
	 */
	public void deleteGroupFromConPermission(String groupId);

	/**
	 * USER_GROUP 테이블에서 사용자 정보를 상위그룹으로 이동(그룹 삭제시)
	 * 
	 * @param user 이동할 사용자 정보
	 */
	public void moveUserToParentGroup(User user);

	/**
	 * 그룹 삭제시에 자식 그룹들을 이동하는 데에 필요함(현재는 자식이 있는 그룹은 삭제 못함)
	 * 
	 * @param groupId 부모 그룹 ID
	 * @return
	 */
	public List<Group> selectChild(String groupId);

	/**
	 * 그룹 삭제 후에 하위 그룹 업데이트
	 * 
	 * @param groupId 부모 그룹 ID
	 */
	public void removeChild(String groupId);

	/**
	 * 그룹 타입별로 그룹목록을 가져옴
	 * 
	 * @param group 부모 그룹 ID
	 * @return
	 */
	public List<Group> selectGroupByGroupType(Group group);

	/**
	 * 해당 그룹의 자식그룹 카운트를 업데이트함
	 * 
	 * @param parentGroupId 부모 그룹 ID
	 * @param plusMinusFlag 카운트를 증가/감소 시키기 위한 플래그
	 */
	public void updateChildGroupCount(String parentGroupId, String plusMinusFlag);

	/**
	 * 정렬순서 최대값 가져오기
	 * 
	 * @return
	 */
	public String getMaxSortOrder();

	/**
	 * 그룹 이동 후에 그룹 정보를 업데이트함(ParentGroup, childCount 등)
	 * 
	 * @param group 업데이트할 그룹 객체
	 */
	public void updateMove(Group group);

	/**
	 * 그룹 이동 후에 sortOrder 업데이트
	 * 
	 * @param group 업데이트할 그룹 객체
	 */
	public void updateSortOrder(String prevSortOrder);

	/**
	 * 그룹을 한단계 위로 이동(같은 레벨)
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public Group goUp(Map<String, String> map);

	/**
	 * 그룹을 한단계 아래로 이동(같은 레벨)
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public Group goDown(Map<String, String> map);

	/**
	 * 해당 사용자가 속한 최상위 그룹 조회
	 * 
	 * @param map 검색 조건
	 * @return
	 */
	public Group selectUserRootGroup(Map<String, Object> map);

	/**
	 * 해당 사용자가 속한 그룹 조회 (최상위 그룹 제외)
	 * 
	 * @param map 검색 조건
	 * @return
	 */
	public List<Group> selectUserGroupWithHierarchy(Map<String, Object> map);

	/**
	 * 해당 사용자가 속한 그룹 조회 (그룹ID, 그룹명만 가져옴)
	 * 
	 * @param map
	 * @return
	 */
	public List<Group> selectUserGroup(Map<String, Object> map);

	/**
	 * 그룹 목록을 그룹타입별로 가져옴
	 * 
	 * @param groupTypeId
	 * @return
	 */
	public List<Group> selectAll(String groupTypeId);

	/**
	 * 그룹 생성시에 그룹에 매핑되는 사용자의 Teamname을 업데이트
	 * 
	 * @param user
	 */
	public void updateTeamName(User user);

	/**
	 * 해당 그룹의 부모 그룹을 가져옴
	 * 
	 * @param id
	 * @return
	 */
	public String selectParentGroupId(String groupId);

	/* ===== 새로 추가하시는 경우 아래에 삽입하시기 바랍니다. ===== */

	/**
	 * 조직도에서 그룹 목록 가져오기 : 지정된 그룹에 대한 하위 그룹
	 * 
	 * @param group
	 * @return
	 */
	public List<Group> selectOrgGroup(Group group);
	
	public List<Group> selectOrgGroupSp(Group group);

	/**
	 * Collaboration에서 그룹 목록 가져오기 : 지정된 그룹에 대한 하위 그룹
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Group> selectCollaborationGroup(String groupId);

	/**
	 * Sns에서 그룹 목록 가져오기 : 지정된 그룹에 대한 하위 그룹
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Group> selectSnsGroup(String groupId);

	/**
	 * 조직도 부터 SNS 까지 모든 통합 그룹 검색 : 그룹명 검색
	 * 
	 * @param keyword
	 * @return
	 */
	public List<Group> selectGroupSearch(Map<String, Object> map);

	/**
	 * 모든 그룹 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<Group> selectAll(UserSearchCondition searchCondition);

	/**
	 * 모든 그룹 정보 갯수
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	Integer countBySearchCondition(UserSearchCondition searchCondition);

	/**
	 * FullPath 업데이트
	 * 
	 * @param group FullPath를 업데이트할 그룹 객체
	 */
	public void updateFullPath(Group group);

	/**
	 * 해당 사용자가 속한 겸직 그룹 조회 (최상위 그룹 제외)
	 * 
	 * @param map
	 * @return
	 */
	public List<Group> selectUserGroupOther(Map<String, Object> map);

	/**
	 * 그룹 생성/수정시 Leader를 선택하는 경우 해당 User의 Leader flag 업데이트
	 * 
	 * @param user
	 */
	public void updateLeaderInfo(User user);

	/**
	 * 그룹명 변경시 User 정보의 teamName을 업데이트하기 위한 정보
	 * 
	 * @param groupInfo 이전 그룹명과 변경된 그룹명을 가지고 있는 Map
	 */
	public void updateUserTeamName(Map<String, String> groupInfo);
	
	/**
	 * 그룹명 변경시 User 정보의 대표그룹의 이름으로 teamName을 업데이트하기 위한 정보
	 * 
	 * @param groupInfo 그룹아이디와 변경된 그룹명을 가지고 있는 Map
	 */
	public void updateUserRepresentTeamName(Map<String, String> groupInfo);

	/**
	 * 그룹 정보 변경시 User 정보에서 대표그룹 정보가 웹에서 받아오는 UserList에 포함되어있지 않기 때문에 본 Dao를 통해
	 * 기존의 대표그룹 정보를 가져와 새로 받아온 UserList에 넣어준다.
	 * 
	 * @param userInfo
	 */
	public User selectUserInfoInGroup(Map<String, String> userInfo);
	
	/**
	 * 사용자 그룹의 전체 경로 조회
	 * 
	 * @param userId
	 * @return groupFullPath
	 */
	public String selectGroupFullPath(Map<String, Object> userInfo);
	
	/**
	 * 그룹의 전체 경로 조회
	 * 
	 * @param userId
	 * @return groupFullPath
	 */
	public String selectGroupFullPathByGroup(Map<String, Object> groupInfo);
	
	/**
	 * 설문대상 그룹 목록
	 * @param surveyId
	 * @return List<Group>
	 */
	public List<Group> getTargetGroup(String surveyId);
	
	/**
	 * 그룹 루트 갯수 구하기
	 * @param map
	 * @return rootGroupCount
	 */
	public int getRootGroupCount(Map<String, String> map);
	
	/**
	 * 해당 그룹의 leader id 조회
	 * @param groupId
	 * @return userId
	 */
	public String getLeaderForGroup(String groupId);
	
	/**
	 * 해당 그룹의 리더 삭제
	 * @param group
	 */
	public void updateEmptyGroupLeader(Group group);
	
	/**
	 * Sap 에서 수신한 조직정보를 저장한다.
	 * @param groupDetail
	 */
	public void updateSapGroup(GroupDetail groupDetail);
	
	/**
	 * Sap에서 수신된 그룹 정보가 담긴 tmp 테이블에서 실 테이블로 전송한다
	 * @return 수행된 결과값
	 */
	public String updateEpTableFromTmpGroupTable();
	
	public void deleteTmpGroup(String tmp);
}
