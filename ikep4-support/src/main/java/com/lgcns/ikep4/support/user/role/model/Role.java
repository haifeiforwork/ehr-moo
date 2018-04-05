/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: Role.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class Role extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -928491024738519109L;

	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 롤(역할) ID
	 */
	private String roleId;

	/**
	 * 롤(역할) 이름
	 */
	@NotNull
	@Size(min = 0, max = 60)
	private String roleName;
	
	@NotNull
	@Size(min = 0, max = 40)
	private String roleEnglishName;

	/**
	 * 해당 롤의 롤 타입 ID
	 */
	private String roleTypeId;

	/**
	 * 롤 타입의 이름
	 */
	private String roleTypeName;

	/**
	 * 롤 설명
	 */
	@NotNull
	@Size(min = 0, max = 150)
	private String description;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private String registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private String updateDate;

	/**
	 * 해당 Portal ID
	 */
	private String portalId;
	
	/**
	 * 코드 중복체크용 변수
	 */
	private String codeCheck;
	
	/**
	 * 역할에 할당된 유저 ID를 담고 있는 리스트(RoleID, UserID)
	 */
	private List<User> userList;
	
	/**
	 * 역할에 할당된 그룹의 ID를 담고 있는 리스트(RoleID, GroupID)
	 */
	private List<Group> groupList;

	/**
	 * 그룹 ID(Group-Role 매핑 시에 사용)
	 */
	private String groupId;
	
	/**
	 * 그룹명(Group-Role 매핑 시에 사용)
	 */
	private String groupName;
	
	/**
	 * 영문 그룹명(Group-Role 매핑 시에 사용)
	 */
	private String groupEnglishName;
	
	/**
	 * 사용자ID(User-Role 매핑 시에 사용)
	 */
	private String userId;
	
	/**
	 * 사용자명(User-Role 매핑 시에 사용)
	 */
	private String userName;
	
	/**
	 * 영문 사용자명(User-Role 매핑 시에 사용)
	 */
	private String userEnglishName;
	
	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCodeCheck() {
		return codeCheck;
	}

	public void setCodeCheck(String codeCheck) {
		this.codeCheck = codeCheck;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(String roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getRoleEnglishName() {
		return roleEnglishName;
	}

	public void setRoleEnglishName(String roleEnglishName) {
		this.roleEnglishName = roleEnglishName;
	}

	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

}
