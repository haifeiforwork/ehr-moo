/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.model;

import java.util.ArrayList;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;


/**
 * ACL 접근 권한 베이스 모델
 * 
 * @author 주길재
 * @version $Id: ACLResourcePermission.java 17346 2012-02-28 08:43:22Z yruyo $
 */
public class ACLResourcePermission extends BaseObject {
	/**
	 * Use serialVersionUID science iKEP 4.0.0
	 */
	private static final long serialVersionUID = 715734577732674236L;

	/**
	 * 리소스 이름
	 */
	private String resourceName;

	/**
	 * 리소스에 대한 설명
	 */
	private String resourceDescription;

	/**
	 * 리소스 타입 이름
	 */
	private String className;

	/**
	 * 리소스의 오픈 여부<br/>
	 * 1 = 오픈<br/>
	 * 0 = 오픈안함
	 */
	private int open;

	/**
	 * 유저 아이디
	 */
	private String userId;

	/**
	 * 유저 이름
	 */
	private String userName;

	/**
	 * 권한 이름<br/>
	 * 입력 하지 않을 경우에는 리소스 이름을 사용한다.
	 */
	private String permissionName;

	/**
	 * 권한에 대한 설명<br/>
	 * 입력 하지 않을 경우에는 리소스 설명을 사용한다.
	 */
	private String permissionDescription;

	/**
	 * 리소스에 대한 오퍼레이션 이름
	 */
	private String operationName;

	/**
	 * 리소스의 권한을 할당할 사용자 아이디<br/>
	 * 값이 없을 경우에는 권한을 할당하지 않는 것으로 인식함
	 */
	private List<String> assignUserIdList;

	/**
	 * 리소스의 권한을 할당할 그룹 정보<br/>
	 * 값이 없을 경우에는 권한을 할당하지 않는 것으로 인식함
	 */
	private List<ACLGroupPermission> groupPermissionList;

	/**
	 * 리소스의 권한을 할당할 역할 아이디<br/>
	 * 값이 없을 경우에는 권한을 할당하지 않는 것으로 인식함
	 */
	private List<String> roleIdList;

	/**
	 * 리소스의 권한을 할당할 직군 코드<br/>
	 * 값이 없을 경우에는 권한을 할당하지 않는 것으로 인식함
	 */
	private List<String> jobClassCodeList;

	/**
	 * 리소스의 권한을 할당할 직책 코드<br/>
	 * 값이 없을 경우에는 권한을 할당하지 않는 것으로 인식함
	 */
	private List<String> jobDutyCodeList;
	
	/**
	 * 리소스의 권한의 예외 사용자 아이디<br/>
	 * 값이 있을 경우에는 권한을 할당하지 않는 것으로 인식함
	 */
	private List<String> exceptUserIdList;

	/**
	 * 유저권한 상세정보
	 */
	private List<User> assignUserDetailList;
	
	/**
	 * 그룹권한 상세정보
	 */
	private List<Group> groupDetailList;
	
	/**
	 * 역할권한 상세정보
	 */
	private List<Role> roleDetailList;
	
	/**
	 * 고용형태 권한 상세정보
	 */
	private List<JobClass> jobClassDetailList;
	
	/**
	 * 직책 권한 상세정보
	 */
	private List<JobDuty> jobDutyDetailList;
	
	/**
	 * 예외유저권한 상세정보
	 */
	private List<User> exceptUserDetailList;

	public List<JobClass> getJobClassDetailList() {
		return jobClassDetailList;
	}
	
	public void setJobClassDetailList(List<JobClass> jobClassDetailList) {
		this.jobClassDetailList = jobClassDetailList;
	}
	
	public List<JobDuty> getJobDutyDetailList() {
		return jobDutyDetailList;
	}

	public void setJobDutyDetailList(List<JobDuty> jobDutyDetailList) {
		this.jobDutyDetailList = jobDutyDetailList;
	}
	
	public List<Role> getRoleDetailList() {
		return roleDetailList;
	}

	public void setRoleDetailList(List<Role> roleDetailList) {
		this.roleDetailList = roleDetailList;
	}

	public List<User> getAssignUserDetailList() {
		return assignUserDetailList;
	}

	public void setAssignUserDetailList(List<User> assignUserDetailList) {
		this.assignUserDetailList = assignUserDetailList;
	}

	public List<User> getExceptUserDetailList() {
		return exceptUserDetailList;
	}

	public void setExceptUserDetailList(List<User> exceptUserDetailList) {
		this.exceptUserDetailList = exceptUserDetailList;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceDescription
	 */
	public String getResourceDescription() {
		return resourceDescription;
	}

	/**
	 * @param resourceDescription the resourceDescription to set
	 */
	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the open
	 */
	public int getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(int open) {
		this.open = open;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the permissionName
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * @param permissionName the permissionName to set
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * @return the permissionDescription
	 */
	public String getPermissionDescription() {
		return permissionDescription;
	}

	/**
	 * @param permissionDescription the permissionDescription to set
	 */
	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}

	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * @return the assignUserIds
	 */
	public List<String> getAssignUserIdList() {
		return assignUserIdList;
	}

	/**
	 * @param assignUserIdList the assignUserIdList to set
	 */
	public void setAssignUserIdList(List<String> assignUserIdList) {
		this.assignUserIdList = assignUserIdList;
	}

	/**
	 * @param assignUserId
	 */
	public void addAssignUserId(String assignUserId) {
		if (this.assignUserIdList == null) {
			this.assignUserIdList = new ArrayList<String>();
		}

		this.assignUserIdList.add(assignUserId);
	}

	public List<Group> getGroupDetailList() {
		return groupDetailList;
	}

	public void setGroupDetailList(List<Group> groupDetailList) {
		this.groupDetailList = groupDetailList;
	}
	/**
	 * @return the groupPermissionList
	 */
	public List<ACLGroupPermission> getGroupPermissionList() {
		return groupPermissionList;
	}

	/**
	 * @param groupPermissionList the groupPermissionList to set
	 */
	public void setGroupPermissionList(List<ACLGroupPermission> groupPermissionList) {
		this.groupPermissionList = groupPermissionList;
	}

	/**
	 * @param groupId
	 * @param hierarchyPermission
	 */
	public void addGroupPermission(String groupId, int hierarchyPermission) {
		if (this.groupPermissionList == null) {
			this.groupPermissionList = new ArrayList<ACLGroupPermission>();
		}

		ACLGroupPermission aclGroupPermission = new ACLGroupPermission();
		aclGroupPermission.setGroupId(groupId);
		aclGroupPermission.setHierarchyPermission(hierarchyPermission);

		this.groupPermissionList.add(aclGroupPermission);
	}

	/**
	 * @return the roleIdList
	 */
	public List<String> getRoleIdList() {
		return roleIdList;
	}

	/**
	 * @param roleIdList the roleIdList to set
	 */
	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}

	/**
	 * @param roleId
	 */
	public void addRoleId(String roleId) {
		if (this.roleIdList == null) {
			this.roleIdList = new ArrayList<String>();
		}

		this.roleIdList.add(roleId);
	}

	/**
	 * @return the jobClassCodeList
	 */
	public List<String> getJobClassCodeList() {
		return jobClassCodeList;
	}

	/**
	 * @param jobClassCodeList the jobClassCodeList to set
	 */
	public void setJobClassCodeList(List<String> jobClassCodeList) {
		this.jobClassCodeList = jobClassCodeList;
	}

	/**
	 * @param jobClassCode
	 */
	public void addJobClassCode(String jobClassCode) {
		if (this.jobClassCodeList == null) {
			this.jobClassCodeList = new ArrayList<String>();
		}

		this.jobClassCodeList.add(jobClassCode);
	}

	/**
	 * @return the jobDutyCodeList
	 */
	public List<String> getJobDutyCodeList() {
		return jobDutyCodeList;
	}

	/**
	 * @param jobDutyCodeList the jobDutyCodeList to set
	 */
	public void setJobDutyCodeList(List<String> jobDutyCodeList) {
		this.jobDutyCodeList = jobDutyCodeList;
	}

	/**
	 * @param jobDutyCode
	 */
	public void addJobDutyCode(String jobDutyCode) {
		if (this.jobDutyCodeList == null) {
			this.jobDutyCodeList = new ArrayList<String>();
		}

		this.jobDutyCodeList.add(jobDutyCode);
	}

	/**
	 * @return the exceptUserIdList
	 */
	public List<String> getExceptUserIdList() {
		return exceptUserIdList;
	}

	/**
	 * @param exceptUserIdList the exceptUserIdList to set
	 */
	public void setExceptUserIdList(List<String> exceptUserIdList) {
		this.exceptUserIdList = exceptUserIdList;
	}
	
	/**
	 * @param exceptUserId
	 */
	public void addExceptUserId(String exceptUserId) {
		if (this.exceptUserIdList == null) {
			this.exceptUserIdList = new ArrayList<String>();
		}

		this.exceptUserIdList.add(exceptUserId);
	}

}
