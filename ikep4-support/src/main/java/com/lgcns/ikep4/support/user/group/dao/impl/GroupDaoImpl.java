/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.model.GroupDetail;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupDaoImpl.java 18238 2012-04-24 05:36:54Z yu_hs $
 */
@Repository("groupDao")
public class GroupDaoImpl extends GenericDaoSqlmap<Group, String> implements GroupDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectOrgGroupByGroupTypeId
	 * (com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public List<Group> selectOrgGroupByGroupTypeId(Group group) {

		return sqlSelectForList("support.user.group.dao.Group.selectOrgGroupByGroupTypeId", group);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectUserInGroup(java
	 * .lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public List selectUserInGroup(String groupId) {

		return sqlSelectForList("support.user.group.dao.Group.selectUserInGroup", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#addUserToGroup(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void addUserToGroup(User user) {

		sqlInsert("support.user.group.dao.Group.addUserToGroup", user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateUserGroup(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateUserGroup(User user) {

		sqlUpdate("support.user.group.dao.Group.updateUserGroup", user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#removeGroupFromUserGroup(java.lang.String)
	 */
	public void removeGroupFromUserGroup(String groupId) {

		sqlDelete("support.user.group.dao.Group.removeGroupFromUserGroup", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#deleteGroupFromRole(java.lang.String)
	 */
	public void deleteGroupFromRole(String groupId) {

		sqlDelete("support.user.group.dao.Group.deleteGroupFromRole", groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#deleteGroupFromSysPermission(java.lang.String)
	 */
	public void deleteGroupFromSysPermission(String groupId) {

		sqlDelete("support.user.group.dao.Group.deleteGroupFromSysPermission", groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#deleteGroupFromConPermission(java.lang.String)
	 */
	public void deleteGroupFromConPermission(String groupId) {

		sqlDelete("support.user.group.dao.Group.deleteGroupFromConPermission", groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#removeUserFromGroup(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void moveUserToParentGroup(User user) {

		sqlUpdate("support.user.group.dao.Group.moveUserToParentGroup", user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#selectChild(java.lang.String)
	 */
	public List<Group> selectChild(String groupId) {

		return (List<Group>) sqlSelectForList("support.user.group.dao.Group.selectChild", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#removeChild(java.lang.String)
	 */
	public void removeChild(String groupId) {

		sqlUpdate("support.user.group.dao.Group.removeChild", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#selectGroupByGroupType(com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public List<Group> selectGroupByGroupType(Group group) {

		return sqlSelectForList("support.user.group.dao.Group.selectOrgGroup", group);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#updateChildGroupCount
	 * (com.lgcns.ikep4.support.user.group.model.Group, boolean)
	 */
	public void updateChildGroupCount(String parentGroupId, String plusMinusFlag) {

		if (plusMinusFlag.equalsIgnoreCase("plus")) {
			sqlUpdate("support.user.group.dao.Group.addChildCount", parentGroupId);
		} else {
			sqlUpdate("support.user.group.dao.Group.deleteChildCount", parentGroupId);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.group.dao.Group.getMaxSortOrder");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateMove(com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public void updateMove(Group group) {

		sqlUpdate("support.user.group.dao.Group.updateMove", group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateSortOrder(java.lang.String)
	 */
	public void updateSortOrder(String prevSortOrder) {

		sqlUpdate("support.user.group.dao.Group.updateSortOrder", prevSortOrder);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#goUp(java.util.Map)
	 */
	public Group goUp(Map<String,String> map) {

		return (Group) sqlSelectForObject("support.user.group.dao.Group.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#goDown(java.util.Map)
	 */
	public Group goDown(Map<String,String> map) {

		return (Group) sqlSelectForObject("support.user.group.dao.Group.selectGoDown", map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectUserRootGroup(java
	 * .util.Map)
	 */
	public Group selectUserRootGroup(Map<String, Object> map) {
		return (Group) sqlSelectForObject("support.user.group.dao.Group.selectUserRootGroup", map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#updateChildGroupCount
	 * (java.util.Map)
	 */
	public List<Group> selectUserGroupWithHierarchy(Map<String, Object> map) {
		return sqlSelectForList("support.user.group.dao.Group.selectUserGroupWithHierarchy", map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#selectUserGroup2(java.util.Map)
	 */
	public List<Group> selectUserGroup(Map<String, Object> map) {
		return sqlSelectForList("support.user.group.dao.Group.selectUserGroup", map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#selectAll(java.lang.String)
	 */
	public List<Group> selectAll(String groupTypeId) {

		return sqlSelectForList("support.user.group.dao.Group.selectAll", groupTypeId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Group get(String id) {

		return (Group) sqlSelectForObject("support.user.group.dao.Group.select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Group group) {

		return (String) sqlInsert("support.user.group.dao.Group.insert", group);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Group group) {

		sqlUpdate("support.user.group.dao.Group.update", group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String groupId) {

		sqlDelete("support.user.group.dao.Group.delete", groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		String count = (String) sqlSelectForObject("support.user.group.dao.Group.checkId", id);

		return !count.equals("0");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateTeamName(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateTeamName(User user) {
		
		sqlUpdate("support.user.group.dao.Group.updateTeamName", user);
	}
	
	public String selectParentGroupId(String groupId) {
		
		return (String) sqlSelectForObject("support.user.group.dao.Group.selectParentGroupId", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectOrgGroup(jjava.
	 * lang.Object)
	 */
	public List<Group> selectOrgGroup(Group group) {
		return sqlSelectForList("support.user.group.dao.Group.selectOrgGroup", group);
	}
	
	public List<Group> selectOrgGroupSp(Group group) {
		return sqlSelectForList("support.user.group.dao.Group.selectOrgGroupSp", group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectCollaborationGroup
	 * (java.lang.String)
	 */
	public List<Group> selectCollaborationGroup(String groupId) {
		return sqlSelectForList("support.user.group.dao.Group.selectCollaborationGroup", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectSnsGroup(java.lang
	 * .String)
	 */
	public List<Group> selectSnsGroup(String groupId) {
		return sqlSelectForList("support.user.group.dao.Group.selectSnsGroup", groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.dao.GroupDao#selectGroupSearch(java
	 * .util.Map)
	 */
	public List<Group> selectGroupSearch(Map<String, Object> map) {
		return sqlSelectForList("support.user.group.dao.Group.selectGroupSearch", map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#selectAll(com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public List<Group> selectAll(UserSearchCondition searchCondition) {

		return sqlSelectForList("support.user.group.dao.Group.selectAllBySearchCondition", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#countBySearchCondition(com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public Integer countBySearchCondition(UserSearchCondition searchCondition) {
		return (Integer) this
				.sqlSelectForObject("support.user.group.dao.Group.countBySearchCondition", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateFullPath(com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public void updateFullPath(Group group) {

		sqlUpdate("support.user.group.dao.Group.updateFullPath", group);
	}

	public List<Group> selectUserGroupOther(Map<String, Object> map) {
		return sqlSelectForList("support.user.group.dao.Group.selectUserGroupOther", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateLeaderInfo(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateLeaderInfo(User user) {
		
		sqlUpdate("support.user.group.dao.Group.updateLeaderInfo", user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateUserTeamName(java.util.Map)
	 */
	public void updateUserTeamName(Map<String,String> groupInfo) {
		
		sqlUpdate("support.user.group.dao.Group.updateUserTeamName", groupInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#updateUserRepresentTeamName(java.util.Map)
	 */
	public void updateUserRepresentTeamName(Map<String,String> groupInfo) {
		
		sqlUpdate("support.user.group.dao.Group.updateUserRepresentTeamName", groupInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupDao#selectUserInfoInGroup(java.util.Map)
	 */
	public User selectUserInfoInGroup(Map<String,String> userInfo) {
		
		return (User) sqlSelectForObject("support.user.group.dao.Group.selectUserInfoInGroup", userInfo);
	}
	
	/*
	 * 
	 */
	public String selectGroupFullPath(Map<String, Object> userInfo) {
		return (String) sqlSelectForObject("support.user.group.dao.Group.selectGroupFullPath",userInfo);
	}
	
	/*
	 * 
	 */
	public String selectGroupFullPathByGroup(Map<String, Object> groupInfo) {
		return (String) sqlSelectForObject("support.user.group.dao.Group.selectGroupFullPathByGroup",groupInfo);
	}
	
	/**
	 * 설문대상 그룹 목록
	 */
	public List<Group> getTargetGroup(String surveyId) {
		return sqlSelectForList("support.user.group.dao.Group.getTargetGroup", surveyId);
	}
	
	/**
	 * 그룹 루트 갯수 구하기
	 */
	public int getRootGroupCount(Map<String, String> map) {
		return (Integer) sqlSelectForObject("support.user.group.dao.Group.getRootGroupCount", map);
	}
	
	public String getLeaderForGroup(String groupId) {
		return (String) sqlSelectForObject("support.user.group.dao.Group.selectLeaderForGroup",groupId);
	}
	
	public void updateEmptyGroupLeader(Group group) {
		sqlUpdate("support.user.group.dao.Group.updateEmptyLeader", group);
	}
	
	public void updateSapGroup(GroupDetail groupDetail){
		this.sqlInsert("support.user.group.dao.Group.updateSapGroup",  groupDetail);
	}
	
	public String updateEpTableFromTmpGroupTable()
	{
		return (String) sqlSelectForObject("support.user.group.dao.Group.updateEpGroupTabeFromTmpGroupTable", "");
	}
	
	public void deleteTmpGroup(String tmp){
		sqlDelete("support.user.group.dao.Group.deleteTmpGroup", tmp);
	}

}
