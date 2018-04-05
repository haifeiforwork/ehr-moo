/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.dao.RoleDao;
import com.lgcns.ikep4.support.user.role.model.Role;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoSqlmap<Role, String> implements RoleDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#selectAll()
	 */
	public List<Role> selectAll() {

		return sqlSelectForList("support.user.role.dao.Role.selectAll");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#selectAllForList(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<Role> selectAllForList(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.role.dao.Role.selectAllForList", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {
		
		return (Integer) sqlSelectForObject("support.user.role.dao.Role.selectCountForList", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Role role) {

		return (String) sqlInsert("support.user.role.dao.Role.insert", role);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#addRoleToGroup(com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public void addRoleToGroup(Group group) {
		
		sqlInsert("support.user.role.dao.Role.addRoleToGroup", group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#removeRoleFromGroup(com.lgcns.ikep4.support.user.role.model.Role)
	 */
	public void removeRoleFromGroup(Role role) {
		
		sqlDelete("support.user.role.dao.Role.removeRoleFromGroup", role);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#removeRoleFromUser(com.lgcns.ikep4.support.user.role.model.Role)
	 */
	public void removeRoleFromUser(Role role) {
		
		sqlDelete("support.user.role.dao.Role.removeRoleFromUser", role);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#deleteRoleFromGroup(java.lang.String)
	 */
	public void deleteRoleFromGroup(String roleId) {
		
		sqlDelete("support.user.role.dao.Role.deleteRoleFromGroup", roleId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#deleteRoleFromUser(java.lang.String)
	 */
	public void deleteRoleFromUser(String roleId) {
		
		sqlDelete("support.user.role.dao.Role.deleteRoleFromUser", roleId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#deleteRoleFromSysPermission(java.lang.String)
	 */
	public void deleteRoleFromSysPermission(String roleId) {

		sqlDelete("support.user.role.dao.Role.deleteRoleFromSysPermission", roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#deleteRoleFromConPermission(java.lang.String)
	 */
	public void deleteRoleFromConPermission(String roleId) {

		sqlDelete("support.user.role.dao.Role.deleteRoleFromConPermission", roleId);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#addRoleToUser(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void addRoleToUser(User user) {
		
		sqlInsert("support.user.role.dao.Role.addRoleToUser", user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {

		String count = (String) sqlSelectForObject("support.user.role.dao.Role.checkId", id);

		return !count.equals("0");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#read(java.lang.String)
	 */
	public Role read(String roleId) {

		return (Role) sqlSelectForObject("support.user.role.dao.Role.select", roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Role role) {

		sqlUpdate("support.user.role.dao.Role.update", role);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

		sqlDelete("support.user.role.dao.Role.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#selectTypeId(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectTypeId(String localeCode) {

		return sqlSelectForList("support.user.role.dao.Role.selectTypeId", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Role get(String roleId) {
		List groupRoleList = sqlSelectForList("support.user.role.dao.Role.selectRoleGroupList", roleId);
		List userRoleList = sqlSelectForList("support.user.role.dao.Role.selectRoleUserList", roleId);
		
		Role role = (Role) sqlSelectForObject("support.user.role.dao.Role.select", roleId);
		role.setGroupList(groupRoleList);
		role.setUserList(userRoleList);
		
		return role;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#selectRoleGroupList(java.lang.String)
	 */
	public List<Role> selectRoleGroupList(String roleId) {
		
		return sqlSelectForList("support.user.role.dao.Role.selectRoleGroupList", roleId);
	}
	
	public List<Role> selectByNameRoleGroupList(String roleName){
		return sqlSelectForList("support.user.role.dao.Role.selectByNameRoleGroupList", roleName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleDao#selectRoleUserList(java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,String>> selectRoleUserList(String roleId) {
		
		return (List) sqlSelectForListOfObject("support.user.role.dao.Role.selectRoleUserList", roleId);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,String>> selectByNameRoleUserList(String roleName){
		return (List) sqlSelectForListOfObject("support.user.role.dao.Role.selectByNameRoleUserList", roleName);
	}

	public List<Role> listRoleAll(String portalId) {
		return sqlSelectForList("support.user.role.dao.Role.listRoleAll", portalId);
	}

}