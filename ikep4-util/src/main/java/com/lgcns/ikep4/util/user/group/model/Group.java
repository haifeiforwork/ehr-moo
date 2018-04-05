/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.user.group.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.util.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: Group.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Group extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6886713921849684521L;

	/**
	 * 그룹(부서) ID
	 */
	@NotEmpty
	private String groupId;

	/**
	 * 그룹(부서) 이름
	 */
	@NotNull
	@Size(min = 0, max = 60)
	private String groupName;

	/**
	 * 그룹(부서) 영문 이름
	 */
	@NotNull
	@Size(min = 0, max = 100)
	private String groupEnglishName;

	/**
	 * 부모(상위) 그룹 ID
	 */
	private String parentGroupId;

	/**
	 * 부모(상위) 그룹 이름
	 */
	private String parentGroupName;

	/**
	 * 정렬 순서
	 */
	@NotEmpty
	private String sortOrder;

	/**
	 * 해당 그룹의 그룹 타입 ID
	 */
	private String groupTypeId;

	/**
	 * 그룹 타입의 이름
	 */
	private String groupTypeName;

	/**
	 * 그룹 타입의 이름
	 */
	private String groupTypeEnglishName;

	/**
	 * 자식(하위) 그룹의 수
	 */
	private String childGroupCount;

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
	 * 그룹의 역할 ID(Role-group 매핑시에 사용됨)
	 */
	private String roleId;

	/**
	 * 속한 그룹의 최상위 그룹 Id
	 */
	private String rootGroupId;

	/**
	 * 속한 그룹의 최상위 그룹 명
	 */
	private String rootGroupName;

	/**
	 * 속한 그룹의 최상위 그룹 영문 명
	 */
	private String rootGroupEnglishName;

	/**
	 * 속한 그룹의 리더 아이디
	 */
	private String leaderId;
	
	/**
	 * 속한 그룹의 리더 명
	 */
	private String leaderName;

	/**
	 * 속한 그룹의 리더 영문 명
	 */
	private String leaderEnglishName;

	/**
	 * 리더의 JobTitle 명
	 */
	private String leaderJobTitle;
	
	/**
	 * 리더의 JobTitle 영문 명
	 */
	private String leaderEnglishJobTitle;
	
	/**
	 * 리더의 팀 이름
	 */
	private String leaderTeamName;

	/**
	 * 리더의 팀 이름
	 */
	private String leaderTeamEnglishName;
	
	/**
	 * 조직도 그룹 Type
	 */
	private String groupType;

	/**
	 * 중복체크를 위한 변수
	 */
	private String checkCodeFlag;

	/**
	 * 그룹에 속한 유저의 이름과 ID를 담고 있는 리스트(GroupID, UserName, UserID)
	 */
	private List<User> userList;

	/**
	 * 리스트 표시 여부 Flag(0: 안보임, 1:보임)
	 */
	private String viewOption;

	/**
	 * RootNode부터 자신까지의 Path
	 */
	private String fullPath;

	/**
	 * 엑셀저장시 입력성공여부
	 */
	private String successYn;

	/**
	 * 엑셀저장시 에러메시지
	 */
	private String errMsg;

	/**
	 * 계층형 조직 정보의 의 Level 값 (역순)
	 */
	private Integer levelNum;

	public String getViewOption() {
		return viewOption;
	}

	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupEnglishName
	 */
	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	/**
	 * @param groupEnglishName the groupEnglishName to set
	 */
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getParentGroupName() {
		return parentGroupName;
	}

	public void setParentGroupName(String parentGroupName) {
		this.parentGroupName = parentGroupName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(String groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public String getGroupTypeName() {
		return groupTypeName;
	}

	public void setGroupTypeName(String groupTypeName) {
		this.groupTypeName = groupTypeName;
	}

	public String getChildGroupCount() {
		return childGroupCount;
	}

	public void setChildGroupCount(String childGroupCount) {
		this.childGroupCount = childGroupCount;
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

	/**
	 * @return the rootGroupId
	 */
	public String getRootGroupId() {
		return rootGroupId;
	}

	/**
	 * @param rootGroupId the rootGroupId to set
	 */
	public void setRootGroupId(String rootGroupId) {
		this.rootGroupId = rootGroupId;
	}

	/**
	 * @return the rootGroupName
	 */
	public String getRootGroupName() {
		return rootGroupName;
	}

	/**
	 * @param rootGroupName the rootGroupName to set
	 */
	public void setRootGroupName(String rootGroupName) {
		this.rootGroupName = rootGroupName;
	}

	
	/**
	 * @return the leaderId
	 */
	public String getLeaderId() {
		return leaderId;
	}

	/**
	 * @param leaderId the leaderId to set
	 */
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}


	public String getCheckCodeFlag() {
		return checkCodeFlag;
	}

	public void setCheckCodeFlag(String checkCodeFlag) {
		this.checkCodeFlag = checkCodeFlag;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getSuccessYn() {
		return successYn;
	}

	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the leaderEnglishName
	 */
	public String getLeaderEnglishName() {
		return leaderEnglishName;
	}

	/**
	 * @param leaderEnglishName the leaderEnglishName to set
	 */
	public void setLeaderEnglishName(String leaderEnglishName) {
		this.leaderEnglishName = leaderEnglishName;
	}

	/**
	 * @return the rootGroupEnglishName
	 */
	public String getRootGroupEnglishName() {
		return rootGroupEnglishName;
	}

	/**
	 * @param rootGroupEnglishName the rootGroupEnglishName to set
	 */
	public void setRootGroupEnglishName(String rootGroupEnglishName) {
		this.rootGroupEnglishName = rootGroupEnglishName;
	}

	/**
	 * @return the levelNum
	 */
	public Integer getLevelNum() {
		return levelNum;
	}

	/**
	 * @param levelNum the levelNum to set
	 */
	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	public String getGroupTypeEnglishName() {
		return groupTypeEnglishName;
	}

	public void setGroupTypeEnglishName(String groupTypeEnglishName) {
		this.groupTypeEnglishName = groupTypeEnglishName;
	}

	
	/**
	 * @return the leaderJobTitle
	 */
	public String getLeaderJobTitle() {
		return leaderJobTitle;
	}

	/**
	 * @param leaderJobTitle the leaderJobTitle to set
	 */
	public void setLeaderJobTitle(String leaderJobTitle) {
		this.leaderJobTitle = leaderJobTitle;
	}

	public String getLeaderEnglishJobTitle() {
		return leaderEnglishJobTitle;
	}

	public void setLeaderEnglishJobTitle(String leaderEnglishJobTitle) {
		this.leaderEnglishJobTitle = leaderEnglishJobTitle;
	}

	public String getLeaderTeamName() {
		return leaderTeamName;
	}

	public void setLeaderTeamName(String leaderTeamName) {
		this.leaderTeamName = leaderTeamName;
	}

	public String getLeaderTeamEnglishName() {
		return leaderTeamEnglishName;
	}

	public void setLeaderTeamEnglishName(String leaderTeamEnglishName) {
		this.leaderTeamEnglishName = leaderTeamEnglishName;
	}


	
}
