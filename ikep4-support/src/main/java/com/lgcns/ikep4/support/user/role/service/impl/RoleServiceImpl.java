/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.dao.RoleDao;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl extends GenericServiceImpl<Role, String> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private IdgenService idgenService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(Role role) {

		// 먼저 role 테이블에 role을 생성함
		String roleId = idgenService.getNextId();
		role.setRoleId(roleId);
		roleDao.create(role);

		// role_group/role_user 테이블 업데이트를 위한 리스트 추출
		List<Group> groupListForCreate = role.getGroupList();
		List<User> userListForCreate = role.getUserList();

		// groupList가 null이 아닌 경우 기존의 role_group 테이블에 매핑되어 있던 정보 삭제 후 생성
		if (groupListForCreate != null && groupListForCreate.size() != 0) {
			for (int i = 0; i < groupListForCreate.size(); i++) {
				Group tempGroup = groupListForCreate.get(i);
				tempGroup.setRoleId(role.getRoleId());

				roleDao.addRoleToGroup(tempGroup);
			}
		}

		if (userListForCreate != null && userListForCreate.size() != 0) {
			for (int i = 0; i < userListForCreate.size(); i++) {
				User tempUser = userListForCreate.get(i);
				tempUser.setRoleId(role.getRoleId());

				roleDao.addRoleToUser(tempUser);
			}
		}

		return roleId;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String id) {

		return roleDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public Role read(String roleId) {

		return roleDao.read(roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(Role role) {

		// 이전의 매핑정보를 삭제
		roleDao.deleteRoleFromGroup(role.getRoleId());

		// 이전의 사용자 매핑정보를 삭제
		roleDao.deleteRoleFromUser(role.getRoleId());

		// 3. 이전의 매핑정보를 삭제 한 뒤에 role 정보를 업데이트한다.
		roleDao.update(role);

		// 4. 새로운 롤 정보로 그룹/사용자 매핑 업데이트
		List<Group> newGroupList = role.getGroupList();
		List<User> newUserList = role.getUserList();

		// 새로운 그룹에 role을 매핑
		if (newGroupList != null) {
			for (int i = 0; i < newGroupList.size(); i++) {
				Group tempGroup = newGroupList.get(i);
				roleDao.addRoleToGroup(tempGroup);
			}
		}

		// 새로운 사용자에 role을 매핑
		if (newUserList != null) {
			for (int i = 0; i < newUserList.size(); i++) {
				User tempUser = newUserList.get(i);
				roleDao.addRoleToUser(tempUser);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.role.service.RoleService#remove(com.lgcns
	 * .ikep4.support.user.role.model.Role)
	 */
	public void remove(Role role) {

		String roleId = role.getRoleId();

		// role_id 관련 테이블에서 삭제
		roleDao.deleteRoleFromGroup(roleId);
		roleDao.deleteRoleFromUser(roleId);
		roleDao.deleteRoleFromSysPermission(roleId);
		roleDao.deleteRoleFromConPermission(roleId);

		roleDao.remove(roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.role.service.RoleService#list(com.lgcns.
	 * ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<Role> list(AdminSearchCondition searchCondition) {

		Integer count = roleDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Role> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Role>(searchCondition);

		} else {

			List<Role> roleList = roleDao.selectAllForList(searchCondition);

			List<Role> returnList = new ArrayList<Role>();
			for (Role role : roleList) {
				if (!searchCondition.getUserLocaleCode().equals("ko")) {
					role.setRoleName(role.getRoleEnglishName());
				}
				returnList.add(role);
			}

			searchResult = new SearchResult<Role>(returnList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.role.service.RoleService#selectRoleGroupList
	 * (java.lang.String)
	 */
	public List<Role> selectRoleGroupList(String roleId) {

		return roleDao.selectRoleGroupList(roleId);
	}
	
	public List<Role> selectByNameRoleGroupList(String roleName){
		return roleDao.selectByNameRoleGroupList(roleName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.role.service.RoleService#selectRoleUserList
	 * (java.lang.String)
	 */
	public List<Map<String, String>> selectRoleUserList(String roleId) {

		return roleDao.selectRoleUserList(roleId);
	}
	
	public List<Map<String,String>> selectByNameRoleUserList(String roleName){
		return roleDao.selectByNameRoleUserList(roleName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.role.service.RoleService#selectTypeId(java
	 * .lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<RoleType> selectTypeId(String localeCode) {

		return roleDao.selectTypeId(localeCode);
	}

}
