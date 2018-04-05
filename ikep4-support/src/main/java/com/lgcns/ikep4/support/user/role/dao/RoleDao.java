/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleDao.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
public interface RoleDao extends GenericDao<Role, String> {

	/**
	 * 역할 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<Role> selectAllForList(AdminSearchCondition searchCondition);

	/**
	 * 역할 코드 조회 카운트
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public Integer selectCount(AdminSearchCondition searchCondition);

	/**
	 * 역할 정보 가져오기
	 * 
	 * @param roldId 정보를 가져올 역할 ID
	 * @return
	 */
	public Role read(String roldId);

	/**
	 * Role-Group 매핑 정보의 그룹목록을 불러옴
	 * 
	 * @param roleId 검색하고자 하는 역할 ID
	 * @return
	 */
	public List<Role> selectRoleGroupList(String roleId);
	
	public List<Role> selectByNameRoleGroupList(String roleName);

	/**
	 * Role-User 매핑 정보의 사용자 목록을 불러옴
	 * 
	 * @param roleId 검색하고자 하는 역할 ID
	 * @return
	 */
	public List<Map<String, String>> selectRoleUserList(String roleId);
	
	public List<Map<String,String>> selectByNameRoleUserList(String roleName);

	/**
	 * Role-Group 매핑정보 생성
	 * 
	 * @param group 역할을 부여할 그룹 정보
	 */
	public void addRoleToGroup(Group group);

	/**
	 * Role-User 매핑정보 생성
	 * 
	 * @param user 역할을 부여할 사용자 정보
	 */
	public void addRoleToUser(User user);

	/**
	 * Role-Group 매핑정보 삭제(Role과 Group ID가 일치하는 것만 삭제)
	 * 
	 * @param role 그룹에서 삭제할 역할 정보
	 */
	public void removeRoleFromGroup(Role role);

	/**
	 * Role-User 매핑정보 삭제(Role과 User ID가 일치하는 것만 삭제)
	 * 
	 * @param role 사용자에서 삭제할 역할 정보
	 */
	public void removeRoleFromUser(Role role);

	/**
	 * Role-Group 에서 해당 Role 매핑 정보 전체 삭제 (Role ID가 일치하는 것은 모두 삭제)
	 * 
	 * @param roleId 그룹 매핑정보를 삭제할 역할 ID
	 */
	public void deleteRoleFromGroup(String roleId);

	/**
	 * Role-User 에서 해당 Role 매핑 정보 전체 삭제 (Role ID가 일치하는 것은 모두 삭제)
	 * 
	 * @param roleId 사용자 매핑정보를 삭제할 역할 ID
	 */
	public void deleteRoleFromUser(String roleId);

	/**
	 * Role-sys-permission 매핑 정보 삭제
	 * 
	 * @param roleId 매핑정보를 삭제할 역할 ID
	 */
	public void deleteRoleFromSysPermission(String roleId);

	/**
	 * Role-con-permission 매핑 정보 삭제
	 * 
	 * @param roleId 매핑정보를 삭제할 역할 ID
	 */
	public void deleteRoleFromConPermission(String roleId);
	
	/**
	 * 역할타입 ID를 조회
	 * 
	 * @param localeCode 로케일코드
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectTypeId(String localeCode);
	
	/**
	 * 역할 목록 조회
	 * 
	 * @param portalId 포탈 ID
	 * @return
	 */
	public List<Role> listRoleAll(String portalId);
	
}
