/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.model.GroupDetail;
import com.lgcns.ikep4.util.model.UserDetail;


/**
 * 그룹 관리 서비스
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupService.java 17883 2012-04-05 03:25:17Z arthes $
 */
@Transactional
public interface GroupService extends GenericService<Group, String> {

	/**
	 * 그룹 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<Group> list(UserSearchCondition searchCondition);

	/**
	 * 그룹 삭제
	 * 
	 * @param group 삭제할 그룹 정보
	 */
	public void delete(Group group);

	/**
	 * 그룹 이동 후에 SortOrder 업데이트
	 * 
	 * @param prevSortOrder 이동 전의 Sort Order
	 * @param orgParentGroupId 이동 전의 부모 그룹 ID
	 * @param currGroup 현재 그룹 정보
	 */
	public void updateGroupMove(String prevSortOrder, String orgParentGroupId, Group currGroup);

	/**
	 * 조직도에서 그룹 목록 그룹타입별로 가져오기
	 * 
	 * @param group
	 * @return
	 */
	public List<Group> selectOrgGroupByGroupTypeId(Group group);

	/**
	 * 조직도에서 그룹 목록 가져오기 : 지정된 그룹에 대한 하위 그룹
	 * 
	 * @param group 가져오는 그룹 목록의 최상단 그룹 정보
	 * @return
	 */
	public List<Group> selectOrgGroup(Group group);
	
	public List<Group> selectOrgGroupSp(Group group);

	/**
	 * Collaboration에서 그룹 목록 가져오기 : 지정된 그룹에 대한 하위 그룹
	 * 
	 * @param groupId 가져오는 그룹 목록의 최상단 그룹 ID
	 * @return
	 */
	public List<Group> selectCollaborationGroup(String groupId);

	/**
	 * Sns에서 그룹 목록 가져오기 : 지정된 그룹에 대한 하위 그룹
	 * 
	 * @param groupId 가져오는 그룹 목록의 최상단 그룹 ID
	 * @return
	 */
	public List<Group> selectSnsGroup(String groupId);

	/**
	 * 조직도 부터 SNS 까지 모든 통합 그룹 검색 : 그룹명 검색
	 * 
	 * @param localeCode
	 * @param keyword
	 * @return
	 */
	public List<Group> selectGroupSearch(String localeCode, String keyword);

	/**
	 * 해당 사용자가 속한 최상위 그룹 조회
	 * 
	 * @param map 조회 조건
	 * @return
	 */
	public Group selectUserRootGroup(Map<String, Object> map);

	/**
	 * 해당 사용자가 속한 그룹 조회(최상위 그룹 제외)
	 * 
	 * @param map 조회 조건
	 * @return
	 */
	public List<Group> selectUserGroupWithHierarchy(Map<String, Object> map);

	/**
	 * 해당 사용자가 속한 그룹 조회(그룹ID, 그룹명만 가져옴)
	 * 
	 * @param map
	 * @return
	 */
	public List<Group> selectUserGroup(Map<String, Object> map);

	/**
	 * 사용자를 그룹에서 제거
	 * 
	 * @param groupId 사용자가 제거될 그룹의 ID
	 */
	public void removeGroupFromUserGroup(String groupId);

	/**
	 * 정렬순서 최대값 가져오기
	 * 
	 * @return
	 */
	public String getMaxSortOrder();

	/**
	 * 그룹을 한단계 위로 이동(같은 레벨)
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goUp(Map<String, String> map);

	/**
	 * 그룹을 한단계 아래로 이동(같은 레벨)
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goDown(Map<String, String> map);
	
	/**
	 * 엑셀 업로드 시, 그룹정보 생성
	 * 
	 * @param group 생성될 그룹 정보
	 * @return groupId 생성될 그룹 아이디
	 */
	public String createForExcel(Group group);

	/**
	 * 엑셀 업로드 시, 그룹정보 수정
	 * 
	 * @param group 수정될 그룹 정보
	 */
	public void updateForExcel(Group group);

	/**
	 * 해당 사용자가 속한 겸직 그룹 조회 (최상위 그룹 제외)
	 * 
	 * @param map 조회 조건
	 * @return
	 */
	public List<Group> selectUserGroupOther(Map<String, Object> map);
	
	/**
	 * 해당 사용자의 대표그룹 전체 경로 조회
	 * 
	 * @param userId 사용자 아이디
	 * @return groupFullPath
	 */
	public String getGroupFullPath(Map<String, Object> userInfo);
	
	
	/**
	 * 선택그룹 전체 경로 조회
	 * 
	 * @param groupId 사용자 아이디
	 * @return groupFullPath
	 */
	public String getGroupFullPathByGroup(Map<String, Object> groupInfo);
	
	/**
	 * 그룹 루트 갯수 구하기
	 * @param map 파라미터값
	 * @return rootGroupCount
	 */
	public int getRootGroupCount(Map<String, String> map);
	
	/**
	 * Sap에서 수신된 조직 정보를 DB의 temp 테이블에 저장
	 * @param groupDetailList
	 */
	public void updateSapGroup(List<GroupDetail> groupDetailList);
	
	/**
	 * Sap에서 수신된 그룹 정보가 담긴 tmp 테이블에서 실 테이블로 전송한다
	 * @return 수행된 결과값
	 */
	public String updateEpTableFromTmpGroupTable();

}
